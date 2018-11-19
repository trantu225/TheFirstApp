package tiwaco.thefirstapp;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.spacenavigation.SpaceNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListThanhToanAdapter;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.JSONRequestObject;
import tiwaco.thefirstapp.DTO.JSONRequestObjectThu;
import tiwaco.thefirstapp.DTO.JSONUpdateThongTinKH;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ListKHTheoDuong;
import tiwaco.thefirstapp.DTO.ListKHTheoDuongThu;
import tiwaco.thefirstapp.DTO.ListRequestObject;
import tiwaco.thefirstapp.DTO.ListRequestObjectThu;
import tiwaco.thefirstapp.DTO.PeriodDTO;
import tiwaco.thefirstapp.DTO.RequestGetBillInfoDTO;
import tiwaco.thefirstapp.DTO.RequestObject;
import tiwaco.thefirstapp.DTO.RequestObjectThu;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.SPData;


public class MainThuActivity extends AppCompatActivity  {

    TextView danhsachduong;
    ImageButton btnThu;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String filename = "";
    String duongdanfile = "";
    boolean kiemtradieukien = false,kiemtradieukiencon = false; //cho phep Thu , false: ko Thu
    String batthuong1 = "", batthuong2 ="";
    private static String dataThu = "";
    SpaceNavigationView spaceNavigationView;
    Context con;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    LichSuDAO lichsudao;
    KhachHangDTO khachhang;
    ThanhToanDAO thanhtoandao;
    TextView lb_sohoadonno, lb_laninTB, lb_laninBN, lb_tbhetno, lbthongtinkh, thongtinKH, BinhQuanBaThang, LoaiKH, DinhMuc, LabelDuong, STT, DanhBo, MaKH, HoTen, DiaChi, MaTLK, HieuTLK, CoTLK, ChiSo1, ChiSo2, ChiSo3, m31, m32, m33, ChiSoCon1, ChiSoCon2, ChiSoCon3, m3con1, m3con2, m3con3, DuongDangThu, ConLai;
    EditText DienThoai, ChiSoMoi, ChiSoMoiCon,TinhTrangTLK,GhiChu,m3moi, m3conmoi;
    TableRow chisocu_con_lb, chisocu_con, chisomoi_con_lb, chisomoi_con;
    ImageButton DoiSDT,Toi,Lui,Thu,CapNhatGhiChu;
    ImageButton ChuyenLoai, ThongTinChiTietHoaDon;

    Spinner spinTT;
    LinearLayout lay_toi , lay_lui , lay_Thu,lay_ki1,lay_ki2,lay_ki3;
    TableLayout layout_thongtinkh;
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
    private static boolean flagDangThu = false;
    TinhTrangTLKDAO tinhtrangtlkdao;
    ArrayList<String>  arrTT = null ;
    String tenTT ="";
    Menu menumain;
    int bienkieuThu =1;
    int sttmax =0;
    boolean biendaGhiChuaThu = false;
    List<String> listtoi = null, listlui=null;
    String ky1="", ky2="", ky3="";
    String urlstr ;
    // HashMap<String, List<ThanhToanDTO>> mDataThanhToan;
    LinearLayout relativeLayout;
    private PrintManager mgr=null;
    byte FONT_TYPE;


    boolean hienthithongtinKH = false;
    int loaiinbiennhan = 0;

    CustomListThanhToanAdapter thanhtoandapter;

    DecimalFormat format,format1,format2 ;
    RadioButton rad_ingiaybao,rad_inhd;
    ExpandableListView ListThanhToanTheoKy;
    Button thanhtoantiennuoc, inthongbao;
    TextView tv_csocu, tv_csomoi, tv_m3, tv_tiennuoc, tv_thue, tv_phi, tv_tongcong, tv_sumofmoney;
    ProgressDialog dialogxuly;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thu);
        con = MainThuActivity.this;
        Log.e("onCreate", "onCreate-----------------------------------------------");
        getSupportActionBar().setTitle(R.string.tab_thunuoc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taoView();
        spdata = new SPData(con);
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);
        lichsudao = new LichSuDAO(con);
        thanhtoandao = new ThanhToanDAO(con);
        listtoi = new ArrayList<>();
        listlui = new ArrayList<>();
        tinhtrangtlkdao = new TinhTrangTLKDAO(con);
        urlstr =   getString(R.string.API_UpdateKhachHangDaGhi2);
        loadDataTinhTrangTLK();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBTReceiver, filter);

//        MyDatabaseHelper mydata = new MyDatabaseHelper(con);
//        SQLiteDatabase db = mydata.openDB();
//        mydata.resetDatabaseTT(db);
         List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//        for(int tt = 0 ; tt<listt.size();tt++){
//            tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//        }





        //----- lay toa do

        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainThuActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        //----------------------------------------------------------------------------
        //lấy intent gọi Activity này
        //Lấy kết quả khi chọn button Thu nước tại listactivity
        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_MADUONGTHU);
        if (packageFromCaller == null) {
            //get sharepreferences
            final String SPduongdangthu  = spdata.getDataDuongDangThuTrongSP(); //lấy đường đang Thu

             //tìm min(stt) của khách hàng chưa nThu tại đường đang Thu
            //luc bat dau chua co gi
            Log.e("MAIN","luc bat dau chua co gi");
            if(SPduongdangthu.equalsIgnoreCase("")){

               //dialog chọn đường
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.main_chuacothongtinduongdethu);
                // thiết lập nội dung cho dialog
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent=new Intent(MainThuActivity.this, ListActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
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


                maduong_nhan = SPduongdangthu;
                tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
                String STT = spdata.getDataSTTDangThuTrongSP() ;
                if(STT.equals("")) {
                    STT= khachhangDAO.getSTTChuaThuNhoNhat(maduong_nhan);
                }
                STT_HienTai = STT;
                Log.e("STTMIN SP--------------------------",STT_HienTai);
                SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
                setDataForView(STT_HienTai,maduong_nhan,"");

                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                //  spdata.luuDataIndexDuongDangThuTrongSP(Bien.bien_index_duong);
                // Bien.selected_item = spdata.getDataIndexDuongDangThuTrongSP();
                Toast.makeText(MainThuActivity.this, maduong_nhan, Toast.LENGTH_SHORT).show();

            }


        } else {
            //từ listactivity  sang main để Thu nước
            Log.e("MAIN","từ listactivity  sang main để Thu nước");
            //Có Bundle rồi thì lấy các thông số dựa vào key maduong và stt
            maduong_nhan = packageFromCaller.getString(Bien.MADUONGTHU);
            tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
            stt_nhan =  packageFromCaller.getString(Bien.STTTHU);
            vitri_nhan =  packageFromCaller.getInt(Bien.VITRITHU);
            makh_nhan = packageFromCaller.getString(Bien.MAKHTHU);
            Log.e("MAINTHUACTIVITY_vitriduong", String.valueOf(maduong_nhan));
            if(makh_nhan == null){
                makh_nhan ="";
            }
            if(maduong_nhan.equals("99")){
                STT_HienTai = "0";
            }
            else {
                STT_HienTai = stt_nhan;
            }
            spdata.luuDataDuongVaSTTDangThuTrongSP(maduong_nhan,STT_HienTai);//luu vao sharepreferences
            SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
            setDataForView(STT_HienTai,maduong_nhan,makh_nhan);
          //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
            if(vitri_nhan !=-1) {
                spdata.luuDataIndexDuongDangThuTrongSP(vitri_nhan);
                Log.e("MAINTHUACTIVITY_vitriduong", String.valueOf(vitri_nhan));
                Bien.selected_item_thu = spdata.getDataIndexDuongDangThuTrongSP();

            }
            Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
        }
        //---set duong
        tenduong =  duongDAO.getTenDuongTheoMa(maduong_nhan).trim();
        DuongDangThu.setText(tenduong);
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
                    alert.showDialog(MainThuActivity.this, ky1, ChiSo1.getText().toString(), m31.getText().toString());
                }
            });
            lay_ki2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDialog_KyHD alert = new ViewDialog_KyHD();
                    alert.showDialog(MainThuActivity.this, ky2, ChiSo2.getText().toString(), m32.getText().toString());
                }
            });
            lay_ki3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewDialog_KyHD alert = new ViewDialog_KyHD();
                    alert.showDialog(MainThuActivity.this, ky3, ChiSo3.getText().toString(), m33.getText().toString());
                }
            });

        }

        //--------------------------------------------------------------------------
        inthongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rad_inhd.setChecked(false);
                rad_ingiaybao.setChecked(true);
                checkbill();

            }
        });

        DoiSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capnhatDTVaGhiChu();
            }
        });
        CapNhatGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiSDT.performClick();
            }
        });


        thanhtoantiennuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rad_inhd.setChecked(true);
                rad_ingiaybao.setChecked(false);
//                if (thanhtoandao.countKhachHangChuaThuTheoMaKH(MaKH.getText().toString().trim()) == 0) {
//
//                    connect(loaiinbiennhan);
//                        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                            connect(loaiinbiennhan);
//
//                        } else {
//                            showGPSDisabledAlertToUser();
//                        }
//                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                    // khởi tạo dialog

                alertDialogBuilder.setMessage("Bạn có muốn thanh toán tiền nước khách hàng này không?");

                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            paybillall();

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

                //               }


//                try {
//                    if (isInternetOn()) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                        // khởi tạo dialog
//                        alertDialogBuilder.setTitle("Thanh toán tiền nước");
//                        alertDialogBuilder.setMessage("Bạn có muốn thanh toán tiền nước không? ");
//                        // thiết lập nội dung cho dialog
//                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                String urlgetBill = getString(R.string.API_PayBillNB);
//                                KiemTraThongTinThuTienNuoc ask = new KiemTraThongTinThuTienNuoc();
//                                ask.execute(urlgetBill);
//                            }
//                        });
//
//                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                            }
//                        });
//
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        // tạo dialog
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                    } else {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                        // khởi tạo dialog
//                        alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
//                        // thiết lập nội dung cho dialog
//
//                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//
//                                // button "no" ẩn dialog đi
//                            }
//                        });
//                        alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//
//                                // button "no" ẩn dialog đi
//                            }
//                        });
//
//
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        // tạo dialog
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();
//                    }
//                } catch (Exception e) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                    // khởi tạo dialog
//                    alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
//                    // thiết lập nội dung cho dialog
//
//                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//
//                            // button "no" ẩn dialog đi
//                        }
//                    });
//                    alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//
//                            // button "no" ẩn dialog đi
//                        }
//                    });
//
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    // tạo dialog
//                    alertDialog.setCanceledOnTouchOutside(false);
//                    alertDialog.show();
//                }


            }
        });
        thongtinKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stt = STT.getText().toString().trim();
                String danhbo = DanhBo.getText().toString().trim();
                String makh = MaKH.getText().toString().trim();
                String hoten = HoTen.getText().toString().trim();
                String diachi = DiaChi.getText().toString().trim() + " " + tenduong.trim();
                String dienthoai = DienThoai.getText().toString().trim();
                ViewDialog_ThongTinKH dialog = new ViewDialog_ThongTinKH();
                dialog.showDialog(MainThuActivity.this, stt, danhbo, makh, hoten, diachi, dienthoai);


//                if (hienthithongtinKH) {
//                    layout_thongtinkh.setVisibility(View.GONE);
//                    hienthithongtinKH = false;
//                } else {
//                    layout_thongtinkh.setVisibility(View.VISIBLE);
//                    hienthithongtinKH = true;
//
//                    String stt = STT.getText().toString().trim();
//                    String danhbo = DanhBo.getText().toString().trim();
//                    String makh = MaKH.getText().toString().trim();
//                    String hoten = HoTen.getText().toString().trim();
//                    String diachi = DiaChi.getText().toString().trim() + " "+ tenduong.trim();
//                    String dienthoai  = DienThoai.getText().toString().trim();
//                    ViewDialog_ThongTinKH dialog   = new ViewDialog_ThongTinKH();
//                    dialog.showDialog(MainThuActivity.this,stt,danhbo,makh,hoten,diachi,dienthoai);
//                }



            }
        });
        lbthongtinkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongtinKH.performClick();
            }
        });


        lay_Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loaiinbiennhan = 0;
                if (rad_ingiaybao.isChecked()) {
                    rad_inhd.setChecked(false);
                    connect(loaiinbiennhan);
//                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                        connect(loaiinbiennhan);
//
//                    } else {
//                        showGPSDisabledAlertToUser();
//                    }
                } else if (rad_inhd.isChecked()) {
                    rad_ingiaybao.setChecked(false);
                    if (thanhtoandao.countKhachHangChuaThuTheoMaKH(MaKH.getText().toString().trim()) == 0) {

                        connect(loaiinbiennhan);
//                        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                            connect(loaiinbiennhan);
//
//                        } else {
//                            showGPSDisabledAlertToUser();
//                        }
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage("Khách hàng chưa thanh toán tiền nước.Bạn có muốn thanh toán tiền nước khách hàng này không?");

                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                thanhtoantiennuoc.performClick();

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





            }
        });
        Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_Thu.performClick();
            }
        });

        lay_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!flagDangThu) {
                    listlui.clear();
                    KhachHangDTO khnext = khachhangDAO.getKHTheoSTT_Duong_khacmaKH(STT_HienTai, maduong_nhan, DanhBo.getText().toString().trim(),"<>");
                    if (khnext != null && !KiemTraPhanTuCoTrongList(khnext.getMaKhachHang(),listtoi)) {

                        addListNeuChuaTonTai(MaKH.getText().toString().trim(),listtoi);
                        setDataForView(STT_HienTai, maduong_nhan, khnext.getMaKhachHang());
                        addListNeuChuaTonTai(khnext.getMaKhachHang(), listtoi);


                    } else {
                        Log.e("maduong stt",maduong_nhan + " " +STT_HienTai);
                        String sttketiep = "";
                        if(!biendaGhiChuaThu) {
                            sttketiep= khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                        }
                        else{
                            sttketiep = khachhangDAO.getSTTChuaThuNhoNhatLonHonHienTai1(maduong_nhan, STT_HienTai);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Bạn đang Thu nước.Bạn có muốn hủy Thu nước không?");

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String sttketiep = "";
                            if(!biendaGhiChuaThu) {
                                sttketiep= khachhangDAO.getSTTNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                            }
                            else{
                                sttketiep = khachhangDAO.getSTTChuaThuNhoNhatLonHonHienTai1(maduong_nhan, STT_HienTai);
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
                if(!flagDangThu){
                    listtoi.clear();
                    KhachHangDTO khlui = khachhangDAO.getKHTheoSTT_Duong_khacmaKH(STT_HienTai, maduong_nhan, DanhBo.getText().toString().trim(),"<>");
                    if (khlui != null && !KiemTraPhanTuCoTrongList(khlui.getMaKhachHang(),listlui)) {

                        addListNeuChuaTonTai(MaKH.getText().toString().trim(),listlui);
                        setDataForView(STT_HienTai, maduong_nhan, khlui.getMaKhachHang());
                        addListNeuChuaTonTai(khlui.getMaKhachHang(), listlui);


                    } else {
                        //String stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                        String stttruoc = "";
                        if(!biendaGhiChuaThu) {
                            stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                        }
                        else{
                            stttruoc = khachhangDAO.getSTTChuaThuLonNhatNhoHonHienTai1(maduong_nhan, STT_HienTai);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Bạn đang Thu nước.Bạn có muốn hủy Thu nước không?");

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            String stttruoc = "";
                            if(!biendaGhiChuaThu) {
                                stttruoc = khachhangDAO.getSTTLonNhatNhoHonHienTai(maduong_nhan,STT_HienTai);
                            }
                            else{
                                stttruoc = khachhangDAO.getSTTChuaThuLonNhatNhoHonHienTai1(maduong_nhan, STT_HienTai);
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
        









    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop-----------------------------------------------");
        Log.e("select duong-mainactivity", String.valueOf(Bien.selected_item));
    }

    private void setDataForView(String tt, String maduong,String Makhach) {
        //Lấy khách hàng có stt hiện tại...mặc đình là 1

        Log.e("thu tu , ma duong, ma khach",tt +","+ maduong+ ","+ Makhach);
        tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong_nhan)) ;
        soKHconlai = String.valueOf(khachhangDAO.countKhachHangChuaThuTheoDuong(maduong)) ;


        String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        soKHHomNay  = String.valueOf(khachhangDAO.countKhachHangThuTrongNgay(maduong,thoigian1));
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

        tv_sumofmoney.setText(thanhtoandao.getSoTienTongCongTheoMAKH(khachhang.getMaKhachHang().trim()));
        lb_sohoadonno.setText(String.valueOf(thanhtoandao.countHDTheoMaKH(khachhang.getMaKhachHang().trim())));

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

        HashMap<String, List<ThanhToanDTO>> mDataThanhToan = new HashMap<>();
        List<String> listKYHD = thanhtoandao.getListKyHDThanhToanTheoMaKH(khachhang.getMaKhachHang().trim());
        Log.e("danh sach hashmap", String.valueOf(listKYHD.size()));
        Log.e("makh mhan", makh_nhan);
        if (listKYHD.size() > 0) {
            for (int i = 0; i < listKYHD.size(); i++) {
                List<ThanhToanDTO> listtt = thanhtoandao.GetThanhToanTheoKyHDVaMaKH(khachhang.getMaKhachHang().trim(), listKYHD.get(i).toString().trim());
                if (listtt.size() > 0) {
                    mDataThanhToan.put(listKYHD.get(i).toString().trim(), listtt);
                }
            }

        }
        Log.e("danh sach hashmap", String.valueOf(mDataThanhToan.size()));
        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(con, "THU TIEN NUOC:You need have granted permission.......", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainThuActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            gps = new GPSTracker(con, MainThuActivity.this);
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                vido = String.valueOf(latitude);
                kinhdo = String.valueOf(longitude);
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }


        if (mDataThanhToan.size() > 0) {
            lb_tbhetno.setVisibility(View.GONE);
            //ListThanhToanTheoKy.setVisibility(View.VISIBLE);
            thanhtoandapter = new CustomListThanhToanAdapter(con, listKYHD, mDataThanhToan, vido, kinhdo, LabelDuong, DuongDangThu, ConLai);
            //thanhtoandapter.notifyDataSetChanged();
            ListThanhToanTheoKy.setAdapter(thanhtoandapter);
        } else {
            lb_tbhetno.setVisibility(View.GONE);
            // ListThanhToanTheoKy.setVisibility(View.GONE);
            thanhtoandapter = null;
            ListThanhToanTheoKy.setAdapter(thanhtoandapter);
        }


        ThongTinChiTietHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog_ChiTietHDThanhToan dialog = new ViewDialog_ChiTietHDThanhToan();
                dialog.showDialog(MainThuActivity.this, thanhtoandapter);
            }
        });

//        ListThanhToanTheoKy.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                setListViewHeight(parent, groupPosition);
//                return false;
//            }
//        });

        // Log.e("Tatca thanh toan",String.valueOf(thanhtoandao.getTatCaThanhToan().size()));



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
        GhiChu.setText(khachhang.getGhichuthu().trim());
        thongtinKH.setText("STT: " + khachhang.getSTT().toString());

        int m3cu1 = Integer.parseInt(m31.getText().toString());
        int m3cu2 = Integer.parseInt(m32.getText().toString());
        int m3cu3 = Integer.parseInt(m33.getText().toString());

        int binhquan3thang  = BinhQuanChiSoNuoc3Thang(m3cu1,m3cu2,m3cu3);
        BinhQuanBaThang.setText("Bình quân 3 tháng gần nhất: "+ binhquan3thang +" m3");
        flagDangThu = false;
        int soluongthanhtoan = thanhtoandao.countKhachHangChuaThuTheoMaKH(khachhang.getMaKhachHang().trim());
        if (soluongthanhtoan == 0) {


            lb_tbhetno.setVisibility(View.GONE);
            LabelDuong.setBackgroundResource(android.R.color.holo_red_dark);
            DuongDangThu.setBackgroundResource(android.R.color.holo_red_dark);
            ConLai.setBackgroundResource(android.R.color.holo_red_dark);
            // lay_Thu.setEnabled(false);
            // Thu.setEnabled(false);



        }
        else{
            lb_tbhetno.setVisibility(View.GONE);
            LabelDuong.setBackgroundResource(R.color.colorPrimaryDark);
            DuongDangThu.setBackgroundResource(R.color.colorPrimaryDark);
            ConLai.setBackgroundResource(R.color.colorPrimaryDark);
            //lay_Thu.setEnabled(true);
            // Thu.setEnabled(true);

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


        tv_csocu.setText(khachhang.getChiSo1());
        tv_csomoi.setText(khachhang.getChiSo());
        tv_m3.setText(khachhang.getSLTieuThu());
        rad_ingiaybao.setChecked(true);

        //  if (thanhtoandao.GetListThanhToanTheoMaKH(khachhang.getMaKhachHang()).size() > 0) {
//            List<String> listkyhd = thanhtoandao.getListKyHDThanhToanTheoMaKH(khachhang.getMaKhachHang());
//            List<String> listsolanin = thanhtoandao.GetSoLanInTheoMaVaKyHD(khachhang.getMaKhachHang(), listkyhd.get(0));
//            String solaninbn = String.valueOf(Integer.parseInt(listsolanin.get(0)));
//            String solanintb = String.valueOf(Integer.parseInt(listsolanin.get(2)));
//            rad_ingiaybao.setText("In giấy báo - Đã in:" + solanintb + " lần");
//            rad_inhd.setText("In biên nhận thanh toán - Đã in:" + solaninbn + " lần");

        //     List<String> listkyhd = thanhtoandao.getListKyHDThanhToanTheoMaKH(khachhang.getMaKhachHang());


        //  }
        List<String> listsolanin = khachhangDAO.GetSoLanInTheoMaKH(khachhang.getMaKhachHang().trim());
        String solaninbn = String.valueOf(Integer.parseInt(listsolanin.get(0)));
        String solanintb = String.valueOf(Integer.parseInt(listsolanin.get(2)));
        rad_ingiaybao.setText("In giấy báo - Đã in:" + solanintb + " lần");
        rad_inhd.setText("In biên nhận thanh toán - Đã in:" + solaninbn + " lần");
        lb_laninTB.setText(solanintb + " lần");
        lb_laninBN.setText(solaninbn + " lần");
        inthongbao.setText("IN GIẤY BÁO - " + solanintb + "");
        thanhtoantiennuoc.setText("THANH TOÁN VÀ IN BIÊN NHẬN - " + solaninbn + "");
//        String tiennuoc = String.valueOf(khachhang.getTienNuoc());
//        if(!tiennuoc.equals("")){
//
//            tv_tiennuoc.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble((tiennuoc)))))+ " đ");
//        }
//        String phi  = String.valueOf(khachhang.getphi());
//        if(!phi.equals("")){
//
//            tv_phi.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(phi))))+ " đ");
//        }
//        String thue  = String.valueOf(khachhang.getThue());
//        if(!thue.equals("")) {
//
//
//            tv_thue.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(thue)))) + " đ");
//        }
//        String tongcong = String.valueOf(khachhang.gettongcong());
//        if (!tongcong.equals("")) {
//
//            tv_tongcong.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(tongcong)))) + " đ");
//        }


    }


    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                if (Bien.btsocket != null) {

                    try {

                        if (Bien.btsocket != null) {
                            Toast.makeText(MainThuActivity.this, "Đóng kết nối...", Toast.LENGTH_LONG).show();
                            Bien.btoutputstream.close();
                            Bien.btsocket.getOutputStream().close();
                            Bien.btsocket.close();
                            Bien.btsocket = null;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                            // khởi tạo dialog

                            alertDialogBuilder.setMessage("Kết nối với máy in thất bại.Hãy kiểm tra lại máy in đã mở chưa.");

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  new UpdateThongTinThuNuoc().execute(urlstr);
                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                            // hiển thị dialog
                        }
                    } catch (Exception ez) {
                        ez.printStackTrace();
                    }


                }

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                try {
                    if (Bien.btsocket != null) {
                        Toast.makeText(MainThuActivity.this, "Đóng kết nối...", Toast.LENGTH_LONG).show();
                        Bien.btoutputstream.close();
                        Bien.btsocket.getOutputStream().close();
                        Bien.btsocket.close();
                        Bien.btsocket = null;
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage("Kết nối với máy in thất bại.Hãy kiểm tra lại máy in đã mở chưa.");

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  new UpdateThongTinThuNuoc().execute(urlstr);
                                dialog.dismiss();
                        }
                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        // hiển thị dialog
                }
                } catch (Exception ez) {
                    ez.printStackTrace();
                }

            }
        }
    };

    protected void connect(int loai) {

        if (Bien.btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
        } else {


            try {


                OutputStream opstream = null;
                try {
                    opstream = Bien.btsocket.getOutputStream();
                    Log.e("getOutputStream", String.valueOf(Bien.btsocket.getOutputStream() == null));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bien.btoutputstream = opstream;
                if (Bien.btoutputstream != null) {
                    if (rad_ingiaybao.isChecked()) {
                        print_bt();
                    } else if (rad_inhd.isChecked()) {
                        print_bt_hoadon(loai);
                    }
                } else {
                    connect(loai);
                }


            } catch (Exception e) {
                e.printStackTrace();


            }
        }
    }
    private void print_bt() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        layoutToImage();
//        try {
//            imageToPDF();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            Bien.btoutputstream = Bien.btsocket.getOutputStream();


            byte[] printformat = { 0x1B, 0x21, FONT_TYPE };
            //Bien.btoutputstream.write(printformat);

            String xuongdong  ="\n";
            String tencty = "CTY TNHH MTV CẤP NƯỚC TG\n\n";
            String Giaybao = "GIẤY BÁO\n";
            String thoigian = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            Log.e("thoi gian", thoigian);
            String ngay = "Ngày "+thoigian+"\n";
            String kyhd = spdata.getDataKyHoaDonTrongSP();
            Log.e("KYHD",kyhd);
            String gach = "--------------------------------";
            String haigach = "================================";
            String kihd ="";
            String lanTB = "";

            String makh = "Mã KH: " + MaKH.getText().toString().trim() + "\n";
            String madb = "Danh bộ: " + LoaiKH.getText().toString().trim() + "-" + maduong_nhan + "-" + DanhBo.getText().toString() + "\n";
            String lb_ten = "Tên KH: ";
            String ten = HoTen.getText().toString().trim();
            List<String> listhotenmoihang = catchuxuongdong(ten, lb_ten);
            String diachi = "Địa chỉ: " + DiaChi.getText().toString().trim() + " - " + DuongDangThu.getText().toString();
            List<String> listdiachimoihang = catchuxuongdong(diachi, "");

            List<ThanhToanDTO> listchuathanhtoan = thanhtoandao.GetListThanhToanTheoMaKH(MaKH.getText().toString().trim());
            if (khachhangDAO.tangSoLanIn(MaKH.getText().toString().trim(), 0, 0, 1)) {
                for (int ky = 0; ky < listchuathanhtoan.size(); ky++) {
                    LichSuDTO ls = new LichSuDTO();
                    ls.setNoiDungLS("In thông báo tiền nước khách hàng có mã KH " + MaKH.getText().toString().trim());
                    ls.setMaLenh("IT");
                    ls.setThoiGianLS(thoigian);
                    lichsudao.addTable_History(ls);

                    if (thanhtoandao.tangSoLanIn(listchuathanhtoan.get(ky).getBienLai().trim(), 0, 0, 1)) {
                        Log.e("bienlaithanhtoan", listchuathanhtoan.get(ky).getBienLai().trim());

                    }
                }
            }
            if (thanhtoandao.GetListThanhToanTheoMaKH(khachhang.getMaKhachHang()).size() > 0) {
                List<String> listkyhd = thanhtoandao.getListKyHDThanhToanTheoMaKH(MaKH.getText().toString().trim());
//                List<String> listsolanin = thanhtoandao.GetSoLanInTheoMaVaKyHD(MaKH.getText().toString().trim(), listkyhd.get(0));
//                 String solaninbn = String.valueOf(Integer.parseInt(listsolanin.get(0)));
//                 String solanintb = String.valueOf(Integer.parseInt(listsolanin.get(2)));
                List<String> listsolanin = khachhangDAO.GetSoLanInTheoMaKH(MaKH.getText().toString().trim());
                String solaninbn = String.valueOf(Integer.parseInt(listsolanin.get(0)));
                String solanintb = String.valueOf(Integer.parseInt(listsolanin.get(2)));
                lanTB = "Lần in:" + solanintb + "\n";
                rad_ingiaybao.setText("In giấy báo - Đã in:" + solanintb + " lần");
                rad_inhd.setText("In biên nhận thanh toán - Đã in: " + solaninbn + " lần");
                lb_laninTB.setText(solanintb + " lần");
                lb_laninBN.setText(solaninbn + " lần");
                inthongbao.setText("IN GIẤY BÁO - " + solanintb + "");
                thanhtoantiennuoc.setText("THANH TOÁN VÀ IN BIÊN NHẬN - " + solaninbn + "");

            }
            double tongsotien = 0;
            for (int i = 0; i < listchuathanhtoan.size(); i++) {
                tongsotien += Double.valueOf(listchuathanhtoan.get(i).gettongcong());
            }
            String tongcong = "\nTổng cộng: " + format2.format(Double.parseDouble(format1.format(Double.valueOf(tongsotien)))) + " đ";
//            String nhacnho  = "\nĐề nghị quý khách vui lòng\nthanh toán tiền nước trong vòng\n5 ngày kể từ ngày nhận giấy báo\nQua thời hạn trên Cty sẽ tiến\nhành tạm ngưng cung cấp nước.\n\n";
//            String lienlac ="Vui lòng liên hệ số điện thoại\n0273.3873425 để được giúp đỡ.\n";

            String nhacnho  = "Đề nghị quý khách vui lòng thanh toán tiền nước trong vòng 5 ngày kể từ ngày nhận giấy báo. Qua thời hạn trên Cty sẽ tiến hành tạm ngưng cung cấp nước.";
            List<String> listnhacnhomoihang = catchuxuongdong(nhacnho, "");
//            String lienlac ="Vui lòng liên hệ số điện thoại 0273.3873425 để được giúp đỡ.\n";
//            List<String> listlienlacmoihang  = catchuxuongdong(lienlac);
//            try {
//                byte[] bytes = tencty.getBytes("windows-1258");
//                tencty = new String(bytes, "windows-1258");
//            } catch ( UnsupportedEncodingException e ) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            writeWithFormat(new Formatter().get(), Formatter.centerAlign());
            Bien.btoutputstream.write(tencty.getBytes("X-UTF-16LE-BOM"));
            writeWithFormat(new Formatter().bold().get(), Formatter.centerAlign());
            Bien.btoutputstream.write(Giaybao.getBytes("X-UTF-16LE-BOM"));
            writeWithFormat( new Formatter().get(), Formatter.centerAlign());
            Bien.btoutputstream.write(ngay.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(kihd.getBytes("X-UTF-16LE-BOM"));

            writeWithFormat( new Formatter().get(), Formatter.leftAlign());
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(lanTB.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(makh.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(madb.getBytes("X-UTF-16LE-BOM"));

            Bien.btoutputstream.write(lb_ten.getBytes("X-UTF-16LE-BOM"));
            writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
            for  (int i  = 0; i<listhotenmoihang.size();i++ )
            {
                if (i == 0) {
                    String hangdau = listhotenmoihang.get(i).toString().substring(lb_ten.length());
                    Bien.btoutputstream.write(hangdau.getBytes("X-UTF-16LE-BOM"));
                } else {
                    Bien.btoutputstream.write(listhotenmoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
                }
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            }
            writeWithFormat(new Formatter().get(), Formatter.leftAlign());
            for  (int i  = 0; i<listdiachimoihang.size();i++ )
            {
                Bien.btoutputstream.write(listdiachimoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            }
            Bien.btoutputstream.write(gach.getBytes("X-UTF-16LE-BOM"));
            for (int ky = 0; ky < listchuathanhtoan.size(); ky++)
            {
                String kyhdon = listchuathanhtoan.get(ky).getKyHD();
                String kihdin = "";
                if (!kyhdon.equals("")) {
                    String nam = kyhdon.substring(0, 4);
                    String thang = kyhdon.substring(4);
                    String strkyhd = thang + "/" + nam;
                    kihdin = "\nKỳ hóa đơn: " + strkyhd;
                }

                String chiso = "\nChỉ số cũ: " + listchuathanhtoan.get(ky).getChiSoCu() + "\nChỉ số mới: " + listchuathanhtoan.get(ky).getChiSoMoi() + "\nSố M3 tiêu thụ: " + listchuathanhtoan.get(ky).getSLTieuThu();
                String lb_sotien = "\nSố tiền phải trả: ";
                String sotien = format2.format(Double.parseDouble(format1.format(Double.valueOf(listchuathanhtoan.get(ky).gettongcong())))) + " đ";

                writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
                Bien.btoutputstream.write(kihdin.getBytes("X-UTF-16LE-BOM"));
                writeWithFormat(new Formatter().get(), Formatter.leftAlign());
                Bien.btoutputstream.write(chiso.getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(lb_sotien.getBytes("X-UTF-16LE-BOM"));
                writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
                Bien.btoutputstream.write(sotien.getBytes("X-UTF-16LE-BOM"));
                writeWithFormat(new Formatter().get(), Formatter.leftAlign());
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(gach.getBytes("X-UTF-16LE-BOM"));

            }


            if (listchuathanhtoan.size() > 1) {

                Bien.btoutputstream.write(tongcong.getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(gach.getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            }

            for (int i = 0; i < listnhacnhomoihang.size(); i++)
            {
                Bien.btoutputstream.write(listnhacnhomoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            }
            writeWithFormat(new Formatter().bold().get(), Formatter.rightAlign());
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));

//            for  (int i  = 0; i<listlienlacmoihang.size();i++ )
//            {
//                Bien.btoutputstream.write(listlienlacmoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
//                Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
//            }
            String nhanvien = "Nhân viên: " + spdata.getDataTenNhanVien();
            String dienthoai = "Điện thoại: " + spdata.getDataDienThoai();
            String dienthoaicskh = "CSKH: " + spdata.getDataDienThoaiHuyen();
            Log.e("dienthoai", dienthoai);
//                    String nhacnho = "Khi có nhu cầu in HĐĐT xin vui lòng truy cập vào trang web www.cskh.tiwaco.com.vn ";
//                    List<String> listnhacnhomoihang = catchuxuongdong(nhacnho);
//                    String lienlac = "Vui lòng liên hệ số điện thoại 0273.3873425 để được giúp đỡ.\n";
//                    List<String> listlienlacmoihang = catchuxuongdong(lienlac);
            //Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(nhanvien.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(dienthoai.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(dienthoaicskh.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));

            String ketthuc = "*--*--*";
            writeWithFormat(new Formatter().get(), Formatter.centerAlign());
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(ketthuc.getBytes("X-UTF-16LE-BOM"));
            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));


            Bien.btoutputstream.write(0x0D);
            Bien.btoutputstream.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void print_bt_hoadon(int loai) {


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    Bien.btoutputstream = Bien.btsocket.getOutputStream();


                    byte[] printformat = {0x1B, 0x21, FONT_TYPE};
                    //Bien.btoutputstream.write(printformat);
                    //Get list thanh toan da thanh toan theo makh
                    List<ThanhToanDTO> listthanhtoan = thanhtoandao.GetListThanhToanTheoMaKHDaThanhToan(MaKH.getText().toString().trim());


                    double tongsotien = 0;
                    for (int i = 0; i < listthanhtoan.size(); i++) {
                        tongsotien += Double.valueOf(listthanhtoan.get(i).gettongcong());
                    }


                    String xuongdong = "\n";
                    String tencty = "CTY TNHH MTV CẤP NƯỚC TG\n\n";
                    String Giaybao = "BIÊN NHẬN THANH TOÁN\n";
                    String thoigian = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    String ngay = "Ngày " + thoigian + "\n";
                    String haigach = "================================";
                    String lanin = "";
                    String solaninbn = "";
                    String solanintb = "";

                    if (khachhangDAO.tangSoLanIn(MaKH.getText().toString().trim(), 1, 0, 0)) {
                        LichSuDTO ls = new LichSuDTO();
                        ls.setNoiDungLS("In biên nhận thanh toán khách hàng " + DanhBo.getText().toString().trim());
                        ls.setMaLenh("IB");
                        ls.setThoiGianLS(thoigian);
                        lichsudao.addTable_History(ls);
                        List<String> listsolanin = khachhangDAO.GetSoLanInTheoMaKH(MaKH.getText().toString().trim());
                        solaninbn = String.valueOf(Integer.parseInt(listsolanin.get(0)));
                        solanintb = String.valueOf(Integer.parseInt(listsolanin.get(2)));

                    }


                    lanin = "Lần in: " + solaninbn + "\n";
                    rad_ingiaybao.setText("In giấy báo - Đã in:" + solanintb + " lần");
                    rad_inhd.setText("In biên nhận thanh toán - Đã in:" + solaninbn + " lần");
                    lb_laninTB.setText(solanintb + " lần");
                    lb_laninBN.setText(solaninbn + " lần");
                    inthongbao.setText("IN GIẤY BÁO - " + solanintb + "");
                    thanhtoantiennuoc.setText("THANH TOÁN VÀ IN BIÊN NHẬN - " + solaninbn + "");
                    String madb = "Danh bộ: " + LoaiKH.getText().toString().trim() + "-" + maduong_nhan + "-" + DanhBo.getText().toString() + "\n";
                    String makh = "Mã KH: " + MaKH.getText().toString().trim() + "\n";
                    String lbten = "Tên KH: ";
                    String tenkhach = HoTen.getText().toString().trim();
                    List<String> listhotenmoihang = catchuxuongdong(tenkhach, lbten);
                    String diachi = "Địa chỉ: " + DiaChi.getText().toString().trim() + " - " + DuongDangThu.getText().toString();
                    List<String> listdiachimoihang = catchuxuongdong(diachi, "");
                    String gach = "--------------------------------";
                    writeWithFormat(new Formatter().get(), Formatter.centerAlign());
                    Bien.btoutputstream.write(tencty.getBytes("X-UTF-16LE-BOM"));
                    writeWithFormat(new Formatter().bold().get(), Formatter.centerAlign());
                    Bien.btoutputstream.write(Giaybao.getBytes("X-UTF-16LE-BOM"));
                    writeWithFormat(new Formatter().get(), Formatter.centerAlign());
                    Bien.btoutputstream.write(ngay.getBytes("X-UTF-16LE-BOM"));


                    writeWithFormat(new Formatter().get(), Formatter.leftAlign());
                    Bien.btoutputstream.write(lanin.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(makh.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(madb.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(lbten.getBytes("X-UTF-16LE-BOM"));
                    writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
                    for (int i = 0; i < listhotenmoihang.size(); i++) {
                        if (i == 0) {
                            String hangdau = listhotenmoihang.get(i).toString().substring(lbten.length());
                            Bien.btoutputstream.write(hangdau.getBytes("X-UTF-16LE-BOM"));
                        } else {
                            Bien.btoutputstream.write(listhotenmoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));

                        }
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    }
                    writeWithFormat(new Formatter().get(), Formatter.leftAlign());
                    for (int i = 0; i < listdiachimoihang.size(); i++) {
                        Bien.btoutputstream.write(listdiachimoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    }


                    Bien.btoutputstream.write(haigach.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));

                    for (int ky = 0; ky < listthanhtoan.size(); ky++) {
                        String kyhd = listthanhtoan.get(ky).getKyHD();//spdata.getDataKyHoaDonTrongSP();
                        Log.e("KYHD", kyhd);
                        String kihd = "";
                        if (!kyhd.equals("")) {
                            String nam = kyhd.substring(0, 4);
                            String thang = kyhd.substring(4);
                            String strkyhd = thang + "/" + nam;
                            kihd = "Kỳ hóa đơn: " + strkyhd + "\n";
                        }
                        String chisocu = "Chỉ số cũ: " + listthanhtoan.get(ky).getChiSoCu().trim();//tv_csocu.getText();
                        String chisomoi = "\nChỉ số mới: " + listthanhtoan.get(ky).getChiSoMoi().trim();// tv_csomoi.getText();
                        String m3 = "\nSố m3 tiêu thụ: " + listthanhtoan.get(ky).getSLTieuThu().trim() + " m3";
                        String chitiet = "\nChi tiết: \n";
                        String m3t1 = listthanhtoan.get(ky).getM3t1().trim();
                        double m3t1d = 0, m3t2d = 0, m3t3d = 0, m3t4d = 0, tien1d = 0, tien2d = 0, tien3d = 0, tien4d = 0;
                        if (thanhtoandao.tangSoLanIn(listthanhtoan.get(ky).getBienLai(), 1, 0, 0)) {
                            LichSuDTO ls = new LichSuDTO();
                            ls.setNoiDungLS("In biên nhận thanh toán khách hàng " + DanhBo.getText().toString().trim());
                            ls.setMaLenh("IB");
                            ls.setThoiGianLS(thoigian);
                            lichsudao.addTable_History(ls);

                        }
                        if (!m3t1.equals("")) {
                            m3t1d = Double.parseDouble(m3t1);
                        }
                        String m3t2 = listthanhtoan.get(ky).getM3t2().trim();
                        if (!m3t2.equals("")) {
                            m3t2d = Double.parseDouble(m3t2);
                        }
                        String m3t3 = listthanhtoan.get(ky).getM3t3().trim();
                        if (!m3t3.equals("")) {
                            m3t3d = Double.parseDouble(m3t3);
                        }
                        String m3t4 = listthanhtoan.get(ky).getM3t4().trim();
                        if (!m3t4.equals("")) {
                            m3t4d = Double.parseDouble(m3t4);
                        }

                        String tien1 = listthanhtoan.get(ky).getTien1().trim();
                        if (!tien1.equals("")) {
                            tien1d = Double.parseDouble(tien1);
                        }
                        String tien2 = listthanhtoan.get(ky).getTien2().trim();
                        if (!tien2.equals("")) {
                            tien2d = Double.parseDouble(tien2);
                        }
                        String tien3 = listthanhtoan.get(ky).getTien3().trim();
                        if (!tien3.equals("")) {
                            tien3d = Double.parseDouble(tien3);
                        }
                        String tien4 = listthanhtoan.get(ky).getTien4().trim();
                        if (!tien4.equals("")) {
                            tien4d = Double.parseDouble(tien4);
                        }

                        String dongia1 = "";
                        if (!m3t1.equals("0") && !m3t1.equals("")) {
                            double dongia1d = tien1d / m3t1d;
                            dongia1 = m3t1 + " x " + format.format(dongia1d) + " = " + format2.format(Double.parseDouble(format1.format(tien1d))) + " đ";
                        }

                        String dongia2 = "";
                        if (!m3t2.equals("0") && !m3t2.equals("")) {
                            double dongia2d = tien2d / m3t2d;
                            dongia2 = m3t2 + " x " + format.format(dongia2d) + " = " + format2.format(Double.parseDouble(format1.format(tien2d))) + " đ";
                        }

                        String dongia3 = "";
                        if (!m3t3.equals("0") && !m3t3.equals("")) {
                            double dongia3d = tien3d / m3t3d;
                            dongia3 = m3t3 + " x " + format.format(dongia3d) + " = " + format2.format(Double.parseDouble(format1.format(tien3d))) + " đ";
                        }

                        String dongia4 = "";
                        if (!m3t4.equals("0") && !m3t4.equals("")) {
                            double dongia4d = tien4d / m3t4d;
                            dongia4 = m3t4 + " x " + format.format(dongia4d) + " = " + format2.format(Double.parseDouble(format1.format(tien4d))) + " đ";
                        }

                        double tiennuocd = 0, thued = 0, phid = 0, tongcongd = 0;
                        if (!listthanhtoan.get(ky).getTienNuoc().trim().equals("")) {
                            tiennuocd = Double.valueOf(listthanhtoan.get(ky).getTienNuoc().trim());
                        }

                        if (!listthanhtoan.get(ky).getThue().trim().equals("")) {
                            thued = Double.valueOf(listthanhtoan.get(ky).getThue().trim());
                        }

                        if (!listthanhtoan.get(ky).getphi().trim().equals("")) {
                            phid = Double.valueOf(listthanhtoan.get(ky).getphi().trim());
                        }

                        if (!listthanhtoan.get(ky).gettongcong().trim().equals("")) {
                            tongcongd = Double.valueOf(listthanhtoan.get(ky).gettongcong().trim());
                        }
                        int lenthue = format2.format(Double.parseDouble(format1.format(tiennuocd))).length() - format2.format(Double.parseDouble(format1.format(thued))).length();
                        int lenphi = format2.format(Double.parseDouble(format1.format(tiennuocd))).length() - format2.format(Double.parseDouble(format1.format(phid))).length();

                        String tiennuoc = "Tiền nước: " + format2.format(Double.parseDouble(format1.format(tiennuocd))) + " đ";
                        String thue = "Thuế GTGT(5% TN): ";
//                        if (lenthue > 0) {
//                            for (int i = 0; i < lenthue; i++) {
//                                thue += " ";
//                            }
//                        }
                        thue += format2.format(Double.parseDouble(format1.format(thued))) + " đ";
                        String phi = "Phí NTSH(10% TN): ";
//                        if (lenphi > 0) {
//                            for (int i = 0; i < lenphi; i++) {
//                                phi += " ";
//                            }
//                        }
                        phi += format2.format(Double.parseDouble(format1.format(phid))) + " đ";


                        String tongcong = "Tổng cộng: " + format2.format(Double.parseDouble(format1.format(tongcongd))) + " đ";

                        writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
                        Bien.btoutputstream.write(kihd.getBytes("X-UTF-16LE-BOM"));
                        writeWithFormat(new Formatter().get(), Formatter.leftAlign());
                        Bien.btoutputstream.write(chisocu.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(chisomoi.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(m3.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(chitiet.getBytes("X-UTF-16LE-BOM"));
                        writeWithFormat(new Formatter().get(), Formatter.rightAlign());
                        if (!dongia1.equals("")) {
                            Bien.btoutputstream.write(dongia1.getBytes("X-UTF-16LE-BOM"));
                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        }
                        if (!dongia2.equals("")) {
                            Bien.btoutputstream.write(dongia2.getBytes("X-UTF-16LE-BOM"));
                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        }
                        if (!dongia3.equals("")) {
                            Bien.btoutputstream.write(dongia3.getBytes("X-UTF-16LE-BOM"));
                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        }
                        if (!dongia4.equals("")) {
                            Bien.btoutputstream.write(dongia4.getBytes("X-UTF-16LE-BOM"));
                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        }

                        Bien.btoutputstream.write(gach.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(tiennuoc.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(phi.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(thue.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(gach.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        writeWithFormat(new Formatter().bold().get(), Formatter.rightAlign());
                        Bien.btoutputstream.write(tongcong.getBytes("X-UTF-16LE-BOM"));

                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(haigach.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    }

                    if (listthanhtoan.size() > 1) {
                        writeWithFormat(new Formatter().bold().get(), Formatter.rightAlign());


                        String tongsotienphaitra = "Số tiền phải trả: " + format2.format(Double.parseDouble(format1.format(tongsotien))) + " đ";
                        Bien.btoutputstream.write(tongsotienphaitra.getBytes("X-UTF-16LE-BOM"));
                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));

                    }

                    String nhanvien = "Nhân viên: " + spdata.getDataTenNhanVien();
                    String dienthoai = "Điện thoại: " + spdata.getDataDienThoai();
                    String dienthoaicskh = "CSKH: " + spdata.getDataDienThoaiHuyen();
                    Log.e("dienthoai", dienthoai);
//                    String nhacnho = "Khi có nhu cầu in HĐĐT xin vui lòng truy cập vào trang web www.cskh.tiwaco.com.vn ";
//                    List<String> listnhacnhomoihang = catchuxuongdong(nhacnho);
//                    String lienlac = "Vui lòng liên hệ số điện thoại 0273.3873425 để được giúp đỡ.\n";
//                    List<String> listlienlacmoihang = catchuxuongdong(lienlac);
//                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(nhanvien.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(dienthoai.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(dienthoaicskh.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
                    String ketthuc = "*--*--*";
                    writeWithFormat(new Formatter().get(), Formatter.centerAlign());
                    Bien.btoutputstream.write(ketthuc.getBytes("X-UTF-16LE-BOM"));
                    Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));



                    // writeWithFormat(new Formatter().get(), Formatter.leftAlign());

//                        for (int i = 0; i < listnhacnhomoihang.size(); i++) {
//                            Bien.btoutputstream.write(listnhacnhomoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
//                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
//                        }
//                        writeWithFormat(new Formatter().bold().get(), Formatter.leftAlign());
//                        Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
//
//                        for (int i = 0; i < listlienlacmoihang.size(); i++) {
//                            Bien.btoutputstream.write(listlienlacmoihang.get(i).toString().getBytes("X-UTF-16LE-BOM"));
//                            Bien.btoutputstream.write(xuongdong.getBytes("X-UTF-16LE-BOM"));
//                        }
                    Bien.btoutputstream.write(0x0D);
                    Bien.btoutputstream.flush();

                    //Tăng số lần in


                    if (loai == 1) {
                        if (khachhangDAO.countKhachHangChuaThuTheoDuong(maduong_nhan) > 0) {

                            //Cập nhật Vị trí hiện tại , load lại (xử lý next.performclick)


                            //xu ly Thu truoc hay Thu lui tai day
                            String sothutu = "";
                            if (bienkieuThu == 1) { //lui{
                                sothutu = khachhangDAO.getSTTChuaThuNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                            } else {//truoc
                                sothutu = khachhangDAO.getSTTChuaThuLonNhatNhoHonHienTai(maduong_nhan, STT_HienTai);
                            }
                            Log.e("Thu nuoc, stt", sothutu);
                            if (!sothutu.equals("0") && !maduong_nhan.equals("99")) {

                                STT_HienTai = sothutu;
                                Log.e("STT Hien tai", STT_HienTai);
                                KhachHangDTO khThuke = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaThu(STT_HienTai, maduong_nhan, MaKH.getText().toString().trim());
                                if (khThuke == null) {
                                    setDataForView(sothutu, maduong_nhan, "");
                                } else {
                                    setDataForView(sothutu, maduong_nhan, khThuke.getMaKhachHang());
                                }
                                spdata.luuDataDuongVaSTTDangThuTrongSP(maduong_nhan, sothutu);
                            } else if (sothutu.equals("0") && maduong_nhan.equals("99")) {
                                STT_HienTai = "0";
                                KhachHangDTO khghike = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaghi("0", maduong_nhan, MaKH.getText().toString().trim());
                                if (khghike == null) {
                                    setDataForView(sothutu, maduong_nhan, "");
                                } else {
                                    setDataForView(sothutu, maduong_nhan, khghike.getMaKhachHang());
                                }
                                spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan, "0");
                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                alertDialogBuilder.setMessage("Vẫn còn khách hàng bạn chưa Thu nước, bạn có muốn Thu nước khách hàng này không");
                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        String sothutukhConLai = khachhangDAO.getSTTChuaThuNhoNhat(maduong_nhan);

                                        if (maduong_nhan.equals("99")) {
                                            sothutukhConLai = "0";
                                        }
                                        if (!sothutukhConLai.equals("")) {
                                            STT_HienTai = sothutukhConLai;
                                            bienkieuThu = 1;
                                            MenuItem itemkieuThu = menumain.findItem(R.id.action_kieughi);
                                            itemkieuThu.setIcon(android.R.drawable.ic_media_ff);

                                            setDataForView(sothutukhConLai, maduong_nhan, "");
                                            spdata.luuDataDuongVaSTTDangThuTrongSP(maduong_nhan, sothutukhConLai);
                                        }

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        MainThuActivity.this.finish();

                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }
                        }
                        //KH đã Thu nước xong => cập nhật đường va show dialog
                        else {
                            //cập nhật trạng thái đường đã Thu
                            if (duongDAO.updateDuongDaThu(maduong_nhan)) {
                                //show dialog đã Thu xong..trở về listactivity
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                if (duongDAO.countDuongChuaThu() > 0) {
                                    alertDialogBuilder.setMessage(R.string.main_thunuoc_duongdathuxong);

                                    // thiết lập nội dung cho dialog
                                } else {
                                    alertDialogBuilder.setMessage(R.string.main_thunuoc_duongdathuxongtatca);


                                }
                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  new UpdateThongTinThuNuoc().execute(urlstr);
                                        dialog.dismiss();
                                        MainThuActivity.this.finish();
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            }
                        }


                        //Tu dong luu 50ngươi / lan
                        /*
                        int flagupdate =  spdata.getDataThuUPdateServer();
                        Log.e("flagupdate", String.valueOf(spdata.getDataThuUPdateServer()));
                        if(flagupdate <50 && flagupdate>=0) {
                            int capnhat = flagupdate + 1;
                            spdata.luuDataThuUpdateServer(capnhat);
                            Log.e("flagupdate < 50 sau khi +", String.valueOf(spdata.getDataThuUPdateServer()));
                        }
                        else if(flagupdate==50){
                            Log.e("tu dong luu","OK");

                            //Tự động lưu dữ liệu vào server
                            String urlstr =   getString(R.string.API_UpdateKhachHangDaThu2);
                            new UpdateThongTinThuNuoc().execute(urlstr);
                        }
                        */
                        thanhtoandapter.notifyDataSetChanged();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//        });
//        alertDialogBuilder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//
//
//            }
//        });
//
//
//        AlertDialog alertDialog1 = alertDialogBuilder1.create();
//        // tạo dialog
//        alertDialog1.setCanceledOnTouchOutside(false);
//        alertDialog1.show();


    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING) {

            // if connected with internet

            Toast.makeText(this, connec.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, " Chưa có internet hoặc 3G/4G  ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            Bien.btoutputstream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean writeWithFormat( final byte[] pFormat, final byte[] pAlignment) {
        try {
            // Notify printer it should be printed with given alignment:
            //   Bien.btoutputstream.write(buffer, 0, buffer.length);

            Bien.btoutputstream.write(pAlignment);
            // Notify printer it should be printed in the given format:
            Bien.btoutputstream.write(pFormat);



            return true;
        } catch (IOException e) {
            //Log.e(TAG, "Exception during write", e);
            return false;
        }
    }

    private void printNewLine() {
        try {
            Bien.btoutputstream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<String> catchuxuongdong(String s, String them) {
        s = them + s;
        List<String> listdiachi  = new ArrayList<String>();
        while (s.length() >0)
        {

            int  vitricachdautien  =   s.indexOf(' ');
            if(vitricachdautien != -1) {
                String tudau = s.substring(0, vitricachdautien);
                Log.e("tu dau+" + s, tudau);
                listdiachi.add(tudau);
                s = s.substring(vitricachdautien).trim();
            }
            else{
                listdiachi.add(s);
                s = "";
            }

        }

//
//
//
//        for  (int i  = 0; i<listdiachi.size();i++ )
//        {
//            Log.e("Tu "+i,listdiachi.get(i).toString() +",dai:"+listdiachi.get(i).toString().length() );
//        }
        String chuoimoihang = "";
        int gioihanchuoi = 28;
        List<String> listdiachimoihang = new ArrayList<>();
        for(int  j  =0;j<listdiachi.size();j++)
        {
            if((chuoimoihang.length()+(listdiachi.get(j).toString().trim() +" ").length())<=gioihanchuoi){
                chuoimoihang +=listdiachi.get(j).toString().trim() +" ";
                Log.e("Chuoi moi hang",chuoimoihang +" dài :"+chuoimoihang.length());
            }
            else{
                listdiachimoihang.add(chuoimoihang.trim());
                chuoimoihang= listdiachi.get(j).toString().trim() +" ";
            }

            if(j == listdiachi.size()-1  )
            {

                listdiachimoihang.add(chuoimoihang);
                chuoimoihang ="";
            }
        }

//        for  (int i  = 0; i<listdiachimoihang.size();i++ )
//        {
//            Log.e("Diachi "+i,listdiachimoihang.get(i).toString() +",dai:"+listdiachimoihang.get(i).toString().length() );
//        }

        return listdiachimoihang ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DESTROY", "DESTROY-----------------------------------------------");
//        try {
//            if(Bien.btsocket!= null){
//                Bien.btoutputstream.close();
//                Bien.btsocket.close();
//                Bien.btsocket = null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "onActivityResult-----------------------------------------------");
        try {
            Bien.btsocket = BTDeviceList.getSocket();

            if (Bien.btsocket != null) {
                if(rad_ingiaybao.isChecked()) {
                    print_bt();
                }
                else if(rad_inhd.isChecked()){
                    print_bt_hoadon(loaiinbiennhan);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        this.menumain  = menu;

//        if(khachhangDAO.checkKHDaThu(khachhang.getMaKhachHang()) > 0){
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
                alert.showDialog(MainThuActivity.this, "Chọn nguồn để lưu dữ liệu: ");
            //    MainActivity.this.finish();

                break;
            case R.id.action_search:
                intent = new Intent(MainThuActivity.this, SearchActivity.class);
                startActivity(intent);
                MainThuActivity.this.finish();
                break;
//            case R.id.action_print:
//                Log.e("makh_nhan",MaKH.getText().toString());
//                KhachHangDTO kh = khachhangDAO.getKHTheoMaKH(MaKH.getText().toString());
//                if(!kh.getChiSo().equals("")){
//                    intent = new Intent(MainThuActivity.this, TinhTienInHDActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(Bien.MAKHTHU, khachhang.getMaKhachHang());
//
//
//                    intent.putExtra(Bien.GOITIN_THU, bundle);
//                    startActivity(intent);
//                   // MainActivity.this.finish();
//                }
//                else{
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                    alertDialogBuilder.setMessage("Không thể in vì khách hàng này chưa được Thu nước");
//                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//
//
//                        }
//                    });
//
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    // tạo dialog
//                    alertDialog.setCanceledOnTouchOutside(false);
//                    alertDialog.show();
//                    // hiển thị dialog
//                }
//
//                break;
            case android.R.id.home:

                MainThuActivity.this.finish();
                break;
            case R.id.action_kieughi:

                if(bienkieuThu == 1){ // lui -> toi
                    bienkieuThu = 0;

                    item.setIcon(android.R.drawable.ic_media_rew); //toi
                }
                else{ //toi -> lui
                    bienkieuThu = 1;
                    item.setIcon(android.R.drawable.ic_media_ff); //toi
                }

                break;

            case R.id.action_daghi:

                if(biendaGhiChuaThu == false){
                    biendaGhiChuaThu = true;

                    item.setIcon(android.R.drawable.presence_online); //toi
                }
                else{ //toi -> lui
                    biendaGhiChuaThu = false;
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
        dialogxuly = new ProgressDialog(MainThuActivity.this);
        dialogxuly.setMessage("Đang xử lý...");
        dialogxuly.setCanceledOnTouchOutside(false);


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
        lb_sohoadonno = (TextView) findViewById(R.id.lb_sohoadonno);

            LabelDuong = (TextView) findViewById(R.id.tv_label_duong);
            DuongDangThu= (TextView) findViewById(R.id.tv_duongdangThu);
            ConLai= (TextView) findViewById(R.id.tv_conlai);
            ConLai.setSelected(true);
            DuongDangThu.setSelected(true);
            DienThoai = (EditText) findViewById(R.id.edit_DienThoaiKH);
            ChiSoMoi = (EditText) findViewById(R.id.edit_chisomoi);
            m3moi= (EditText) findViewById(R.id.edit_m3moi);
            ChiSoMoiCon = (EditText) findViewById(R.id.edit_chisomoicon);
            m3conmoi= (EditText) findViewById(R.id.edit_m3moicon);
            TinhTrangTLK = (EditText) findViewById(R.id.edit_tinhtrangTLK);
        GhiChu = (EditText) findViewById(R.id.edit_GhichuThu);

            LoaiKH= (TextView) findViewById(R.id.tv_loaiKH);
            DinhMuc= (TextView) findViewById(R.id.tv_DinhMuc);

        lb_laninTB = (TextView) findViewById(R.id.lb_laninTB);
        lb_laninBN = (TextView) findViewById(R.id.lb_laninBN);
        DinhMuc = (TextView) findViewById(R.id.tv_DinhMuc);
            BinhQuanBaThang = (TextView) findViewById(R.id.tv_binhquan3thang);
            DoiSDT  = (ImageButton) findViewById(R.id.imgbtn_doi);
            Thu = (ImageButton) findViewById(R.id.btn_Thunuoc);
            Toi = (ImageButton) findViewById(R.id.btn_toi);
            Lui = (ImageButton) findViewById(R.id.btn_lui);
        CapNhatGhiChu = (ImageButton) findViewById(R.id.btn_updateGhichuThu);
            ChuyenLoai =(ImageButton) findViewById(R.id.btn_chuyenloai);

            lay_toi =(LinearLayout)findViewById(R.id.layout_toi);
            lay_lui =(LinearLayout)findViewById(R.id.layout_lui);
            lay_Thu =(LinearLayout)findViewById(R.id.layout_Thu);
            lay_ki1 = (LinearLayout)findViewById(R.id.lay_ki1);
            lay_ki2 = (LinearLayout)findViewById(R.id.lay_ki2);
            lay_ki3 = (LinearLayout)findViewById(R.id.lay_ki3);
        thanhtoantiennuoc = (Button) findViewById(R.id.btn_thanhtoantiennuoc);
        inthongbao = (Button) findViewById(R.id.btn_inthongbao);
        ThongTinChiTietHoaDon = (ImageButton) findViewById(R.id.btn_hienthithongtinhd);
        lbthongtinkh = (TextView) findViewById(R.id.lb_thongtinkh);

        thongtinKH = (TextView) findViewById(R.id.tv_thongtinKH);

        thongtinKH.setSelected(true);
        layout_thongtinkh = (TableLayout) findViewById(R.id.layout_thongtinKH);
        // layout_thongtinkh.setVisibility(View.GONE);

            chisocu_con_lb =(TableRow) findViewById(R.id.tableRow_chisocucon_lb);
            chisocu_con =(TableRow) findViewById(R.id.tableRow_chisocucon);

            chisomoi_con_lb =(TableRow) findViewById(R.id.tableRow_chisomoicon_lb);
            chisomoi_con =(TableRow) findViewById(R.id.tableRow_chisomoicon);
        lb_tbhetno = (TextView) findViewById(R.id.lb_thongbaohetno);
        spinTT = (Spinner) findViewById(R.id.spin_tinhtrangtlk);
        tv_csocu = (TextView) findViewById(R.id.tv_chisocu);
        tv_csomoi= (TextView) findViewById(R.id.tv_chisomoi);
        tv_m3 = (TextView) findViewById(R.id.tv_m3);
        tv_tiennuoc = (TextView) findViewById(R.id.tv_tiennuoc);
        tv_thue = (TextView) findViewById(R.id.tv_thue);
        tv_phi= (TextView) findViewById(R.id.tv_phi);
        tv_tongcong = (TextView) findViewById(R.id.tv_tongcong);
        tv_sumofmoney = (TextView) findViewById(R.id.lb_sumofmoney);
        rad_ingiaybao = (RadioButton) findViewById(R.id.rad_ingiaybao);
        rad_inhd = (RadioButton) findViewById(R.id.rad_inhd);
        mgr=(PrintManager)getSystemService(PRINT_SERVICE);
        ListThanhToanTheoKy = (ExpandableListView) findViewById(R.id.ListThanhToanTheoKy);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);

    }




//hàm Thu nước
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

                gps = new GPSTracker(con, MainThuActivity.this);

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


    public void Thunuoc(String trans, String thoigian2) {


        String maKH = MaKH.getText().toString().trim();
        List<ThanhToanDTO> listchuatt = thanhtoandao.GetListThanhToanTheoMaKHChuaThanhToan(maKH);

        String thoigian1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String nhanvienthu = spdata.getDataNhanVienTrongSP();

        // \n is for new line
        //  Toast.makeText(getApplicationContext(), "Thu NUOC:Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        //Log.e("Toa do", vido + "-" + kinhdo);


        if (listchuatt.size() > 0) {
            int sothanhtoan = 0;
            for (int bienlai = 0; bienlai < listchuatt.size(); bienlai++) {
                if (thanhtoandao.updateThanhToanTheoMaKhvaKyHD(maKH, thoigian2, trans, nhanvienthu, listchuatt.get(bienlai).getKyHD())) {
                    Log.e("bienlai", listchuatt.get(bienlai).getBienLai());
                    sothanhtoan++;
                }

            }
            if (sothanhtoan == listchuatt.size()) {

                if (khachhangDAO.updateKhachHangThanhToan(maKH, thoigian2, nhanvienthu)) {
                    setDataForView(STT.getText().toString(), maduong_nhan, MaKH.getText().toString());
                    Toast.makeText(con, "Thu tiền nước thành công", Toast.LENGTH_SHORT).show();
                    LichSuDTO ls = new LichSuDTO();
                    ls.setNoiDungLS("Thu tiền nước đường " + tenduong + ", khách hàng có danh bộ " + DanhBo.getText().toString().trim());
                    ls.setMaLenh("TN");
                    ls.setThoiGianLS(thoigian1);
                    lichsudao.addTable_History(ls);


                    //Hien thi dialog hoi co muon in hoa don hay khong
                    //Co thi in va chuyen sang khach hang chua thu ke tiep
                    //khong thi ko in va chuyen sang khach hang chua thu ke tiep
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                    alertDialogBuilder.setMessage("Thanh toán tiền nước thành công.Bạn có muốn in hóa đơn tiền nước không?");
                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            rad_inhd.setChecked(true);
                            connect(loaiinbiennhan);


                        }
                    });
                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Nếu còn khách hàng chưa Thu -> tiếp tục Thu
                            if (khachhangDAO.countKhachHangChuaThuTheoDuong(maduong_nhan) > 0) {

                                //Cập nhật Vị trí hiện tại , load lại (xử lý next.performclick)

                                //xu ly Thu truoc hay Thu lui tai day
                                String sothutu = "";
                                if (bienkieuThu == 1) { //lui{
                                    sothutu = khachhangDAO.getSTTChuaThuNhoNhatLonHonHienTai(maduong_nhan, STT_HienTai);
                                } else {//truoc
                                    sothutu = khachhangDAO.getSTTChuaThuLonNhatNhoHonHienTai(maduong_nhan, STT_HienTai);
                                }
                                Log.e("Thu nuoc, stt", sothutu);
                                if (!sothutu.equals("0") && !maduong_nhan.equals("99")) {

                                    STT_HienTai = sothutu;
                                    Log.e("STT Hien tai", STT_HienTai);
                                    KhachHangDTO khThuke = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaThu(STT_HienTai, maduong_nhan, MaKH.getText().toString().trim());
                                    if (khThuke == null) {
                                        setDataForView(sothutu, maduong_nhan, "");
                                    } else {
                                        setDataForView(sothutu, maduong_nhan, khThuke.getMaKhachHang());
                                    }
                                    spdata.luuDataDuongVaSTTDangThuTrongSP(maduong_nhan, sothutu);
                                } else if (sothutu.equals("0") && maduong_nhan.equals("99")) {
                                    STT_HienTai = "0";
                                    KhachHangDTO khghike = khachhangDAO.getKHTheoSTT_Duong_khacmaKH_chuaghi("0", maduong_nhan, MaKH.getText().toString().trim());
                                    if (khghike == null) {
                                        setDataForView(sothutu, maduong_nhan, "");
                                    } else {
                                        setDataForView(sothutu, maduong_nhan, khghike.getMaKhachHang());
                                    }
                                    spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan, "0");
                                } else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    alertDialogBuilder.setMessage("Vẫn còn khách hàng bạn chưa Thu nước, bạn có muốn Thu nước khách hàng này không");
                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            String sothutukhConLai = khachhangDAO.getSTTChuaThuNhoNhat(maduong_nhan);

                                            if (maduong_nhan.equals("99")) {
                                                sothutukhConLai = "0";
                                            }
                                            if (!sothutukhConLai.equals("")) {
                                                STT_HienTai = sothutukhConLai;
                                                bienkieuThu = 1;
                                                MenuItem itemkieuThu = menumain.findItem(R.id.action_kieughi);
                                                itemkieuThu.setIcon(android.R.drawable.ic_media_ff);

                                                setDataForView(sothutukhConLai, maduong_nhan, "");
                                                spdata.luuDataDuongVaSTTDangThuTrongSP(maduong_nhan, sothutukhConLai);
                                            }

                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            MainThuActivity.this.finish();

                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                    // hiển thị dialog
                                }
                            }
                            //KH đã Thu nước xong => cập nhật đường va show dialog
                            else {
                                //cập nhật trạng thái đường đã Thu
                                if (duongDAO.updateDuongDaThu(maduong_nhan)) {
                                    //show dialog đã Thu xong..trở về listactivity
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    if (duongDAO.countDuongChuaThu() > 0) {
                                        alertDialogBuilder.setMessage(R.string.main_thunuoc_duongdathuxong);

                                        // thiết lập nội dung cho dialog
                                    } else {
                                        alertDialogBuilder.setMessage(R.string.main_thunuoc_duongdathuxongtatca);


                                    }
                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  new UpdateThongTinThuNuoc().execute(urlstr);
                                            dialog.dismiss();
                                            MainThuActivity.this.finish();
                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                    // hiển thị dialog
                                }
                            }

                            hideKeyboard(MainThuActivity.this);
                            //Tu dong luu 50ngươi / lan
                        /*
                        int flagupdate =  spdata.getDataThuUPdateServer();
                        Log.e("flagupdate", String.valueOf(spdata.getDataThuUPdateServer()));
                        if(flagupdate <50 && flagupdate>=0) {
                            int capnhat = flagupdate + 1;
                            spdata.luuDataThuUpdateServer(capnhat);
                            Log.e("flagupdate < 50 sau khi +", String.valueOf(spdata.getDataThuUPdateServer()));
                        }
                        else if(flagupdate==50){
                            Log.e("tu dong luu","OK");

                            //Tự động lưu dữ liệu vào server
                            String urlstr =   getString(R.string.API_UpdateKhachHangDaThu2);
                            new UpdateThongTinThuNuoc().execute(urlstr);
                        }
                        */
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    // hiển thị dialog
                } else {
                    Toast.makeText(con, "Thu tiền nước thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(con, "Thu tiền nước thất bại", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private ListRequestObjectThu taoJSONData_KH_DaThu_CapNhatServer2(String tendanhsach) {
        ListRequestObjectThu jsondata = new ListRequestObjectThu();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuongThu> listtiwaread = new ArrayList<ListKHTheoDuongThu>();
        String soluongKH = String.valueOf(khachhangDAO.countKhachHangDaThuServer());
        listduong = duongDAO.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObjectThu> listkh = new ArrayList<RequestObjectThu>();
            listkh = khachhangDAO.getAllKHDaThuTheoDuongChuaCapNhat1(maduong);
            ListKHTheoDuongThu tiwaread = new ListKHTheoDuongThu();
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
            // spdata.luuDataFlagBKDaThuTrongSP(-1);
            // Bien.bienbkdg = -1;
            jsondata = null;

        }
        return jsondata;
    }
    public class UpdateThongTinThuNuoc extends AsyncTask<String, String, String>
    {
        String status ="";
        String kyhd = spdata.getDataKyHoaDonTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
        String result ="";
        //ListJsonData jsondata = new ListJsonData();
        ListRequestObjectThu jsondata = new ListRequestObjectThu();

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
                    final URL url = new URL(connUrl[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setChunkedStreamingMode(0);
                    conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                    conn.setRequestMethod("PUT");
                    publishProgress("Lấy thông tin khách hàng...");
                    //jsondata = taoJSONData_KH_DaThu_CapNhatServer(kyhd);

                        jsondata = taoJSONData_KH_DaThu_CapNhatServer2(kyhd);




                    // JSONListTiwaread requestjson  = new JSONListTiwaread();
                    //  requestjson.setJsontiwaread(jsondata);


                    JSONRequestObjectThu requestjson = new JSONRequestObjectThu();
                    requestjson.setJsontiwaread(jsondata);

                    Gson gson = new Gson();
                    json = gson.toJson(requestjson);
                    Log.e("json gui", json);
                    //  json = taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
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
                    if (objstt.has("CapNhatThuTienNuocNongThonResult")) {

                        result = String.valueOf(objstt.getString("CapNhatThuTienNuocNongThonResult"));

                        Log.e("CapNhatThuTienNuocNongThonResult", result);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("loi tra ve ne ", e.toString());
                    result = "0";
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

            if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật
                spdata.luuDataThuUpdateServer(0);
//                if(jsondata.getListTiwaread().size() >0) {
//                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
//                        for (RequestObject kh : lista.getTiwareadList()) {
//                            if (khachhangDAO.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
//                                //capnhattrangthai++;
//                            }
//                        }
//                    }
//                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình cập nhật dữ liệu");
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

                        spdata.luuDataThuUpdateServer(0);
                        Toast.makeText(MainThuActivity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_LONG).show();
                        for (ListKHTheoDuongThu lista : jsondata.getListTiwaread()) {
                            for (RequestObjectThu kh : lista.getTiwareadList()) {
                                if (khachhangDAO.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "3")) {
                                    //capnhattrangthai++;
                                }
                            }
                        }
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                        // khởi tạo dialog
//                        alertDialogBuilder.setMessage("Cật nhật dữ liệu thành công "+ String.valueOf(soluongkhachhangdacapnhat) +"/"+ String.valueOf(jsondata.getTongSLkh()));
//                        // thiết lập nội dung cho dialog
//
//                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//
//                                // button "no" ẩn dialog đi
//                            }
//                        });
//
//
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        // tạo dialog
//                        alertDialog.setCanceledOnTouchOutside(false);
//                        alertDialog.show();


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
//                                        if (khachangdao.updateTrangThaiThuCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                    }

                                }
                                if(DanhSachLoi.size() >0){
                                    for(String ma : DanhSachLoi){
                                        if (khachhangDAO.updateTrangThaiThuCapNhat(ma, "2")) {
                                            capnhatlaichuaupdate++;
                                        }
                                    }
                                }
//                                if(jsondata.getListTiwaread().size()>0) {
//                                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
//                                        for (RequestObject kh : lista.getTiwareadList()) {
//                                            if (khachangdao.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
//                                                capnhatlaichuaupdate++;
//                                            }
//                                        }
//                                    }
//                                }
                                // intent.putExtra("mylist", myList);
                                if (capnhatlaichuaupdate == DanhSachLoi.size()) {
                                    spdata.luuDataThuUpdateServer(0);
                                    //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                    Toast.makeText(MainThuActivity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();
                                    //   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                                    // khởi tạo dialog
//                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
//                                    // thiết lập nội dung cho dialog
//
//                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//
//                                            // button "no" ẩn dialog đi
//                                        }
//                                    });
//                                    alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//
//
//                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainThuActivity.this);
//                                            LayoutInflater inflater = getLayoutInflater();
//                                            View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
//                                            alertDialog.setView(convertView);
//                                            alertDialog.setTitle("Danh sách lỗi");
//                                            ListView lv = (ListView) convertView.findViewById(R.id.lv);
//                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainThuActivity.this,android.R.layout.simple_list_item_1,myListerror);
//                                            lv.setAdapter(adapter);
//                                            alertDialog.show();
//                                            // button "no" ẩn dialog đi
//                                        }
//                                    });
//
//
//                                    AlertDialog alertDialog = alertDialogBuilder.create();
//                                    // tạo dialog
//                                    alertDialog.setCanceledOnTouchOutside(false);
//                                    alertDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                int capnhatlaidanhsachjson =0;
                                spdata.luuDataThuUpdateServer(0);
                                if(jsondata.getListTiwaread().size()>0) {
                                    for (ListKHTheoDuongThu lista : jsondata.getListTiwaread()) {
                                        for (RequestObjectThu kh : lista.getTiwareadList()) {
                                            if (khachhangDAO.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "2")) {
                                                capnhatlaidanhsachjson++;
                                            }
                                        }
                                    }
                                }

                                if (capnhatlaidanhsachjson == Integer.parseInt(jsondata.getTongSLkh())) {
//                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
//                                    // khởi tạo dialog
//                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
//                                    // thiết lập nội dung cho dialog
//
//                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//
//                                            // button "no" ẩn dialog đi
//                                        }
//                                    });

                                }
                            }

                        }

                    }
                }
                catch(Exception e){
                    spdata.luuDataThuUpdateServer(0);
                }
            }

        }



    }








private void showDiaLogThongBao(String mess){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
    // khởi tạo dialog

    alertDialogBuilder.setMessage(mess);

    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            resetViewThuNuoc();
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
private void  resetViewThuNuoc(){
  //  DienThoai.setText("");
   // TinhTrangTLK.setText("");
   // spinTT.setSelection(0);
    ChiSoMoi.setText("");
    ChiSoMoiCon.setText("");
    m3moi.setText("");
    m3conmoi.setText("");
  //  GhiChu.setText("");
}

public boolean KiemTraDaThu(String maKH){
    KhachHangDTO kh = khachhangDAO.getKHTheoMaKH(maKH);
 //   Log.e("thong tin chi so", "Chi so db:" + kh.getChiSo() +  "  Chi so man hinh: "+ChiSoMoi.getText().toString().trim() );
  //  Log.e("Ten tt",tenTT);


    if(kh.getTrangThaiTLK().equals("")) {
        Log.e("KT dt", kh.getDienThoai()+     kh.getDienThoai().equals(DienThoai.getText().toString().trim()));
        Log.e("KT getChiSo", kh.getChiSo()+      kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim() ));
        Log.e("KT sltieuthu",kh.getSLTieuThu() +   kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()));
        Log.e("KT chisocon", kh.getChiSocon()+  kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()));
        Log.e("KT GhiChu",kh.getGhiChu()+    kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()));
        if(kh.getDienThoai().equals(DienThoai.getText().toString().trim())  &&
                // kh.getTrangThaiTLK().equals(TinhTrangTLK.getText().toString().trim()) &&
                kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim()) &&
               // kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()) &&
                kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()) &&
                kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()))
        {
            Log.e("Da Thu ko co bat thuong", "true");
            return true;
        }
        else {
            Log.e("Chua Thu ko co bat thuong", "true");
            return false;
        }
    }
    else{
        Log.e("KT dt", kh.getDienThoai()+     kh.getDienThoai().equals(DienThoai.getText().toString().trim()));
        Log.e("Ten getTrangThaiTLK",kh.getTrangThaiTLK()+ kh.getTrangThaiTLK().trim().equalsIgnoreCase(tenTT.trim()));
        Log.e("KT getChiSo", kh.getChiSo()+      kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim() ));
        Log.e("KT sltieuthu",kh.getSLTieuThu() + ".san luong :"+ m3moi.getText().toString().trim() +" "+  kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()));
        Log.e("KT chisocon", kh.getChiSocon()+  kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()));
        Log.e("KT GhiChu",kh.getGhiChu()+    kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()));
        if(kh.getDienThoai().equalsIgnoreCase(DienThoai.getText().toString().trim())  &&
                // kh.getTrangThaiTLK().equals(TinhTrangTLK.getText().toString().trim()) &&

            //    kh.getTrangThaiTLK().trim().equalsIgnoreCase(tenTT.trim()) &&
                kh.getChiSo().trim().equalsIgnoreCase(ChiSoMoi.getText().toString().trim()) &&
          //      kh.getSLTieuThu().trim().equalsIgnoreCase(m3moi.getText().toString().trim()) &&
                kh.getChiSocon().trim().equalsIgnoreCase(ChiSoMoiCon.getText().toString().trim()) &&
                kh.getGhiChu().trim().equalsIgnoreCase(GhiChu.getText().toString().trim()))
        {
            Log.e("Da Thu co bat thuong", "true");
            return true;
        }
        else {
            Log.e("Chua Thu co bat thuong", "true");
            return false;
        }
    }

}

    public boolean KiemTraDaThuChiSo(String maKH) {
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
        alertDialogBuilder.setMessage("GPS đã bị tắt.Hãy bật GPS để có thể Thu nước.")
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

    //param custNo, username, password, requestTime
    public class KiemTraThongTinThuTienNuoc extends AsyncTask<String, String, String> {
        String status = "";
        String kyhd = spdata.getDataKyHoaDonTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
        String result = "";
        //ListJsonData jsondata = new ListJsonData();
        RequestGetBillInfoDTO jsondata = new RequestGetBillInfoDTO();
        String transactionID = "";

        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn = null;

            //Get Danh Sach Duong khoa so

            String fileContent = "";
            publishProgress("WAIT");


            try {

                String manvthu = spdata.getDataIDNhanVien();
                String manvmoi = "";
                if (manvthu.trim().length() == 1) {
                    manvmoi = "00" + manvthu;
                } else if (manvthu.trim().length() == 2) {
                    manvmoi = "0" + manvthu;
                } else {
                    manvmoi = manvthu;
                }
                transactionID = thanhtoandao.taoTransactionID(manvmoi, MaKH.getText().toString().trim());
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");
                publishProgress("Lấy thông tin khách hàng...");
                int summoney = 0;
                List<PeriodDTO> listbillsend = new ArrayList<>();
                List<ThanhToanDTO> listbill = thanhtoandao.GetListThanhToanTheoMaKH(MaKH.getText().toString().trim());
                for (ThanhToanDTO bill : listbill) {
                    PeriodDTO p = new PeriodDTO();
                    p.setBillNo(bill.getBienLai());
                    p.setPeriodNum(bill.getKyHD());
                    p.setTotalMoney(Integer.parseInt(bill.gettongcong()));
                    listbillsend.add(p);
                    summoney += Integer.parseInt(bill.gettongcong());
                }
                jsondata.setCustNo(MaKH.getText().toString().trim());
                jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                jsondata.setTransactionID(transactionID);
                jsondata.setSumOfTotalMoney(summoney);
                jsondata.setPaymentChannel("GT");
                jsondata.setPeriodNums(listbillsend);
                jsondata.setKyhd(spdata.getDataKyHoaDonTrongSP());
                String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                jsondata.setRequestTime(thoigian2);


                Gson gson = new Gson();
                json = gson.toJson(jsondata);
                Log.e("json gui", json);
                //  json = taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
                publishProgress("Đang cập nhật server...");
                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(json.getBytes());
                out.flush();
                out.close();


                int resultint = conn.getResponseCode();
                if (resultint == 200) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        result = line;
                    }
                    reader.close();
                }
                publishProgress("DONE");

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("loi ne ", ex.toString());
                result = "0";
            }


            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            if (status.equals("WAIT")) {
                dialogxuly.show();
            } else if (status.equals("DONE")) {
                dialogxuly.dismiss();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result get data", result);
            int returncode = 0;
            JSONObject jsonobj = null;
//            try {
//                jsonobj = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình lấy dữ liệu hóa đơn");
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
                    if (jsonobj.has("ResponseCode")) {


                        try {
                            returncode = jsonobj.getInt("ResponseCode");
                            if (returncode == -2) {
                                //Sai ten tài khoản và mật khẩu
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Tên tài khoản và mật khẩu không hợp lệ. Hãy đăng nhập lại hoặc liên hệ với IT để giải quyết lỗi");
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
                            } else if (returncode == -1) {
                                //sai ma kh
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Sai mã khách hàng. Liên hệ với IT để giải quyết");
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
                            } else if (returncode == 1) {
                                //Kiểm tra nếu ngaythu == jsonngaythu thì ko cập nhật
                                final JSONObject finalJsonobj = jsonobj;
                                // button "no" ẩn dialog đi
                                Log.e("kết qua trung", "da ton tai  hoa don");
                                String maKHTraVe = "";
                                JSONArray arrayDaThu = null;
                                List<String> listnhanvienthu = new ArrayList<>();
                                if (finalJsonobj.has("CustNo")) {
                                    try {
                                        maKHTraVe = finalJsonobj.getString("CustNo");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (finalJsonobj.has("PeriodNums")) {
                                    try {
                                        arrayDaThu = finalJsonobj.getJSONArray("PeriodNums");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    for (int i = 0; i < arrayDaThu.length(); i++) {
                                        String thoigianthutrave = "", nhanvienthutrave = "", kyhdtrave = "", transactionidtrave = "";
                                        try {
                                            if (arrayDaThu.getJSONObject(i).has("ThoiGianThu")) {
                                                thoigianthutrave = arrayDaThu.getJSONObject(i).getString("ThoiGianThu");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (arrayDaThu.getJSONObject(i).has("NhanVienThu")) {
                                                nhanvienthutrave = arrayDaThu.getJSONObject(i).getString("NhanVienThu");
                                                if (!listnhanvienthu.contains(nhanvienthutrave)) {
                                                    listnhanvienthu.add(nhanvienthutrave);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (arrayDaThu.getJSONObject(i).has("PeriodNum")) {
                                                kyhdtrave = arrayDaThu.getJSONObject(i).getString("PeriodNum");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (arrayDaThu.getJSONObject(i).has("TransactionID")) {
                                                transactionidtrave = arrayDaThu.getJSONObject(i).getString("TransactionID");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.e("trave", thoigianthutrave + " " + nhanvienthutrave + " " + kyhdtrave + " " + transactionidtrave + " ");
                                        //Lấy danh sách thanhtoan theo cust va kyhd.so sanh ngaythu

                                        //KhachHangDTO khlist = khachhangDAO.getKHTheoMaKH(maKHTraVe);
                                        // Log.e("nhanvienthuhd",khlist.getNhanvienthu());
                                        // if(!khlist.getNhanvienthu().equals(nhanvienthutrave)) {
                                        boolean kt = khachhangDAO.updateKhachHangThanhToan(maKHTraVe, thoigianthutrave, nhanvienthutrave);
                                        if (kt) {
                                            List<ThanhToanDTO> billlist = thanhtoandao.GetThanhToanTheoKyHDVaMaKH(maKHTraVe, kyhdtrave);
                                            for (int j = 0; j < billlist.size(); j++) {
                                                Log.e("billlist.get(j).getTransactionID()", billlist.get(j).getTransactionID());
                                                Log.e("billlist.get(j).getNhanvienthu()", billlist.get(j).getNhanvienthu());
                                                if (billlist.get(j).getTransactionID().equals(transactionidtrave) == false && billlist.get(j).getNhanvienthu().equals(nhanvienthutrave) == false) {
                                                    thanhtoandao.updateThanhToanTheoMaKhvaKyHD(maKHTraVe, thoigianthutrave, transactionidtrave, nhanvienthutrave, kyhdtrave);

                                                }

                                            }
                                        }
                                        // }


                                    }
                                    //ko có nợ
                                    setDataForView(STT.getText().toString(), maduong_nhan, MaKH.getText().toString());

                                }

                                if (listnhanvienthu.contains(spdata.getDataNhanVienTrongSP())) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Khách hàng này không tồn tại hóa đơn nợ hoặc đã thanh toán hết nợ.Bạn có muốn in biên nhận thanh toán không?");
                                    // thiết lập nội dung cho dialog


                                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();


                                        }
                                    });
                                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            rad_inhd.setChecked(true);
                                            rad_ingiaybao.setChecked(false);
                                            loaiinbiennhan = 0;
                                            connect(loaiinbiennhan);

                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Khách hàng này không tồn tại hóa đơn nợ hoặc đã thanh toán hết nợ");
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


                            } else if (returncode == 0) { //thanh toán thành công, cập nhật lai trong hệ thống
                                //có ds nợ
                                loaiinbiennhan = 1;
                                String trans = "";
                                if (jsonobj.has("ResponseDesc")) {
                                    trans = jsonobj.getString("ResponseDesc");
                                }

                                String responsedate = "";
                                if (jsonobj.has("ResponseDate")) {
                                    responsedate = jsonobj.getString("ResponseDate");
                                }

                                Thunuoc(trans, responsedate);
                            } else if (returncode == -3) {
                                //Lỗi hệ thống
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Lỗi hệ thống: Không thể lấy dữ liệu hóa đơn.Hãy thử lại sau");
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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                } catch (Exception e) {

                }
            }

        }


    }

    public class CheckBillNB extends AsyncTask<String, String, String> {
        String status = "";
        String kyhd = spdata.getDataKyHoaDonTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
        String result = "";
        //ListJsonData jsondata = new ListJsonData();
        RequestGetBillInfoDTO jsondata = new RequestGetBillInfoDTO();
        String transactionID = "";

        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn = null;

            //Get Danh Sach Duong khoa so

            String fileContent = "";
            publishProgress("WAIT");


            try {

                String manvthu = spdata.getDataIDNhanVien();
                String manvmoi = "";
                if (manvthu.trim().length() == 1) {
                    manvmoi = "00" + manvthu;
                } else if (manvthu.trim().length() == 2) {
                    manvmoi = "0" + manvthu;
                } else {
                    manvmoi = manvthu;
                }
                transactionID = thanhtoandao.taoTransactionID(manvmoi, MaKH.getText().toString().trim());
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");
                publishProgress("Lấy thông tin khách hàng...");
                int summoney = 0;
                List<PeriodDTO> listbillsend = new ArrayList<>();
                List<ThanhToanDTO> listbill = thanhtoandao.GetListThanhToanTheoMaKH(MaKH.getText().toString().trim());
                for (ThanhToanDTO bill : listbill) {
                    PeriodDTO p = new PeriodDTO();
                    p.setBillNo(bill.getBienLai());
                    p.setPeriodNum(bill.getKyHD());
                    p.setTotalMoney(Integer.parseInt(bill.gettongcong()));
                    listbillsend.add(p);
                    summoney += Integer.parseInt(bill.gettongcong());
                }
                jsondata.setCustNo(MaKH.getText().toString().trim());
                jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                jsondata.setTransactionID(transactionID);
                jsondata.setSumOfTotalMoney(summoney);
                jsondata.setPaymentChannel("GT");
                jsondata.setKyhd(spdata.getDataKyHoaDonTrongSP());
                jsondata.setPeriodNums(listbillsend);
                String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                jsondata.setRequestTime(thoigian2);


                Gson gson = new Gson();
                json = gson.toJson(jsondata);
                Log.e("json gui", json);
                //  json = taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
                publishProgress("Đang cập nhật server...");
                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(json.getBytes());
                out.flush();
                out.close();


                int resultint = conn.getResponseCode();
                if (resultint == 200) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        result = line;
                    }
                    reader.close();
                }
                publishProgress("DONE");

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("loi ne ", ex.toString());
                result = "0";
            }


            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            if (status.equals("WAIT")) {
                dialogxuly.show();
            } else if (status.equals("DONE")) {
                dialogxuly.dismiss();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result get data", result);
            int returncode = 0;
            JSONObject jsonobj = null;
//            try {
//                jsonobj = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình lấy dữ liệu hóa đơn");
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
                    if (jsonobj.has("ResponseCode")) {


                        try {
                            returncode = jsonobj.getInt("ResponseCode");
                            if (returncode == -2) {
                                //Sai ten tài khoản và mật khẩu
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Tên tài khoản và mật khẩu không hợp lệ. Hãy đăng nhập lại hoặc liên hệ với IT để giải quyết lỗi");
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
                            } else if (returncode == -1) {
                                //sai ma kh
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Sai mã khách hàng. Liên hệ với IT để giải quyết");
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
                                if (returncode == 1) {
                                    //Kiểm tra nếu ngaythu == jsonngaythu thì ko cập nhật

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Khách hàng này không tồn tại hóa đơn nợ hoặc đã thanh toán hết nợ");
                                    // thiết lập nội dung cho dialog

                                    final JSONObject finalJsonobj = jsonobj;
                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            // button "no" ẩn dialog đi
                                            Log.e("kết qua trung", "da ton tai  hoa don");
                                            String maKHTraVe = "";
                                            JSONArray arrayDaThu = null;
                                            if (finalJsonobj.has("CustNo")) {
                                                try {
                                                    maKHTraVe = finalJsonobj.getString("CustNo");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            if (finalJsonobj.has("PeriodNums")) {
                                                try {
                                                    arrayDaThu = finalJsonobj.getJSONArray("PeriodNums");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                for (int i = 0; i < arrayDaThu.length(); i++) {
                                                    String thoigianthutrave = "", nhanvienthutrave = "", kyhdtrave = "", transactionidtrave = "";
                                                    try {
                                                        if (arrayDaThu.getJSONObject(i).has("ThoiGianThu")) {
                                                            thoigianthutrave = arrayDaThu.getJSONObject(i).getString("ThoiGianThu");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        if (arrayDaThu.getJSONObject(i).has("NhanVienThu")) {
                                                            nhanvienthutrave = arrayDaThu.getJSONObject(i).getString("NhanVienThu");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        if (arrayDaThu.getJSONObject(i).has("PeriodNum")) {
                                                            kyhdtrave = arrayDaThu.getJSONObject(i).getString("PeriodNum");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        if (arrayDaThu.getJSONObject(i).has("TransactionID")) {
                                                            transactionidtrave = arrayDaThu.getJSONObject(i).getString("TransactionID");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("trave", thoigianthutrave + " " + nhanvienthutrave + " " + kyhdtrave + " " + transactionidtrave + " ");
                                                    //Lấy danh sách thanhtoan theo cust va kyhd.so sanh ngaythu
                                                    KhachHangDTO khlist = khachhangDAO.getKHTheoMaKH(maKHTraVe);
                                                    Log.e("nhanvienthuhd", khlist.getNhanvienthu());
                                                    // if (!khlist.getNhanvienthu().equals(nhanvienthutrave)) {
                                                    boolean kt = khachhangDAO.updateKhachHangThanhToan(maKHTraVe, thoigianthutrave, nhanvienthutrave);
                                                    if (kt) {
                                                        List<ThanhToanDTO> billlist = thanhtoandao.GetThanhToanTheoKyHDVaMaKH(maKHTraVe, kyhdtrave);
                                                        for (int j = 0; j < billlist.size(); j++) {
                                                            Log.e("billlist.get(j).getTransactionID()", billlist.get(j).getTransactionID());
                                                            Log.e("billlist.get(j).getNhanvienthu()", billlist.get(j).getNhanvienthu());
                                                            if (billlist.get(j).getTransactionID().equals(transactionidtrave) == false && billlist.get(j).getNhanvienthu().equals(nhanvienthutrave) == false) {
                                                                thanhtoandao.updateThanhToanTheoMaKhvaKyHD(maKHTraVe, thoigianthutrave, transactionidtrave, nhanvienthutrave, kyhdtrave);

                                                            }

                                                        }
                                                    }
                                                    // }
                                                }

                                                //ko có nợ


                                            }
                                            setDataForView(STT.getText().toString(), maduong_nhan, MaKH.getText().toString());
                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();


                                } else if (returncode == 0) {  //chua thanh toan
                                    //có ds nợ
                                    //loaiinbiennhan = 1;
                                    //In giấy báo
                                    rad_inhd.setChecked(false);
                                    rad_ingiaybao.setChecked(true);
                                    connect(loaiinbiennhan);
//                                String trans = "";
//                                if (jsonobj.has("ResponseDesc")) {
//                                    trans = jsonobj.getString("ResponseDesc");
//                                }
//
//                                String responsedate = "";
//                                if (jsonobj.has("ResponseDate")) {
//                                    responsedate = jsonobj.getString("ResponseDate");
//                                }
//
//                                Thunuoc(trans, responsedate);
                                } else if (returncode == -3) {
                                    //Lỗi hệ thống
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Lỗi hệ thống: Không thể lấy dữ liệu hóa đơn.Hãy thử lại sau");
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
                                } else if (returncode == 4) {
                                    //Lỗi hệ thống
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Dữ liệu không trùng khớp với máy chủ");
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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                } catch (Exception e) {

                }
            }

        }


    }

    public void paybillall() {
        try {
            if (isInternetOn()) {

                String urlgetBill = getString(R.string.API_PayBillNB);
                KiemTraThongTinThuTienNuoc ask = new KiemTraThongTinThuTienNuoc();
                ask.execute(urlgetBill);

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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


    public void checkbill() {
        try {
            if (isInternetOn()) {

                String urlgetBill = getString(R.string.API_GetBillInfoNB);
                CheckBillNB ask = new CheckBillNB();
                ask.execute(urlgetBill);

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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

    public void capnhatDTVaGhiChu() {
        try {
            if (isInternetOn()) {

                String urlgetBill = getString(R.string.API_UpDatePhoneAndNote);
                CapNhatGhiChuVaSoDienThoai ask = new CapNhatGhiChuVaSoDienThoai();
                ask.execute(urlgetBill);

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
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


    public class CapNhatGhiChuVaSoDienThoai extends AsyncTask<String, String, String> {


        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
        String result = "";
        //ListJsonData jsondata = new ListJsonData();
        JSONUpdateThongTinKH jsondata = new JSONUpdateThongTinKH();


        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn = null;

            //Get Danh Sach Duong khoa so

            String fileContent = "";
            publishProgress("WAIT");


            try {


                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");
                publishProgress("Lấy thông tin khách hàng...");

                jsondata.setCustNo(MaKH.getText().toString().trim());
                jsondata.setPaymentNote(GhiChu.getText().toString().trim());
                jsondata.setPhoneNumber(DienThoai.getText().toString().trim());
                jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                jsondata.setRequestTime(thoigian2);

                Gson gson = new Gson();
                json = gson.toJson(jsondata);
                Log.e("json gui", json);
                //  json = taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
                publishProgress("Đang cập nhật server...");
                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(json.getBytes());
                out.flush();
                out.close();


                int resultint = conn.getResponseCode();
                if (resultint == 200) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        result = line;
                    }
                    reader.close();
                }
                publishProgress("DONE");

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("loi ne ", ex.toString());
                result = "0";
            }


            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            if (status.equals("WAIT")) {
                dialogxuly.show();
            } else if (status.equals("DONE")) {
                dialogxuly.dismiss();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result get data", result);
            int returncode = 0;
            JSONObject jsonobj = null;
//            try {
//                jsonobj = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình lấy dữ liệu hóa đơn");
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
                    if (jsonobj.has("ResponseCode")) {


                        try {
                            returncode = jsonobj.getInt("ResponseCode");
                            if (returncode == -2) {
                                //Sai ten tài khoản và mật khẩu
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Tên tài khoản và mật khẩu không hợp lệ. Hãy đăng nhập lại hoặc liên hệ với IT để giải quyết lỗi");
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
                            } else if (returncode == -1) {
                                //sai ma kh
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Sai mã khách hàng. Liên hệ với IT để giải quyết");
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

                            } else if (returncode == 0) { //thanh toán thành công, cập nhật lai trong hệ thống
                                //Thành công

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Cập nhật dữ liệu thành công");
                                // thiết lập nội dung cho dialog

                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        khachhangDAO.updateDienThoai(MaKH.getText().toString().trim(), DienThoai.getText().toString().trim());
                                        khachhangDAO.updateGhiChuThu(MaKH.getText().toString().trim(), GhiChu.getText().toString().trim());
                                        KhachHangDTO khachhangup = khachhangDAO.getKHTheoMaKH(MaKH.getText().toString().trim());
                                        GhiChu.setText(khachhangup.getGhichuthu().trim());
                                        DienThoai.setText(khachhangup.getDienThoai().trim());
                                        // button "no" ẩn dialog đi
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();

                            } else if (returncode == -3) {
                                //Lỗi hệ thống
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainThuActivity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Lỗi hệ thống: Không thể lấy dữ liệu hóa đơn.Hãy thử lại sau");
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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                } catch (Exception e) {

                }
            }

        }


    }


}

