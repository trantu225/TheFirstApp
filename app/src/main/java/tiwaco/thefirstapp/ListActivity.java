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
import tiwaco.thefirstapp.Database.SPData;

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
    SPData spdata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.listkh_fragment);
        super.onCreate(savedInstanceState);


        con = ListActivity.this;

        bar = getSupportActionBar();
        bar.setTitle(getString(R.string.tab_dsKH));
        bar.setLogo(R.mipmap.ic_logo_tiwaco);
        spdata= new SPData(con);
        ListView listviewKH = (ListView) findViewById(R.id.lv_khachhang);
        txtduongchon = (TextView) findViewById(R.id.txt_maduongchon);
        txtTiltle =(TextView) findViewById(R.id.txt_title_dskh1);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_listduong);
        duongDAO = new DuongDAO(con);
        listduong = duongDAO.getAllDuong();


        adapter = new CustomListDuongAdapter(con,listduong,listviewKH,txtduongchon,recyclerView,txtTiltle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
      //  Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
        layoutManager.scrollToPositionWithOffset(spdata.getDataIndexDuongDangGhiTrongSP() , 0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SPData spdata = new SPData(con);
        String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP();
        if(!SPduongdangghi.equals("")){
            Bien.ma_duong_dang_chon = SPduongdangghi;
        }else {
            Bien.ma_duong_dang_chon = listduong.get(spdata.getDataIndexDuongDangGhiTrongSP()).getMaDuong();
        }

        khachhangDAO = new KhachHangDAO(con);
        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);

        title +=  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);
        Bien.adapterKH = new CustomListAdapter(con, liskhdao, spdata.getDataIndexDuongDangGhiTrongSP());
        listviewKH.setAdapter(Bien.adapterKH);
        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
        listviewKH.setSelection( Bien.bien_index_khachhang);


        Log.e("select duong---listactivity", String.valueOf(Bien.selected_item));


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
                int vitriduong = 0;
                for(int  i  = 0; i<listduong.size();i++){
                    if(listduong.get(i).getMaDuong().equals(Bien.ma_duong_dang_chon)){
                        vitriduong = i;
                    }
                }
                final int finalVitriduong = vitriduong;
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.tab_ghinuoc))
                        .setMessage(getString(R.string.list_chuyendulieu_hoighinuoc1) +" "+ Bien.ma_duong_dang_chon + " "+ getString(R.string.list_chuyendulieu_hoighinuoc2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                bundle.putInt(Bien.VITRI,finalVitriduong);
                                Log.e("LISTACTIVITY_vitriduongghi", String.valueOf(finalVitriduong));
                                intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                Log.e("gui bundle maduong", Bien.ma_duong_dang_chon);
                                Log.e("gui bundle sott",khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon));
                                bundle.putString(Bien.STT,khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setCancelable(false)
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
