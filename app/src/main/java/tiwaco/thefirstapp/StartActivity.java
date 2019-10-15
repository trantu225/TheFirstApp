package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;

public class StartActivity extends AppCompatActivity  {

    ImageButton btnGhinuoc, btnDanhSachKH, btnLoadDl, btnBackup,btnSearch, btnLogout,btnHistory;
    LinearLayout layout2, layout3;
    TextView txtuser;
    Context con;
    SPData spdata;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    KhachHangThuDAO khachhangthuDAO;
    SharedPreferences pre;
    LichSuDAO lichsudao;
    String urlcapnhat = "";
    NetworkChangeReceiver networkreceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con =  StartActivity.this;

        setContentView(R.layout.activity_start);
//        if(spdata.getDataNhanVienTrongSP().equalsIgnoreCase("Tu") || spdata.getDataNhanVienTrongSP().equalsIgnoreCase("HoaiLinh")|| spdata.getDataNhanVienTrongSP().equalsIgnoreCase("Admin")){
//
//      chieu cao 2   }
//        else {
//            setContentView(R.layout.activity_start_hide_loaddata);
//        }

        getSupportActionBar().hide();
        khachhangDAO = new KhachHangDAO(con);
        khachhangthuDAO = new KhachHangThuDAO(con);
        lichsudao = new LichSuDAO(con);
        duongDAO = new DuongDAO(con);
        spdata = new SPData(con);
        networkreceiver = new NetworkChangeReceiver();
        registerReceiver(networkreceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        btnGhinuoc = (ImageButton) findViewById(R.id.btn_ghinuoc);
        btnDanhSachKH = (ImageButton) findViewById(R.id.btn_dskh);
        btnLoadDl = (ImageButton) findViewById(R.id.btn_loaddata);
        btnBackup = (ImageButton) findViewById(R.id.btn_backup);
        btnSearch = (ImageButton) findViewById(R.id.btn_timkiem);
        btnLogout =(ImageButton) findViewById(R.id.btn_dangxuat);
        btnHistory = (ImageButton) findViewById(R.id.btn_lichsu);
        layout2 = (LinearLayout) findViewById(R.id.layout_2);
        layout3 = (LinearLayout) findViewById(R.id.layout_3);
        txtuser = (TextView) findViewById(R.id.txt_user);


        ViewGroup.LayoutParams params = layout3.getLayoutParams();

        params.height = getViewHeight(layout2);
        Log.e("chieu cao 2", String.valueOf(getViewHeight(layout2)));
        layout3.setLayoutParams(params);
        btnGhinuoc.setOnClickListener(myclick);
        btnDanhSachKH.setOnClickListener(myclick);
        btnLoadDl.setOnClickListener(myclick);
        btnBackup.setOnClickListener(myclick);
        btnSearch.setOnClickListener(myclick);
        btnLogout.setOnClickListener(myclick);
        btnHistory.setOnClickListener(myclick);

       // pre= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
        Bien.bienbkall = spdata.getDataBKALLTrongSP();
        Bien.bienbkcg = spdata.getDataBKCGTrongSP();
        Bien.bienbkdg = spdata.getDataBKDGTrongSP();
        Bien.bienbkdghn = spdata.getDataBKDGHomNayTrongSP();

        Log.e("ten nhanvien ", spdata.getDataTenNhanVien());
        txtuser.setText("Người dùng: " + spdata.getDataTenNhanVien());
        Log.e("flag flagghi", String.valueOf(Bien.bienghi));
        Log.e("flag flagall", String.valueOf(Bien.bienbkall));
        Log.e("flag flagcg", String.valueOf(Bien.bienbkcg));
        Log.e("flag flagdg", String.valueOf(Bien.bienbkdg));
        urlcapnhat = getString(R.string.API_CapNhatVaSuDung) + "/" + String.valueOf(BuildConfig.VERSION_CODE);
        btnBackup.setEnabled(true);
        btnBackup.setBackgroundResource(R.drawable.ic_save);


//
//        try {
//            if (isInternetOn()) {
//                new CheckUpdate().execute(urlcapnhat);
//            } else {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
//                // khởi tạo dialog
//                alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
//                // thiết lập nội dung cho dialog
//
//                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//
//                        // button "no" ẩn dialog đi
//                    }
//                });
//                alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                        // button "no" ẩn dialog đi
//                    }
//                });
//
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                // tạo dialog
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.show();
//            }
//        }
//        catch(Exception e){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
//            // khởi tạo dialog
//            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
//            // thiết lập nội dung cho dialog
//
//            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//
//                    // button "no" ẩn dialog đi
//                }
//            });
//            alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//
//                    // button "no" ẩn dialog đi
//                }
//            });
//
//
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            // tạo dialog
//            alertDialog.setCanceledOnTouchOutside(false);
//            alertDialog.show();
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        kiemtracapnhat();
        super.onResume();


    }

    public void kiemtracapnhat() {
        try {
            if (isInternetOn()) {
                new CheckUpdate().execute(urlcapnhat);
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        // button "no" ẩn dialog đi
                    }
                });
                alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();

                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
            // thiết lập nội dung cho dialog

            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    // button "no" ẩn dialog đi
                }
            });
            alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    // button "no" ẩn dialog đi
                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            // tạo dialog
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }
    @Override
    protected void onDestroy() {
        if (networkreceiver != null) {
            unregisterReceiver(networkreceiver);
        }
        super.onDestroy();
        Log.e("DESTROY", "DESTROY-----------------------------------------------");


    }

    public final boolean isInternetOn() {

        boolean k = false;
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING) {

            // if connected with internet

            // Toast.makeText(this, connec.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
            k = true;

        } else if (
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            //Toast.makeText(this, " Chưa có internet hoặc 3G/4G ", Toast.LENGTH_LONG).show();
            k = false;
        }

        return k;
    }
    public static int getViewHeight(View view) {
        WindowManager wm =
                (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceWidth;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        } else {
            deviceWidth = display.getWidth();
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight(); //        view.getMeasuredWidth();
    }
    private View.OnClickListener myclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent;
            switch (v.getId()) {
                case R.id.btn_ghinuoc:

//                    String maduong = spdata.getDataDuongDangGhiTrongSP();
//
//
//                    if (maduong.equals("")) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
//                        // khởi tạo dialog
//                        alertDialogBuilder.setMessage(R.string.start_chuacoduongdeghinuoc);
//                        // thiết lập nội dung cho dialog
//                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                Intent myIntent2 = new Intent(con, ListActivity.class);
//                                startActivity(myIntent2);
//                                finish();
//                            }
//                        });
//
//                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//                            }
//                        });
//
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        // tạo dialog
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                    } else {
//                        Log.e("maduong", maduong);
//                        String tenduong = duongDAO.getTenDuongTheoMa(maduong);
//                        String mess = "Bạn có muốn tiếp tục ghi nước đường " + maduong + "." + tenduong + " không?";
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
//                        // khởi tạo dialog
//                        alertDialogBuilder.setMessage(mess);
//                        // thiết lập nội dung cho dialog
//                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//
//                                Intent myIntent = new Intent(con, MainActivity.class);
//                                //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
//                                myIntent.putExtra("MauLoadGhiThu", "1");
//                                startActivity(myIntent);
//
//
//                                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
//                                //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
//                                // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
//                            }
//                        });
//
//                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
//                                // khởi tạo dialog
//                                alertDialogBuilder.setMessage("Bạn có muốn ghi nước đường khác không?");
//                                // thiết lập nội dung cho dialog
//                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//
//                                        Intent myIntent2 = new Intent(con, ListActivity.class);
//                                        startActivity(myIntent2);
//                                    }
//                                });
//
//                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//
//
//                                    }
//                                });
//
//                                AlertDialog alertDialog = alertDialogBuilder.create();
//                                // tạo dialog
//                                alertDialog.setCanceledOnTouchOutside(false);
//                                alertDialog.show();
//
//                            }
//                        });
//
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        // tạo dialog
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                    }

                    //Chọn màn hình ghi hay thu

                    ViewDialog_GhiThu alert = new ViewDialog_GhiThu();
                    alert.showDialog(StartActivity.this, "Chọn chức năng ghi/thu: ", 1); //1: chuc nang ghi



                    break;

                case R.id.btn_dskh:
//                    Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
//                    myIntent = new Intent(StartActivity.this, ListActivity.class);
//                    startActivity(myIntent);

                    //Chọn màn hình ghi hay thu

                    if (khachhangDAO.countKhachHangAll() == 0 && khachhangthuDAO.countKhachHangAll() == 0) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa có dữ liệu. Hãy tiến hành lấy dữ liệu");
                        // thiết lập nội dung cho dialog


                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Luu lai file chua sqlite cũ


                                //Intent myIntent = new Intent(StartActivity.this, LoadActivity.class);
                                ViewDialog alert = new ViewDialog();
                                alert.showDialog(StartActivity.this, "Chọn nguồn để load dữ liệu: ");

                                // Intent myIntent = new Intent(StartActivity.this, LoadFromServerActivity.class);
                                // startActivity(myIntent);

                            }
                        });

                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // button "no" ẩn dialog đi
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {

                        ViewDialog_GhiThu alert2 = new ViewDialog_GhiThu();
                        alert2.showDialog(StartActivity.this, "Chọn chức năng ghi/thu: ", 2); //2: chuc nang danh sach

                    }
                    break;
                case R.id.btn_backup:

                    ViewDialog_ChonNguonBackUp alert1 = new ViewDialog_ChonNguonBackUp();
                    alert1.showDialog(StartActivity.this, "Chọn nguồn để lưu dữ liệu: ");




                 //  myIntent = new Intent(StartActivity.this, Backup_Activity.class);
                 //   startActivity(myIntent);
                    break;


                case R.id.btn_loaddata:
                    Log.e("LOADDATA", "OK");
                    if (KiemTraTonTaiDuLieu()) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(R.string.delete_file_load_file);
                        // thiết lập nội dung cho dialog


                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Luu lai file chua sqlite cũ


                                //Intent myIntent = new Intent(StartActivity.this, LoadActivity.class);
                                ViewDialog alert = new ViewDialog();
                                alert.showDialog(StartActivity.this, "Chọn nguồn để load dữ liệu: ");

                               // Intent myIntent = new Intent(StartActivity.this, LoadFromServerActivity.class);
                               // startActivity(myIntent);

                            }
                        });

                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // button "no" ẩn dialog đi
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {
                        ViewDialog alertload = new ViewDialog();
                        alertload.showDialog(StartActivity.this, "Chọn nguồn để load dữ liệu: ");
                    }

                        break;

                case R.id.btn_timkiem:

//                    myIntent = new Intent(StartActivity.this, SearchActivity.class);
//                    startActivity(myIntent);
                    ViewDialog_GhiThu alert3 = new ViewDialog_GhiThu();
                    alert3.showDialog(StartActivity.this, "Chọn chức năng ghi/thu: ", 3); //2: chuc nang danh sach
                    Bien.bienManHinhChuyenTimKiem ="start";

                    break;
                case R.id.btn_lichsu:

                    myIntent = new Intent(StartActivity.this, ThongTinActivity.class);
                    startActivity(myIntent);

                    break;
                case R.id.btn_dangxuat:
                    LichSuDTO ls = new LichSuDTO();
                    ls.setNoiDungLS(spdata.getDataNhanVienTrongSP() +" đăng xuất.");
                    ls.setMaLenh("DN");
                    String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    ls.setThoiGianLS(thoigian1);
                    lichsudao.addTable_History(ls);
                    spdata.luuDataNhanVienTrongSP("");
                    spdata.luuDataMatKhauNhanVienTrongSP("");
                    spdata.luuThongTinNhanVien("", "", "", "", "");
                    finish();
                    myIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(myIntent);


                    break;
                    }

            }
        }

        ;

        private void taoDialogThongBao(String message) {
            //Hiển thị dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(message);
            // thiết lập nội dung cho dialog

            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    // button "no" ẩn dialog đi
                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            // tạo dialog
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            // hiển thị dialog
        }
    private Boolean KiemTraTonTaiDuLieu(){

        if(duongDAO.countDuong() <=0 && khachhangDAO.countKhachHangAll()<=0)
        {
            return false;
        }
        return true;
    }


    public class CheckUpdate extends AsyncTask<String, Void, String> {

        String fileContent = "";

        @Override
        protected String doInBackground(String... connUrl) {
            if (!isCancelled()) {


                try {

                    final URL url = new URL(connUrl[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                    conn.setRequestMethod("GET");
                    int result = conn.getResponseCode();
                    if (result == 200) {

                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder sb = new StringBuilder();
                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            // long status = (i+1) *100/line.length();
                            //     String.valueOf(status)

                            fileContent = line;

                        }
                        reader.close();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("loi load du lieu", ex.toString());
                }
                if (fileContent.equals("")) {
                    Log.e("filetuserver", "Rong");
                } else {
                    Log.e("filetuserver", fileContent);
                }


            }
            return fileContent;

        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result update", result);

            if (result.equals("1")) { //phiên bản mới nhất
                this.cancel(true);

            } else if (result.equals("-1")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Không có thông tin về bản cập nhật phần mềm");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            } else if (result.equals("0")) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Hãy cập nhật phiên bản mới nhất để tiếp tục sử dụng phần mềm.Bạn có muốn cập nhật phiên bản mới nhất không?");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.link_apk)));


                        updateIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        startActivity(updateIntent);
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }

                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        finish();
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            recreate();
//                        } else {
//                            Intent intent = getIntent();
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                        }
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            } else if (result.equals("")) {
                this.cancel(true);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load_server);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            }

        }
    }
}
