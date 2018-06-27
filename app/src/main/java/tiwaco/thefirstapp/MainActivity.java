package tiwaco.thefirstapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.location.LocationServices;

import com.google.gson.Gson;
import com.luseen.spacenavigation.SpaceNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.JSONRequestObject;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ListKHTheoDuong;
import tiwaco.thefirstapp.DTO.ListRequestObject;
import tiwaco.thefirstapp.DTO.RequestObject;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;



public class MainActivity extends AppCompatActivity  {

    TextView danhsachduong;
    ImageButton btnghi;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String filename = "";
    String duongdanfile = "";
    boolean kiemtradieukien = false,kiemtradieukiencon = false; //cho phep ghi , false: ko ghi
    String batthuong1 = "", batthuong2 ="";
    private static String dataGhi = "";
    SpaceNavigationView spaceNavigationView;
    Context con;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    LichSuDAO lichsudao;
    KhachHangDTO khachhang;
    TextView BinhQuanBaThang,LoaiKH, DinhMuc,LabelDuong,STT,DanhBo, MaKH, HoTen, DiaChi, MaTLK, HieuTLK, CoTLK, ChiSo1, ChiSo2, ChiSo3, m31, m32, m33, ChiSoCon1, ChiSoCon2, ChiSoCon3, m3con1, m3con2, m3con3,  DuongDangGhi, ConLai;
    EditText DienThoai, ChiSoMoi, ChiSoMoiCon,TinhTrangTLK,GhiChu,m3moi, m3conmoi;
    TableRow chisocu_con_lb, chisocu_con, chisomoi_con_lb, chisomoi_con;
    ImageButton DoiSDT,Toi,Lui,Ghi,CapNhatGhiChu;
    ImageButton ChuyenLoai;
    Spinner spinTT;
    LinearLayout lay_toi , lay_lui , lay_ghi,lay_ki1,lay_ki2,lay_ki3;
    String STT_HienTai ="1";
    int SoLuongKH = 0;
    String maduong_nhan="",stt_nhan ="",makh_nhan="";
    int vitri_nhan = 0;
    SPData spdata;
    String tenduong="";
    String soKHconlai ="",tongsoKHTheoDuong="", soKHHomNay ="";
    //double  longitude,latitude;
    String vido ="" ;
    String kinhdo="";
    GPSTracker gps;
    boolean kt = false;
    private static boolean flagDangGhi = false;
    TinhTrangTLKDAO tinhtrangtlkdao;
    ArrayList<String>  arrTT = null ;
    String tenTT ="";
    Menu menumain;
    int bienkieughi =1;
    int sttmax =0;
    boolean biendaghichuaghi = false;
    List<String> listtoi = null, listlui=null;
    String ky1="", ky2="", ky3="";
    String urlstr ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;

        getSupportActionBar().setTitle(R.string.tab_ghinuoc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taoView();
        spdata = new SPData(con);
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);
        lichsudao = new LichSuDAO(con);
        listtoi = new ArrayList<>();
        listlui = new ArrayList<>();
        tinhtrangtlkdao = new TinhTrangTLKDAO(con);
        urlstr =   getString(R.string.API_UpdateKhachHangDaGhi2);
        loadDataTinhTrangTLK();

//        MyDatabaseHelper mydata = new MyDatabaseHelper(con);
//        SQLiteDatabase db = mydata.openDB();
//        mydata.resetDatabaseTT(db);
         List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//        for(int tt = 0 ; tt<listt.size();tt++){
//            tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//        }





        //----- lay toa do

        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        //----------------------------------------------------------------------------
        //lấy intent gọi Activity này
        //Lấy kết quả khi chọn button ghi nước tại listactivity
        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_MADUONG);
        if (packageFromCaller == null) {
            //get sharepreferences
            final String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP(); //lấy đường đang ghi

             //tìm min(stt) của khách hàng chưa nghi tại đường đang ghi
            //luc bat dau chua co gi
            Log.e("MAIN","luc bat dau chua co gi");
            if(SPduongdangghi.equalsIgnoreCase("")){

               //dialog chọn đường
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.main_chuacothongtinduong);
                // thiết lập nội dung cho dialog
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent=new Intent(MainActivity.this, ListActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else{
                //tu start vao main
                Log.e("MAIN","tu start vao main");


                maduong_nhan = SPduongdangghi;
                tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
                String STT = spdata.getDataSTTDangGhiTrongSP() ;
                if(STT.equals("")) {
                    STT= khachhangDAO.getSTTChuaGhiNhoNhat(maduong_nhan);
                }
                STT_HienTai = STT;
                Log.e("STTMIN SP--------------------------",STT_HienTai);
                SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
                setDataForView(STT_HienTai,maduong_nhan,"");

                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
                // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                Toast.makeText(MainActivity.this, maduong_nhan, Toast.LENGTH_SHORT).show();

            }


        } else {
            //từ listactivity  sang main để ghi nước
            Log.e("MAIN","từ listactivity  sang main để ghi nước");
            //Có Bundle rồi thì lấy các thông số dựa vào key maduong và stt
            maduong_nhan = packageFromCaller.getString(Bien.MADUONG);
            tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
            stt_nhan =  packageFromCaller.getString(Bien.STT);
            vitri_nhan =  packageFromCaller.getInt(Bien.VITRI);

            makh_nhan = packageFromCaller.getString(Bien.MAKH);
            if(makh_nhan == null){
                makh_nhan ="";
            }
            if(maduong_nhan.equals("99")){
                STT_HienTai = "0";
            }
            else {
                STT_HienTai = stt_nhan;
            }
            spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan,STT_HienTai);//luu vao sharepreferences
            SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
            setDataForView(STT_HienTai,maduong_nhan,makh_nhan);
          //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
            if(vitri_nhan !=-1) {
                spdata.luuDataIndexDuongDangGhiTrongSP(vitri_nhan);
                Log.e("MAINACTIVITY_vitriduong", String.valueOf(vitri_nhan));
                Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();

            }
            Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
        }
        //---set duong
        tenduong =  duongDAO.getTenDuongTheoMa(maduong_nhan).trim();
        DuongDangGhi.setText(tenduong);
        sttmax = Integer.valueOf(khachhangDAO.getSTTLonNhat(maduong_nhan));


        String kyhd = spdata.getDataKyHoaDonTrongSP();
        Log.e("KYHD",kyhd);
        if(!kyhd.equals("")){
            String nam = kyhd.substring(0,4);
            String thang = kyhd.substring(4);
            final String strkyhd = thang+"/"+nam;


            DateFormat df = new SimpleDateFormat("MM/yyyy");

            try {
                Date date = df.parse(strkyhd);


                Calendar c1 = Calendar.getInstance();
                c1.setTime(date);


                Log.e("Ngày ban đầu : " , df.format(c1.getTime()));
                c1.add(Calendar.MONTH, -1);
                ky1=  df.format(c1.getTime());
                Log.e("kỳ 1 : " , ky1);
                c1.add(Calendar.MONTH, -1);
                ky2  =df.format(c1.getTime());
                Log.e("kỳ 2 : " , ky2);
                c1.add(Calendar.MONTH, -1);
                ky3  = df.format(c1.getTime());
                Log.e("kỳ 3 : " , ky3);

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("loi date",e.toString());
            }

            lay_ki1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDialog_KyHD alert = new ViewDialog_KyHD();
                    alert.showDialog(MainActivity.this, ky1, ChiSo1.getText().toString(), m31.getText().toString());
                }
            });
            lay_ki2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDialog_KyHD alert = new ViewDialog_KyHD();
                    alert.showDialog(MainActivity.this, ky2, ChiSo2.getText().toString(), m32.getText().toString());
                }
            });
            lay_ki3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDialog_KyHD alert = new ViewDialog_KyHD();
                    alert.showDialog(MainActivity.this, ky3, ChiSo3.getText().toString(), m33.getText().toString());
                }
            });

        }

        //--------------------------------------------------------------------------




        lay_ghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    kiemTraDieuKienDeGhiNuoc();

                }else{
                    showGPSDisabledAlertToUser();
                }


            }
        });
        Ghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_ghi.performClick();
            }
        });

        lay_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!flagDangGhi) {
                    listlui.clear();
                    KhachHangDTO khnext = khachhangDAO.getKHTheoSTT_Duong_khacmaKH(STT_HienTai, maduong_nhan, DanhBo.getText().toString().trim(),"<>");
                    if (khnext != null && !KiemTraPhanTuCoTrongList(khnext.getMaKhachHang(),listtoi)) {

                        addListNeuChuaTonTai(MaKH.getText().toString().trim(),listtoi);
                        setDataForView(STT_HienTai, maduong_nhan, khnext.getMaKhachHang());
                        addListNeuChuaTonTai(khnext.getMaKhachHang(), listtoi);


                    } else {
                        Log.e("maduong stt",maduong_nhan + " " +STT_HienTai);
                        String sttketiep = "";
                        if(!biendaghichuaghi) {
                            sttketiep= khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                        }
                        else{
                            sttketiep = khachhangDAO.getSTTChuaGhiNhoNhatLonHonHienTai1(maduong_nhan, STT_HienTai);
                        }
                        Log.e("sttketiep",sttketiep);
                        int next = Integer.parseInt(sttketiep);// Integer.parseInt(STT_HienTai) + 1;
                        STT_HienTai = String.valueOf(next);
                        Log.e("BienSTTHIenTai", STT_HienTai);
                        Log.e("SoLuongKH", String.valueOf(SoLuongKH));

                        String strnextketiep = khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan,sttketiep);
                        int nexttiep = Integer.parseInt(strnextketiep);
                        //if (next + 1 > SoLuongKH) {
                        Log.e("strnextketiep", strnextketiep+"  " );
                        if (nexttiep >= sttmax) {

                            lay_toi.setEnabled(false);
                            Toi.setEnabled(false);
                            lay_toi.setBackgroundResource(R.color.space_background_color);
                        } else {
                            lay_toi.setEnabled(true);
                            Toi.setEnabled(true);
                            lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                        }
                        if (next > 0 && next <= sttmax) {


                            setDataForView(STT_HienTai, maduong_nhan, "");

                            next = nexttiep;//Integer.parseInt(STT_HienTai) + 1;
                            int pre =  Integer.valueOf(khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai));//Integer.parseInt(STT_HienTai) - 1;
                            Log.e("ve truoc va ve sau",pre +" "+ next);
                            if (next > sttmax || next == 0) {
                                lay_toi.setEnabled(false);
                                Toi.setEnabled(false);
                                lay_toi.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_toi.setEnabled(true);
                                Toi.setEnabled(true);
                                lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }
                            if (pre <= 0) {
                                lay_lui.setEnabled(false);
                                Lui.setEnabled(false);
                                lay_lui.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_lui.setEnabled(true);
                                Lui.setEnabled(true);
                                lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }
                        } else {
                            Log.e("BienSTTHIenTai", STT_HienTai);
                        }
                    }
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Bạn đang ghi nước.Bạn có muốn hủy ghi nước không?");

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String sttketiep = "";
                            if(!biendaghichuaghi) {
                                sttketiep= khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                            }
                            else{
                                sttketiep = khachhangDAO.getSTTChuaGhiNhoNhatLonHonHienTai1(maduong_nhan, STT_HienTai);
                            } Log.e("sttketiep",sttketiep);
                            int next = Integer.parseInt(sttketiep);// Integer.parseInt(STT_HienTai) + 1;
                           // int next = Integer.parseInt(STT_HienTai) + 1;
                            STT_HienTai = String.valueOf(next);
                            Log.e("BienSTTHIenTai", STT_HienTai);
                            Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                            String strnextketiep = khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan,sttketiep);
                            Log.e("strnextketiep", strnextketiep);
                            int nexttiep = Integer.parseInt(strnextketiep);
                            //if (next + 1 > SoLuongKH) {
                            if (nexttiep >= sttmax) {
                                lay_toi.setEnabled(false);
                                Toi.setEnabled(false);
                                lay_toi.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_toi.setEnabled(true);
                                Toi.setEnabled(true);
                                lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }
                            if (next > 0 && next <= sttmax) {
                                setDataForView(STT_HienTai, maduong_nhan,"");
                               // next = Integer.parseInt(STT_HienTai) + 1;
                               // int pre = Integer.parseInt(STT_HienTai) - 1;
                                next = nexttiep;//Integer.parseInt(STT_HienTai) + 1;
                                int pre =  Integer.valueOf(khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai));//Integer.parseInt(STT_HienTai) - 1;
                                Log.e("ve truoc va ve sau",pre +" "+ next);
                                if (next > sttmax || next == 0) {
                                    lay_toi.setEnabled(false);
                                    Toi.setEnabled(false);
                                    lay_toi.setBackgroundResource(R.color.space_background_color);
                                } else {
                                    lay_toi.setEnabled(true);
                                    Toi.setEnabled(true);
                                    lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                                }
                                if (pre <= 0) {
                                    lay_lui.setEnabled(false);
                                    Lui.setEnabled(false);
                                    lay_lui.setBackgroundResource(R.color.space_background_color);
                                } else {
                                    lay_lui.setEnabled(true);
                                    Lui.setEnabled(true);
                                    lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                                }
                            } else {
                                Log.e("BienSTTHIenTai", STT_HienTai);
                            }

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
                    // hiển thị dialog
                }

            }
        });
        Toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_toi.performClick();
            }
        });
        lay_lui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagDangGhi){
                    listtoi.clear();
                    KhachHangDTO khlui = khachhangDAO.getKHTheoSTT_Duong_khacmaKH(STT_HienTai, maduong_nhan, DanhBo.getText().toString().trim(),"<>");
                    if (khlui != null && !KiemTraPhanTuCoTrongList(khlui.getMaKhachHang(),listlui)) {

                        addListNeuChuaTonTai(MaKH.getText().toString().trim(),listlui);
                        setDataForView(STT_HienTai, maduong_nhan, khlui.getMaKhachHang());
                        addListNeuChuaTonTai(khlui.getMaKhachHang(), listlui);


                    } else {
                        //String stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                        String stttruoc = "";
                        if(!biendaghichuaghi) {
                            stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                        }
                        else{
                            stttruoc = khachhangDAO.getSTTChuaGhiLonNhatNhoHonHienTai1(maduong_nhan, STT_HienTai);
                        }
                        Log.e("stttruoc",stttruoc);
                        int pre = Integer.parseInt(stttruoc);// Integer.parseInt(STT_HienTai) + 1;

                       // int pre = Integer.parseInt(STT_HienTai) - 1;
                        STT_HienTai = String.valueOf(pre);

                        Log.e("BienSTTHIenTai", STT_HienTai);
                        Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                        String strtruocdo = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,stttruoc);
                        Log.e("strtruocdo", strtruocdo);
                        int truocdonua = Integer.parseInt(strtruocdo);
                        //if (pre - 1 <= 0) {
                        if (truocdonua <= 0) {
                            lay_lui.setEnabled(false);
                            Lui.setEnabled(false);
                            lay_lui.setBackgroundResource(R.color.space_background_color);
                        } else {
                            lay_lui.setEnabled(true);
                            Lui.setEnabled(true);
                            lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                        }
                        if (pre > 0 && pre <= sttmax) {
                            Log.e("BienSTTHIenTai", "chay vao 3");
                            setDataForView(STT_HienTai, maduong_nhan, "");
                         //   pre = Integer.parseInt(STT_HienTai) - 1;
                         //   int next = Integer.parseInt(STT_HienTai) + 1;
                            pre = truocdonua;//Integer.parseInt(STT_HienTai) + 1;
                            int next =  Integer.valueOf(khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan,STT_HienTai));
                            Log.e("ve truoc va ve sau",pre +" "+ next);


                            if (next > sttmax|| next == 0 ) {
                                lay_toi.setEnabled(false);
                                Toi.setEnabled(false);
                                lay_toi.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_toi.setEnabled(true);
                                Toi.setEnabled(true);
                                lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }
                            if (pre <= 0) {
                                lay_lui.setEnabled(false);
                                Lui.setEnabled(false);
                                lay_lui.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_lui.setEnabled(true);
                                Lui.setEnabled(true);
                                lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }

                        } else {
                            Log.e("BienSTTHIenTai", "chay vao 4");

                        }
                    }
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Bạn đang ghi nước.Bạn có muốn hủy ghi nước không?");

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String stttruoc = "";
                            if(!biendaghichuaghi) {
                                stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                            }
                            else{
                                stttruoc = khachhangDAO.getSTTChuaGhiLonNhatNhoHonHienTai1(maduong_nhan, STT_HienTai);
                            }Log.e("stttruoc",stttruoc);
                            int pre = Integer.parseInt(stttruoc);// Integer.parseInt(STT_HienTai) + 1;
                            //int pre = Integer.parseInt(STT_HienTai) - 1;
                            STT_HienTai = String.valueOf(pre);

                            Log.e("BienSTTHIenTai", STT_HienTai);
                            Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                            String strtruocdo = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,stttruoc);
                            int truocdonua = Integer.parseInt(strtruocdo);
                            Log.e("strtruocdo", strtruocdo);
                            //if (pre - 1 <= 0) {
                            if (truocdonua <= 0) {
                                lay_lui.setEnabled(false);
                                Lui.setEnabled(false);
                                lay_lui.setBackgroundResource(R.color.space_background_color);
                            } else {
                                lay_lui.setEnabled(true);
                                Lui.setEnabled(true);
                                lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                            }
                            if (pre > 0 && pre <= sttmax) {
                                Log.e("BienSTTHIenTai", "chay vao 3");
                                setDataForView(STT_HienTai, maduong_nhan,"");
                               // pre = Integer.parseInt(STT_HienTai) - 1;
                               // int next = Integer.parseInt(STT_HienTai) + 1;
                                pre = truocdonua;//Integer.parseInt(STT_HienTai) + 1;
                                int next =  Integer.valueOf(khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan,STT_HienTai));
                                Log.e("ve truoc va ve sau",pre +" "+ next);
                                if (next > sttmax || next == 0) {
                                    lay_toi.setEnabled(false);
                                    Toi.setEnabled(false);
                                    lay_toi.setBackgroundResource(R.color.space_background_color);
                                } else {
                                    lay_toi.setEnabled(true);
                                    Toi.setEnabled(true);
                                    lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                                }
                                if (pre <= 0) {
                                    lay_lui.setEnabled(false);
                                    Lui.setEnabled(false);
                                    lay_lui.setBackgroundResource(R.color.space_background_color);
                                } else {
                                    lay_lui.setEnabled(true);
                                    Lui.setEnabled(true);
                                    lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                                }

                            } else {
                                Log.e("BienSTTHIenTai", "chay vao 4");

                            }

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
                    // hiển thị dialog
                }


            }
        });
        Lui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_lui.performClick();
            }
        });
        DoiSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makh = MaKH.getText().toString().trim();
                String dt = DienThoai.getText().toString().trim();

                if(khachhangDAO.updateDienThoai(makh,dt)){
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienghi = Bien.bienghi +1;
                    spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    khachhangDAO.updateTrangThaiCapNhat(makh,"0");
                    Toast.makeText(con, R.string.main_capnhatsdt_thanhcong,Toast.LENGTH_SHORT).show();
                }
                else{
                    //dialog chọn đường
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.main_capnhatsdt_thatbai);
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            }
        });

        ChuyenLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                final EditText edittextLoaiKH = new EditText(con);
                alert.setMessage("Nhập loại KH:");
                alert.setTitle("Chuyển loại khách hàng:");

                alert.setView(edittextLoaiKH);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value

                        //OR
                        String makh = MaKH.getText().toString().trim();
                        String loaimoi = edittextLoaiKH.getText().toString();
                        String loaicu = khachhangDAO.getLoaiKHCu(makh);

                        if(khachhangDAO.updateLoaiKH(makh,loaimoi)){
                            if(!loaimoi.equals(loaicu)) {
                                LoaiKH.setText(loaimoi + "(Loại KH cũ: " + loaicu + " )");
                                LoaiKH.setTextColor(R.color.badge_background_color);
                            }
                            else{
                                LoaiKH.setText(loaimoi);
                                LoaiKH.setTextColor(R.color.space_default_color);
                            }
                            Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                            Bien.bienghi = Bien.bienghi +1;
                            spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                            Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                            khachhangDAO.updateTrangThaiCapNhat(makh,"0");
                            Toast.makeText(con, R.string.main_capnhatloaikh_thanhcong,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //dialog chọn đường
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage(R.string.main_capnhatloaikh_thatbai);
                            // thiết lập nội dung cho dialog

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    }
                });

                alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });

        CapNhatGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makh = MaKH.getText().toString().trim();
                String ghichu = GhiChu.getText().toString().trim();

                if(khachhangDAO.updateGhiChu(makh,ghichu)){
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienghi = Bien.bienghi +1;
                    spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    khachhangDAO.updateTrangThaiCapNhat(makh,"0");
                    Toast.makeText(con, R.string.main_capnhatghichu_thanhcong,Toast.LENGTH_SHORT).show();
                }
                else{
                    //dialog chọn đường
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.main_capnhatghichu_thatbai);
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            }
        });
        DienThoai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!DienThoai.getText().toString().trim().equals("")) {
                        flagDangGhi = true;

                    }
                    else{
                        if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                            flagDangGhi = false;
                        }
                        else{
                            flagDangGhi = true;
                        }


                    }
                }
            }
        });
        DienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                    flagDangGhi = false;
                }
                else{
                    flagDangGhi = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TinhTrangTLK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                        flagDangGhi = false;
                    }
                    else{
                        flagDangGhi = true;
                    }


                }
            }
        });
        TinhTrangTLK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                    flagDangGhi = false;
                }
                else{
                    flagDangGhi = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GhiChu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){


                    if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                        flagDangGhi = false;
                    }
                    else{
                        flagDangGhi = true;
                    }

                }
            }
        });
        GhiChu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                    flagDangGhi = false;
                }
                else{
                    flagDangGhi = true;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ChiSoMoi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                        flagDangGhi = true;
                    if (ChiSoMoi.getText().toString().trim().equals("")) {

                        showDiaLogThongBao("Bạn chưa nhập chỉ số nước đồng hồ .");

                        m3moi.setText("");
                    } else {
                        if(TextUtils.isDigitsOnly(ChiSoMoi.getText().toString().trim())) {
                            int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
                            int chisocu = Integer.parseInt(ChiSo1.getText().toString().trim());
                            //Kiểm tra âm
                            if (chisomoi < 0) {
                                //Show dialog thông báo âm
                                showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                            } else {
                                if (chisomoi < chisocu) {
                                    //Show dialog thông báo nhỏ hơn chỉ số cũ
                                    //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog

                                    alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            m3moi.setEnabled(true);
                                            m3moi.setText("");

                                            kiemtradieukien= true;

                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            resetViewGhiNuoc();
                                            m3moi.setEnabled(false);
                                            m3moi.setText("");
                                            dialog.dismiss();
                                            kiemtradieukien= true;


                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                    // hiển thị dialog
                                } else {
                                    if(spinTT.getSelectedItemPosition() ==0) {
                                        m3moi.setEnabled(false);
                                    }
                                    else{
                                        m3moi.setEnabled(true);
                                    }
                                    m3moi.setText(String.valueOf(chisomoi - chisocu));
                                //    BatThuong.setVisibility(View.GONE);
                                    //
                                    //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false

                                    int m3cu1 = Integer.parseInt(m31.getText().toString());
                                    int m3cu2 = Integer.parseInt(m32.getText().toString());
                                    int m3cu3 = Integer.parseInt(m33.getText().toString());
                                    int m3NEW = Integer.parseInt(m3moi.getText().toString());
                                    int binhquan3thang  = BinhQuanChiSoNuoc3Thang(m3cu1,m3cu2,m3cu3);
                                    if(kiemtraBatThuongLonHon(m3NEW,binhquan3thang)){
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                        // khởi tạo dialog

                                        alertDialogBuilder.setMessage("Số m3 quá lớn so với bình thường. Bạn có xác định đây là bất thường không?");

                                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                batthuong1 ="BT";

                                                kiemtradieukien=true;
                                            }
                                        });
                                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                batthuong1 ="BT";

                                                kiemtradieukien=true;


                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        // tạo dialog
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();
                                        // hiển thị dialog
                                    }else {
                                       // ghinuoc("");
                                        batthuong1 ="";

                                        kiemtradieukien= true;
                                    }



                                }
                            }
                        }
                        else{
                            showDiaLogThongBao("Chỉ số nước là số nguyên, không được chứa ký tự đặc biệt");
                        }
                    }
                    }
                    else{
                        flagDangGhi = false;
                        kiemtradieukien= true;
                    }
                }
            }
        });

        ChiSoMoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Log.e("KiemTraDaGhi(MaKH.getText().toString().trim()", String.valueOf(KiemTraDaGhi(MaKH.getText().toString().trim())));
                if(!KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                    Log.e("Kiem tra ontext chi so moi -  chua ghi","true");
                    flagDangGhi = true;
                if (!ChiSoMoi.getText().toString().trim().equals("") ) {
                    if(TextUtils.isDigitsOnly(ChiSoMoi.getText().toString().trim())) {
                        if (Integer.parseInt(ChiSoMoi.getText().toString().trim()) >= Integer.parseInt(ChiSo1.getText().toString().trim())) {
                            if(spinTT.getSelectedItemPosition() ==0) {
                                m3moi.setEnabled(false);
                            }
                            else{
                                m3moi.setEnabled(true);
                            }
                            m3moi.setText(String.valueOf(Integer.parseInt(ChiSoMoi.getText().toString().trim()) - Integer.parseInt(ChiSo1.getText().toString().trim())));

                            //    BatThuong.setVisibility(View.GONE);
                            //
                            //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false

                            int m3cu1 = Integer.parseInt(m31.getText().toString());
                            int m3cu2 = Integer.parseInt(m32.getText().toString());
                            int m3cu3 = Integer.parseInt(m33.getText().toString());
                            int m3NEW = Integer.parseInt(m3moi.getText().toString());
                            int binhquan3thang  = BinhQuanChiSoNuoc3Thang(m3cu1,m3cu2,m3cu3);
                            if(kiemtraBatThuongLonHon(m3NEW,binhquan3thang)){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                // khởi tạo dialog

                                alertDialogBuilder.setMessage("Số m3 quá lớn so với bình thường. Bạn có xác định đây là bất thường không?");

                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        batthuong1 ="BT";

                                        kiemtradieukien = true;

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        batthuong1 ="";

                                        kiemtradieukien = true;


                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }else {
                                // ghinuoc("");
                                batthuong1 ="";

                                kiemtradieukien = true;
                            }


                        } else {
                            m3moi.setEnabled(true);
                            m3moi.setText("");
                        }
                    }
                    else{
                        showDiaLogThongBao("Chỉ số nước là số nguyên, không được chứa ký tự đặc biệt");
                    }
                }
                else{
                    m3moi.setEnabled(true);
                    m3moi.setText("");
                }



                }
                else{
                    flagDangGhi = false;

                    kiemtradieukien = true;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        ChiSoMoiCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                        flagDangGhi = true;
                    if (ChiSoMoiCon.getText().toString().trim().equals("")) {
                        showDiaLogThongBao("Bạn chưa nhập chỉ số nước đồng hồ con.");
                    } else {
                        if (TextUtils.isDigitsOnly(ChiSoMoiCon.getText().toString().trim())) {
                            int chisomoicon = Integer.parseInt(ChiSoMoiCon.getText().toString().trim());
                            int chisocucon = Integer.parseInt(ChiSoCon1.getText().toString().trim());
                            //Kiểm tra âm
                            if (chisomoicon < 0) {
                                //Show dialog thông báo âm
                                showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                            } else {
                                if (chisomoicon < chisocucon) {
                                    //Show dialog thông báo nhỏ hơn chỉ số cũ
                                    //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog

                                    alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            m3conmoi.setEnabled(true);
                                            m3conmoi.setText("");
                                            dialog.dismiss();

                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            resetViewGhiNuoc();
                                            m3conmoi.setEnabled(false);
                                            m3conmoi.setText("");
                                            dialog.dismiss();


                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    // tạo dialog
                                    alertDialog.show();
                                    // hiển thị dialog
                                } else {
                                    m3conmoi.setEnabled(false);
                                    m3conmoi.setText(String.valueOf(chisomoicon - chisocucon));
                                    //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false

                                    int m3cu1con = Integer.parseInt(m3con1.getText().toString());
                                    int m3cu2con = Integer.parseInt(m3con2.getText().toString());
                                    int m3cu3con = Integer.parseInt(m3con3.getText().toString());
                                    int m3NEWccon = Integer.parseInt(m3conmoi.getText().toString());
                                    int binhquan3thangcon  = BinhQuanChiSoNuoc3Thang(m3cu1con,m3cu2con,m3cu3con);
                                    if(kiemtraBatThuongLonHon(m3NEWccon,binhquan3thangcon)){
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                        // khởi tạo dialog

                                        alertDialogBuilder.setMessage("Số m3 quá lớn so với bình thường. Bạn có xác định đây là bất thường không?");

                                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                batthuong2 ="BT";
                                                kiemtradieukiencon = true;

                                            }
                                        });
                                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                batthuong2 ="";
                                                kiemtradieukiencon = true;


                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        // tạo dialog
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();
                                        // hiển thị dialog
                                    }else {
                                        // ghinuoc("");
                                        batthuong2 ="";
                                        kiemtradieukiencon = true;
                                    }


                                }
                            }
                        }
                        else{
                            showDiaLogThongBao("Chỉ số nước là số nguyên, không được chứa ký tự đặc biệt");
                        }
                    }


                    }
                    else{
                        flagDangGhi = false;
                        kiemtradieukiencon = true;
                    }

                }
            }
        });
        ChiSoMoiCon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!KiemTraDaGhi(MaKH.getText().toString().trim()) ) {
                    flagDangGhi = true;
                if (!ChiSoMoiCon.getText().toString().trim().equals("")  ) {
                    if (TextUtils.isDigitsOnly(ChiSoMoiCon.getText().toString().trim())) {
                        if (Integer.parseInt(ChiSoMoiCon.getText().toString().trim()) >= Integer.parseInt(ChiSoCon1.getText().toString().trim())) {
                            m3conmoi.setEnabled(false);
                            m3conmoi.setText(String.valueOf(Integer.parseInt(ChiSoMoiCon.getText().toString().trim()) - Integer.parseInt(ChiSoCon1.getText().toString().trim())));
                            //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false

                            int m3cu1con = Integer.parseInt(m3con1.getText().toString());
                            int m3cu2con = Integer.parseInt(m3con2.getText().toString());
                            int m3cu3con = Integer.parseInt(m3con3.getText().toString());
                            int m3NEWccon = Integer.parseInt(m3conmoi.getText().toString());
                            int binhquan3thangcon  = BinhQuanChiSoNuoc3Thang(m3cu1con,m3cu2con,m3cu3con);
                            if(kiemtraBatThuongLonHon(m3NEWccon,binhquan3thangcon)){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                // khởi tạo dialog

                                alertDialogBuilder.setMessage("Số m3 quá lớn so với bình thường. Bạn có xác định đây là bất thường không?");

                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        batthuong2 ="BT";
                                        kiemtradieukiencon = true;

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // hiển thị dialog
                                        dialog.dismiss();
                                        batthuong2 ="";
                                        kiemtradieukiencon = true;



                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }else {
                                // ghinuoc("");
                                batthuong2 ="";
                                kiemtradieukiencon = true;
                            }


                        } else {
                            m3conmoi.setEnabled(true);
                            m3conmoi.setText("");
                        }
                    }
                    else{
                        showDiaLogThongBao("Chỉ số nước là số nguyên, không được chứa ký tự đặc biệt");
                    }
                }



                }
                else{
                    flagDangGhi = false;
                    kiemtradieukiencon = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("select duong-mainactivity", String.valueOf(Bien.selected_item));
    }

    private void setDataForView(String tt, String maduong,String Makhach) {
        //Lấy khách hàng có stt hiện tại...mặc đình là 1

        Log.e("thu tu , ma duong, ma khach",tt +","+ maduong+ ","+ Makhach);
        tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
        soKHconlai = String.valueOf(khachhangDAO.countKhachHangChuaGhiTheoDuong(maduong)) ;
        String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        soKHHomNay  = String.valueOf(khachhangDAO.countKhachHangGhiTrongNgay(maduong,thoigian1));
        ConLai.setText("Hôm nay: "+ soKHHomNay+"   " +getString(R.string.string_con)+" "+soKHconlai+"/"+ tongsoKHTheoDuong);
        ConLai.setSelected(true);
        if(Makhach==null || Makhach.equals("")) {
            khachhang = khachhangDAO.getKHTheoSTT_Duong(tt.trim(), maduong.trim());
        }
        else{
            khachhang = khachhangDAO.getKHTheoSTT_Duong_maKH(tt.trim(), maduong.trim(),Makhach.trim());
        }
        if(khachhang == null) {
            Log.e("khach hang null","OK");
        }



        STT.setText(khachhang.getSTT().trim());
        MaKH.setText(khachhang.getMaKhachHang().trim());
        HoTen.setText(khachhang.getTenKhachHang().trim());
        DanhBo.setText(khachhang.getDanhBo().trim());
        DiaChi.setText(khachhang.getDiaChi().trim());
        MaTLK.setText(khachhang.getMasotlk().trim());
        HieuTLK.setText(khachhang.getHieutlk().trim());
        CoTLK.setText(khachhang.getCotlk().trim());
        Log.e("trang thai TLK",khachhang.getTrangThaiTLK());
        int indextt = Integer.parseInt(tinhtrangtlkdao.getindexTinhTrang1(khachhang.getTrangThaiTLK()));
        Log.e("indextt TLK", String.valueOf(indextt));
        spinTT.setSelection(indextt);

        if(khachhang.getChiSo1().trim().equals("")){
            ChiSo1.setText("0");
        }else {
            ChiSo1.setText(khachhang.getChiSo1().trim());
        }
        if(khachhang.getChiSo2().trim().equals("")){
            ChiSo2.setText("0");
        }else {
            ChiSo2.setText(khachhang.getChiSo2().trim());
        }
        if(khachhang.getChiSo3().trim().equals("")){
            ChiSo3.setText("0");
        }else {
            ChiSo3.setText(khachhang.getChiSo3().trim());
        }
        if(khachhang.getSLTieuThu1().trim().equals("")){
            m31.setText("0");
        }else {
            m31.setText(khachhang.getSLTieuThu1().trim());
        }
        if(khachhang.getSLTieuThu2().trim().equals("")){
            m32.setText("0");
        }else {
            m32.setText(khachhang.getSLTieuThu2().trim());
        }
        if(khachhang.getSLTieuThu3().trim().equals("")){
            m33.setText("0");
        }else {
            m33.setText(khachhang.getSLTieuThu3().trim());
        }
        if(khachhang.getLoaikh().toString().trim().equals(khachhangDAO.getLoaiKHMoi(khachhang.getMaKhachHang().trim()))) {
            LoaiKH.setText(khachhang.getLoaikh().trim());
            LoaiKH.setTextColor(R.color.default_active_item_color);
        }
        else{
            LoaiKH.setTextColor(R.color.badge_background_color);
            LoaiKH.setText(khachhangDAO.getLoaiKHMoi(khachhang.getMaKhachHang().trim()) + "(Loại KH cũ: "+  khachhang.getLoaikh().trim()+" )");
        }
        DinhMuc.setText(khachhang.getDinhmuc().trim());
        ChiSoMoi.setText(khachhang.getChiSo().trim());
        m3moi.setText(khachhang.getSLTieuThu().trim());
        m3moi.setEnabled(false);
        ChiSoMoiCon.setText(khachhang.getChiSocon().trim());
        m3conmoi.setText(khachhang.getSLTieuThucon().trim());
        TinhTrangTLK.setText(khachhang.getTrangThaiTLK().trim());
        GhiChu.setText(khachhang.getGhiChu().trim());

        int m3cu1 = Integer.parseInt(m31.getText().toString());
        int m3cu2 = Integer.parseInt(m32.getText().toString());
        int m3cu3 = Integer.parseInt(m33.getText().toString());

        int binhquan3thang  = BinhQuanChiSoNuoc3Thang(m3cu1,m3cu2,m3cu3);
        BinhQuanBaThang.setText("Bình quân 3 tháng gần nhất: "+ binhquan3thang +" m3");
        flagDangGhi = false;
        if(!khachhang.getChiSo().equals("")){
            if(khachhangDAO.checkTrangThaiBatThuongKH(khachhang.getMaKhachHang()).equals("")) {


                LabelDuong.setBackgroundResource(android.R.color.holo_red_dark);
                DuongDangGhi.setBackgroundResource(android.R.color.holo_red_dark);
                ConLai.setBackgroundResource(android.R.color.holo_red_dark);

                if( !khachhang.getGhiChu().trim().equals("")) {

                    LabelDuong.setBackgroundResource(R.color.yellow);
                    DuongDangGhi.setBackgroundResource(R.color.yellow);
                    ConLai.setBackgroundResource(R.color.yellow);
                }
            }
            else{
                if(spinTT.getSelectedItemPosition()!=0 || !khachhang.getGhiChu().trim().equals("")) {

                    LabelDuong.setBackgroundResource(R.color.yellow);
                    DuongDangGhi.setBackgroundResource(R.color.yellow);
                    ConLai.setBackgroundResource(R.color.yellow);
                }
                else{
                    LabelDuong.setBackgroundResource(android.R.color.holo_green_light);
                    DuongDangGhi.setBackgroundResource(android.R.color.holo_green_light);
                    ConLai.setBackgroundResource(android.R.color.holo_green_light);

                }

            }
        }
        else{
            LabelDuong.setBackgroundResource(R.color.colorPrimaryDark);
            DuongDangGhi.setBackgroundResource(R.color.colorPrimaryDark);
            ConLai.setBackgroundResource(R.color.colorPrimaryDark);
;
        }

        if(khachhang.getChiSo1con().equals("0") && khachhang.getChiSo2con().equals("0") && khachhang.getChiSo3con().equals("0")
            &&  khachhang.getSLTieuThu1con().equals("0")  &&  khachhang.getSLTieuThu2con().equals("0")  &&  khachhang.getSLTieuThu3con().equals("0")){

            chisocu_con_lb.setVisibility(View.GONE);
            chisocu_con.setVisibility(View.GONE);
            chisomoi_con_lb.setVisibility(View.GONE);
            chisomoi_con.setVisibility(View.GONE);
        }
        else {
            chisocu_con_lb.setVisibility(View.VISIBLE);
            chisocu_con.setVisibility(View.VISIBLE);
            chisomoi_con_lb.setVisibility(View.VISIBLE);
            chisomoi_con.setVisibility(View.VISIBLE);

            ChiSoCon1.setText(khachhang.getChiSo1con().trim());
            ChiSoCon2.setText(khachhang.getChiSo2con().trim());
            ChiSoCon3.setText(khachhang.getChiSo3con().trim());
            m3con1.setText(khachhang.getSLTieuThu1con().trim());
            m3con2.setText(khachhang.getSLTieuThu2con().trim());
            m3con3.setText(khachhang.getSLTieuThu3con().trim());
        }
        DienThoai.setText(khachhang.getDienThoai().trim());
        //set background cho tới và lùi
        if(tt.equals("1")){
            lay_lui.setBackgroundResource(R.color.space_background_color);
            lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
            lay_lui.setEnabled(false);
            Lui.setEnabled(false);
            lay_toi.setEnabled(true);
            Toi.setEnabled(true);
        }
        else if(tt.equals(String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong)))){
            lay_toi.setBackgroundResource(R.color.space_background_color);
            lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
            lay_lui.setEnabled(true);
            Lui.setEnabled(true);
            lay_toi.setEnabled(false);
            Toi.setEnabled(false);
        }
        else{
            lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
            lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
            lay_lui.setEnabled(true);
            Lui.setEnabled(true);
            lay_toi.setEnabled(true);
            Toi.setEnabled(true);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        this.menumain  = menu;

//        if(khachhangDAO.checkKHDaGhi(khachhang.getMaKhachHang()) > 0){
//
//            menumain.getItem(2).setVisible(true);
//            this.invalidateOptionsMenu();
//        }
//        else{
//            menumain.getItem(2).setVisible(false);
//            this.invalidateOptionsMenu();
//        }
        return true;
    }

    private void selectItem(MenuItem item) {
        Intent intent;

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_save:
                ViewDialog_ChonNguonBackUp alert = new ViewDialog_ChonNguonBackUp();
                alert.showDialog(MainActivity.this, "Chọn nguồn để lưu dữ liệu: ");
            //    MainActivity.this.finish();

                break;
            case R.id.action_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.action_print:
                Log.e("makh_nhan",MaKH.getText().toString());
                KhachHangDTO kh = khachhangDAO.getKHTheoMaKH(MaKH.getText().toString());
                if(!kh.getChiSo().equals("")){
                    intent = new Intent(MainActivity.this, TinhTienInHDActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Bien.MAKH, khachhang.getMaKhachHang());


                    intent.putExtra(Bien.GOITIN_GHINUOC, bundle);
                    startActivity(intent);
                   // MainActivity.this.finish();
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Không thể in vì khách hàng này chưa được ghi nước");
                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    // hiển thị dialog
                }

                break;
            case android.R.id.home:

                MainActivity.this.finish();
                break;
            case R.id.action_kieughi:

                if(bienkieughi == 1){ // lui -> toi
                    bienkieughi = 0;

                    item.setIcon(android.R.drawable.ic_media_rew); //toi
                }
                else{ //toi -> lui
                    bienkieughi = 1;
                    item.setIcon(android.R.drawable.ic_media_ff); //toi
                }

                break;

            case R.id.action_daghi:

                if(biendaghichuaghi == false){
                    biendaghichuaghi = true;

                    item.setIcon(android.R.drawable.presence_online); //toi
                }
                else{ //toi -> lui
                    biendaghichuaghi = false;
                    item.setIcon(android.R.drawable.presence_invisible); //toi
                }

                break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        selectItem(item);
        return super.onOptionsItemSelected(item);
    }


    private void taoView() {
            STT = (TextView) findViewById(R.id.tv_sttKH);
            MaKH= (TextView) findViewById(R.id.tv_maKH);
            MaKH.setSelected(true);
            DanhBo = (TextView) findViewById(R.id.tv_DanhBo);
            DanhBo.setSelected(true);
            HoTen= (TextView) findViewById(R.id.tv_hotenKH);
            HoTen.setSelected(true);
            DiaChi= (TextView) findViewById(R.id.tv_diachiKH);
            MaTLK= (TextView) findViewById(R.id.tv_maTLK);
            HieuTLK= (TextView) findViewById(R.id.tv_hieuTLK);
            CoTLK= (TextView) findViewById(R.id.tv_coTLK);
            ChiSo1= (TextView) findViewById(R.id.tv_chisocu1);
            ChiSo1.setSelected(true);
            ChiSo2= (TextView) findViewById(R.id.tv_chisocu2);
            ChiSo2.setSelected(true);
            ChiSo3= (TextView) findViewById(R.id.tv_chisocu3);
            ChiSo3.setSelected(true);
            m31= (TextView) findViewById(R.id.tv_m3cu1);
            m31.setSelected(true);
            m32= (TextView) findViewById(R.id.tv_m3cu2);
            m32.setSelected(true);
            m33= (TextView) findViewById(R.id.tv_m3cu3);
            m33.setSelected(true);
            ChiSoCon1= (TextView) findViewById(R.id.tv_chisocu1con);
            ChiSoCon2= (TextView) findViewById(R.id.tv_chisocu2con);
            ChiSoCon3= (TextView) findViewById(R.id.tv_chisocu3con);
            m3con1= (TextView) findViewById(R.id.tv_m3cu1con);
            m3con2= (TextView) findViewById(R.id.tv_m3cu2con);
            m3con3= (TextView) findViewById(R.id.tv_m3cu3con);

            LabelDuong = (TextView) findViewById(R.id.tv_label_duong);
            DuongDangGhi= (TextView) findViewById(R.id.tv_duongdangghi);
            ConLai= (TextView) findViewById(R.id.tv_conlai);
            ConLai.setSelected(true);
            DuongDangGhi.setSelected(true);
            DienThoai = (EditText) findViewById(R.id.edit_DienThoaiKH);
            ChiSoMoi = (EditText) findViewById(R.id.edit_chisomoi);
            m3moi= (EditText) findViewById(R.id.edit_m3moi);
            ChiSoMoiCon = (EditText) findViewById(R.id.edit_chisomoicon);
            m3conmoi= (EditText) findViewById(R.id.edit_m3moicon);
            TinhTrangTLK = (EditText) findViewById(R.id.edit_tinhtrangTLK);
            GhiChu = (EditText) findViewById(R.id.edit_ghichu);

            LoaiKH= (TextView) findViewById(R.id.tv_loaiKH);
            DinhMuc= (TextView) findViewById(R.id.tv_DinhMuc);
            BinhQuanBaThang = (TextView) findViewById(R.id.tv_binhquan3thang);
            DoiSDT  = (ImageButton) findViewById(R.id.imgbtn_doi);
            Ghi = (ImageButton) findViewById(R.id.btn_ghinuoc);
            Toi = (ImageButton) findViewById(R.id.btn_toi);
            Lui = (ImageButton) findViewById(R.id.btn_lui);
            CapNhatGhiChu = (ImageButton) findViewById(R.id.btn_updateghichu);
            ChuyenLoai =(ImageButton) findViewById(R.id.btn_chuyenloai);

            lay_toi =(LinearLayout)findViewById(R.id.layout_toi);
            lay_lui =(LinearLayout)findViewById(R.id.layout_lui);
            lay_ghi =(LinearLayout)findViewById(R.id.layout_ghi);
            lay_ki1 = (LinearLayout)findViewById(R.id.lay_ki1);
            lay_ki2 = (LinearLayout)findViewById(R.id.lay_ki2);
            lay_ki3 = (LinearLayout)findViewById(R.id.lay_ki3);

            chisocu_con_lb =(TableRow) findViewById(R.id.tableRow_chisocucon_lb);
            chisocu_con =(TableRow) findViewById(R.id.tableRow_chisocucon);

            chisomoi_con_lb =(TableRow) findViewById(R.id.tableRow_chisomoicon_lb);
            chisomoi_con =(TableRow) findViewById(R.id.tableRow_chisomoicon);
            spinTT  = (Spinner) findViewById(R.id.spin_tinhtrangtlk);

    }




//hàm ghi nước
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
        case 1: {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the

                // contacts-related task you need to do.

                gps = new GPSTracker(con, MainActivity.this);

                // Check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    vido = String.valueOf(latitude);
                    kinhdo = String.valueOf(longitude);

                    Log.e("Toa do", vido +"-"+kinhdo );
                    // \n is for new line
                   Toast.makeText(getApplicationContext(), "REQUEST: Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.

                Toast.makeText(con, "REQUEST: You need to grant permission....", Toast.LENGTH_SHORT).show();

            }
            return;
        }
    }
}
    private void ghinuoc(String BT){


        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(con,"GHI NUOC:You need have granted permission.......",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            gps = new GPSTracker(con, MainActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {
                String maKH = MaKH.getText().toString().trim();
                String Chiso = ChiSoMoi.getText().toString().trim();
                String m3 = m3moi.getText().toString().trim();
                String Chisocon = ChiSoMoiCon.getText().toString().trim();
                String m3con = m3conmoi.getText().toString().trim();
                String Dienthoai = DienThoai.getText().toString().trim();
                String ghichu = GhiChu.getText().toString().trim();
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                 vido = String.valueOf(latitude);
                 kinhdo = String.valueOf(longitude);
                // \n is for new line
              //  Toast.makeText(getApplicationContext(), "GHI NUOC:Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                Log.e("Toa do", vido +"-"+kinhdo );
                String nhanvien = spdata.getDataNhanVienTrongSP();
               /*
                if(m3.equals("")){
                    String SL =  String.valueOf(Integer.parseInt(ChiSoMoi.getText().toString()) - Integer.parseInt(ChiSo1.getText().toString())).trim();
                }


                String SLCon;
                if(!ChiSoMoiCon.getText().toString().equals("")) {
                    SLCon = String.valueOf(Integer.parseInt(ChiSoMoiCon.getText().toString()) - Integer.parseInt(ChiSoCon1.getText().toString())).trim();
                }
                else{
                    SLCon ="";
                }
                */
                String thoigian = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                Log.e("Thoi gian", thoigian );
               // String trangthaiTLK  = TinhTrangTLK.getText().toString().trim();
                String trangthaiTLK  = tenTT;
                if(spinTT.getSelectedItemPosition()!=0){
                    BT ="BT";
                }
                //Kiem tra khach hang da ghi chua
                //if(KiemTraDaGhiChiSo(maKH)){

                //}
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                String formattedDate = format.format(date);
                SPData sp = new SPData(MainActivity.this);
                String kihd = sp.getDataKyHoaDonTrongSP();// "08/2017";
                Log.e("formattedDate",formattedDate);
                Log.e("kihd",kihd);
//                if(formattedDate.equals(kihd)) {

                    if(khachhangDAO.updateKhachHang(maKH,Chiso,Chisocon,Dienthoai,ghichu,vido,kinhdo,nhanvien,m3,m3con,thoigian,trangthaiTLK,BT))
                    {
                        Toast.makeText(con,"Ghi nước thành công",Toast.LENGTH_SHORT).show();
                        //Cập nhật biến ghi
                        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                        Bien.bienghi = Bien.bienghi +1;
                        spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                        flagDangGhi = false;
                        LichSuDTO ls = new LichSuDTO();
                        ls.setNoiDungLS("Ghi nước đường "+ tenduong +", khách hàng có danh bộ " + DanhBo.getText().toString().trim());
                        ls.setMaLenh("GN");
                        String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

                        ls.setThoiGianLS(thoigian1);
                        lichsudao.addTable_History(ls);
                        khachhangDAO.updateTrangThaiCapNhat(maKH,"0");

                        /*
                        //Tinh tien nuoc

                        if(!khachhangDAO.getKHTheoMaKH(maKH).getChiSo().equals("")) {
                            khachhangDAO.tinhTienNuoc(maKH);
                        }
                        else{
                            khachhangDAO.updateGiaNuoc(maKH,"","","");
                        }
                         */
                        //Nếu còn khách hàng chưa ghi -> tiếp tục ghi
                        if(khachhangDAO.countKhachHangChuaGhiTheoDuong(maduong_nhan)>0) {

                            //Cập nhật Vị trí hiện tại , load lại (xử lý next.performclick)

                            //xu ly ghi truoc hay ghi lui tai day
                            String sothutu = "";
                            if (bienkieughi == 1){ //lui{
                                sothutu = khachhangDAO.getSTTChuaGhiNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                            }
                            else{//truoc
                                sothutu = khachhangDAO.getSTTChuaGhiLonNhatNhoHonHienTai(maduong_nhan, STT_HienTai);
                            }
                            Log.e("Ghi nuoc, stt",sothutu);
                            if(!sothutu.equals("0")  && !maduong_nhan.equals("99")) {

                                STT_HienTai = sothutu;
                                Log.e("STT Hien tai",STT_HienTai);
                                KhachHangDTO khghike = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaghi(STT_HienTai, maduong_nhan, MaKH.getText().toString().trim());
                                if(khghike == null) {
                                    setDataForView(sothutu, maduong_nhan, "");
                                }
                                else{
                                    setDataForView(sothutu, maduong_nhan, khghike.getMaKhachHang());
                                }
                                spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan, sothutu);
                            }
                            else if(sothutu.equals("0")  && maduong_nhan.equals("99"))
                            {
                                STT_HienTai = "0";
                                KhachHangDTO khghike = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaghi("0", maduong_nhan, MaKH.getText().toString().trim());
                                if(khghike == null) {
                                    setDataForView(sothutu, maduong_nhan, "");
                                }
                                else{
                                    setDataForView(sothutu, maduong_nhan, khghike.getMaKhachHang());
                                }
                                spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan, "0");
                            }
                            else{
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setMessage("Vẫn còn khách hàng bạn chưa ghi nước, bạn có muốn ghi nước khách hàng này không");
                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        String sothutukhConLai = khachhangDAO.getSTTChuaGhiNhoNhat(maduong_nhan);

                                        if(maduong_nhan.equals("99")){
                                            sothutukhConLai ="0";
                                        }
                                        if(!sothutukhConLai.equals("")) {
                                            STT_HienTai = sothutukhConLai;
                                            bienkieughi = 1;
                                            MenuItem itemkieughi = menumain.findItem(R.id.action_kieughi);
                                            itemkieughi.setIcon(android.R.drawable.ic_media_ff);

                                            setDataForView(sothutukhConLai, maduong_nhan,"");
                                            spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan, sothutukhConLai);
                                        }

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        MainActivity.this.finish();

                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }
                        }
                        //KH đã ghi nước xong => cập nhật đường va show dialog
                        else {
                            //cập nhật trạng thái đường đã ghi
                            if(duongDAO.updateDuongDaGhi(maduong_nhan)) {
                                //show dialog đã ghi xong..trở về listactivity
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                // khởi tạo dialog
                                if(duongDAO.countDuongChuaGhi()>0) {
                                    alertDialogBuilder.setMessage(R.string.main_ghinuoc_duongdaghixong);

                                    // thiết lập nội dung cho dialog
                                }
                                else{
                                    alertDialogBuilder.setMessage(R.string.main_ghinuoc_duongdaghixongtatca);


                                }
                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      //  new UpdateThongTinGhiNuoc().execute(urlstr);
                                        dialog.dismiss();
                                        MainActivity.this.finish();
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }
                        }

                        hideKeyboard(MainActivity.this);
                       //Tu dong luu 50ngươi / lan
                        if (spdata.getDataOnOffLuu() == 1) {
                            int flagupdate = spdata.getDataUPdateServer();
                            int chisoluutudong = spdata.getDataChiSoLuuCapNhat();
                            Log.e("flagupdate", String.valueOf(spdata.getDataUPdateServer()));
                            if (flagupdate < chisoluutudong && flagupdate >= 0) {
                                int capnhat = flagupdate + 1;
                                spdata.luuDataUpdateServer(capnhat);
                                Log.e("flagupdate < 30 sau khi +", String.valueOf(spdata.getDataUPdateServer()));
                            } else if (flagupdate == chisoluutudong) {
                                Log.e("tu dong luu", "OK");

                                //Tự động lưu dữ liệu vào server
                                String urlstr = getString(R.string.API_UpdateKhachHangDaGhi2);
                                try {
                                    if (isInternetOn()) {
                                        new UpdateThongTinGhiNuoc().execute(urlstr);
                                    } else {
                                        spdata.luuDataUpdateServer(0);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                        // khởi tạo dialog
                                        alertDialogBuilder.setMessage("Kết nối máy chủ không thành công. Cập nhật dữ liệu tự động thất bại");
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
                                    }
                                } catch (Exception a) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Chưa mở Wifi hoặc 3G/4G. Thử lại");
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
                                }
                            }
                        }

                    }
                    else{
                        Toast.makeText(con,"Ghi nước thất bại",Toast.LENGTH_SHORT).show();
                    }



            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }



    }
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ) {

            // if connected with internet

            Toast.makeText(this, connec.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Chưa có internet hoặc 3G/4G  ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    private ListRequestObject taoJSONData_KH_DaGhi_CapNhatServer2(String tendanhsach) {
        ListRequestObject jsondata = new ListRequestObject();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuong> listtiwaread = new ArrayList<ListKHTheoDuong>();
        String soluongKH = String.valueOf(khachhangDAO.countKhachHangCapNhatServer());
        listduong = duongDAO.getAllDuongChuaKhoaSo();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObject> listkh = new ArrayList<RequestObject>();
            listkh = khachhangDAO.getAllKHDaGhiTheoDuongChuaCapNhat1(maduong);
            ListKHTheoDuong tiwaread = new ListKHTheoDuong();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size() >0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json="";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            jsondata.setTenDS(tendanhsach);
            jsondata.setTongSLkh(soluongKH);
            Log.e("tong sokh", String.valueOf(soluongKH));
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
            Log.e("json data", json);
        }
        else{
            // spdata.luuDataFlagBKDaghiTrongSP(-1);
            // Bien.bienbkdg = -1;
            jsondata = null;

        }
        return jsondata;
    }
    public class UpdateThongTinGhiNuoc extends AsyncTask<String, String, String>
    {
        String status ="";
        String kyhd = spdata.getDataKyHoaDonTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaGhi_CapNhatServer(kyhdsau);
        String result ="";
        //ListJsonData jsondata = new ListJsonData();
        ListRequestObject jsondata = new ListRequestObject();

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn=null;

            //Get Danh Sach Duong khoa so

            String fileContent = "";
            try {
                String duongdankhoaso = getString(R.string.API_DuongKhoaSo)+"/"+spdata.getDataNhanVienTrongSP()+"/"+spdata.getDataKyHoaDonTrongSP();
                final URL url = new URL(duongdankhoaso);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");

                int result = conn.getResponseCode();
                if (result == 200) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        // long status = (i+1) *100/line.length();
                        //     String.valueOf(status)
                        publishProgress("Lấy thông tin đường khóa sổ...");
                        fileContent = line;

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("loi load du lieu", ex.toString());
            }
            try {
                JSONArray listduongks  = new JSONArray(fileContent);

                for(int i  =0;i<listduongks.length();i++)
                {
                    JSONObject objTiwaread = listduongks.getJSONObject(i);
                    String maduong = "";
                    String khoaso = "";
                    if (objTiwaread.has("maduong")) {
                        maduong = objTiwaread.getString("maduong").trim();
                    }

                    if (objTiwaread.has("khoaso")) {
                        khoaso = objTiwaread.getString("khoaso").trim();
                    }

                    if(duongDAO.updateDuongKhoaSo(maduong,khoaso))
                    {
                        Log.e("Cập nhật kso","OK");
                    }
                    else{
                        Log.e("Cập nhật kso","Fail");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<DuongDTO> duongchuakhoaso =  duongDAO.getAllDuongChuaKhoaSo();

            if(duongchuakhoaso.size() <=0)
            {
                result=  "DAKHOASOHET";
            }
            else {


                try {
                    final URL url = new URL(connUrl[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setChunkedStreamingMode(0);
                    conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                    conn.setRequestMethod("PUT");
                    publishProgress("Lấy thông tin khách hàng...");
                    //jsondata = taoJSONData_KH_DaGhi_CapNhatServer(kyhd);

                        jsondata = taoJSONData_KH_DaGhi_CapNhatServer2(kyhd);



                    //Cập nhật tất cả tạm là đã update
                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                        for (RequestObject kh : lista.getTiwareadList()) {
                            if (khachhangDAO.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "1")) {
                                //capnhattrangthai++;
                            }
                        }
                    }
                    // JSONListTiwaread requestjson  = new JSONListTiwaread();
                    //  requestjson.setJsontiwaread(jsondata);


                    JSONRequestObject requestjson = new JSONRequestObject();
                    requestjson.setJsontiwaread(jsondata);

                    Gson gson = new Gson();
                    json = gson.toJson(requestjson);
                    Log.e("json gui", json);
                    //  json = taoJSONData_KH_DaGhi_CapNhatServer(kyhdsau);
                    publishProgress("Đang cập nhật server...");
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(json.getBytes());
                    out.flush();
                    out.close();
                /*
                XuLyFile xl = new XuLyFile(con);
                String path = xl.getBoNhoTrong();
                thumucchuafile = path+"/"+THUMUCBACKUP;

                String filename = thumucchuafile + "/TEST" ;
                Log.e("filename",filename);
                taoThuMuc(filename);



                boolean kt = writeFile(filename, "TEST", json);
                 */

                    int result = conn.getResponseCode();
                    if (result == 200) {

                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        while ((line = reader.readLine()) != null) {
                            status = line;
                        }
                        reader.close();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("loi ne ", ex.toString());
                    result = "0";
                }
                try {
                    JSONObject objstt = new JSONObject(status);
                    if (objstt.has("CapNhatChiSoTLKResult")) {

                        result = String.valueOf(objstt.getString("CapNhatChiSoTLKResult"));


                    } else if (objstt.has("CapNhatChiSoDongHoNuocResult")) {

                        result = String.valueOf(objstt.getString("CapNhatChiSoDongHoNuocResult"));


                    } else if (objstt.has("CapNhatChiSoDongHoNuocNongThonResult")) {

                        result = String.valueOf(objstt.getString("CapNhatChiSoDongHoNuocNongThonResult"));

                        Log.e("CapNhatChiSoDongHoNuocNongThonResult", result);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("loi tra ve ne ", e.toString());
                    result = "0";
                }

            }
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];



        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result update", result);
            int capnhattrangthai = 0;
            String soluongkhachhangdacapnhat = "";
            List<String> DanhSachLoi = new ArrayList<>();
            JSONObject jsonobj = null;
//            try {
//                jsonobj = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            if(result.equals("DAKHOASOHET"))
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Tất cả các đường đã khóa sổ. Không thể cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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

            else if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật
                spdata.luuDataUpdateServer(0);
                if(jsondata.getListTiwaread().size() >0) {
                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                        for (RequestObject kh : lista.getTiwareadList()) {
                            if (khachhangDAO.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
                                //capnhattrangthai++;
                            }
                        }
                    }
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Kết nối máy chủ không thành công. Cập nhật dữ liệu tự động thất bại");
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
            } else {
                try {
                    try {
                        jsonobj = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (jsonobj.has("soluongcapnhat")) {


                        try {
                            soluongkhachhangdacapnhat = jsonobj.getString("soluongcapnhat");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    if (soluongkhachhangdacapnhat.equals(jsondata.getTongSLkh())) {
                        //Toast.makeText(Backup_Activity.this,"Cập nhật dữ liệu thành công",Toast.LENGTH_LONG).show();

//                for(ListKHTheoDuong lista : jsondata.getListTiwaread()) {
//                    for(RequestObject kh : lista.getTiwareadList())
//                    {
//                        if(khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(),"1")){
//                            capnhattrangthai++;
//                        }
//                    }
//                }
//                if(capnhattrangthai == Integer.parseInt(jsondata.getTongSLkh())){
//
//                        if(radioTatca.isChecked()){
//                            for(ListKHTheoDuong lista : jsondata.getListTiwaread()) {
//                                for(RequestObject kh : lista.getTiwareadList())
//                                {
//                                    if(khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(),"1")){
//                                        //capnhattrangthai++;
//                                    }
//                                }
//                            }
//                        }
                        spdata.luuDataUpdateServer(0);
                        Toast.makeText(MainActivity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Cật nhật dữ liệu thành công "+ String.valueOf(soluongkhachhangdacapnhat) +"/"+ String.valueOf(jsondata.getTongSLkh()));
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


                        // }


                    } else {
                        if (jsonobj.has("danhsachkhloi")) {


                            try {
                                int capnhatlaichuaupdate = 0;
                                final ArrayList<String> myListerror = new ArrayList<String>();
                                JSONArray tong = jsonobj.getJSONArray("danhsachkhloi");
                                for (int i = 0; i < tong.length(); i++) {
                                    JSONObject objKHLOI = tong.getJSONObject(i);

                                    if (objKHLOI.has("maKH")) {
                                        String maKH = objKHLOI.getString("maKH").trim();
                                        KhachHangDTO kherror = khachhangDAO.getKHTheoMaKH(maKH.trim());
                                        String maduong  = khachhangDAO.getMaDuongTheoMaKhachHang(maKH.trim());
                                        String chuoihienthi = "Đường:" + maduong+"- Danh bộ:" + kherror.getDanhBo() +" - Tên:" + kherror.getTenKhachHang();
                                        myListerror.add(chuoihienthi);
                                        DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                    }

                                }
                                if(DanhSachLoi.size() >0){
                                    for(String ma : DanhSachLoi){
                                        if (khachhangDAO.updateTrangThaiCapNhat(ma, "0")) {
                                            capnhatlaichuaupdate++;
                                        }
                                    }
                                }
//                                if(jsondata.getListTiwaread().size()>0) {
//                                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
//                                        for (RequestObject kh : lista.getTiwareadList()) {
//                                            if (khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
//                                                capnhatlaichuaupdate++;
//                                            }
//                                        }
//                                    }
//                                }
                                // intent.putExtra("mylist", myList);
                                if (capnhatlaichuaupdate == DanhSachLoi.size()) {
                                    spdata.luuDataUpdateServer(0);
                                    //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                    Toast.makeText(MainActivity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            // button "no" ẩn dialog đi
                                        }
                                    });
                                    alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();


                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                            LayoutInflater inflater = getLayoutInflater();
                                            View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                            alertDialog.setView(convertView);
                                            alertDialog.setTitle("Danh sách lỗi");
                                            ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,myListerror);
                                            lv.setAdapter(adapter);
                                            alertDialog.show();
                                            // button "no" ẩn dialog đi
                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                int capnhatlaidanhsachjson =0;
                                spdata.luuDataUpdateServer(0);
                                if(jsondata.getListTiwaread().size()>0) {
                                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                                        for (RequestObject kh : lista.getTiwareadList()) {
                                            if (khachhangDAO.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
                                                capnhatlaidanhsachjson++;
                                            }
                                        }
                                    }
                                }

                                if (capnhatlaidanhsachjson == Integer.parseInt(jsondata.getTongSLkh())) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            // button "no" ẩn dialog đi
                                        }
                                    });

                                }
                            }

                        }

                    }
                }
                catch(Exception e){
                    spdata.luuDataUpdateServer(0);
                }
            }

        }



    }






private void kiemTraDieuKienDeGhiNuoc(){




        if(chisomoi_con.getVisibility()==View.VISIBLE){


         Log.e("kiemtradieukien & kiemtradieukiencon", String.valueOf(kiemtradieukien) + kiemtradieukiencon);
            Log.e("batthuong1 & batthuong2", String.valueOf(batthuong1) + batthuong2);
         if(KiemTraDaGhi(MaKH.getText().toString().trim())){
                kiemtradieukien =true;
                kiemtradieukiencon = true;
            }
            if(kiemtradieukien && kiemtradieukiencon){
                if(batthuong2.equals("") && batthuong1.equals("")&& spinTT.getSelectedItemPosition()==0){
                    ghinuoc("");
                }
                else{
                    ghinuoc("BT");
                }
            }

        }
        else{
            if(ChiSoMoi.getText().toString().trim().equals("")){
                Log.e("da ghi nuoc", String.valueOf(KiemTraDaGhi(MaKH.getText().toString().trim())));
                if(KiemTraDaGhi(MaKH.getText().toString().trim())) {
                    showDiaLogThongBao("Bạn chưa nhập chỉ số nước.");
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Bạn có muốn cập nhật lại thông tin ghi nước của khách hàng này không?");

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ghinuoc("");


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
                    // hiển thị dialog
                }

            }
            else{
                //Kiem tra chi so moi có nhỏ hơn chỉ số cũ ko
                int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
                int chisocu =  Integer.parseInt(ChiSo1.getText().toString().trim());
                //Kiểm tra âm
                if(chisomoi<0){
                    //Show dialog thông báo âm
                    showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                }
                else{
                    if(chisomoi <chisocu){
                        //Show dialog thông báo nhỏ hơn chỉ số cũ
                        //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if(!m3moi.getText().toString().trim().equals("")) {
                                    ghinuoc("BT");
                                }
                                else{
                                    showDiaLogThongBao("Sử dụng chỉ số bất thường cần nhập m3 nước.");
                                }
                                dialog.dismiss();

                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                resetViewGhiNuoc();
                                dialog.dismiss();


                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        // hiển thị dialog
                    }
                    else{
                        //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false
                        if(m3moi.getText().toString().trim().equals("")) {
                            m3moi.setText(String.valueOf(chisomoi - chisocu));
                        }
                        int m3cu1 = Integer.parseInt(m31.getText().toString());
                        int m3cu2 = Integer.parseInt(m32.getText().toString());
                        int m3cu3 = Integer.parseInt(m33.getText().toString());
                        int m3NEW = Integer.parseInt(m3moi.getText().toString());
                        int binhquan3thang  = BinhQuanChiSoNuoc3Thang(m3cu1,m3cu2,m3cu3);
                        if(kiemtraBatThuongLonHon(m3NEW,binhquan3thang)){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            // khởi tạo dialog

                            alertDialogBuilder.setMessage("Số m3 quá lớn so với bình thường. Bạn có xác định đây là bất thường không?");

                            alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog

                                    alertDialogBuilder.setMessage("Đã xác định đây là bất thường. Bạn có muốn ghi nước không?");

                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ghinuoc("BT");

                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            resetViewGhiNuoc();
                                            dialog.dismiss();


                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                    // hiển thị dialog

                                }
                            });
                            alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    // khởi tạo dialog

                                    alertDialogBuilder.setMessage("Đã xác định đây không phải là bất thường. Bạn có muốn ghi nước không?");

                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ghinuoc("");

                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            resetViewGhiNuoc();
                                            dialog.dismiss();


                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                    // hiển thị dialog


                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                            // hiển thị dialog
                        }else {
                            ghinuoc("");
                        }
                    }
                }

            }
        }


    }

private void showDiaLogThongBao(String mess){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
    // khởi tạo dialog

    alertDialogBuilder.setMessage(mess);

    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            resetViewGhiNuoc();
            dialog.dismiss();

        }
    });


    AlertDialog alertDialog = alertDialogBuilder.create();
    // tạo dialog
    alertDialog.setCanceledOnTouchOutside(false);
    alertDialog.show();
    // hiển thị dialog
}

 public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
private void  resetViewGhiNuoc(){
  //  DienThoai.setText("");
   // TinhTrangTLK.setText("");
   // spinTT.setSelection(0);
    ChiSoMoi.setText("");
    ChiSoMoiCon.setText("");
    m3moi.setText("");
    m3conmoi.setText("");
  //  GhiChu.setText("");
}

public boolean KiemTraDaGhi(String maKH){
    KhachHangDTO kh = khachhangDAO.getKHTheoMaKH(maKH);
 //   Log.e("thong tin chi so", "Chi so db:" + kh.getChiSo() +  "  Chi so man hinh: "+ChiSoMoi.getText().toString().trim() );
  //  Log.e("Ten tt",tenTT);


    if(kh.getTrangThaiTLK().equals("")) {
        Log.e("KT dt", kh.getDienThoai()+     kh.getDienThoai().equals(DienThoai.getText().toString().trim()));
        Log.e("KT getChiSo", kh.getChiSo()+      kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim() ));
        Log.e("KT sltieuthu",kh.getSLTieuThu() +   kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()));
        Log.e("KT chisocon", kh.getChiSocon()+  kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()));
        Log.e("KT ghichu",kh.getGhiChu()+    kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()));
        if(kh.getDienThoai().equals(DienThoai.getText().toString().trim())  &&
                // kh.getTrangThaiTLK().equals(TinhTrangTLK.getText().toString().trim()) &&
                kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim()) &&
               // kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()) &&
                kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()) &&
                kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()))
        {
            Log.e("Da ghi ko co bat thuong", "true");
            return true;
        }
        else {
            Log.e("Chua ghi ko co bat thuong", "true");
            return false;
        }
    }
    else{
        Log.e("KT dt", kh.getDienThoai()+     kh.getDienThoai().equals(DienThoai.getText().toString().trim()));
        Log.e("Ten getTrangThaiTLK",kh.getTrangThaiTLK()+ kh.getTrangThaiTLK().trim().equalsIgnoreCase(tenTT.trim()));
        Log.e("KT getChiSo", kh.getChiSo()+      kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim() ));
        Log.e("KT sltieuthu",kh.getSLTieuThu() + ".san luong :"+ m3moi.getText().toString().trim() +" "+  kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()));
        Log.e("KT chisocon", kh.getChiSocon()+  kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()));
        Log.e("KT ghichu",kh.getGhiChu()+    kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()));
        if(kh.getDienThoai().equalsIgnoreCase(DienThoai.getText().toString().trim())  &&
                // kh.getTrangThaiTLK().equals(TinhTrangTLK.getText().toString().trim()) &&

            //    kh.getTrangThaiTLK().trim().equalsIgnoreCase(tenTT.trim()) &&
                kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim()) &&
          //      kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()) &&
                kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()) &&
                kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()))
        {
            Log.e("Da ghi co bat thuong", "true");
            return true;
        }
        else {
            Log.e("Chua ghi co bat thuong", "true");
            return false;
        }
    }

}

    public boolean KiemTraDaGhiChiSo(String maKH) {
        KhachHangDTO kh = khachhangDAO.getKHTheoMaKH(maKH);
        if(kh.getChiSo().equals("") || kh.getSLTieuThu().equals("")){
            return false;
        }else{
            return true;
        }
    }



    private void loadDataTinhTrangTLK(){
       // List<TinhTrangTLKDTO> listtt = tinhtrangtlkdao.getAllTinhTrang();
        List<TinhTrangTLKDTO> listtt = tinhtrangtlkdao.TaoDSTinhTrang();
        Log.e("list tinh trang", String.valueOf(listtt.size()));
        arrTT = new ArrayList<>();
        for(int i = 0; i<listtt.size();i++){
            String sDuong  = listtt.get(i).getTentt();
            sDuong.trim();
            arrTT.add(sDuong);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arrTT
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinTT.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tenTT = arrTT.get(position);
                if(position==0){

                        if(!ChiSoMoi.getText().toString().trim().equals("")) {
                            int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
                            int chisocu = Integer.parseInt(ChiSo1.getText().toString().trim());
                            if(chisomoi >chisocu) {
                                m3moi.setText(String.valueOf(chisomoi - chisocu));
                            }
                        }
                        else{
                            m3moi.setText("");
                        }

                    m3moi.setEnabled(false);

                }
                else{
                    if(m3moi.getText().toString().trim().equals("")){
                        Log.e("asdasdasd","ok");
                        if(!ChiSoMoi.getText().toString().trim().equals("")) {
                            int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
                            int chisocu = Integer.parseInt(ChiSo1.getText().toString().trim());
                            if(chisomoi >chisocu) {
                                m3moi.setText(String.valueOf(chisomoi - chisocu));
                            }
                        }
                        else{
                            m3moi.setText("");
                        }
                    }
                    m3moi.setEnabled(true);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                tenTT = arrTT.get(0);
                m3moi.setEnabled(false);

            }
        });


    }
    public int BinhQuanChiSoNuoc3Thang(int x, int y, int z){
        int tong3so =  x + y + z;
        int sobichia = 0;
        int ketqua = 0;
        if(x == 0 && y==0 && z ==0){
            ketqua = 0;
        }
        else {
            if (x != 0) {
                sobichia = sobichia + 1;
            }
            if (y != 0) {
                sobichia = sobichia + 1;
            }
            if (z != 0) {
                sobichia = sobichia + 1;
            }
            ketqua = tong3so / sobichia;
        }

        return Math.round(ketqua);
    }
    public boolean kiemtraBatThuongLonHon(int x,int binhquan3thang){

            if (x >= binhquan3thang * 2 && Math.abs(x - binhquan3thang) > 20) {
                return true;
            } else {
                return false;
            }


    }
    public boolean KiemTraPhanTuCoTrongList(String makh,List<String> listmakh){
        boolean kt = true;
        if(listmakh.contains(makh)){
            kt = true;
        }
        else{
            kt = false;
        }

        return kt;

    }
    public void addListNeuChuaTonTai(String makh,List<String> listmakh){
        if(!KiemTraPhanTuCoTrongList(makh,listmakh)){
            listmakh.add(makh);
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS đã bị tắt.Hãy bật GPS để có thể ghi nước.")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}

