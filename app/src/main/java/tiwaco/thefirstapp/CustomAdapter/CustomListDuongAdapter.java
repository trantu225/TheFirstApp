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

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
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
    public CustomListDuongAdapter(Context context,List<DuongDTO> listData,ListView listKH,TextView txtMaDuong,RecyclerView re,TextView titleKH) {
        this.listDuong = listData;
        this.con = context;
        this.listviewKH = listKH;
        this.tvmaduong = txtMaDuong;
        this.tvTitleKH = titleKH;
        this.reduong = re;

    }
    @Override
    public CustomListDuongAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.listduong_item, parent, false);

        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final CustomListDuongAdapter.RecyclerViewHolder holder, final int position) {

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

        if(position == Bien.selected_item){
            holder.itemView.setSelected(true);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = position;

                if (pos != RecyclerView.NO_POSITION) {

                    Bien.pre_item = Bien.selected_item;
                    Bien.selected_item = pos;
                    notifyDataSetChanged();
                }

                DuongDTO duong = listDuong.get(position);
                Bien.ma_duong_dang_chon = duong.getMaDuong();
                Log.e("Ma duong dang chon",Bien.ma_duong_dang_chon);
                tvmaduong.setText(duong.getMaDuong());
                title =   "";
                List<KhachHangDTO> liskhdao = new ArrayList<KhachHangDTO>();
                KhachHangDAO khachhangDAO = new KhachHangDAO(con);
                liskhdao = khachhangDAO.getAllKHTheoDuong(duong.getMaDuong());

                Bien.adapterKH = new CustomListAdapter(con,liskhdao);
                title += String.valueOf(liskhdao.size()) +" khách hàng";
                tvTitleKH.setText(title);
                listviewKH.setAdapter(Bien.adapterKH);

            }

                        });


                    if(position == Bien.selected_item){
                        holder.itemView.setSelected(true);
                        holder.itemView.setPressed(true);
                        holder.MaDuong.setSelected(true);
                        holder.TenDuong.setSelected(true);

                    }
                    else
                    {
                        holder.itemView.setSelected(false);
                        holder.itemView.setPressed(false);
                        holder.MaDuong.setSelected(false);
                        holder.TenDuong.setSelected(false);

                    }


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


        }



    }
}
