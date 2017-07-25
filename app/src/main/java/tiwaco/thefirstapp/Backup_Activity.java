package tiwaco.thefirstapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import circleprogress.DonutProgress;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ListJsonData;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
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
    private static final String THUMUCCHUAGHI = "CHUAGHI";
    private static final String THUMUCLAST = "LAST";
    private static final String TENFILETATCA_LAST = "customers_last.txt";
    private static final String TENFILEDAGHI_LAST = "customers_daghi_last.txt";
    private static final String TENFILEDAGHIHOMNAY_LAST = "customers_daghitheongay_last.txt";
    private static final String TENFILECHUAGHI_LAST = "customers_chuaghi_last.txt";
    RadioButton radioTatca, radioDaghi, radioChuaghi,radioDaghihomnay,radioDaghiTheoDuong;
    Spinner spinDuong;
    RadioGroup group;
    DatePicker datepicker;
    TextView mstatus,txttitle;
    EditText tenfile,txtngay;
    Button luu, trove,chonngay;
    DuongDAO duongdao;
    KhachHangDAO khachangdao;
    Context con;
    SPData spdata;
    List<DuongDTO> listduong;
    ArrayList<String> arrDuong;
    LichSuDAO lichsudao;
    DonutProgress prgTime;
    String tenfileluu = "";
    TableLayout table;
    String thumucchuafile="";
    Date dateFinish;
    String maduong="";
    Calendar cal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        getSupportActionBar().hide();
        con = Backup_Activity.this;
        duongdao = new DuongDAO(con);
        khachangdao = new KhachHangDAO(con);
        lichsudao = new LichSuDAO(con);
        cal = Calendar.getInstance();
        getView();
        loadDataDuong();
        askPermissionAndReadFile();
        askPermissionAndWriteFile();

    }

    private void getView() {
        group = (RadioGroup) findViewById(R.id.group);
        tenfile = (EditText) findViewById(R.id.edit_tenfile);
        txttitle = (TextView) findViewById(R.id.txt_title);
        txtngay = (EditText) findViewById(R.id.txt_ngay);
        luu = (Button) findViewById(R.id.btn_Luu);
        trove = (Button) findViewById(R.id.btn_trove);
        chonngay = (Button) findViewById(R.id.btn_chongay);
        radioTatca = (RadioButton) findViewById(R.id.radio_tatca);
        radioDaghi = (RadioButton) findViewById(R.id.radio_daghi);
        radioChuaghi = (RadioButton) findViewById(R.id.radio_chuaghi);
        radioDaghihomnay = (RadioButton) findViewById(R.id.radio_daghihomnay);
        radioDaghiTheoDuong = (RadioButton) findViewById(R.id.radio_daghitheoduong);
        prgTime = (DonutProgress) findViewById(R.id.prgTime);
        spinDuong = (Spinner) findViewById(R.id.spinduong);
        table = (TableLayout) findViewById(R.id.TableLayout1);
        mstatus = (TextView) findViewById(R.id.tv_status);
        chonngay.setVisibility(View.GONE);
        mstatus.setVisibility(View.GONE);
        prgTime.setProgress(0);
        prgTime.setText("0 %");
        spdata = new SPData(con);
        if (radioTatca.isChecked() == false && radioDaghi.isChecked() == false && radioChuaghi.isChecked() == false && radioDaghihomnay.isChecked()==false && radioDaghiTheoDuong.isChecked()==false) {
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

        }
        else if(checkedRadioId == R.id.radio_daghihomnay){

                spinDuong.setVisibility(View.GONE);
                txttitle.setVisibility(View.VISIBLE);
                txttitle.setText("Ngày: ");
                txtngay.setVisibility(View.VISIBLE);
            chonngay.setVisibility(View.VISIBLE);
            tenfileluu = taoTenFile("DaGhiTheoNgay");

            showDatePickerDialog();

        }
        else if(checkedRadioId == R.id.radio_daghitheoduong){
            spinDuong.setVisibility(View.VISIBLE);
            txttitle.setVisibility(View.VISIBLE);
            chonngay.setVisibility(View.GONE);
            txttitle.setText("Đường: ");
            txtngay.setVisibility(View.GONE);
            tenfileluu = taoTenFile("DaGhiTheoDuong_"+maduong);
        }
        tenfile.setText(tenfileluu);
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
        String thoigian1 = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        txtngay.setText(thoigian1);
        String s=txtngay.getText()+"";
        String strArrtmp[]=s.split("-");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                Backup_Activity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày ghi nước");
        pic.show();
    }

    public void trove(View view) {

        finish();

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
            if(Bien.bienbkall  == Bien.bienghi) {
                taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
            }
            else {
                mstatus.setVisibility(View.VISIBLE);
                table.setVisibility(View.GONE);
                MyBackUpTask task = new MyBackUpTask();
                task.execute();
            }
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
            jsondata.setTenDS(tendanhsach);
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
            jsondata.setTenDS(tendanhsach);
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
            jsondata.setTenDS(tendanhsach);
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
            jsondata.setTenDS(tendanhsach);
            jsondata.setTongSLkh(soluongKH);

            Gson gson = new Gson();
            json = gson.toJson(jsondata);
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
            jsondata.setTenDS(tendanhsach);
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
                if(Bien.bienbkall  == Bien.bienghi) {
                 //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                }

                else {
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
                }
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
                    if (Bien.bienbkall == Bien.bienghi) {
                        //   taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                    } else {
                        Log.e("back up", "Vao tat ca");


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
                    }
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


}
