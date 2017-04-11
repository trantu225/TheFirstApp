package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.IOException;


import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;


public class MainActivity extends Fragment {

    TextView danhsachduong;
    ImageButton btnghi;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String filename = "";
    String duongdanfile ="";
    private static String dataGhi  = "";
    BottomNavigationView mBottomNav;

    DuongDAO duongDAO ;
    KhachHangDAO khachhangDAO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        btnghi = (ImageButton) v.findViewById(R.id.btn_ghi);
        danhsachduong = (TextView) v.findViewById(R.id.tv_danhsachduongchuaghi);
        mBottomNav = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);

        File extStore = Environment.getExternalStorageDirectory();

        filename = getString(R.string.data_file_name);
        duongdanfile = extStore.getAbsolutePath() + "/" + filename;

        duongDAO = new DuongDAO(getContext());
        khachhangDAO = new KhachHangDAO(getContext());

        btnghi.setBackgroundResource(R.mipmap.btn_ghi);
        btnghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndReadFile();

            }
        });


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectItem(item);
                return true;
            }
        });

        return v;
    }
    private void selectItem(MenuItem item) {

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(getContext(), "Seacrh", Toast.LENGTH_SHORT).show();
                break;

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }



    private void askPermissionAndWriteFile(String path, String data) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {

            this.writeFile(path,data);
        }
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.e("canread", String.valueOf(canRead));
        if (canRead) {

            Log.e("canread", "chay vao if");
           // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
          //  task.execute(duongdanfile);
            readFileandSaveDatabase();
        }
    }


    // Với Android Level >= 23 bạn phải hỏi người dùng cho phép các quyền với thiết bị
    // (Chẳng hạn đọc/ghi dữ liệu vào thiết bị).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(getContext(), permissionName);


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
                       // readFile(duongdanfile);
                        //luu database
                       // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
                      //  task.execute(duongdanfile);
 //                       Log.e("canread", "chay vao request read");
//                        readFileandSaveDatabase();
                        readFileandSaveDatabase();
                        loadDataDuongfromDB();
                }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       // writeFile(duongdanfile,dataGhi);
                    }
                }
            }
        } else {
            Toast.makeText(getContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


    private void writeFile(String path, String data) {
        // Thư mục gốc của SD Card.


        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();

            Toast.makeText(getContext(), filename + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFile(String path) {
        Log.i("ExternalStorageDemo", "Read file: " + path);
        Log.e("file path ne", path);
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
             //String finalFileContent = fileContent;


        } catch (IOException e) {
            e.printStackTrace();

        }

        return fileContent;
    }

    public void readFileandSaveDatabase(){
        boolean flagDB=false , flagFile = false;

        //check database
        flagDB = doesDatabaseExist(getContext(), MyDatabaseHelper.DATABASE_NAME);
        Log.e("DB exist:", String.valueOf(flagDB));
        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
        SQLiteDatabase sqldb = db.openDB();
        sqldb.getVersion();

        Log.e("DB File:", String.valueOf( sqldb.getVersion()));
        //check file
        flagFile = doesFileExist(duongdanfile);
        Log.e("DB File:", String.valueOf(flagFile));
        //DB = true && file = false => load từ Db
        if(flagDB==true && flagFile == false){
            //Load dataduong
            loadDataDuongfromDB();
            //load datakhachhang
        }
        else if (flagDB==false && flagFile == false){


        }
        else
        {
            //Doc tu file
            MyJsonTaskDatabasefromFile task = new MyJsonTaskDatabasefromFile();
            task.execute(duongdanfile);
        }
        //db = false && file = true => load từ file

        //DB = true && file = true
        //Check version
        //DB.version < File.version
    }


    public class MyJsonTaskDatabasefromFile extends AsyncTask<String, JSONObject, JSONObject > {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            //Lấy URL truyền vào
            String jsontext =  readFile(params[0])  ;

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(jsontext);
            } catch (JSONException e) {
                e.printStackTrace();
            }

          return jsonObj;
        }

        @Override
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);
            //ta cập nhật giao diện ở đây:
            JSONObject jsonObj = values[0];
         /*  try {
            //kiểm tra xem có tồn tại thuộc tính id hay không
                if (jsonObj.has("tongSLkh"))
                    viewjson.setText(jsonObj.getString("tongSLkh"));

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(),
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }*/
        }



        @Override
        protected void onPostExecute(JSONObject result) {

            super.onPostExecute(result);
            if (result.has("ListTiwaread")) {
                try {
                    JSONArray listtiwaread = result.getJSONArray("ListTiwaread");
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
                                Log.e("kiem tra KH da ton tai hay chua", String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong)));
                                if(khachhangDAO.countKhachHangTheoDuong(maduong) <=0) {
                                 //   duongDAO = new DuongDAO(MainActivity.this);
                                 //   khachhangDAO = new KhachHangDAO(MainActivity.this);
                                    Log.e("Them database_KH: ", "chay zo day rui");

                                    boolean kt = khachhangDAO.addTable_KH(kh,maduong);
                                    if (kt) {
                                        Log.e("Them database_KH: " + maduong, "Thanh cong");
                                    } else {
                                        Log.e("Them database_KH: " + maduong, "ko Thanh cong");

                                    }
                                    //loadDataDuongfromDB();
                                }
                                else{
                                    Log.e("Them database_KH: ", "KIem tra tồn tại khach hang thu "+j   +":" +MaKhachHang );
                                    if(khachhangDAO.checkExistKH(MaKhachHang,maduong)) {
                                        //    loadDataDuongfromDB();
                                        Log.e("Them database_KH: ", "Da ton tai "+j   +":" +MaKhachHang );
                                    }
                                    else{
                                        boolean kt = khachhangDAO.addTable_KH(kh,maduong);
                                        if (kt) {
                                            Log.e("Them database_KH: " + TenKhachHang, "Thanh cong");
                                        } else {
                                            Log.e("Them database_KH: " + TenKhachHang, "ko Thanh cong");

                                        }
                                    }
                                }

                            }

                        }
                    }
                    loadDataDuongfromDB();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }



    //check database
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
    private static boolean doesFileExist(String path){
        File file =  new File(path);
        return file.exists();
    }
    private void loadDataDuongfromDB(){
        Bien.listDuongChuaGhi =  duongDAO.getAllDuongChuaGhi();
        String maduongchuaghi = "";
        if( Bien.listDuongChuaGhi.size() >0 ) {
            for (DuongDTO x : Bien.listDuongChuaGhi) {
                maduongchuaghi += x.getMaDuong() + " ";

            }
            danhsachduong.setText(maduongchuaghi);
        }
        else{
            danhsachduong.setText(R.string.THONGBAO_DUONGCHUAGHI_ERROR);
        }
        int soluongKH= khachhangDAO.countKhachHangTheoDuong("01");
        Log.e("soluongKh",String.valueOf(soluongKH));
    }
}

