package tiwaco.thefirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.NhanVienDAO;
import tiwaco.thefirstapp.DTO.JSONUser;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;
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
    SPData spdata;
    String UserJson = "";

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





        spdata= new SPData(con);
        nhanviendto = new NhanVienDTO();
        nhanviendao = new NhanVienDAO();
        lichsudao = new LichSuDAO(con);
        Bien.listNV = nhanviendao.TaoDSNhanVien();

        btn_dangnhap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
        btn_thoat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }


    public void dangnhap(){
        String ten = edt_ten.getText().toString();
        String pass = edt_pass.getText().toString();
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
                    Intent myIntent=new Intent(this, StartActivity.class);
                    startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(this, LoadActivity.class);
                    startActivity(myIntent);
                }
                this.finish();
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
                u.setUserID(Integer.parseInt(edt_ten.getText().toString()));
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

    public class UpdateUser extends AsyncTask<String, Void, String>
    {
        String status ="";
        String json = "";
        String result ="";
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
                conn.setRequestMethod("PUT");
                User u  = new User();
                u.setUserID(9);
                u.setUserName("Van Long");
                u.setPassword("123456789");
                Gson gson = new Gson();
                json = gson.toJson(u);
                Log.e("json update",json);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("jsonuser",json);  //object name is case senstive. it must be same as service parameter.

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
                result="0";
            }
            try {
                JSONObject objstt = new JSONObject(status);
                if (objstt.has("UpdatePassUserResult")){

                    result = String.valueOf(objstt.getInt("UpdatePassUserResult"));



                }
            } catch (JSONException e) {
                e.printStackTrace();
                result="0";
            }
            return result;
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


}

