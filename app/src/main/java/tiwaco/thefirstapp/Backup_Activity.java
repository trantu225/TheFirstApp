package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
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

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
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
    private static final String THUMUCCHUAGHI = "CHUAGHI";
    private static final String THUMUCLAST = "LAST";
    private static final String TENFILETATCA_LAST = "customers_last.txt";
    private static final String TENFILEDAGHI_LAST = "customers_daghi_last.txt";
    private static final String TENFILECHUAGHI_LAST = "customers_chuaghi_last.txt";
    RadioButton radioTatca, radioDaghi, radioChuaghi;
    RadioGroup group;
    EditText tenfile;
    Button luu, trove;
    DuongDAO duongdao;
    KhachHangDAO khachangdao;
    Context con;
    SPData spdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        getSupportActionBar().hide();
        con = Backup_Activity.this;
        duongdao = new DuongDAO(con);
        khachangdao = new KhachHangDAO(con);


        getView();


    }

    private void getView() {
        group = (RadioGroup) findViewById(R.id.group);
        tenfile = (EditText) findViewById(R.id.edit_tenfile);
        luu = (Button) findViewById(R.id.btn_Luu);
        trove = (Button) findViewById(R.id.btn_trove);
        radioTatca = (RadioButton) findViewById(R.id.radio_tatca);
        radioDaghi = (RadioButton) findViewById(R.id.radio_daghi);
        radioChuaghi = (RadioButton) findViewById(R.id.radio_chuaghi);
        if (radioTatca.isChecked() == false && radioDaghi.isChecked() == false && radioChuaghi.isChecked() == false) {
            radioTatca.setChecked(true);
            tenfile.setText(taoTenFile("customers"));
        }

        this.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupradio, int checkedId) {
                xuLyChon(groupradio, checkedId);
            }
        });


    }

    private void xuLyChon(RadioGroup groupradio, int checkedId) {
        int checkedRadioId = groupradio.getCheckedRadioButtonId();
        String tenfileluu = "";
        if (checkedRadioId == R.id.radio_tatca) {
            tenfileluu = taoTenFile("customers");

        } else if (checkedRadioId == R.id.radio_daghi) {
            tenfileluu = taoTenFile("DaGhi");

        } else if (checkedRadioId == R.id.radio_chuaghi) {
            tenfileluu = taoTenFile("ChuaGhi");

        }
        tenfile.setText(tenfileluu);
    }

    public void trove(View view) {

        finish();

    }
    //Backup --------------------------------------------------------------------------
    public void backupfile(View view) {
        Log.e("ghi file","vao trong request");
        spdata = new SPData(con);
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
        }
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
            // hiển thị dialog
        }

        return json;
    }

    private String taoJSONData_KH_DaGhi(String tendanhsach) {
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
            // hiển thị dialog
        }
        return json;
    }

    private String taoJSONData_KH_ChuaGhi(String tendanhsach) {
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
        }
        return json;
    }

    private String ObjectToJson(ListJsonData jsondata) {

        Gson gson = new Gson();
        String json = gson.toJson(jsondata);
        return json;
    }

    private void askPermissionAndWriteFile(String path, String tenfile,String data) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
            Log.e("ghi file","vao trong ghi");
            this.writeFile(path, tenfile,data);
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
                        Log.e("ghi file","vao trong request");
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




}
