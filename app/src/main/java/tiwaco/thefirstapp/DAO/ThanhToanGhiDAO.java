package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import tiwaco.thefirstapp.DTO.BillTamThu;
import tiwaco.thefirstapp.DTO.JSONTHANHTOANTHU;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.ObjectThu;
import tiwaco.thefirstapp.DTO.ThanhToanGhiDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;
import tiwaco.thefirstapp.Database.SPData;

/**
 * Created by TUTRAN on 24/07/2018.
 */

public class ThanhToanGhiDAO {

    SQLiteDatabase db;
    MyDatabaseHelper myda;
    DecimalFormatSymbols decimalFormatSymbols;
    DecimalFormat format, format1, format2;
    KhachHangDAO khachhangdao;


    public ThanhToanGhiDAO(Context con) {
        myda = new MyDatabaseHelper(con);
        //     db = myda.openDB();
        //    myda.resetDatabase(db);
        decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        format = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        format1 = new DecimalFormat("0.#");
        format2 = new DecimalFormat("#,##0.#", decimalFormatSymbols);
        khachhangdao = new KhachHangDAO(con);
    }


    public boolean addTable_ThanhToan(ThanhToanGhiDTO kh, String maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI, kh.getBienLai().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH, kh.getMaKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU, kh.getChiSoCu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI, kh.getChiSoMoi().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU, kh.getSLTieuThu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD, kh.getKyHD().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID, kh.getTransactionID());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG, maduong.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC, kh.getTienNuoc().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_PHI, kh.getphi().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG, kh.gettongcong().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_M31, kh.getM3t1().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_M32, kh.getM3t2().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_M33, kh.getM3t3().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_M34, kh.getM3t4().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1, kh.getTien1().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2, kh.getTien2().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3, kh.getTien3().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4, kh.getTien4().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_THUE, kh.getThue().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN, kh.getNgaythanhtoan().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU, kh.getNhanvienthu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN, "0");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO, "0");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_BS1, "");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_BS2, "");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_BS3, "");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_BS4, "");


        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_THANHTOANGHI, null, values);
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }


    public int countThanhToanAll() {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI;
        //  String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor == null) {
            sokh = 0;
        } else {
            sokh = cursor.getCount();
        }
        cursor.close();
        db.close();
        Log.e("sokh", String.valueOf(sokh));
        return sokh;


    }


    public List<String> getListKyHDThanhToanTheoMaKH(String makh) {
        db = myda.openDB();
        List<String> ListKyHD = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String kyhd = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD));


                // Adding contact to list
                ListKyHD.add(kyhd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong kyhd", String.valueOf(ListKyHD.size()));
        return ListKyHD;
    }


    public List<ThanhToanGhiDTO> GetThanhToanTheoKyHDVaMaKH(String makh, String kyhd) {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD + " = '" + kyhd + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }


    public List<ThanhToanGhiDTO> GetListThanhToanTheoMaKH(String makh) {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "'  order by " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }


    public List<ThanhToanGhiDTO> GetListThanhToanTheoMaKHChuaThanhToan(String makh) {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " = '' order by " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }


    public List<ThanhToanGhiDTO> GetListThanhToanTheoMaKHDaThanhToan(String makh) {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " <> '' order by " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }

    public List<ThanhToanGhiDTO> GetListThanhToanTheoMaKHDaThanhToanTheoNV(String makh, String tennhanvien) {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " <> '' " + " and " + MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU + " = '" + tennhanvien + "' order by " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }


    public List<ThanhToanGhiDTO> getTatCaThanhToan() {
        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT;
    }


    public List<String> GetSoLanIn(String bienlai) {
        db = myda.openDB();
        List<String> ListIn = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI + " = '" + bienlai + "' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String laninbiennhan = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN));

                String laninthongbaotruoc = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO));


                // Adding contact to list
                ListIn.add(laninbiennhan);
                ListIn.add(laninthongbaotruoc);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListIn.size()));
        return ListIn;
    }

    public List<String> GetSoLanInTheoMaVaKyHD(String makh, String kyhd) {
        db = myda.openDB();
        List<String> ListIn = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = '" + makh + "' and  " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD + " = '" + kyhd + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String laninbiennhan = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN));

                String laninthongbaotruoc = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO));


                // Adding contact to list
                ListIn.add(laninbiennhan);
                ListIn.add(laninthongbaotruoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListIn.size()));
        return ListIn;
    }

    public boolean updateThanhToan(String bienlai, String thoigian, String nhanvienthu, String transid) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU, nhanvienthu.trim());
        //values.put(MyDatabaseHelper.KEY_THANHTOANGHI_CAPNHATTHU, "1");
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID, transid.trim());


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_THANHTOANGHI, values, MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI + " = ?", new String[]{bienlai}) > 0;
        db.close();
        return kt;

    }

    public boolean updateThanhToanTheoMaKhvaKyHD(String makh, String thoigian, String transid, String nhanvienthu, String kyhd) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU, nhanvienthu.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID, transid.trim());
        //values.put(MyDatabaseHelper.KEY_THANHTOANGHI_CAPNHATTHU, "1");


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_THANHTOANGHI, values, MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = ? and " + MyDatabaseHelper.KEY_THANHTOANGHI_KYHD + " = ? ", new String[]{makh, kyhd}) > 0;
        db.close();
        return kt;

    }

    public boolean updateThanhToanTheoMaKh(String makh, String thoigian, String transid, String nhanvienthu) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU, nhanvienthu.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID, transid.trim());
        //values.put(MyDatabaseHelper.KEY_THANHTOANGHI_CAPNHATTHU, "1");


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_THANHTOANGHI, values, MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + " = ? ", new String[]{makh}) > 0;
        db.close();
        return kt;

    }


    public boolean updateLanIn(String bienlai, String biennhan, String thbaotruoc, String thbaosau) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN, biennhan);
        values.put(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO, thbaotruoc);


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_THANHTOANGHI, values, MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI + " = ?", new String[]{bienlai}) > 0;
        db.close();
        return kt;

    }


    public boolean tangSoLanIn(String bienlai, int solanbienhan, int solantbtruoc, int solantbsau) {
        List<String> listsolanin = GetSoLanIn(bienlai);
        String solaninbiennhan = String.valueOf(Integer.parseInt(listsolanin.get(0)) + solanbienhan);
        String solanintbtruoc = String.valueOf(Integer.parseInt(listsolanin.get(1)) + solantbtruoc);
        String solanintbsau = String.valueOf(Integer.parseInt(listsolanin.get(2)) + solantbsau);
        Log.e("so lan in sau", solanintbsau);
        if (updateLanIn(bienlai, solaninbiennhan, solanintbtruoc, solanintbsau)) {
            return true;
        }
        return false;
    }


    public int countKhachHangChuaThuTheoDuong(String maduong) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangChuaThuTheoMaKH(String makh) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "'and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }


    public int countHDTheoMaKH(String makh) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "' ";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countHDChuaThuTheoMaKH(String makh) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countSoHDDaThuTuNgayDenNgay(String ngaytu, String ngayden) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE (" + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + ">='" + ngaytu + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " <='" + ngayden + "'and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countSoHDDaThuTheoNgay(String ngay) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "LIKE '%" + ngay + "%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public String getSoTienTongCongTheoMAKH(String makh) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String getSoTienTongCongChuaThuTheoMAKH(String makh) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "=''";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String formatTien(int tien) {
        String tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(tien)))) + " đ";
        return tongcong;
    }


    public int getSoTienTongCongTheoMAKHKhongFormat(String makh) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return 0;
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }


            return summoney;
        }


    }

    public int getSoHDTheoMAKHKhongFormat(String makh) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MAKH + "='" + makh + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return 0;
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }


            return ListTT.size();
        }


    }


    public String getSoTienTongCong() {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String getSoTienTongCongDaThuTheoNgay(String ngay) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " LIKE '%" + ngay + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String getSoTienTongCongDaThuTheoNgayTheoTen(String ngay, String tennv) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " LIKE '%" + ngay + "%' and " + MyDatabaseHelper.KEY_DANHSACHKH_NHANVIENTHU + " like '%" + tennv + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String getSoTienTongCongDaThu() {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>''";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));
//                if(Double.parseDouble(kh.getNgaythanhtoan()) >= 20181214000000.00 &&Double.parseDouble(kh.getNgaythanhtoan()) < 20181215000000.00 ) {
//                    // Adding contact to list
//                    Log.e("makh",kh.getMaKhachHang());
                ListTT.add(kh);
                //      }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public String getSoTienTongCongDaThuTheoTen(String tennv) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>'' and " + MyDatabaseHelper.KEY_DANHSACHKH_NHANVIENTHU + " like '%" + tennv + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));
//                if(Double.parseDouble(kh.getNgaythanhtoan()) >= 20181214000000.00 &&Double.parseDouble(kh.getNgaythanhtoan()) < 20181215000000.00 ) {
//                    // Adding contact to list
//                    Log.e("makh",kh.getMaKhachHang());
                ListTT.add(kh);
                //      }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }


    }

    public int getHDTongCong() {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT.size();


    }

    public int getSoHDDaThuTheoNgay(String ngay) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " LIKE '%" + ngay + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return ListTT.size();

    }

    public int getSoHDDaThuTheoNgayTheoTen(String ngay, String tennv) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " LIKE '%" + ngay + "%' and " + MyDatabaseHelper.KEY_DANHSACHKH_NHANVIENTHU + " like '%" + tennv + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return ListTT.size();

    }

    public int getSoHDDaThu() {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>''";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT.size();

    }


    public int getSoHDDaThuTheoTen(String tennv) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>'' and " + MyDatabaseHelper.KEY_DANHSACHKH_NHANVIENTHU + " like  '%" + tennv + "%'";
        ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT.size();

    }

    public String getTimeTheoFormat(String yyyyMMddHHmmss)//format yyyyMMddHHmmss
    {
        String nam = yyyyMMddHHmmss.substring(0, 4);
        String thang = yyyyMMddHHmmss.substring(4, 6);
        String ngay = yyyyMMddHHmmss.substring(6, 8);
        String gio = yyyyMMddHHmmss.substring(8, 10);
        String phut = yyyyMMddHHmmss.substring(10, 12);
        String giay = yyyyMMddHHmmss.substring(12, 14);
        String datenhan = nam + "-" + thang + "-" + ngay + " " + gio + ":" + phut + ":" + giay;
        return datenhan;
    }

    public List<ObjectThu> getHDDaThuTheoTen(String tennv) {

        db = myda.openDB();
        List<ObjectThu> ListTT = new ArrayList<ObjectThu>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + "<>'' and " + MyDatabaseHelper.KEY_DANHSACHKH_NHANVIENTHU + "= '" + tennv + "'";
        ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ObjectThu kh = new ObjectThu();
                kh.setBIENLAI(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMAKH(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setTHANG(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setTONGCONG(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                String thoigianthu = getTimeTheoFormat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setTHOIGIANTHU(thoigianthu);

                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT;

    }

    // 0: tat ca, 1: da thu, 2: chua thu, 3: da thu hom nay
    public String getSoHDTheoMaDuongPhanLoai(int loai, String maduong) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "";
        if (loai == 0) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "'";
        } else if (loai == 1) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " <> '' ";
        } else if (loai == 2) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " = '' ";
        } else if (loai == 3) {
            String thoigiantu = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());// + "000000";

            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " like '%" + thoigiantu + "%' ";
        }
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        if (ListTT.size() == 0) {
            return "0";
        } else {
            return String.valueOf(ListTT.size());
        }

    }


    // 0: tat ca, 1: da thu, 2: chua thu, 3: da thu hom nay
    public String getSoTienTheoMaDuongPhanLoai(int loai, String maduong) {

        db = myda.openDB();
        List<ThanhToanGhiDTO> ListTT = new ArrayList<ThanhToanGhiDTO>();
        // Select All Query
        String selectQuery = "";
        if (loai == 0) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "'";
        } else if (loai == 1) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " <> '' ";
        } else if (loai == 2) {
            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " = '' ";
        } else if (loai == 3) {
            String thoigiantu = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());//+ "000000";

            selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOANGHI + " WHERE " + MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG + " = '" + maduong + "' and " + MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN + " like '%" + thoigiantu + "%' ";
        }
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanGhiDTO kh = new ThanhToanGhiDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MAKH)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_CHISOCU)));
                kh.setMaDuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_MADUONG)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_SLTIEUTHU)));
                kh.setTransactionID(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TRANSACTIONID)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_NGAYTHANHTOAN)));
                kh.setLanINBN(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINBIENNHAN)));
                kh.setLanINTB(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOANGHI_LANINTHONGBAO)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if (ListTT.size() == 0) {
            return " 0 đ";
        } else {
            String tongcong = "";
            int summoney = 0;
            for (ThanhToanGhiDTO t : ListTT) {
                int money = Integer.parseInt(t.gettongcong());
                summoney += money;
            }

            tongcong = " " + format2.format(Double.parseDouble(format1.format(Double.valueOf(summoney)))) + " đ";
            return tongcong;
        }

    }

    public String taoTransactionID(String manv, String makh) {
        String thoigian1 = new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime());
        return "TWC" + manv + thoigian1 + makh;
    }


}
