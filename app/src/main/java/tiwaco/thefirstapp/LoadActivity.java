package tiwaco.thefirstapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

public class LoadActivity extends AppCompatActivity {
    private String filename = "";
    DonutProgress prgTime;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    String duongdanfile ="";
    DuongDAO duongDAO ;
    KhachHangDAO khachhangDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        duongDAO = new DuongDAO(LoadActivity.this);
        khachhangDAO = new KhachHangDAO(LoadActivity.this);
        File extStore = Environment.getExternalStorageDirectory();
        filename = getString(R.string.data_file_name);
        duongdanfile = extStore.getAbsolutePath() + "/" + filename;
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        prgTime.setProgress(0);
        prgTime.setText("0 %");
        ActionBar ac = getSupportActionBar();
        ac.hide();

        askPermissionAndReadFile();
    }

    private String readFile(String path) {

        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
            if(myFile.exists()){
                Log.e("file ton tai","true");
            }
            else{
                Log.e("file ton tai","false");
            }
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            while ((s = myReader.readLine()) != null) {
                fileContent += s + "\n";
            }
            //          this.viewjson.setText(fileContent);
            myReader.close();
            // Log.e("file json",fileContent);
           // String finalFileContent = fileContent;


        } catch (IOException e) {
            e.printStackTrace();

        }

        return fileContent;
    }

    public class MyJsonTaskDatabasefromFile extends AsyncTask<String, String , Boolean > {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Lấy URL truyền vào
            Boolean FlagupdateDB = true;
            String jsontext = readFile(params[0])  ;
            JSONObject jsonobj = null;
            try {
                jsonobj = new JSONObject(jsontext);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonobj.has("ListTiwaread")) {
                try {
                    JSONArray listtiwaread = jsonobj.getJSONArray("ListTiwaread");
                    for(int  i  =0 ;i <listtiwaread.length();i++){
                        JSONObject objTiwaread = listtiwaread.getJSONObject(i);
                        String maduong = "";
                        String tenduong  ="";
                        if(objTiwaread.has("Maduong")){
                            maduong = objTiwaread.getString("Maduong");
                        }

                        if(objTiwaread.has("Tenduong")){
                            tenduong = objTiwaread.getString("Tenduong");
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
                                String ChiSo1con = "";
                                String ChiSo2  ="";
                                String ChiSo2con = "";
                                String ChiSo3  ="";
                                String ChiSo3con = "";
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
                                String SLTieuThu1con = "";
                                String SLTieuThu2  ="";
                                String SLTieuThu2con = "";
                                String SLTieuThu3  ="";
                                String SLTieuThu3con = "";
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
                                if(objKH.has("ChiSo")){
                                    ChiSo = objKH.getString("ChiSo");
                                }

                                if(objKH.has("ChiSo1")){
                                    ChiSo1 = objKH.getString("ChiSo1");
                                }
                                if(objKH.has("ChiSo1con")){
                                    ChiSo1con = objKH.getString("ChiSo1con");
                                }

                                if(objKH.has("ChiSo2")){
                                    ChiSo2 = objKH.getString("ChiSo2");
                                }
                                if(objKH.has("ChiSo2con")){
                                    ChiSo2con = objKH.getString("ChiSo2con");
                                }

                                if(objKH.has("ChiSo3")){
                                    ChiSo3 = objKH.getString("ChiSo3");
                                }
                                if(objKH.has("ChiSo3con")){
                                    ChiSo3con = objKH.getString("ChiSo3con");
                                }

                                if(objKH.has("ChiSocon")){
                                    ChiSocon = objKH.getString("ChiSocon");
                                }
                                if(objKH.has("DanhBo")){
                                    DanhBo = objKH.getString("DanhBo");
                                }

                                if(objKH.has("DiaChi")){
                                    DiaChi = objKH.getString("DiaChi");
                                }
                                if(objKH.has("DienThoai")){
                                    DienThoai = objKH.getString("DienThoai");
                                }

                                if(objKH.has("GhiChu")){
                                    GhiChu = objKH.getString("GhiChu");
                                }
                                if(objKH.has("Lat")){
                                    Lat = objKH.getString("Lat");
                                }

                                if(objKH.has("Lon")){
                                    Lon = objKH.getString("Lon");
                                }
                                if(objKH.has("MaKhachHang")){
                                    MaKhachHang = objKH.getString("MaKhachHang");
                                }

                                if(objKH.has("NhanVien")){
                                    NhanVien = objKH.getString("NhanVien");
                                }
                                if(objKH.has("SLTieuThu")){
                                    SLTieuThu = objKH.getString("SLTieuThu");
                                }

                                if(objKH.has("SLTieuThu1")){
                                    SLTieuThu1 = objKH.getString("SLTieuThu1");
                                }
                                if(objKH.has("SLTieuThu1con")){
                                    SLTieuThu1con = objKH.getString("SLTieuThu1con");
                                }

                                if(objKH.has("SLTieuThu2")){
                                    SLTieuThu2 = objKH.getString("SLTieuThu2");
                                }
                                if(objKH.has("SLTieuThu2con")){
                                    SLTieuThu2con = objKH.getString("SLTieuThu2con");
                                }

                                if(objKH.has("SLTieuThu3")){
                                    SLTieuThu3 = objKH.getString("SLTieuThu3");
                                }
                                if(objKH.has("SLTieuThu3con")){
                                    SLTieuThu3con = objKH.getString("SLTieuThu3con");
                                }

                                if(objKH.has("SLTieuThucon")){
                                    SLTieuThucon = objKH.getString("SLTieuThucon");
                                }
                                if(objKH.has("STT")){
                                    STT = objKH.getString("STT");
                                }

                                if(objKH.has("TenKhachHang")){
                                    TenKhachHang = objKH.getString("TenKhachHang");
                                }
                                if(objKH.has("ThoiGian")){
                                    ThoiGian = objKH.getString("ThoiGian");
                                }

                                if(objKH.has("TrangThaiTLK")){
                                    TrangThaiTLK = objKH.getString("TrangThaiTLK");
                                }
                                if(objKH.has("chitietloai")){
                                    chitietloai = objKH.getString("chitietloai");
                                }

                                if(objKH.has("cotlk")){
                                    cotlk = objKH.getString("cotlk");
                                }
                                if(objKH.has("dinhmuc")){
                                    dinhmuc = objKH.getString("dinhmuc");
                                }

                                if(objKH.has("hieutlk")){
                                    hieutlk = objKH.getString("hieutlk");
                                }
                                if(objKH.has("loaikh")){
                                    loaikh = objKH.getString("loaikh");
                                }

                                if(objKH.has("masotlk")){
                                    masotlk = objKH.getString("masotlk");
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
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "Thanh cong");
                                } else {
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "ko Thanh cong");

                                }
                                FlagupdateDB = kt;

                                long status = (j+1) *100/listKH.length();
                                publishProgress(String.valueOf(status));

                            }

                        }
                    }
                    //     loadDataDuongfromDB();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return FlagupdateDB;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            prgTime.setProgress(Integer.parseInt(status));
            // update giá trị ở TextView
            prgTime.setText(status+" %");
        }



        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
            if(result){
                Intent myIntent=new Intent(LoadActivity.this, MainActivity.class);
                startActivity(myIntent);
                LoadActivity.this.finish();
            }
            else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadData();
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
                alertDialog.show();
                // hiển thị dialog
            }


        }
    }
    private void loadData(){
        MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
        task.execute(duongdanfile);
    }
    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (canRead) {

            // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
            //  task.execute(duongdanfile);
         //   readFileandSaveDatabase();
            loadData();;
        }
    }
    private void askPermissionAndWriteFile(String path, String data) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
        //Backup file json
        //    this.writeFile(path,data);
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
                    loadData();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // writeFile(duongdanfile,dataGhi);
                        //backup file json
                    }
                }

            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


    private void backupdulieu(){

    }

}