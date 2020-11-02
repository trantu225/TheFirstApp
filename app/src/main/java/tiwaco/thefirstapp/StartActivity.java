package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    ProgressDialog mProgressDialog;
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

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            if (netInfo != null && netInfo.isConnected()) {
//                Runtime runtime = Runtime.getRuntime();
//                try {
//                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//                    int     exitValue = ipProcess.waitFor();
//                    return (exitValue == 0);
//                }
//                catch (IOException e)          { e.printStackTrace(); }
//                catch (InterruptedException e) { e.printStackTrace(); }
//
//                return false;
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
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
//                        Intent updateIntent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(getString(R.string.link_apk)));
//
//
//                        updateIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                        startActivity(updateIntent);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            recreate();
//                        } else {
//                            Intent intent = getIntent();
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            finish();
//                            overridePendingTransition(0, 0);
//
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                        }


                        mProgressDialog = new ProgressDialog(StartActivity.this);
                        mProgressDialog.setMessage("Đang tải...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.setCancelable(true);

                        // execute this when the downloader must be fired
                        final StartActivity.DownloadTask downloadTask = new StartActivity.DownloadTask(StartActivity.this);
                        String url2 = "https://cskh.tiwaco.com.vn/tiwaread.apk";
                        String url = "http://tiwaco.com.vn/uploads/apk/tiwaread.apk";
                        downloadTask.execute(url);

                        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                downloadTask.cancel(true); //cancel the task
                            }
                        });

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

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        File extStore = Environment.getExternalStorageDirectory();
        String filename = "tiwaread.apk";
        String duongdanfile = extStore.getAbsolutePath() + "/" + filename;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage() + ".Try again.";
                } else {

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();
                    input = new BufferedInputStream(connection.getInputStream());
                    // download the file
                    //input = connection.getInputStream();

                    output = new FileOutputStream(duongdanfile);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                    return null;
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            Log.e("response", String.valueOf(result));
            if (result != null) {
                //Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load_server);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final DownloadTask downloadTask = new DownloadTask(StartActivity.this);

                        String url = "http://tiwaco.com.vn/uploads/apk/tiwaread.apk";
                        downloadTask.execute(url);

                        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                downloadTask.cancel(true); //cancel the task
                            }
                        });
                    }
                });
                alertDialogBuilder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        finish();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();


            } else {
                //solancapnhatthatbai = 0;
                Toast.makeText(context, "Download thành công", Toast.LENGTH_SHORT).show();


                File file = new File(duongdanfile); // assume refers to "sdcard/myapp_folder/myapp.apk"


                Uri fileUri = Uri.fromFile(file); //for Build.VERSION.SDK_INT <= 24

                if (Build.VERSION.SDK_INT >= 24) {

                    //fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                    final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

                    fileUri = FileProvider.getUriForFile(StartActivity.this,
                            AUTHORITY,
                            new File(duongdanfile));
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //dont forget add this line
                    context.startActivity(intent);


                } catch (Exception s) {
                    Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                }


            }


        }


    }
}
