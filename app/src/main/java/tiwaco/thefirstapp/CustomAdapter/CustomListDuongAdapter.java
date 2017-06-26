package tiwaco.thefirstapp.CustomAdapter;

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
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.R;

import static android.R.color.holo_green_dark;

/**
 * Created by TUTRAN on 12/04/2017.
 */

public class CustomListDuongAdapter extends  RecyclerView.Adapter<CustomListDuongAdapter.RecyclerViewHolder> {

    List<DuongDTO> listDuong;
    Context con;
    ListView listviewKH;
    DuongDTO duongchon;
    TextView tvmaduong;
    TextView tvTitleKH;
    RecyclerView reduong;
    String title ="";
    SPData spdata ;
    int vitri = 0;
    int loaighi = 0;



    public CustomListDuongAdapter(Context context,List<DuongDTO> listData,ListView listKH,TextView txtMaDuong,RecyclerView re,TextView titleKH) {
        this.listDuong = listData;
        this.con = context;
        this.listviewKH = listKH;
        this.tvmaduong = txtMaDuong;
        this.tvTitleKH = titleKH;
        this.reduong = re;
        spdata = new SPData(con);


    }
    public void setData(List<DuongDTO> list){
        listDuong = list;
    }

    @Override
    public CustomListDuongAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(final CustomListDuongAdapter.RecyclerViewHolder holder, final int position) {
        vitri  =position;
        holder.MaDuong.setText(listDuong.get(position).getMaDuong());
        holder.TenDuong.setText(listDuong.get(position).getTenDuong());
        if(listDuong.get(position).getTrangThai()==1){
            holder.MaDuong.setBackgroundResource(R.drawable.button_round_daghi);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.selector_recylerview_text_daghi));
        }
        else{
            holder.MaDuong.setBackgroundResource(R.drawable.button_round);
            holder.TenDuong.setTextColor(con.getResources().getColor(R.color.selector_recylerview_text));

        }


        Log.e("select duong--customlistduong bien.select", String.valueOf(Bien.selected_item));




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = position;

                if (pos != RecyclerView.NO_POSITION) {

                  Bien.selected_item = pos;

                   // selected_item =pos;


                    notifyDataSetChanged();
                }

                DuongDTO duong = listDuong.get(position);
                Bien.ma_duong_dang_chon = duong.getMaDuong();
             //   Bien.bien_index_duong = pos;
                Log.e("Ma duong dang chon",Bien.ma_duong_dang_chon);
                tvmaduong.setText(duong.getMaDuong());
                title =   "";
                List<KhachHangDTO> liskhdao = new ArrayList<KhachHangDTO>();
                KhachHangDAO khachhangDAO = new KhachHangDAO(con);

                //xu ly lay list tai day ( tat ca, da ghi, chua ghi)


                if(Bien.bientrangthaighi == 0){
                    liskhdao = khachhangDAO.getAllKHTheoDuong(duong.getMaDuong());
                }
                else if(Bien.bientrangthaighi ==1 )
                {
                    liskhdao = khachhangDAO.getAllKHDaGhiTheoDuong(duong.getMaDuong());
                }
                else if(Bien.bientrangthaighi ==2 ){
                    liskhdao = khachhangDAO.getAllKHChuaGhiTheoDuong(duong.getMaDuong());
                }
                else{
                    String thoigian1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                    liskhdao = khachhangDAO.getAllKHDaGhiHomNay(duong.getMaDuong(),thoigian1);
                }

                Bien.adapterKH = new CustomListAdapter(con,liskhdao,pos);
                title += String.valueOf(liskhdao.size()) +" KH";
                Bien.bienSoLuongKH = liskhdao.size();
                tvTitleKH.setText(title);
                listviewKH.setAdapter(Bien.adapterKH);

            }

                        });

        if (position ==   Bien.selected_item) {
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
            Log.e("select duong--customlistduong--khoitao", String.valueOf(Bien.selected_item));


        }



    }
}
