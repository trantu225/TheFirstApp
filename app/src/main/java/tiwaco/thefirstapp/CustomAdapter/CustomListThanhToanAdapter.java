package tiwaco.thefirstapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.Database.SPData;
import tiwaco.thefirstapp.GPSTracker;
import tiwaco.thefirstapp.MainActivity;
import tiwaco.thefirstapp.MainThuActivity;
import tiwaco.thefirstapp.R;
import tiwaco.thefirstapp.StartActivity;
import tiwaco.thefirstapp.ViewDialog;

/**
 * Created by TUTRAN on 18/05/2017.
 */

public class CustomListThanhToanAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CustomAdapter";
    Context mContext;
    private List<String> mHeaderGroup;
    private HashMap<String, List<ThanhToanDTO>> mDataChild;
    private List<ThanhToanDTO> customerList;
    private LayoutInflater layoutInflater;
    int index_duong;
    SPData spdata;
    DuongThuDAO duongdao;
    KhachHangThuDAO khachhangdao;
    ThanhToanDAO thanhtoandao;
    String strghichu = "", vido = "", kinhdo = "";
    DecimalFormat format, format1, format2;
    TextView duong, duongthu, conlai;

    public CustomListThanhToanAdapter(Context context, List<String> headerGroup, HashMap<String, List<ThanhToanDTO>> datas, String vd, String kd, TextView lbduong, TextView lbduongdangthu, TextView lbconlai) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = datas;
        vido = vd;
        kinhdo = kd;
        duong = lbduong;
        duongthu = lbduongdangthu;
        conlai = lbconlai;

        spdata = new SPData(context);
        duongdao = new DuongThuDAO(context);
        khachhangdao = new KhachHangThuDAO(context);
        thanhtoandao = new ThanhToanDAO(context);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public ThanhToanDTO getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
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
            convertView = li.inflate(R.layout.listkyhd_item, parent, false);
        }
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);

        //TextView soketqua = (TextView) convertView.findViewById(R.id.tv_conlaiTK);
        final TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_kyhd);
        final TextView tvthongbao = (TextView) convertView.findViewById(R.id.tv_thongbao);
        //final Button btnthanhtoan = (Button) convertView.findViewById(R.id.btn_thanhtoan);
        tvthongbao.setVisibility(View.GONE);
        String kihd = "";
        if (!mHeaderGroup.get(groupPosition).toString().equals("")) {
            String nam = mHeaderGroup.get(groupPosition).toString().substring(0, 4);
            String thang = mHeaderGroup.get(groupPosition).toString().substring(4);
            String strkyhd = thang + "/" + nam;
            kihd = "Kỳ: " + strkyhd + " ";
        }


        tvHeader.setText(kihd);
        //Log.e("KyhD: ", mHeaderGroup.get(groupPosition).toString());


        final ThanhToanDTO cus = getChild(groupPosition, 0);
        if (cus.getNgaythanhtoan().equals("")) {
            tvthongbao.setText("Chưa thanh toán");
        } else {

            tvthongbao.setText("Đã thanh toán ");
        }

        //Kiem tra thanh toan chua. neu da thanh toan hiển thị đã thanh toán
        final String finalKihd = kihd;



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.listthanhtoan_item, null);

            holder = new CustomListThanhToanAdapter.ViewHolder();
            holder.BienLai = (TextView) convertView.findViewById(R.id.tv_bienlai);
            holder.ChiSoCu = (TextView) convertView.findViewById(R.id.tv_label_cscu);
            holder.ChiSoMoi = (TextView) convertView.findViewById(R.id.tv_label_csmoi);
            holder.M3 = (TextView) convertView.findViewById(R.id.tv_label_m3);
            holder.SoTienThanhToan = (TextView) convertView.findViewById(R.id.tv_tongcong);
            convertView.setTag(holder);

        } else {
            holder = (CustomListThanhToanAdapter.ViewHolder) convertView.getTag();
        }
        final ThanhToanDTO cus = getChild(groupPosition, childPosition);
        // final KhachHangDTO cus = customerList.get(childPosition);
        holder.BienLai.setText("Biên lai: " + cus.getBienLai());
        holder.BienLai.setSelected(true);
        holder.ChiSoCu.setText("CSC: " + cus.getChiSoCu());
        holder.ChiSoMoi.setText("CSM: " + cus.getChiSoMoi());
        holder.M3.setText("M3: " + cus.getSLTieuThu());
        holder.ChiSoCu.setSelected(true);
        holder.ChiSoMoi.setSelected(true);
        holder.M3.setSelected(true);
        holder.BienLai.setSelected(true);
        holder.SoTienThanhToan.setText("Số tiền: " + format2.format(Double.parseDouble(format1.format(Double.valueOf(cus.gettongcong())))) + " đ");



        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class ViewHolder {

        TextView BienLai;
        TextView ChiSoCu;
        TextView ChiSoMoi;
        //  ImageView TrangThai;
        TextView M3;
        TextView SoTienThanhToan;

    }


}
