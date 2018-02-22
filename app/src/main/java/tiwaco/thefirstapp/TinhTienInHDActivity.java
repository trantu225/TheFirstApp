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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


/**
 * Created by TUTRAN on 15/12/2017.
 */

public class TinhTienInHDActivity  extends Activity {




    EditText message;
    Button printbtn;
    LinearLayout relativeLayout;
    private PrintManager mgr=null;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream;
    String makh_nhan = "";
    KhachHangDAO khdao;
    Context con;
    TextView  tv_makh, tv_danhbo, tv_hoten, tv_diachi, tv_csocu, tv_csomoi, tv_m3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printbill);
       // message = (EditText)findViewById(R.id.message);

        printbtn = (Button)findViewById(R.id.printButton);
        relativeLayout = (LinearLayout) findViewById(R.id.print);

        tv_makh = (TextView) findViewById(R.id.tv_maKH);
        tv_danhbo = (TextView) findViewById(R.id.tv_danhbo);
        tv_hoten = (TextView) findViewById(R.id.tv_hoten);
        tv_diachi = (TextView) findViewById(R.id.tv_diachi);
        tv_csocu = (TextView) findViewById(R.id.tv_chisocu);
        tv_csomoi= (TextView) findViewById(R.id.tv_chisomoi);
        tv_m3 = (TextView) findViewById(R.id.tv_m3);
        con =TinhTienInHDActivity.this;
        khdao = new KhachHangDAO(con);
        mgr=(PrintManager)getSystemService(PRINT_SERVICE);

        HienThiViewTheoMaDuong();



        printbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                connect();

            }
        });
    }


    public void HienThiViewTheoMaDuong(){

        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_GHINUOC);
        makh_nhan = packageFromCaller.getString(Bien.MAKH);
        KhachHangDTO kh = khdao.getKHTheoMaKH(makh_nhan);
        tv_makh.setText(kh.getMaKhachHang());
        tv_danhbo.setText(kh.getDanhBo());
        tv_hoten.setText(kh.getTenKhachHang());
        tv_diachi.setText(kh.getDiaChi());
        tv_csocu.setText(kh.getChiSo1());
        tv_csomoi.setText(kh.getChiSo());
        tv_m3.setText(kh.getSLTieuThu());

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
            print_bt();

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
            String msg = message.getText().toString()+"\n";
            String xuongdong  ="";
            String  tencty  = "CTY TNHH MTV CẤP NƯỚC TIỀN GIANG\n\n";
            String Giaybao = "GIẤY BÁO\n";
            String ngay = "Ngày 18 tháng 12 năm 2017\n";
            String kihd = "Kỳ hóa đơn: 12/2017\n\n";
            String madb = "Danh bộ: 00050\n";
            String khachhang = "Tên KH: NGUYEN HOANG TAM\n";
            String diachi = "Địa chỉ: 16 Hai Ba Trung\n";
            String chisocu = "Chỉ số cũ: 18\n";
            String chisomoi = "Chỉ số mới: 28\n";
            String m3  = "Số m3 tiêu thụ: 10 m3\n";
            String sotien ="Số tiền phải trả:100.000 đồng\n";
            String nhacnho  = "\nĐề nghị quý khách vui lòng\nthanh toán tiền nước trong vòng\n5 ngày kể từ ngày nhận giấy báo\nQua thời hạn trên Cty sẽ tiến\nhành tạm ngưng cung cấp nước.\n\n";
            String lienlac ="Vui lòng liên hệ số điện thoại\n0273.3873425 để được giúp đỡ.\n";
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
            btoutputstream.write(khachhang.getBytes("UTF-16LE"));
            btoutputstream.write(diachi.getBytes("UTF-16LE"));
            btoutputstream.write(chisocu.getBytes("UTF-16LE"));
            btoutputstream.write(chisomoi.getBytes("UTF-16LE"));
            btoutputstream.write(m3.getBytes("UTF-16LE"));
            btoutputstream.write(sotien.getBytes("UTF-16LE"));
            btoutputstream.write(nhacnho.getBytes("UTF-16LE"));
            writeWithFormat( new Formatter().bold().get(), Formatter.leftAlign());
            btoutputstream.write(lienlac.getBytes("UTF-16LE"));
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
                print_bt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void printText(String msg) {
        try {
            // Print normal text
            btoutputstream.write(msg.getBytes());
        } catch (IOException e) {
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
    String dirpath;
    public void layoutToImage() {
        // get view group using reference

        Bitmap bm =getBitmapFromView(relativeLayout);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        if (bm != null) {
            byte[] command = Utils.decodeBitmap(bm);
            try {
                btoutputstream.write(PrinterCommands.ESC_ALIGN_CENTER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printText(command);
        } else {
            Log.e("Print Photo error", "the file isn't exists");
        }
        File f = new File(Environment.getExternalStorageDirectory() + "/BACKUPTIWAREAD/image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void printNewLine() {
        try {
            btoutputstream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                btoutputstream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap


        return returnedBitmap;
    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            Log.e("dirpath",dirpath);
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + "/BACKUPTIWAREAD/image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            MediaScannerConnection.scanFile(TinhTienInHDActivity.this, new String[]{dirpath + "/NewPDF.pdf"}, null, null);
            document.close();

            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {

        }
    }


/*

    private String TAG = "InHD Activity";
    EditText message;
    Button btnPrint, btnBill;
    LinearLayout layoutprint;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_print);
        message = (EditText) findViewById(R.id.txtMessage);
        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnBill = (Button) findViewById(R.id.btnBill);
        layoutprint = (LinearLayout) findViewById(R.id.activity_print);
        btnPrint.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                printDemo();
            }
        });
        btnBill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                printBill();
            }
        });


    }

    String dirpath;

    public void layoutToImage() {
        // get view group using reference

        Bitmap bm = getBitmapFromView(layoutprint);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + "/BACKUPTIWAREAD/image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap


        return returnedBitmap;
    }

    protected void printBill() {
        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
        } else {
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                outputStream.write(printformat);


                printCustom("Fair Group BD", 2, 1);
                printCustom("Pepperoni Foods Ltd.", 0, 1);
                printPhoto(R.drawable.ic_logo_tiwaco);
                printCustom("H-123, R-123, Dhanmondi, Dhaka-1212", 0, 1);
                printCustom("Hot Line: +88000 000000", 0, 1);
                printCustom("Vat Reg : 0000000000,Mushak : 11", 0, 1);
                String dateTime[] = getDateTime();
                printText(leftRightAlign(dateTime[0], dateTime[1]));
                printText(leftRightAlign("Qty: Name", "Price "));
                printCustom(new String(new char[32]).replace("\0", "."), 0, 1);
                printText(leftRightAlign("Total", "2,0000/="));
                printNewLine();
                printCustom("Thank you for coming & we look", 0, 1);
                printCustom("forward to serve you again", 0, 1);
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void printDemo() {
        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
        } else {
            layoutToImage();
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();

                byte[] printformat = {0x1B, 0 * 21, FONT_TYPE};
                //outputStream.write(printformat);

                //print title
                printUnicode();
                //print normal text
                printCustom(message.getText().toString(), 0, 0);
                Bitmap bm = getBitmapFromView(layoutprint);
                printPhoto(bm);
                printNewLine();
                printText("\t>>>>\t   Thank you\t  <<<<     "); // total 32 char in a single line
                //resetPrint(); //reset printer
                printUnicode();
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(Bitmap bmp) {
        try {
            // Bitmap bmp = BitmapFactory.decodeResource(getResources(),
            //         img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode() {
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try {
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 31) {
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime[] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        return dateTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (btsocket != null) {
                outputStream.close();
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
            if (btsocket != null) {
                printText(message.getText().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


*/


}
