package tiwaco.thefirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListDuongAdapter;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

/**
 * Created by TUTRAN on 17/04/2017.
 */

public class ListActivity extends AppCompatActivity {

    ListView listviewKH ;
    List<KhachHangDTO> liskh ;
    KhachHangDAO khachhangDAO = null;
    RecyclerView recyclerView;
    CustomListDuongAdapter adapter;
    List<DuongDTO> listduong;
    DuongDAO duongDAO;
    Context con;
    ActionBar bar;
    List<KhachHangDTO> liskhdao;
    TextView txtduongchon,txtTiltle;
    int vitri = 0;
    String title ="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.listkh_fragment);
        super.onCreate(savedInstanceState);

        con = ListActivity.this;

        bar = getSupportActionBar();
        bar.setTitle(getString(R.string.tab_dsKH));
        bar.setLogo(R.mipmap.ic_logo_tiwaco);
        ListView listviewKH = (ListView) findViewById(R.id.lv_khachhang);
        txtduongchon = (TextView) findViewById(R.id.txt_maduongchon);
        txtTiltle =(TextView) findViewById(R.id.txt_title_dskh1);
        duongDAO = new DuongDAO(con);
        listduong = duongDAO.getAllDuongChuaGhi();

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_listduong);
        adapter = new CustomListDuongAdapter(con,listduong,listviewKH,txtduongchon,recyclerView,txtTiltle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        Bien.ma_duong_dang_chon = listduong.get(Bien.selected_item).getMaDuong();
        khachhangDAO = new KhachHangDAO(con);
        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);

        title +=  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);
        Bien.adapterKH = new CustomListAdapter(con, liskhdao);
        listviewKH.setAdapter(Bien.adapterKH);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return true;
    }

    private void selectItem(MenuItem item) {
        if(!txtduongchon.getText().toString().trim().equalsIgnoreCase("")) {
            Bien.ma_duong_dang_chon = txtduongchon.getText().toString().trim();
        }
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_ghinuoc:


                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.tab_ghinuoc))
                        .setMessage(getString(R.string.list_chuyendulieu_hoighinuoc1) +" "+ Bien.ma_duong_dang_chon + " "+ getString(R.string.list_chuyendulieu_hoighinuoc2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                bundle.putString(Bien.STT,"1");
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();



          break;
   }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        selectItem(item);
        return super.onOptionsItemSelected(item);
    }

}
