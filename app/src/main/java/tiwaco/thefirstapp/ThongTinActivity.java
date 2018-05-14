package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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
import java.util.Calendar;
import java.util.List;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.JSONUser;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.User;
import tiwaco.thefirstapp.Database.SPData;

public class ThongTinActivity extends AppCompatActivity {

    Context con;
    TextView txt_tongsokh , txt_sokhdaghi, txt_dokhghihomnay, txt_som3daghi;
    EditText matkhaucu, matkhaumoi;
    Button doimatkhau, truyvanluocsu, thoat;
    SPData spdata;
    KhachHangDAO khachhangdao ;
    String urldoimatkhau = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        con = ThongTinActivity.this;
        spdata = new SPData(con);
        khachhangdao = new KhachHangDAO(con);
        getSupportActionBar().setTitle("Thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        urldoimatkhau  =   getString(R.string.API_DoiMatKhau);
        taoview();
        hienThiView();
        truyvanluocsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ThongTinActivity.this, HistoryActivity.class);
                startActivity(in);
            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ThongTinActivity.this, StartActivity.class);
                startActivity(in);
            }
        });
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu =  spdata.getDataMatKhauNhanVienTrongSP();
                if(mkcu.equals(matkhaucu.getText().toString().trim()))
                {
                    if(matkhaumoi.getText().toString().trim().equals(""))
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
                        // khởi tạo dialog
                        alertDialogBuilder.setMessage("Chưa điền mật khẩu mới");
                        // thiết lập nội dung cho dialog

                        alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
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
                    }
                    else{
                        new UpdatePassword().execute(urldoimatkhau);
                    }
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Mật khẩu cũ không trùng khớp");
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
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
                }

            }
        });


    }
    public void taoview(){
        txt_tongsokh = (TextView) findViewById(R.id.tv_tongsoKH);
        txt_sokhdaghi = (TextView) findViewById(R.id.tv_sokhdaghi);
        txt_dokhghihomnay = (TextView) findViewById(R.id.tv_sokhghihomnay);
        txt_som3daghi = (TextView) findViewById(R.id.tv_som3daghi);
        matkhaucu = (EditText) findViewById(R.id.password_cu);
        matkhaumoi = (EditText) findViewById(R.id.password_moi);
        doimatkhau = (Button) findViewById(R.id.doimatkhau_button);
        truyvanluocsu = (Button) findViewById(R.id.luocsu_button);
        thoat = (Button) findViewById(R.id.close_button);


    }

    public void hienThiView(){
        int TongSoKH = khachhangdao.countKhachHangAll();
        int SoKHDaGhi  = khachhangdao.countKhachHangDaGhi();
        String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        int SoKHDaghiHomNay  = khachhangdao.countKhachHangDaGhiHomNay(thoigian1);
        List<KhachHangDTO> listkh  = khachhangdao.getAllKHDaGhi();
        int som3daghi  = 0;
        for(int  i  =0 ; i<listkh.size();i++)
        {
            som3daghi += Integer.parseInt(listkh.get(i).getSLTieuThu());
        }

        txt_tongsokh.setText(String.valueOf(TongSoKH));
        txt_sokhdaghi.setText(String.valueOf(SoKHDaGhi));
        txt_dokhghihomnay.setText(String.valueOf(SoKHDaghiHomNay));
        txt_som3daghi.setText(String.valueOf(som3daghi));
    }
    public class UpdatePassword extends AsyncTask<String, Void, String> {

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
                conn.setRequestMethod("PUT");
                User u  = new User();
                u.setUserName(spdata.getDataNhanVienTrongSP());
                u.setPassword(matkhaumoi.getText().toString().trim());
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
                if (objstt.has("UpdatePassUserResult")){
                    strresult = String.valueOf(objstt.getInt("UpdatePassUserResult"));
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
                spdata.luuDataMatKhauNhanVienTrongSP(matkhaumoi.getText().toString().trim());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đổi mật khẩu thành công. Hãy đăng nhập lại!");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent in = new Intent(con,LoginActivity.class);
                        startActivity(in);

                        // button "no" ẩn dialog đi
                    }
                });



                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đổi mật khẩu thất bại!");
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
            }
        }
    }
}
