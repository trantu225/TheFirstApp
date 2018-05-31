package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.User;
import tiwaco.thefirstapp.Database.SPData;

public class ThongTinActivity extends AppCompatActivity {

    Context con;
    TextView txt_tongsokh , txt_sokhdaghi, txt_dokhghihomnay, txt_som3daghi,txt_phienban,txt_kyhd;
    EditText matkhaucu, matkhaumoi;
    Button doimatkhau, truyvanluocsu, thoat,capnhat;
    SPData spdata;
    KhachHangDAO khachhangdao ;
    String urldoimatkhau = "";
    String urlcapnhat  ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        con = ThongTinActivity.this;
        spdata = new SPData(con);
        khachhangdao = new KhachHangDAO(con);
        getSupportActionBar().setTitle("Thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        urldoimatkhau  =   getString(R.string.API_DoiMatKhau);
        urlcapnhat = getString(R.string.API_CapNhat)+"/"+String.valueOf(getVersionCode());
        Log.e("urlcapnhat",urlcapnhat);
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
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isInternetOn()) {
                        new CheckUpdate().execute(urlcapnhat);
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
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
                catch(Exception e){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
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

    public final boolean isInternetOn() {

        boolean k =false;
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ) {

            // if connected with internet

            // Toast.makeText(this, connec.getActiveNetworkInfo().getTypeName(), Toast.LENGTH_LONG).show();
            k= true;

        } else if (
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            //Toast.makeText(this, " Chưa có internet hoặc 3G/4G ", Toast.LENGTH_LONG).show();
            k = false;
        }

        return k ;
    }
    public void taoview(){
        txt_tongsokh = (TextView) findViewById(R.id.tv_tongsoKH);
        txt_sokhdaghi = (TextView) findViewById(R.id.tv_sokhdaghi);
        txt_dokhghihomnay = (TextView) findViewById(R.id.tv_sokhghihomnay);
        txt_kyhd = (TextView) findViewById(R.id.tv_kyhd);
        txt_phienban = (TextView) findViewById(R.id.tv_phienban);
        txt_som3daghi = (TextView) findViewById(R.id.tv_som3daghi);
        matkhaucu = (EditText) findViewById(R.id.password_cu);
        matkhaumoi = (EditText) findViewById(R.id.password_moi);
        doimatkhau = (Button) findViewById(R.id.doimatkhau_button);
        truyvanluocsu = (Button) findViewById(R.id.luocsu_button);
        capnhat  = (Button) findViewById(R.id.kiemtracapnhat_button);
        thoat = (Button) findViewById(R.id.close_button);


    }

    //Thay đổi version trong build.gradle khi build apk
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;

    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;

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
        txt_phienban.setText(String.valueOf(getVersionName()));

        String kyhd = spdata.getDataKyHoaDonTrongSP();
        Log.e("KYHD",kyhd);
        if(!kyhd.equals("")) {
            String nam = kyhd.substring(0, 4);
            String thang = kyhd.substring(4);
            String strkyhd = thang + "/" + nam;
            txt_kyhd .setText(strkyhd);
        }

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

    public class CheckUpdate extends AsyncTask<String, Void, String> {

        String fileContent = "";

        @Override
        protected String doInBackground(String... connUrl) {
            if (!isCancelled()) {


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
                    Log.e("filetuserver", fileContent);
                }


            }
            return fileContent;

        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result update", result);

            if (result.equals("1")) { //phiên bản mới nhất
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.note_phienbanmoinhat_true);
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

            } else if (result.equals("0")) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Đây không phải là phiên bản mới nhất.Bạn có muốn cập nhật phiên bản mới nhất không?");
                // thiết lập nội dung cho dialog

                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.link_apk)));
                        startActivity(updateIntent);

                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
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
            } else if(result.equals("")) {
                this.cancel(true);

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
