package tiwaco.thefirstapp.CustomAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.R;

import static android.R.color.holo_green_dark;

/**
 * Created by TUTRAN on 12/04/2017.
 */

public class CustomListDuongThuAdapter extends  RecyclerView.Adapter<CustomListDuongThuAdapter.RecyclerViewHolder> {

    List<DuongDTO> listDuong;
    Context con;
    ListView listviewKH;
    DuongDTO duongchon;
    TextView tvmaduong;
    TextView tvTitleKH, titleHD;
    RecyclerView reduong;
    String title ="";
    SPData spdata ;
    int vitri = 0;
    int loaighi = 0;
    ProgressDialog p;
    ThanhToanDAO thanhtoandao;


    public CustomListDuongThuAdapter(Context context, List<DuongDTO> listData, ListView listKH, TextView txtMaDuong, RecyclerView re, TextView titleKH, TextView titleHD) {
        this.listDuong = listData;
        this.con = context;
        this.listviewKH = listKH;
        this.tvmaduong = txtMaDuong;
        this.tvTitleKH = titleKH;
        this.titleHD = titleHD;
        this.reduong = re;
        spdata = new SPData(con);
        thanhtoandao = new ThanhToanDAO(con);
        p = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);


    }
    public void setData(List<DuongDTO> list){
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

                DuongDTO duong = listDuong.get(position);
                Bien.ma_duong_dang_chon_thu = duong.getMaDuong();
                //   Bien.bien_index_duong = pos;
                Log.e("Ma duong dang chon thu", Bien.ma_duong_dang_chon_thu);
                tvmaduong.setText(duong.getMaDuong());
                title =   "";
                List<KhachHangDTO> liskhdao = new ArrayList<KhachHangDTO>();
                KhachHangDAO khachhangDAO = new KhachHangDAO(con);

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
                }
                Bien.listKH_thu = liskhdao;
                Bien.adapterKHThu = new CustomListThuAdapter(con,liskhdao,pos);
                title += String.valueOf(liskhdao.size()) +" KH";
                Bien.bienSoLuongKHThu = liskhdao.size();
                tvTitleKH.setText(title);

                listviewKH.setAdapter(Bien.adapterKHThu);

            }

        });

        if (position ==   Bien.selected_item_thu) {
            holder.itemView.setSelected(true);
            holder.itemView.setPressed(true);
            holder.MaDuong.setSelected(true);
            holder.TenDuong.setSelected(true);

        } else {
            holder.itemView.setSelected(false);
            holder.itemView.setPressed(false);
            holder.MaDuong.setSelected(false);
            holder.TenDuong.setSelected(false);

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