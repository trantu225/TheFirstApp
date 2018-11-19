package tiwaco.thefirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.ListJsonData;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.File.XuLyFile;

public class LoadFromServerThuActivity extends AppCompatActivity {
    private String filename = "";
    DonutProgress prgTime;
    LinearLayout layout_noidungload;
    Button btngetData, btnthoat;
    EditText editKyHD, edtNV, edtnhanvientai, edtmatkhau;
    TextView lb_nvtai, lb_mktai;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    String duongdanfile = "";

    DuongDAO duongDAO;
    Context con;
    KhachHangDAO khachhangDAO;
    ThanhToanDAO thanhtoanDAO;
    TinhTrangTLKDAO tinhtrangtlkdao;
    TextView lv_nhanvien, lb_matkhau;
    SPData spdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata_server);
        getSupportActionBar().hide();
        con = LoadFromServerThuActivity.this;
        duongDAO = new DuongDAO(LoadFromServerThuActivity.this);
        khachhangDAO = new KhachHangDAO(LoadFromServerThuActivity.this);
        thanhtoanDAO = new ThanhToanDAO(LoadFromServerThuActivity.this);
        tinhtrangtlkdao = new TinhTrangTLKDAO(con);
        spdata = new SPData(con);
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        layout_noidungload = (LinearLayout) findViewById(R.id.lay_noidungload);
        btngetData = (Button) findViewById(R.id.btn_GetDaTa);
        btnthoat = (Button) findViewById(R.id.btn_Thoat);

        editKyHD = (EditText) findViewById(R.id.edt_kyhd);
        edtNV = (EditText) findViewById(R.id.edt_nhanvien);

        lb_nvtai = (TextView) findViewById(R.id.lb_nhanvientai);
        lb_mktai = (TextView) findViewById(R.id.lb_matkhau);
        edtnhanvientai = (EditText) findViewById(R.id.edt_nhanvientai);
        edtmatkhau = (EditText) findViewById(R.id.edt_matkhau);
        lb_nvtai.setVisibility(View.VISIBLE);
        lb_mktai.setVisibility(View.VISIBLE);
        edtnhanvientai.setVisibility(View.VISIBLE);
        edtmatkhau.setVisibility(View.VISIBLE);
        edtnhanvientai.setText("chau");
        edtmatkhau.setText("123");
        edtnhanvientai.setEnabled(false);
        edtmatkhau.setEnabled(false);


        prgTime.setProgress(0);
        prgTime.setText("0 %");
        if (spdata.getDataNhanVienTrongSP().trim().equals("admin")) {
            edtNV.setEnabled(true);
            edtNV.setTextColor(R.color.default_active_item_color);
        } else {
            edtNV.setText(spdata.getDataNhanVienTrongSP().trim());
            edtNV.setEnabled(false);

        }
        if (!spdata.getDataKyHoaDonTrongSP().trim().equals("")) {
            editKyHD.setText(spdata.getDataKyHoaDonTrongSP().trim());
        }

        duongdanfile = getString(R.string.API_GetListTiwareadDataThu);
        btngetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new GetUserList().execute("http://192.168.1.91/Service1.svc/GetListUser");
                try {

                    String nhanvientai = "chau";//edtnhanvientai.getText().toString().trim();
                    String matkhautai = "123";//edtmatkhau.getText().toString().trim();
                    if (!nhanvientai.equals("") && !matkhautai.equals("")) {

                        String manv = edtNV.getText().toString().trim(); // Lay manv
                        Log.e("nhap", editKyHD.getText().toString());
                        String thang = editKyHD.getText().toString().substring(4, 6);
                        Log.e("thang", thang);
                        String nam = editKyHD.getText().toString().substring(0, 4);
                        Log.e("nam", nam);

                        duongdanfile += "/" + nhanvientai + "/" + matkhautai + "/" + manv + "/" + thang + "/" + nam;
                        Log.e("duongdan", duongdanfile);
                        layout_noidungload.setVisibility(View.GONE);
                        prgTime.setVisibility(View.VISIBLE);

                        askPermissionAndReadFile();
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa nhập thông tin nhân viên hoặc mật khẩu tải dữ liệu");
                        // thiết lập nội dung cho dialog

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                layout_noidungload.setVisibility(View.VISIBLE);
                                prgTime.setVisibility(View.GONE);

                                // button "no" ẩn dialog đi
                            }
                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        // hiển thị dialog
                    }
                } catch (Exception loi) {
                    //Hiển thị dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Kết nối thất bại.Hãy kiểm tra lại thông tin nhân viên và kỳ hóa đơn!");
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            layout_noidungload.setVisibility(View.VISIBLE);
                            prgTime.setVisibility(View.GONE);

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
        });

        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoadFromServerThuActivity.this, StartActivity.class);
                startActivity(myIntent);
                LoadFromServerThuActivity.this.finish();

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
            loadData(duongdanfile);

        }
    }

    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


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
                        loadData(duongdanfile);

                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // writeFile(duongdanfile,dataGhi);
                        //backup file json
                    }
                }
                case 1: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the

                        // contacts-related task you need to do.

                        GPSTracker gps = new GPSTracker(con, LoadFromServerThuActivity.this);

                        // Check if GPS enabled
                        if (gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            String vido = String.valueOf(latitude);
                            String kinhdo = String.valueOf(longitude);

                            Log.e("Toa do", vido + "-" + kinhdo);
                            // \n is for new line
                            //    Toast.makeText(getApplicationContext(), "REQUEST: Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            askPermissionAndReadFile();
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

                }

            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


    public class MyJsonTaskDatabasefromServer extends AsyncTask<String, String, String> {

        int sokhdacapnhat = 0;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            HttpURLConnection conn = null;
            BufferedReader reader;
            String FlagupdateDB = "TB"; // TC, TB, EMPTY
            if (!isCancelled()) {
                //Duong dan file
                String duongdan = params[0];
                String codulieu = params[1];
                String fileContent = "";

                int sokhco = 0;
                Log.e("duongdan", duongdan);
                Log.e("codulieu", codulieu);
                try {
                    try {
                        final URL url = new URL(duongdan);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                        conn.setRequestMethod("GET");
                        int result = conn.getResponseCode();
                        if (result == 200) {

                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                // long status = (i+1) *100/line.length();
                                //     String.valueOf(status)
                                publishProgress("0", "server");
                                fileContent = line;

                            }
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
                    //Lay json tu server
                    JSONObject jsonobj = null;
                    try {
                        jsonobj = new JSONObject(fileContent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (jsonobj.has("tenDS")) {


                        try {
                            String tenDS = jsonobj.getString("tenDS");
                            // String kyhd = "082017"; //cắt chuỗi từ tên DS
                            //Luu ky hd vao SP
                            spdata.luuDataKyHoaDonTrongSP(tenDS);
                            Log.e("kyhddaluu", tenDS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    if (jsonobj.has("tongSLkh")) {


                        try {
                            String tong = jsonobj.getString("tongSLkh");
                            // String kyhd = "082017"; //cắt chuỗi từ tên DS
                            //Luu ky hd vao SP
                            sokhco = Integer.parseInt(tong);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if (sokhco > 0) {
                        if (codulieu.equals("1")) {
                            Log.e("DaCoDuLieu", "yes");
                            MyDatabaseHelper mydata = new MyDatabaseHelper(con);
                            SQLiteDatabase db = mydata.openDB();
                            mydata.resetDatabase(db);
                        } else {
                            Log.e("DaCoDuLieu", "no");
                        }
                        //THêm database loại tinh trang tlk
                        List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
                        for (int tt = 0; tt < listt.size(); tt++) {
                            tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
                        }

                        if (jsonobj.has("ListTiwaread")) {
                            try {
                                JSONArray listtiwaread = jsonobj.getJSONArray("ListTiwaread");
                                List<DuongDTO> listduong = new ArrayList<>();
                                for (int i = 0; i < listtiwaread.length(); i++) {
                                    JSONObject objTiwaread = listtiwaread.getJSONObject(i);
                                    String maduong = "";
                                    String tenduong = "";
                                    if (objTiwaread.has("Maduong")) {
                                        maduong = objTiwaread.getString("Maduong").trim();
                                    }

                                    if (objTiwaread.has("Tenduong")) {
                                        tenduong = objTiwaread.getString("Tenduong").trim();
                                    }
                                    Log.e("kiem tra da ton tai hay chua", String.valueOf(duongDAO.countDuong()));
                                    if (duongDAO.countDuong() <= 0) {


                                        Log.e("Them database_duong: ", "chay zo day rui");
                                        DuongDTO duong = new DuongDTO(maduong, tenduong, 0, "0", 0);
                                        boolean kt = duongDAO.addTable_Duong(duong);


                                        if (kt) {
                                            listduong.add(duong);
                                            Log.e("Them database_duong: " + maduong, "Thanh cong");
                                        } else {
                                            Log.e("Them database_duong: " + maduong, "ko Thanh cong");

                                        }
                                        //loadDataDuongfromDB();
                                    } else {
                                        if (duongDAO.checkExistDuong(maduong)) {
                                            //    loadDataDuongfromDB();
                                            Log.e("Them database_duong: ", " Da ton tai duong nay");
                                        } else {
                                            DuongDTO duong = new DuongDTO(maduong, tenduong, 0, "0", 0);
                                            boolean kt = duongDAO.addTable_Duong(duong);
                                            if (kt) {
                                                Log.e("Them database_duong: " + maduong, "Thanh cong");
                                            } else {
                                                Log.e("Them database_duong: " + maduong, "ko Thanh cong");

                                            }
                                            //     loadDataDuongfromDB();
                                        }
                                    }

                                    if (objTiwaread.has("LisKHThu")) {
                                        Log.e("Them database_KH: ", "Lay JSONARRAY");
                                        JSONArray listKH = objTiwaread.getJSONArray("LisKHThu");
                                        Log.e("Them database_KH: ", "JSONARRAY_LENGTH " + listKH.length());
                                        for (int j = 0; j < listKH.length(); j++) {
                                            JSONObject objKH = listKH.getJSONObject(j);
                                            String ChiSo = "";
                                            String ChiSo1 = "";
                                            String ChiSo1con = "0";
                                            String ChiSo2 = "";
                                            String ChiSo2con = "0";
                                            String ChiSo3 = "";
                                            String ChiSo3con = "0";
                                            String ChiSocon = "";
                                            String DanhBo = "";
                                            String DiaChi = "";
                                            String DienThoai = "";
                                            String GhiChu = "";
                                            String Lat = "";
                                            String Lon = "";
                                            String MaKhachHang = "";
                                            String NhanVien = "";
                                            String SLTieuThu = "";
                                            String SLTieuThu1 = "";
                                            String SLTieuThu1con = "0";
                                            String SLTieuThu2 = "";
                                            String SLTieuThu2con = "0";
                                            String SLTieuThu3 = "";
                                            String SLTieuThu3con = "0";
                                            String SLTieuThucon = "";
                                            String STT = "";
                                            String TenKhachHang = "";
                                            String ThoiGian = "";
                                            String TrangThaiTLK = "";
                                            String chitietloai = "";
                                            String cotlk = "";
                                            String dinhmuc = "";
                                            String hieutlk = "";
                                            String loaikh = "";
                                            String masotlk = "";
                                            String loaikhcu = "";
                                            String ntsh = "";
                                            String novat = "";
                                            String m31 = "";
                                            String m32 = "";
                                            String m33 = "";
                                            String m34 = "";
                                            String tien1 = "";
                                            String tien2 = "";
                                            String tien3 = "";
                                            String tien4 = "";
                                            String tiennuoc = "";
                                            String tienthue = "";
                                            String phi = "";
                                            String tongcong = "";
                                            String ngaythanhtoan = "";
                                            String capnhatghi = "1";
                                            String ghichuthu = "";

                                            if (objKH.has("ChiSo")) {
                                                ChiSo = objKH.getString("ChiSo").toString().trim();
                                            }

                                            if (objKH.has("ChiSo1")) {
                                                ChiSo1 = objKH.getString("ChiSo1").toString().trim();
                                            }
//                                if(objKH.has("ChiSo1con")){
//                                    ChiSo1con = objKH.getString("ChiSo1con").toString().trim();
//                                }

                                            if (objKH.has("ChiSo2")) {
                                                ChiSo2 = objKH.getString("ChiSo2").toString().trim();
                                            }
//                                if(objKH.has("ChiSo2con")){
//                                    ChiSo2con = objKH.getString("ChiSo2con").toString().trim();
//                                }

                                            if (objKH.has("ChiSo3")) {
                                                ChiSo3 = objKH.getString("ChiSo3").toString().trim();
                                            }
//                                if(objKH.has("ChiSo3con")){
//                                    ChiSo3con = objKH.getString("ChiSo3con").toString().trim();
//                                }

//                                if(objKH.has("ChiSocon")){
//                                    ChiSocon = objKH.getString("ChiSocon").toString().trim();
//                                }
                                            if (objKH.has("DanhBo")) {
                                                DanhBo = objKH.getString("DanhBo").toString().trim();
                                            }

                                            if (objKH.has("DiaChi")) {
                                                DiaChi = objKH.getString("DiaChi").toString().trim();
                                            }
                                            if (objKH.has("DienThoai")) {
                                                DienThoai = objKH.getString("DienThoai").toString().trim();
                                            }

                                            if (objKH.has("GhiChu")) {
                                                GhiChu = objKH.getString("GhiChu").toString().trim();
                                            }
                                            if (objKH.has("Lat")) {
                                                Lat = objKH.getString("Lat").toString().trim();
                                            }

                                            if (objKH.has("Lon")) {
                                                Lon = objKH.getString("Lon").toString().trim();
                                            }
                                            if (objKH.has("MaKhachHang")) {
                                                MaKhachHang = objKH.getString("MaKhachHang").toString().trim();
                                            }

                                            if (objKH.has("NhanVien")) {
                                                NhanVien = objKH.getString("NhanVien").toString().trim();
                                            }
                                            if (objKH.has("SLTieuThu")) {
                                                SLTieuThu = objKH.getString("SLTieuThu").toString().trim();
                                            }

                                            if (objKH.has("SLTieuThu1")) {
                                                SLTieuThu1 = objKH.getString("SLTieuThu1").toString().trim();
                                            }
                                            if (objKH.has("nhanvien")) {
                                                NhanVien = objKH.getString("nhanvien").toString().trim();
                                            }

                                            if (objKH.has("lat")) {
                                                Lat = objKH.getString("lat").toString().trim();
                                            }
                                            if (objKH.has("lon")) {
                                                Lon = objKH.getString("lon").toString().trim();
                                            }

                                            if (objKH.has("thoigian")) {
                                                ThoiGian = objKH.getString("thoigian").toString().trim();
                                            }

                                            if (objKH.has("SLTieuThu3")) {
                                                SLTieuThu3 = objKH.getString("SLTieuThu3").toString().trim();
                                            }
                                            if (objKH.has("trangthaitlk")) {
                                                TrangThaiTLK = objKH.getString("trangthaitlk").toString().trim();
                                            }


                                            if (objKH.has("STT")) {
                                                STT = objKH.getString("STT").toString().trim();
                                            }

                                            if (objKH.has("TenKhachHang")) {
                                                TenKhachHang = objKH.getString("TenKhachHang").toString().trim();
                                            }
                                            if (objKH.has("ThoiGian")) {
                                                ThoiGian = objKH.getString("ThoiGian").toString().trim();
                                            }

                                            if (objKH.has("TrangThaiTLK")) {
                                                TrangThaiTLK = objKH.getString("TrangThaiTLK").toString().trim();
                                            }
                                            if (objKH.has("chitietloai")) {
                                                chitietloai = objKH.getString("chitietloai").toString().trim();
                                            }

                                            if (objKH.has("cotlk")) {
                                                cotlk = objKH.getString("cotlk").toString().trim();
                                            }
                                            if (objKH.has("dinhmuc")) {
                                                dinhmuc = objKH.getString("dinhmuc").toString().trim();
                                            }

                                            if (objKH.has("hieutlk")) {
                                                hieutlk = objKH.getString("hieutlk").toString().trim();
                                            }
                                            if (objKH.has("loaikh")) {
                                                loaikh = objKH.getString("loaikh").toString().trim();
                                                loaikhcu = loaikh;
                                            }

                                            if (objKH.has("masotlk")) {
                                                masotlk = objKH.getString("masotlk").toString().trim();
                                            }


                                            if (objKH.has("NTSH")) {
                                                ntsh = objKH.getString("NTSH").toString().trim();
                                            }

                                            if (objKH.has("NOVAT")) {
                                                novat = objKH.getString("NOVAT").toString().trim();
                                            }

                                            if (objKH.has("tiennuoc")) {
                                                tiennuoc = objKH.getString("tiennuoc").toString().trim();
                                            }
                                            if (objKH.has("phi")) {
                                                phi = objKH.getString("phi").toString().trim();
                                            }
                                            if (objKH.has("thue")) {
                                                tienthue = objKH.getString("thue").toString().trim();
                                            }
                                            if (objKH.has("tongcong")) {
                                                tongcong = objKH.getString("tongcong").toString().trim();
                                            }
                                            if (objKH.has("ghichuthu")) {
                                                ghichuthu = objKH.getString("ghichuthu").toString().trim();
                                            }

                                            if (objKH.has("m3muc1")) {
                                                m31 = objKH.getString("m3muc1").toString().trim();
                                            }
                                            if (objKH.has("m3muc2")) {
                                                m32 = objKH.getString("m3muc2").toString().trim();
                                            }
                                            if (objKH.has("m3muc3")) {
                                                m33 = objKH.getString("m3muc3").toString().trim();
                                            }
                                            if (objKH.has("m3muc4")) {
                                                m34 = objKH.getString("m3muc4").toString().trim();
                                            }
                                            if (objKH.has("tienmuc1")) {
                                                tien1 = objKH.getString("tienmuc1").toString().trim();
                                            }
                                            if (objKH.has("tienmuc2")) {
                                                tien2 = objKH.getString("tienmuc2").toString().trim();
                                            }
                                            if (objKH.has("tienmuc3")) {
                                                tien3 = objKH.getString("tienmuc3").toString().trim();
                                            }
                                            if (objKH.has("tienmuc4")) {
                                                tien4 = objKH.getString("tienmuc4").toString().trim();
                                            }

                                            String thoigian1 = new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime());
                                            if (Integer.parseInt(spdata.getDataKyHoaDonTrongSP()) >= Integer.parseInt(thoigian1)) {
                                                if (NhanVien.equals("")) {
                                                    SLTieuThu = "";
                                                    ChiSo = "";
                                                    Lat = "";
                                                    Lon = "";
                                                    TrangThaiTLK = "";
                                                    ThoiGian = "";
                                                    capnhatghi = "0";

                                                }
                                            }
                                            KhachHangDTO kh = new KhachHangDTO();
                                            kh.setMaKhachHang(MaKhachHang);
                                            kh.setTenKhachHang(TenKhachHang);
                                            kh.setDanhBo(DanhBo);
                                            kh.setDiaChi(DiaChi);
                                            kh.setDienThoai(DienThoai);
                                            kh.setSTT(STT);
                                            kh.setTrangThaiTLK(TrangThaiTLK);
                                            kh.setChitietloai(chitietloai);
                                            kh.setCotlk(cotlk);
                                            kh.setDinhmuc(dinhmuc);
                                            kh.setHieutlk(hieutlk);
                                            kh.setLoaikh(loaikh);
                                            kh.setMasotlk(masotlk);
                                            kh.setGhiChu(GhiChu);
                                            kh.setChiSo(ChiSo);
                                            kh.setChiSocon(ChiSocon);
                                            kh.setChiSo1(ChiSo1);
                                            kh.setChiSo1con(ChiSo1con);
                                            kh.setChiSo2(ChiSo2);
                                            kh.setChiSo2con(ChiSo2con);
                                            kh.setChiSo3(ChiSo3);
                                            kh.setChiSo3con(ChiSo3con);
                                            kh.setGhichuthu(ghichuthu);
                                            kh.setSLTieuThu(SLTieuThu);
                                            kh.setSLTieuThu1(SLTieuThu1);
                                            kh.setSLTieuThu1con(SLTieuThu1con);
                                            kh.setSLTieuThu2(SLTieuThu2);
                                            kh.setSLTieuThu2con(SLTieuThu2con);
                                            kh.setSLTieuThu3(SLTieuThu3);
                                            kh.setSLTieuThu3con(SLTieuThu3con);
                                            kh.setSLTieuThucon(SLTieuThucon);
                                            kh.setLat(Lat);
                                            kh.setLon(Lon);
                                            kh.setThoiGian(ThoiGian);
                                            kh.setNhanVien(NhanVien);
                                            kh.setNTSH(ntsh);
                                            kh.setvat(novat);
                                            kh.setM3t1(m31);
                                            kh.setM3t2(m32);
                                            kh.setM3t3(m33);
                                            kh.setM3t4(m34);
                                            kh.setTien1(tien1);
                                            kh.setTien2(tien2);
                                            kh.setTien3(tien3);
                                            kh.setTien4(tien4);
                                            kh.setTienNuoc(tiennuoc);
                                            kh.setphi(phi);
                                            kh.setThue(tienthue);
                                            kh.settongcong(tongcong);
                                            kh.setNgaythanhtoan(ngaythanhtoan);


                                            Log.e("Them database_KH: ", "Da ton tai " + j + ":" + MaKhachHang + ":" + khachhangDAO.checkExistKH(MaKhachHang, maduong));
                                            boolean kt = khachhangDAO.addTable_KH(kh, maduong, capnhatghi);
                                            //SUABUG
                                            //boolean kt = khachhangDAO.updateTable_KH(kh);
                                            if (kt) {
                                                sokhdacapnhat++;
//                                                if(kh.getChiSo().equals("") && kh.getSLTieuThu().equals(""))
//                                                {
//
//                                                }
//                                                else{
//                                                    khachhangDAO.tinhTienNuoc(kh.getMaKhachHang().trim());
//                                                }
                                                Log.e("Them database_KH: " + MaKhachHang + " " + TenKhachHang, "Thanh cong");
                                            } else {
                                                Log.e("Them database_KH: " + MaKhachHang + " " + TenKhachHang, "ko Thanh cong");

                                            }


                                            // FlagupdateDB = kt;

                                            long status = (j + 1) * 100 / listKH.length();
                                            //     String.valueOf(status)
                                            publishProgress(String.valueOf(status));
                                            // Escape early if cancel() is called
                                            if (isCancelled()) break;
                                        }

                                    }

                                    if (objTiwaread.has("TiwareadList")) {

                                        JSONArray listKH = objTiwaread.getJSONArray("TiwareadList");
                                        Log.e("Them database_KH ThanhToan: ", "JSONARRAY_LENGTH " + listKH.length());
                                        for (int j = 0; j < listKH.length(); j++) {
                                            JSONObject objKH = listKH.getJSONObject(j);
                                            /*
                                              "ChiSoCu": "10",
                                              "ChiSoMoi": "14",
                                              "MaKhachHang": "05100463",
                                              "SLTieuThu": "4",
                                              "kyhd": "201807",
                                              "m3muc1": "4",
                                              "m3muc2": "0",
                                              "m3muc3": "0",
                                              "m3muc4": "0",
                                              "phi": "0.00",
                                              "thue": "1276.00",
                                              "tienmuc1": "25524",
                                              "tienmuc2": "0",
                                              "tienmuc3": "0",
                                              "tienmuc4": "0",
                                              "tiennuoc": "25524.00",
                                              "tongcong": "26800"




                                             */

                                            int ID = 0;
                                            String ChiSoCu = "";
                                            String ChiSoMoi = "";
                                            String MaKhachHang = "";
                                            String SLTieuThu = "";
                                            String KyHD = "";
                                            String GhiChuThu = "";
                                            String Lat = "";
                                            String Lon = "";
                                            String tiennuoc = "";
                                            String phi = "";
                                            String tongcong = "";
                                            String transid = "";
                                            String thue = "";
                                            String m3t1 = "";
                                            String m3t2 = "";
                                            String m3t3 = "";
                                            String m3t4 = "";
                                            String tien1 = "";
                                            String tien2 = "";
                                            String tien3 = "";
                                            String tien4 = "";
                                            String ngaythanhtoan = "";
                                            String NhanVienThu = "";
                                            String CapNhatThu = "0";
                                            String BienLai = "";

                                            if (objKH.has("BienLai")) {
                                                BienLai = objKH.getString("BienLai").toString().trim();
                                            }
                                            if (objKH.has("TransactionID")) {
                                                if (objKH.getString("TransactionID") != null) {
                                                    transid = objKH.getString("TransactionID").toString().trim();
                                                }
                                            }
                                            if (objKH.has("ChiSoCu")) {
                                                ChiSoCu = objKH.getString("ChiSoCu").toString().trim();
                                            }

                                            if (objKH.has("ChiSoMoi")) {
                                                ChiSoMoi = objKH.getString("ChiSoMoi").toString().trim();
                                            }

                                            if (objKH.has("MaKhachHang")) {
                                                MaKhachHang = objKH.getString("MaKhachHang").toString().trim();
                                            }


                                            if (objKH.has("SLTieuThu")) {
                                                SLTieuThu = objKH.getString("SLTieuThu").toString().trim();
                                            }

                                            if (objKH.has("kyhd")) {
                                                KyHD = objKH.getString("kyhd").toString().trim();
                                            }


                                            if (objKH.has("GhiChuThu")) {
                                                GhiChuThu = objKH.getString("GhiChuThu").toString().trim();
                                            }
//                                            if(objKH.has("lon")){
//                                                Lon = objKH.getString("lon").toString().trim();
//                                            }


                                            if (objKH.has("tiennuoc")) {
                                                tiennuoc = objKH.getString("tiennuoc").toString().trim();
                                            }
                                            if (objKH.has("phi")) {
                                                phi = objKH.getString("phi").toString().trim();
                                            }
                                            if (objKH.has("thue")) {
                                                thue = objKH.getString("thue").toString().trim();
                                            }
                                            if (objKH.has("tongcong")) {
                                                tongcong = objKH.getString("tongcong").toString().trim();
                                            }


                                            if (objKH.has("m3muc1")) {
                                                m3t1 = objKH.getString("m3muc1").toString().trim();
                                            }
                                            if (objKH.has("m3muc2")) {
                                                m3t2 = objKH.getString("m3muc2").toString().trim();
                                            }
                                            if (objKH.has("m3muc3")) {
                                                m3t3 = objKH.getString("m3muc3").toString().trim();
                                            }
                                            if (objKH.has("m3muc4")) {
                                                m3t4 = objKH.getString("m3muc4").toString().trim();
                                            }
                                            if (objKH.has("tienmuc1")) {
                                                tien1 = objKH.getString("tienmuc1").toString().trim();
                                            }
                                            if (objKH.has("tienmuc2")) {
                                                tien2 = objKH.getString("tienmuc2").toString().trim();
                                            }
                                            if (objKH.has("tienmuc3")) {
                                                tien3 = objKH.getString("tienmuc3").toString().trim();
                                            }
                                            if (objKH.has("tienmuc4")) {
                                                tien4 = objKH.getString("tienmuc4").toString().trim();
                                            }
                                            if (objKH.has("NgayThu")) {
                                                if (objKH.getString("NgayThu") != null) {
                                                    ngaythanhtoan = objKH.getString("NgayThu").toString().trim();
                                                }

                                            }
                                            if (objKH.has("NhanVienThu")) {
                                                if (objKH.getString("NhanVienThu") != null) {
                                                    NhanVienThu = objKH.getString("NhanVienThu").toString().trim();
                                                }
                                            }


                                            ThanhToanDTO kh = new ThanhToanDTO();
                                            kh.setBienLai(BienLai);
                                            kh.setMaKhachHang(MaKhachHang);
                                            kh.setChiSoMoi(ChiSoMoi);
                                            kh.setChiSoCu(ChiSoCu);
                                            kh.setSLTieuThu(SLTieuThu);
                                            kh.setTransactionID(transid);
                                            kh.setLon(Lon);
                                            kh.setKyHD(KyHD);
                                            kh.setNhanvienthu(NhanVienThu);
                                            kh.setM3t1(m3t1);
                                            kh.setM3t2(m3t2);
                                            kh.setM3t3(m3t3);
                                            kh.setM3t4(m3t4);
                                            kh.setTien1(tien1);
                                            kh.setTien2(tien2);
                                            kh.setTien3(tien3);
                                            kh.setTien4(tien4);
                                            kh.setTienNuoc(tiennuoc);
                                            kh.setphi(phi);
                                            kh.setThue(thue);
                                            kh.settongcong(tongcong);
                                            kh.setNgaythanhtoan(ngaythanhtoan);
                                            kh.setCapNhatThu(CapNhatThu);


                                            Log.e("Them database_KH: ", "Da ton tai " + j + ":" + MaKhachHang + ":" + khachhangDAO.checkExistKH(MaKhachHang, maduong));
                                            boolean kt = thanhtoanDAO.addTable_ThanhToan(kh, maduong);
                                            //SUABUG
                                            //boolean kt = khachhangDAO.updateTable_KH(kh);
                                            if (kt) {
                                                ID++;

//                                                if(kh.getChiSo().equals("") && kh.getSLTieuThu().equals(""))
//                                                {
//
//                                                }
//                                                else{
//                                                    khachhangDAO.tinhTienNuoc(kh.getMaKhachHang().trim());
//                                                }
                                                Log.e("Them database_THANHTOAN: " + MaKhachHang + " ", "Thanh cong");
                                            } else {
                                                Log.e("Them database_THANHTOAN: " + MaKhachHang + " ", "ko Thanh cong");

                                            }


                                            // FlagupdateDB = kt;

                                            long status = (j + 1) * 100 / listKH.length();
                                            //     String.valueOf(status)
                                            publishProgress(String.valueOf(status));
                                            // Escape early if cancel() is called
                                            if (isCancelled()) break;
                                        }

                                    }


                                }
                                List<DuongDTO> listduongcapnhat = duongDAO.getAllDuong();
                                for (int i = 0; i < listduongcapnhat.size(); i++) {
                                    Log.e("listduongcapnhat" + i, listduongcapnhat.get(i).getMaDuong());
                                    Log.e("so khchuaghi", String.valueOf(khachhangDAO.countKhachHangChuaGhiTheoDuong(listduongcapnhat.get(i).getMaDuong())));
                                    if (khachhangDAO.countKhachHangChuaGhiTheoDuong(listduongcapnhat.get(i).getMaDuong()) == 0) {
                                        duongDAO.updateDuongDaGhi(listduongcapnhat.get(i).getMaDuong());
                                    }
                                }
                                Log.e("sokhco", String.valueOf(sokhco));
                                Log.e("sokhdacapnhat", String.valueOf(sokhdacapnhat));
                                if (sokhco == sokhdacapnhat) {
                                    FlagupdateDB = "TC";
                                } else {
                                    FlagupdateDB = "TB";
                                }
                                //     loadDataDuongfromDB();
                            } catch (JSONException e) {
                                FlagupdateDB = "TB";
                                e.printStackTrace();
                            }
                        }
                    } else {
                        FlagupdateDB = "EMPTY";
                        publishProgress("Empty");
                    }
                } catch (Exception x) {
                    FlagupdateDB = "TB";
                }
            }

            return FlagupdateDB;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            if (!status.equals("Empty")) {

                prgTime.setProgress(Integer.parseInt(status));
                prgTime.setText("Đang lấy data từ server");
            }

        }


        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (result.equals("TC")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Load dữ liệu thành công  " + sokhdacapnhat + " khách hàng");
                // thiết lập nội dung cho dialog


                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spdata.luuDataTuDongLuuTapTin(1);
                        spdata.luuChucNangGhiThu("THU");
                        Intent myIntent = new Intent(LoadFromServerThuActivity.this, StartActivity.class);
                        startActivity(myIntent);
                        LoadFromServerThuActivity.this.finish();
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog


            } else if (result.equals("EMPTY")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.loadserver_error_kocokh);
                // thiết lập nội dung cho dialog


                alertDialogBuilder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(LoadFromServerThuActivity.this, StartActivity.class);
                        startActivity(myIntent);
                        LoadFromServerThuActivity.this.finish();
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadData(duongdanfile);
                        // button "no" ẩn dialog đi
                    }
                });

                alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent myIntent = new Intent(LoadFromServerThuActivity.this, StartActivity.class);
                        startActivity(myIntent);
                        LoadFromServerThuActivity.this.finish();
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

    private Boolean KiemTraTonTaiDuLieu() {
        if (duongDAO.countDuong() <= 0 && khachhangDAO.countKhachHangAll() <= 0) {
            return false;
        }
        return true;
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

    public boolean kiemtramang() {
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo wifiCheck3g = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if (wifiCheck.isConnected() || wifiCheck3g.isConnected()) {
            // Do whatever here
            return true;
        } else {
            return false;
        }
    }


    private void loadData(String duongdan) {
//        getSdCardPath();
        //String  duongdanfile = "http://192.168.1.101/Service1.svc/GetListTiwareadData/R010/09/2017";
        if (isInternetOn()) {
            if (KiemTraTonTaiDuLieu()) {
                //xóa sqlite
//                MyDatabaseHelper mydata = new MyDatabaseHelper(con);
//                SQLiteDatabase db = mydata.openDB();
//                mydata.resetDatabase(db);

                if (spdata.getDataTuDongLuuTapTin() == 1) {
                    //Luu lai file tat ca
                    Bien.bienbkall = spdata.getDataBKALLTrongSP();
                    XuLyFile xl = new XuLyFile(con);
                    String path = "";
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        path = xl.getBoNhoTrong();
                    } else {
                        path = getFilesDir().getAbsolutePath();
                    }

                    Log.e("path", path);
                    String thumucchuafile = "";
                    thumucchuafile = path + "/" + "BACKUPTIWAREAD";
                    File rootfile = new File(thumucchuafile);
                    if (rootfile.exists() == false) {
                        spdata.luuDataFlagGhivaBackUpTrongSP(1, 0, 0, 0, 0);
                        File f = new File(thumucchuafile);
                        if (!f.exists()) {
                            f.mkdirs();

                        }

                    }
                    String tenfile = "customers_thu_";
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    tenfile += timeStamp + ".txt";


                    String result_tatca_string = taoJSONData_KH_TatCa(tenfile.trim());
                    if (!result_tatca_string.equals("")) {
                        String filename1 = thumucchuafile + "/TUDONGCAPNHAT";

                        File f = new File(filename1);
                        if (!f.exists()) {
                            f.mkdirs();

                        }

                        boolean kt = writeFile(filename1, tenfile.trim(), result_tatca_string);
                        if (kt) {
                            Toast.makeText(con, "Tự động cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


                //Thêm data vào sqlite
                //String[] sarray = { duongdanfile, "1" };
                MyJsonTaskDatabasefromServer task = new MyJsonTaskDatabasefromServer();
                task.execute(duongdan, "1");

                Bien.selected_item = 0;
                Bien.bien_index_duong = 0;
                Bien.bien_index_khachhang = 0;
                Bien.pre_item = 0;
                Bien.bienghi = 1;
                Bien.bienbkall = 0;
                Bien.bienbkcg = 0;
                Bien.bienbkdg = 0;
                Bien.bienbkdghn = 0;
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi, Bien.bienbkall, Bien.bienbkcg, Bien.bienbkdg, Bien.bienbkdghn);


            } else {
                // String[] sarray = { duongdanfile, "0" };
                MyJsonTaskDatabasefromServer task = new MyJsonTaskDatabasefromServer();
                task.execute(duongdan, "0");
                // spdata = new SPData(con);

                Bien.selected_item = 0;
                Bien.bien_index_duong = 0;
                Bien.bien_index_khachhang = 0;
                Bien.pre_item = 0;
                Bien.bienghi = 1;
                Bien.bienbkall = 0;
                Bien.bienbkcg = 0;
                Bien.bienbkdg = 0;
                Bien.bienbkdghn = 0;
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi, Bien.bienbkall, Bien.bienbkcg, Bien.bienbkdg, Bien.bienbkdghn);


            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerThuActivity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.error_load_server);
            // thiết lập nội dung cho dialog


            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Intent in = new Intent(LoadFromServerThuActivity.this, StartActivity.class);
                    startActivity(in);
                    // button "no" ẩn dialog đi
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            // tạo dialog
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

    }

    private boolean writeFile(String path, String tenfile, String data) {


        try {
            Log.e("duongdanfile", path);
            Log.e("file", tenfile);
            String duongdanfile = path + "/" + tenfile;
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

//            //Hiển thị dialog
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadActivity.this);
//            // khởi tạo dialog
//            alertDialogBuilder.setMessage(R.string.backup_thatbai);
//            // thiết lập nội dung cho dialog
//
//            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
//            // hiển thị dialog
            return false;
        }
    }

    private String taoJSONData_KH_TatCa(String tendanhsach) {
        ListJsonData jsondata = new ListJsonData();
        //Lấy danh sách tất cả các đường
        List<DuongDTO> listduong = new ArrayList<DuongDTO>();
        List<ListTiwareadDTO> listtiwaread = new ArrayList<ListTiwareadDTO>();
        String soluongKH = String.valueOf(khachhangDAO.countKhachHangAll());
        listduong = duongDAO.getAllDuong();
        for (int thutuduong = 0; thutuduong < listduong.size(); thutuduong++) {
            String maduong = listduong.get(thutuduong).getMaDuong();
            String tenduong = listduong.get(thutuduong).getTenDuong();
            List<KhachHangDTO> listkh = new ArrayList<KhachHangDTO>();
            listkh = khachhangDAO.getAllKHTheoDuong(maduong);
            ListTiwareadDTO tiwaread = new ListTiwareadDTO();
            tiwaread.setMaDuong(maduong);
            tiwaread.setTenDuong(tenduong);
            tiwaread.setTiwareadList(listkh);
            if (listkh.size() > 0) {
                listtiwaread.add(tiwaread);
            }
        }
        String json = "";
        if (listtiwaread.size() > 0) {
            jsondata.setListTiwaread(listtiwaread);
            String kyhd = spdata.getDataKyHoaDonTrongSP();
            jsondata.setTenDS(kyhd);

            jsondata.setTongSLkh(soluongKH);
            Gson gson = new Gson();
            json = gson.toJson(jsondata);
        } else {
            spdata.luuDataFlagBKAllTrongSP(-1);
            Bien.bienbkall = -1;

        }

        return json;
    }


}
