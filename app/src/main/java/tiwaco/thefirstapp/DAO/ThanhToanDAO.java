package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;

/**
 * Created by TUTRAN on 24/07/2018.
 */

public class ThanhToanDAO {

    SQLiteDatabase db;
    MyDatabaseHelper myda;

    public ThanhToanDAO(Context con) {
        myda = new MyDatabaseHelper(con);
        //     db = myda.openDB();
        //    myda.resetDatabase(db);
    }

    public boolean addTable_ThanhToan(ThanhToanDTO kh, String maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_THANHTOAN_BIENLAI, kh.getBienLai().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_MAKH, kh.getMaKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_GHICHU, kh.getGhiChu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_CHISOCU, kh.getChiSoCu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_CHISOMOI, kh.getChiSoMoi().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_SLTIEUTHU, kh.getSLTieuThu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_KYHD, kh.getKyHD().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_VIDO, kh.getLat().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_KINHDO, kh.getLon().trim());

        values.put(MyDatabaseHelper.KEY_THANHTOAN_MADUONG, maduong.trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TIENNUOC, kh.getTienNuoc().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_PHI, kh.getphi().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TONGCONG, kh.gettongcong().trim());

        values.put(MyDatabaseHelper.KEY_THANHTOAN_M31, kh.getM3t1().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_M32, kh.getM3t2().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_M33, kh.getM3t3().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_M34, kh.getM3t4().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TIEN1, kh.getTien1().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TIEN2, kh.getTien2().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TIEN3, kh.getTien3().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_TIEN4, kh.getTien4().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_THUE, kh.getThue().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_NGAYTHANHTOAN, kh.getNgaythanhtoan().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_CAPNHATTHU, kh.getCapNhatThu().trim());
        values.put(MyDatabaseHelper.KEY_THANHTOAN_NHANVIENTHU, kh.getNhanvienthu().trim());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_THANHTOAN, null, values);
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<ThanhToanDTO> TimKiemTheoSQLThanhToan(String sqlstring) {
        db = myda.openDB();
        List<ThanhToanDTO> ListTT = new ArrayList<ThanhToanDTO>();
        // Select All Query
        //    String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH ;
        Cursor cursor = db.rawQuery(sqlstring, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanDTO kh = new ThanhToanDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_MAKH)));
                kh.setGhiChu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_GHICHU)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOCU)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_SLTIEUTHU)));
                kh.setLat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_VIDO)));
                kh.setLon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KINHDO)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NGAYTHANHTOAN)));
                kh.setCapNhatThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CAPNHATTHU)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach kh tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }


    public List<String> getListKyHDThanhToanTheoMaKH(String makh) {
        db = myda.openDB();
        List<String> ListKyHD = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " = '" + makh + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String kyhd = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KYHD));


                // Adding contact to list
                ListKyHD.add(kyhd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong kyhd", String.valueOf(ListKyHD.size()));
        return ListKyHD;
    }


    public List<ThanhToanDTO> GetThanhToanTheoKyHDVaMaKH(String makh, String kyhd) {
        db = myda.openDB();
        List<ThanhToanDTO> ListTT = new ArrayList<ThanhToanDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " = '" + makh + "' and " + MyDatabaseHelper.KEY_THANHTOAN_KYHD + " = '" + kyhd + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanDTO kh = new ThanhToanDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_MAKH)));
                kh.setGhiChu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_GHICHU)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOCU)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_SLTIEUTHU)));
                kh.setLat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_VIDO)));
                kh.setLon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KINHDO)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NGAYTHANHTOAN)));
                kh.setCapNhatThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CAPNHATTHU)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListTT.size()));
        return ListTT;
    }

    public List<ThanhToanDTO> getTatCaThanhToan() {
        db = myda.openDB();
        List<ThanhToanDTO> ListTT = new ArrayList<ThanhToanDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_THANHTOAN;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThanhToanDTO kh = new ThanhToanDTO();
                kh.setBienLai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_BIENLAI)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_MAKH)));
                kh.setGhiChu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_GHICHU)));
                kh.setChiSoMoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOMOI)));
                kh.setChiSoCu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CHISOCU)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_SLTIEUTHU)));
                kh.setLat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_VIDO)));
                kh.setLon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KINHDO)));
                kh.setKyHD(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_KYHD)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NHANVIENTHU)));
                kh.setM3t1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M31)));
                kh.setM3t2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M32)));
                kh.setM3t3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M33)));
                kh.setM3t4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_M34)));
                kh.setTien1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN1)));
                kh.setTien2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN2)));
                kh.setTien3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN3)));
                kh.setTien4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIEN4)));
                kh.setTienNuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TIENNUOC)));
                kh.setphi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_PHI)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_THUE)));
                kh.settongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_TONGCONG)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_NGAYTHANHTOAN)));
                kh.setCapNhatThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_THANHTOAN_CAPNHATTHU)));


                // Adding contact to list
                ListTT.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListTT;
    }


}
