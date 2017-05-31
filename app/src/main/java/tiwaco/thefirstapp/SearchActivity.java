package tiwaco.thefirstapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomTimKiem;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;



public class SearchActivity  extends AppCompatActivity  {
    private ListView listview;
    CustomListAdapter adapter;
    CustomTimKiem adapterTK;
    Context con;
    DuongDAO duongdao ;
    ArrayList<String> arrDuong;
    List<KhachHangDTO> listkhachhang =null;
    KhachHangDAO khachhangdao;
    List<DuongDTO> listduong;
    ImageButton btnBack, btnEye , btnSearch;
    Spinner spinDuong;
    CheckBox ckDanhBo, ckHoten, ckDienThoai;
    EditText khungtimkiem ;
    LinearLayout layout_chitieu;
    String maduong ="";
    int vitriduong =-1;
    Boolean flagEye  = false;
    ExpandableListView epdlistdata;
    HashMap<String, List<KhachHangDTO>> mDataTimKiem;
    TextView khongtimthay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        con = SearchActivity.this;
        getSupportActionBar().hide();
        listkhachhang = new ArrayList<KhachHangDTO>();
        khachhangdao  = new KhachHangDAO(con);
        listduong = new ArrayList<>();

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

    @Override
    protected void onResume() {

        if(adapterTK != null) {
            timkiem();
            adapterTK.notifyDataSetChanged();
        }
        if(adapter !=null) {
            timkiem();
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private void timkiem() {
        String sqlstringselect = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +"  ";
        String dieukien ="";
        String stringtim = khungtimkiem.getText().toString().trim();
        String stringdanhbo = "";
        String stringmaduong = "";
        String stringhoten = "";
        String stringdienthoai = "";
        String or1 = "";
        String or2 ="";
        hideKeyboard(SearchActivity.this);
        if(!flagEye){
            vitriduong = -1;
            if(stringtim.equals("")){
                dieukien ="";
            }
            else {
                dieukien = " WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_DANHBO + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKH_TENKH + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI + " = '" + stringtim + "' ";
            }
            final List<DuongDTO> listduongtheoDK = khachhangdao.getListDuongTheoDK(con,dieukien);
            mDataTimKiem = new HashMap<>();
            for(int  i =0;i<listduongtheoDK.size();i++){
                String maduong = listduongtheoDK.get(i).getMaDuong().trim();
                String dieukien2 =" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG + " = '" + maduong + "' and (" +
                        MyDatabaseHelper.KEY_DANHSACHKH_DANHBO + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKH_TENKH + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI + " = '" + stringtim + "' )";
                List<KhachHangDTO> listkh = khachhangdao.TimKiemTheoSQL(sqlstringselect+dieukien2);
                mDataTimKiem.put(maduong,listkh);
            }
            Log.e("kich thuoc hashmap", String.valueOf(mDataTimKiem.size()));
            if(mDataTimKiem.size() >0) {
                adapterTK = new CustomTimKiem(con, listduongtheoDK, mDataTimKiem, -1);
                epdlistdata.setAdapter(adapterTK);
                listview.setVisibility(View.GONE);
                epdlistdata.setVisibility(View.VISIBLE);
              //  for (int i = 0; i < adapterTK.getGroupCount(); i++) {
               //     epdlistdata.expandGroup(i);
               // }
                khongtimthay.setVisibility(View.GONE);
            }
            else{
                khongtimthay.setVisibility(View.VISIBLE);
                epdlistdata.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
            }
        }
        else{

                stringmaduong = " WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +" = '"+maduong+"' ";

                if(ckDanhBo.isChecked()){
                    if(stringtim.equals("")){
                        stringdanhbo ="";
                    }
                    else {
                        stringdanhbo = " " + MyDatabaseHelper.KEY_DANHSACHKH_DANHBO + " = '" + stringtim + "'";
                    }
                }else{
                    stringdanhbo ="";
                }

                if(ckDienThoai.isChecked()){
                    if(stringtim.equals("")){
                        stringdienthoai ="";
                    }else {
                        stringdienthoai = " " + MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI + " = '" + stringtim + "' ";
                    }

                }else{
                    stringdienthoai ="";
                }

                if(ckHoten.isChecked()){
                    if(stringtim.equals("")){
                        stringdienthoai ="";
                    }else {
                        stringhoten = " " + MyDatabaseHelper.KEY_DANHSACHKH_TENKH + " LIKE '%" + stringtim + "%'";
                    }
                }else{
                    stringhoten ="";
                }

                if(!ckDanhBo.isChecked() &&!ckDienThoai.isChecked() &&!ckHoten.isChecked() )
                {
                    dieukien = stringmaduong;
                }
                else{

                    if(stringdanhbo.equals("")){
                        or1 ="";
                    }
                    else if(!stringdanhbo.equals("") && stringhoten.equals("") && stringdienthoai.equals("") )
                    {
                        or1 = "";
                    }
                    else{
                        or1= " or ";
                    }
                    Log.d("or1",or1);
                    if(stringdienthoai.equals("")){
                        or2 ="";
                    }
                    else if (!stringdienthoai.equals("") && stringdanhbo.equals("") && stringhoten.equals("")){

                        or2= "";
                    }
                    else {
                        or2 = " or ";
                    }
                    Log.d("or2",or2);
                    dieukien = stringmaduong + " and ( " + stringdanhbo + or1 +   stringhoten + or2 + stringdienthoai+" )";
                    Log.d("dieukiensearch",dieukien);
                }


                String sqlstr = sqlstringselect + dieukien;
                listkhachhang = khachhangdao.TimKiemTheoSQL(sqlstr);
                 if(listkhachhang.size() >0) {
                     adapter = new CustomListAdapter(con, listkhachhang, vitriduong);
                     listview.setAdapter(adapter);
                     listview.setVisibility(View.VISIBLE);
                     epdlistdata.setVisibility(View.GONE);
                     khongtimthay.setVisibility(View.GONE);
                 }
                 else{

                     khongtimthay.setVisibility(View.VISIBLE);
                     epdlistdata.setVisibility(View.GONE);
                     listview.setVisibility(View.GONE);
                 }
                epdlistdata.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return  epdlistdata.isGroupExpanded(groupPosition) ? epdlistdata.collapseGroup(groupPosition) : epdlistdata.expandGroup(groupPosition);
                }
            });

        }





    }
    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        khungtimkiem = (EditText) findViewById(R.id.edit_khungtimkiem);
        layout_chitieu =(LinearLayout) findViewById(R.id.layout_khungchitieu);
        epdlistdata = (ExpandableListView) findViewById(R.id.eplData);
        khongtimthay = (TextView) findViewById(R.id.tv_khongtimthay);
        epdlistdata.setVisibility(View.GONE);
        layout_chitieu.setVisibility(View.GONE);
        khongtimthay.setVisibility(View.GONE);
    }
    public int dp2px(float dp) {
        // Get the screen's density scale
        final float density = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * density + 0.5f);
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
                Log.e("Vi tri duong", String.valueOf(vitriduong));
                Log.e("Ma duong", maduong);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vitriduong = 0;
                maduong = arrDuong.get(0).substring(0,2);
                Log.e("Vi tri duong", String.valueOf(vitriduong));
                Log.e("Ma duong", maduong);
            }
        });


    }



}
