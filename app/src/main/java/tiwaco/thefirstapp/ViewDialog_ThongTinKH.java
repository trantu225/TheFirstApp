package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by TUTRAN on 02/11/2018.
 */

public class ViewDialog_ThongTinKH {
    public void showDialog(final Activity activity, String stt, String danhbo, String makh, String hoten, String diachi, String dienthoai) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_kyhd);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        String kyhd = "Thông tin khách hàng";
        text.setText(kyhd);

        TextView textchiso = (TextView) dialog.findViewById(R.id.cscu);
        String strstt = "STT: " + stt;
        textchiso.setText(strstt);

        TextView textm3 = (TextView) dialog.findViewById(R.id.m3cu);
        String strdanhbo = "Danh bộ: " + danhbo;
        textm3.setText(strdanhbo);

        TextView textm4 = (TextView) dialog.findViewById(R.id.makh);
        String strmakh = "Mã KH: " + makh;
        textm4.setText(strmakh);


        TextView textm5 = (TextView) dialog.findViewById(R.id.hoten);
        String strhoten = "Họ và tên: " + hoten;
        textm5.setText(strhoten);

        TextView textm6 = (TextView) dialog.findViewById(R.id.diachi);
        String strdiachi = "Địa chỉ: " + diachi;
        textm6.setText(strdiachi);

        TextView textm7 = (TextView) dialog.findViewById(R.id.dienthoai);
        String strdienthoai = "Điện thoại: " + dienthoai;
        textm7.setText(strdienthoai);


        Button dialogButton_thoat = (Button) dialog.findViewById(R.id.btn_thoat);
        dialogButton_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();

    }
}
