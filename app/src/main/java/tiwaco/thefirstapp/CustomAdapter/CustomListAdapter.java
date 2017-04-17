package tiwaco.thefirstapp.CustomAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<KhachHangDTO> customerList ;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomListAdapter(Context con, List<KhachHangDTO> listcus){
        customerList = listcus ;
        context = con ;
        layoutInflater = LayoutInflater.from(con);
    }
    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
     if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listkh_item,null);
            holder = new ViewHolder();
            holder.Ten = (TextView)convertView.findViewById(R.id.tv_hoten);
            holder.STT = (TextView)convertView.findViewById(R.id.tv_stt);
            holder.DiaChi = (TextView)convertView.findViewById(R.id.tv_diachi);
        //    holder.TrangThai = (ImageView) convertView.findViewById(R.id.img_trangthai);
            convertView.setTag(holder);
        }
        else{
            holder  = (ViewHolder)convertView.getTag();
        }

        KhachHangDTO cus = customerList.get(position);
        holder.Ten.setText(cus.getTenKhachHang());
        holder.STT.setText(cus.getSTT());
        holder.DiaChi.setText(cus.getDiaChi());
        if(cus.getChiSo().equalsIgnoreCase("")){
            holder.STT.setBackgroundResource(R.drawable.remove_bg);

        }
        else{
            holder.STT.setBackgroundResource(R.drawable.remove_bg_daghi);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView Ten;
        TextView STT;
      //  ImageView TrangThai;
        TextView DiaChi;
    }
}
