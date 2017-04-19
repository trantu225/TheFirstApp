package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

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


public class MainActivity extends AppCompatActivity {

    TextView danhsachduong;
    ImageButton btnghi;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String filename = "";
    String duongdanfile = "";
    private static String dataGhi = "";
    SpaceNavigationView spaceNavigationView;
    Context con;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    KhachHangDTO khachhang;
    TextView STT, MaKH, HoTen, DiaChi, MaTLK, HieuTLK, CoTLK, ChiSo1, ChiSo2, ChiSo3, m31, m32, m33, ChiSoCon1, ChiSoCon2, ChiSoCon3, m3con1, m3con2, m3con3, m3moi, m3conmoi;
    EditText DienThoai, ChiSoMoi, TinhTrangTLK;
    ImageButton DoiSDT,Toi,Lui,Ghi;
    LinearLayout lay_toi , lay_lui , lay_ghi;

    int SoLuongKH = 0;
    String maduong_nhan="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;

        getSupportActionBar().setTitle(R.string.tab_ghinuoc);
        taoView();
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);



        //----------------------------------------------------------------------------
        //lấy intent gọi Activity này
        //Lấy kết quả khi chọn button ghi nước tại listactivity
        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key Calculation
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_MADUONG);
        if (packageFromCaller == null) {
            Toast.makeText(this, "Chưa chọn mã đường", Toast.LENGTH_SHORT).show();
        } else {
            //Có Bundle rồi thì lấy các thông số dựa vào key NUMBERA và NUMBERB
            maduong_nhan = packageFromCaller.getString(Bien.MADUONG);
            SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
            setDataForView(Bien.STT_HienTai,maduong_nhan);
            Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
        }
        //--------------------------------------------------------------------------
        lay_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = Integer.parseInt(Bien.STT_HienTai) + 1;
                Bien.STT_HienTai = String.valueOf(next);
                Log.e("BienSTTHIenTai", Bien.STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (next > SoLuongKH) {
                    lay_toi.setEnabled(false);
                } else {
                    lay_toi.setEnabled(true);
                }
                if (next > 0 && next < SoLuongKH) {
                    setDataForView(Bien.STT_HienTai, maduong_nhan);
                } else {
                    Log.e("BienSTTHIenTai", Bien.STT_HienTai);
                }

            }
        });

        lay_lui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pre = Integer.parseInt(Bien.STT_HienTai) - 1;
                Bien.STT_HienTai = String.valueOf(pre);

                Log.e("BienSTTHIenTai", Bien.STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (pre <= 0) {
                    lay_lui.setEnabled(false);
                } else {
                    lay_lui.setEnabled(true);
                }
                if (pre > 0 && pre < SoLuongKH) {
                    Log.e("BienSTTHIenTai", "chay vao 3");
                    setDataForView(Bien.STT_HienTai, maduong_nhan);
                } else {
                    Log.e("BienSTTHIenTai", "chay vao 4");

                }
            }
        });
    }

    private void setDataForView(String tt, String maduong) {
        //Lấy khách hàng có stt hiện tại...mặc đình là 1
        khachhang =  khachhangDAO.getKHTheoSTT_Duong(tt,maduong);
        STT.setText(khachhang.getSTT());
        MaKH.setText(khachhang.getMaKhachHang());
        HoTen.setText(khachhang.getTenKhachHang());
        DiaChi.setText(khachhang.getDiaChi());
        MaTLK.setText(khachhang.getMasotlk());
        HieuTLK.setText(khachhang.getHieutlk());
        CoTLK.setText(khachhang.getCotlk());
        ChiSo1.setText(khachhang.getChiSo1());
        ChiSo2.setText(khachhang.getChiSo2());
        ChiSo3.setText(khachhang.getChiSo3());
        m31.setText(khachhang.getSLTieuThu1());
        m32.setText(khachhang.getSLTieuThu2());
        m33.setText(khachhang.getSLTieuThu3());
        ChiSoCon1.setText(khachhang.getChiSo1con());
        ChiSoCon2.setText(khachhang.getChiSo2con());
        ChiSoCon3.setText(khachhang.getChiSo3con());
        m3con1.setText(khachhang.getSLTieuThu1con());
        m3con2.setText(khachhang.getSLTieuThu2con());
        m3con3.setText(khachhang.getSLTieuThu3con());
        DienThoai.setText(khachhang.getDienThoai());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    private void selectItem(MenuItem item) {

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_save:

                break;
            case R.id.action_search:

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
        HoTen= (TextView) findViewById(R.id.tv_hotenKH);
        DiaChi= (TextView) findViewById(R.id.tv_diachiKH);
        MaTLK= (TextView) findViewById(R.id.tv_maTLK);
        HieuTLK= (TextView) findViewById(R.id.tv_hieuTLK);
        CoTLK= (TextView) findViewById(R.id.tv_coTLK);
        ChiSo1= (TextView) findViewById(R.id.tv_chisocu1);
        ChiSo2= (TextView) findViewById(R.id.tv_chisocu2);
        ChiSo3= (TextView) findViewById(R.id.tv_chisocu3);
        m31= (TextView) findViewById(R.id.tv_m3cu1);
        m32= (TextView) findViewById(R.id.tv_m3cu2);
        m33= (TextView) findViewById(R.id.tv_m3cu3);
        ChiSoCon1= (TextView) findViewById(R.id.tv_chisocu1con);
        ChiSoCon2= (TextView) findViewById(R.id.tv_chisocu2con);
        ChiSoCon3= (TextView) findViewById(R.id.tv_chisocu3con);
        m3con1= (TextView) findViewById(R.id.tv_m3cu1con);
        m3con2= (TextView) findViewById(R.id.tv_m3cu2con);
        m3con3= (TextView) findViewById(R.id.tv_m3cu3con);
        m3moi= (TextView) findViewById(R.id.tv_m3moi);
        m3conmoi= (TextView) findViewById(R.id.tv_m3moicon);

        DienThoai = (EditText) findViewById(R.id.edit_DienThoaiKH);
        ChiSoMoi = (EditText) findViewById(R.id.edit_chisomoi);
        TinhTrangTLK = (EditText) findViewById(R.id.edit_chisomoicon);

        DoiSDT  = (ImageButton) findViewById(R.id.imgbtn_doi);
        Ghi = (ImageButton) findViewById(R.id.btn_ghinuoc);
        Toi = (ImageButton) findViewById(R.id.btn_toi);


        lay_toi =(LinearLayout)findViewById(R.id.layout_toi);
        lay_lui =(LinearLayout)findViewById(R.id.layout_lui);
        lay_ghi =(LinearLayout)findViewById(R.id.layout_ghi);

    }

    private void askPermissionAndWriteFile(String path, String data) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {

            this.writeFile(path, data);
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
                        // readFile(duongdanfile);
                        //luu database
                        // MyJsonTaskDatabase task = new MyJsonTaskDatabase();
                        //  task.execute(duongdanfile);
                        //                       Log.e("canread", "chay vao request read");
//                        readFileandSaveDatabase();

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
            Toast.makeText(con, "Permission Cancelled!", Toast.LENGTH_SHORT).show();
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

            Toast.makeText(con, filename + " saved", Toast.LENGTH_LONG).show();
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
            //String finalFileContent = fileContent;


        } catch (IOException e) {
            e.printStackTrace();

        }

        return fileContent;
    }


    //check database
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private static boolean doesFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    private void loadDataDuongfromDB() {
        Bien.listDuongChuaGhi = duongDAO.getAllDuongChuaGhi();
        String maduongchuaghi = "";
        if (Bien.listDuongChuaGhi.size() > 0) {
            for (DuongDTO x : Bien.listDuongChuaGhi) {
                maduongchuaghi += x.getMaDuong() + " ";

            }
            danhsachduong.setText(maduongchuaghi);
        } else {
            danhsachduong.setText(R.string.THONGBAO_DUONGCHUAGHI_ERROR);
        }
        int soluongKH = khachhangDAO.countKhachHangTheoDuong("01");
        Log.e("soluongKh", String.valueOf(soluongKH));
    }
}

