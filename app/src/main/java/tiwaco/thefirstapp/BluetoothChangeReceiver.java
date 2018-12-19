package tiwaco.thefirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by TUTRAN on 19/11/2018.
 */

public class BluetoothChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            if (Bien.btsocket != null) {
                Bien.btoutputstream.close();
                Bien.btsocket.close();
                Bien.btsocket = null;
                Toast.makeText(context, "Đóng kết nối...", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // khởi tạo dialog

                alertDialogBuilder.setMessage("Kết nối với máy in thất bại.Hãy kiểm tra lại máy in đã mở chưa.");

                alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  new UpdateThongTinThuNuoc().execute(urlstr);
                        dialog.dismiss();
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // hiển thị dialog
            }
        } catch (Exception ez) {
            ez.printStackTrace();
        }

    }

}
