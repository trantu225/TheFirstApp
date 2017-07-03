package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Build;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;

public class StartActivity extends AppCompatActivity  {

    ImageButton btnGhinuoc, btnDanhSachKH, btnLoadDl, btnBackup,btnSearch, btnLogout,btnHistory;
    LinearLayout layout2, layout3;
    Context con;
    SPData spdata;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    SharedPreferences pre;
    LichSuDAO lichsudao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con =  StartActivity.this;
        spdata = new SPData(con);
        if(spdata.getDataNhanVienTrongSP().equalsIgnoreCase("Tu") || spdata.getDataNhanVienTrongSP().equalsIgnoreCase("HoaiLinh")){

            setContentView(R.layout.activity_start);
        }
        else {
            setContentView(R.layout.activity_start_hide_loaddata);
        }

        getSupportActionBar().hide();
        khachhangDAO = new KhachHangDAO(con);
        lichsudao = new LichSuDAO(con);
        btnGhinuoc = (ImageButton) findViewById(R.id.btn_ghinuoc);
        btnDanhSachKH = (ImageButton) findViewById(R.id.btn_dskh);
        btnLoadDl = (ImageButton) findViewById(R.id.btn_loaddata);
        btnBackup = (ImageButton) findViewById(R.id.btn_backup);
        btnSearch = (ImageButton) findViewById(R.id.btn_timkiem);
        btnLogout =(ImageButton) findViewById(R.id.btn_dangxuat);
        btnHistory = (ImageButton) findViewById(R.id.btn_lichsu);
        layout2 = (LinearLayout) findViewById(R.id.layout_2);
        layout3 = (LinearLayout) findViewById(R.id.layout_3);



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
        duongDAO = new DuongDAO(con);
        spdata = new SPData(con);
        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
        Bien.bienbkall = spdata.getDataBKALLTrongSP();
        Bien.bienbkcg = spdata.getDataBKCGTrongSP();
        Bien.bienbkdg = spdata.getDataBKDGTrongSP();
        Bien.bienbkdghn = spdata.getDataBKDGHomNayTrongSP();
        Log.e("flag flagghi", String.valueOf(Bien.bienghi));
        Log.e("flag flagall", String.valueOf(Bien.bienbkall));
        Log.e("flag flagcg", String.valueOf(Bien.bienbkcg));
        Log.e("flag flagdg", String.valueOf(Bien.bienbkdg));
        if( Bien.bienbkall == Bien.bienghi  && Bien.bienbkcg ==Bien.bienghi  && Bien.bienbkdg ==Bien.bienghi  && Bien.bienbkdghn ==Bien.bienghi  )
        {
            btnBackup.setEnabled(false);
            btnBackup.setBackgroundResource(R.drawable.ic_save_disable);
          //  taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
        }
        else{

            btnBackup.setEnabled(true);
            btnBackup.setBackgroundResource(R.drawable.ic_save);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if( (Bien.bienbkall == Bien.bienghi  && Bien.bienbkcg ==Bien.bienghi && Bien.bienbkdg ==Bien.bienghi && Bien.bienbkdghn ==Bien.bienghi)
                || Bien.bienbkall == -1
                || (Bien.bienbkall == Bien.bienghi  && Bien.bienbkcg ==Bien.bienghi && Bien.bienbkdghn ==Bien.bienghi && Bien.bienbkdg ==-1 )
                || (Bien.bienbkall == Bien.bienghi  && Bien.bienbkcg ==Bien.bienghi && Bien.bienbkdg ==Bien.bienghi && Bien.bienbkdghn ==-1 )
                || (Bien.bienbkall == Bien.bienghi  && Bien.bienbkdg ==Bien.bienghi && Bien.bienbkdghn ==Bien.bienghi && Bien.bienbkcg == -1 ))
        {
            btnBackup.setEnabled(false);
            btnBackup.setBackgroundResource(R.drawable.ic_save_disable);
            //  taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
        }
        else{

            btnBackup.setEnabled(true);
            btnBackup.setBackgroundResource(R.drawable.selector_button_backup_change);
        }
     //   getWindow().getDecorView().findViewById(android.R.id.content).invalidate();

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
                    String maduong = spdata.getDataDuongDangGhiTrongSP();

                    String tenduong = duongDAO.getTenDuongTheoMa(maduong);
                    if (maduong.equals("")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(R.string.start_chuacoduongdeghinuoc);
                        // thiết lập nội dung cho dialog
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent myIntent2 = new Intent(StartActivity.this, ListActivity.class);
                                startActivity(myIntent2);
                            }
                        });

                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {
                        String mess = "Bạn có muốn tiếp tục ghi nước đường " + maduong + "." + tenduong + " không?";
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(mess);
                        // thiết lập nội dung cho dialog
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent myIntent1 = new Intent(StartActivity.this, MainActivity.class);
                                startActivity(myIntent1);

                                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                                //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
                                // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Bạn có muốn ghi nước đường khác không?");
                                // thiết lập nội dung cho dialog
                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent myIntent2 = new Intent(StartActivity.this, ListActivity.class);
                                        startActivity(myIntent2);
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();

                    }
                    break;

                case R.id.btn_dskh:
                    Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                    myIntent = new Intent(StartActivity.this, ListActivity.class);
                    startActivity(myIntent);

                    break;
                case R.id.btn_backup:

                    myIntent = new Intent(StartActivity.this, Backup_Activity.class);
                    startActivity(myIntent);
                    break;


                case R.id.btn_loaddata:
                    if (KiemTraTonTaiDuLieu()) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage(R.string.delete_file_load_file);
                        // thiết lập nội dung cho dialog


                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Luu lai file chua sqlite cũ


                                Intent myIntent = new Intent(StartActivity.this, LoadActivity.class);
                                startActivity(myIntent);

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
                    }

                        break;

                case R.id.btn_timkiem:

                    myIntent = new Intent(StartActivity.this, SearchActivity.class);
                    startActivity(myIntent);

                    break;
                case R.id.btn_lichsu:

                    myIntent = new Intent(StartActivity.this, HistoryActivity.class);
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
}
