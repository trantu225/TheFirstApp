package tiwaco.thefirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.NhanVienDAO;
import tiwaco.thefirstapp.DAO.TinhTrangTLKDAO;
import tiwaco.thefirstapp.DTO.JSONUser;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.DTO.User;
import tiwaco.thefirstapp.Database.SPData;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    Button btn_dangnhap,btn_thoat,btn_test;
    EditText edt_ten;
    EditText edt_pass;
    NhanVienDTO nhanviendto ;
    NhanVienDAO nhanviendao;
    Context con ;
    LichSuDAO lichsudao;
    KhachHangDAO khdao;
    KhachHangThuDAO khthudao;
    SPData spdata;
    String UserJson = "";
    boolean ketquakiemtra = false;
    String duongdanfile ="";
    ViewDialog_login dialoglogin ;
    String dienthoai = "", idnhanvien = "", dienthoaihuyen = "";
    String hoten = "";
    String idhuyen = "";



    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        con = LoginActivity.this;
        btn_dangnhap = (Button) this.findViewById(R.id.sign_in_button) ;
        btn_thoat = (Button) this.findViewById(R.id.close_button) ;
        btn_test = (Button) this.findViewById(R.id.test_api_button) ;
        edt_ten =(EditText) this.findViewById(R.id.idnhanvien) ;
        edt_pass =(EditText) this.findViewById(R.id.password) ;
        duongdanfile= getString(R.string.API_CheckLogin);




        spdata= new SPData(con);
        nhanviendto = new NhanVienDTO();
        nhanviendao = new NhanVienDAO();
        lichsudao = new LichSuDAO(con);
        khdao = new KhachHangDAO(con);
        khthudao = new KhachHangThuDAO(con);
        dialoglogin = new ViewDialog_login(LoginActivity.this);

        if(!spdata.getDataNhanVienTrongSP().equals("")){
            edt_ten.setText(spdata.getDataNhanVienTrongSP().trim());
            if (khdao.countKhachHangAll() > 0 || khthudao.countKhachHangAll() > 0) {

//                    }
                finish();
                Intent myIntent=new Intent(LoginActivity.this, StartActivity.class);
                startActivity(myIntent);

            }
            else {
//                    Intent myIntent = new Intent(this, LoadActivity.class);
//                    //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
//                    startActivity(myIntent);
                ViewDialog alert = new ViewDialog();
                alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
            }
        }
        //      Bien.listNV = nhanviendao.TaoDSNhanVien();



        btn_test.setVisibility(View.GONE);
        btn_dangnhap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

             //   dangnhap();

                try {
                    if (isInternetOn()) {
                        dangnhap();
                    }
                    else{
                     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                            // button "no" ẩn dialog đi
                        }
                    });
                    alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
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
                catch(Exception e)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                // khởi tạo dialog
               alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
               // thiết lập nội dung cho dialog

            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    // button "no" ẩn dialog đi
                }
            });
            alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
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
        });
        btn_thoat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });
        btn_test.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               //new AddUser().execute("http://192.168.1.91/Service1.svc/AddUser");
                //new UpdateUser().execute("http://192.168.1.91/Service1.svc/UpdatePassUser");
              //  new GetUserList().execute("http://192.168.1.91/Service1.svc/GetListUser");
            }
        });
        ActionBar bar = this.getSupportActionBar();
        bar.hide();
        hienthicanhbaomang();


    }

    public void hienthicanhbaomang()
    {
        try {
            if (isInternetOn()) {
                Log.e("check wifi", "yes");


            } else {
                Log.e("check wifi", "no");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        // button "no" ẩn dialog đi
                    }
                });
                alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
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

        } catch(Exception e)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
            // khởi tạo dialog
            alertDialogBuilder.setMessage("Chưa kết nối internet. Hãy kiểm tra lại wifi hoặc 3G/4G");
            // thiết lập nội dung cho dialog

            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    // button "no" ẩn dialog đi
                }
            });
            alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
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


    public void dangnhap(){
        String ten = edt_ten.getText().toString();
        String pass = edt_pass.getText().toString();
        duongdanfile = duongdanfile +"/"+ten +"/" +pass;
        if(ten =="" && pass ==""){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
            // khởi tạo dialog
            alertDialogBuilder.setMessage(R.string.error_field_required);
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
        else{
            new CheckUser().execute(duongdanfile);//
/*
             boolean kt = nhanviendao.kiemtraDangNhap(edt_ten.getText().toString(),edt_pass.getText().toString(),Bien.listNV,LoginActivity.this);

            if(kt){
                //Bien.nhanvien = edt_ten.getText().toString().trim();
                //spdata.luuDataKyHoaDonTrongSP("092017");
                spdata.luuDataNhanVienTrongSP(edt_ten.getText().toString().trim() );
                LichSuDTO ls = new LichSuDTO();
                ls.setNoiDungLS(edt_ten.getText().toString().trim() +" đăng nhập.");
                ls.setMaLenh("DN");
                String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                ls.setThoiGianLS(thoigian1);
                lichsudao.addTable_History(ls);
                KhachHangDAO khdao = new KhachHangDAO(LoginActivity.this);
                if(khdao.countKhachHangAll() >0){
//                    TinhTrangTLKDAO tinhtrangtlkdao = new TinhTrangTLKDAO(con);
//                    List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//                    for(int tt = 0 ; tt<listt.size();tt++){
//                        tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//                    }

                    Intent myIntent=new Intent(this, StartActivity.class);
                    startActivity(myIntent);
                    this.finish();
                }
                else {
//                    Intent myIntent = new Intent(this, LoadActivity.class);
//                    //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
//                    startActivity(myIntent);
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
                }

            }
            else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_incorrect_password);
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





*/





        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        SPData spdata1 = new SPData(LoginActivity.this);
        if(!spdata1.getDataNhanVienTrongSP().equals("")){
            KhachHangDAO khdao = new KhachHangDAO(LoginActivity.this);
            if(khdao.countKhachHangAll() >0){
                Intent myIntent=new Intent(this, StartActivity.class);
                startActivity(myIntent);
            }
            else {
                Intent myIntent = new Intent(this, LoadActivity.class);
                startActivity(myIntent);
            }
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


    public class AddUser extends AsyncTask<String, Void, String>{

        String status ="";
        String json = "";
        String strresult ="";

        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn=null;
            BufferedReader reader;

            try{
                final URL url=new URL(connUrl[0]);
                conn=(HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");
                User u  = new User();

                u.setUserName("Nguyen Van Long");
                u.setPassword("123456789");
                JSONUser juser = new JSONUser();
                juser.setUser(u);
                Gson gson = new Gson();
                json = gson.toJson(juser);
                Log.e("json update",json);

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(json.getBytes());
                out.flush();
                out.close();

                int result = conn.getResponseCode();
                if(result==200){

                    InputStream in=new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb=new StringBuilder();
                    String line = null;

                    while((line=reader.readLine())!=null){
                        status=line;
                    }
                }

            }catch(Exception ex){
                ex.printStackTrace();
                Log.e("loi ne ",ex.toString());
                strresult = "0";
            }


            Log.e("status",status);
            try {
                JSONObject objstt = new JSONObject(status);
                if (objstt.has("AddUserResult")){
                    strresult = String.valueOf(objstt.getInt("AddUserResult"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                strresult = "0";

            }
            Log.e("result",strresult);
            return strresult.toString();
        }


        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.e("result update", result);
            if(result.equals("1")){
                Toast.makeText(LoginActivity.this,"User Saved Successfuly.",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(LoginActivity.this,"Doesn't Saved Data.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public final boolean isInternetOn() {

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            if (netInfo != null && netInfo.isConnected()) {
//                Runtime runtime = Runtime.getRuntime();
//                try {
//                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//                    int     exitValue = ipProcess.waitFor();
//                    return (exitValue == 0);
//                }
//                catch (IOException e)          { e.printStackTrace(); }
//                catch (InterruptedException e) { e.printStackTrace(); }
//
//                return false;
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public class CheckUser extends AsyncTask<String, Void, String>
    {

        String fileContent = "";
        @Override
        protected String doInBackground(String... connUrl) {
            if(!isCancelled()) {


                try {

                    final URL url = new URL(connUrl[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                    conn.setRequestMethod("GET");
                    int result = conn.getResponseCode();
                    if (result == 200) {

                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder sb = new StringBuilder();
                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            // long status = (i+1) *100/line.length();
                            //     String.valueOf(status)

                            fileContent = line;

                        }
                        reader.close();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("loi load du lieu", ex.toString());
                }
                if (fileContent.equals("")) {
                    Log.e("filetuserver", "Rong");
                } else {
                    if (fileContent.equals("1")) {
                        Log.e("Lay thong tin nhan vien", "OK");
                        String resultnv = "";
                        try {
                            String duongdankhoaso = getString(R.string.API_GetNhanVien) + "/" + edt_ten.getText().toString().trim() + "/" + edt_pass.getText().toString().trim();
                            final URL url = new URL(duongdankhoaso);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                            conn.setRequestMethod("GET");

                            int result = conn.getResponseCode();

                            if (result == 200) {

                                InputStream in = new BufferedInputStream(conn.getInputStream());
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder sb = new StringBuilder();
                                String line = null;
                                int i = 0;
                                while ((line = reader.readLine()) != null) {
                                    // long status = (i+1) *100/line.length();
                                    //     String.valueOf(status)

                                    resultnv = line;

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e("loi load du lieu", ex.toString());
                        }
                        try {
                            JSONObject objTiwaread = new JSONObject(resultnv);


                            if (objTiwaread.has("dienthoai")) {
                                dienthoai = objTiwaread.getString("dienthoai").trim();
                                Log.e("dienthoai", dienthoai);

                            }
                            if (objTiwaread.has("idnhanvien")) {
                                idnhanvien = objTiwaread.getString("idnhanvien").trim();
                                Log.e("idnhanvien", dienthoai);

                            }

                            if (objTiwaread.has("hoten")) {
                                hoten = objTiwaread.getString("hoten").trim();
                                Log.e("hoten", hoten);
                            }

                            if (objTiwaread.has("idhuyen")) {
                                idhuyen = objTiwaread.getString("idhuyen").trim();
                                Log.e("idhuyen", idhuyen);
                            }
                            if (objTiwaread.has("dienthoaihuyen")) {
                                dienthoaihuyen = objTiwaread.getString("dienthoaihuyen").trim();
                                Log.e("dienthoaihuyen", idhuyen);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }


            }
            return fileContent;

        }


        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.e("result update", result);
            dialoglogin.closeDialog();
            if(result.equals("1")){
                ketquakiemtra = true;
                //Bien.nhanvien = edt_ten.getText().toString().trim();
                //spdata.luuDataKyHoaDonTrongSP("092017");
                spdata.luuDataNhanVienTrongSP(edt_ten.getText().toString().trim() );
                spdata.luuDataMatKhauNhanVienTrongSP(edt_pass.getText().toString().trim());
                spdata.luuThongTinNhanVien(idnhanvien, hoten, dienthoai, idhuyen, dienthoaihuyen);
                Log.e("tennv,dienthoai", spdata.getDataTenNhanVien() + " + " + spdata.getDataDienThoai());
                LichSuDTO ls = new LichSuDTO();
                ls.setNoiDungLS(edt_ten.getText().toString().trim() +" đăng nhập.");
                ls.setMaLenh("DN");
                String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                ls.setThoiGianLS(thoigian1);
                lichsudao.addTable_History(ls);

                Log.e("nhanvien",spdata.getDataNhanVienTrongSP());
                if (spdata.getDataTuDongLuuTapTin() == 0) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
                } else {
                    if (khdao.countKhachHangAll() > 0 || khthudao.countKhachHangAll() > 0) {
//                    TinhTrangTLKDAO tinhtrangtlkdao = new TinhTrangTLKDAO(con);
//                    List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//                    for(int tt = 0 ; tt<listt.size();tt++){
//                        tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//                    }
                        finish();
                        Intent myIntent = new Intent(LoginActivity.this, StartActivity.class);
                        startActivity(myIntent);

                    } else {
//                    Intent myIntent = new Intent(this, LoadActivity.class);
//                    //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
//                    startActivity(myIntent);
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
                    }
                }

            }else if(result.equals("0")){
                ketquakiemtra = false;
                this.cancel(true);
                Log.e("nhanvien",spdata.getDataNhanVienTrongSP());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_incorrect_password);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            }
            else{
                this.cancel(true);
                Log.e("nhanvien", spdata.getDataNhanVienTrongSP());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load_server);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
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


    public class CheckUser2 extends AsyncTask<String, Void, String> {

        String fileContent = "";

        @Override
        protected String doInBackground(String... connUrl) {


            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");
                int result = conn.getResponseCode();
                if (result == 200) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        // long status = (i+1) *100/line.length();
                        //     String.valueOf(status)

                        fileContent = line;

                    }
                    reader.close();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("loi load du lieu", ex.toString());
            }
            if (fileContent.equals("")) {
                Log.e("filetuserver", "Rong");
            } else {
                if (fileContent.equals("1")) {
                    Log.e("Lay thong tin nhan vien", "OK");
                    String resultnv = "";
                    try {
                        String duongdankhoaso = getString(R.string.API_GetNhanVien) + "/" + edt_ten.getText().toString().trim() + "/" + edt_pass.getText().toString().trim();
                        final URL url = new URL(duongdankhoaso);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
                        conn.setRequestMethod("GET");

                        int result = conn.getResponseCode();

                        if (result == 200) {

                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                // long status = (i+1) *100/line.length();
                                //     String.valueOf(status)

                                resultnv = line;

                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.e("loi load du lieu", ex.toString());
                    }
                    try {
                        JSONObject objTiwaread = new JSONObject(resultnv);


                        if (objTiwaread.has("dienthoai")) {
                            dienthoai = objTiwaread.getString("dienthoai").trim();
                            Log.e("dienthoai", dienthoai);

                        }
                        if (objTiwaread.has("idnhanvien")) {
                            idnhanvien = objTiwaread.getString("idnhanvien").trim();
                            Log.e("idnhanvien", dienthoai);

                        }

                        if (objTiwaread.has("hoten")) {
                            hoten = objTiwaread.getString("hoten").trim();
                            Log.e("hoten", hoten);
                        }

                        if (objTiwaread.has("idhuyen")) {
                            idhuyen = objTiwaread.getString("idhuyen").trim();
                            Log.e("idhuyen", idhuyen);
                        }
                        if (objTiwaread.has("dienthoaihuyen")) {
                            dienthoaihuyen = objTiwaread.getString("dienthoaihuyen").trim();
                            Log.e("dienthoaihuyen", idhuyen);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


            return fileContent;

        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result update", result);
            dialoglogin.closeDialog();
            if (result.equals("1")) {

                //Bien.nhanvien = edt_ten.getText().toString().trim();
                //spdata.luuDataKyHoaDonTrongSP("092017");
                spdata.luuDataNhanVienTrongSP(edt_ten.getText().toString().trim());
                spdata.luuDataMatKhauNhanVienTrongSP(edt_pass.getText().toString().trim());
                spdata.luuThongTinNhanVien(idnhanvien, hoten, dienthoai, idhuyen, dienthoaihuyen);
                Log.e("tennv,dienthoai", spdata.getDataTenNhanVien() + " + " + spdata.getDataDienThoai());
                LichSuDTO ls = new LichSuDTO();
                ls.setNoiDungLS(edt_ten.getText().toString().trim() + " đăng nhập.");
                ls.setMaLenh("DN");
                String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                ls.setThoiGianLS(thoigian1);
                lichsudao.addTable_History(ls);

                Log.e("nhanvien", spdata.getDataNhanVienTrongSP());
                if (spdata.getDataTuDongLuuTapTin() == 0) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
                } else {
                    if (khdao.countKhachHangAll() > 0 || khthudao.countKhachHangAll() > 0) {
//                    TinhTrangTLKDAO tinhtrangtlkdao = new TinhTrangTLKDAO(con);
//                    List<TinhTrangTLKDTO> listt = tinhtrangtlkdao.TaoDSTinhTrang();
//                    for(int tt = 0 ; tt<listt.size();tt++){
//                        tinhtrangtlkdao.addTable_TinhTrangTLK(listt.get(tt));
//                    }
                        finish();
                        Intent myIntent = new Intent(LoginActivity.this, StartActivity.class);
                        startActivity(myIntent);

                    } else {
//                    Intent myIntent = new Intent(this, LoadActivity.class);
//                    //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
//                    startActivity(myIntent);
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(LoginActivity.this, "Chọn nguồn để load dữ liệu: ");
                    }
                }

            } else if (result.equals("0")) {


                Log.e("nhanvien", spdata.getDataNhanVienTrongSP());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_incorrect_password);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            } else {

                Log.e("nhanvien",spdata.getDataNhanVienTrongSP());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.error_load_server);
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= 11) {
                            recreate();
                        } else {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);

                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
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


}

