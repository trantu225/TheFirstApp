package tiwaco.thefirstapp.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by TUTRAN on 12/04/2017.
 */

public class CustomListDuongAdapter extends  RecyclerView.Adapter<CustomListDuongAdapter.RecyclerViewHolder> {

    List<DuongDTO> listDuong;
    Context con;
    ListView listviewKH;
    DuongDTO duongchon;
    TextView tvmaduong;
    RecyclerView reduong;
    public CustomListDuongAdapter(Context context,List<DuongDTO> listData,ListView listKH,TextView txtMaDuong,RecyclerView re) {
        this.listDuong = listData;
        this.con = context;
        this.listviewKH = listKH;
        this.tvmaduong = txtMaDuong;
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
                Bien.DuongDuocChon = duong;
                tvmaduong.setText(duong.getMaDuong());
                if(Bien.DuongDuocChon !=null) {
                    //        Toast.makeText(v.getContext(), Bien.DuongDuocChon.getMaDuong(), Toast.LENGTH_SHORT).show();
                    //       Log.e("DuongDuocChon",Bien.DuongDuocChon.getMaDuong());
                }
                        /*ListView listviewKH = (ListView) itemView.findViewById(R.id.lv_khachhang);*/
                List<KhachHangDTO> liskhdao = new ArrayList<KhachHangDTO>();
                KhachHangDAO khachhangDAO = new KhachHangDAO(con);
                liskhdao = khachhangDAO.getAllKHTheoDuong(duong.getMaDuong());

                Bien.adapterKH = new CustomListAdapter(con,liskhdao);
                //adapterKH.notifyDataSetChanged();
                listviewKH.setAdapter(Bien.adapterKH);
                //      Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();



            }

                });
                       /*
                        if (  reduong.findViewHolderForAdapterPosition(Bien.selected_item).itemView != null) {
                            reduong.findViewHolderForAdapterPosition(Bien.selected_item).itemView.setSelected(true);
                        }

                        if (reduong.findViewHolderForAdapterPosition(Bien.pre_item).itemView != null) {
                            reduong.findViewHolderForAdapterPosition(Bien.pre_item).itemView.setSelected(false);
                        }

*/

                    if(position == Bien.selected_item){
                        holder.itemView.setSelected(true);
                    }
                    else
                    {
                        holder.itemView.setSelected(false);
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



        }



    }
}
