package tiwaco.thefirstapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
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
import java.io.FileOutputStream;
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
import tiwaco.thefirstapp.File.XuLyFile;

public class Backup_Activity extends AppCompatActivity {
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    RadioButton radioTatca, radioDaghi, radioChuaghi;
    RadioGroup group;
    EditText tenfile;
    Button luu, trove;

    DuongDAO duongdao;
    KhachHangDAO khachangdao;
    Context con;

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

    public void backupfile(View view) {
        Log.e("ghi file","vao trong request");
        String result_tatca_string = taoJSONData_KH_TatCa(tenfile.getText().toString());
        XuLyFile xl  = new XuLyFile(con);
        String path = xl.getBoNhoTrong();
        String path1 = getSdCardPath();
        Log.e("path",path);
        Log.e("path1",path1);
        String filename = path +"/"+ tenfile.getText().toString();
        String filename1 = path1+"/"+ tenfile.getText().toString();
        writeFile(filename,result_tatca_string);
        writeFile(filename1,result_tatca_string);
       // askPermissionAndWriteFile(filename,result_tatca_string);

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
            listtiwaread.add(tiwaread);
        }
        jsondata.setListTiwaread(listtiwaread);
        jsondata.setTenDS(tendanhsach);
        jsondata.setTongSLkh(soluongKH);

        Gson gson = new Gson();
        String json = gson.toJson(jsondata);
        return json;
    }

    private ListJsonData taoJSONData_KH_DaGhi() {
        ListJsonData jsondata = new ListJsonData();
        return jsondata;
    }

    private ListJsonData taoJSONData_KH_ChuaGhi() {
        ListJsonData jsondata = new ListJsonData();
        return jsondata;
    }

    private String ObjectToJson(ListJsonData jsondata) {

        Gson gson = new Gson();
        String json = gson.toJson(jsondata);
        return json;
    }

    private void askPermissionAndWriteFile(String path, String data) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
            Log.e("ghi file","vao trong ghi");
            this.writeFile(path, data);
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

    private void writeFile(String path, String data) {

        try {
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
