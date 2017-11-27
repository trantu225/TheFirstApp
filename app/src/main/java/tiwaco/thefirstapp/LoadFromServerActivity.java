package tiwaco.thefirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;

public class LoadFromServerActivity extends AppCompatActivity {
    private String filename = "";
    DonutProgress prgTime;
    LinearLayout layout_noidungload;
    Button btngetData;
    EditText editKyHD,edtNV;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    String  duongdanfile = "";

    DuongDAO duongDAO ;
    Context con;
    KhachHangDAO khachhangDAO;
    TinhTrangTLKDAO tinhtrangtlkdao;

    SPData spdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata_server);
        getSupportActionBar().hide();
        con = LoadFromServerActivity.this;
        duongDAO = new DuongDAO(LoadFromServerActivity.this);
        khachhangDAO = new KhachHangDAO(LoadFromServerActivity.this);
        tinhtrangtlkdao = new TinhTrangTLKDAO(con);
        spdata = new SPData(con);
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        layout_noidungload =(LinearLayout) findViewById(R.id.lay_noidungload) ;
        btngetData =(Button) findViewById(R.id.btn_GetDaTa) ;
        editKyHD = (EditText) findViewById(R.id.edt_kyhd) ;
        edtNV = (EditText) findViewById(R.id.edt_nhanvien) ;
        prgTime.setProgress(0);
        prgTime.setText("0 %");
        duongdanfile = getString(R.string.API_GetListTiwareadData);
        btngetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new GetUserList().execute("http://192.168.1.91/Service1.svc/GetListUser");
                try {
                    String manv = edtNV.getText().toString().trim(); // Lay manv
                    Log.e("nhap", editKyHD.getText().toString());
                    String thang = editKyHD.getText().toString().substring(4, 6);
                    Log.e("thang", thang);
                    String nam = editKyHD.getText().toString().substring(0, 4);
                    Log.e("nam", nam);
                    duongdanfile += "/" + manv + "/" + thang + "/" + nam;
                    Log.e("duongdan", duongdanfile);
                    layout_noidungload.setVisibility(View.GONE);
                    prgTime.setVisibility(View.VISIBLE);

                    askPermissionAndReadFile();
                }
                catch(Exception loi){
                    //Hiển thị dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Kết nối thất bại.Hãy kiểm tra lại thông tin nhân viên và kỳ hóa đơn!");
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

                        GPSTracker gps = new GPSTracker(con, LoadFromServerActivity.this);

                        // Check if GPS enabled
                        if (gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            String vido = String.valueOf(latitude);
                            String kinhdo = String.valueOf(longitude);

                            Log.e("Toa do", vido +"-"+kinhdo );
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


    public class MyJsonTaskDatabasefromServer extends AsyncTask<String, String , Boolean > {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            HttpURLConnection conn=null;
            BufferedReader reader;
            Boolean FlagupdateDB = true;
            //Duong dan file
            String duongdan = params[0];
            String fileContent ="";
            int sokhdacapnhat = 0;
            int sokhco =0;


            try{
                final URL url=new URL(params[0]);
                conn=(HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if(result==200){

                    InputStream in=new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb=new StringBuilder();
                    String line = null;
                    int i  =0;
                    while((line=reader.readLine())!=null){
                        long status = (i+1) *100/line.length();
                        //     String.valueOf(status)
                        publishProgress(String.valueOf(status),"server");
                        fileContent=line;

                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
          //THêm database loại tinh trang tlk
            List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
            for(int tt = 0 ; tt<listt.size();tt++){
                tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
            }
            //Lay json tu server
            JSONObject jsonobj = null;
            try {
                jsonobj = new JSONObject(fileContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonobj.has("tenDS")){


                try {
                    String tenDS = jsonobj.getString("tenDS");
                   // String kyhd = "082017"; //cắt chuỗi từ tên DS
                    //Luu ky hd vao SP
                   spdata.luuDataKyHoaDonTrongSP(tenDS);
                    Log.e("kyhddaluu",tenDS);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            if (jsonobj.has("tongSLkh")){


                try {
                    String tong = jsonobj.getString("tongSLkh");
                    // String kyhd = "082017"; //cắt chuỗi từ tên DS
                    //Luu ky hd vao SP
                    sokhco = Integer.parseInt(tong);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (jsonobj.has("ListTiwaread")) {
                try {
                    JSONArray listtiwaread = jsonobj.getJSONArray("ListTiwaread");
                    for(int  i  =0 ;i <listtiwaread.length();i++){
                        JSONObject objTiwaread = listtiwaread.getJSONObject(i);
                        String maduong = "";
                        String tenduong  ="";
                        if(objTiwaread.has("Maduong")){
                            maduong = objTiwaread.getString("Maduong").trim();
                        }

                        if(objTiwaread.has("Tenduong")){
                            tenduong = objTiwaread.getString("Tenduong").trim();
                        }
                        Log.e("kiem tra da ton tai hay chua", String.valueOf(duongDAO.countDuong()));
                        if(duongDAO.countDuong() <=0) {


                            Log.e("Them database_duong: ", "chay zo day rui");
                            DuongDTO duong = new DuongDTO(maduong, tenduong, 0);
                            boolean kt = duongDAO.addTable_Duong(duong);
                            if (kt) {
                                Log.e("Them database_duong: " + maduong, "Thanh cong");
                            } else {
                                Log.e("Them database_duong: " + maduong, "ko Thanh cong");

                            }
                            //loadDataDuongfromDB();
                        }
                        else{
                            if(duongDAO.checkExistDuong(maduong)) {
                                //    loadDataDuongfromDB();
                                Log.e("Them database_duong: ", " Da ton tai duong nay");
                            }
                            else{
                                DuongDTO duong = new DuongDTO(maduong, tenduong, 0);
                                boolean kt = duongDAO.addTable_Duong(duong);
                                if (kt) {
                                    Log.e("Them database_duong: " + maduong, "Thanh cong");
                                } else {
                                    Log.e("Them database_duong: " + maduong, "ko Thanh cong");

                                }
                                //     loadDataDuongfromDB();
                            }
                        }

                        if (objTiwaread.has("TiwareadList")){
                            Log.e("Them database_KH: " , "Lay JSONARRAY");
                            JSONArray listKH = objTiwaread.getJSONArray("TiwareadList");
                            Log.e("Them database_KH: " , "JSONARRAY_LENGTH "+listKH.length());
                            for(int  j =0 ;j <listKH.length();j++){
                                JSONObject objKH = listKH.getJSONObject(j);
                                String ChiSo = "";
                                String ChiSo1  ="";
                                String ChiSo1con = "0";
                                String ChiSo2  ="";
                                String ChiSo2con = "0";
                                String ChiSo3  ="";
                                String ChiSo3con = "0";
                                String ChiSocon  ="";
                                String DanhBo = "";
                                String DiaChi  ="";
                                String DienThoai = "";
                                String GhiChu  ="";
                                String Lat = "";
                                String Lon  ="";
                                String MaKhachHang = "";
                                String NhanVien  ="";
                                String SLTieuThu = "";
                                String SLTieuThu1  ="";
                                String SLTieuThu1con = "0";
                                String SLTieuThu2  ="";
                                String SLTieuThu2con = "0";
                                String SLTieuThu3  ="";
                                String SLTieuThu3con = "0";
                                String SLTieuThucon  ="";
                                String STT = "";
                                String TenKhachHang  ="";
                                String ThoiGian = "";
                                String TrangThaiTLK  ="";
                                String chitietloai = "";
                                String cotlk  ="";
                                String dinhmuc = "";
                                String hieutlk  ="";
                                String loaikh = "";
                                String masotlk  ="";
                                String loaikhcu="";
//                                if(objKH.has("ChiSo")){
//                                    ChiSo = objKH.getString("ChiSo").toString().trim();
//                                }

                                if(objKH.has("ChiSo1")){
                                    ChiSo1 = objKH.getString("ChiSo1").toString().trim();
                                }
//                                if(objKH.has("ChiSo1con")){
//                                    ChiSo1con = objKH.getString("ChiSo1con").toString().trim();
//                                }

                                if(objKH.has("ChiSo2")){
                                    ChiSo2 = objKH.getString("ChiSo2").toString().trim();
                                }
//                                if(objKH.has("ChiSo2con")){
//                                    ChiSo2con = objKH.getString("ChiSo2con").toString().trim();
//                                }

                                if(objKH.has("ChiSo3")){
                                    ChiSo3 = objKH.getString("ChiSo3").toString().trim();
                                }
//                                if(objKH.has("ChiSo3con")){
//                                    ChiSo3con = objKH.getString("ChiSo3con").toString().trim();
//                                }

//                                if(objKH.has("ChiSocon")){
//                                    ChiSocon = objKH.getString("ChiSocon").toString().trim();
//                                }
                                if(objKH.has("DanhBo")){
                                    DanhBo = objKH.getString("DanhBo").toString().trim();
                                }

                                if(objKH.has("DiaChi")){
                                    DiaChi = objKH.getString("DiaChi").toString().trim();
                                }
                                if(objKH.has("DienThoai")){
                                    DienThoai = objKH.getString("DienThoai").toString().trim();
                                }

                                if(objKH.has("GhiChu")){
                                    GhiChu = objKH.getString("GhiChu").toString().trim();
                                }
//                                if(objKH.has("Lat")){
//                                    Lat = objKH.getString("Lat").toString().trim();
//                                }
//
//                                if(objKH.has("Lon")){
//                                    Lon = objKH.getString("Lon").toString().trim();
//                                }
                                if(objKH.has("MaKhachHang")){
                                    MaKhachHang = objKH.getString("MaKhachHang").toString().trim();
                                }

//                                if(objKH.has("NhanVien")){
//                                    NhanVien = objKH.getString("NhanVien").toString().trim();
//                                }
//                                if(objKH.has("SLTieuThu")){
//                                    SLTieuThu = objKH.getString("SLTieuThu").toString().trim();
//                                }

                                if(objKH.has("SLTieuThu1")){
                                    SLTieuThu1 = objKH.getString("SLTieuThu1").toString().trim();
                                }
//                                if(objKH.has("SLTieuThu1con")){
//                                    SLTieuThu1con = objKH.getString("SLTieuThu1con").toString().trim();
//                                }

                                if(objKH.has("SLTieuThu2")){
                                    SLTieuThu2 = objKH.getString("SLTieuThu2").toString().trim();
                                }
//                                if(objKH.has("SLTieuThu2con")){
//                                    SLTieuThu2con = objKH.getString("SLTieuThu2con").toString().trim();
//                                }

                                if(objKH.has("SLTieuThu3")){
                                    SLTieuThu3 = objKH.getString("SLTieuThu3").toString().trim();
                                }
//                                if(objKH.has("SLTieuThu3con")){
//                                    SLTieuThu3con = objKH.getString("SLTieuThu3con").toString().trim();
//                                }

//                                if(objKH.has("SLTieuThucon")){
//                                    SLTieuThucon = objKH.getString("SLTieuThucon").toString().trim();
//                                }
                                if(objKH.has("STT")){
                                    STT = objKH.getString("STT").toString().trim();
                                }

                                if(objKH.has("TenKhachHang")){
                                    TenKhachHang = objKH.getString("TenKhachHang").toString().trim();
                                }
//                                if(objKH.has("ThoiGian")){
//                                    ThoiGian = objKH.getString("ThoiGian").toString().trim();
//                                }

//                                if(objKH.has("TrangThaiTLK")){
//                                    TrangThaiTLK = objKH.getString("TrangThaiTLK").toString().trim();
//                                }
                                if(objKH.has("chitietloai")){
                                    chitietloai = objKH.getString("chitietloai").toString().trim();
                                }

                                if(objKH.has("cotlk")){
                                    cotlk = objKH.getString("cotlk").toString().trim();
                                }
                                if(objKH.has("dinhmuc")){
                                    dinhmuc = objKH.getString("dinhmuc").toString().trim();
                                }

                                if(objKH.has("hieutlk")){
                                    hieutlk = objKH.getString("hieutlk").toString().trim();
                                }
                                if(objKH.has("loaikh")){
                                    loaikh = objKH.getString("loaikh").toString().trim();
                                    loaikhcu = loaikh;
                                }

                                if(objKH.has("masotlk")){
                                    masotlk = objKH.getString("masotlk").toString().trim();
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

                                Log.e("Them database_KH: ", "Da ton tai "+j   +":" +MaKhachHang + ":" + khachhangDAO.checkExistKH(MaKhachHang,maduong));
                                boolean kt = khachhangDAO.addTable_KH(kh,maduong);

                                if (kt) {
                                    sokhdacapnhat ++;
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "Thanh cong");
                                } else {
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "ko Thanh cong");

                                }


                               // FlagupdateDB = kt;

                                long status = (j+1) *100/listKH.length();
                           //     String.valueOf(status)
                                publishProgress(String.valueOf(status),tenduong);

                            }

                        }
                    }
                    Log.e("sokhco", String.valueOf(sokhco));
                    Log.e("sokhdacapnhat", String.valueOf(sokhdacapnhat));
                    if(sokhco == sokhdacapnhat){
                        FlagupdateDB =true;
                    }
                    else{
                        FlagupdateDB = false;
                    }
                    //     loadDataDuongfromDB();
                } catch (JSONException e) {
                    FlagupdateDB = false;
                    e.printStackTrace();
                }
            }
            return FlagupdateDB;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            String duong = values[1];
            prgTime.setProgress(Integer.parseInt(status));
            prgTime.setText("Đang lấy data từ server");

        }



        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
            if(result){
                Intent myIntent=new Intent(LoadFromServerActivity.this, StartActivity.class);
                startActivity(myIntent);
                LoadFromServerActivity.this.finish();
            }
            else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerActivity.this);
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

                alertDialogBuilder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
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
                // hiển thị dialog
            }


        }
    }
    private Boolean KiemTraTonTaiDuLieu(){
        if(duongDAO.countDuong() <=0 && khachhangDAO.countKhachHangAll()<=0)
        {
            return false;
        }
        return true;
    }
    public boolean kiemtramang(){
        return true;
    }


    private void loadData(String duongdanfile){
//        getSdCardPath();
        //String  duongdanfile = "http://192.168.1.101/Service1.svc/GetListTiwareadData/R010/09/2017";
        if(kiemtramang()) {
            if(KiemTraTonTaiDuLieu()) {

                //xóa sqlite
                MyDatabaseHelper mydata = new MyDatabaseHelper(con);
                SQLiteDatabase db = mydata.openDB();
                mydata.resetDatabase(db);


                //Thêm data vào sqlite
                MyJsonTaskDatabasefromServer task = new MyJsonTaskDatabasefromServer();
                task.execute(duongdanfile);

                Bien.selected_item =0;
                Bien.bien_index_duong = 0;
                Bien.bien_index_khachhang = 0;
                Bien.pre_item = 0;
                Bien.bienghi = 1;
                Bien.bienbkall = 0;
                Bien.bienbkcg = 0;
                Bien.bienbkdg = 0;
                Bien.bienbkdghn = 0;
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi,Bien.bienbkall, Bien.bienbkcg,Bien.bienbkdg,Bien.bienbkdghn);



            }
            else{
                MyJsonTaskDatabasefromServer task = new MyJsonTaskDatabasefromServer();
                task.execute(duongdanfile);
                spdata = new SPData(con);

                Bien.selected_item =0;
                Bien.bien_index_duong = 0;
                Bien.bien_index_khachhang = 0;
                Bien.pre_item = 0;
                Bien.bienghi = 1;
                Bien.bienbkall = 0;
                Bien.bienbkcg = 0;
                Bien.bienbkdg = 0;
                Bien.bienbkdghn = 0;
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi,Bien.bienbkall, Bien.bienbkcg,Bien.bienbkdg,Bien.bienbkdghn);


            }
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadFromServerActivity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.error_load_file);
            // thiết lập nội dung cho dialog


            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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







}
