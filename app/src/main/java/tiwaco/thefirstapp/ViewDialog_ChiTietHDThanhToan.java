package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import tiwaco.thefirstapp.CustomAdapter.CustomListThanhToanAdapter;

/**
 * Created by TUTRAN on 02/11/2018.
 */

public class ViewDialog_ChiTietHDThanhToan {

    public void showDialog(final Activity activity, CustomListThanhToanAdapter thanhtoandapter) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_chitiethoadon);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        String kyhd = "Thông tin hóa đơn";
        text.setText(kyhd);

        ExpandableListView exlist = (ExpandableListView) dialog.findViewById(R.id.ListThanhToanTheoKy);
        exlist.setAdapter(thanhtoandapter);

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
