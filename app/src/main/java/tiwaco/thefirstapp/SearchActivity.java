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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

public class SearchActivity  extends AppCompatActivity {
    private ListView listview;
    CustomListAdapter adapter;
    Context con;
    DuongDAO duongdao ;
    ArrayList<String> arrDuong;
    List<KhachHangDTO> listkhachhang =null;
    KhachHangDAO khachhangdao;
    List<DuongDTO> listduong;
    ImageButton btnBack, btnEye , btnSearch;
    Spinner spinDuong;
    CheckBox ckDanhBo, ckHoten, ckDienThoai;
    LinearLayout layout_chitieu;
    String maduong ="";
    Integer vitriduong =0;
    Boolean flagEye  = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        con = getApplicationContext();
        getSupportActionBar().hide();
        listkhachhang = new ArrayList<KhachHangDTO>();
        khachhangdao  = new KhachHangDAO(con);
        listduong = new ArrayList<>();
        listkhachhang = khachhangdao.getAllKH();
        arrDuong = new ArrayList<>();
        duongdao = new DuongDAO(con);

        taoview();
        loadDataDuong();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagEye) {
                    layout_chitieu.setVisibility(View.GONE);
                    btnEye.setBackgroundResource(R.drawable.ic_eye_red);
                    flagEye = false;
                }
                else{
                    layout_chitieu.setVisibility(View.VISIBLE);
                    btnEye.setBackgroundResource(R.drawable.ic_eye);
                    flagEye = true;
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timkiem();
            }
        });
      //  adapter = new CustomListAdapter(con,listkhachhang,-1);

     //   listview.setAdapter(adapter);


    }

    private void timkiem() {
    }

    private void taoview(){
        listview = (ListView) findViewById(R.id.lvData);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnEye = (ImageButton) findViewById(R.id.btn_an);
        btnSearch = (ImageButton) findViewById(R.id.btn_tim);;
        spinDuong = (Spinner) findViewById(R.id.spin_dsduong);
        ckDanhBo = (CheckBox) findViewById(R.id.cb_danhbo);
        ckHoten = (CheckBox) findViewById(R.id.cb_hoten);
        ckDienThoai =(CheckBox) findViewById(R.id.cb_dienthoai);
        layout_chitieu =(LinearLayout) findViewById(R.id.layout_khungchitieu);
        layout_chitieu.setVisibility(View.VISIBLE);
    }
    private void loadDataDuong(){
        listduong = duongdao.getAllDuong();
        for(int i = 0; i<listduong.size();i++){
            String sDuong  = listduong.get(i).getMaDuong() +"."+listduong.get(i).getTenDuong();
            sDuong.trim();
            arrDuong.add(sDuong);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arrDuong
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinDuong.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinDuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vitriduong = position;
                    maduong = arrDuong.get(position).substring(0,2);
                Log.e("Vi tri duong", vitriduong.toString());
                Log.e("Ma duong", maduong);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vitriduong = 0;
                maduong = arrDuong.get(0).substring(0,2);
                Log.e("Vi tri duong", vitriduong.toString());
                Log.e("Ma duong", maduong);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // thêm search vào vào action bar
    /*
        getMenuInflater().inflate(R.menu.timkiem_khachhang, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        searchView = (SearchView) itemSearch.getActionView();
        //set OnQueryTextListener cho search view để thực hiện search theo text
        searchView.setOnQueryTextListener(this);
        */
        return true;

    }

}
