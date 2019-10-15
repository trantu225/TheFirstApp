package tiwaco.thefirstapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import circleprogress.DonutProgress;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tiwaco.thefirstapp.CustomAdapter.APIService;
import tiwaco.thefirstapp.CustomAdapter.ApiUtils;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.BillTamThu;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.JSONBACKUPTHU;
import tiwaco.thefirstapp.DTO.JSONDUONGTHU;
import tiwaco.thefirstapp.DTO.JSONKHTHU;
import tiwaco.thefirstapp.DTO.JSONListTiwaread;
import tiwaco.thefirstapp.DTO.JSONRequestObject;
import tiwaco.thefirstapp.DTO.JSONRequestObjectThu;
import tiwaco.thefirstapp.DTO.JSONTHANHTOANTHU;
import tiwaco.thefirstapp.DTO.JSONTHU;
import tiwaco.thefirstapp.DTO.JSONTHUTHEOUSERNAME;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.LOGNBDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ListJsonData;
import tiwaco.thefirstapp.DTO.ListKHTheoDuong;
import tiwaco.thefirstapp.DTO.ListKHTheoDuongThu;
import tiwaco.thefirstapp.DTO.ListRequestObject;
import tiwaco.thefirstapp.DTO.ListRequestObjectThu;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
import tiwaco.thefirstapp.DTO.ObjectThu;
import tiwaco.thefirstapp.DTO.PeriodDTO;
import tiwaco.thefirstapp.DTO.RequestGetBillInfoDTO;
import tiwaco.thefirstapp.DTO.RequestObject;
import tiwaco.thefirstapp.DTO.RequestObjectThu;
import tiwaco.thefirstapp.DTO.RequestTamThu;
import tiwaco.thefirstapp.DTO.ResponePayBillAllNB;
import tiwaco.thefirstapp.DTO.ResponePayTamThu;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.File.XuLyFile;

public class Backup_Activity extends AppCompatActivity  {
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final String THUMUCBACKUP = "BACKUPTIWAREAD";
    private static final String THUMUCTATCA = "TATCA";
    private static final String THUMUCDAGHI = "DAGHI";
    private static final String THUMUCDAGHIHOMNAY = "DAGHITHEONGAY";
    private static final String THUMUCDAGHITHEODUONG = "DAGHITHEODUONG";
    private static final String THUMUCDATHU = "DATHU";
    private static final String THUMUCCHUAGHI = "CHUAGHI";
    private static final String THUMUCLAST = "LAST";
    private static final String TENFILETATCA_LAST = "customers_last.txt";
    private static final String TENFILEDAGHI_LAST = "customers_daghi_last.txt";
    private static final String TENFILEDAGHIHOMNAY_LAST = "customers_daghitheongay_last.txt";
    private static final String TENFILECHUAGHI_LAST = "customers_chuaghi_last.txt";
    private static final String TAG = "BACKUP";
    RadioButton radioTatca, radioDaghi, radioChuaghi, radioDaghihomnay, radioDaghiTheoDuong, radioDaThu;
    Spinner spinDuong;
    RadioGroup group;
    DatePicker datepicker;
    TextView mstatus,txttitle;
    EditText tenfile,txtngay;
    Button luu, trove,chonngay, CapNhatServer;
    DuongDAO duongdao;
    KhachHangDAO khachangdao;
    KhachHangThuDAO khachhangthudao;
    Context con;
    SPData spdata;
    List<DuongDTO> listduong;
    ArrayList<String> arrDuong;
    LichSuDAO lichsudao;
    ThanhToanDAO thanhtoandao;
    DonutProgress prgTime;
    String tenfileluu = "";
    TableLayout table;
    String thumucchuafile="";
    Date dateFinish;
    String maduong="";
    Calendar cal ;
    String url = "";
    String urlthu = "";
    String mauload  ="";
    APIService mAPIService;
    Call<ResponePayTamThu> call;
    Call<ResponePayTamThu> callfiledathu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mauload = bundle.getString("MauLoadBackUp");
        if(bundle.getString("MauLoadBackUp").equals("1"))
        {
            setContentView(R.layout.activity_backup);
        }
        else{
            setContentView(R.layout.activity_backupserver);
        }

      //  setContentView(R.layout.activity_backup);
        getSupportActionBar().hide();
        con = Backup_Activity.this;
        duongdao = new DuongDAO(con);
        khachangdao = new KhachHangDAO(con);
        khachhangthudao = new KhachHangThuDAO(con);
        lichsudao = new LichSuDAO(con);
        thanhtoandao = new ThanhToanDAO(con);
        cal = Calendar.getInstance();
        mAPIService = ApiUtils.getAPIService();
        getView();
        loadDataDuong();
        askPermissionAndReadFile();
        askPermissionAndWriteFile();
        url =  getString(R.string.API_UpdateKhachHangDaGhi2);
        urlthu = getString(R.string.API_UpdateThuTienNuoc);
//        if(khachangdao.countKhachHangCapNhatServer() > 0){
//            CapNhatServer.setEnabled(true);
//        }
//        else{
//            CapNhatServer.setEnabled(false);
//        }
        CapNhatServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radioTatca.isChecked()) {
       //     if (khachangdao.countKhachHangCapNhatServer() > 0) {

                //Kiểm tra wifi or 3G
                try {
                    if (isInternetOn()) {
                        Log.e("check wifi", "yes");
                        mstatus.setText("Đang cập nhật server...");
                        mstatus.setVisibility(View.VISIBLE);
                        table.setVisibility(View.GONE);

                        new UpdateThongTinGhiNuoc().execute(url);// connected to wifi
                    } else {
                        Log.e("check wifi", "no");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại");
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

                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Chưa bật wifi hoặc 3G. Hãy kiểm tra lại");
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


//            } else {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
//                // khởi tạo dialog
//                alertDialogBuilder.setMessage("Không có KH nào để cập nhật");
//                // thiết lập nội dung cho dialog
//
//                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
        } else if (radioDaghi.isChecked()) {

          //  if (khachangdao.countKhachHangDaGhiServer() > 0) {

                //Kiểm tra wifi or 3G
                try {
                    if (isInternetOn()) {
                        Log.e("check wifi", "yes");
                        mstatus.setText("Đang cập nhật server...");
                        mstatus.setVisibility(View.VISIBLE);
                        table.setVisibility(View.GONE);

                        new UpdateThongTinGhiNuoc().execute(url);// connected to wifi
                    } else {
                        Log.e("check wifi", "no");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại");
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

                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Chưa bật wifi hoặc 3G. Hãy kiểm tra lại");
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



        } else if (radioDaghiTheoDuong.isChecked()) {
                    //UPdate thông tin thu tiền nước
                    //  if (khachangdao.countKhachHangDaGhiServer() > 0) {

                    //Kiểm tra wifi or 3G
                    try {
                        if (isInternetOn()) {
                            Log.e("check wifi", "yes");
                            mstatus.setText("Đang cập nhật server...");
                            mstatus.setVisibility(View.VISIBLE);
                            table.setVisibility(View.GONE);
                            if (thanhtoandao.GetSoLuongThanhToanTamThu() > 0) {
                                UpdateThanhToanThuTamHDRetrofit();
                            } else {
                                mstatus.setVisibility(View.GONE);
                                table.setVisibility(View.VISIBLE);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Không tồn tại hóa đơn tạm thu nào để cập nhật lên server");
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

                            // new UpdateThanhToanThuTamHD().execute(urlthu);// connected to wifi
                        } else {
                            Log.e("check wifi", "no");
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại");
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

                    } catch (Exception e) {
                        Log.e("Loi", e.toString());
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa bật wifi hoặc 3G. Hãy kiểm tra lại");
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


                } else if (radioDaThu.isChecked()) {
                    try {
                        if (isInternetOn()) {
                            Log.e("check wifi", "yes");
                            mstatus.setText("Đang cập nhật server...");
                            mstatus.setVisibility(View.VISIBLE);
                            table.setVisibility(View.GONE);
                            if (thanhtoandao.getSoHDDaThuTheoTen(spdata.getDataNhanVienTrongSP()) > 0) {
                                UpdateThanhToanDaThuTheoUserNameRetrofit();
                            } else {
                                mstatus.setVisibility(View.GONE);
                                table.setVisibility(View.VISIBLE);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Không tồn tại hóa đơn đã thu nào để đối soát lên server");
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

                            // new UpdateThanhToanThuTamHD().execute(urlthu);// connected to wifi
                        } else {
                            Log.e("check wifi", "no");
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại");
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

                    } catch (Exception e) {
                        Log.e("Loi", e.toString());
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa bật wifi hoặc 3G. Hãy kiểm tra lại");
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
                } else if (radioDaghihomnay.isChecked()) {

                    //  if (khachangdao.countKhachHangDaGhiServer() > 0) {

                    //Kiểm tra wifi or 3G
                    try {
                        if (isInternetOn()) {
                            Log.e("check wifi", "yes");
                            mstatus.setText("Đang cập nhật server...");
                            mstatus.setVisibility(View.VISIBLE);
                            table.setVisibility(View.GONE);

                            new UpdateThongTinThuNuoc().execute(urlthu);// connected to wifi
                        } else {
                            Log.e("check wifi", "no");
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại");
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

                    } catch (Exception e) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa bật wifi hoặc 3G. Hãy kiểm tra lại");
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
        });
    }
    public final boolean isInternetOn() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
//        // get Connectivity Manager object to check connection
//        ConnectivityManager connec =
//                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
//
//        // Check for network connections
//        if ( connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
//                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ) {
//
//            // if connected with internet
//
//            Toast.makeText(this, connec.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
//            return true;
//
//        } else if (
//                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
//                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
//
//            Toast.makeText(this, " Chưa có internet hoặc 3G/4G  ", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return false;
    }


    public void cancelRequest() {
        call.cancel();
    }

    public void UpdateThanhToanThuTamHDRetrofit() {

        try {
            final List<BillTamThu> listbill = thanhtoandao.GetThanhToanTamThu();
            RequestTamThu jsondata = new RequestTamThu();
            if (listbill.size() > 0) {
                jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                jsondata.setListTamThu(listbill);
                jsondata.setPaymentChannel("TT");
                String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                jsondata.setRequestTime(thoigian2);
                final ArrayList<String> myListerror = new ArrayList<String>();
                final ArrayList<String> myListTrung = new ArrayList<String>();
                call = mAPIService.updatetamthu(jsondata);
                Log.i(TAG, "Works until here");

                call.enqueue(new Callback<ResponePayTamThu>() {
                    @Override
                    public void onResponse(Call<ResponePayTamThu> call, Response<ResponePayTamThu> response) {
                        //  Toast.makeText(MainActivity.this, "Все прошло хорошо",Toast.LENGTH_SHORT).show();

                        if (response.isSuccessful()) {

                            int code = response.body().getResponseCode();
                            String desc = response.body().getResponseDesc();

                            if (code == 0) {

                                //Update vào database
                                for (BillTamThu ma : listbill) {
                                    if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                        if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                        }
                                    }
                                }
                                List<LOGNBDTO> listhutrung = response.body().getListError();
                                Log.e("List trung ", "" + listhutrung.size());
                                if (listhutrung.size() > 0) {
                                    for (LOGNBDTO thutrung : listhutrung) {
                                        String MATHUTRUNG = thutrung.getMAKH();
                                        Log.e("MATHUTRUNG", MATHUTRUNG);
                                        String KYHDTRUNG = thutrung.getRETURNCODEDESC();
                                        Log.e("KYHDTRUNG", KYHDTRUNG);
                                        String kyhd = KYHDTRUNG.substring(4) + "/" + KYHDTRUNG.substring(0, 4);
                                        if (thanhtoandao.updateThanhToanTrangThaiTamThu(MATHUTRUNG, "3")) {
                                            if (khachhangthudao.updateKhachHangTamThuCapNhatServer(MATHUTRUNG, "3")) {

                                            }
                                        }

                                        KhachHangThuDTO kherror = khachhangthudao.getKHTheoMaKH(MATHUTRUNG.trim());
                                        String maduong = khachhangthudao.getMaDuongTheoMaKhachHang(MATHUTRUNG.trim());
                                        String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang() + " -  Lỗi: Thu trùng kỳ hóa đơn " + kyhd;
                                        Log.d("Chuoi hien thi", chuoihienthi);
                                        myListTrung.add(chuoihienthi);
                                    }
                                    desc += ".Trong đó,thu trùng: " + listhutrung.size() + " hóa đơn";


                                }

                            } else if (code == -5) {
                                try {
                                    desc += ".Kiểm tra lại danh sách lỗi";
                                    int capnhatlaichuaupdate = 0;

                                    List<LOGNBDTO> tong = response.body().getListError();
                                    List<String> DanhSachLoi = new ArrayList<>();
                                    for (int i = 0; i < tong.size(); i++) {
                                        String returndescerr = "";
                                        LOGNBDTO objKHLOI = tong.get(i);
                                        if (!objKHLOI.getRETURNCODEDESC().equals("")) {
                                            returndescerr = objKHLOI.getRETURNCODEDESC();
                                        }
                                        Log.e("Loi hien thi", returndescerr);
                                        if (!objKHLOI.getMAKH().equals("")) {
                                            String maKH = objKHLOI.getMAKH().trim();
                                            KhachHangThuDTO kherror = khachhangthudao.getKHTheoMaKH(maKH.trim());
                                            String maduong = khachhangthudao.getMaDuongTheoMaKhachHang(maKH.trim());
                                            String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang() + " -  Lỗi: " + returndescerr;
                                            myListerror.add(chuoihienthi);
                                            DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                        }

                                    }

                                    for (BillTamThu ma : listbill) {
                                        if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                            if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                            }
                                        }
                                    }
                                    if (DanhSachLoi.size() > 0) {
                                        for (String loi : DanhSachLoi) {
                                            if (khachhangthudao.updateKhachHangTamThuCapNhatServer(loi, "1")) {
                                                if (thanhtoandao.updateThanhToanTrangThaiTamThu(loi, "1")) {
                                                }
                                                capnhatlaichuaupdate++;
                                            }
                                        }
                                    }


                                    if (capnhatlaichuaupdate == DanhSachLoi.size()) {
                                        //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                        Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();

                                    }
                                } catch (Exception e) {

                                }

                            }


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage(desc);
                            // thiết lập nội dung cho dialog

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    mstatus.setVisibility(View.GONE);
                                    table.setVisibility(View.VISIBLE);
                                    // button "no" ẩn dialog đi
                                }
                            });
                            if (code == -5) {
                                alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mstatus.setVisibility(View.GONE);
                                        table.setVisibility(View.VISIBLE);

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Backup_Activity.this);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                        alertDialog.setView(convertView);
                                        alertDialog.setTitle("Danh sách lỗi");
                                        ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Backup_Activity.this, android.R.layout.simple_list_item_1, myListerror);
                                        lv.setAdapter(adapter);
                                        alertDialog.show();
                                        // button "no" ẩn dialog đi
                                    }
                                });
                            } else if (code == 0) {
                                if (myListTrung.size() > 0) {
                                    alertDialogBuilder.setPositiveButton("DS Thu Trùng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);

                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Backup_Activity.this);
                                            LayoutInflater inflater = getLayoutInflater();
                                            View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                            alertDialog.setView(convertView);
                                            alertDialog.setTitle("Danh sách thu trùng");
                                            ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Backup_Activity.this, android.R.layout.simple_list_item_1, myListTrung);
                                            lv.setAdapter(adapter);
                                            alertDialog.show();
                                            // button "no" ẩn dialog đi
                                        }
                                    });
                                }
                            }

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponePayTamThu> call, Throwable t) {
                        Log.e("LOI ", "Unable to submit post to API." + t.getCause().toString());
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        call.cancel();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Lỗi hệ thống: Không cập nhật được dữ liệu");
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
                });
            } else {
                Log.e("LOI ", "Không có list");
            }



        } catch (Exception e) {
            if (call.isCanceled()) {
                Log.e(TAG, "request was aborted");
            } else {
                call.cancel();
                Log.e(TAG, "Unable to submit post to API.");
            }
        }
    }

    public void UpdateThanhToanDaThuTheoUserNameRetrofit() {

        try {
            final List<ObjectThu> listbill = thanhtoandao.getHDDaThuTheoTen(spdata.getDataNhanVienTrongSP());

            if (listbill.size() > 0) {
                JSONTHUTHEOUSERNAME tiwaread = new JSONTHUTHEOUSERNAME();
                tiwaread.setListThanhToan(listbill);
                tiwaread.setUserName(spdata.getDataNhanVienTrongSP());
                tiwaread.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                final ArrayList<String> myListerror = new ArrayList<String>();
                final ArrayList<String> myListTrung = new ArrayList<String>();
                callfiledathu = mAPIService.FileDaThu(tiwaread);
                Log.i(TAG, "Works until here");

                callfiledathu.enqueue(new Callback<ResponePayTamThu>() {
                    @Override
                    public void onResponse(Call<ResponePayTamThu> call, Response<ResponePayTamThu> response) {
                        //  Toast.makeText(MainActivity.this, "Все прошло хорошо",Toast.LENGTH_SHORT).show();

                        if (response.isSuccessful()) {
                            mstatus.setVisibility(View.GONE);
                            table.setVisibility(View.VISIBLE);
                            int code = response.body().getResponseCode();
                            String desc = response.body().getResponseDesc();
                            if (code == 0) {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Đã chuyển file để đối soát thành công");
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
                            } else if (code == -2) {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Tài khoản nhân viên không hợp lệ.Liên hệ IT để giải quyết");
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
                            } else if (code == -3) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Lỗi" + desc);
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

                    @Override
                    public void onFailure(Call<ResponePayTamThu> call, Throwable t) {
                        Log.e("LOI ", "Unable to submit post to API." + t.getCause().toString());
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        call.cancel();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Lỗi hệ thống: Không cập nhật được dữ liệu");
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
                });
            } else {
                Log.e("LOI ", "Không có list");
            }


        } catch (Exception e) {
            if (callfiledathu.isCanceled()) {
                Log.e(TAG, "request was aborted");
            } else {
                callfiledathu.cancel();
                Log.e(TAG, "Unable to submit post to API.");
            }
        }
    }



    public class UpdateThanhToanThuTamHD extends AsyncTask<String, String, String> {
        String status = "";
        String kyhd = spdata.getDataKyHoaDonThuTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaThu_CapNhatServer(kyhdsau);
        String result = "";
        //ListJsonData jsondata = new ListJsonData();
        RequestTamThu jsondata = new RequestTamThu();
        String transactionID = "";
        List<BillTamThu> listbill = thanhtoandao.GetThanhToanTamThu();

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

                if (listbill.size() > 0) {
                    jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                    jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                    jsondata.setListTamThu(listbill);
                    jsondata.setPaymentChannel("TT");
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
                } else {
                    result = "RONG";
                }

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

            mstatus.setText(status);


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result get data", result);
            int returncode = 0;
            String returndesc = "";
            List<String> DanhSachLoi = new ArrayList<>();
            JSONObject jsonobj = null;
//            try {
//                jsonobj = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (result.equals("RONG")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Không có hóa đơn nào để cập nhật");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình lấy dữ liệu hóa đơn");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
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
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Tên tài khoản và mật khẩu không hợp lệ. Hãy đăng nhập lại hoặc liên hệ với IT để giải quyết lỗi");
                                // thiết lập nội dung cho dialog

                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mstatus.setVisibility(View.GONE);
                                        table.setVisibility(View.VISIBLE);
                                        // button "no" ẩn dialog đi
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                            } else if (returncode == -4) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage("Không tồn tại hóa đơn để cập nhật");
                                // thiết lập nội dung cho dialog

                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mstatus.setVisibility(View.GONE);
                                        table.setVisibility(View.VISIBLE);
                                        // button "no" ẩn dialog đi
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                            } else if (returncode == 0) {  //thanh toan thanh cong
                                for (BillTamThu ma : listbill) {
                                    if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                        if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                        }
                                    }
                                }
                                returndesc = jsonobj.getString("ResponseDesc");
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage(returndesc);
                                // thiết lập nội dung cho dialog

                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mstatus.setVisibility(View.GONE);
                                        table.setVisibility(View.VISIBLE);
                                        // button "no" ẩn dialog đi
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                            } else if (returncode == -3) {
                                returndesc = jsonobj.getString("ResponseDesc");
                                //Lỗi hệ thống
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                // khởi tạo dialog
                                alertDialogBuilder.setMessage(returndesc);
                                // thiết lập nội dung cho dialog

                                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mstatus.setVisibility(View.GONE);
                                        table.setVisibility(View.VISIBLE);
                                        // button "no" ẩn dialog đi
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                            } else if (returncode == -5) {
                                if (jsonobj.has("ResponseDesc")) {
                                    returndesc = jsonobj.getString("ResponseDesc");
                                }
                                //Lỗi hệ thống
                                if (jsonobj.has("ListError")) {


                                    try {
                                        int capnhatlaichuaupdate = 0;
                                        final ArrayList<String> myListerror = new ArrayList<String>();

                                        JSONArray tong = jsonobj.getJSONArray("ListError");
                                        for (int i = 0; i < tong.length(); i++) {
                                            String returndescerr = "";
                                            JSONObject objKHLOI = tong.getJSONObject(i);
                                            if (objKHLOI.has("RETURNCODEDESC")) {
                                                returndescerr = objKHLOI.getString("RETURNCODEDESC");
                                            }
                                            Log.e("Loi hien thi", returndescerr);
                                            if (objKHLOI.has("MAKH")) {
                                                String maKH = objKHLOI.getString("MAKH").trim();
                                                KhachHangThuDTO kherror = khachhangthudao.getKHTheoMaKH(maKH.trim());
                                                String maduong = khachhangthudao.getMaDuongTheoMaKhachHang(maKH.trim());
                                                String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang() + " -  Lỗi: " + returndescerr;
                                                myListerror.add(chuoihienthi);
                                                DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                            }

                                        }

                                        for (BillTamThu ma : listbill) {
                                            if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                                if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                                }
                                            }
                                        }
                                        if (DanhSachLoi.size() > 0) {
                                            for (String loi : DanhSachLoi) {
                                                if (khachhangthudao.updateKhachHangTamThuCapNhatServer(loi, "1")) {
                                                    if (thanhtoandao.updateThanhToanTrangThaiTamThu(loi, "1")) {
                                                    }
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
                                            //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                            Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                            // khởi tạo dialog
                                            alertDialogBuilder.setMessage(returndesc + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                            // thiết lập nội dung cho dialog

                                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    mstatus.setVisibility(View.GONE);
                                                    table.setVisibility(View.VISIBLE);
                                                    // button "no" ẩn dialog đi
                                                }
                                            });
                                            alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    mstatus.setVisibility(View.GONE);
                                                    table.setVisibility(View.VISIBLE);

                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Backup_Activity.this);
                                                    LayoutInflater inflater = getLayoutInflater();
                                                    View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                                    alertDialog.setView(convertView);
                                                    alertDialog.setTitle("Danh sách lỗi");
                                                    ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Backup_Activity.this, android.R.layout.simple_list_item_1, myListerror);
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

                                    }

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
    private void getView() {
        group = (RadioGroup) findViewById(R.id.group);
        tenfile = (EditText) findViewById(R.id.edit_tenfile);
        txttitle = (TextView) findViewById(R.id.txt_title);
        txtngay = (EditText) findViewById(R.id.txt_ngay);
        luu = (Button) findViewById(R.id.btn_Luu);
        trove = (Button) findViewById(R.id.btn_trove);
        CapNhatServer =(Button) findViewById(R.id.btn_CapNhatServer);
        chonngay = (Button) findViewById(R.id.btn_chongay);
        radioTatca = (RadioButton) findViewById(R.id.radio_tatca);
        radioDaghi = (RadioButton) findViewById(R.id.radio_daghi);
        radioChuaghi = (RadioButton) findViewById(R.id.radio_chuaghi);
        radioDaghihomnay = (RadioButton) findViewById(R.id.radio_daghihomnay);
        radioDaghiTheoDuong = (RadioButton) findViewById(R.id.radio_daghitheoduong);
        radioDaThu = (RadioButton) findViewById(R.id.radio_dathu);
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        spinDuong = (Spinner) findViewById(R.id.spinduong);
        table = (TableLayout) findViewById(R.id.TableLayout1);
        mstatus = (TextView) findViewById(R.id.tv_status);
        chonngay.setVisibility(View.GONE);
        mstatus.setVisibility(View.GONE);
        prgTime.setProgress(0);
        prgTime.setText("0 %");
        spdata = new SPData(con);
        if (radioTatca.isChecked() == false && radioDaghi.isChecked() == false && radioChuaghi.isChecked() == false && radioDaghihomnay.isChecked() == false && radioDaghiTheoDuong.isChecked() == false && radioDaThu.isChecked() == false) {
            radioTatca.setChecked(true);
            tenfile.setText(taoTenFile("customers"));
        }

        this.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupradio, int checkedId) {
                xuLyChon(groupradio, checkedId);
            }
        });
        chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (canRead) {

            // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
            //  task.execute(duongdanfile);
            //   readFileandSaveDatabase();


        }
    }


    // Khi yêu cầu hỏi người dùng được trả về (Chấp nhận hoặc không chấp nhận).




    private void xuLyChon(RadioGroup groupradio, int checkedId) {
        int checkedRadioId = groupradio.getCheckedRadioButtonId();
        if(mauload.equals("1")) {
            if (checkedRadioId == R.id.radio_tatca) {
                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.GONE);
                txtngay.setVisibility(View.GONE);
                chonngay.setVisibility(View.GONE);
                tenfileluu = taoTenFile("customers");

            } else if (checkedRadioId == R.id.radio_daghi) {
                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.GONE);
                txtngay.setVisibility(View.GONE);
                chonngay.setVisibility(View.GONE);
                tenfileluu = taoTenFile("DaGhi");

            } else if (checkedRadioId == R.id.radio_chuaghi) {

                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.GONE);
                txtngay.setVisibility(View.GONE);
                chonngay.setVisibility(View.GONE);

                tenfileluu = taoTenFile("ChuaGhi");

            } else if (checkedRadioId == R.id.radio_daghihomnay) {

                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.VISIBLE);
                txttitle.setText("Ngày: ");
                txtngay.setVisibility(View.VISIBLE);
                chonngay.setVisibility(View.VISIBLE);
                tenfileluu = taoTenFile("DaGhiTheoNgay");

                showDatePickerDialog();

            } else if (checkedRadioId == R.id.radio_daghitheoduong) {
                spinDuong.setVisibility(View.VISIBLE);
                txttitle.setVisibility(View.VISIBLE);
                chonngay.setVisibility(View.GONE);
                txttitle.setText("Đường: ");
                txtngay.setVisibility(View.GONE);
                tenfileluu = taoTenFile("DaGhiTheoDuong_" + maduong);
            } else if (checkedRadioId == R.id.radio_dathu) {
                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.GONE);
                txtngay.setVisibility(View.GONE);
                chonngay.setVisibility(View.GONE);
                tenfileluu = taoTenFile("DaThu");
            }
            tenfile.setText(tenfileluu);
        }
        else{


                CapNhatServer.setEnabled(true);

        }
    }
    public void showDatePickerDialog()
    {

        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                String ngay ="";
                String thang ="";
                if(dayOfMonth < 10) {
                    ngay = "0"+dayOfMonth;
                }
                else{
                    ngay = String.valueOf(dayOfMonth);
                }
                if(monthOfYear+1 <10 ){
                    thang  = "0"+(monthOfYear+1);
                }
                else{
                    thang = String.valueOf(monthOfYear+1);
                }
                txtngay.setText(ngay +"-"+thang+"-"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
                tenfileluu = taoTenFile("DaGhiTheoNgay_"+ngay+thang+year);
                tenfile.setText(tenfileluu);
                String thoigian2 = new SimpleDateFormat("yyyy-MM-dd").format(dateFinish);
                Log.e("thoigian2",thoigian2);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtngay.getText()+"";
        String thoigian1 = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        Log.e("thoigian1",thoigian1);
        txtngay.setText(thoigian1);
        String[] strArrtmp=thoigian1.split("-");

        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);

        Log.e("ngay", String.valueOf(ngay));
        Log.e("thang",String.valueOf(thang));
        Log.e("nam",String.valueOf(nam));
        DatePickerDialog pic=new DatePickerDialog(
                Backup_Activity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày ghi nước");
        pic.show();
    }

    public void trove(View view) {

        finish();
        Intent myIntent = new Intent(Backup_Activity.this, StartActivity.class);

        startActivity(myIntent);

    }
    //Backup --------------------------------------------------------------------------
    public void backupfile(View view) {
        Log.e("ghi file","vao trong request");
        //Duong dan file
        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
        Bien.bienbkall = spdata.getDataBKALLTrongSP();
        Bien.bienbkcg = spdata.getDataBKCGTrongSP();
        Bien.bienbkdg = spdata.getDataBKDGTrongSP();
        Bien.bienbkdghn = spdata.getDataBKDGHomNayTrongSP();


        Log.e("flag flagghi 1", String.valueOf(Bien.bienghi));
        Log.e("flag flagall 1", String.valueOf( Bien.bienbkall));
        Log.e("flag flagcg 1", String.valueOf(Bien.bienbkcg));
        Log.e("flag flagdg 1", String.valueOf(Bien.bienbkdg));

        XuLyFile xl  = new XuLyFile(con);
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = xl.getBoNhoTrong();
        } else {
            path = getFilesDir().getAbsolutePath();
        }

        Log.e("path",path);
        thumucchuafile = path+"/"+THUMUCBACKUP;
        File rootfile = new File(thumucchuafile);
        if(rootfile.exists()==false){
            spdata.luuDataFlagGhivaBackUpTrongSP(1,0,0,0,0);
            taoThuMuc(thumucchuafile);
        }

       // prgTime.setVisibility(View.VISIBLE);



        if (radioTatca.isChecked() == true) {
//            if(Bien.bienbkall  == Bien.bienghi) {
//                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
//            }
//            else {
                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();
//            }
        }
        else if (radioDaghi.isChecked() == true)
        {
            if(Bien.bienbkdg  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();
            }
        }
        else if(radioChuaghi.isChecked() == true){
            if(Bien.bienbkcg  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();
            }
        }
        else if(radioDaghihomnay.isChecked() == true){
             /* if(Bien.bienbkdghn  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else { */
                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();
          //  }
        }
        else if(radioDaghiTheoDuong.isChecked() == true){

                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();

        } else if (radioDaThu.isChecked() == true) {

            mstatus.setVisibility(View.VISIBLE);
            table.setVisibility(View.GONE);
            MyBackUpTask task = new MyBackUpTask();
            task.execute();

        }


        /*
        Bien.bienghi = spdata.getDataFlagGhiTrongSP();
        Bien.bienbkall = spdata.getDataBKALLTrongSP();
        Bien.bienbkcg = spdata.getDataBKCGTrongSP();
        Bien.bienbkdg = spdata.getDataBKDGTrongSP();


        Log.e("flag flagghi 1", String.valueOf(Bien.bienghi));
        Log.e("flag flagall 1", String.valueOf( Bien.bienbkall));
        Log.e("flag flagcg 1", String.valueOf(Bien.bienbkcg));
        Log.e("flag flagdg 1", String.valueOf(Bien.bienbkdg));
  
        XuLyFile xl  = new XuLyFile(con);
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = xl.getBoNhoTrong();
        } else {
            path = getFilesDir().getAbsolutePath();
        }

        Log.e("path",path);
        String thumucchuafile = path+"/"+THUMUCBACKUP;
        File rootfile = new File(thumucchuafile);
        if(rootfile.exists()==false){
            spdata.luuDataFlagGhivaBackUpTrongSP(1,0,0,0);
            Log.e("asdasd","asdasdasdasd");
            taoThuMuc(thumucchuafile);
        }


        String filename="";
        String filenameLAST = "";
        boolean kt =false, ktlast =false;
        if (radioTatca.isChecked() == true) {
            if(Bien.bienbkall  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                Log.e("back up", "Vao tat ca");
                String result_tatca_string = taoJSONData_KH_TatCa(tenfile.getText().toString());
                if (!result_tatca_string.equals("")) {
                    filename = thumucchuafile + "/" + THUMUCTATCA;
                    filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                    taoThuMuc(filename);
                    taoThuMuc(filenameLAST);
                    kt = writeFile(filename, tenfile.getText().toString(), result_tatca_string);
                    ktlast = writeFile(filenameLAST, TENFILETATCA_LAST, result_tatca_string);
                    spdata.luuDataFlagBKAllTrongSP(Bien.bienghi);
                    //   MediaScannerConnection.scanFile(con, new String[]{filename,filenameLAST}, null, null);
                }
            }
        }
        else if (radioDaghi.isChecked() == true)
        {
            if(Bien.bienbkdg  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                Log.e("back up", "Vao da ghi");
                String result_daghi_string = taoJSONData_KH_DaGhi(tenfile.getText().toString());
                if (!result_daghi_string.equals("")) {
                    filename = thumucchuafile + "/" + THUMUCDAGHI;
                    filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                    taoThuMuc(filename);
                    taoThuMuc(filenameLAST);
                    kt = writeFile(filename, tenfile.getText().toString(), result_daghi_string);
                    ktlast = writeFile(filenameLAST, TENFILEDAGHI_LAST, result_daghi_string);
                    spdata.luuDataFlagBKDaghiTrongSP(Bien.bienghi);
                }
            }
        }
        else if(radioChuaghi.isChecked() == true){
            if(Bien.bienbkcg  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                Log.e("back up", "Vao chua ghi");
                String result_chuaghi_string = taoJSONData_KH_ChuaGhi(tenfile.getText().toString());
                if (!result_chuaghi_string.equals("")) {
                    filename = thumucchuafile + "/" + THUMUCCHUAGHI;
                    filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                    taoThuMuc(filename);
                    taoThuMuc(filenameLAST);
                    kt = writeFile(filename, tenfile.getText().toString(), result_chuaghi_string);
                    ktlast = writeFile(filenameLAST, TENFILECHUAGHI_LAST, result_chuaghi_string);
                    spdata.luuDataFlagBKChuaGhiTrongSP(Bien.bienghi);
                }
            }
        }

        if(kt && ktlast) {
            Bien.bienghi = spdata.getDataFlagGhiTrongSP();
            Bien.bienbkall = spdata.getDataBKALLTrongSP();
            Bien.bienbkcg = spdata.getDataBKCGTrongSP();
            Bien.bienbkdg = spdata.getDataBKDGTrongSP();
            Log.e("flag flagghi 2", String.valueOf(Bien.bienghi));
            Log.e("flag flagall 2", String.valueOf( Bien.bienbkall));
            Log.e("flag flagcg 2", String.valueOf(Bien.bienbkcg));
            Log.e("flag flagdg 2", String.valueOf(Bien.bienbkdg));
            LichSuDTO ls = new LichSuDTO();
            ls.setNoiDungLS("Lưu dữ liệu ghi nước");
            ls.setMaLenh("BK");
            String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            ls.setThoiGianLS(thoigian1);
            lichsudao.addTable_History(ls);
            //Hiển thị dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_thanhcong);
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

        } */
    }
    //-------------------------------------------------------------------------------------------
    public  String getSdCardPath() {

        String[] deviceID = getExternalStorageDirectories();
        if(deviceID == null) {
            return "";
        }
        else {
            return deviceID[0];
        }

    }

    public String[] getExternalStorageDirectories() {

        List<String> results = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //Method 1 for KitKat & above
            File[] externalDirs = getExternalFilesDirs(null);

            for (File file : externalDirs) {
                String path = file.getPath().split("/Android")[0];

                boolean addPath = false;

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addPath = Environment.isExternalStorageRemovable(file);
                }
                else{
                    addPath = Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(file));
                }

                if(addPath){
                    results.add(path);
                }
            }
        }

        if(results.isEmpty()) { //Method 2 for all versions
            // better variation of: http://stackoverflow.com/a/40123073/5002496
            String output = "";
            try {
                final Process process = new ProcessBuilder().command("mount | grep /dev/block/vold")
                        .redirectErrorStream(true).start();
                process.waitFor();
                final InputStream is = process.getInputStream();
                final byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    output = output + new String(buffer);
                }
                is.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if(!output.trim().isEmpty()) {
                String devicePoints[] = output.split("\n");
                for(String voldPoint: devicePoints) {
                    results.add(voldPoint.split(" ")[2]);
                }
            }
        }

        //Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().matches(".*[0-9a-f]{4}[-][0-9a-f]{4}")) {
                    Log.d("sdcard", results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().contains("ext") && !results.get(i).toLowerCase().contains("sdcard")) {
                    Log.d("sdcard", results.get(i)+" might not be extSDcard");
                    results.remove(i--);
                }
            }
        }

        String[] storageDirectories = new String[results.size()];
        for(int i=0; i<results.size(); ++i) storageDirectories[i] = results.get(i);

        return storageDirectories;
    }
    private boolean checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if(mExternalStorageAvailable==true  && mExternalStorageWriteable == true ){
            return true;
        }
        else
            return false;

    }
    private String taoTenFile(String loairadio) {
        String tenfile = loairadio + "_";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        tenfile += timeStamp+".txt";
        return tenfile.trim();
    }

    private String taoJSONData_KH_TatCa(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangAll());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachangdao.getAllKHTheoDuong(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size() >0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json ="";
        if(listtiwaread.size()>0) {

            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        }
        else{
            spdata.luuDataFlagBKAllTrongSP(-1);
            Bien.bienbkall = -1;
            //Hiển thị dialog
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_tatca);
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
            */
            // hiển thị dialog
        }

        return json;
    }

    private String taoJSONData_KH_DaGhi(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaGhi());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachangdao.getAllKHDaGhiTheoDuong(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
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
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);

            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        }
        else{
            spdata.luuDataFlagBKDaghiTrongSP(-1);
            Bien.bienbkdg = -1;
            //Hiển thị dialog
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_daghi);
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
            */
            // hiển thị dialog
        }
        return json;
    }
    private String taoJSONData_KH_DaGhiTheoDuong(String tendanhsach,String duong) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
       // List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaGhiTheoDuong(duong));
       // listduong = duongdao.getAllDuong();
      //  for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = duong;
            String tenduong = duongdao.getTenDuongTheoMa(duong);
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachangdao.getAllKHDaGhiTheoDuong(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size() >0) {
                listtiwaread.add(tiwaread);
            }
       // }
        String json="";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);

            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        }
        else{
            spdata.luuDataFlagBKDaghiTrongSP(-1);
            Bien.bienbkdg = -1;
            //Hiển thị dialog
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_daghi);
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
            */
            // hiển thị dialog
        }
        return json;
    }

    private String taoJSONDataDaThu() {

        //Lấy danh sách tất cả các đường
        // List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ObjectThu> listtiwaread = new ArrayList<ObjectThu>();


        listtiwaread = thanhtoandao.getHDDaThuTheoTen(spdata.getDataNhanVienTrongSP());


        // }
        String json = "";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if (listtiwaread.size() > 0) {
            JSONTHU tiwaread = new JSONTHU();
            tiwaread.setListThanhToan(listtiwaread);
            Gson gson = new Gson();
            json = gson.toJson(tiwaread);
        } else {

        }
        return json;
    }

    private String taoJSONDataDaThuTheoUserName(String username, String password) {

        //Lấy danh sách tất cả các đường
        // List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ObjectThu> listtiwaread = new ArrayList<ObjectThu>();


        listtiwaread = thanhtoandao.getHDDaThuTheoTen(spdata.getDataNhanVienTrongSP());


        // }
        String json = "";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if (listtiwaread.size() > 0) {
            JSONTHUTHEOUSERNAME tiwaread = new JSONTHUTHEOUSERNAME();
            tiwaread.setListThanhToan(listtiwaread);
            tiwaread.setUserName(spdata.getDataNhanVienTrongSP());
            tiwaread.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
            Gson gson = new Gson();
            json = gson.toJson(tiwaread);
        } else {

        }
        return json;
    }

    private ListJsonData taoJSONData_KH_DaGhi_CapNhatServer(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaGhi());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachangdao.getAllKHDaGhiTheoDuongChuaCapNhat(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
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
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(String.valueOf(listtiwaread.size()));
            Log.e("tong sokh", String.valueOf(String.valueOf(listtiwaread.size())));
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
            Log.e("json data", json);
        }
        else{
            spdata.luuDataFlagBKDaghiTrongSP(-1);
            Bien.bienbkdg = -1;
            //Hiển thị dialog
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_daghi);
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
            */
            // hiển thị dialog
        }
        return jsondata;
    }

    private ListRequestObject taoJSONData_KH_DaGhi_CapNhatServer2(String tendanhsach) {
        ListRequestObject jsondata = new ListRequestObject();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuong> listtiwaread = new ArrayList<ListKHTheoDuong>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangCapNhatServer());
        listduong = duongdao.getAllDuongChuaKhoaSo();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObject> listkh = new ArrayList<RequestObject>();
            listkh = khachangdao.getAllKHDaGhiTheoDuongChuaCapNhat1(maduong);
            ListKHTheoDuong tiwaread = new ListKHTheoDuong();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size() >0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json="";

        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);
            Log.e("tong sokh", soluongKH);
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

    private ListRequestObject taoJSONData_KHDaGhiTatCa_CapNhatServer2(String tendanhsach) {
        ListRequestObject jsondata = new ListRequestObject();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuong> listtiwaread = new ArrayList<ListKHTheoDuong>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaGhiServer());



        listduong = duongdao.getAllDuongChuaKhoaSo();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObject> listkh = new ArrayList<RequestObject>();
            listkh = khachangdao.getAllKHTatCaDaGhiTheoDuong(maduong);
            ListKHTheoDuong tiwaread = new ListKHTheoDuong();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setHuyen(spdata.getDataHuyen());
            tiwaread.setTiwareadList(listkh);
            if(listkh!=null) {
                if (listkh.size() > 0) {
                    listtiwaread.add(tiwaread);
                }
            }
        }
        String json="";

        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);
            Log.e("tong sokh", soluongKH);
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




    private String taoJSONData_KH_DaGhiHomNay(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
    //    String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        String thoigian2 = new SimpleDateFormat("yyyy-MM-dd").format(dateFinish);
        Log.e("thoigian2",thoigian2);
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaGhiHomNay(thoigian2));
        Log.e("soluongtheongay",soluongKH);
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();

            listkh = khachangdao.getAllKHDaGhiHomNay(maduong,thoigian2);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size() >0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json="";
        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);

            JSONListTiwaread jsonupload = new JSONListTiwaread();
            jsonupload.setJsontiwaread(jsondata);
            Gson gson = new Gson();
            json = gson.toJson(jsonupload);
        }
        else{
            spdata.luuDataFlagBKDaghiHomNayTrongSP(-1);
            Bien.bienbkdghn = -1;
            //Hiển thị
            /*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_daghi);
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
            */
            // hiển thị dialog
        }
        return json;
    }

    private String taoJSONData_KH_ChuaGhi(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangChuaGhi());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachangdao.getAllKHChuaGhiTheoDuong(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if(listkh.size()>0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json="";
        if(listtiwaread.size()>0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd  = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);
            jsondata.setTongSLkh(soluongKH);

            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        }
        else{
            spdata.luuDataFlagBKChuaGhiTrongSP(-1);
            Bien.bienbkcg = -1;
            /*
            //Hiển thị dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_listrong_chuaghinuoc);
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
                    */
                    }
                    return json;
                    }

    private String ObjectToJson(ListJsonData jsondata) {

        Gson gson = new Gson();
        String json = gson.toJson(jsondata);
        return json;
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
            Log.e("ghi file","Co quyen ghi");

        }
    }

    // Với Android Level >= 23 bạn phải hỏi người dùng cho phép các quyền với thiết bị
    // (Chẳng hạn đọc/ghi dữ liệu vào thiết bị).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(con, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    // Khi yêu cầu hỏi người dùng được trả về (Chấp nhận hoặc không chấp nhận).

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //

        // Chú ý: Nếu yêu cầu bị hủy, mảng kết quả trả về là rỗng.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("ghi file","Co quyen read");
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("ghi file","Co write");
                       // String result_tatca_string = taoJSONData_KH_TatCa(tenfile.getText().toString());
                       // XuLyFile xl  = new XuLyFile(con);
                       // String path = xl.getBoNhoTrong();
                       // String filename = path +"/"+ tenfile.getText().toString();
                      //  writeFile(filename,result_tatca_string);
                    }
                }
            }
        } else {
            Toast.makeText(con, "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean writeFile(String path,String tenfile, String data) {


        try {
            Log.e("duongdanfile",path);
            Log.e("file",tenfile);
            String duongdanfile = path+"/"+tenfile;
            File myFile = new File(duongdanfile);
           // myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            MediaScannerConnection.scanFile(con, new String[]{duongdanfile}, null, null);
            myOutWriter.close();
            fOut.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            //Hiển thị dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.backup_thatbai);
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
            return false;
        }
    }
    private void taoThuMuc(String path){
        File f = new File(path);
        if(!f.exists()) {
            f.mkdirs();

        }
    }
    private void taoDialogThongBao(String message)
    {
        //Hiển thị dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
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
    public class MyBackUpTask extends AsyncTask<Void, String , String > {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            //Lấy URL truyền vào
            String result ="";
            Boolean FlagupdateDB = true;


            String filename="";
            String filenameLAST = "";
            String status ="";

            boolean kt =false, ktlast =false;
            if (radioTatca.isChecked() == true) {
//                if(Bien.bienbkall  == Bien.bienghi) {
//                 //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
//                }
//
//                else {
                    Log.e("back up", "Vao tat ca");


                    status = "Đang tập hợp dữ liệu...";
                    //     String.valueOf(status)
                    publishProgress(String.valueOf(status));
                    String result_tatca_string = taoJSONData_KH_TatCa(tenfile.getText().toString());
                    result  = result_tatca_string;
                    /*
                    if (!result_tatca_string.equals("")) {
                        filename = thumucchuafile + "/" + THUMUCTATCA;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        kt = writeFile(filename, tenfile.getText().toString(), result_tatca_string);
                        ktlast = writeFile(filenameLAST, TENFILETATCA_LAST, result_tatca_string);
                        spdata.luuDataFlagBKAllTrongSP(Bien.bienghi);
                        //   MediaScannerConnection.scanFile(con, new String[]{filename,filenameLAST}, null, null);
                    }
                    */
//                }
            }
            else if (radioDaghi.isChecked() == true)
            {
                if(Bien.bienbkdg  == Bien.bienghi) {
                 //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                }
                else {
                    Log.e("back up", "Vao da ghi");
                     status = "Đang tập hợp dữ liệu...";
                    //     String.valueOf(status)
                    publishProgress(String.valueOf(status));

                    String result_daghi_string = taoJSONData_KH_DaGhi(tenfile.getText().toString());
                    result =result_daghi_string;

                    /*

                    if (!result_daghi_string.equals("")) {
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        filename = thumucchuafile + "/" + THUMUCDAGHI;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        kt = writeFile(filename, tenfile.getText().toString(), result_daghi_string);
                        ktlast = writeFile(filenameLAST, TENFILEDAGHI_LAST, result_daghi_string);
                        spdata.luuDataFlagBKDaghiTrongSP(Bien.bienghi);
                    }
                    */
                }
            }

            else if (radioDaghihomnay.isChecked() == true)
            {
               // if(Bien.bienbkdghn  == Bien.bienghi) {
              //      //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
              //  }
              //  else {
                    Log.e("back up", "Vao da ghi");
                    status = "Đang tập hợp dữ liệu...";
                    //     String.valueOf(status)
                    publishProgress(String.valueOf(status));

                    String result_daghitheongay_string = taoJSONData_KH_DaGhiHomNay(tenfile.getText().toString());
                    result =result_daghitheongay_string;


                    /*
                    if (!result_daghi_string.equals("")) {
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        filename = thumucchuafile + "/" + THUMUCDAGHIHOMNAY;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        kt = writeFile(filename, tenfile.getText().toString(), result_daghi_string);
                        ktlast = writeFile(filenameLAST, TENFILEDAGHIHOMNAY_LAST, result_daghi_string);
                        spdata.luuDataFlagBKDaghiHomNayTrongSP(Bien.bienghi);
                    }
                    */
              //  }
            }

            else if(radioChuaghi.isChecked() == true){
                if(Bien.bienbkcg  == Bien.bienghi) {
                //    taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                }
                else {
                    Log.e("back up", "Vao chua ghi");
                     status = "Đang tập hợp dữ liệu...";
                    //     String.valueOf(status)
                    publishProgress(String.valueOf(status));
                    String result_chuaghi_string = taoJSONData_KH_ChuaGhi(tenfile.getText().toString());
                    result = result_chuaghi_string;
                    /*
                    if (!result_chuaghi_string.equals("")) {
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        filename = thumucchuafile + "/" + THUMUCCHUAGHI;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        kt = writeFile(filename, tenfile.getText().toString(), result_chuaghi_string);
                        ktlast = writeFile(filenameLAST, TENFILECHUAGHI_LAST, result_chuaghi_string);
                        spdata.luuDataFlagBKChuaGhiTrongSP(Bien.bienghi);
                    }*/
                }
            }
            else if(radioDaghiTheoDuong.isChecked() == true){

                    Log.e("back up", "Vao da ghi theo duong");
                    status = "Đang tập hợp dữ liệu...";
                    //     String.valueOf(status)
                    publishProgress(String.valueOf(status));

                    String result_daghitheoduong_string = taoJSONData_KH_DaGhiTheoDuong(tenfile.getText().toString(),maduong);
                    result = result_daghitheoduong_string;
                    /*
                    if (!result_chuaghi_string.equals("")) {
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        filename = thumucchuafile + "/" + THUMUCCHUAGHI;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        kt = writeFile(filename, tenfile.getText().toString(), result_chuaghi_string);
                        ktlast = writeFile(filenameLAST, TENFILECHUAGHI_LAST, result_chuaghi_string);
                        spdata.luuDataFlagBKChuaGhiTrongSP(Bien.bienghi);
                    }*/

            } else if (radioDaThu.isChecked() == true) {

                Log.e("back up", "Vao da ghi theo duong");
                status = "Đang tập hợp dữ liệu...";
                //     String.valueOf(status)
                publishProgress(String.valueOf(status));

                String result_daghitheoduong_string = taoJSONDataDaThu();
                result = result_daghitheoduong_string;
                    /*
                    if (!result_chuaghi_string.equals("")) {
                        status = "Đang tạo file dữ liệu...";
                        publishProgress(String.valueOf(status));
                        filename = thumucchuafile + "/" + THUMUCCHUAGHI;
                        filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                        taoThuMuc(filename);
                        taoThuMuc(filenameLAST);
                        kt = writeFile(filename, tenfile.getText().toString(), result_chuaghi_string);
                        ktlast = writeFile(filenameLAST, TENFILECHUAGHI_LAST, result_chuaghi_string);
                        spdata.luuDataFlagBKChuaGhiTrongSP(Bien.bienghi);
                    }*/

            }
            /*
            if(kt && ktlast) {

                FlagupdateDB = true;
                Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                Bien.bienbkall = spdata.getDataBKALLTrongSP();
                Bien.bienbkcg = spdata.getDataBKCGTrongSP();
                Bien.bienbkdg = spdata.getDataBKDGTrongSP();
                Bien.bienbkdghn = spdata.getDataBKDGHomNayTrongSP();
                Log.e("flag flagghi 2", String.valueOf(Bien.bienghi));
                Log.e("flag flagall 2", String.valueOf( Bien.bienbkall));
                Log.e("flag flagcg 2", String.valueOf(Bien.bienbkcg));
                Log.e("flag flagdg 2", String.valueOf(Bien.bienbkdg));
                LichSuDTO ls = new LichSuDTO();
                ls.setNoiDungLS("Lưu dữ liệu ghi nước");
                ls.setMaLenh("BK");
                String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                ls.setThoiGianLS(thoigian1);
                lichsudao.addTable_History(ls);
                status = "Thành công";
                publishProgress(String.valueOf(status));

            }
            else{
                FlagupdateDB = false;
            }
            */

            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];

            mstatus.setText(status);
          //  prgTime.setProgress(Integer.parseInt(status));

            // update giá trị ở TextView
           // prgTime.setText(getString(R.string.load_status));
            if(status.equalsIgnoreCase("Thành công")) {
                mstatus.setVisibility(View.GONE);
                table.setVisibility(View.VISIBLE);
            }
        }



        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            Boolean FlagupdateDB = true;


            String filename = "";
            String filenameLAST = "";
            boolean kt = false, ktlast = false;

            if (result.equals("")) {

                String mess = "";
                if (radioTatca.isChecked() == true) {
                  mess = getString(R.string.backup_listrong_tatca);
                } else if (radioDaghi.isChecked() == true) {
                    mess = getString(R.string.backup_listrong_daghi);
                } else if (radioDaghihomnay.isChecked() == true) {

                    mess = getString(R.string.backup_listrong_daghi);
                } else if (radioChuaghi.isChecked() == true) {
                mess = getString(R.string.backup_listrong_chuaghinuoc);

                 } else if (radioDaghiTheoDuong.isChecked() == true) {

                mess = getString(R.string.backup_listrong_daghi);
                 } else if (radioDaThu.isChecked() == true) {

                    mess = "Chưa có khách hàng nào được thu";
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(mess);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

            } else {


                if (radioTatca.isChecked() == true) {
//                    if (Bien.bienbkall == Bien.bienghi) {
//                        //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
//                    } else {
//                        Log.e("back up", "Vao tat ca");


                        String result_tatca_string = result;//taoJSONData_KH_TatCa(tenfile.getText().toString());


                        if (!result_tatca_string.equals("")) {
                                filename = thumucchuafile + "/" + THUMUCTATCA;
                                filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                                taoThuMuc(filename);
                                taoThuMuc(filenameLAST);

                                kt = writeFile(filename, tenfile.getText().toString(), result_tatca_string);
                            ktlast = writeFile(filenameLAST, TENFILETATCA_LAST, result_tatca_string);
                            spdata.luuDataFlagBKAllTrongSP(Bien.bienghi);
                            //   MediaScannerConnection.scanFile(con, new String[]{filename,filenameLAST}, null, null);
                        }
 //                   }
                } else if (radioDaghi.isChecked() == true) {
                    if (Bien.bienbkdg == Bien.bienghi) {
                        //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                    } else {
                        Log.e("back up", "Vao da ghi");


                        String result_daghi_string = result;//taoJSONData_KH_DaGhi(tenfile.getText().toString());


                        if (!result_daghi_string.equals("")) {

                            filename = thumucchuafile + "/" + THUMUCDAGHI;
                            filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                            taoThuMuc(filename);
                            taoThuMuc(filenameLAST);
                            kt = writeFile(filename, tenfile.getText().toString(), result_daghi_string);
                            ktlast = writeFile(filenameLAST, TENFILEDAGHI_LAST, result_daghi_string);
                            spdata.luuDataFlagBKDaghiTrongSP(Bien.bienghi);
                        }
                    }
                } else if (radioDaghihomnay.isChecked() == true) {
                   // if (Bien.bienbkdghn == Bien.bienghi) {
                        //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                   // } else {
                        Log.e("back up", "Vao da ghi");


                        String result_daghi_string = result;//taoJSONData_KH_DaGhiHomNay(tenfile.getText().toString());


                        if (!result_daghi_string.equals("")) {

                            filename = thumucchuafile + "/" + THUMUCDAGHIHOMNAY;
                            filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                            taoThuMuc(filename);
                            taoThuMuc(filenameLAST);
                            kt = writeFile(filename, tenfile.getText().toString(), result_daghi_string);
                            ktlast = writeFile(filenameLAST, TENFILEDAGHIHOMNAY_LAST, result_daghi_string);
                            spdata.luuDataFlagBKDaghiHomNayTrongSP(Bien.bienghi);
                        }
                  //  }
                } else if (radioChuaghi.isChecked() == true) {
                    if (Bien.bienbkcg == Bien.bienghi) {
                        //    taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                    } else {
                        Log.e("back up", "Vao chua ghi");

                        String result_chuaghi_string =result;// taoJSONData_KH_ChuaGhi(tenfile.getText().toString());


                        if (!result_chuaghi_string.equals("")) {

                            filename = thumucchuafile + "/" + THUMUCCHUAGHI;
                            filenameLAST = thumucchuafile + "/" + THUMUCLAST;
                            taoThuMuc(filename);
                            taoThuMuc(filenameLAST);
                            kt = writeFile(filename, tenfile.getText().toString(), result_chuaghi_string);
                            ktlast = writeFile(filenameLAST, TENFILECHUAGHI_LAST, result_chuaghi_string);
                            spdata.luuDataFlagBKChuaGhiTrongSP(Bien.bienghi);
                        }
                    }
                }
                else if (radioDaghiTheoDuong.isChecked() == true) {

                        Log.e("back up", "Vao da ghi");


                        String result_daghitheoduong_string = result;//taoJSONData_KH_DaGhi(tenfile.getText().toString());


                        if (!result_daghitheoduong_string.equals("")) {

                            filename = thumucchuafile + "/" + THUMUCDAGHITHEODUONG;

                            taoThuMuc(filename);

                            kt = writeFile(filename, tenfile.getText().toString(), result_daghitheoduong_string);

                            ktlast =true;
                        }

                } else if (radioDaThu.isChecked() == true) {

                    Log.e("back up", "Vao da thu");


                    String result_daghitheoduong_string = result;//taoJSONData_KH_DaGhi(tenfile.getText().toString());


                    String tenfilethu = "customersthu_";
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    tenfilethu += timeStamp + ".txt";
                    String result_thu = taoJSONData_KH_TatCa_thu(tenfilethu.trim());
                    if (!result_thu.equals("")) {
                        String filename1 = thumucchuafile + "/TUDONGCAPNHATTHU";

                        File f = new File(filename1);
                        if (!f.exists()) {
                            f.mkdirs();

                        }

                        boolean ktthu = writeFile(filename1, tenfilethu.trim(), result_thu);
//                        if (kt) {
//                            Toast.makeText(con, "Tự động cập nhật thành công", Toast.LENGTH_SHORT).show();
//                        }

                    }
                    if (!result_daghitheoduong_string.equals("")) {

                        filename = thumucchuafile + "/" + THUMUCDATHU;

                        taoThuMuc(filename);

                        kt = writeFile(filename, tenfile.getText().toString(), result_daghitheoduong_string);

                        ktlast = true;

                    }

                }

                if (kt && ktlast) {

                    FlagupdateDB = true;
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienbkall = spdata.getDataBKALLTrongSP();
                    Bien.bienbkcg = spdata.getDataBKCGTrongSP();
                    Bien.bienbkdg = spdata.getDataBKDGTrongSP();
                    Bien.bienbkdghn = spdata.getDataBKDGHomNayTrongSP();
                    Log.e("flag flagghi 2", String.valueOf(Bien.bienghi));
                    Log.e("flag flagall 2", String.valueOf(Bien.bienbkall));
                    Log.e("flag flagcg 2", String.valueOf(Bien.bienbkcg));
                    Log.e("flag flagdg 2", String.valueOf(Bien.bienbkdg));
                    LichSuDTO ls = new LichSuDTO();
                    ls.setNoiDungLS("Lưu dữ liệu ghi nước");
                    ls.setMaLenh("BK");
                    String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    ls.setThoiGianLS(thoigian1);
                    lichsudao.addTable_History(ls);


                } else {
                    FlagupdateDB = false;
                }


                if (FlagupdateDB) {
                    //Hiển thị dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.backup_thanhcong);
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
                    mstatus.setVisibility(View.GONE);
                    table.setVisibility(View.VISIBLE);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.error_backup);
                    // thiết lập nội dung cho dialog


                    alertDialogBuilder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            // button "no" ẩn dialog đi
                            mstatus.setVisibility(View.GONE);
                            table.setVisibility(View.VISIBLE);

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

    private void loadDataDuong(){
        listduong = new ArrayList<>();
        arrDuong = new ArrayList<>();
       listduong = duongdao.getAllDuong();
        for(int i = 0; i<listduong.size();i++){
            String sDuong  = listduong.get(i).getMaDuong() +"."+listduong.get(i).getTenDuong();
            sDuong.trim();
            arrDuong.add(sDuong);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arrDuong
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinDuong.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinDuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                maduong = arrDuong.get(position).substring(0,2);
                String tenfileluu = taoTenFile("DaGhiTheoDuong_"+maduong);
                tenfile.setText(tenfileluu);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                maduong = arrDuong.get(0).substring(0,2);

            }
        });


    }

    private ListRequestObjectThu taoJSONData_KH_DaThu_CapNhatServer2(String tendanhsach) {
        ListRequestObjectThu jsondata = new ListRequestObjectThu();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuongThu> listtiwaread = new ArrayList<ListKHTheoDuongThu>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangDaThuServer());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObjectThu> listkh = new ArrayList<RequestObjectThu>();
            listkh = khachangdao.getAllKHDaThuTheoDuongChuaCapNhat1(maduong);
            ListKHTheoDuongThu tiwaread = new ListKHTheoDuongThu();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if (listkh.size() > 0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json = "";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if (listtiwaread.size() > 0) {
            jsondata.setListTiwaread(listtiwaread);
            jsondata.setTenDS(tendanhsach);
            jsondata.setTongSLkh(soluongKH);
            Log.e("tong sokh", String.valueOf(soluongKH));
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
            Log.e("json data", json);
        } else {
            // spdata.luuDataFlagBKDaThuTrongSP(-1);
            // Bien.bienbkdg = -1;
            jsondata = null;

        }
        return jsondata;
    }

    private ListRequestObjectThu taoJSONData_KH_DaThuTatCa_CapNhatServer2(String tendanhsach) {
        ListRequestObjectThu jsondata = new ListRequestObjectThu();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListKHTheoDuongThu> listtiwaread = new ArrayList<ListKHTheoDuongThu>();
        String soluongKH = String.valueOf(khachangdao.countKhachHangCapNhatServerThu());
        listduong = duongdao.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong().trim();
            String tenduong = listduong.get(thutuduong).getTenDuong().trim();
            List<RequestObjectThu> listkh = new ArrayList<RequestObjectThu>();
            listkh = khachangdao.getAllKHTatCaDaThuTheoDuong(maduong);
            ListKHTheoDuongThu tiwaread = new ListKHTheoDuongThu();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if (listkh.size() > 0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json = "";
        Log.e("list tiwaread", String.valueOf(listtiwaread.size()));
        if (listtiwaread.size() > 0) {
            jsondata.setListTiwaread(listtiwaread);
            jsondata.setTenDS(tendanhsach);
            jsondata.setTongSLkh(soluongKH);
            Log.e("tong sokh", String.valueOf(soluongKH));
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
            Log.e("json data", json);
        } else {
            // spdata.luuDataFlagBKDaThuTrongSP(-1);
            // Bien.bienbkdg = -1;
            jsondata = null;

        }
        return jsondata;
    }

    public class UpdateThongTinThuNuoc extends AsyncTask<String, String, String> {
        String status = "";
        String kyhd = spdata.getDataKyHoaDonTrongSP();// "201710";
        //  Log.e("tendanhsach kyhd",kyhd);
        String json = "";//taoJSONData_KH_DaGhi_CapNhatServer(kyhdsau);
        String result = "";
        //ListJsonData jsondata = new ListJsonData();
        ListRequestObjectThu jsondata = new ListRequestObjectThu();

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn = null;

            //Get Danh Sach Duong khoa so

            String fileContent = "";


            int soluongcapnhat = 0;
            if (radioDaghiTheoDuong.isChecked()) {
                soluongcapnhat = khachangdao.countKhachHangCapNhatServerThu();
                Log.e("soluongtatcathu", String.valueOf(soluongcapnhat));
            } else if (radioDaghihomnay.isChecked()) {
                soluongcapnhat = khachangdao.countKhachHangDaThuServer();
            }
            if (soluongcapnhat > 0) {

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
                    if (radioDaghiTheoDuong.isChecked()) {

                        jsondata = taoJSONData_KH_DaThuTatCa_CapNhatServer2(kyhd);
                    } else if (radioDaghihomnay.isChecked()) {
                        jsondata = taoJSONData_KH_DaThu_CapNhatServer2(kyhd);


                    }
                    if (jsondata != null) {

                        if (jsondata.getListTiwaread().size() > 0) {
                            for (ListKHTheoDuongThu lista : jsondata.getListTiwaread()) {
                                for (RequestObjectThu kh : lista.getTiwareadList()) {
                                    if (khachangdao.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "3")) {
                                        //capnhattrangthai++;
                                    }
                                }
                            }
                        }
                        JSONRequestObjectThu requestjson = new JSONRequestObjectThu();
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
                    } else {
                        result = "DAGHIKHOASO";
                    }
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
            } else {
                result = "RONG";
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];

            mstatus.setText(status);

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

            if (result.equals("DAKHOASOHET")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Tất cả các đường đã khóa sổ. Không thể cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else if (result.equals("RONG")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Không còn KH nào để cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else if (result.equals("DAGHIKHOASO")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Các đường đã ghi bị khóa sổ. Không thể cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật
                if (jsondata != null) {
                    if (jsondata.getListTiwaread().size() > 0) {
                        for (ListKHTheoDuongThu lista : jsondata.getListTiwaread()) {
                            for (RequestObjectThu kh : lista.getTiwareadList()) {
                                if (khachangdao.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "2")) {
                                    //capnhattrangthai++;
                                }
                            }
                        }
                    }
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình cập nhật dữ liệu");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
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
                        Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Cật nhật dữ liệu thành công " + String.valueOf(soluongkhachhangdacapnhat) + "/" + String.valueOf(jsondata.getTongSLkh()));
                        // thiết lập nội dung cho dialog

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mstatus.setVisibility(View.GONE);
                                table.setVisibility(View.VISIBLE);

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
                                        KhachHangDTO kherror = khachangdao.getKHTheoMaKH(maKH.trim());
                                        String maduong = khachangdao.getMaDuongTheoMaKhachHang(maKH.trim());
                                        String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang();
                                        myListerror.add(chuoihienthi);
                                        DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                    }

                                }
                                if (DanhSachLoi.size() > 0) {
                                    for (String ma : DanhSachLoi) {
                                        if (khachangdao.updateTrangThaiThuCapNhat(ma, "2")) {
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
                                    //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                    Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);
                                            // button "no" ẩn dialog đi
                                        }
                                    });
                                    alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);

                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Backup_Activity.this);
                                            LayoutInflater inflater = getLayoutInflater();
                                            View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                            alertDialog.setView(convertView);
                                            alertDialog.setTitle("Danh sách lỗi");
                                            ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Backup_Activity.this, android.R.layout.simple_list_item_1, myListerror);
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
                                int capnhatlaidanhsachjson = 0;
                                if (jsondata.getListTiwaread().size() > 0) {
                                    for (ListKHTheoDuongThu lista : jsondata.getListTiwaread()) {
                                        for (RequestObjectThu kh : lista.getTiwareadList()) {
                                            if (khachangdao.updateTrangThaiThuCapNhat(kh.getMaKhachHang().toString().trim(), "2")) {
                                                capnhatlaidanhsachjson++;
                                            }
                                        }
                                    }
                                }

                                if (capnhatlaidanhsachjson == Integer.parseInt(jsondata.getTongSLkh())) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);
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
                } catch (Exception e) {

                }
            }

        }


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
                        Log.e("Duong KS:",maduong);
                    }

                    if (objTiwaread.has("khoaso")) {
                        khoaso = objTiwaread.getString("khoaso").trim();
                        Log.e("khoaso:",khoaso);
                    }

                    if(duongdao.updateDuongKhoaSo(maduong,khoaso))
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

            List<DuongDTO> duongchuakhoaso =  duongdao.getAllDuongChuaKhoaSo();

            if(duongchuakhoaso.size() <=0)
            {
                result=  "DAKHOASOHET";
            }
            else {

            int soluongcapnhat =  0;
                if(radioDaghi.isChecked()) {
                    soluongcapnhat = khachangdao.countKhachHangCapNhatServer();
                } else {
                    soluongcapnhat = khachangdao.countKhachHangDaGhiServer();
                }
                if(soluongcapnhat > 0) {

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
                        if (radioTatca.isChecked()) {

                            jsondata = taoJSONData_KHDaGhiTatCa_CapNhatServer2(kyhd);
                        } else if (radioDaghi.isChecked()) {
                            jsondata = taoJSONData_KH_DaGhi_CapNhatServer2(kyhd);
                        }
                        if (jsondata != null) {
                            if (jsondata.getListTiwaread().size() > 0) {
                                //Cập nhật tất cả tạm là đã update
                                for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                                    for (RequestObject kh : lista.getTiwareadList()) {
                                        if (khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "1")) {
                                            //capnhattrangthai++;
                                        }
                                    }
                                }
                                // JSONListTiwaread requestjson  = new JSONListTiwaread();
                                //  requestjson.setJsontiwaread(jsondata);
                            }

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
                        } else {
                            result = "DAGHIKHOASO";
                        }
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
                else{
                    result ="RONG";
                }
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];

            mstatus.setText(status);

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Tất cả các đường đã khóa sổ. Không thể cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else if(result.equals("RONG"))
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Không còn KH nào để cập nhật hoặc đường đã bị khóa sổ");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else  if(result.equals("DAGHIKHOASO"))
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Các đường đã ghi bị khóa sổ. Không thể cập nhật.");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }

            else if (result.equals("0")) {
                //Bị lỗi cập nhật lại là chưa có cập nhật
                if(jsondata !=null ) {
                    if( jsondata.getListTiwaread().size() >0){
                        for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                            for (RequestObject kh : lista.getTiwareadList()) {
                                if (khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
                                    //capnhattrangthai++;
                                }
                            }
                        }
                    }
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đã xảy ra lỗi trong quá trình cập nhật dữ liệu");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mstatus.setVisibility(View.GONE);
                        table.setVisibility(View.VISIBLE);
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
                        Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Cật nhật dữ liệu thành công "+ String.valueOf(soluongkhachhangdacapnhat) +"/"+ String.valueOf(jsondata.getTongSLkh()));
                        // thiết lập nội dung cho dialog

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mstatus.setVisibility(View.GONE);
                                table.setVisibility(View.VISIBLE);

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
                                String duongkhoaso = "";
                                if (tong.length() == 0) {
                                    if (jsonobj.has("danhsachDuongKhoaSo")) {
                                        JSONArray dskhoaso = jsonobj.getJSONArray("danhsachDuongKhoaSo");

                                        for (int i = 0; i < dskhoaso.length(); i++) {
                                            JSONObject objKHLOI = dskhoaso.getJSONObject(i);

                                            if (objKHLOI.has("maduong")) {
                                                String maDuong = objKHLOI.getString("maduong").trim();
                                                duongkhoaso += maDuong + " ";
                                                duongdao.updateDuongKhoaSo(maDuong, "1");
                                            }


                                        }
                                    }

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Cập nhật không thành công.Do đường " + duongkhoaso + " đã khóa sổ");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);


                                            // button "no" ẩn dialog đi
                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();


                                } else {

                                    for (int i = 0; i < tong.length(); i++) {
                                        JSONObject objKHLOI = tong.getJSONObject(i);

                                        if (objKHLOI.has("maKH")) {
                                            String maKH = objKHLOI.getString("maKH").trim();
                                            KhachHangDTO kherror = khachangdao.getKHTheoMaKH(maKH.trim());
                                            String maduong = khachangdao.getMaDuongTheoMaKhachHang(maKH.trim());
                                            String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang();
                                            myListerror.add(chuoihienthi);
                                            DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                        }

                                    }
                                    if (DanhSachLoi.size() > 0) {
                                        for (String ma : DanhSachLoi) {
                                            if (khachangdao.updateTrangThaiCapNhat(ma, "0")) {
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
                                        //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                        Toast.makeText(Backup_Activity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                        // khởi tạo dialog
                                        alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                        // thiết lập nội dung cho dialog

                                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                mstatus.setVisibility(View.GONE);
                                                table.setVisibility(View.VISIBLE);
                                                // button "no" ẩn dialog đi
                                            }
                                        });
                                        alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                mstatus.setVisibility(View.GONE);
                                                table.setVisibility(View.VISIBLE);

                                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Backup_Activity.this);
                                                LayoutInflater inflater = getLayoutInflater();
                                                View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                                alertDialog.setView(convertView);
                                                alertDialog.setTitle("Danh sách lỗi");
                                                ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Backup_Activity.this, android.R.layout.simple_list_item_1, myListerror);
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
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                int capnhatlaidanhsachjson = 0;
                                if (jsondata.getListTiwaread().size() > 0) {
                                    for (ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                                        for (RequestObject kh : lista.getTiwareadList()) {
                                            if (khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(), "0")) {
                                                capnhatlaidanhsachjson++;
                                            }
                                        }
                                        }
                                }

                                if (capnhatlaidanhsachjson == Integer.parseInt(jsondata.getTongSLkh())) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Backup_Activity.this);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage("Đã cập nhật thành công: " + soluongkhachhangdacapnhat + "/" + jsondata.getTongSLkh() + ".Kiểm tra lại khách hàng cập nhật thất bại");
                                    // thiết lập nội dung cho dialog

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mstatus.setVisibility(View.GONE);
                                            table.setVisibility(View.VISIBLE);
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
                }
                catch(Exception e){
                    for(ListKHTheoDuong lista : jsondata.getListTiwaread()) {
                        for(RequestObject kh : lista.getTiwareadList())
                        {
                            if(khachangdao.updateTrangThaiCapNhat(kh.getMaKhachHang().toString().trim(),"0")){
                                //capnhattrangthai++;
                            }
                        }
                    }
                }

            }

        }


    }

    private String taoJSONData_KH_TatCa_thu(String tendanhsach) {
        JSONBACKUPTHU jsondata = new JSONBACKUPTHU();
        KhachHangThuDAO khachhangdao = new KhachHangThuDAO(con);
        ThanhToanDAO thanhToanDAO = new ThanhToanDAO(con);
        DuongThuDAO duongthudao = new DuongThuDAO(con);
        //Lấy danh sách tất cả các đường
        List<JSONDUONGTHU> listduong = duongthudao.getAllDuongThu();
        List<JSONKHTHU> listkh = khachhangdao.getAllKHThu();
        List<JSONTHANHTOANTHU> listthanhtoan = thanhToanDAO.GetAllThanhToanThu();


        String json = "";
        if (listduong.size() > 0 && listkh.size() > 0 && listthanhtoan.size() > 0) {
            jsondata.setListduong(listduong);
            jsondata.setListkh(listkh);
            jsondata.setListthanhtoan(listthanhtoan);

            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        }

        return json;
    }



}
