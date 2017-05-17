package tiwaco.thefirstapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.luseen.spacenavigation.SpaceNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;


public class MainActivity extends AppCompatActivity  {

    TextView danhsachduong;
    ImageButton btnghi;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String filename = "";
    String duongdanfile = "";
    private static String dataGhi = "";
    SpaceNavigationView spaceNavigationView;
    Context con;
    DuongDAO duongDAO;
    KhachHangDAO khachhangDAO;
    KhachHangDTO khachhang;
    TextView LabelDuong,STT,DanhBo, MaKH, HoTen, DiaChi, MaTLK, HieuTLK, CoTLK, ChiSo1, ChiSo2, ChiSo3, m31, m32, m33, ChiSoCon1, ChiSoCon2, ChiSoCon3, m3con1, m3con2, m3con3, m3moi, m3conmoi, DuongDangGhi, ConLai;
    EditText DienThoai, ChiSoMoi, ChiSoMoiCon,TinhTrangTLK,GhiChu;
    TableRow chisocu_con_lb, chisocu_con, chisomoi_con_lb, chisomoi_con;
    ImageButton DoiSDT,Toi,Lui,Ghi;
    LinearLayout lay_toi , lay_lui , lay_ghi;
    String STT_HienTai ="1";
    int SoLuongKH = 0;
    String maduong_nhan="",stt_nhan ="";
    int vitri_nhan = 0;
    SPData spdata;
    String tenduong;
    String soKHconlai,tongsoKHTheoDuong;
    double longitude,latitude;
    String vido ="" ;
    String kinhdo="";
    GPSTracker gps;
    boolean kt = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;

        getSupportActionBar().setTitle(R.string.tab_ghinuoc);
        taoView();
        spdata = new SPData(con);
        duongDAO = new DuongDAO(con);
        khachhangDAO = new KhachHangDAO(con);


        //----- lay toa do

        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        //----------------------------------------------------------------------------
        //lấy intent gọi Activity này
        //Lấy kết quả khi chọn button ghi nước tại listactivity
        Intent callerIntent = getIntent();

        //có intent rồi thì lấy Bundle dựa vào key
        Bundle packageFromCaller = callerIntent.getBundleExtra(Bien.GOITIN_MADUONG);
        if (packageFromCaller == null) {
            //get sharepreferences
            final String SPduongdangghi  = spdata.getDataDuongDangGhiTrongSP(); //lấy đường đang ghi

             //tìm min(stt) của khách hàng chưa nghi tại đường đang ghi
            //luc bat dau chua co gi
            Log.e("MAIN","luc bat dau chua co gi");
            if(SPduongdangghi.equalsIgnoreCase("")){
               //dialog chọn đường
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage(R.string.main_chuacothongtinduong);
                // thiết lập nội dung cho dialog
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent=new Intent(MainActivity.this, ListActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else{
                //tu start vao main
                Log.e("MAIN","tu start vao main");


                maduong_nhan = SPduongdangghi;
                String STT = spdata.getDataSTTDangGhiTrongSP() ;
                if(STT.equals("")) {
                    STT= khachhangDAO.getSTTChuaGhiNhoNhat(maduong_nhan);
                }
                STT_HienTai = STT;
                Log.e("STTMIN SP--------------------------",STT_HienTai);
                SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
                setDataForView(STT_HienTai,maduong_nhan);

                //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
                //  spdata.luuDataIndexDuongDangGhiTrongSP(Bien.bien_index_duong);
                // Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
                Toast.makeText(MainActivity.this, maduong_nhan, Toast.LENGTH_SHORT).show();

            }


        } else {
            //từ listactivity  sang main để ghi nước
            Log.e("MAIN","từ listactivity  sang main để ghi nước");
            //Có Bundle rồi thì lấy các thông số dựa vào key maduong và stt
            maduong_nhan = packageFromCaller.getString(Bien.MADUONG);
            stt_nhan =  packageFromCaller.getString(Bien.STT);
            vitri_nhan =  packageFromCaller.getInt(Bien.VITRI);
            STT_HienTai =stt_nhan;
            spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan,STT_HienTai);//luu vao sharepreferences
            SoLuongKH = khachhangDAO.countKhachHangTheoDuong(maduong_nhan);
            setDataForView(STT_HienTai,maduong_nhan);
          //  Log.e("Bien index duong", String.valueOf(Bien.bien_index_duong));
            if(vitri_nhan !=-1) {
                spdata.luuDataIndexDuongDangGhiTrongSP(vitri_nhan);
                Log.e("MAINACTIVITY_vitriduong", String.valueOf(vitri_nhan));
                Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();

            }
            Toast.makeText(this, maduong_nhan, Toast.LENGTH_SHORT).show();
        }

        //--------------------------------------------------------------------------




        lay_ghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraDieuKienDeGhiNuoc();

            }
        });
        Ghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_ghi.performClick();
            }
        });

        lay_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = Integer.parseInt(STT_HienTai) + 1;
                STT_HienTai = String.valueOf(next);
                Log.e("BienSTTHIenTai", STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (next+1 > SoLuongKH) {
                    lay_toi.setEnabled(false);
                    Toi.setEnabled(false);
                    lay_toi.setBackgroundResource(R.color.space_background_color);
                } else {
                    lay_toi.setEnabled(true);
                    Toi.setEnabled(true);
                    lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                }
                if (next > 0 && next <= SoLuongKH) {
                    setDataForView(STT_HienTai, maduong_nhan);
                    next = Integer.parseInt(STT_HienTai) + 1;
                    int pre = Integer.parseInt(STT_HienTai) - 1;
                    if (next > SoLuongKH) {
                        lay_toi.setEnabled(false);
                        Toi.setEnabled(false);
                        lay_toi.setBackgroundResource(R.color.space_background_color);
                    } else {
                        lay_toi.setEnabled(true);
                        Toi.setEnabled(true);
                        lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                    }
                    if (pre <= 0) {
                        lay_lui.setEnabled(false);
                        Lui.setEnabled(false);
                        lay_lui.setBackgroundResource(R.color.space_background_color);
                    } else {
                        lay_lui.setEnabled(true);
                        Lui.setEnabled(true);
                        lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                    }
                } else {
                    Log.e("BienSTTHIenTai", STT_HienTai);
                }

            }
        });
        Toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_toi.performClick();
            }
        });
        lay_lui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pre = Integer.parseInt(STT_HienTai) - 1;
                STT_HienTai = String.valueOf(pre);

                Log.e("BienSTTHIenTai", STT_HienTai);
                Log.e("SoLuongKH", String.valueOf(SoLuongKH));
                if (pre-1 <= 0) {
                    lay_lui.setEnabled(false);
                    Lui.setEnabled(false);
                    lay_lui.setBackgroundResource(R.color.space_background_color);
                } else {
                    lay_lui.setEnabled(true);
                    Lui.setEnabled(true);
                    lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                }
                if (pre > 0 && pre <= SoLuongKH) {
                    Log.e("BienSTTHIenTai", "chay vao 3");
                    setDataForView(STT_HienTai, maduong_nhan);
                    pre = Integer.parseInt(STT_HienTai) - 1;
                    int next = Integer.parseInt(STT_HienTai) + 1;
                    if (next > SoLuongKH) {
                        lay_toi.setEnabled(false);
                        Toi.setEnabled(false);
                        lay_toi.setBackgroundResource(R.color.space_background_color);
                    } else {
                        lay_toi.setEnabled(true);
                        Toi.setEnabled(true);
                        lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                    }
                    if (pre <= 0) {
                        lay_lui.setEnabled(false);
                        Lui.setEnabled(false);
                        lay_lui.setBackgroundResource(R.color.space_background_color);
                    } else {
                        lay_lui.setEnabled(true);
                        Lui.setEnabled(true);
                        lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
                    }

                } else {
                    Log.e("BienSTTHIenTai", "chay vao 4");

                }
            }
        });
        Lui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_lui.performClick();
            }
        });
        DoiSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makh = MaKH.getText().toString().trim();
                String dt = DienThoai.getText().toString().trim();

                if(khachhangDAO.updateDienThoai(makh,dt)){
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienghi = Bien.bienghi +1;
                    spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Toast.makeText(con, R.string.main_capnhatsdt_thanhcong,Toast.LENGTH_SHORT).show();
                }
                else{
                    //dialog chọn đường
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog
                    alertDialogBuilder.setMessage(R.string.main_capnhatsdt_thatbai);
                    // thiết lập nội dung cho dialog

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("select duong-mainactivity", String.valueOf(Bien.selected_item));
    }

    private void setDataForView(String tt, String maduong) {
        //Lấy khách hàng có stt hiện tại...mặc đình là 1
        tenduong =  duongDAO.getTenDuongTheoMa(maduong).trim();
        DuongDangGhi.setText(tenduong);
        soKHconlai = String.valueOf(khachhangDAO.countKhachHangChuaGhiTheoDuong(maduong)) ;
        tongsoKHTheoDuong = String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong)) ;
        ConLai.setText(getString(R.string.string_con)+" "+soKHconlai+"/"+ tongsoKHTheoDuong);

        khachhang =  khachhangDAO.getKHTheoSTT_Duong(tt,maduong);
        STT.setText(khachhang.getSTT().trim());
        MaKH.setText(khachhang.getMaKhachHang().trim());
        HoTen.setText(khachhang.getTenKhachHang().trim());
        DanhBo.setText(khachhang.getDanhBo().trim());
        DiaChi.setText(khachhang.getDiaChi().trim());
        MaTLK.setText(khachhang.getMasotlk().trim());
        HieuTLK.setText(khachhang.getHieutlk().trim());
        CoTLK.setText(khachhang.getCotlk().trim());
        ChiSo1.setText(khachhang.getChiSo1().trim());
        ChiSo2.setText(khachhang.getChiSo2().trim());
        ChiSo3.setText(khachhang.getChiSo3().trim());
        m31.setText(khachhang.getSLTieuThu1().trim());
        m32.setText(khachhang.getSLTieuThu2().trim());
        m33.setText(khachhang.getSLTieuThu3().trim());
        ChiSoMoi.setText(khachhang.getChiSo().trim());
         m3moi.setText(khachhang.getSLTieuThu().trim());
        ChiSoMoiCon.setText(khachhang.getChiSocon().trim());
        m3conmoi.setText(khachhang.getSLTieuThucon().trim());
        TinhTrangTLK.setText(khachhang.getTrangThaiTLK().trim());
        GhiChu.setText(khachhang.getGhiChu().trim());
        if(!khachhang.getChiSo().equals("")){
            LabelDuong.setBackgroundResource(android.R.color.holo_red_dark);
            DuongDangGhi.setBackgroundResource(android.R.color.holo_red_dark);
            ConLai.setBackgroundResource(android.R.color.holo_red_dark);
        }
        else{
            LabelDuong.setBackgroundResource(R.color.colorPrimaryDark);
            DuongDangGhi.setBackgroundResource(R.color.colorPrimaryDark);
            ConLai.setBackgroundResource(R.color.colorPrimaryDark);
        }

        if(khachhang.getChiSo1con().equals("0") && khachhang.getChiSo2con().equals("0") && khachhang.getChiSo3con().equals("0")
            &&  khachhang.getSLTieuThu1con().equals("0")  &&  khachhang.getSLTieuThu2con().equals("0")  &&  khachhang.getSLTieuThu3con().equals("0")){

            chisocu_con_lb.setVisibility(View.GONE);
            chisocu_con.setVisibility(View.GONE);
            chisomoi_con_lb.setVisibility(View.GONE);
            chisomoi_con.setVisibility(View.GONE);
        }
        else {
            chisocu_con_lb.setVisibility(View.VISIBLE);
            chisocu_con.setVisibility(View.VISIBLE);
            chisomoi_con_lb.setVisibility(View.VISIBLE);
            chisomoi_con.setVisibility(View.VISIBLE);

            ChiSoCon1.setText(khachhang.getChiSo1con().trim());
            ChiSoCon2.setText(khachhang.getChiSo2con().trim());
            ChiSoCon3.setText(khachhang.getChiSo3con().trim());
            m3con1.setText(khachhang.getSLTieuThu1con().trim());
            m3con2.setText(khachhang.getSLTieuThu2con().trim());
            m3con3.setText(khachhang.getSLTieuThu3con().trim());
        }
        DienThoai.setText(khachhang.getDienThoai().trim());
        //set background cho tới và lùi
        if(tt.equals("1")){
            lay_lui.setBackgroundResource(R.color.space_background_color);
        }
        else if(tt.equals(String.valueOf(khachhangDAO.countKhachHangTheoDuong(maduong)))){
            lay_toi.setBackgroundResource(R.color.space_background_color);
        }
        else{
            lay_toi.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
            lay_lui.setBackgroundResource(R.drawable.backdround_vungngoai_ghi);
        }
        ChiSoMoi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (ChiSoMoi.getText().toString().trim().equals("")) {
                        showDiaLogThongBao("Bạn chưa nhập chỉ số nước đồng hồ con.");
                    } else {
                        int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
                        int chisocu = Integer.parseInt(ChiSo1.getText().toString().trim());
                        //Kiểm tra âm
                        if (chisomoi < 0) {
                            //Show dialog thông báo âm
                            showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                        } else {
                            if (chisomoi < chisocu) {
                                //Show dialog thông báo nhỏ hơn chỉ số cũ
                                //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                // khởi tạo dialog

                                alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        m3moi.setEnabled(true);
                                        m3moi.setText("");

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        resetViewGhiNuoc();
                                        m3moi.setEnabled(false);
                                        m3moi.setText("");
                                        dialog.dismiss();


                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // tạo dialog
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                // hiển thị dialog
                            } else {
                                m3moi.setEnabled(false);
                                m3moi.setText(String.valueOf(chisomoi - chisocu));
                            }
                        }
                    }
                }
            }
        });
        ChiSoMoiCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (ChiSoMoiCon.getText().toString().trim().equals("")) {
                        showDiaLogThongBao("Bạn chưa nhập chỉ số nước đồng hồ con.");
                    } else {
                        int chisomoicon = Integer.parseInt(ChiSoMoiCon.getText().toString().trim());
                        int chisocucon = Integer.parseInt(ChiSoCon1.getText().toString().trim());
                        //Kiểm tra âm
                        if (chisomoicon < 0) {
                            //Show dialog thông báo âm
                            showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                        } else {
                            if (chisomoicon < chisocucon) {
                                //Show dialog thông báo nhỏ hơn chỉ số cũ
                                //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                // khởi tạo dialog

                                alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        m3conmoi.setEnabled(true);
                                        m3conmoi.setText("");
                                        dialog.dismiss();

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        resetViewGhiNuoc();
                                        m3conmoi.setEnabled(false);
                                        m3conmoi.setText("");
                                        dialog.dismiss();


                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                // tạo dialog
                                alertDialog.show();
                                // hiển thị dialog
                            } else {
                                m3conmoi.setEnabled(false);
                                m3conmoi.setText(String.valueOf(chisomoicon - chisocucon));
                            }
                        }
                    }
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    private void selectItem(MenuItem item) {

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_save:

                break;
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        selectItem(item);
        return super.onOptionsItemSelected(item);
    }


    private void taoView() {
        STT = (TextView) findViewById(R.id.tv_sttKH);
        MaKH= (TextView) findViewById(R.id.tv_maKH);
        MaKH.setSelected(true);
        DanhBo = (TextView) findViewById(R.id.tv_DanhBo);
        DanhBo.setSelected(true);
        HoTen= (TextView) findViewById(R.id.tv_hotenKH);
        HoTen.setSelected(true);
        DiaChi= (TextView) findViewById(R.id.tv_diachiKH);
        MaTLK= (TextView) findViewById(R.id.tv_maTLK);
        HieuTLK= (TextView) findViewById(R.id.tv_hieuTLK);
        CoTLK= (TextView) findViewById(R.id.tv_coTLK);
        ChiSo1= (TextView) findViewById(R.id.tv_chisocu1);
        ChiSo2= (TextView) findViewById(R.id.tv_chisocu2);
        ChiSo3= (TextView) findViewById(R.id.tv_chisocu3);
        m31= (TextView) findViewById(R.id.tv_m3cu1);
        m32= (TextView) findViewById(R.id.tv_m3cu2);
        m33= (TextView) findViewById(R.id.tv_m3cu3);
        ChiSoCon1= (TextView) findViewById(R.id.tv_chisocu1con);
        ChiSoCon2= (TextView) findViewById(R.id.tv_chisocu2con);
        ChiSoCon3= (TextView) findViewById(R.id.tv_chisocu3con);
        m3con1= (TextView) findViewById(R.id.tv_m3cu1con);
        m3con2= (TextView) findViewById(R.id.tv_m3cu2con);
        m3con3= (TextView) findViewById(R.id.tv_m3cu3con);


        LabelDuong = (TextView) findViewById(R.id.tv_label_duong);
        DuongDangGhi= (TextView) findViewById(R.id.tv_duongdangghi);
        ConLai= (TextView) findViewById(R.id.tv_conlai);
        DuongDangGhi.setSelected(true);
        DienThoai = (EditText) findViewById(R.id.edit_DienThoaiKH);
        ChiSoMoi = (EditText) findViewById(R.id.edit_chisomoi);
        m3moi= (TextView) findViewById(R.id.edit_m3moi);
        ChiSoMoiCon = (EditText) findViewById(R.id.edit_chisomoicon);
        m3conmoi= (TextView) findViewById(R.id.edit_m3moicon);
        TinhTrangTLK = (EditText) findViewById(R.id.edit_tinhtrangTLK);
        GhiChu = (EditText) findViewById(R.id.edit_ghichu);

        DoiSDT  = (ImageButton) findViewById(R.id.imgbtn_doi);
        Ghi = (ImageButton) findViewById(R.id.btn_ghinuoc);
        Toi = (ImageButton) findViewById(R.id.btn_toi);
        Lui = (ImageButton) findViewById(R.id.btn_lui);


        lay_toi =(LinearLayout)findViewById(R.id.layout_toi);
        lay_lui =(LinearLayout)findViewById(R.id.layout_lui);
        lay_ghi =(LinearLayout)findViewById(R.id.layout_ghi);

        chisocu_con_lb =(TableRow) findViewById(R.id.tableRow_chisocucon_lb);
        chisocu_con =(TableRow) findViewById(R.id.tableRow_chisocucon);

        chisomoi_con_lb =(TableRow) findViewById(R.id.tableRow_chisomoicon_lb);
        chisomoi_con =(TableRow) findViewById(R.id.tableRow_chisomoicon);


    }




//hàm ghi nước
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
        case 1: {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the

                // contacts-related task you need to do.

                gps = new GPSTracker(con, MainActivity.this);

                // Check if GPS enabled
                if (gps.canGetLocation()) {

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();
                    vido = String.valueOf(latitude);
                    kinhdo = String.valueOf(longitude);

                    Log.e("Toa do", vido +"-"+kinhdo );
                    // \n is for new line
                   Toast.makeText(getApplicationContext(), "REQUEST: Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.

                Toast.makeText(con, "REQUEST: You need to grant permission....", Toast.LENGTH_SHORT).show();

            }
            return;
        }
    }
}
    private void ghinuoc(){


        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(con,"GHI NUOC:You need have granted permission.......",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            gps = new GPSTracker(con, MainActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {
                String maKH = MaKH.getText().toString().trim();
                String Chiso = ChiSoMoi.getText().toString().trim();
                String m3 = m3moi.getText().toString().trim();
                String Chisocon = ChiSoMoiCon.getText().toString().trim();
                String m3con = m3conmoi.getText().toString().trim();
                String Dienthoai = DienThoai.getText().toString().trim();
                String ghichu = GhiChu.getText().toString().trim();
                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();
                 vido = String.valueOf(latitude);
                 kinhdo = String.valueOf(longitude);
                // \n is for new line
              //  Toast.makeText(getApplicationContext(), "GHI NUOC:Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                Log.e("Toa do", vido +"-"+kinhdo );
                String nhanvien = spdata.getDataNhanVienTrongSP();
               /*
                if(m3.equals("")){
                    String SL =  String.valueOf(Integer.parseInt(ChiSoMoi.getText().toString()) - Integer.parseInt(ChiSo1.getText().toString())).trim();
                }


                String SLCon;
                if(!ChiSoMoiCon.getText().toString().equals("")) {
                    SLCon = String.valueOf(Integer.parseInt(ChiSoMoiCon.getText().toString()) - Integer.parseInt(ChiSoCon1.getText().toString())).trim();
                }
                else{
                    SLCon ="";
                }
                */
                String thoigian = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                Log.e("Thoi gian", thoigian );
                String trangthaiTLK  = TinhTrangTLK.getText().toString().trim();

                if(khachhangDAO.updateKhachHang(maKH,Chiso,Chisocon,Dienthoai,ghichu,vido,kinhdo,nhanvien,m3,m3con,thoigian,trangthaiTLK))
                {
                    Toast.makeText(con,"Ghi nước thành công",Toast.LENGTH_SHORT).show();
                    //Cập nhật biến ghi
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    Bien.bienghi = Bien.bienghi +1;
                    spdata.luuDataFlagGhiTrongSP(Bien.bienghi);
                    Bien.bienghi = spdata.getDataFlagGhiTrongSP();
                    //Nếu còn khách hàng chưa ghi -> tiếp tục ghi
                    if(khachhangDAO.countKhachHangChuaGhiTheoDuong(maduong_nhan)>0) {

                        //Cập nhật Vị trí hiện tại , load lại (xử lý next.performclick)
                        String sothutu = khachhangDAO.getSTTChuaGhiNhoNhatLonHonHienTai(maduong_nhan,STT_HienTai);
                        STT_HienTai = sothutu;
                        setDataForView(sothutu,maduong_nhan);
                        spdata.luuDataDuongVaSTTDangGhiTrongSP(maduong_nhan,sothutu);
                    }
                    //KH đã ghi nước xong => cập nhật đường va show dialog
                    else {
                        //cập nhật trạng thái đường đã ghi
                       if(duongDAO.updateDuongDaGhi(maduong_nhan)) {
                           //show dialog đã ghi xong..trở về listactivity
                           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                           // khởi tạo dialog
                           if(duongDAO.countDuongChuaGhi()>0) {
                               alertDialogBuilder.setMessage(R.string.main_ghinuoc_duongdaghixong);
                               // thiết lập nội dung cho dialog
                           }
                           else{
                               alertDialogBuilder.setMessage(R.string.main_ghinuoc_duongdaghixongtatca);
                           }
                           alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                                   Intent intent = new Intent(MainActivity.this, ListActivity.class);
                                   startActivity(intent);
                               }
                           });


                           AlertDialog alertDialog = alertDialogBuilder.create();
                           // tạo dialog
                           alertDialog.setCanceledOnTouchOutside(false);
                           alertDialog.show();
                           // hiển thị dialog
                       }
                    }


                }
                else{
                    Toast.makeText(con,"Ghi nước thất bại",Toast.LENGTH_SHORT).show();
                }
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }



    }
    private void kiemTraDieuKienDeGhiNuoc(){



        if(ChiSoMoi.getText().toString().trim().equals("")){
            showDiaLogThongBao("Bạn chưa nhập chỉ số nước.");
        }
        else{
            //Kiem tra chi so moi có nhỏ hơn chỉ số cũ ko
            int chisomoi = Integer.parseInt(ChiSoMoi.getText().toString().trim());
            int chisocu =  Integer.parseInt(ChiSo1.getText().toString().trim());
            //Kiểm tra âm
            if(chisomoi<0){
                //Show dialog thông báo âm
                showDiaLogThongBao(getString(R.string.main_thongbao_soam));

            }
            else{
                if(chisomoi <chisocu){
                    //Show dialog thông báo nhỏ hơn chỉ số cũ
                    //showDiaLogThongBao(getString(R.string.main_thongbao_batthuong));
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if(!m3moi.getText().toString().trim().equals("")) {
                                ghinuoc();
                            }
                            else{
                                showDiaLogThongBao("Sử dụng chỉ số bất thường cần nhập m3 nước.");
                            }
                            dialog.dismiss();

                        }
                    });
                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            resetViewGhiNuoc();
                            dialog.dismiss();


                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    // hiển thị dialog
                }
                else{
                    //Kiem tra bat thuong...tạo dialog hỏi muốn ghi ko...nếu có thì kt= true, ko thì kt = false
                   if(m3moi.getText().toString().trim().equals("")){
                       m3moi.setText(String.valueOf(chisomoi -chisocu));
                       ghinuoc();
                   }
                   else {
                       ghinuoc();
                   }
                }
            }

        }
        if(chisomoi_con.getVisibility()==View.VISIBLE){
            if(ChiSoMoiCon.getText().toString().trim().equals("")){
                showDiaLogThongBao("Bạn chưa nhập chỉ số nước đồng hồ con.");
            }
            else{

                int chisomoicon = Integer.parseInt(ChiSoMoiCon.getText().toString().trim());
                int chisocucon =  Integer.parseInt(ChiSoCon1.getText().toString().trim());

                if(chisomoicon<0){
                    //Show dialog thông báo âm
                    showDiaLogThongBao(getString(R.string.main_thongbao_soam));

                }
                else{
                    if(chisomoicon <chisocucon){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage(getString(R.string.main_thongbao_batthuong));

                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.e("m3 rong","co - vo day");
                                if(!m3conmoi.getText().toString().trim().equals("")) {
                                    ghinuoc();
                                }
                                else{
                                    Log.e("m3 rong","vo day");
                                    showDiaLogThongBao("Sử dụng chỉ số bất thường cần nhập m3 nước.");
                                }
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                resetViewGhiNuoc();
                                dialog.dismiss();


                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        // hiển thị dialog
                    }
                    else{
                        if(m3conmoi.getText().toString().trim().equals("")){
                            m3moi.setText(String.valueOf(chisomoicon -chisocucon));
                            ghinuoc();
                        }
                        else {
                            ghinuoc();
                        }
                    }
                }
            }

        }


    }

private void showDiaLogThongBao(String mess){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
    // khởi tạo dialog

    alertDialogBuilder.setMessage(mess);

    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            resetViewGhiNuoc();
            dialog.dismiss();

        }
    });


    AlertDialog alertDialog = alertDialogBuilder.create();
    // tạo dialog
    alertDialog.setCanceledOnTouchOutside(false);
    alertDialog.show();
    // hiển thị dialog
}
private void  resetViewGhiNuoc(){
    DienThoai.setText("");
    TinhTrangTLK.setText("");
    ChiSoMoi.setText("");
    ChiSoMoiCon.setText("");
    GhiChu.setText("");
}

}

