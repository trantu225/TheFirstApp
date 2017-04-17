package tiwaco.thefirstapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    TextView txtduongchon;
    int vitri = 0;
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
        duongDAO = new DuongDAO(con);
        listduong = duongDAO.getAllDuongChuaGhi();

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_listduong);
        adapter = new CustomListDuongAdapter(con,listduong,listviewKH,txtduongchon);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        khachhangDAO = new KhachHangDAO(con);
        liskhdao = khachhangDAO.getAllKHTheoDuong(listduong.get(0).getMaDuong());
        if(Bien.adapterKH ==null){
            Bien.adapterKH = new CustomListAdapter(con,liskhdao);
            //adapterKH.notifyDataSetChanged();
            listviewKH.setAdapter(Bien.adapterKH);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return true;
    }

    private void selectItem(MenuItem item) {

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_ghinuoc:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                break;


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        selectItem(item);
        return super.onOptionsItemSelected(item);
    }

}
