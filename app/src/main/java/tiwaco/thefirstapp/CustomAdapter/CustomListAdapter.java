package tiwaco.thefirstapp.CustomAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.ListActivity;
import tiwaco.thefirstapp.MainActivity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<KhachHangDTO> customerList ;
    private LayoutInflater layoutInflater;
    private Context context;
    private int index_duong;
    SPData spdata;
    public CustomListAdapter(Context con, List<KhachHangDTO> listcus,int vitriduong){
        customerList = listcus ;
        context = con ;
        layoutInflater = LayoutInflater.from(con);
        index_duong = vitriduong;
        spdata = new SPData(context);
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
        final ViewHolder holder ;
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

        final KhachHangDTO cus = customerList.get(position);
        holder.Ten.setText(cus.getTenKhachHang());
        holder.Ten.setSelected(true);
        holder.STT.setText(cus.getSTT());
        holder.DiaChi.setText(cus.getDiaChi());
        if(cus.getChiSo().equalsIgnoreCase("")){
            holder.STT.setBackgroundResource(R.drawable.remove_bg);

        }
        else{
            holder.STT.setBackgroundResource(R.drawable.remove_bg_daghi);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cus.getChiSo().equalsIgnoreCase("")){
                  //chua ghi
                    Log.e("Ma duong dang chon",Bien.ma_duong_dang_chon);
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.tab_ghinuoc))
                            .setMessage(context.getString(R.string.list_chuyendulieu_hoighinuoc_khachhang) +" "+ cus.getTenKhachHang() + " "+ context.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                    bundle.putString(Bien.STT,cus.getSTT());
                                    if(index_duong !=-1) {
                                        bundle.putInt(Bien.VITRI, index_duong);
                                    }
                                    intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                    context.startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
                else{
                    Log.e("Ma duong dang chon",Bien.ma_duong_dang_chon);
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.tab_ghinuoc))
                            .setMessage(context.getString(R.string.list_chuyendulieu_hoighinuoc_capnhatkhachhang) +" "+ cus.getTenKhachHang() + " "+ context.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                    bundle.putString(Bien.STT,cus.getSTT());
                                    bundle.putInt(Bien.VITRI,index_duong);
                                    intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                    context.startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();


                }

            }
        });
        return convertView;
    }

    private static class ViewHolder {

        TextView Ten;
        TextView STT;
      //  ImageView TrangThai;
        TextView DiaChi;
    }
}
