package tiwaco.thefirstapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

// Import neccessor namespace
import android.bluetooth.BluetoothAdapter;
import  android.bluetooth.BluetoothDevice;
import  android.bluetooth.BluetoothSocket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.RunnableFuture;
import java.util.logging.LogRecord;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;


/**
 * Created by TUTRAN on 15/12/2017.
 */

public class TinhTienInHDActivity  extends AppCompatActivity {





    Button printbtn;
    LinearLayout relativeLayout;
    private PrintManager mgr=null;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream;
    String makh_nhan = "";
    KhachHangDAO khdao;
    Context con;
    SPData spdata;
    DecimalFormat format,format1,format2 ;
    RadioButton rad_ingiaybao,rad_inhd;
    KhachHangDTO kh;

    TextView  tv_makh, tv_danhbo, tv_hoten, tv_diachi, tv_csocu, tv_csomoi, tv_m3,tv_tiennuoc, tv_thue, tv_phi, tv_tongcong;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printbill);
        // message = (EditText)findViewById(R.id.message);
        getSupportActionBar().setTitle("Tiền nước");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        printbtn = (Button)findViewById(R.id.printButton);
        relativeLayout = (LinearLayout) findViewById(R.id.print);

        tv_makh = (TextView) findViewById(R.id.tv_maKH);
        tv_danhbo = (TextView) findViewById(R.id.tv_danhbo);
        tv_hoten = (TextView) findViewById(R.id.tv_hoten);
        tv_diachi = (TextView) findViewById(R.id.tv_diachi);
        tv_csocu = (TextView) findViewById(R.id.tv_chisocu);
        tv_csomoi= (TextView) findViewById(R.id.tv_chisomoi);
        tv_m3 = (TextView) findViewById(R.id.tv_m3);

        tv_tiennuoc = (TextView) findViewById(R.id.tv_tiennuoc);
        tv_thue = (TextView) findViewById(R.id.tv_thue);
        tv_phi= (TextView) findViewById(R.id.tv_phi);
        tv_tongcong = (TextView) findViewById(R.id.tv_tongcong);
        rad_ingiaybao = (RadioButton) findViewById(R.id.rad_ingiaybao);
        rad_inhd = (RadioButton) findViewById(R.id.rad_inhd);
        con =TinhTienInHDActivity.this;
        spdata = new SPData(con);
        khdao = new KhachHangDAO(con);
        mgr=(PrintManager)getSystemService(PRINT_SERVICE);


        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);

        HienThiViewTheoMaDuong();



        printbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(rad_ingiaybao.isChecked()) {
                    rad_inhd.setChecked(false);
                }
                else if(rad_inhd.isChecked()){
                    rad_ingiaybao.setChecked(false);
                }

                connect();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void HienThiViewTheoMaDuong(){

        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_GHINUOC);
        makh_nhan = packageFromCaller.getString(Bien.MAKH);
        kh = khdao.getKHTheoMaKH(makh_nhan);
        tv_makh.setText(kh.getMaKhachHang());
        tv_danhbo.setText(kh.getDanhBo());
        tv_hoten.setText(kh.getTenKhachHang());
        tv_diachi.setText(kh.getDiaChi());


        tv_csocu.setText(kh.getChiSo1());
        tv_csomoi.setText(kh.getChiSo());
        tv_m3.setText(kh.getSLTieuThu());
        rad_ingiaybao.setChecked(true);
        String tiennuoc = String.valueOf(kh.getTienNuoc());
        if(!tiennuoc.equals("")){

            tv_tiennuoc.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble((tiennuoc)))))+ " đ");
        }
        String phi  = String.valueOf(kh.getphi());
        if(!phi.equals("")){

            tv_phi.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(phi))))+ " đ");
        }
        String thue  = String.valueOf(kh.getThue());
        if(!thue.equals("")) {


            tv_thue.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(thue)))) + " đ");
        }
        String tongcong = String.valueOf(kh.gettongcong());
        if (!tongcong.equals("")) {

            tv_tongcong.setText(format2.format(Double.parseDouble(format1.format(Double.parseDouble(tongcong)))) + " đ");
        }








    }
    protected void connect() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
        }
        else{

            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btoutputstream = opstream;
            if(rad_ingiaybao.isChecked()) {
                print_bt();
            }
            else if(rad_inhd.isChecked()){
                print_bt_hoadon();
            }

        }

    }
    private void print_bt() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        layoutToImage();
//        try {
//            imageToPDF();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            btoutputstream = btsocket.getOutputStream();


            byte[] printformat = { 0x1B, 0x21, FONT_TYPE };
            //btoutputstream.write(printformat);

            String xuongdong  ="\n";
            String  tencty  = "CTY TNHH MTV CẤP NƯỚC TIỀN GIANG\n\n";
            String Giaybao = "GIẤY BÁO\n";
            String thoigian = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            String ngay = "Ngày "+thoigian+"\n";
            String kyhd = spdata.getDataKyHoaDonTrongSP();
            Log.e("KYHD",kyhd);
            String kihd ="";
            if(!kyhd.equals("")) {
                String nam = kyhd.substring(0, 4);
                String thang = kyhd.substring(4);
                String strkyhd = thang + "/" + nam;
                kihd = "Kỳ hóa đơn: "+strkyhd+"\n\n";
            }

            String madb = "Danh bộ: "+tv_danhbo.getText()+"\n";
            String khachhang = "Tên KH: "+tv_hoten.getText();
            List<String> listhotenmoihang  = catchuxuongdong(khachhang);
            String diachi = "Địa chỉ: "+tv_diachi.getText();
            List<String> listdiachimoihang  = catchuxuongdong(diachi);


            String chisocu = "\nChỉ số cũ: "+tv_csocu.getText();
            String chisomoi = "\nChỉ số mới: "+tv_csomoi.getText();
            String m3  = "\nSố m3 tiêu thụ: "+ tv_m3.getText()+" m3";
            String sotien ="\nSố tiền phải trả: "+ format2.format(Double.parseDouble(format1.format(Double.valueOf(kh.gettongcong())))) +" đ";
//            String nhacnho  = "\nĐề nghị quý khách vui lòng\nthanh toán tiền nước trong vòng\n5 ngày kể từ ngày nhận giấy báo\nQua thời hạn trên Cty sẽ tiến\nhành tạm ngưng cung cấp nước.\n\n";
//            String lienlac ="Vui lòng liên hệ số điện thoại\n0273.3873425 để được giúp đỡ.\n";

            String nhacnho  = "Đề nghị quý khách vui lòng thanh toán tiền nước trong vòng 5 ngày kể từ ngày nhận giấy báo. Qua thời hạn trên Cty sẽ tiến hành tạm ngưng cung cấp nước.";
            List<String> listnhacnhomoihang  = catchuxuongdong(nhacnho);
            String lienlac ="Vui lòng liên hệ số điện thoại 0273.3873425 để được giúp đỡ.\n";
            List<String> listlienlacmoihang  = catchuxuongdong(lienlac);
//            try {
//                byte[] bytes = tencty.getBytes("windows-1258");
//                tencty = new String(bytes, "windows-1258");
//            } catch ( UnsupportedEncodingException e ) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            writeWithFormat(new Formatter().get(), Formatter.leftAlign());
            btoutputstream.write(tencty.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().width().get(), Formatter.centerAlign());
            btoutputstream.write(Giaybao.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().get(), Formatter.centerAlign());
            btoutputstream.write(ngay.getBytes("UTF-16LE"));
            btoutputstream.write(kihd.getBytes("UTF-16LE"));

            writeWithFormat( new Formatter().get(), Formatter.leftAlign());
            btoutputstream.write(madb.getBytes("UTF-16LE"));

            for  (int i  = 0; i<listhotenmoihang.size();i++ )
            {
                btoutputstream.write(listhotenmoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }

            for  (int i  = 0; i<listdiachimoihang.size();i++ )
            {
                btoutputstream.write(listdiachimoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            btoutputstream.write(chisocu.getBytes("UTF-16LE"));
            btoutputstream.write(chisomoi.getBytes("UTF-16LE"));
            btoutputstream.write(m3.getBytes("UTF-16LE"));
            btoutputstream.write(sotien.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            for  (int i  = 0; i<listnhacnhomoihang.size();i++ )
            {
                btoutputstream.write(listnhacnhomoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            writeWithFormat( new Formatter().bold().get(), Formatter.leftAlign());
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));

            for  (int i  = 0; i<listlienlacmoihang.size();i++ )
            {
                btoutputstream.write(listlienlacmoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            btoutputstream.write(0x0D);
            btoutputstream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void print_bt_hoadon() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        layoutToImage();
//        try {
//            imageToPDF();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            btoutputstream = btsocket.getOutputStream();


            byte[] printformat = { 0x1B, 0x21, FONT_TYPE };
            //btoutputstream.write(printformat);

            String xuongdong  ="\n";
            String  tencty  = "CTY TNHH MTV CẤP NƯỚC TIỀN GIANG\n\n";
            String Giaybao = "BIÊN NHẬN THANH TOÁN\n";
            String thoigian = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            String ngay = "Ngày "+thoigian+"\n";
            String kyhd = spdata.getDataKyHoaDonTrongSP();
            Log.e("KYHD",kyhd);
            String kihd ="";
            if(!kyhd.equals("")) {
                String nam = kyhd.substring(0, 4);
                String thang = kyhd.substring(4);
                String strkyhd = thang + "/" + nam;
                kihd = "Kỳ hóa đơn: "+strkyhd+"\n\n";
            }

            String madb = "Danh bộ: "+tv_danhbo.getText()+"\n";
            String khachhang = "Tên KH: "+tv_hoten.getText();
            List<String> listhotenmoihang  = catchuxuongdong(khachhang);
            String diachi = "Địa chỉ: "+tv_diachi.getText();
            List<String> listdiachimoihang  = catchuxuongdong(diachi);


            String chisocu = "Chỉ số cũ: "+tv_csocu.getText();
            String chisomoi = "\nChỉ số mới: "+tv_csomoi.getText();
            String m3  = "\nSố m3 tiêu thụ: "+ tv_m3.getText()+" m3";
            String chitiet  ="\nChi tiết: \n";
            String m3t1 = kh.getM3t1();
            double m3t1d = 0,m3t2d= 0,m3t3d= 0,m3t4d= 0,tien1d= 0,tien2d= 0,tien3d= 0,tien4d= 0;

            if(!m3t1.equals("")) {
                m3t1d = Double.parseDouble(m3t1);
            }
            String m3t2 = kh.getM3t2();
            if(!m3t2.equals("")) {
                m3t2d = Double.parseDouble(m3t2);
            }
            String m3t3 = kh.getM3t3();
            if(!m3t3.equals("")) {
                m3t3d = Double.parseDouble(m3t3);
            }
            String m3t4 = kh.getM3t4();
            if(!m3t4.equals("")) {
                m3t4d = Double.parseDouble(m3t4);
            }

            String tien1 = kh.getTien1();
            if(!tien1.equals("")) {
                tien1d = Double.parseDouble(tien1);
            }
            String tien2 = kh.getTien2();
            if(!tien2.equals("")) {
                tien2d = Double.parseDouble(tien2);
            }
            String tien3 = kh.getTien3();
            if(!tien3.equals("")) {
                tien3d = Double.parseDouble(tien3);
            }
            String tien4 = kh.getTien4();
            if(!tien4.equals("")) {
                tien4d = Double.parseDouble(tien4);
            }

            String dongia1 ="";
            if(!m3t1.equals("0") && !m3t1.equals(""))
            {
                double dongia1d  = tien1d/m3t1d;
                dongia1 = m3t1 +" x " +format.format(dongia1d) + " = " +format2.format(Double.parseDouble(format1.format(tien1d))) +" đ";
            }

            String dongia2 ="";
            if(!m3t2.equals("0") && !m3t2.equals(""))
            {
                double dongia2d  = tien2d/m3t2d;
                dongia2 = m3t2 +" x " +format.format(dongia2d) + " = " +format2.format(Double.parseDouble(format1.format(tien2d))) +" đ";
            }

            String dongia3 = "";
            if(!m3t3.equals("0") && !m3t3.equals(""))
            {
                double dongia3d  = tien3d/m3t3d;
                dongia3 = m3t3 +" x " +format.format(dongia3d) + " = " +format2.format(Double.parseDouble(format1.format(tien3d))) +" đ";
            }

            String dongia4 = "";
            if(!m3t4.equals("0") && !m3t4.equals(""))
            {
                double dongia4d  = tien4d/m3t4d;
                dongia4 = m3t4 +" x " +format.format(dongia4d) + " = " +format2.format(Double.parseDouble(format1.format(tien4d))) +" đ";
            }

            double tiennuocd = 0, thued  =  0,phid =0, tongcongd = 0;
            if(!kh.getTienNuoc().equals(""))
            {
                tiennuocd =  Double.valueOf(kh.getTienNuoc());
            }

            if(!kh.getThue().equals(""))
            {
                thued =  Double.valueOf(kh.getThue());
            }

            if(!kh.getphi().equals(""))
            {
                phid =  Double.valueOf(kh.getphi());
            }

            if(!kh.gettongcong().equals(""))
            {
                tongcongd =  Double.valueOf(kh.gettongcong());
            }
            int lenthue  = format2.format(Double.parseDouble(format1.format(tiennuocd))).length()  - format2.format(Double.parseDouble(format1.format(thued))).length();
            int lenphi  = format2.format(Double.parseDouble(format1.format(tiennuocd))).length()  - format2.format(Double.parseDouble(format1.format(phid))).length();

            String tiennuoc  = "Tiền nước: "+format2.format(Double.parseDouble(format1.format(tiennuocd))) +" đ";
            String thue  = "Thuế GTGT: ";
            if(lenthue>0){
                for(int i  =0;i<lenthue;i++)
                {
                    thue +=" ";
                }
            }
            thue +=  format2.format(Double.parseDouble(format1.format(thued))) +" đ";
            String phi  = "Phí NTSH: ";
            if(lenphi>0){
                for(int i  =0;i<lenphi;i++)
                {
                    phi +=" ";
                }
            }
            phi += format2.format(Double.parseDouble(format1.format(phid)))  +" đ";

            String tongcong ="Tổng cộng: "+format2.format(Double.parseDouble(format1.format(tongcongd)))  +" đ";
            String gach = "--------------------------------";
//            String nhacnho  = "\nĐề nghị quý khách vui lòng\nthanh toán tiền nước trong vòng\n5 ngày kể từ ngày nhận giấy báo\nQua thời hạn trên Cty sẽ tiến\nhành tạm ngưng cung cấp nước.\n\n";
//            String lienlac ="Vui lòng liên hệ số điện thoại\n0273.3873425 để được giúp đỡ.\n";

            String nhacnho  = "Khi có nhu cầu in HĐĐT xin vui lòng truy cập vào website http://www.cskh.tiwaco.com.vn .";
            List<String> listnhacnhomoihang  = catchuxuongdong(nhacnho);
            String lienlac ="Vui lòng liên hệ số điện thoại 0273.3873425 để được giúp đỡ.\n";
            List<String> listlienlacmoihang  = catchuxuongdong(lienlac);
//            try {
//                byte[] bytes = tencty.getBytes("windows-1258");
//                tencty = new String(bytes, "windows-1258");
//            } catch ( UnsupportedEncodingException e ) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            writeWithFormat(new Formatter().get(), Formatter.leftAlign());
            btoutputstream.write(tencty.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().bold().get(), Formatter.centerAlign());
            btoutputstream.write(Giaybao.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().get(), Formatter.centerAlign());
            btoutputstream.write(ngay.getBytes("UTF-16LE"));
            btoutputstream.write(kihd.getBytes("UTF-16LE"));

            writeWithFormat( new Formatter().get(), Formatter.leftAlign());
            btoutputstream.write(madb.getBytes("UTF-16LE"));

            for  (int i  = 0; i<listhotenmoihang.size();i++ )
            {
                btoutputstream.write(listhotenmoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }

            for  (int i  = 0; i<listdiachimoihang.size();i++ )
            {
                btoutputstream.write(listdiachimoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            btoutputstream.write(gach.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(chisocu.getBytes("UTF-16LE"));
            btoutputstream.write(chisomoi.getBytes("UTF-16LE"));
            btoutputstream.write(m3.getBytes("UTF-16LE"));
            btoutputstream.write(chitiet.getBytes("UTF-16LE"));
            writeWithFormat(new Formatter().get(), Formatter.rightAlign());
            if(!dongia1.equals(""))
            {
                btoutputstream.write(dongia1.getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            if(!dongia2.equals(""))
            {
                btoutputstream.write(dongia2.getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            if(!dongia3.equals(""))
            {
                btoutputstream.write(dongia3.getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            if(!dongia4.equals(""))
            {
                btoutputstream.write(dongia4.getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }

            btoutputstream.write(gach.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(tiennuoc.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(phi.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(thue.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(gach.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().bold().get(), Formatter.rightAlign());
            btoutputstream.write(tongcong.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            writeWithFormat(new Formatter().get(), Formatter.leftAlign());
            for  (int i  = 0; i<listnhacnhomoihang.size();i++ )
            {
                btoutputstream.write(listnhacnhomoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            writeWithFormat( new Formatter().bold().get(), Formatter.leftAlign());
            btoutputstream.write(xuongdong.getBytes("UTF-16LE"));

            for  (int i  = 0; i<listlienlacmoihang.size();i++ )
            {
                btoutputstream.write(listlienlacmoihang.get(i).toString().getBytes("UTF-16LE"));
                btoutputstream.write(xuongdong.getBytes("UTF-16LE"));
            }
            btoutputstream.write(0x0D);
            btoutputstream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                btoutputstream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = BTDeviceList.getSocket();
            if(btsocket != null){
                if(rad_ingiaybao.isChecked()) {
                    print_bt();
                }
                else if(rad_inhd.isChecked()){
                    print_bt_hoadon();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            btoutputstream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean writeWithFormat( final byte[] pFormat, final byte[] pAlignment) {
        try {
            // Notify printer it should be printed with given alignment:
            //   btoutputstream.write(buffer, 0, buffer.length);

            btoutputstream.write(pAlignment);
            // Notify printer it should be printed in the given format:
            btoutputstream.write(pFormat);



            return true;
        } catch (IOException e) {
            //Log.e(TAG, "Exception during write", e);
            return false;
        }
    }

    private void printNewLine() {
        try {
            btoutputstream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public List<String> catchuxuongdong(String s){
        List<String> listdiachi  = new ArrayList<String>();
        while (s.length() >0)
        {

            int  vitricachdautien  =   s.indexOf(' ');
            if(vitricachdautien != -1) {
                String tudau = s.substring(0, vitricachdautien);
                listdiachi.add(tudau);
                s = s.substring(vitricachdautien).trim();
            }
            else{
                listdiachi.add(s);
                s = "";
            }

        }

//
//
//
//        for  (int i  = 0; i<listdiachi.size();i++ )
//        {
//            Log.e("Tu "+i,listdiachi.get(i).toString() +",dai:"+listdiachi.get(i).toString().length() );
//        }
        String chuoimoihang = "";
        int gioihanchuoi = 32;
        List<String> listdiachimoihang = new ArrayList<>();
        for(int  j  =0;j<listdiachi.size();j++)
        {
            if((chuoimoihang.length()+(listdiachi.get(j).toString().trim() +" ").length())<=gioihanchuoi){
                chuoimoihang +=listdiachi.get(j).toString().trim() +" ";
                Log.e("Chuoi moi hang",chuoimoihang +" dài :"+chuoimoihang.length());
            }
            else{
                listdiachimoihang.add(chuoimoihang.trim());
                chuoimoihang= listdiachi.get(j).toString().trim() +" ";
            }

            if(j == listdiachi.size()-1  )
            {

                listdiachimoihang.add(chuoimoihang);
                chuoimoihang ="";
            }
        }

//        for  (int i  = 0; i<listdiachimoihang.size();i++ )
//        {
//            Log.e("Diachi "+i,listdiachimoihang.get(i).toString() +",dai:"+listdiachimoihang.get(i).toString().length() );
//        }

        return listdiachimoihang ;
    }





}
