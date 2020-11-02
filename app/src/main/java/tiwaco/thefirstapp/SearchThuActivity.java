package tiwaco.thefirstapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListThu2Adapter;
import tiwaco.thefirstapp.CustomAdapter.CustomThuTimKiem;
import tiwaco.thefirstapp.CustomAdapter.CustomTimKiem;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;


public class SearchThuActivity extends AppCompatActivity {
    private ListView listview;
    CustomListThu2Adapter adapter;
    CustomThuTimKiem adapterTK;
    Context con;
    DuongThuDAO duongdao;
    ArrayList<String> arrDuong;
    List<KhachHangThuDTO> listkhachhang = null;
    KhachHangThuDAO khachhangthudao;
    List<DuongThuDTO> listduong;
    ImageButton btnBack, btnEye, btnSearch;
    Spinner spinDuong;
    CheckBox ckDanhBo, ckHoten, ckDienThoai, ckMaTLK, ckDiaChi;
    EditText khungtimkiem;
    LinearLayout layout_chitieu;
    String maduong = "";
    int vitriduong = -1;
    Boolean flagEye = false;
    ExpandableListView epdlistdata;
    HashMap<String, List<KhachHangThuDTO>> mDataTimKiem;
    TextView khongtimthay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        con = SearchThuActivity.this;
        getSupportActionBar().hide();
        listkhachhang = new ArrayList<KhachHangThuDTO>();
        khachhangthudao = new KhachHangThuDAO(con);
        listduong = new ArrayList<>();

        arrDuong = new ArrayList<>();
        duongdao = new DuongThuDAO(con);

        taoview();
        loadDataDuong();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEye.setVisibility(View.GONE);
        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagEye) {
                    layout_chitieu.setVisibility(View.GONE);
                    btnEye.setBackgroundResource(R.drawable.ic_eye_red);
                    flagEye = false;
                } else {
                    layout_chitieu.setVisibility(View.VISIBLE);
                    btnEye.setBackgroundResource(R.drawable.ic_eye);
                    flagEye = true;
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timkiem(1);
            }
        });
        //  adapter = new CustomListAdapter(con,listkhachhang,-1);

        //   listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {

        if (adapterTK != null) {
            timkiem(1);
            adapterTK.notifyDataSetChanged();
        }
        if (adapter != null) {
            timkiem(1);
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private void timkiem(int loai) {
        String sqlstringselect = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "  ";
        String dieukien = "";
        String stringtim = khungtimkiem.getText().toString().trim();
        String stringdanhbo = "";
        String stringmaduong = "";
        String stringhoten = "";
        String stringdienthoai = "";
        String stringdiachi = "";
        String stringtlk = "";
        String or1 = "";
        String or2 = "";
        String or3 = "";
        String or4 = "";
        if (loai == 1) {
            hideKeyboard(SearchThuActivity.this);
        }


        if (!flagEye) {
            vitriduong = -1;
            if (stringtim.equals("")) {
                dieukien = "";
            } else {
                dieukien = " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI + " = '" + stringtim + "' ";
            }
            final List<DuongThuDTO> listduongtheoDK = khachhangthudao.getListDuongTheoDK(con, dieukien);
            mDataTimKiem = new HashMap<>();
            for (int i = 0; i < listduongtheoDK.size(); i++) {
                String maduong = listduongtheoDK.get(i).getMaDuong().trim();
                String dieukien2 = " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + " = '" + maduong + "' and (" +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI + " LIKE '%" + stringtim + "%' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK + " = '" + stringtim + "' or " +
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI + " = '" + stringtim + "' )";
                List<KhachHangThuDTO> listkh = khachhangthudao.TimKiemTheoSQL(sqlstringselect + dieukien2);
                mDataTimKiem.put(maduong, listkh);
            }
            Log.e("kich thuoc hashmap", String.valueOf(mDataTimKiem.size()));
            if (mDataTimKiem.size() > 0) {
                adapterTK = new CustomThuTimKiem(con, listduongtheoDK, mDataTimKiem, -1);
                epdlistdata.setAdapter(adapterTK);
                listview.setVisibility(View.GONE);
                epdlistdata.setVisibility(View.VISIBLE);
                //  for (int i = 0; i < adapterTK.getGroupCount(); i++) {
                //     epdlistdata.expandGroup(i);
                // }
                khongtimthay.setVisibility(View.GONE);
            } else {
                khongtimthay.setVisibility(View.VISIBLE);
                epdlistdata.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
            }
        } else {

            stringmaduong = " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + " = '" + maduong + "' ";

            if (ckDanhBo.isChecked()) {

                stringdanhbo = " " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO + " = '" + stringtim + "'";

            } else {
                stringdanhbo = "";
            }

            if (ckDienThoai.isChecked()) {

                stringdienthoai = " " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI + " = '" + stringtim + "' ";


            } else {
                stringdienthoai = "";
            }

            if (ckHoten.isChecked()) {

                stringhoten = " " + MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH + " LIKE '%" + stringtim + "%'";

            } else {
                stringhoten = "";
            }


            if (ckDiaChi.isChecked()) {

                stringdiachi = " " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI + " LIKE '%" + stringtim + "%'";

            } else {
                stringdiachi = "";
            }
            if (ckMaTLK.isChecked()) {

                stringtlk = " " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK + " = '" + stringtim + "'";

            } else {
                stringtlk = "";
            }

            if (!ckDanhBo.isChecked() && !ckDienThoai.isChecked() && !ckHoten.isChecked() && !ckDiaChi.isChecked() && !ckMaTLK.isChecked()) {
                dieukien = stringmaduong;
            } else {

                if (stringdanhbo.equals("")) {
                    or1 = "";
                } else if (!stringdanhbo.equals("") && stringhoten.equals("") && stringdienthoai.equals("") && stringdiachi.equals("") && stringtlk.equals("")) {
                    or1 = "";
                } else {
                    or1 = " or ";
                }
                Log.e("or1", or1);
                if (stringhoten.equals("")) {
                    or2 = "";
                } else if (!stringhoten.equals("") && stringdienthoai.equals("") && stringdiachi.equals("") && stringtlk.equals("")) {

                    or2 = "";
                } else {
                    or2 = " or ";
                }
                Log.e("or2", or2);
                if (stringdienthoai.equals("")) {
                    or3 = "";
                } else if (!stringdienthoai.equals("") && stringdiachi.equals("") && stringtlk.equals("")) {

                    or3 = "";
                } else {
                    or3 = " or ";
                }
                Log.e("or3", or3);
                if (stringdiachi.equals("")) {
                    or4 = "";
                } else if (!stringdiachi.equals("") && stringtlk.equals("")) {

                    or4 = "";
                } else {
                    or4 = " or ";
                }
                Log.e("or4", or4);


                dieukien = stringmaduong + " and ( " + stringdanhbo + or1 + stringhoten + or2 + stringdienthoai + or3 + stringdiachi + or4 + stringtlk + " )";
                Log.e("dieukiensearch", dieukien);
            }


            String sqlstr = sqlstringselect + dieukien;
            listkhachhang = khachhangthudao.TimKiemTheoSQL(sqlstr);
            if (listkhachhang.size() > 0) {
                adapter = new CustomListThu2Adapter(con, listkhachhang, vitriduong, null, null);
                listview.setAdapter(adapter);
                listview.setVisibility(View.VISIBLE);
                epdlistdata.setVisibility(View.GONE);
                khongtimthay.setVisibility(View.GONE);
            } else {

                khongtimthay.setVisibility(View.VISIBLE);
                epdlistdata.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
            }
            epdlistdata.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return epdlistdata.isGroupExpanded(groupPosition) ? epdlistdata.collapseGroup(groupPosition) : epdlistdata.expandGroup(groupPosition);
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


    private void taoview() {
        listview = (ListView) findViewById(R.id.lvData);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnEye = (ImageButton) findViewById(R.id.btn_an);
        btnSearch = (ImageButton) findViewById(R.id.btn_tim);
        ;
        spinDuong = (Spinner) findViewById(R.id.spin_dsduong);
        ckDanhBo = (CheckBox) findViewById(R.id.cb_danhbo);
        ckHoten = (CheckBox) findViewById(R.id.cb_hoten);
        ckDienThoai = (CheckBox) findViewById(R.id.cb_dienthoai);
        ckMaTLK = (CheckBox) findViewById(R.id.cb_seriTLK);
        ckDiaChi = (CheckBox) findViewById(R.id.cb_DiaChi);
        khungtimkiem = (EditText) findViewById(R.id.edit_khungtimkiem);
        layout_chitieu = (LinearLayout) findViewById(R.id.layout_khungchitieu);
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

    private void loadDataDuong() {
        listduong = duongdao.getAllDuong();
        for (int i = 0; i < listduong.size(); i++) {
            String sDuong = listduong.get(i).getMaDuong() + "." + listduong.get(i).getTenDuong();
            sDuong.trim();
            arrDuong.add(sDuong);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
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
                maduong = arrDuong.get(position).substring(0, 2);
                Log.e("Vi tri duong", String.valueOf(vitriduong));
                Log.e("Ma duong", maduong);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vitriduong = 0;
                maduong = arrDuong.get(0).substring(0, 2);
                Log.e("Vi tri duong", String.valueOf(vitriduong));
                Log.e("Ma duong", maduong);
            }
        });


    }


}
