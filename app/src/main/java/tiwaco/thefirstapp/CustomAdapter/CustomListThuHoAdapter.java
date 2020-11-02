package tiwaco.thefirstapp.CustomAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.MainThu2Activity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class CustomListThuHoAdapter extends BaseAdapter {
    private List<KhachHangThuDTO> customerList;
    private LayoutInflater layoutInflater;
    private Context context;
    int index_duong;
    SPData spdata;
    DuongThuDAO duongdao;
    KhachHangThuDAO khachhangdao;
    ThanhToanDAO thanhtoandao;
    LinearLayout layoutthuho;
    TextView tongsotienthuho, tongtiendialog;
    String strghichu = "";
    DecimalFormatSymbols decimalFormatSymbols;
    DecimalFormat format, format1, format2;

    public CustomListThuHoAdapter(Context con, List<KhachHangThuDTO> listcus, int vitriduong, LinearLayout laythuho, TextView sotien, TextView tiendialog) {
        customerList = listcus;
        context = con;
        layoutInflater = LayoutInflater.from(con);
        index_duong = vitriduong;
        layoutthuho = laythuho;
        tongsotienthuho = sotien;
        tongtiendialog = tiendialog;
        spdata = new SPData(context);
        duongdao = new DuongThuDAO(context);
        khachhangdao = new KhachHangThuDAO(context);
        thanhtoandao = new ThanhToanDAO(context);
        decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.listkh_item, null);
            holder = new ViewHolder();
            holder.Ten = (TextView) convertView.findViewById(R.id.tv_hoten);
            holder.STT = (TextView) convertView.findViewById(R.id.tv_stt);
            holder.DiaChi = (TextView) convertView.findViewById(R.id.tv_diachi);
            holder.LBDiaChi = (TextView) convertView.findViewById(R.id.tv_label_diachi);
            holder.DanhBo = (TextView) convertView.findViewById(R.id.tv_danhbo);
            holder.GhiChu = (TextView) convertView.findViewById(R.id.tv_ghichu);
            holder.SoTien = (TextView) convertView.findViewById(R.id.tv_sotien);
            holder.ThuHo = (TextView) convertView.findViewById(R.id.tv_thuho);

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
            // Log.e("AN STT", "OK");
            // holder.STT.setVisibility(View.GONE);
            holder.STT.setText(String.valueOf(position + 1));
        } else {
            Log.e("AN STT", "NO");
            //  holder.STT.setVisibility(View.VISIBLE);
            holder.STT.setText(cus.getSTT());
        }
        holder.STT.setSelected(true);
        holder.DiaChi.setText(cus.getDiaChi());
        holder.DiaChi.setVisibility(View.GONE);
        holder.LBDiaChi.setVisibility(View.GONE);
        holder.DiaChi.setSelected(true);
        holder.DanhBo.setText(cus.getDanhBo());

        if (!cus.getGhichuthu().toString().equals("")) {
            strghichu = "Ghi chú: " + cus.getGhichuthu();
            holder.GhiChu.setVisibility(View.VISIBLE);
            holder.GhiChu.setText(strghichu);
            holder.GhiChu.setVisibility(View.GONE);

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
            if (thanhtoandao.countKhachHangTamThuTrungTheoMaKH(cus.getMaKhachHang().trim()) != 0) {
                holder.STT.setBackgroundResource(R.drawable.remove_bg_thutrung);
            } else {
                holder.STT.setBackgroundResource(R.drawable.remove_bg_daghi);
            }
        }

        String trangthaithuho = khachhangdao.checkTrangThaiThuHoKH(customerList.get(position).getMaKhachHang());
        if (trangthaithuho.equalsIgnoreCase("") || trangthaithuho.equals("0")) {
            holder.ThuHo.setVisibility(View.GONE);
        } else {
            holder.ThuHo.setVisibility(View.GONE);
        }


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("trang thai thu ho", khachhangdao.checkTrangThaiThuHoKH(customerList.get(position).getMaKhachHang()));
                String sotientongcong = thanhtoandao.getSoTienTongCongTheoMAKH(customerList.get(position).getMaKhachHang());
                if (customerList.get(position).getNgaythanhtoan().equalsIgnoreCase("") && !sotientongcong.equalsIgnoreCase(" 0 đ")) {
                    String trangthai = khachhangdao.checkTrangThaiThuHoKH(customerList.get(position).getMaKhachHang());
                    if (!trangthai.equalsIgnoreCase("0") && !trangthai.equalsIgnoreCase("")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Xóa thu hộ");
                        alertDialogBuilder.setMessage("Bạn có muốn xóa khách hàng " + customerList.get(position).getTenKhachHang() + " ra khỏi danh sách thu hộ không?");
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (khachhangdao.updateThuHoKhachHang(customerList.get(position).getMaKhachHang(), "0")) {
                                    //Thêm vào thu hộ

                                    holder.ThuHo.setVisibility(View.GONE);

                                    if (khachhangdao.getAllKHTrongDanhSachThuHo(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang())).size() == 0) {
                                        layoutthuho.setVisibility(View.GONE);
                                        tongsotienthuho.setText("0 đ");
                                        tongtiendialog.setText("0 đ");
                                    } else {

                                        layoutthuho.setVisibility(View.VISIBLE);
                                        List<KhachHangThuDTO> listkhthuho = khachhangdao.getAllKHTrongDanhSachThuHo(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang()));
                                        int tongtien = 0;
                                        for (KhachHangThuDTO kh : listkhthuho) {
                                            List<ThanhToanDTO> listhoadon = thanhtoandao.GetListThanhToanTheoMaKH(kh.getMaKhachHang());

                                            for (ThanhToanDTO hoadonthuho : listhoadon) {


                                                tongtien += Integer.parseInt(hoadonthuho.gettongcong());
                                                Log.e("tongtien", "" + tongtien);
                                            }


                                        }

                                        if (tongtien > 0) {
                                            tongsotienthuho.setText(formatTien(tongtien));
                                            tongtiendialog.setText(formatTien(tongtien));
                                        } else {
                                            tongsotienthuho.setText("0 đ");
                                            tongtiendialog.setText("0 đ");
                                        }

                                    }
                                    customerList.clear();
                                    customerList.addAll(khachhangdao.getAllKHTrongDanhSachThuHo(khachhangdao.getMaDuongTheoMaKhachHang(cus.getMaKhachHang())));
                                    notifyDataSetChanged();
                                    Bien.adapterKHThu.notifyDataSetChanged();
                                }

                                dialog.dismiss();

                            }
                        });

                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();


                            }
                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }
                }
                return false;
            }
        });
        return convertView;
    }

    private static class ViewHolder {

        TextView Ten;
        TextView STT;
        //  ImageView TrangThai;
        TextView DiaChi;
        TextView LBDiaChi;
        TextView DanhBo;
        TextView GhiChu;
        TextView SoTien;
        TextView ThuHo;
    }

    public String formatTien(int tien) {
        String tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(tien)))) + " đ";
        return tongcong;
    }

}
