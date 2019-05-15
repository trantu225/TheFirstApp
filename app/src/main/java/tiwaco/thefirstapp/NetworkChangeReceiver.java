package tiwaco.thefirstapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tiwaco.thefirstapp.CustomAdapter.APIService;
import tiwaco.thefirstapp.CustomAdapter.ApiUtils;
import tiwaco.thefirstapp.DAO.DuongThuDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DAO.ThanhToanDAO;
import tiwaco.thefirstapp.DTO.BillTamThu;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.LOGNBDTO;
import tiwaco.thefirstapp.DTO.RequestTamThu;
import tiwaco.thefirstapp.DTO.ResponePayTamThu;
import tiwaco.thefirstapp.Database.SPData;

/**
 * Created by Admin on 26/4/2019.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    KhachHangThuDAO khachhangthudao;
    LichSuDAO lichsudao;
    ThanhToanDAO thanhtoandao;
    SPData spdata;
    Context context;
    APIService mAPIService;
    Call<ResponePayTamThu> call = null;

    @Override
    public void onReceive(Context con, Intent intent) {
        this.context = con;
        khachhangthudao = new KhachHangThuDAO(context);
        lichsudao = new LichSuDAO(context);
        thanhtoandao = new ThanhToanDAO(context);
        mAPIService = ApiUtils.getAPIService();
        spdata = new SPData(context);
        String urlthu = context.getString(R.string.API_UpdateThuTienNuoc);
        try {
            if (isOnline(context)) {

                if (spdata.getDataTuDongChuyenOffline() == 1) {
                    spdata.luuDataThuOffline(0);
                }
                // Toast.makeText(context, "Online Connect Intenet: "+ getNetworkType(context), Toast.LENGTH_SHORT).show();
                // dialog(true);
                Log.e("keshav", "Online Connect Intenet: " + getNetworkType(context));
                Log.e("so luong hd thu", String.valueOf(thanhtoandao.GetSoLuongThanhToanTamThu()));
                if (getNetworkType(context).equals("4g") || getNetworkType(context).equals("wifi")) {
                    Log.e("so luong hd thu", String.valueOf(thanhtoandao.GetSoLuongThanhToanTamThu()));
                    if (thanhtoandao.GetSoLuongThanhToanTamThu() > 0) {
                        if (spdata.getDataLuuTuDongThu() == 1) {
                            UpdateThanhToanThuTamHDRetrofit();
                        }

                    }
                }

            } else {
                if (spdata.getDataTuDongChuyenOffline() == 1) {
                    spdata.luuDataThuOffline(1);
                }

//                Toast.makeText(context, "Conectivity Failure ", Toast.LENGTH_SHORT).show();
                //  dialog(false);
                Log.e("keshav", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        String kq = "";
        if (info != null && info.isAvailable()) {

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                kq = "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {

                TelephonyManager mTelephonyManager = (TelephonyManager)
                        context.getSystemService(Context.TELEPHONY_SERVICE);
                int networkType = mTelephonyManager.getNetworkType();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        kq = "2g";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        /**
                         From this link https://en.wikipedia.org/wiki/Evolution-Data_Optimized ..NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_A
                         EV-DO is an evolution of the CDMA2000 (IS-2000) standard that supports high data rates.

                         Where CDMA2000 https://en.wikipedia.org/wiki/CDMA2000 .CDMA2000 is a family of 3G[1] mobile technology standards for sending voice,
                         data, and signaling data between mobile phones and cell sites.
                         */
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        //Log.d("Type", "3g");
                        //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                        //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                        //Some cases are added after  testing(real) in device with 3g enable data
                        //and speed also matters to decide 3g network type
                        //https://en.wikipedia.org/wiki/4G#Data_rate_comparison
                        kq = "3g";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        //No specification for the 4g but from wiki
                        //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                        //https://en.wikipedia.org/wiki/LTE_(telecommunication)
                        kq = "4g";
                        break;
                    default:
                        kq = "Notfound";
                        break;
                }
            }
        } else {
            kq = "Notfound";
        }

        return kq;
    }

    public void UpdateThanhToanThuTamHDRetrofit() {

        try {
            List<BillTamThu> listbill = null;
            listbill = thanhtoandao.GetThanhToanTamThu();
            RequestTamThu jsondata = new RequestTamThu();
            if (listbill.size() > 0) {
                jsondata.setUserName(spdata.getDataNhanVienTrongSP());
                jsondata.setPassWord(spdata.getDataMatKhauNhanVienTrongSP());
                jsondata.setListTamThu(listbill);
                jsondata.setPaymentChannel("TT");
                String thoigian2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                jsondata.setRequestTime(thoigian2);
                final ArrayList<String> myListerror = new ArrayList<String>();
                if (call == null) {
                    call = mAPIService.updatetamthu(jsondata);


                    final List<BillTamThu> finalListbill = listbill;
                    call.enqueue(new Callback<ResponePayTamThu>() {
                        @Override
                        public void onResponse(Call<ResponePayTamThu> call, Response<ResponePayTamThu> response) {
                            //  Toast.makeText(MainActivity.this, "Все прошло хорошо",Toast.LENGTH_SHORT).show();
                            call = null;

                            int code = response.body().getResponseCode();
                            String desc = response.body().getResponseDesc();

                            if (code == 0) {
                                //Update vào database
                                for (BillTamThu ma : finalListbill) {
                                    if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                        if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                        }
                                    }
                                }
                            } else if (code == -5) {
                                try {
                                    desc += ".Kiểm tra lại danh sách lỗi";
                                    int capnhatlaichuaupdate = 0;

                                    List<LOGNBDTO> tong = response.body().getListError();
                                    List<String> DanhSachLoi = new ArrayList<>();
                                    for (int i = 0; i < tong.size(); i++) {
                                        String returndescerr = "";
                                        LOGNBDTO objKHLOI = tong.get(i);
                                        if (!objKHLOI.getRETURNCODEDESC().equals("")) {
                                            returndescerr = objKHLOI.getRETURNCODEDESC();
                                        }
                                        Log.e("Loi hien thi", returndescerr);
                                        if (!objKHLOI.getMAKH().equals("")) {
                                            String maKH = objKHLOI.getMAKH().trim();
                                            KhachHangThuDTO kherror = khachhangthudao.getKHTheoMaKH(maKH.trim());
                                            String maduong = khachhangthudao.getMaDuongTheoMaKhachHang(maKH.trim());
                                            String chuoihienthi = "Đường:" + maduong + "- Danh bộ:" + kherror.getDanhBo() + " - Tên:" + kherror.getTenKhachHang() + " -  Lỗi: " + returndescerr;
                                            myListerror.add(chuoihienthi);
                                            DanhSachLoi.add(maKH);
//                                        if (khachangdao.updateTrangThaiCapNhat(maKH, "0")) {
//                                            capnhatlaichuaupdate++;
//                                        }
                                        }

                                    }

                                    for (BillTamThu ma : finalListbill) {
                                        if (thanhtoandao.updateThanhToanTrangThaiTamThu(ma.getCustNo(), "2")) {
                                            if (khachhangthudao.updateKhachHangTamThuCapNhatServer(ma.getCustNo(), "2")) {

                                            }
                                        }
                                    }
                                    if (DanhSachLoi.size() > 0) {
                                        for (String loi : DanhSachLoi) {
                                            if (khachhangthudao.updateKhachHangTamThuCapNhatServer(loi, "1")) {
                                                if (thanhtoandao.updateThanhToanTrangThaiTamThu(loi, "1")) {
                                                }
                                                capnhatlaichuaupdate++;
                                            }
                                        }
                                    }


                                    if (capnhatlaichuaupdate == DanhSachLoi.size()) {
                                        //if (capnhatlaichuaupdate == Integer.parseInt(jsondata.getTongSLkh())) {
                                        Toast.makeText(context, "Cập nhật dữ liệu thất bại", Toast.LENGTH_LONG).show();

                                    }
                                } catch (Exception e) {

                                }

                            }


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            // khởi tạo dialog
                            alertDialogBuilder.setMessage(desc);
                            // thiết lập nội dung cho dialog

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    // button "no" ẩn dialog đi
                                }
                            });
                            if (code == -5) {
                                alertDialogBuilder.setPositiveButton("DS Lỗi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();


                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                                        View convertView = (View) inflater.inflate(R.layout.lishkh_error, null);
                                        alertDialog.setView(convertView);
                                        alertDialog.setTitle("Danh sách lỗi");
                                        ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, myListerror);
                                        lv.setAdapter(adapter);
                                        alertDialog.show();
                                        // button "no" ẩn dialog đi
                                    }
                                });
                            }

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // tạo dialog
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();

                        }

                        @Override
                        public void onFailure(Call<ResponePayTamThu> call, Throwable t) {
                            call = null;

                            Log.e("LOI ", "Unable to submit post to API." + t.getMessage());
                        }
                    });
                }
            } else {

                Log.e("LOI ", "Không có list");
            }


        } catch (Exception e) {
            call = null;

            Log.e("loi retrofit", e.toString());
        }

    }

}