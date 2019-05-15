package tiwaco.thefirstapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import tiwaco.thefirstapp.CustomAdapter.CustomListDuongThuAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListThu2Adapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListThuAdapter;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.Database.SPData;

/**
 * Created by TUTRAN on 17/04/2017.
 */

public class ListThuActivity extends AppCompatActivity {

    ListView listviewKH ;
    List<KhachHangThuDTO> liskh;
    KhachHangThuDAO khachhangDAO = null;
    RecyclerView recyclerView;
    CustomListDuongThuAdapter adapter;
    List<DuongThuDTO> listduong;
    DuongThuDAO duongDAO;
    Context con;
    ActionBar bar;
    List<KhachHangThuDTO> liskhdao;
    TextView txtduongchon, txtTiltle, txt_title_dskh, txt_title_hd;
    int vitri = 0;
    String title ="";
    SPData spdata;
    Spinner spinTTGhi;
    EditText locSTT;
    ProgressDialog dialogdoi;
    ThanhToanDAO thanhtoandao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.listkh_fragment);
        super.onCreate(savedInstanceState);


        con = ListThuActivity.this;

        bar = getSupportActionBar();
        bar.setTitle(getString(R.string.tab_dsKHThu));
        bar.setLogo(R.mipmap.ic_logo_tiwaco);

        listviewKH = (ListView) findViewById(R.id.lv_khachhang);
        txtduongchon = (TextView) findViewById(R.id.txt_maduongchon);
        txtTiltle =(TextView) findViewById(R.id.txt_title_dskh1);

        txt_title_dskh =  (TextView) findViewById(R.id.txt_title_dskh);
        txt_title_hd = (TextView) findViewById(R.id.txt_sohd);
        locSTT = (EditText) findViewById(R.id.txt_title_STT);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_listduong);
        spinTTGhi = (Spinner) findViewById(R.id.spin_tinhtrangghi);
        spinTTGhi.setSelected(true);
        spdata= new SPData(con);
        duongDAO = new DuongThuDAO(con);
        khachhangDAO = new KhachHangThuDAO(con);
        thanhtoandao = new ThanhToanDAO(con);
        listduong = duongDAO.getAllDuong();
        dialogdoi = new ProgressDialog(con, ProgressDialog.STYLE_SPINNER);
        dialogdoi.setMessage("Đang tập hợp dữ liệu...");
        dialogdoi.setCanceledOnTouchOutside(false);
        Log.e("soluongkh", String.valueOf(khachhangDAO.countKhachHangAll()));

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBTReceiver, filter);
        adapter = new CustomListDuongThuAdapter(con, listduong, listviewKH, txtduongchon, recyclerView, txtTiltle, txt_title_hd);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //  Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
        layoutManager.scrollToPositionWithOffset(spdata.getDataIndexDuongDangThuTrongSP() , 0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        String SPduongdangthu = spdata.getDataDuongDangThuTrongSP();

        if (!SPduongdangthu.equals("")) {
            Bien.ma_duong_dang_chon_thu = SPduongdangthu.trim();
        }else {
            Bien.ma_duong_dang_chon_thu = listduong.get(spdata.getDataIndexDuongDangThuTrongSP()).getMaDuong().trim();
        }
        Log.e("ma duong dang chon thu", Bien.ma_duong_dang_chon_thu);

        txt_title_dskh.setText("Danh sách thu");
        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu.trim());

        title =  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);


        Bien.adapterKHThu = new CustomListThu2Adapter(con, liskhdao, spdata.getDataIndexDuongDangThuTrongSP());
        listviewKH.setAdapter(Bien.adapterKHThu);
        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu)) -1;
        listviewKH.setSelection(Bien.bien_index_khachhang_thu - Integer.parseInt(khachhangDAO.getSTTnhoNhat(Bien.ma_duong_dang_chon_thu)));


        Log.e("select duong---listactivity", String.valueOf(Bien.selected_item_thu));
        //   setview();
        loadDataDuong();


        locSTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!locSTT.getText().toString().equals("")) {

                    KhachHangThuDTO khchuyen = khachhangDAO.getKHTheoDanhBoSTTDuong(Bien.ma_duong_dang_chon_thu, locSTT.getText().toString().trim());
                    if(khchuyen!=null) {
                        Log.e("chuyendenstt",locSTT.getText().toString()+": codulieu");
                        int stt = Integer.parseInt(khchuyen.getSTT().toString());
                        int thutuchuyen =0;
                        for(int t =0;t<Bien.listKH_thu.size();t++){
                            Log.e("stt tim + size",Bien.listKH_thu.get(t).getSTT() +" " +Bien.listKH_thu.size());
                            if(Bien.listKH_thu.get(t).getSTT().equals(khchuyen.getSTT()))
                            {
                                thutuchuyen = t;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(stt) +" "+Bien.bienSoLuongKH);
                        if (stt > 0 && stt <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen);
                        }
                    }
                }
                else {
                    int chuaghinhonhat = Integer.parseInt(khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon_thu));
                    int sttnhonhat = Integer.parseInt(khachhangDAO.getSTTnhoNhat(Bien.ma_duong_dang_chon_thu));
                    listviewKH.setSelection(chuaghinhonhat - sttnhonhat - 1);
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
            Bien.ma_duong_dang_chon_thu = txtduongchon.getText().toString().trim();
        }
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_ghinuoc:
                int vitriduong = 0;
                for(int  i  = 0; i<listduong.size();i++){
                    if(listduong.get(i).getMaDuong().equals(Bien.ma_duong_dang_chon_thu)){
                        vitriduong = i;
                    }
                }
                final int finalVitriduong = vitriduong;
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.tab_thunuoc))
                        .setMessage("Bạn có muốn thu tiền nước đường " + Bien.ma_duong_dang_chon_thu + " " + getString(R.string.list_chuyendulieu_hoighinuoc2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListThuActivity.this, MainThu2Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Bien.STTTHU, khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                                bundle.putString(Bien.MADUONGTHU, Bien.ma_duong_dang_chon_thu);
                                bundle.putInt(Bien.VITRITHU,finalVitriduong);
                                bundle.putString(Bien.MAKHTHU,"");
                                //Log.e("LISTACTIVITY_vitriduongghi", String.valueOf(finalVitriduong));
                                intent.putExtra(Bien.GOITIN_MADUONGTHU, bundle);
                                //Log.e("gui bundle maduong", Bien.ma_duong_dang_chon_thu);
                                // Log.e("gui bundle sott",khachhangDAO.getSTTChuaGhiNhoNhat(Bien.ma_duong_dang_chon_thu));

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
        if(adapter != null &&  Bien.adapterKHThu !=null ) {

            //  Log.e("RESUME LIST","OK");
            //   setview();
            listduong = duongDAO.getAllDuong();
            adapter.setData(listduong);
            adapter.notifyDataSetChanged();
            // liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu);
            //  Bien.adapterKHThu.setData(liskhdao);
            //  Bien.adapterKHThu.notifyDataSetChanged();
            switch (Bien.bientrangthaithu){


                case 0:
                    liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);
                    // Log.e("ma duong ", Bien.ma_duong_dang_chon_thu);
                    String sohd = thanhtoandao.getSoHDTheoMaDuongPhanLoai(0, Bien.ma_duong_dang_chon_thu);
                    String tongcong = thanhtoandao.getSoTienTheoMaDuongPhanLoai(0, Bien.ma_duong_dang_chon_thu);

                    txt_title_hd.setText("Số HD: " + sohd + " - Số tiền: " + tongcong);
                    // Log.e("Số tiền đã thu:", String.valueOf(tongcong));
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    //Log.e(" Bien.bien_index_khachhang_thu", String.valueOf(Bien.bien_index_khachhang_thu));
                    int thutuchuyen_onre = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        // Log.e("stt tim + size -onresume", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());

                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen_onre = t;
                            Log.e("thutu1", String.valueOf(thutuchuyen_onre));
                        }

                        }
                    // Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen_onre - 1);
                    }


                    break;
                case 1:
                    liskhdao = khachhangDAO.getAllKHDaThuTheoDuong(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);
                    //Log.e("Bien.ma_duong_dang_chon_thu", Bien.ma_duong_dang_chon_thu);
                    String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(1, Bien.ma_duong_dang_chon_thu);
                    String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(1, Bien.ma_duong_dang_chon_thu);

                    txt_title_hd.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));

                    listviewKH.setSelection(0);

                    break;
                case 2:
                    dialogdoi.show();
                    liskhdao = khachhangDAO.getAllKHChuaThuTheoDuong(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);
                    String sohd2 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(2, Bien.ma_duong_dang_chon_thu);
                    String tongcong2 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(2, Bien.ma_duong_dang_chon_thu);

                    txt_title_hd.setText("Số HD: " + sohd2 + " - Số tiền: " + tongcong2);
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();

                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    Bien.listKH_thu = liskhdao;
                    int thutuchuyen1 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen1 = t - 1;
                        }
                        //    Log.e("thutu", String.valueOf(thutuchuyen1));
                        }
                    // Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen1 - 1);
                    }
                    dialogdoi.dismiss();
                    break;
                case 3:
                    String thoigian1 = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                    liskhdao = khachhangDAO.getAllKHDaThuHomNay(Bien.ma_duong_dang_chon_thu, thoigian1);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);
                    String sohd3 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(3, Bien.ma_duong_dang_chon_thu);
                    String tongcong3 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(3, Bien.ma_duong_dang_chon_thu);

                    txt_title_hd.setText("Số HD: " + sohd3 + " - Số tiền: " + tongcong3);

                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));

                    int thutuchuyen2 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen2 = t - 1;
                        }
                        //      Log.e("thutu", String.valueOf(thutuchuyen2));
                        }
                    //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen2 - 1);
                    }

                    break;


                case 4: //ghi chu

                    liskhdao = khachhangDAO.getAllKHGhiChuThu(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);

                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        }
                    txt_title_hd.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    int thutuchuyen3 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen3 = t - 1;
                        }
                        Log.e("thutu", String.valueOf(thutuchuyen3));
                    }
                    //    Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen3 - 1);
                    }


                    break;
                case 5: //có nợ

                    liskhdao = khachhangDAO.getAllKHCoNoTheoDuong(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);

                    int tongcong5 = 0;
                    int sohd5 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd5 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong5 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    txt_title_hd.setText("Số HD: " + sohd5 + " - Số tiền: " + thanhtoandao.formatTien(tongcong5));
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    int thutuchuyen5 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        //     Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen5 = t - 1;
                        }
                        //       Log.e("thutu", String.valueOf(thutuchuyen5));
                    }
                    //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen5 - 1);
                    }


                    break;

                case 6: //có nợ

                    liskhdao = khachhangDAO.getAllKHTamThuChuaCapNhat(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);

                    int tongcong6 = 0;
                    int sohd6 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd6 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong6 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    txt_title_hd.setText("Số HD: " + sohd6 + " - Số tiền: " + thanhtoandao.formatTien(tongcong6));
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    int thutuchuyen6 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        //     Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen6 = t - 1;
                        }
                        //       Log.e("thutu", String.valueOf(thutuchuyen5));
                    }
                    //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen6 - 1);
                    }


                    break;
                case 7: //có nợ

                    liskhdao = khachhangDAO.getAllKHTamThuDaCapNhat(Bien.ma_duong_dang_chon_thu);
                    title = String.valueOf(liskhdao.size()) + " KH";
                    txtTiltle.setText(title);

                    int tongcong7 = 0;
                    int sohd7 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd7 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong7 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    txt_title_hd.setText("Số HD: " + sohd7 + " - Số tiền: " + thanhtoandao.formatTien(tongcong7));
                    Bien.adapterKHThu.setData(liskhdao);
                    Bien.adapterKHThu.notifyDataSetChanged();
                    Bien.listKH_thu = liskhdao;
                    Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                    int thutuchuyen7 = 0;
                    for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                        //     Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                        if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                            thutuchuyen7 = t - 1;
                        }
                        //       Log.e("thutu", String.valueOf(thutuchuyen5));
                    }
                    //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                    if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                        listviewKH.setSelection(thutuchuyen7 - 1);
                    }


                    break;
            }
        }


        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBTReceiver);
    }
    public void setview(){


        listduong = duongDAO.getAllDuong();


        adapter = new CustomListDuongThuAdapter(con, listduong, listviewKH, txtduongchon, recyclerView, txtTiltle, txt_title_hd);

        LinearLayoutManager layoutManager = new LinearLayoutManager(con);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //  Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
        layoutManager.scrollToPositionWithOffset(spdata.getDataIndexDuongDangThuTrongSP() , 0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        String SPduongdangghi  = spdata.getDataDuongDangThuTrongSP();
        if(!SPduongdangghi.equals("")){
            Bien.ma_duong_dang_chon_thu = SPduongdangghi;
        }else {
            Bien.ma_duong_dang_chon_thu = listduong.get(spdata.getDataIndexDuongDangThuTrongSP()).getMaDuong();
        }


        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu);

        title =  String.valueOf(liskhdao.size()) +" khách hàng";
        txtTiltle.setText(title);
        if(Bien.adapterKHThu == null) {
            Bien.adapterKHThu = new CustomListThu2Adapter(con, liskhdao, spdata.getDataIndexDuongDangThuTrongSP());
        }
        listviewKH.setAdapter(Bien.adapterKHThu);
        Bien.bien_index_khachhang = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu)) -1;
        listviewKH.setSelection( Bien.bien_index_khachhang);


        Log.e("select duong---listactivity", String.valueOf(Bien.selected_item_thu));
    }

    private void loadDataDuong(){
        ArrayList<String> listTTGhi  = new ArrayList<>();
        listTTGhi.add("Tất cả");
        listTTGhi.add("Đã thu");
        listTTGhi.add("Chưa thu");
        listTTGhi.add("Đã thu hôm nay");
        listTTGhi.add("Ghi chú");
        listTTGhi.add("Có HĐ nợ");
        listTTGhi.add("Tạm thu - Chưa cập nhật");
        listTTGhi.add("Tạm thu - Đã cập nhật");
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
                Bien.bientrangthaithu = position;
                switch (position){
                    case 0:
                        liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Log.e("ma duong ", Bien.ma_duong_dang_chon_thu);
                        String sohd = thanhtoandao.getSoHDTheoMaDuongPhanLoai(0, Bien.ma_duong_dang_chon_thu);
                        String tongcong = thanhtoandao.getSoTienTheoMaDuongPhanLoai(0, Bien.ma_duong_dang_chon_thu);

                        txt_title_hd.setText("Số HD: " + sohd + " - Số tiền: " + tongcong);
                        Log.e("Số tiền đã thu:", String.valueOf(tongcong));
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        Log.e(" Bien.bien_index_khachhang_thu", String.valueOf(Bien.bien_index_khachhang_thu));
                        int thutuchuyen_onre = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            Log.e("stt tim + size -onresume", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());

                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen_onre = t;
                                Log.e("thutu1", String.valueOf(thutuchuyen_onre));
                            }

                        }
                        Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen_onre - 1);
                        }


                        break;
                    case 1:
                        liskhdao = khachhangDAO.getAllKHDaThuTheoDuong(Bien.ma_duong_dang_chon_thu);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        Log.e("Bien.ma_duong_dang_chon_thu", Bien.ma_duong_dang_chon_thu);
                        String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(1, Bien.ma_duong_dang_chon_thu);
                        String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(1, Bien.ma_duong_dang_chon_thu);

                        txt_title_hd.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));

                        listviewKH.setSelection(0);

                        break;
                    case 2:
                        dialogdoi.show();
                        liskhdao = khachhangDAO.getAllKHChuaThuTheoDuong(Bien.ma_duong_dang_chon_thu);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        String sohd2 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(2, Bien.ma_duong_dang_chon_thu);
                        String tongcong2 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(2, Bien.ma_duong_dang_chon_thu);

                        txt_title_hd.setText("Số HD: " + sohd2 + " - Số tiền: " + tongcong2);
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();

                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        Bien.listKH_thu = liskhdao;
                        int thutuchuyen1 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen1 = t - 1;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen1));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen1 - 1);
                        }
                        dialogdoi.dismiss();
                        break;
                    case 3:
                        String thoigian1 = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                        liskhdao = khachhangDAO.getAllKHDaThuHomNay(Bien.ma_duong_dang_chon_thu,thoigian1);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);
                        String sohd3 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(3, Bien.ma_duong_dang_chon_thu);
                        String tongcong3 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(3, Bien.ma_duong_dang_chon_thu);

                        txt_title_hd.setText("Số HD: " + sohd3 + " - Số tiền: " + tongcong3);

                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));

                        int thutuchuyen2 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen2 = t - 1;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen2));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen2 - 1);
                        }

                        break;


                    case 4: //ghi chu

                        liskhdao = khachhangDAO.getAllKHGhiChuThu(Bien.ma_duong_dang_chon_thu);
                        title =  String.valueOf(liskhdao.size()) +" KH";
                        txtTiltle.setText(title);

                        int tongcong4 = 0;
                        int sohd4 = 0;
                        for (int i = 0; i < liskhdao.size(); i++) {
                            sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                            tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        }
                        txt_title_hd.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        int thutuchuyen3 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen3 = t - 1;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen3));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen3 - 1);
                        }


                        break;
                    case 5: //có nợ

                        liskhdao = khachhangDAO.getAllKHCoNoTheoDuong(Bien.ma_duong_dang_chon_thu);
                        title = String.valueOf(liskhdao.size()) + " KH";
                        txtTiltle.setText(title);

                        int tongcong5 = 0;
                        int sohd5 = 0;
                        for (int i = 0; i < liskhdao.size(); i++) {
                            sohd5 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                            tongcong5 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        }
                        txt_title_hd.setText("Số HD: " + sohd5 + " - Số tiền: " + thanhtoandao.formatTien(tongcong5));
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        int thutuchuyen5 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen5 = t - 1;
                            }
                            Log.e("thutu", String.valueOf(thutuchuyen5));
                        }
                        Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen5 - 1);
                        }


                        break;
                    case 6: //tam thu - chưa cap nhat

                        liskhdao = khachhangDAO.getAllKHTamThuChuaCapNhat(Bien.ma_duong_dang_chon_thu);
                        title = String.valueOf(liskhdao.size()) + " KH";
                        txtTiltle.setText(title);

                        int tongcong6 = 0;
                        int sohd6 = 0;
                        for (int i = 0; i < liskhdao.size(); i++) {
                            sohd6 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                            tongcong6 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        }
                        txt_title_hd.setText("Số HD: " + sohd6 + " - Số tiền: " + thanhtoandao.formatTien(tongcong6));
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        int thutuchuyen6 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            //     Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen6 = t - 1;
                            }
                            //       Log.e("thutu", String.valueOf(thutuchuyen5));
                        }
                        //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen6 - 1);
                        }


                        break;
                    case 7: //có nợ

                        liskhdao = khachhangDAO.getAllKHTamThuDaCapNhat(Bien.ma_duong_dang_chon_thu);
                        title = String.valueOf(liskhdao.size()) + " KH";
                        txtTiltle.setText(title);

                        int tongcong7 = 0;
                        int sohd7 = 0;
                        for (int i = 0; i < liskhdao.size(); i++) {
                            sohd7 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                            tongcong7 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        }
                        txt_title_hd.setText("Số HD: " + sohd7 + " - Số tiền: " + thanhtoandao.formatTien(tongcong7));
                        Bien.adapterKHThu.setData(liskhdao);
                        Bien.adapterKHThu.notifyDataSetChanged();
                        Bien.listKH_thu = liskhdao;
                        Bien.bien_index_khachhang_thu = Integer.parseInt(khachhangDAO.getSTTChuaThuNhoNhat(Bien.ma_duong_dang_chon_thu));
                        int thutuchuyen7 = 0;
                        for (int t = 0; t < Bien.listKH_thu.size(); t++) {
                            //     Log.e("stt tim + size", Bien.listKH_thu.get(t).getSTT() + " " + Bien.listKH_thu.size());
                            if (Bien.listKH_thu.get(t).getSTT().equals(String.valueOf(Bien.bien_index_khachhang_thu))) {
                                thutuchuyen7 = t - 1;
                            }
                            //       Log.e("thutu", String.valueOf(thutuchuyen5));
                        }
                        //  Log.e("chuyendenstt-stt", String.valueOf(Bien.bien_index_khachhang_thu) + " " + Bien.bienSoLuongKH);
                        if (Bien.bien_index_khachhang_thu > 0 && Bien.bien_index_khachhang_thu <= Integer.parseInt(khachhangDAO.getSTTLonNhat(Bien.ma_duong_dang_chon_thu))) {
                            listviewKH.setSelection(thutuchuyen7 - 1);
                        }


                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                liskhdao = khachhangDAO.getAllKHTheoDuong(Bien.ma_duong_dang_chon_thu);
                Bien.adapterKHThu.setData(liskhdao);
                Bien.adapterKHThu.notifyDataSetChanged();
                Bien.listKH_thu = liskhdao;
            }
        });


    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                if (Bien.btsocket != null) {

                    try {

                        if (Bien.btsocket != null) {
                            Toast.makeText(ListThuActivity.this, "Đóng kết nối...", Toast.LENGTH_LONG).show();
                            Bien.btoutputstream.close();
                            Bien.btsocket.getOutputStream().close();
                            Bien.btsocket.close();
                            Bien.btsocket = null;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListThuActivity.this);
                            // khởi tạo dialog

                            alertDialogBuilder.setMessage("Kết nối với máy in thất bại.Hãy kiểm tra lại máy in đã mở chưa.");

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  new UpdateThongTinThuNuoc().execute(urlstr);
                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                            // hiển thị dialog
                        }
                    } catch (Exception ez) {
                        ez.printStackTrace();
                    }


                }

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                try {
                    if (Bien.btsocket != null) {
                        Toast.makeText(ListThuActivity.this, "Đóng kết nối...", Toast.LENGTH_LONG).show();
                        Bien.btoutputstream.close();
                        Bien.btsocket.getOutputStream().close();
                        Bien.btsocket.close();
                        Bien.btsocket = null;
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListThuActivity.this);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage("Kết nối với máy in thất bại.Hãy kiểm tra lại máy in đã mở chưa.");

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  new UpdateThongTinThuNuoc().execute(urlstr);
                                dialog.dismiss();
                            }
                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        // hiển thị dialog
                    }
                } catch (Exception ez) {
                    ez.printStackTrace();
                }

            }
        }
    };

}
