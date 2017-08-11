package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.NhanVienDAO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;
import tiwaco.thefirstapp.Database.SPData;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    Button btn_dangnhap,btn_thoat;
    EditText edt_ten;
    EditText edt_pass;
    NhanVienDTO nhanviendto ;
    NhanVienDAO nhanviendao;
    Context con ;
    LichSuDAO lichsudao;
    SPData spdata;
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        con = LoginActivity.this;
        btn_dangnhap = (Button) this.findViewById(R.id.sign_in_button) ;
        btn_thoat = (Button) this.findViewById(R.id.close_button) ;
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
                spdata.luuDataKyHoaDonTrongSP("082017");
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
}

