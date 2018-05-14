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

public class ViewDialog_login {
    final Dialog dialog;

    public ViewDialog_login( final Activity activity){
        dialog = new Dialog(activity);
    }
    public void showDialog( String msg){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_thongbao);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        dialog.show();

    }
    public void closeDialog()
    {
        dialog.dismiss();
    }
}
