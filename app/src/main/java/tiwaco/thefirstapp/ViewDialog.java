package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by TUTRAN on 24/11/2017.
 */

public class ViewDialog {

    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loadfrom);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton_from_file = (Button) dialog.findViewById(R.id.btn_dialog_from_file);
        dialogButton_from_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                Intent myIntent = new Intent(activity, LoadActivity.class);
                //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
                activity.startActivity(myIntent);
               // activity.finish();


            }
        });

        Button dialogButton_from_server = (Button) dialog.findViewById(R.id.btn_dialog_from_server);
        dialogButton_from_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                Intent myIntent = new Intent(activity, LoadFromServerActivity.class);
                activity.startActivity(myIntent);
                // activity.finish();


            }
        });


        Button dialogButton_from_server_again = (Button) dialog.findViewById(R.id.btn_dialog_from_server_again);
        dialogButton_from_server_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                Intent myIntent = new Intent(activity, LoadFromServerDaGhiActivity.class);
                activity.startActivity(myIntent);
                // activity.finish();


            }
        });

        Button dialogButton_dulieuthu = (Button) dialog.findViewById(R.id.btn_dialog_from_server_thu);
        //dialogButton_dulieuthu.setVisibility(View.GONE);
        dialogButton_dulieuthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                Intent myIntent = new Intent(activity, LoadFromServerThu2Activity.class);
                activity.startActivity(myIntent);
                // activity.finish();


            }
        });
        Button dialogButton_thoat = (Button) dialog.findViewById(R.id.btn_thoat);
        dialogButton_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                Intent myIntent = new Intent(activity, StartActivity.class);
                //Intent myIntent = new Intent(this, LoadFromServerActivity.class);
                activity.startActivity(myIntent);


            }
        });


        dialog.show();

    }
}