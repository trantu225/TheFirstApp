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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
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
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.JSONUser;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.DTO.User;
import tiwaco.thefirstapp.Database.SPData;

public class ThongTinActivity extends AppCompatActivity {

    Context con;
    TextView txt_tongsokh , txt_sokhdaghi, txt_dokhghihomnay, txt_som3daghi,txt_phienban,txt_kyhd;

    TextView txt_tongsokhthu, txt_tongtien, txt_tonghd, txt_sokhdathu, txt_sokhdathuhomnay, txt_hddathu, txt_hddathuhomnay, txt_sotientdathu, txt_sotiendathuhomnay;
    EditText matkhaucu, matkhaumoi, luutudong, edit_kyhd, edit_kyhdthu;
    Switch SwitchLuuTuDong, SwitchThuOffline;
    Button doimatkhau, truyvanluocsu, thoat, capnhat, doiluucapnhat, capnhatkyhd, capnhatkyhdthu;
    SPData spdata;
    KhachHangDAO khachhangdao ;
    KhachHangThuDAO khachhangthudao;
    String urldoimatkhau = "";
    String urlcapnhat  ="";
    LinearLayout layoutghi, layoutthu, lay_kyhd, lay_kyhdthu;
    ThanhToanDAO thanhtoandao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        con = ThongTinActivity.this;
        spdata = new SPData(con);
        khachhangdao = new KhachHangDAO(con);
        khachhangthudao = new KhachHangThuDAO(con);
        thanhtoandao = new ThanhToanDAO(con);
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
        doiluucapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ThongTinActivity.this);
                if (luutudong.getText().toString().trim().equals("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage("Chưa nhập chỉ số cấu hình lưu dữ liệu tự động.");
                    // thiết lập nội dung cho dialog

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
                } else {

                    Toast.makeText(ThongTinActivity.this, "Cập nhật thành công ", Toast.LENGTH_LONG).show();
                    spdata.luuDataChiSoLuuCapNhat(Integer.parseInt(luutudong.getText().toString()));
                    luutudong.setText(String.valueOf(spdata.getDataChiSoLuuCapNhat()));
                }
            }
        });
        SwitchLuuTuDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (SwitchLuuTuDong.isChecked()) {
                    spdata.luuDataOnOffLuu(1);
                } else {
                    spdata.luuDataOnOffLuu(0);
                }
            }
        });

        SwitchThuOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (SwitchThuOffline.isChecked()) {
                    spdata.luuDataThuOffline(1);
                } else {
                    spdata.luuDataThuOffline(0);
                }
            }
        });
        luutudong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (luutudong.getText().toString().trim().equals("")) {

                        showDiaLogThongBao("Bạn chưa nhập chỉ số mức độ cập nhật tự động.");


                    } else {
                        if (TextUtils.isDigitsOnly(luutudong.getText().toString().trim())) {
                            if (spdata.getDataHuyen() != "01") {
                                doiluucapnhat.setEnabled(true);
                            } else {
                                doiluucapnhat.setEnabled(false);
                            }
                        } else {
                            luutudong.setText("");
                            showDiaLogThongBao("Chỉ được nhập số nguyên, không được chứa ký tự đặc biệt");
                        }
                    }
                }
            }
        });


        capnhatkyhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_kyhd.getText().toString().trim().equals("")) {

                    showDiaLogThongBao("Bạn chưa nhập kỳ hóa đơn.");


                } else {
                    if (TextUtils.isDigitsOnly(edit_kyhd.getText().toString().trim())) {
                        spdata.luuDataKyHoaDonTrongSP(edit_kyhd.getText().toString().trim());
                        Toast.makeText(ThongTinActivity.this, "Cập nhật thành công ", Toast.LENGTH_LONG).show();
                    } else {
                        edit_kyhd.setText("");
                        showDiaLogThongBao("Chỉ được nhập số nguyên, không được chứa ký tự đặc biệt");
                    }
                }
            }
        });


        capnhatkyhdthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_kyhdthu.getText().toString().trim().equals("")) {

                    showDiaLogThongBao("Bạn chưa nhập kỳ hóa đơn.");


                } else {
                    if (TextUtils.isDigitsOnly(edit_kyhdthu.getText().toString().trim())) {
                        spdata.luuDataKyHoaDonThuTrongSP(edit_kyhdthu.getText().toString().trim());
                    } else {
                        capnhatkyhdthu.setText("");
                        showDiaLogThongBao("Chỉ được nhập số nguyên, không được chứa ký tự đặc biệt");
                    }
                }
            }
        });

    }

    private void showDiaLogThongBao(String mess) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThongTinActivity.this);
        // khởi tạo dialog

        alertDialogBuilder.setMessage(mess);

        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        // tạo dialog
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        // hiển thị dialog
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        layoutghi = (LinearLayout) findViewById(R.id.lay_ghi);
        layoutthu = (LinearLayout) findViewById(R.id.lay_thu);
        txt_tongsokh = (TextView) findViewById(R.id.tv_tongsoKH);
        txt_sokhdaghi = (TextView) findViewById(R.id.tv_sokhdaghi);
        txt_dokhghihomnay = (TextView) findViewById(R.id.tv_sokhghihomnay);
        txt_kyhd = (TextView) findViewById(R.id.tv_kyhd);
        txt_phienban = (TextView) findViewById(R.id.tv_phienban);
        txt_som3daghi = (TextView) findViewById(R.id.tv_som3daghi);
        matkhaucu = (EditText) findViewById(R.id.password_cu);
        matkhaumoi = (EditText) findViewById(R.id.password_moi);
        luutudong = (EditText) findViewById(R.id.edit_luutudong);
        doiluucapnhat = (Button) findViewById(R.id.btn_luutudong);
        doimatkhau = (Button) findViewById(R.id.doimatkhau_button);
        truyvanluocsu = (Button) findViewById(R.id.luocsu_button);
        capnhat  = (Button) findViewById(R.id.kiemtracapnhat_button);
        thoat = (Button) findViewById(R.id.close_button);
        SwitchLuuTuDong = (Switch) findViewById(R.id.SwitchLuuTuDong);
        SwitchThuOffline = (Switch) findViewById(R.id.SwitchThuOffline);
        txt_tongsokhthu = (TextView) findViewById(R.id.tv_tongsoKHThu);
        txt_tongtien = (TextView) findViewById(R.id.tv_tongsotien);
        txt_tonghd = (TextView) findViewById(R.id.tv_tongsoHD);
        txt_sokhdathu = (TextView) findViewById(R.id.tv_sokhdathu);
        txt_sokhdathuhomnay = (TextView) findViewById(R.id.tv_sokhthuhomnay);
        txt_hddathu = (TextView) findViewById(R.id.tv_tongsoHDDaThu);
        txt_hddathuhomnay = (TextView) findViewById(R.id.tv_tongsoHDDaThuHomNay);
        txt_sotientdathu = (TextView) findViewById(R.id.tv_sotiendathu);
        txt_sotiendathuhomnay = (TextView) findViewById(R.id.tv_sotiendathuhomnay);
        edit_kyhd = (EditText) findViewById(R.id.edit_kyhd);
        capnhatkyhd = (Button) findViewById(R.id.btn_luukyhd);
        edit_kyhdthu = (EditText) findViewById(R.id.edit_kyhdthu);
        capnhatkyhdthu = (Button) findViewById(R.id.btn_luukyhdthu);
        lay_kyhd = (LinearLayout) findViewById(R.id.lay_kyhd);
        lay_kyhdthu = (LinearLayout) findViewById(R.id.lay_kyhdthu);

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
        int TongSoKHThu = khachhangthudao.countKhachHangAll();
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
        String kyhdthu = spdata.getDataKyHoaDonThuTrongSP();
        Log.e("KYHD",kyhd);
        if(!kyhd.equals("")) {
            String nam = kyhd.substring(0, 4);
            String thang = kyhd.substring(4);
            String strkyhd = thang + "/" + nam;
            txt_kyhd .setText(strkyhd);
        }

        String thoigian2 = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        int SoKHDaThu = khachhangthudao.countKhachHangDaThuTheoNhanVien(spdata.getDataNhanVienTrongSP());
        int SoKHDaThuHomNay = khachhangthudao.countKhachHangDaThuHomNayTheoNV(thoigian2, spdata.getDataNhanVienTrongSP());
        String tongsotien = thanhtoandao.getSoTienTongCong();
        String tongtienthu = thanhtoandao.getSoTienTongCongDaThuTheoTen(spdata.getDataNhanVienTrongSP());
        String tongtienthuhomnay = thanhtoandao.getSoTienTongCongDaThuTheoNgayTheoTen(thoigian2, spdata.getDataNhanVienTrongSP());
        int sohdtong = thanhtoandao.getHDTongCong();
        int sohdthu = thanhtoandao.getSoHDDaThuTheoTen(spdata.getDataNhanVienTrongSP());
        int sohdthuhomnay = thanhtoandao.getSoHDDaThuTheoNgayTheoTen(thoigian2, spdata.getDataNhanVienTrongSP());

        txt_tongsokhthu.setText(String.valueOf(TongSoKHThu));
        txt_tongtien.setText(tongsotien);
        txt_tonghd.setText(String.valueOf(sohdtong));
        txt_sokhdathu.setText(String.valueOf(SoKHDaThu));
        txt_sokhdathuhomnay.setText(String.valueOf(SoKHDaThuHomNay));
        txt_hddathu.setText(String.valueOf(sohdthu));
        txt_hddathuhomnay.setText(String.valueOf(sohdthuhomnay));
        txt_sotientdathu.setText(String.valueOf(tongtienthu));
        txt_sotiendathuhomnay.setText(String.valueOf(tongtienthuhomnay));

        if (spdata.getChucNangGhiThu().equals("GHI")) {
            layoutghi.setVisibility(View.VISIBLE);
            layoutthu.setVisibility(View.GONE);
        } else if (spdata.getChucNangGhiThu().equals("THU")) {
            layoutghi.setVisibility(View.GONE);
            layoutthu.setVisibility(View.VISIBLE);
        } else if (spdata.getChucNangGhiThu().equals("GHITHU")) {
            layoutghi.setVisibility(View.VISIBLE);
            layoutthu.setVisibility(View.VISIBLE);
        }



        luutudong.setText(String.valueOf(spdata.getDataChiSoLuuCapNhat()));
        if (!spdata.getDataHuyen().equals("01")) {
            SwitchLuuTuDong.setVisibility(View.VISIBLE);
            doiluucapnhat.setEnabled(true);
        } else {
            SwitchLuuTuDong.setVisibility(View.GONE);
            doiluucapnhat.setEnabled(false);
        }
        if (spdata.getDataOnOffLuu() == 0) {
            SwitchLuuTuDong.setChecked(false);
        } else {
            SwitchLuuTuDong.setChecked(true);
        }

        if (spdata.getDataThuOffline() == 0) {
            SwitchThuOffline.setChecked(false);
        } else {
            SwitchThuOffline.setChecked(true);
        }
        edit_kyhd.setText(spdata.getDataKyHoaDonTrongSP());
        edit_kyhdthu.setText(spdata.getDataKyHoaDonThuTrongSP());
        if (spdata.getDataNhanVienTrongSP().equalsIgnoreCase("admin")) {
            lay_kyhd.setVisibility(View.VISIBLE);
            lay_kyhdthu.setVisibility(View.VISIBLE);
        } else {
            lay_kyhd.setVisibility(View.GONE);
            lay_kyhdthu.setVisibility(View.GONE);
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
                spdata.luuDataMatKhauNhanVienTrongSP("");
                spdata.luuDataNhanVienTrongSP("");
                spdata.luuThongTinNhanVien("", "", "", "", "");
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


                        updateIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
