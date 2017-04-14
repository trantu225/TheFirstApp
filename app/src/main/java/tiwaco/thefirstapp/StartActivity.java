package tiwaco.thefirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StartActivity extends AppCompatActivity  {

    ImageButton btnGhinuoc, btnDanhSachKH, btnLoadDl, btnBackup;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnGhinuoc = (ImageButton) findViewById(R.id.btn_ghinuoc);
        btnDanhSachKH = (ImageButton) findViewById(R.id.btn_dskh);
        btnLoadDl = (ImageButton) findViewById(R.id.btn_loaddata);
        btnBackup = (ImageButton) findViewById(R.id.btn_backup);



        btnGhinuoc.setOnClickListener(myclick);
        btnDanhSachKH.setOnClickListener(myclick);
        btnLoadDl.setOnClickListener(myclick);
        btnBackup.setOnClickListener(myclick);

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
                    myIntent=new Intent(StartActivity.this, Tab_MainActivity.class);
                    startActivity(myIntent);

                    break;
                case R.id.btn_backup:
                    break;
                case R.id.btn_loaddata:
                    myIntent=new Intent(StartActivity.this, LoadActivity.class);
                    startActivity(myIntent);
                    break;
            }
        }
    };
}
