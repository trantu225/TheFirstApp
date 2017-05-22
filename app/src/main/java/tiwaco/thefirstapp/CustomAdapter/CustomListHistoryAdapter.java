package tiwaco.thefirstapp.CustomAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.MainActivity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 22/05/2017.
 */

public class CustomListHistoryAdapter extends BaseAdapter {
    private List<LichSuDTO> historylist ;
    private LayoutInflater layoutInflater;
    private Context context;
    LichSuDAO lichsudao;

    public CustomListHistoryAdapter(Context con, List<LichSuDTO> listcus){
        historylist = listcus ;
        context = con ;
        layoutInflater = LayoutInflater.from(con);

        lichsudao = new LichSuDAO(context);

    }
    @Override
    public int getCount() {
        return historylist.size();
    }

    @Override
    public Object getItem(int position) {
        return historylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.listhistory_item,null);

            holder = new ViewHolder();
            holder.ThoiGian = (TextView)convertView.findViewById(R.id.tv_thoigian);

            holder.NoiDung = (TextView)convertView.findViewById(R.id.tv_noidung);

            convertView.setTag(holder);

        }
        else{
            holder  = (ViewHolder)convertView.getTag();
        }

        final LichSuDTO cus = historylist.get(position);
        holder.ThoiGian.setText(cus.getThoiGianLS());
        holder.NoiDung.setText(cus.getNoiDungLS());
        holder.NoiDung.setSelected(true);



        return convertView;
    }

    private static class ViewHolder {

        TextView ThoiGian;
        TextView NoiDung;

    }
}
