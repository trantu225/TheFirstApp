package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by TUTRAN on 11/04/2018.
 */

public class ViewDialog_KyHD {
    public void showDialog(final Activity activity, String msg,String cs, String m3){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_kyhd);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        String kyhd = "Kỳ hóa đơn tháng: "+msg;
        text.setText(kyhd);

        TextView textchiso = (TextView) dialog.findViewById(R.id.cscu);
        String cscu = "Chỉ số cũ: "+ cs;
        textchiso.setText(cscu);

        TextView textm3 = (TextView) dialog.findViewById(R.id.m3cu);
        String m3cu = "M3 cũ: "+ m3;
        textm3.setText(m3cu);
        TextView txtmakh = (TextView) dialog.findViewById(R.id.makh);
        TextView txthoten = (TextView) dialog.findViewById(R.id.hoten);
        TextView txtdiachi = (TextView) dialog.findViewById(R.id.diachi);
        TextView txtdienthoai = (TextView) dialog.findViewById(R.id.dienthoai);
        TextView txtdienthoai2 = (TextView) dialog.findViewById(R.id.dienthoai2);
        txtmakh.setVisibility(View.GONE);
        txthoten.setVisibility(View.GONE);
        txtdiachi.setVisibility(View.GONE);
        txtdienthoai.setVisibility(View.GONE);
        txtdienthoai2.setVisibility(View.GONE);
        Button dialogButton_thoat = (Button) dialog.findViewById(R.id.btn_thoat);
        dialogButton_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // activity.finish();

                // activity.finish();


            }
        });


        dialog.show();

    }
}
