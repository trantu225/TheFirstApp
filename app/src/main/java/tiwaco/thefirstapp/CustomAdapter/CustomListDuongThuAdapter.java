package tiwaco.thefirstapp.CustomAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.R;

import static android.R.color.holo_green_dark;

/**
 * Created by TUTRAN on 12/04/2017.
 */

public class CustomListDuongThuAdapter extends  RecyclerView.Adapter<CustomListDuongThuAdapter.RecyclerViewHolder> {

    List<DuongThuDTO> listDuong;
    Context con;
    ListView listviewKH;
    DuongThuDTO duongchon;
    TextView tvmaduong;
    TextView tvTitleKH, titleHD;
    RecyclerView reduong;
    String title ="";
    SPData spdata ;
    int vitri = 0;
    int loaighi = 0;
    ProgressDialog p;
    ThanhToanDAO thanhtoandao;
    LinearLayout layoutthuho;
    TextView sotienthuho;
    DecimalFormatSymbols decimalFormatSymbols;
    DecimalFormat format, format1, format2;


    public CustomListDuongThuAdapter(Context context, List<DuongThuDTO> listData, ListView listKH, TextView txtMaDuong, RecyclerView re, TextView titleKH, TextView titleHD, LinearLayout laythuho, TextView sotien) {
        this.listDuong = listData;
        this.con = context;
        this.listviewKH = listKH;
        this.tvmaduong = txtMaDuong;
        this.tvTitleKH = titleKH;
        this.titleHD = titleHD;
        this.reduong = re;
        this.layoutthuho = laythuho;
        decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);
        sotienthuho = sotien;
        spdata = new SPData(con);
        thanhtoandao = new ThanhToanDAO(con);
        p = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);


    }

    public void setData(List<DuongThuDTO> list) {
        listDuong = list;
    }

    @Override
    public CustomListDuongThuAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listduong_item, parent, false);
        RecyclerViewHolder mViewHold = new RecyclerViewHolder(mView);
        return mViewHold;
        /*
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.listduong_item, parent, false);

        return new RecyclerViewHolder(itemview);
        */
    }

    public String formatTien(int tien) {
        String tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(tien)))) + " đ";
        return tongcong;
    }
    @Override
    public void onBindViewHolder(final CustomListDuongThuAdapter.RecyclerViewHolder holder, final int position) {
        vitri  =position;
        holder.MaDuong.setText(listDuong.get(position).getMaDuong());
        holder.TenDuong.setText(listDuong.get(position).getTenDuong());
        if(listDuong.get(position).getTrangThaiThu()==1){
            holder.MaDuong.setBackgroundResource(R.drawable.button_round_daghi);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.selector_recylerview_text_daghi));
        }
        else{
            holder.MaDuong.setBackgroundResource(R.drawable.button_round);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.selector_recylerview_text));

        }


        Log.e("select duong--customlistduong bien.select", String.valueOf(Bien.selected_item_thu));




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = position;

                if (pos != RecyclerView.NO_POSITION) {

                    Bien.selected_item_thu = pos;

                    // selected_item =pos;


                    notifyDataSetChanged();
                }

                DuongThuDTO duong = listDuong.get(position);
                Bien.ma_duong_dang_chon_thu = duong.getMaDuong();
                //   Bien.bien_index_duong = pos;
                Log.e("Ma duong dang chon thu", Bien.ma_duong_dang_chon_thu);
                tvmaduong.setText(duong.getMaDuong());
                title =   "";
                List<KhachHangThuDTO> liskhdao = new ArrayList<KhachHangThuDTO>();
                final KhachHangThuDAO khachhangDAO = new KhachHangThuDAO(con);

                //xu ly lay list tai day ( tat ca, da ghi, chua ghi)


                if(Bien.bientrangthaithu == 0){
                    liskhdao = khachhangDAO.getAllKHTheoDuong(duong.getMaDuong());
                    Log.e("Đuong", duong.getMaDuong());
                    String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(0, duong.getMaDuong());
                    String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(0, duong.getMaDuong());

                    titleHD.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);

                }
                else if(Bien.bientrangthaithu ==1 )
                {
                    liskhdao = khachhangDAO.getAllKHDaThuTheoDuong(duong.getMaDuong());
                    String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(1, duong.getMaDuong());
                    String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(1, duong.getMaDuong());

                    titleHD.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);
                }
                else if(Bien.bientrangthaithu ==2 ){
                    p.setMessage("Đang tập hợp dữ liệu...");
                    p.setCanceledOnTouchOutside(false);
                    p.show();
                    liskhdao = khachhangDAO.getAllKHChuaThuTheoDuong(duong.getMaDuong());
                    String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(2, duong.getMaDuong());
                    String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(2, duong.getMaDuong());

                    titleHD.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);
                    p.dismiss();
                }
                else if(Bien.bientrangthaithu ==3 ){
                    String thoigian1 = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                    liskhdao = khachhangDAO.getAllKHDaThuHomNay(duong.getMaDuong(),thoigian1);
                    String sohd1 = thanhtoandao.getSoHDTheoMaDuongPhanLoai(3, duong.getMaDuong());
                    String tongcong1 = thanhtoandao.getSoTienTheoMaDuongPhanLoai(3, duong.getMaDuong());

                    titleHD.setText("Số HD: " + sohd1 + " - Số tiền: " + tongcong1);
                }
                else  if(Bien.bientrangthaithu == 4){
                    liskhdao = khachhangDAO.getAllKHGhiChu(duong.getMaDuong());
                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    titleHD.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                } else if (Bien.bientrangthaithu == 5) {
                    liskhdao = khachhangDAO.getAllKHCoNoTheoDuong(duong.getMaDuong());
                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    titleHD.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                } else if (Bien.bientrangthaithu == 6) {
                    liskhdao = khachhangDAO.getAllKHTamThuChuaCapNhat(duong.getMaDuong());
                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    titleHD.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                } else if (Bien.bientrangthaithu == 7) {
                    liskhdao = khachhangDAO.getAllKHTamThuDaCapNhat(duong.getMaDuong());
                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    titleHD.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                } else if (Bien.bientrangthaithu == 8) {
                    liskhdao = khachhangDAO.getAllKHTamThuDaCapNhatBiTrung(duong.getMaDuong());
                    int tongcong4 = 0;
                    int sohd4 = 0;
                    for (int i = 0; i < liskhdao.size(); i++) {
                        sohd4 += thanhtoandao.getSoHDTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                        tongcong4 += thanhtoandao.getSoTienTongCongTheoMAKHKhongFormat(liskhdao.get(i).getMaKhachHang());
                    }
                    titleHD.setText("Số HD: " + sohd4 + " - Số tiền: " + thanhtoandao.formatTien(tongcong4));
                }
                Bien.listKH_thu = liskhdao;
                Bien.adapterKHThu = new CustomListThu2Adapter(con, liskhdao, pos, layoutthuho, sotienthuho);
                title += String.valueOf(liskhdao.size()) +" KH";
                Bien.bienSoLuongKHThu = liskhdao.size();

                tvTitleKH.setText(title);


                listviewKH.setAdapter(Bien.adapterKHThu);


                List<KhachHangThuDTO> listkhthuho = khachhangDAO.getAllKHTrongDanhSachThuHo(listDuong.get(position).getMaDuong());
                if (listkhthuho.size() > 0) {
                    int tongtien = 0;
                    for (KhachHangThuDTO kh : listkhthuho) {
                        List<ThanhToanDTO> listhoadon = thanhtoandao.GetListThanhToanTheoMaKH(kh.getMaKhachHang());

                        for (ThanhToanDTO hoadonthuho : listhoadon) {


                            tongtien += Integer.parseInt(hoadonthuho.gettongcong());
                        }


                    }
                    sotienthuho.setText(formatTien(tongtien));
                    layoutthuho.setVisibility(View.VISIBLE);
                } else {
                    layoutthuho.setVisibility(View.GONE);
                }
                //

            }

        });

        if (position ==   Bien.selected_item_thu) {
            holder.itemView.setSelected(true);
            holder.itemView.setPressed(true);
            holder.MaDuong.setSelected(true);
            holder.TenDuong.setSelected(true);
            holder.MaDuong.setBackgroundResource(R.drawable.button_round_chon);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.badge_background_color));

        } else {
            holder.itemView.setSelected(false);
            holder.itemView.setPressed(false);
            holder.MaDuong.setSelected(false);
            holder.TenDuong.setSelected(false);
            holder.MaDuong.setBackgroundResource(R.drawable.button_round);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.selector_recylerview_text));

        }



        //    Log.e("select duong--customlistduong", String.valueOf(Bien.selected_item));


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public int getPosition(){
        return vitri;
    }
    @Override
    public int getItemCount() {
        return listDuong.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView MaDuong;
        TextView TenDuong;


        public RecyclerViewHolder(final View itemView) {
            super(itemView);

            MaDuong = (TextView) itemView.findViewById(R.id.tv_MaDuong);
            TenDuong = (TextView) itemView.findViewById(R.id.tv_TenDuong);
            //TenDuong.setSelected(true);
            //Bien.selected_item = spdata.getDataIndexDuongDangGhiTrongSP();
            Log.e("select duong--customlistduong--khoitao", String.valueOf(Bien.selected_item_thu));


        }



    }
}
