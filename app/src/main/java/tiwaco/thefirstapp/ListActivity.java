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
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    Spinner spinTTGhi;
    EditText locSTT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.listkh_fragment);
        super.onCreate(savedInstanceState);


        con = ListActivity.this;

        bar = getSupportActionBar();
        bar.setTitle(getString(R.string.tab_dsKH));
        bar.setLogo(R.mipmap.ic_logo_tiwaco);

        listviewKH = (ListView) findViewById(R.id.lv_khachhang);
        txtduongchon = (TextView) findViewById(R.id.txt_maduongchon);
        txtTiltle =(TextView) findViewById(R.id.txt_title_dskh1);
        locSTT = (EditText) findViewById(R.id.txt_title_STT);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_listduong);
        spinTTGhi = (Spinner) findViewById(R.id.spin_tinhtrangghi);
        spinTTGhi.setSelected(true);
        spdata= new SPData(con);
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);
        listduong = duongDAO.getAllDuong();


        adapter = new CustomListDuongAdapter(con,listduong,listviewKH,txtduongchon,recyclerView,txtTiltle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //  Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
        layoutManager.scrollToPositionWithOffset(spdata.getDataIndexDuongDangGhiTrongSP() , 0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP();
        if(!SPduongdangghi.equals("")){
            Bien.ma_duong_dang_chon = SPduongdangghi;
        }else {
            Bien.ma_duong_dang_chon = listduong.get(spdata.getDataIndexDuongDangGhiTrongSP()).getMaDuong();
        }


        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);

        title =  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);
        Bien.adapterKH = new CustomListAdapter(con, liskhdao, spdata.getDataIndexDuongDangGhiTrongSP());
        listviewKH.setAdapter(Bien.adapterKH);
        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
        listviewKH.setSelection( Bien.bien_index_khachhang);


        Log.e("select duong---listactivity", String.valueOf(Bien.selected_item));
     //   setview();
        loadDataDuong();


        locSTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!locSTT.getText().toString().equals("")) {

                    KhachHangDTO khchuyen = khachhangDAO.getKHTheoDanhBoSTTDuong(Bien.ma_duong_dang_chon,locSTT.getText().toString());
                    if(khchuyen!=null) {
                        Log.e("chuyendenstt",locSTT.getText().toString()+": codulieu");
                        int stt = Integer.parseInt(khchuyen.getSTT().toString()) - 1;
                        int thutuchuyen =0;
                        for(int t =0;t<Bien.listKH.size();t++){
                            Log.e("stt tim + size",Bien.listKH.get(t).getSTT() +" " +Bien.listKH.size());
                            if(Bien.listKH.get(t).getSTT().equals(khchuyen.getSTT()))
                            {
                                thutuchuyen = t;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(stt) +" "+Bien.bienSoLuongKH);
                        if (stt > 0 && stt <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon))) {
                            listviewKH.setSelection(thutuchuyen);
                        }
                    }
                }
                else {
                    listviewKH.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                                bundle.putString(Bien.MAKH,"");
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
    @Override
    protected void onResume() {

        //adapter duong
        if(adapter != null &&  Bien.adapterKH !=null ) {

            Log.e("RESUME LIST","OK");
         //   setview();
            listduong = duongDAO.getAllDuong();
            adapter.setData(listduong);
            adapter.notifyDataSetChanged();
           // liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);
          //  Bien.adapterKH.setData(liskhdao);
          //  Bien.adapterKH.notifyDataSetChanged();
            switch (Bien.bientrangthaighi){
                case 0:
                    liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 1:
                    liskhdao = khachhangDAO.getAllKHDaGhiTheoDuong(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.listKH = liskhdao;
                    break;
                case 2:
                    liskhdao = khachhangDAO.getAllKHChuaGhiTheoDuong(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 3:
                    String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                    liskhdao = khachhangDAO.getAllKHDaGhiHomNay(Bien.ma_duong_dang_chon,thoigian1);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 4:
                    liskhdao = khachhangDAO.getAllKHDaGhiBatThuong(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 5: //ghi chu

                    liskhdao = khachhangDAO.getAllKHKhongGhiDuoc(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 6:

                    liskhdao = khachhangDAO.getAllKHChuyenLoai(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
                case 7: //ghi chu

                    liskhdao = khachhangDAO.getAllKHGhiChu(Bien.ma_duong_dang_chon);
                    title =  String.valueOf(liskhdao.size()) +" KH";
                    txtTiltle.setText(title);
                    Bien.adapterKH.setData(liskhdao);
                    Bien.adapterKH.notifyDataSetChanged();
                    Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                    listviewKH.setSelection( Bien.bien_index_khachhang);
                    Bien.listKH = liskhdao;
                    break;
            }
        }

        super.onResume();
    }
    public void setview(){


        listduong = duongDAO.getAllDuong();


        adapter = new CustomListDuongAdapter(con,listduong,listviewKH,txtduongchon,recyclerView,txtTiltle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //  Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
        layoutManager.scrollToPositionWithOffset(spdata.getDataIndexDuongDangGhiTrongSP() , 0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP();
        if(!SPduongdangghi.equals("")){
            Bien.ma_duong_dang_chon = SPduongdangghi;
        }else {
            Bien.ma_duong_dang_chon = listduong.get(spdata.getDataIndexDuongDangGhiTrongSP()).getMaDuong();
        }


        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);

        title =  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);
        if(Bien.adapterKH == null) {
            Bien.adapterKH = new CustomListAdapter(con, liskhdao, spdata.getDataIndexDuongDangGhiTrongSP());
        }
        listviewKH.setAdapter(Bien.adapterKH);
        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
        listviewKH.setSelection( Bien.bien_index_khachhang);


        Log.e("select duong---listactivity", String.valueOf(Bien.selected_item));
    }

    private void loadDataDuong(){
        ArrayList<String> listTTGhi  = new ArrayList<>();
        listTTGhi.add("Tất cả");
        listTTGhi.add("Đã ghi");
        listTTGhi.add("Chưa ghi");
        listTTGhi.add("Đã ghi hôm nay");
        listTTGhi.add("Bất thường");
        listTTGhi.add("Không ghi được");
        listTTGhi.add("Chuyển loại");
        listTTGhi.add("Ghi chú");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        listTTGhi
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinTTGhi.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinTTGhi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bien.bientrangthaighi = position;
                switch (position){
                    case 0:
                        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;

                        break;
                    case 1:
                        liskhdao = khachhangDAO.getAllKHDaGhiTheoDuong(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.listKH = liskhdao;
                        break;
                    case 2:
                        liskhdao = khachhangDAO.getAllKHChuaGhiTheoDuong(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                    case 3:
                        String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                        liskhdao = khachhangDAO.getAllKHDaGhiHomNay(Bien.ma_duong_dang_chon,thoigian1);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                    case 4:

                        liskhdao = khachhangDAO.getAllKHDaGhiBatThuong(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                    case 5: //ghi chu

                        liskhdao = khachhangDAO.getAllKHKhongGhiDuoc(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                    case 6:

                        liskhdao = khachhangDAO.getAllKHChuyenLoai(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                    case 7: //ghi chu

                        liskhdao = khachhangDAO.getAllKHGhiChu(Bien.ma_duong_dang_chon);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Bien.adapterKH.setData(liskhdao);
                        Bien.adapterKH.notifyDataSetChanged();
                        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon)) -1;
                        listviewKH.setSelection( Bien.bien_index_khachhang);
                        Bien.listKH = liskhdao;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon);
                Bien.adapterKH.setData(liskhdao);
                Bien.adapterKH.notifyDataSetChanged();
                Bien.listKH = liskhdao;
            }
        });


    }
}
