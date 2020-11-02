package tiwaco.thefirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tiwaco.thefirstapp.CustomAdapter.APIService;
import tiwaco.thefirstapp.CustomAdapter.ApiUtils;
import tiwaco.thefirstapp.CustomAdapter.CustomListThu2Adapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListThuHoAdapter;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.BillTamThu;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.LOGNBDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.PeriodDTO;
import tiwaco.thefirstapp.DTO.PeriodNhanVienThu;
import tiwaco.thefirstapp.DTO.RequestPayBillAllNB;
import tiwaco.thefirstapp.DTO.RequestPayThuHo;
import tiwaco.thefirstapp.DTO.ResponePayTamThu;
import tiwaco.thefirstapp.DTO.ResponseObjectNhieuHDNB;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.DTO.TienNuocInFo;
import tiwaco.thefirstapp.Database.SPData;

/**
 * Created by Admin on 21/10/2019.
 */

public class ViewDialog_DanhSachThuHo {

    public String getTongTien(AppCompatActivity activity, String maduong) {
        KhachHangThuDAO khachhangdao = new KhachHangThuDAO(activity);
        ThanhToanDAO thanhtoandao = new ThanhToanDAO(activity);


        List<KhachHangThuDTO> listkhthuho = khachhangdao.getAllKHTrongDanhSachThuHo(maduong);
        int tongtien = 0;
        for (KhachHangThuDTO kh : listkhthuho) {
            List<ThanhToanDTO> listhoadon = thanhtoandao.GetListThanhToanTheoMaKH(kh.getMaKhachHang());

            for (ThanhToanDTO hoadonthuho : listhoadon) {


                tongtien += Integer.parseInt(hoadonthuho.gettongcong());
            }


        }
        DecimalFormatSymbols decimalFormatSymbols;
        DecimalFormat format, format1, format2;
        decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);
        String tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(tongtien)))) + " đ";
        return tongcong;
    }

    public void showDialog(final AppCompatActivity activity, final String maduong, final LinearLayout laythuho, final TextView txttongtien) {
        final LichSuDAO lichsudao = new LichSuDAO(activity);
        final KhachHangThuDAO khachhangDAO = new KhachHangThuDAO(activity);
        final SPData spdata = new SPData(activity);
        final ThanhToanDAO thanhtoandao = new ThanhToanDAO(activity);
        final List<KhachHangThuDTO> listthuho = khachhangDAO.getAllKHTrongDanhSachThuHo(Bien.ma_duong_dang_chon_thu);

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_thuho);
        Button dialogButton_thoat = (Button) dialog.findViewById(R.id.btn_thoat);
        Button dialogButton_xoathuho = (Button) dialog.findViewById(R.id.btn_xoalist);
        final Button dialogButton_thanhtoan = (Button) dialog.findViewById(R.id.btn_thanhtoanvainbiennhan);
        if (listthuho.size() > 0) {
            dialogButton_thanhtoan.setEnabled(true);
        }

        final TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        String kyhd = "Danh sách thu hộ";

        text.setText(getTongTien(activity, maduong));
        final CustomListThuHoAdapter thuhodapter = new CustomListThuHoAdapter(activity, listthuho, spdata.getDataIndexDuongDangThuTrongSP(), laythuho, txttongtien, text);

        final ListView exlist = (ListView) dialog.findViewById(R.id.ListThuHo);
        exlist.setAdapter(thuhodapter);


        dialogButton_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialogButton_xoathuho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final KhachHangThuDAO khachhangdao = new KhachHangThuDAO(activity);
                if (khachhangdao.getAllKHTrongDanhSachThuHo(maduong).size() > 0) {
                    if (khachhangdao.updateXoaThuHo()) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                        // khởi tạo dialog

                        alertDialogBuilder.setMessage("Xóa các khách hàng thu hộ thành công");

                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  new UpdateThongTinThuNuoc().execute(urlstr);

                                thuhodapter.setData(khachhangdao.getAllKHTrongDanhSachThuHo(maduong));
                                thuhodapter.notifyDataSetChanged();

                                Bien.adapterKHThu.notifyDataSetChanged();
                                laythuho.setVisibility(View.GONE);
                                txttongtien.setText("0 đ");
                                text.setText("0 đ");
                                dialogButton_thanhtoan.setEnabled(false);
                                dialog.dismiss();
                            }
                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // tạo dialog
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Không có khách hàng nào trong danh sách thu hộ");

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  new UpdateThongTinThuNuoc().execute(urlstr);


                            dialog.dismiss();
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            }
        });

        dialogButton_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tạo json danh sách thu hộ
                RequestPayThuHo jsondata = new RequestPayThuHo();
                final List<KhachHangThuDTO> listKHTHUHO = khachhangDAO.getAllKHTrongDanhSachThuHo(maduong);
                if (listKHTHUHO.size() > 0) {
                    List<String> listKH = new ArrayList<String>();
                    List<ThanhToanDTO> listbill = new ArrayList<ThanhToanDTO>();
                    List<PeriodDTO> listbillsend = new ArrayList<>();

                    for (KhachHangThuDTO KH : listKHTHUHO) {
                        listKH.add(KH.getMaKhachHang());
                        listbill = thanhtoandao.GetListThanhToanTheoMaKH(KH.getMaKhachHang());
                        for (ThanhToanDTO bill : listbill) {
                            PeriodDTO p = new PeriodDTO();
                            p.setBillNo(bill.getMaKhachHang());
                            p.setPeriodNum(bill.getKyHD());
                            p.setTotalMoney(Integer.parseInt(bill.gettongcong()));
                            listbillsend.add(p);

                        }
                    }

                    jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                    jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                    jsondata.setPeriodNums(listbillsend);
                    final String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                    jsondata.setRequestTime(thoigian2);
                    jsondata.setListKH(listKH);
                    Gson gson = new Gson();
                    String jsonrequest = gson.toJson(jsondata);
                    Log.e("JSONREQUESTTHUHO", jsonrequest);
                    Call<ResponseObjectNhieuHDNB> call = null;
                    APIService mAPIService = ApiUtils.getAPIService();
                    call = mAPIService.ThuHo(jsondata);
                    call.enqueue(new Callback<ResponseObjectNhieuHDNB>() {
                        @Override
                        public void onResponse(Call<ResponseObjectNhieuHDNB> call, Response<ResponseObjectNhieuHDNB> response) {
                            //  Toast.makeText(MainActivity.this, "Все прошло хорошо",Toast.LENGTH_SHORT).show();

                            if (response.isSuccessful()) {

                                int code = response.body().getResponseCode();
                                String desc = response.body().getResponseDesc();
                                String thoigian = response.body().getResponseDate();
                                List<TienNuocInFo> datainfo = response.body().getDataInfo();
                                List<PeriodNhanVienThu> DataDaThu = response.body().getDataDaThu();
                                List<LOGNBDTO> ListError = response.body().getListError();

                                if (code == -2 || code == -3 || code == -4 || code == -5) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                                    // khởi tạo dialog

                                    alertDialogBuilder.setMessage(desc);

                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  new UpdateThongTinThuNuoc().execute(urlstr);
                                            dialog.dismiss();
                                        }
                                    });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();

                                } else if (code == 0) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage(desc);
                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  new UpdateThongTinThuNuoc().execute(urlstr);
                                            dialog.dismiss();

                                            //Cập nhật tiền nước,

                                        }
                                    });
                                    if (ListError.size() > 0) {
                                        final List<String> myListerror = new ArrayList<String>();
                                        for (int i = 0; i < ListError.size(); i++) {

                                            String chuoihienthi = "Mã KH:" + ListError.get(i).getMAKH() + " -  Lỗi: " + ListError.get(i).getRETURNCODEDESC();
                                            myListerror.add(chuoihienthi);
                                        }
                                        alertDialogBuilder.setPositiveButton("Danh sách lỗi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //  new UpdateThongTinThuNuoc().execute(urlstr);
                                                dialog.dismiss();

                                                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(activity);
                                                LayoutInflater inflater = activity.getLayoutInflater();
                                                View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                                alertDialog.setView(convertView);
                                                alertDialog.setTitle("Danh sách lỗi");
                                                ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, myListerror);
                                                lv.setAdapter(adapter);
                                                alertDialog.show();
                                            }
                                        });
                                    } else {
                                        for (KhachHangThuDTO khthuho : listKHTHUHO) {
                                            if (khachhangDAO.updateKhachHangThanhToan(khthuho.getMaKhachHang(), thoigian, spdata.getDataNhanVienTrongSP())) {


                                                if (thanhtoandao.updateThanhToanTheoMaKh(khthuho.getMaKhachHang(), thoigian, "THUHO" + khthuho.getMaKhachHang(), spdata.getDataNhanVienTrongSP())) {
                                                    String thoigian3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                                    LichSuDTO ls = new LichSuDTO();
                                                    ls.setNoiDungLS("Thu hộ tiền nước  khách hàng có mã " + khthuho.getMaKhachHang());
                                                    ls.setMaLenh("TN");
                                                    ls.setThoiGianLS(thoigian3);
                                                    lichsudao.addTable_History(ls);

                                                }


                                            }
                                        }
                                    }

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();


                                } else if (code == 1) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                                    // khởi tạo dialog
                                    alertDialogBuilder.setMessage(desc);
                                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  new UpdateThongTinThuNuoc().execute(urlstr);
                                            dialog.dismiss();

                                            //Cập nhật tiền nước,

                                        }
                                    });
                                    if (ListError.size() > 0) {
                                        final List<String> myListerror = new ArrayList<String>();
                                        for (int i = 0; i < ListError.size(); i++) {

                                            String chuoihienthi = "Mã KH:" + ListError.get(i).getMAKH() + " -  Lỗi: " + ListError.get(i).getRETURNCODEDESC();
                                            myListerror.add(chuoihienthi);
                                        }
                                        alertDialogBuilder.setPositiveButton("Danh sách lỗi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //  new UpdateThongTinThuNuoc().execute(urlstr);
                                                dialog.dismiss();

                                                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(activity);
                                                LayoutInflater inflater = activity.getLayoutInflater();
                                                View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                                alertDialog.setView(convertView);
                                                alertDialog.setTitle("Danh sách lỗi");
                                                ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, myListerror);
                                                lv.setAdapter(adapter);
                                                alertDialog.show();
                                            }
                                        });
                                    } else {
                                        for (KhachHangThuDTO khthuho : listKHTHUHO) {
                                            if (khachhangDAO.updateKhachHangThanhToan(khthuho.getMaKhachHang(), thoigian, spdata.getDataNhanVienTrongSP())) {


                                                if (thanhtoandao.updateThanhToanTheoMaKh(khthuho.getMaKhachHang(), thoigian, "THUHO" + khthuho.getMaKhachHang(), spdata.getDataNhanVienTrongSP())) {
                                                    String thoigian3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                                    LichSuDTO ls = new LichSuDTO();
                                                    ls.setNoiDungLS("Thu hộ tiền nước  khách hàng có mã " + khthuho.getMaKhachHang());
                                                    ls.setMaLenh("TN");
                                                    ls.setThoiGianLS(thoigian3);
                                                    lichsudao.addTable_History(ls);

                                                }


                                            }
                                        }

                                        for (PeriodNhanVienThu hoadondathu : DataDaThu) {

                                            if (khachhangDAO.updateKhachHangThanhToan(hoadondathu.getCustNo(), hoadondathu.getThoiGianThu(), hoadondathu.getNhanVienThu())) {

                                                if (thanhtoandao.updateThanhToanTheoMaKh(hoadondathu.getCustNo(), hoadondathu.getThoiGianThu(), hoadondathu.getTransactionID(), hoadondathu.getNhanVienThu())) {
                                                }
                                            }
                                        }
                                    }

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // tạo dialog
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                } else if (code == 4) {


                                }


                            } else {
                                call.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseObjectNhieuHDNB> call, Throwable t) {
                            Log.e("LOI ", "Unable to submit post to API." + t.getCause().toString());
                            call.cancel();
                        }
                    });
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    // khởi tạo dialog

                    alertDialogBuilder.setMessage("Không có khách hàng nào trong danh sách thu hộ");

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  new UpdateThongTinThuNuoc().execute(urlstr);


                            dialog.dismiss();
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // tạo dialog
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }

            }
        });

        dialog.show();

    }
}
