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
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.MainThu2Activity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class CustomListThu2Adapter extends BaseAdapter {
    private List<KhachHangThuDTO> customerList;
    private LayoutInflater layoutInflater;
    private Context context;
    int index_duong;
    SPData spdata;
    DuongThuDAO duongdao;
    KhachHangThuDAO khachhangdao;
    ThanhToanDAO thanhtoandao;
    String strghichu = "";

    public CustomListThu2Adapter(Context con, List<KhachHangThuDTO> listcus, int vitriduong) {
        customerList = listcus;
        context = con;
        layoutInflater = LayoutInflater.from(con);
        index_duong = vitriduong;
        spdata = new SPData(context);
        duongdao = new DuongThuDAO(context);
        khachhangdao = new KhachHangThuDAO(context);
        thanhtoandao = new ThanhToanDAO(context);
    }

    public void setData(List<KhachHangThuDTO> list) {
        customerList = list;
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
        final ViewHolder holder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.listkh_item, null);
            holder = new ViewHolder();
            holder.Ten = (TextView) convertView.findViewById(R.id.tv_hoten);
            holder.STT = (TextView) convertView.findViewById(R.id.tv_stt);
            holder.DiaChi = (TextView) convertView.findViewById(R.id.tv_diachi);
            holder.DanhBo = (TextView) convertView.findViewById(R.id.tv_danhbo);
            holder.GhiChu = (TextView) convertView.findViewById(R.id.tv_ghichu);
            holder.SoTien = (TextView) convertView.findViewById(R.id.tv_sotien);
            //    holder.TrangThai = (ImageView) convertView.findViewById(R.id.img_trangthai);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final KhachHangThuDTO cus = customerList.get(position);
        holder.Ten.setText(cus.getTenKhachHang());
        holder.Ten.setSelected(true);
        Log.e("index", String.valueOf(index_duong));
        if (index_duong == -1) {
            Log.e("AN STT", "OK");
            // holder.STT.setVisibility(View.GONE);
            holder.STT.setText(String.valueOf(position + 1));
        } else {
            Log.e("AN STT", "NO");
            //  holder.STT.setVisibility(View.VISIBLE);
            holder.STT.setText(cus.getSTT());
        }
        holder.STT.setSelected(true);
        holder.DiaChi.setText(cus.getDiaChi());
        holder.DiaChi.setSelected(true);
        holder.DanhBo.setText(cus.getDanhBo());

        if (!cus.getGhichuthu().toString().equals("")) {
            strghichu = "Ghi chú: " + cus.getGhichuthu();
            holder.GhiChu.setVisibility(View.VISIBLE);
            holder.GhiChu.setText(strghichu);

        } else {
            strghichu = "";
            holder.GhiChu.setVisibility(View.GONE);

        }
        holder.SoTien.setVisibility(View.VISIBLE);
        holder.SoTien.setText("Số tiền thu: " + thanhtoandao.getSoTienTongCongTheoMAKH(cus.getMaKhachHang().trim()));
        holder.SoTien.setSelected(true);


        if (thanhtoandao.countKhachHangChuaThuTheoMaKH(cus.getMaKhachHang().trim()) != 0) {
            holder.STT.setBackgroundResource(R.drawable.remove_bg);

        } else {
            holder.STT.setBackgroundResource(R.drawable.remove_bg_daghi);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cus.getNgaythanhtoan().equalsIgnoreCase("")) {
                    //chua thu
                    Log.e("Ma duong dang chon", Bien.ma_duong_dang_chon_thu);
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.tab_thunuoc))
                            .setMessage(context.getString(R.string.list_chuyendulieu_hoithunuoc_khachhang) + " " + cus.getTenKhachHang() + " " + context.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, MainThu2Activity.class);
                                    Bundle bundle = new Bundle();
                                    // bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon_thu);
                                    bundle.putString(Bien.MADUONGTHU, khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                    bundle.putString(Bien.STTTHU, cus.getSTT());
                                    if (index_duong != -1) { //tu tim kiem chuyen qua
                                        bundle.putInt(Bien.VITRITHU, index_duong);
                                    } else {
                                        int vitri = duongdao.getindexDuong(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                        bundle.putInt(Bien.VITRITHU, vitri);
                                    }
                                    bundle.putString(Bien.MAKHTHU, cus.getMaKhachHang());
                                    intent.putExtra(Bien.GOITIN_MADUONGTHU, bundle);
                                    context.startActivity(intent);
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

                } else { // da thu
                    Log.e("Ma duong dang chon", Bien.ma_duong_dang_chon_thu);
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.tab_thunuoc))
                            .setMessage(context.getString(R.string.list_chuyendulieu_hoithunuoc_capnhatkhachhang) + " " + cus.getTenKhachHang() + " " + context.getString(R.string.list_chuyendulieu_hoighinuoc2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, MainThu2Activity.class);
                                    Bundle bundle = new Bundle();
                                    // bundle.putString(Bien.MADUONG, Bien.ma_duong_dang_chon_thu);
                                    bundle.putString(Bien.MADUONGTHU, khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                    bundle.putString(Bien.STTTHU, cus.getSTT());
                                    if (index_duong != -1) {
                                        bundle.putInt(Bien.VITRITHU, index_duong);
                                    } else {
                                        int vitri = duongdao.getindexDuong(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                        bundle.putInt(Bien.VITRITHU, vitri);
                                    }

                                    bundle.putString(Bien.MAKHTHU, cus.getMaKhachHang());

                                    intent.putExtra(Bien.GOITIN_MADUONGTHU, bundle);
                                    context.startActivity(intent);
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

    private static class ViewHolder {

        TextView Ten;
        TextView STT;
        //  ImageView TrangThai;
        TextView DiaChi;
        TextView DanhBo;
        TextView GhiChu;
        TextView SoTien;
    }

}
