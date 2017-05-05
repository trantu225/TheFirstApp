package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.TableRow;
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
import tiwaco.thefirstapp.Database.SPData;


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
    TextView STT, MaKH, HoTen, DiaChi, MaTLK, HieuTLK, CoTLK, ChiSo1, ChiSo2, ChiSo3, m31, m32, m33, ChiSoCon1, ChiSoCon2, ChiSoCon3, m3con1, m3con2, m3con3, m3moi, m3conmoi, DuongDangGhi, ConLai;
    EditText DienThoai, ChiSoMoi, TinhTrangTLK,GhiChu;
    TableRow chisocu_con_lb, chisocu_con, chisomoi_con_lb, chisomoi_con;
    ImageButton DoiSDT,Toi,Lui,Ghi;
    LinearLayout lay_toi , lay_lui , lay_ghi;
    String STT_HienTai ="1";
    int SoLuongKH = 0;
    String maduong_nhan="",stt_nhan ="";
    SPData spdata;
    String tenduong;
    String soKHconlai,tongsoKHTheoDuong;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;

        getSupportActionBar().setTitle(R.string.tab_ghinuoc);
        taoView();
        spdata = new SPData(con);
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);



        //----------------------------------------------------------------------------
        //lấy intent gọi Activity này
        //Lấy kết quả khi chọn button ghi nước tại listactivity
        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key Calculation
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_MADUONG);
        if (packageFromCaller == null) {
            //get sharepreferences
            String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP(); //lấy đường đang ghi
            String STT ="1";  //tìm min(stt) của khách hàng chưa nghi tại đường đang ghi
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
                alertDialog.show();
            }
            else{
                maduong_nhan = SPduongdangghi;
                STT_HienTai = STT;
                SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
                setDataForView(STT_HienTai,maduong_nhan);
                Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
            }


        } else {
            //Có Bundle rồi thì lấy các thông số dựa vào key maduong và stt
            maduong_nhan = packageFromCaller.getString(Bien.MADUONG);
            stt_nhan =  packageFromCaller.getString(Bien.STT);
            STT_HienTai =stt_nhan;
            spdata.luuDataDuongDangGhiTrongSP(maduong_nhan);//luu vao sharepreferences
            SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
            setDataForView(STT_HienTai,maduong_nhan);
            Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
        }
        //--------------------------------------------------------------------------
        lay_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = Integer.parseInt(STT_HienTai) + 1;
                STT_HienTai = String.valueOf(next);
                Log.e("BienSTTHIenTai", STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (next+1 > SoLuongKH) {
                    lay_toi.setEnabled(false);
                    Toi.setEnabled(false);
                } else {
                    lay_toi.setEnabled(true);
                    Toi.setEnabled(true);
                }
                if (next > 0 && next <= SoLuongKH) {
                    setDataForView(STT_HienTai, maduong_nhan);
                    next = Integer.parseInt(STT_HienTai) + 1;
                    int pre = Integer.parseInt(STT_HienTai) - 1;
                    if (next > SoLuongKH) {
                        lay_toi.setEnabled(false);
                        Toi.setEnabled(false);
                    } else {
                        lay_toi.setEnabled(true);
                        Toi.setEnabled(true);
                    }
                    if (pre <= 0) {
                        lay_lui.setEnabled(false);
                        Lui.setEnabled(false);
                    } else {
                        lay_lui.setEnabled(true);
                        Lui.setEnabled(true);
                    }
                } else {
                    Log.e("BienSTTHIenTai", STT_HienTai);
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
                int pre = Integer.parseInt(STT_HienTai) - 1;
                STT_HienTai = String.valueOf(pre);

                Log.e("BienSTTHIenTai", STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (pre-1 <= 0) {
                    lay_lui.setEnabled(false);
                    Lui.setEnabled(false);
                } else {
                    lay_lui.setEnabled(true);
                    Lui.setEnabled(true);
                }
                if (pre > 0 && pre <= SoLuongKH) {
                    Log.e("BienSTTHIenTai", "chay vao 3");
                    setDataForView(STT_HienTai, maduong_nhan);
                    pre = Integer.parseInt(STT_HienTai) - 1;
                    int next = Integer.parseInt(STT_HienTai) + 1;
                    if (next > SoLuongKH) {
                        lay_toi.setEnabled(false);
                        Toi.setEnabled(false);
                    } else {
                        lay_toi.setEnabled(true);
                        Toi.setEnabled(true);
                    }
                    if (pre <= 0) {
                        lay_lui.setEnabled(false);
                        Lui.setEnabled(false);
                    } else {
                        lay_lui.setEnabled(true);
                        Lui.setEnabled(true);
                    }

                } else {
                    Log.e("BienSTTHIenTai", "chay vao 4");

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
                    alertDialog.show();
                }
            }
        });


    }

    private void setDataForView(String tt, String maduong) {
        //Lấy khách hàng có stt hiện tại...mặc đình là 1
        tenduong =  duongDAO.getTenDuongTheoMa(maduong).trim();
        DuongDangGhi.setText(tenduong);
        soKHconlai = String.valueOf(khachhangDAO.countKhachHangChuaGhiTheoDuong(maduong)) ;
        tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong)) ;
        ConLai.setText(getString(R.string.string_con)+" "+soKHconlai+"/"+ tongsoKHTheoDuong);

        khachhang =  khachhangDAO.getKHTheoSTT_Duong(tt,maduong);
        STT.setText(khachhang.getSTT().trim());
        MaKH.setText(khachhang.getMaKhachHang().trim());
        HoTen.setText(khachhang.getTenKhachHang().trim());

        DiaChi.setText(khachhang.getDiaChi().trim());
        MaTLK.setText(khachhang.getMasotlk().trim());
        HieuTLK.setText(khachhang.getHieutlk().trim());
        CoTLK.setText(khachhang.getCotlk().trim());
        ChiSo1.setText(khachhang.getChiSo1().trim());
        ChiSo2.setText(khachhang.getChiSo2().trim());
        ChiSo3.setText(khachhang.getChiSo3().trim());
        m31.setText(khachhang.getSLTieuThu1().trim());
        m32.setText(khachhang.getSLTieuThu2().trim());
        m33.setText(khachhang.getSLTieuThu3().trim());
        GhiChu.setText(khachhang.getGhiChu().trim());
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
        HoTen.setSelected(true);
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
        DuongDangGhi= (TextView) findViewById(R.id.tv_duongdangghi);
        ConLai= (TextView) findViewById(R.id.tv_conlai);
        DuongDangGhi.setSelected(true);
        DienThoai = (EditText) findViewById(R.id.edit_DienThoaiKH);
        ChiSoMoi = (EditText) findViewById(R.id.edit_chisomoi);
        TinhTrangTLK = (EditText) findViewById(R.id.edit_chisomoicon);
        GhiChu = (EditText) findViewById(R.id.edit_ghichu);

        DoiSDT  = (ImageButton) findViewById(R.id.imgbtn_doi);
        Ghi = (ImageButton) findViewById(R.id.btn_ghinuoc);
        Toi = (ImageButton) findViewById(R.id.btn_toi);
        Lui = (ImageButton) findViewById(R.id.btn_lui);


        lay_toi =(LinearLayout)findViewById(R.id.layout_toi);
        lay_lui =(LinearLayout)findViewById(R.id.layout_lui);
        lay_ghi =(LinearLayout)findViewById(R.id.layout_ghi);

        chisocu_con_lb =(TableRow) findViewById(R.id.tableRow_chisocucon_lb);
        chisocu_con =(TableRow) findViewById(R.id.tableRow_chisocucon);

        chisomoi_con_lb =(TableRow) findViewById(R.id.tableRow_chisomoicon_lb);
        chisomoi_con =(TableRow) findViewById(R.id.tableRow_chisomoicon);

    }

    private boolean KiemTraDieuKienGhiNuoc(){

        return true;
    }
    /*
    //-----------------------------------------------------------------------------
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

                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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
 //-------------------------------------------------------------------------------------
*/

//hàm ghi nước
    private void ghinuoc(){

    }
    private boolean kiemTraDieuKienDeGhiNuoc(){
        return true;
    }

}

