package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import tiwaco.thefirstapp.Database.SPData;

public class StartActivity extends AppCompatActivity  {

    ImageButton btnGhinuoc, btnDanhSachKH, btnLoadDl, btnBackup;
    Context con;
    SPData spdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnGhinuoc = (ImageButton) findViewById(R.id.btn_ghinuoc);
        btnDanhSachKH = (ImageButton) findViewById(R.id.btn_dskh);
        btnLoadDl = (ImageButton) findViewById(R.id.btn_loaddata);
        btnBackup = (ImageButton) findViewById(R.id.btn_backup);

        getSupportActionBar().hide();

        btnGhinuoc.setOnClickListener(myclick);
        btnDanhSachKH.setOnClickListener(myclick);
        btnLoadDl.setOnClickListener(myclick);
      //  btnBackup.setOnClickListener(myclick);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private View.OnClickListener myclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent;
            switch(v.getId()){
                case R.id.btn_ghinuoc:
                     myIntent = new Intent(StartActivity.this,MainActivity.class);
                    startActivity(myIntent);

                    break;

                case R.id.btn_dskh:
                    myIntent=new Intent(StartActivity.this, ListActivity.class);
                    startActivity(myIntent);

                    break;

/*                     myIntent=new Intent(StartActivity.this, Backup_Activity.class);
                    startActivity(myIntent);
                   spdata = new SPData(con);
                    int ghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienbkall = spdata.getDataBKALLTrongSP();
                    Bien.bienbkcg = spdata.getDataBKCGTrongSP();
                    Bien.bienbkdg = spdata.getDataBKDGTrongSP();

                    if(Bien.bienbkall == Bien.bienghi && Bien.bienbkcg == Bien.bienghi && Bien.bienbkdg == Bien.bienghi)
                    {

                        taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                    }
                    else{
                        myIntent=new Intent(StartActivity.this, Backup_Activity.class);
                        startActivity(myIntent);
                    }
*/

                case R.id.btn_loaddata:
                    myIntent=new Intent(StartActivity.this, LoadActivity.class);
                    startActivity(myIntent);
                    break;
            }
        }
    };
    private void taoDialogThongBao(String message)
    {
        //Hiển thị dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
        // khởi tạo dialog
        alertDialogBuilder.setMessage(message);
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
        alertDialog.show();
        // hiển thị dialog
    }
    public void gobackup(View view){
                 /*   spdata = new SPData(con);
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienbkall = spdata.getDataBKALLTrongSP();
                    Bien.bienbkcg = spdata.getDataBKCGTrongSP();
                    Bien.bienbkdg = spdata.getDataBKDGTrongSP();

                    if(Bien.bienbkall == Bien.bienghi && Bien.bienbkcg == Bien.bienghi && Bien.bienbkdg == Bien.bienghi)
                    {

                        taoDialogThongBao(getString(R.string.backup_dialog_moinhat));
                    }
                    else{*/
                         Intent myIntent=new Intent(StartActivity.this, Backup_Activity.class);
                         startActivity(myIntent);
                 //   }

    }
}
