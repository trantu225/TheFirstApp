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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.MainActivity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 18/05/2017.
 */

public class CustomTimKiem extends BaseExpandableListAdapter {
    private static final String TAG = "CustomAdapter";
    Context mContext;
    private List<DuongDTO> mHeaderGroup;
    private HashMap<String, List<KhachHangDTO>> mDataChild;
    private List<KhachHangDTO> customerList ;
    private LayoutInflater layoutInflater;
    int index_duong ;
    SPData spdata;
    DuongDAO duongdao  ;
    KhachHangDAO khachhangdao ;
    public CustomTimKiem(Context context, List<DuongDTO> headerGroup, HashMap<String, List<KhachHangDTO>> datas,int vitriduong) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = datas;
        index_duong = vitriduong;
        spdata = new SPData(context);
        duongdao = new DuongDAO(context);
        khachhangdao = new KhachHangDAO(context);

    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition).getMaDuong()).size();
    }

    @Override
    public DuongDTO getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public KhachHangDTO getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition).getMaDuong()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.epl_duong_item, parent, false);
        }

        final TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_tenduongTK);
        TextView soketqua = (TextView) convertView.findViewById(R.id.tv_conlaiTK);
        tvHeader.setText(mHeaderGroup.get(groupPosition).getMaDuong()+"."+mHeaderGroup.get(groupPosition).getTenDuong());
        soketqua.setText(String.valueOf(getChildrenCount(groupPosition)) +" kết quả");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.listkh_item,null);

            holder = new CustomTimKiem.ViewHolder();
            holder.Ten = (TextView)convertView.findViewById(R.id.tv_hoten);
            holder.STT = (TextView)convertView.findViewById(R.id.tv_stt);
            holder.DiaChi = (TextView)convertView.findViewById(R.id.tv_diachi);
            holder.DanhBo  = (TextView) convertView.findViewById(R.id.tv_danhbo);
            convertView.setTag(holder);

        }
        else{
            holder  = (CustomTimKiem.ViewHolder)convertView.getTag();
        }
        final KhachHangDTO cus = getChild(groupPosition,childPosition);
       // final KhachHangDTO cus = customerList.get(childPosition);
        holder.Ten.setText(cus.getTenKhachHang());
        holder.Ten.setSelected(true);
        Log.e("index", String.valueOf(index_duong));
        holder.STT.setText(cus.getSTT());
        holder.DiaChi.setText(cus.getDiaChi());
        holder.DiaChi.setSelected(true);
        holder.DanhBo.setText(cus.getDanhBo());


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
                    Log.e("Ma duong dang chon", Bien.ma_duong_dang_chon);

                    new AlertDialog.Builder(mContext)
                            .setTitle(mContext.getString(R.string.tab_ghinuoc))
                            .setMessage(mContext.getString(R.string.list_chuyendulieu_hoighinuoc_khachhang) +" "+ cus.getTenKhachHang() + " "+ mContext.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    // bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                    bundle.putString(Bien.MADUONG, khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                    bundle.putString(Bien.STT,cus.getSTT() );
                                    if(index_duong !=-1) { //tu tim kiem chuyen qua
                                        bundle.putInt(Bien.VITRI, index_duong);
                                    }
                                    else{
                                        int vitri=  duongdao.getindexDuong(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                        bundle.putInt(Bien.VITRI, vitri);
                                    }
                                    intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                    mContext.startActivity(intent);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setCancelable(false)
                            .show();



                }
                else{ // da ghi
                    Log.e("Ma duong dang chon",Bien.ma_duong_dang_chon);
                    new AlertDialog.Builder(mContext)
                            .setTitle(mContext.getString(R.string.tab_ghinuoc))
                            .setMessage(mContext.getString(R.string.list_chuyendulieu_hoighinuoc_capnhatkhachhang) +" "+ cus.getTenKhachHang() + " "+ mContext.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    // bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon);
                                    bundle.putString(Bien.MADUONG, khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                    bundle.putString(Bien.STT,cus.getSTT());
                                    if(index_duong !=-1) {
                                        bundle.putInt(Bien.VITRI, index_duong);
                                    }
                                    else{
                                        int vitri=  duongdao.getindexDuong(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                        bundle.putInt(Bien.VITRI, vitri);
                                    }
                                    intent.putExtra(Bien.GOITIN_MADUONG, bundle);
                                    mContext.startActivity(intent);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setCancelable(false)
                            .show();


                }

            }
        });
        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    private static class ViewHolder {

        TextView Ten;
        TextView STT;
        //  ImageView TrangThai;
        TextView DiaChi;
        TextView DanhBo;
    }
}
