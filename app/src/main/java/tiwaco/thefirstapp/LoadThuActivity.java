package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.security.keystore.KeyNotYetValidException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.JSONBACKUPTHU;
import tiwaco.thefirstapp.DTO.JSONDUONGTHU;
import tiwaco.thefirstapp.DTO.JSONKHTHU;
import tiwaco.thefirstapp.DTO.JSONTHANHTOANTHU;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.ListJsonData;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.File.XuLyFile;

public class LoadThuActivity extends AppCompatActivity {
    private String filename = "";
    DonutProgress prgTime;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    String duongdanfile = "";
    DuongThuDAO duongDAO;
    Context con;
    KhachHangThuDAO khachhangDAO;
    ThanhToanDAO thanhToanDAO;
    TinhTrangTLKDAO tinhtrangtlkdao;
    SPData spdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        getSupportActionBar().hide();
        con = LoadThuActivity.this;
        duongDAO = new DuongThuDAO(LoadThuActivity.this);
        khachhangDAO = new KhachHangThuDAO(LoadThuActivity.this);
        thanhToanDAO = new ThanhToanDAO(LoadThuActivity.this);
        tinhtrangtlkdao = new TinhTrangTLKDAO(con);
        spdata = new SPData(con);
        File extStore = Environment.getExternalStorageDirectory();
        filename = getString(R.string.data_file_name_thu);
        duongdanfile = extStore.getAbsolutePath() + "/" + filename;
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        prgTime.setProgress(0);
        prgTime.setText("0 %");

        if (ContextCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoadThuActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            askPermissionAndReadFile();
        }

    }

    private String readFile(String path) {

        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
            if (myFile.exists()) {
                Log.e("file ton tai", "true");
            } else {
                Log.e("file ton tai", "false");
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

    public String getSdCardPath() {

        String[] deviceID = getExternalStorageDirectories();
        if (deviceID == null) {
            return "";
        } else {
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addPath = Environment.isExternalStorageRemovable(file);
                } else {
                    addPath = Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(file));
                }

                if (addPath) {
                    results.add(path);
                }
            }
        }

        if (results.isEmpty()) { //Method 2 for all versions
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
            if (!output.trim().isEmpty()) {
                String devicePoints[] = output.split("\n");
                for (String voldPoint : devicePoints) {
                    results.add(voldPoint.split(" ")[2]);
                }
            }
        }

        //Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().matches(".*[0-9a-f]{4}[-][0-9a-f]{4}")) {
                    Log.d("sdcard", results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().contains("ext") && !results.get(i).toLowerCase().contains("sdcard")) {
                    Log.d("sdcard", results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        }

        String[] storageDirectories = new String[results.size()];
        for (int i = 0; i < results.size(); ++i) storageDirectories[i] = results.get(i);

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
        if (mExternalStorageAvailable == true && mExternalStorageWriteable == true) {
            return true;
        } else
            return false;

    }

    public class MyJsonTaskDatabasefromFile extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Lấy URL truyền vào
            Boolean FlagupdateDB = true;
            Boolean Flagupdate1 = true;
            Boolean Flagupdate2 = true;
            Boolean Flagupdate3 = true;

            //Duong dan file
            String duongdan = params[0];
            String fileContent = "";
            fileContent = readFile(duongdan);


            //THêm database loại tinh trang tlk
//            List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//            for (int tt = 0; tt < listt.size(); tt++) {
//                tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//            }

            JSONObject jsonobj = null;
            try {
                jsonobj = new JSONObject(fileContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (jsonobj.has("listduong")) {
                try {
                    JSONArray listDaTaduong = jsonobj.getJSONArray("listduong");
                    int soluongcapnhat = 0;
                    for (int i = 0; i < listDaTaduong.length(); i++) {

                        JSONObject objTiwaread = listDaTaduong.getJSONObject(i);
                        String maduong = "";
                        String tenduong = "";
                        String khoaso = "";
                        String trangthai = "";
                        String trangthaithu = "";
                        if (objTiwaread.has("maduong")) {
                            maduong = objTiwaread.getString("maduong");
                        }
                        if (objTiwaread.has("tenduong")) {
                            tenduong = objTiwaread.getString("tenduong");
                        }
                        if (objTiwaread.has("khoaso")) {
                            khoaso = objTiwaread.getString("khoaso");
                        }
                        if (objTiwaread.has("trangthai")) {
                            trangthai = objTiwaread.getString("trangthai");
                        }
                        if (objTiwaread.has("trangthaithu")) {
                            trangthaithu = objTiwaread.getString("trangthaithu");
                        }

                        Log.e("kiem tra da ton tai hay chua", String.valueOf(duongDAO.countDuong()));

                        if (duongDAO.countDuong() <= 0) {


                            Log.e("Them database_duong: ", "chay zo day rui");
                            DuongThuDTO duong = new DuongThuDTO(maduong, tenduong, Integer.parseInt(trangthai), khoaso, Integer.parseInt(trangthaithu));
                            boolean kt = duongDAO.addTABLE_DUONGTHU(duong);

                            if (kt) {
                                soluongcapnhat++;
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
                                DuongThuDTO duong = new DuongThuDTO(maduong, tenduong, Integer.parseInt(trangthai), khoaso, Integer.parseInt(trangthaithu));
                                boolean kt = duongDAO.addTABLE_DUONGTHU(duong);
                                if (kt) {
                                    soluongcapnhat++;
                                    Log.e("Them database_duong: " + maduong, "Thanh cong");
                                } else {
                                    Log.e("Them database_duong: " + maduong, "ko Thanh cong");

                                }
                                //     loadDataDuongfromDB();
                            }
                        }


                    }
                    Log.e("BangDuong", soluongcapnhat + " " + listDaTaduong.length());
                    if (soluongcapnhat == listDaTaduong.length()) {
                        Flagupdate1 = true;
                    } else {
                        Flagupdate1 = false;
                    }


                    //     loadDataDuongfromDB();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jsonobj.has("listkh")) {
                try {
                    Log.e("Them database_KH: ", "Lay JSONARRAY");
                    JSONArray listKH = null;
                    try {
                        listKH = jsonobj.getJSONArray("listkh");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int soluongcapnhat = 0;
                    Log.e("Them database_KH: ", "JSONARRAY_LENGTH " + listKH.length());
                    for (int j = 0; j < listKH.length(); j++) {
                        JSONObject objKH = null;
                        try {
                            objKH = listKH.getJSONObject(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String ChiSo = "";
                        String ChiSocon = "";
                        String ChiSo1 = "";
                        String ChiSo1con = "";
                        String ChiSo2 = "";
                        String ChiSo2con = "";
                        String ChiSo3 = "";
                        String ChiSo3con = "";
                        String DanhBo = "";
                        String DiaChi = "";
                        String DienThoai = "";
                        String DienThoai2 = "";
                        String GhiChu = "";
                        String Lat = "";
                        String Lon = "";
                        String MaKhachHang = "";
                        String NhanVien = "";
                        String SLTieuThu = "";
                        String SLTieuThu1 = "";
                        String SLTieuThu1con = "";
                        String SLTieuThu2 = "";
                        String SLTieuThu2con = "";
                        String SLTieuThu3 = "";
                        String SLTieuThu3con = "";
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
                        String batthuong = "";
                        String maduong = "";
                        String capnhat = "";
                        String loaikhmoi = "";
                        String NTSH = "";
                        String tiennuoc = "";
                        String phi = "";
                        String tongcong = "";
                        String novat = "";
                        String thue = "";
                        String m3muc1 = "";
                        String m3muc2 = "";
                        String m3muc3 = ""; //ko sd
                        String m3muc4 = ""; //ko sd
                        String tienmuc1 = "";//ko sd
                        String tienmuc2 = "";//ko sd
                        String tienmuc3 = "";//ko sd
                        String tienmuc4 = "";//ko sd
                        String ngaythanhtoan = "";
                        String capnhatthu = "";
                        String nhanvienthu = "";
                        String tamthu = "";

                        if (objKH.has("capnhat")) {
                            capnhat = objKH.getString("capnhat").toString().trim();
                        }
                        if (objKH.has("loaikhmoi")) {
                            loaikhmoi = objKH.getString("loaikhmoi").toString().trim();
                        }
                        if (objKH.has("NTSH")) {
                            NTSH = objKH.getString("NTSH").toString().trim();
                        }
                        if (objKH.has("nhanvienthu")) {
                            nhanvienthu = objKH.getString("nhanvienthu").toString().trim();
                        }
                        if (objKH.has("tamthu")) {
                            tamthu = objKH.getString("tamthu").toString().trim();
                        }

                        if (objKH.has("m3muc1")) {
                            m3muc1 = objKH.getString("m3muc1").toString().trim();
                        }
                        if (objKH.has("m3muc2")) {
                            m3muc2 = objKH.getString("m3muc2").toString().trim();
                        }
                        if (objKH.has("m3muc3")) {
                            m3muc3 = objKH.getString("m3muc3").toString().trim();
                        }
                        if (objKH.has("m3muc4")) {
                            m3muc4 = objKH.getString("m3muc4").toString().trim();
                        }
                        if (objKH.has("tienmuc1")) {
                            tienmuc1 = objKH.getString("tienmuc1").toString().trim();
                        }
                        if (objKH.has("tienmuc2")) {
                            tienmuc2 = objKH.getString("tienmuc2").toString().trim();
                        }
                        if (objKH.has("tienmuc3")) {
                            tienmuc3 = objKH.getString("tienmuc3").toString().trim();
                        }
                        if (objKH.has("tienmuc4")) {
                            tienmuc4 = objKH.getString("tienmuc4").toString().trim();
                        }
                        if (objKH.has("ChiSo")) {
                            ChiSo = objKH.getString("ChiSo").toString().trim();
                        }

                        if (objKH.has("ChiSo1")) {
                            ChiSo1 = objKH.getString("ChiSo1").toString().trim();
                        }
                        if (objKH.has("ChiSo1con")) {
                            ChiSo1con = objKH.getString("ChiSo1con").toString().trim();
                        }

                        if (objKH.has("ChiSo2")) {
                            ChiSo2 = objKH.getString("ChiSo2").toString().trim();
                        }
                        if (objKH.has("ChiSo2con")) {
                            ChiSo2con = objKH.getString("ChiSo2con").toString().trim();
                        }

                        if (objKH.has("ChiSo3")) {
                            ChiSo3 = objKH.getString("ChiSo3").toString().trim();
                        }
                        if (objKH.has("ChiSo3con")) {
                            ChiSo3con = objKH.getString("ChiSo3con").toString().trim();
                        }

                        if (objKH.has("ChiSocon")) {
                            ChiSocon = objKH.getString("ChiSocon").toString().trim();
                        }
                        if (objKH.has("DanhBo")) {
                            DanhBo = objKH.getString("DanhBo").toString().trim();
                        }

                        if (objKH.has("DiaChi")) {
                            DiaChi = objKH.getString("DiaChi").toString().trim();
                        }
                        if (objKH.has("DienThoai")) {
                            DienThoai = objKH.getString("DienThoai").toString().trim();
                        }
                        if (objKH.has("DienThoai2")) {
                            DienThoai2 = objKH.getString("DienThoai2").toString().trim();
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
                        if (objKH.has("SLTieuThu1con")) {
                            SLTieuThu1con = objKH.getString("SLTieuThu1con").toString().trim();
                        }

                        if (objKH.has("SLTieuThu2")) {
                            SLTieuThu2 = objKH.getString("SLTieuThu2").toString().trim();
                        }
                        if (objKH.has("SLTieuThu2con")) {
                            SLTieuThu2con = objKH.getString("SLTieuThu2con").toString().trim();
                        }

                        if (objKH.has("SLTieuThu3")) {
                            SLTieuThu3 = objKH.getString("SLTieuThu3").toString().trim();
                        }
                        if (objKH.has("SLTieuThu3con")) {
                            SLTieuThu3con = objKH.getString("SLTieuThu3con").toString().trim();
                        }

                        if (objKH.has("SLTieuThucon")) {
                            SLTieuThucon = objKH.getString("SLTieuThucon").toString().trim();
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

                        }

                        if (objKH.has("masotlk")) {
                            masotlk = objKH.getString("masotlk").toString().trim();
                        }


                        if (objKH.has("NOVAT")) {
                            novat = objKH.getString("NOVAT").toString().trim();
                            if (novat.equals("")) {
                                novat = "0";
                            }
                        }
                        if (objKH.has("batthuong")) {
                            batthuong = objKH.getString("batthuong").toString().trim();
                        }
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
                        if (objKH.has("maduong")) {
                            maduong = objKH.getString("maduong").toString().trim();
                        }
                        if (objKH.has("capnhatthu")) {
                            capnhatthu = objKH.getString("capnhatthu").toString().trim();
                        }
                        if (objKH.has("ngaythanhtoan")) {
                            ngaythanhtoan = objKH.getString("ngaythanhtoan").toString().trim();
                        }


                        JSONKHTHU kh = new JSONKHTHU();
                        kh.setChiSo(ChiSo);
                        kh.setChiSocon(ChiSocon);
                        kh.setChiSo1(ChiSo1);
                        kh.setChiSo1con(ChiSo1con);
                        kh.setChiSo2(ChiSo2);
                        kh.setChiSo2con(ChiSo2con);
                        kh.setChiSo3(ChiSo3);
                        kh.setChiSo3con(ChiSo3con);
                        kh.setDanhBo(DanhBo);
                        kh.setDiaChi(DiaChi);
                        kh.setDienThoai(DienThoai);
                        kh.setGhiChu(GhiChu);
                        kh.setLat(Lat);
                        kh.setLon(Lon);
                        kh.setMaKhachHang(MaKhachHang);
                        kh.setNhanVien(NhanVien);
                        kh.setSLTieuThu(SLTieuThu);
                        kh.setSLTieuThu1(SLTieuThu1);
                        kh.setSLTieuThu1con(SLTieuThu1con);
                        kh.setSLTieuThu2(SLTieuThu2);
                        kh.setSLTieuThu2con(SLTieuThu2con);
                        kh.setSLTieuThu3(SLTieuThu3);
                        kh.setSLTieuThu3con(SLTieuThu3con);
                        kh.setSLTieuThucon(SLTieuThucon);
                        kh.setSTT(STT);
                        kh.setTenKhachHang(TenKhachHang);
                        kh.setThoiGian(ThoiGian);
                        kh.setTrangThaiTLK(TrangThaiTLK);
                        kh.setChitietloai(chitietloai);
                        kh.setCotlk(cotlk);
                        kh.setDinhmuc(dinhmuc);
                        kh.setHieutlk(hieutlk);
                        kh.setLoaikh(loaikh);
                        kh.setMasotlk(masotlk);
                        kh.setBatthuong(batthuong);
                        kh.setMaduong(maduong);
                        kh.setCapnhat(capnhat);
                        kh.setLoaikhmoi(loaikhmoi);
                        kh.setNTSH(NTSH);
                        kh.setTiennuoc(tiennuoc);
                        kh.setPhi(phi);
                        kh.setTongcong(tongcong);
                        kh.setNovat(novat);
                        kh.setThue(thue);
                        kh.setM3muc1(m3muc1);
                        kh.setM3muc2(m3muc2);
                        kh.setM3muc3(m3muc3);
                        kh.setM3muc4(m3muc4);
                        kh.setDienThoai2(DienThoai2);
                        kh.setTienmuc2(tienmuc2);
                        kh.setTienmuc3(tienmuc3);
                        kh.setTienmuc4(tienmuc4);
                        kh.setNgaythanhtoan(ngaythanhtoan);
                        kh.setCapnhatthu(capnhatthu);
                        kh.setNhanvienthu(nhanvienthu);
                        kh.setTamthu(tamthu);


                        Log.e("Them database_KH: ", "Da ton tai " + j + ":" + MaKhachHang + ":" + khachhangDAO.checkExistKH(MaKhachHang, maduong));
                        boolean kt = khachhangDAO.addTable_KHThu(kh);
                        //  boolean kt = khachhangDAO.updateTable_KH(kh);

                        if (kt) {
                            Log.e("Them database_KH: " + MaKhachHang + " " + TenKhachHang, "Thanh cong");
                            soluongcapnhat++;
                        } else {
                            Log.e("Them database_KH: " + MaKhachHang + " " + TenKhachHang, "ko Thanh cong");

                        }


                        long status = (j + 1) * 100 / listKH.length();
                        //     String.valueOf(status)
                        publishProgress(String.valueOf(status));


                    }
                    Log.e("BangKH", soluongcapnhat + " " + listKH.length());
                    if (soluongcapnhat == listKH.length()) {
                        Flagupdate2 = true;

                    } else {
                        Flagupdate2 = false;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (jsonobj.has("listthanhtoan")) {
                try {
                    Log.e("Them database_KH: ", "Lay JSONARRAY");
                    JSONArray listthanhtoan = null;
                    try {
                        listthanhtoan = jsonobj.getJSONArray("listthanhtoan");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int soluongcapnhat = 0;
                    Log.e("Them database_KH: ", "JSONARRAY_LENGTH " + listthanhtoan.length());
                    for (int j = 0; j < listthanhtoan.length(); j++) {
                        JSONObject objKH = null;
                        try {
                            objKH = listthanhtoan.getJSONObject(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String BienLai = "";
                        String ChiSoCu = "";
                        String ChiSoMoi = "";
                        String GhiChu = "";
                        String TransactionID = "";
                        String Lon = "";
                        String MaKhachHang = "";
                        String SLTieuThu = "";
                        String maduong = "";
                        String tiennuoc = "";
                        String phi = "";
                        String tongcong = "";
                        String kyhd = "";
                        String thue = "";
                        String m3muc1 = "";
                        String m3muc2 = "";
                        String m3muc3 = "";
                        String m3muc4 = "";
                        String tienmuc1 = "";
                        String tienmuc2 = "";
                        String tienmuc3 = "";
                        String tienmuc4 = "";
                        String ngaythanhtoan = "";
                        String ngaythanhtoanrequest = "";
                        String capnhatthu = "";
                        String nhanvienthu = "";
                        String laninbiennhan = "";
                        String laninthongbaosau = "";
                        String laninthongbaotruoc = "";
                        String thanhtoantamthu = "";
                        String TuNgay = "";
                        String DenNgay = "";

                        if (objKH.has("BienLai")) {
                            BienLai = objKH.getString("BienLai").toString().trim();
                        }
                        if (objKH.has("ChiSoCu")) {
                            ChiSoCu = objKH.getString("ChiSoCu").toString().trim();
                        }

                        if (objKH.has("TuNgay")) {
                            TuNgay = objKH.getString("TuNgay").toString().trim();
                        }
                        if (objKH.has("DenNgay")) {
                            DenNgay = objKH.getString("DenNgay").toString().trim();
                        }
                        if (objKH.has("ChiSoMoi")) {
                            ChiSoMoi = objKH.getString("ChiSoMoi").toString().trim();
                        }
                        if (objKH.has("nhanvienthu")) {
                            nhanvienthu = objKH.getString("nhanvienthu").toString().trim();
                        }
                        if (objKH.has("TransactionID")) {
                            TransactionID = objKH.getString("TransactionID").toString().trim();
                        }

                        if (objKH.has("m3muc1")) {
                            m3muc1 = objKH.getString("m3muc1").toString().trim();
                        }
                        if (objKH.has("m3muc2")) {
                            m3muc2 = objKH.getString("m3muc2").toString().trim();
                        }
                        if (objKH.has("m3muc3")) {
                            m3muc3 = objKH.getString("m3muc3").toString().trim();
                        }
                        if (objKH.has("m3muc4")) {
                            m3muc4 = objKH.getString("m3muc4").toString().trim();
                        }
                        if (objKH.has("tienmuc1")) {
                            tienmuc1 = objKH.getString("tienmuc1").toString().trim();
                        }
                        if (objKH.has("tienmuc2")) {
                            tienmuc2 = objKH.getString("tienmuc2").toString().trim();
                        }
                        if (objKH.has("tienmuc3")) {
                            tienmuc3 = objKH.getString("tienmuc3").toString().trim();
                        }
                        if (objKH.has("tienmuc4")) {
                            tienmuc4 = objKH.getString("tienmuc4").toString().trim();
                        }
                        if (objKH.has("kyhd")) {
                            kyhd = objKH.getString("kyhd").toString().trim();
                        }
                        if (objKH.has("ngaythanhtoan")) {
                            ngaythanhtoan = objKH.getString("ngaythanhtoan").toString().trim();
                        }
                        if (objKH.has("ngaythanhtoanrequest")) {
                            ngaythanhtoanrequest = objKH.getString("ngaythanhtoanrequest").toString().trim();
                        }
                        if (objKH.has("laninbiennhan")) {
                            laninbiennhan = objKH.getString("laninbiennhan").toString().trim();
                        }

                        if (objKH.has("laninthongbaosau")) {
                            laninthongbaosau = objKH.getString("laninthongbaosau").toString().trim();
                        }
                        if (objKH.has("laninthongbaotruoc")) {
                            laninthongbaotruoc = objKH.getString("laninthongbaotruoc").toString().trim();
                        }

                        if (objKH.has("thanhtoantamthu")) {
                            thanhtoantamthu = objKH.getString("thanhtoantamthu").toString().trim();
                        }


                        if (objKH.has("GhiChu")) {
                            GhiChu = objKH.getString("GhiChu").toString().trim();
                        }


                        if (objKH.has("Lon")) {
                            Lon = objKH.getString("Lon").toString().trim();
                        }
                        if (objKH.has("MaKhachHang")) {
                            MaKhachHang = objKH.getString("MaKhachHang").toString().trim();
                        }


                        if (objKH.has("SLTieuThu")) {
                            SLTieuThu = objKH.getString("SLTieuThu").toString().trim();
                        }


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
                        if (objKH.has("maduong")) {
                            maduong = objKH.getString("maduong").toString().trim();
                        }
                        if (objKH.has("capnhatthu")) {
                            capnhatthu = objKH.getString("capnhatthu").toString().trim();
                        }


                        JSONTHANHTOANTHU kh = new JSONTHANHTOANTHU();

                        kh.setBIENLAI(BienLai);
                        kh.setMaKhachHang(MaKhachHang);
                        kh.setChiSoMoi(ChiSoMoi);
                        kh.setChiSoCu(ChiSoCu);
                        kh.setSLTieuThu(SLTieuThu);
                        kh.setTRANSACTIONID(TransactionID);
                        kh.setTuNgay(TuNgay);
                        kh.setKyhd(kyhd);
                        kh.setNhanvienthu(nhanvienthu);
                        kh.setM3muc1(m3muc1);
                        kh.setM3muc2(m3muc2);
                        kh.setM3muc3(m3muc3);
                        kh.setM3muc4(m3muc4);
                        kh.setTienmuc1(tienmuc1);
                        kh.setTienmuc2(tienmuc2);
                        kh.setTienmuc3(tienmuc3);
                        kh.setTienmuc4(tienmuc4);
                        kh.setTiennuoc(tiennuoc);
                        kh.setPhi(phi);
                        kh.setThue(thue);
                        kh.setTongcong(tongcong);
                        kh.setNgaythanhtoan(ngaythanhtoan);
                        kh.setCapnhatthu(capnhatthu);
                        kh.setDenNgay(DenNgay);
                        kh.setLaninbiennhan(laninbiennhan);
                        kh.setLaninthongbaotruoc(laninthongbaotruoc);
                        kh.setLaninthongbaosau(laninthongbaosau);
                        kh.setGhiChu(GhiChu);
                        kh.setMaduong(maduong);
                        kh.setThanhtoantamthu(thanhtoantamthu);


                        Log.e("Them database_KH: ", "Da ton tai " + j + ":" + MaKhachHang + ":" + khachhangDAO.checkExistKH(MaKhachHang, maduong));
                        boolean kt = thanhToanDAO.addTable_ThanhToanThu(kh);
                        //  boolean kt = khachhangDAO.updateTable_KH(kh);

                        if (kt) {
                            Log.e("Them database_KH: " + MaKhachHang, "  Thanh cong");
                            soluongcapnhat++;
                        } else {
                            Log.e("Them database_KH: " + MaKhachHang, " ko Thanh cong");

                        }


                        long status = (j + 1) * 100 / listthanhtoan.length();
                        //     String.valueOf(status)
                        publishProgress(String.valueOf(status));


                    }
                    Log.e("Bangthanhtoan", soluongcapnhat + " " + listthanhtoan.length());
                    if (soluongcapnhat == listthanhtoan.length()) {
                        Flagupdate3 = true;

                    } else {
                        Flagupdate3 = false;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (Flagupdate1 && Flagupdate2 && Flagupdate3) {
                FlagupdateDB = true;
            } else {
                FlagupdateDB = false;
            }

            return FlagupdateDB;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            // String duong = values[1];
            prgTime.setProgress(Integer.parseInt(status));

            // update giá trị ở TextView
            prgTime.setText(getString(R.string.load_status));
        }


        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
            if (result) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadThuActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Lấy dữ liệu từ tập tin thành công");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        spdata.luuDataTuDongLuuTapTin(1);
                        spdata.luuChucNangGhiThu();
                        Intent myIntent = new Intent(LoadThuActivity.this, StartActivity.class);
                        startActivity(myIntent);
                        LoadThuActivity.this.finish();
                        // button "no" ẩn dialog đi
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog


            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadThuActivity.this);
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

    private Boolean KiemTraTonTaiDuLieu() {
        if (duongDAO.countDuong() <= 0 && khachhangDAO.countKhachHangAll() <= 0 && thanhToanDAO.countThanhToanAll() <= 0) {
            return false;
        } else {
            return true;
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
        JSONBACKUPTHU jsondata = new JSONBACKUPTHU();
        KhachHangThuDAO khachhangdao = new KhachHangThuDAO(con);
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


    private void loadData() {
//        getSdCardPath();
        File file = new File(duongdanfile);
        if (file.exists()) {
            if (KiemTraTonTaiDuLieu()) {
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
                        String filename1 = thumucchuafile + "/TUDONGCAPNHATTHU";

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


                //xóa sqlite
                MyDatabaseHelper mydata = new MyDatabaseHelper(con);
                SQLiteDatabase db = mydata.openDB();
                mydata.resetDatabaseTHU(db);


                //Thêm data vào sqlite
                MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
                task.execute(duongdanfile);

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
                spdata.luuDataUpdateServer(0);
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi, Bien.bienbkall, Bien.bienbkcg, Bien.bienbkdg, Bien.bienbkdghn);


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
            } else {
                MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
                task.execute(duongdanfile);
                spdata = new SPData(con);

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
                spdata.luuDataUpdateServer(0);
                spdata.luuDataFlagGhivaBackUpTrongSP(Bien.bienghi, Bien.bienbkall, Bien.bienbkcg, Bien.bienbkdg, Bien.bienbkdghn);


            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoadThuActivity.this);
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

    private void askPermissionLocation() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (canRead) {

            // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
            //  task.execute(duongdanfile);
            //   readFileandSaveDatabase();
            loadData();

        }

        if (ContextCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoadThuActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

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
        if (Build.VERSION.SDK_INT >= 23) {

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

                        GPSTracker gps = new GPSTracker(con, LoadThuActivity.this);

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


}
