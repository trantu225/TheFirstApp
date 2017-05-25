package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;

public class LoadActivity extends AppCompatActivity {
    private String filename = "";
    DonutProgress prgTime;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    String duongdanfile ="";
    DuongDAO duongDAO ;
    Context con;
    KhachHangDAO khachhangDAO;
    SPData spdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        getSupportActionBar().hide();
        con = LoadActivity.this;
        duongDAO = new DuongDAO(LoadActivity.this);
        khachhangDAO = new KhachHangDAO(LoadActivity.this);
        spdata = new SPData(con);
        File extStore = Environment.getExternalStorageDirectory();
        filename = getString(R.string.data_file_name);
        duongdanfile = extStore.getAbsolutePath() + "/" + filename;
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        prgTime.setProgress(0);
        prgTime.setText("0 %");
        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoadActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
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
    public class MyJsonTaskDatabasefromFile extends AsyncTask<String, String , Boolean > {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Lấy URL truyền vào
            Boolean FlagupdateDB = true;
            //Duong dan file
            String duongdan = params[0];
            String fileContent ="";
            fileContent = readFile(duongdan);


/*
            FileInputStream inputStream = null;
            Scanner sc = null;
            try {
                inputStream = new FileInputStream(duongdan);
                sc = new Scanner(inputStream, "UTF-8");
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    fileContent += line + "\n";
                    // System.out.println(line);
                }
                // note that Scanner suppresses exceptions
                if (sc.ioException() != null) {
                    throw sc.ioException();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (sc != null) {
                    sc.close();
                }
            }

            //String jsontext = readFile(params[0]);
*/


            JSONObject jsonobj = null;
            try {
                jsonobj = new JSONObject(fileContent);
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
                                    ChiSo = objKH.getString("ChiSo").toString().trim();
                                }

                                if(objKH.has("ChiSo1")){
                                    ChiSo1 = objKH.getString("ChiSo1").toString().trim();
                                }
                                if(objKH.has("ChiSo1con")){
                                    ChiSo1con = objKH.getString("ChiSo1con").toString().trim();
                                }

                                if(objKH.has("ChiSo2")){
                                    ChiSo2 = objKH.getString("ChiSo2").toString().trim();
                                }
                                if(objKH.has("ChiSo2con")){
                                    ChiSo2con = objKH.getString("ChiSo2con").toString().trim();
                                }

                                if(objKH.has("ChiSo3")){
                                    ChiSo3 = objKH.getString("ChiSo3").toString().trim();
                                }
                                if(objKH.has("ChiSo3con")){
                                    ChiSo3con = objKH.getString("ChiSo3con").toString().trim();
                                }

                                if(objKH.has("ChiSocon")){
                                    ChiSocon = objKH.getString("ChiSocon").toString().trim();
                                }
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
                                if(objKH.has("Lat")){
                                    Lat = objKH.getString("Lat").toString().trim();
                                }

                                if(objKH.has("Lon")){
                                    Lon = objKH.getString("Lon").toString().trim();
                                }
                                if(objKH.has("MaKhachHang")){
                                    MaKhachHang = objKH.getString("MaKhachHang").toString().trim();
                                }

                                if(objKH.has("NhanVien")){
                                    NhanVien = objKH.getString("NhanVien").toString().trim();
                                }
                                if(objKH.has("SLTieuThu")){
                                    SLTieuThu = objKH.getString("SLTieuThu").toString().trim();
                                }

                                if(objKH.has("SLTieuThu1")){
                                    SLTieuThu1 = objKH.getString("SLTieuThu1").toString().trim();
                                }
                                if(objKH.has("SLTieuThu1con")){
                                    SLTieuThu1con = objKH.getString("SLTieuThu1con").toString().trim();
                                }

                                if(objKH.has("SLTieuThu2")){
                                    SLTieuThu2 = objKH.getString("SLTieuThu2").toString().trim();
                                }
                                if(objKH.has("SLTieuThu2con")){
                                    SLTieuThu2con = objKH.getString("SLTieuThu2con").toString().trim();
                                }

                                if(objKH.has("SLTieuThu3")){
                                    SLTieuThu3 = objKH.getString("SLTieuThu3").toString().trim();
                                }
                                if(objKH.has("SLTieuThu3con")){
                                    SLTieuThu3con = objKH.getString("SLTieuThu3con").toString().trim();
                                }

                                if(objKH.has("SLTieuThucon")){
                                    SLTieuThucon = objKH.getString("SLTieuThucon").toString().trim();
                                }
                                if(objKH.has("STT")){
                                    STT = objKH.getString("STT").toString().trim();
                                }

                                if(objKH.has("TenKhachHang")){
                                    TenKhachHang = objKH.getString("TenKhachHang").toString().trim();
                                }
                                if(objKH.has("ThoiGian")){
                                    ThoiGian = objKH.getString("ThoiGian").toString().trim();
                                }

                                if(objKH.has("TrangThaiTLK")){
                                    TrangThaiTLK = objKH.getString("TrangThaiTLK").toString().trim();
                                }
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
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "Thanh cong");
                                } else {
                                    Log.e("Them database_KH: "+MaKhachHang+" " + TenKhachHang, "ko Thanh cong");

                                }


                                FlagupdateDB = kt;

                                long status = (j+1) *100/listKH.length();
                           //     String.valueOf(status)
                                publishProgress(String.valueOf(status),tenduong);

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
            String duong = values[1];
            prgTime.setProgress(Integer.parseInt(status));

            // update giá trị ở TextView
            prgTime.setText(getString(R.string.load_status));
        }



        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
            if(result){
                Intent myIntent=new Intent(LoadActivity.this, StartActivity.class);
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
    private void loadData(){
//        getSdCardPath();
        File file = new File(duongdanfile);
        if(file.exists()) {
            if(KiemTraTonTaiDuLieu()) {

                //xóa sqlite
                MyDatabaseHelper mydata = new MyDatabaseHelper(con);
                SQLiteDatabase db = mydata.openDB();
                mydata.resetDatabase(db);


                //Thêm data vào sqlite
                MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
                task.execute(duongdanfile);

                Bien.selected_item =0;
                Bien.bien_index_duong = 0;
                Bien.bien_index_khachhang = 0;
                Bien.pre_item = 0;
                Bien.bienghi = 1;
                Bien.bienbkall = 0;
                Bien.bienbkcg = 0;
                Bien.bienbkdg = 0;
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi,Bien.bienbkall, Bien.bienbkcg,Bien.bienbkdg);
/*
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.delete_file_load_file);
                // thiết lập nội dung cho dialog


                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Luu lai file chua sqlite cũ


                        //xóa sqlite
                        MyDatabaseHelper mydata = new MyDatabaseHelper(con);
                        SQLiteDatabase db = mydata.openDB();
                        mydata.resetDatabase(db);


                        //Thêm data vào sqlite
                        MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
                        task.execute(duongdanfile);

                        Bien.selected_item =0;
                        Bien.bien_index_duong = 0;
                        Bien.bien_index_khachhang = 0;
                        Bien.pre_item = 0;
                        Bien.bienghi = 1;
                        Bien.bienbkall = 0;
                        Bien.bienbkcg = 0;
                        Bien.bienbkdg = 0;
                        spdata.KhoiTaoLaiSPDATA();
                        spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi,Bien.bienbkall, Bien.bienbkcg,Bien.bienbkdg);



                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.show();*/
            }
            else{
                MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
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
                spdata.KhoiTaoLaiSPDATA();
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi,Bien.bienbkall, Bien.bienbkcg,Bien.bienbkdg);


            }
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadActivity.this);
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
    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (canRead) {

            // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
            //  task.execute(duongdanfile);
         //   readFileandSaveDatabase();
            loadData();

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
                case 1: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the

                        // contacts-related task you need to do.

                        GPSTracker gps = new GPSTracker(con, LoadActivity.this);

                        // Check if GPS enabled
                        if (gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            String vido = String.valueOf(latitude);
                            String kinhdo = String.valueOf(longitude);

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
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }




}
