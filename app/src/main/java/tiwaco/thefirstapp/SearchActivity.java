package tiwaco.thefirstapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

public class SearchActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView searchView;
    private ListView listview;
    CustomListAdapter adapter;
    Context con;
    List<KhachHangDTO> listkhachhang =null;
    private KhachHangDAO khachhangdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        con = getApplicationContext();
        listkhachhang = new ArrayList<KhachHangDTO>();
        khachhangdao  = new KhachHangDAO(con);
        listkhachhang = khachhangdao.getAllKH();
        if(listkhachhang == null){
            Log.e("search","null listkh");
        }
        adapter = new CustomListAdapter(con,listkhachhang,-1);
        listview = (ListView) findViewById(R.id.lvData);
        listview.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // thêm search vào vào action bar
        getMenuInflater().inflate(R.menu.timkiem_khachhang, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        searchView = (SearchView) itemSearch.getActionView();
        //set OnQueryTextListener cho search view để thực hiện search theo text
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            listview.clearTextFilter();
        }else {

        }
        return true;
    }
}
