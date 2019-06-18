package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.JSONKHTHU;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.RequestObject;
import tiwaco.thefirstapp.DTO.RequestObjectThu;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;


/**
 * Created by TUTRAN on 04/04/2017.
 */

public class KhachHangThuDAO {
    SQLiteDatabase db;
    MyDatabaseHelper myda;
    Context context;

    public KhachHangThuDAO(Context con) {
        myda = new MyDatabaseHelper(con);
        context = con;
        //     db = myda.openDB();
        //    myda.resetDatabase(db);
    }

    public boolean addTable_KH(KhachHangThuDTO kh, String maduong, String capnhatghi) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH, kh.getMaKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH, kh.getTenKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO, kh.getDanhBo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI, kh.getDiaChi().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI, kh.getDienThoai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_STT, Integer.parseInt(kh.getSTT().trim()));
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK, kh.getTrangThaiTLK().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI, kh.getChitietloai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK, kh.getCotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC, kh.getDinhmuc().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK, kh.getHieutlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH, kh.getLoaikh().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI, kh.getLoaikh().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK, kh.getMasotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU, kh.getGhiChu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO, kh.getChiSo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON, kh.getChiSocon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1, kh.getChiSo1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON, kh.getChiSo1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2, kh.getChiSo2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON, kh.getChiSo2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3, kh.getChiSo3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON, kh.getChiSo3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU, kh.getSLTieuThu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1, kh.getSLTieuThu1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON, kh.getSLTieuThu1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2, kh.getSLTieuThu2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON, kh.getSLTieuThu2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3, kh.getSLTieuThu3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON, kh.getSLTieuThu3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON, kh.getSLTieuThucon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO, kh.getLat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO, kh.getLon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN, kh.getThoiGian().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN, kh.getNhanVien().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG, maduong.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHAT, capnhatghi);
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_BATTHUONG, "");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH, kh.getNTSH().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC, kh.getTienNuoc().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI, kh.getphi().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG, kh.gettongcong().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT, kh.getvat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN, "0");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC, "0");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU, "0");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU, kh.getGhichuthu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1, kh.getTien1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2, kh.getTien2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3, kh.getTien3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4, kh.getTien4().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE, kh.getThue().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN, kh.getNgaythanhtoan().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU, "0");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU, "");
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU, "0");

        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DANHSACHKHTHU, null, values);
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addTable_KHThu(JSONKHTHU kh) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH, kh.getMaKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH, kh.getTenKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO, kh.getDanhBo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI, kh.getDiaChi().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI, kh.getDienThoai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_STT, Integer.parseInt(kh.getSTT().trim()));
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK, kh.getTrangThaiTLK().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI, kh.getChitietloai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK, kh.getCotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC, kh.getDinhmuc().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK, kh.getHieutlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH, kh.getLoaikh().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI, kh.getLoaikh().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK, kh.getMasotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU, kh.getGhiChu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO, kh.getChiSo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON, kh.getChiSocon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1, kh.getChiSo1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON, kh.getChiSo1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2, kh.getChiSo2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON, kh.getChiSo2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3, kh.getChiSo3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON, kh.getChiSo3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU, kh.getSLTieuThu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1, kh.getSLTieuThu1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON, kh.getSLTieuThu1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2, kh.getSLTieuThu2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON, kh.getSLTieuThu2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3, kh.getSLTieuThu3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON, kh.getSLTieuThu3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON, kh.getSLTieuThucon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO, kh.getLat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO, kh.getLon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN, kh.getThoiGian().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN, kh.getNhanVien().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG, kh.getMaduong().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHAT, kh.getCapnhat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_BATTHUONG, kh.getBatthuong().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH, kh.getNTSH().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC, kh.getTiennuoc().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI, kh.getPhi().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG, kh.getTongcong().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT, kh.getNovat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN, kh.getM3muc1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC, kh.getM3muc1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU, kh.getM3muc1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU, kh.getGhiChu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1, kh.getTienmuc1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2, kh.getTienmuc2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3, kh.getTienmuc3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4, kh.getTienmuc4().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE, kh.getThue().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN, kh.getNgaythanhtoan().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU, kh.getCapnhatthu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU, kh.getNhanvienthu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU, kh.getTamthu().trim());

        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DANHSACHKHTHU, null, values);
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTable_KH(KhachHangThuDTO kh) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        // values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH , kh.getMaKhachHang().trim());
        // values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1 , kh.getChiSo1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU, kh.getGhiChu().trim());
        //    values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1 , kh.getSLTieuThu1().trim());

        // Inserting Row


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{kh.getMaKhachHang().trim()}) > 0;
        db.close();
        return kt;


    }


    public List<JSONKHTHU> getAllKHThu() {
        db = myda.openDB();
        List<JSONKHTHU> ListKH = new ArrayList<JSONKHTHU>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                JSONKHTHU kh = new JSONKHTHU();
                kh.setChiSo(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO)));
                kh.setChiSocon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON)));
                kh.setChiSo1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1)));
                kh.setChiSo1con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON)));
                kh.setChiSo2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2)));
                kh.setChiSo2con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON)));
                kh.setChiSo3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3)));
                kh.setChiSo3con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON)));
                kh.setDanhBo(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO)));
                kh.setDiaChi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI)));
                kh.setDienThoai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI)));
                kh.setGhiChu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU)));
                kh.setLat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO)));
                kh.setLon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO)));
                kh.setMaKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH)));
                kh.setNhanVien(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN)));
                kh.setSLTieuThu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU)));
                kh.setSLTieuThu1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1)));
                kh.setSLTieuThu1con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON)));
                kh.setSLTieuThu2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2)));
                kh.setSLTieuThu2con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON)));
                kh.setSLTieuThu3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3)));
                kh.setSLTieuThu3con(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON)));
                kh.setSLTieuThucon(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON)));
                kh.setSTT(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_STT)));
                kh.setTenKhachHang(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH)));
                kh.setThoiGian(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN)));
                kh.setTrangThaiTLK(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK)));
                kh.setChitietloai(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI)));
                kh.setCotlk(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK)));
                kh.setDinhmuc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC)));
                kh.setHieutlk(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK)));
                kh.setLoaikh(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH)));
                kh.setMasotlk(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK)));
                kh.setBatthuong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_BATTHUONG)));
                kh.setMaduong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG)));
                kh.setCapnhat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHAT)));
                kh.setLoaikhmoi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI)));
                kh.setNTSH(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH)));
                kh.setTiennuoc(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC)));
                kh.setPhi(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI)));
                kh.setTongcong(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG)));
                kh.setNovat(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT)));
                kh.setThue(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE)));
                kh.setM3muc1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN)));
                kh.setM3muc2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC)));
                kh.setM3muc3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU)));
                kh.setM3muc4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU)));
                kh.setTienmuc1(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1)));
                kh.setTienmuc2(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2)));
                kh.setTienmuc3(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3)));
                kh.setTienmuc4(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4)));
                kh.setNgaythanhtoan(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN)));
                kh.setCapnhatthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU)));
                kh.setNhanvienthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU)));
                kh.setTamthu(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU)));


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }


    public List<KhachHangThuDTO> getAllKHTheoDuong(String maduong) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0));
                kh.setTenKhachHang(cursor.getString(1));
                kh.setDanhBo(cursor.getString(2));
                kh.setDiaChi(cursor.getString(3));
                kh.setDienThoai(cursor.getString(4));
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6));
                kh.setChitietloai(cursor.getString(7));
                kh.setCotlk(cursor.getString(8));
                kh.setDinhmuc(cursor.getString(9));
                kh.setHieutlk(cursor.getString(10));
                kh.setLoaikh(cursor.getString(11));
                kh.setMasotlk(cursor.getString(12));
                kh.setGhiChu(cursor.getString(13));
                kh.setChiSo(cursor.getString(14));
                kh.setChiSocon(cursor.getString(15));
                kh.setChiSo1(cursor.getString(16));
                kh.setChiSo1con(cursor.getString(17));
                kh.setChiSo2(cursor.getString(18));
                kh.setChiSo2con(cursor.getString(19));
                kh.setChiSo3(cursor.getString(20));
                kh.setChiSo3con(cursor.getString(21));
                kh.setSLTieuThu(cursor.getString(22));
                kh.setSLTieuThu1(cursor.getString(23));
                kh.setSLTieuThu1con(cursor.getString(24));
                kh.setSLTieuThu2(cursor.getString(25));
                kh.setSLTieuThu2con(cursor.getString(26));
                kh.setSLTieuThu3(cursor.getString(27));
                kh.setSLTieuThu3con(cursor.getString(28));
                kh.setSLTieuThucon(cursor.getString(29));
                kh.setLat(cursor.getString(30));
                kh.setLon(cursor.getString(31));
                kh.setThoiGian(cursor.getString(32));
                kh.setNhanVien(cursor.getString(33));
                kh.setLoaikhmoi(cursor.getString(36));
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));

                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }



    public List<KhachHangThuDTO> getAllKHDaThuTheoDuong(String maduong) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        // String selectQuery = "SELECT DISTINCT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "<>'' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and  ( SELECT COUNT (BIENLAI) FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + "=" + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " and " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_DANHSACHKH_NGAYTHANHTOAN + " <>'' ) >  0  ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        //SUABUG:Ghi chu sai du lieu (SQL sua)
        //String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"<>'' and ("+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO  +"-"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1   +")<>"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU +" ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0));
                kh.setTenKhachHang(cursor.getString(1));
                kh.setDanhBo(cursor.getString(2));
                kh.setDiaChi(cursor.getString(3));
                kh.setDienThoai(cursor.getString(4));
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6));
                kh.setChitietloai(cursor.getString(7));
                kh.setCotlk(cursor.getString(8));
                kh.setDinhmuc(cursor.getString(9));
                kh.setHieutlk(cursor.getString(10));
                kh.setLoaikh(cursor.getString(11));
                kh.setMasotlk(cursor.getString(12));
                kh.setGhiChu(cursor.getString(13));
                kh.setChiSo(cursor.getString(14));
                kh.setChiSocon(cursor.getString(15));
                kh.setChiSo1(cursor.getString(16));
                kh.setChiSo1con(cursor.getString(17));
                kh.setChiSo2(cursor.getString(18));
                kh.setChiSo2con(cursor.getString(19));
                kh.setChiSo3(cursor.getString(20));
                kh.setChiSo3con(cursor.getString(21));
                kh.setSLTieuThu(cursor.getString(22));
                kh.setSLTieuThu1(cursor.getString(23));
                kh.setSLTieuThu1con(cursor.getString(24));
                kh.setSLTieuThu2(cursor.getString(25));
                kh.setSLTieuThu2con(cursor.getString(26));
                kh.setSLTieuThu3(cursor.getString(27));
                kh.setSLTieuThu3con(cursor.getString(28));
                kh.setSLTieuThucon(cursor.getString(29));
                kh.setLat(cursor.getString(30));
                kh.setLon(cursor.getString(31));
                kh.setThoiGian(cursor.getString(32));
                kh.setNhanVien(cursor.getString(33));
                kh.setLoaikhmoi(cursor.getString(36));
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));

                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }





    public List<KhachHangThuDTO> getAllKHChuaThuTheoDuong(String maduong) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"='' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "=''  and " + " ( SELECT COUNT (BIENLAI) FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + "=" + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + ") > 0   ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        // String selectQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU+" WHERE " +MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN +"='' ORDER BY cast( "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//               int sohd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOHD")));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
//                ThanhToanDAO thanhtoandao = new ThanhToanDAO(context);
//                if(thanhtoandao.countKhachHangChuaThuTheoMaKH(kh.getMaKhachHang()) > 0) {
                ListKH.add(kh);
//                }
//                if(sohd>0){
//                    ListKH.add(kh);
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListKH;
    }

    public List<KhachHangThuDTO> getAllKHCoNoTheoDuong(String maduong) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"='' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and  ( SELECT COUNT (BIENLAI) FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + "=" + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + ") >= 2   ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        // String selectQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU+" WHERE " +MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN +"='' ORDER BY cast( "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//               int sohd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOHD")));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
//                ThanhToanDAO thanhtoandao = new ThanhToanDAO(context);
//                if(thanhtoandao.countKhachHangChuaThuTheoMaKH(kh.getMaKhachHang()) > 0) {
                ListKH.add(kh);
//                }
//                if(sohd>0){
//                    ListKH.add(kh);
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListKH;
    }

    public List<KhachHangThuDTO> getAllKHTamThuChuaCapNhat(String maduong) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"='' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and  " + MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU + " = '1'   ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        // String selectQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU+" WHERE " +MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN +"='' ORDER BY cast( "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//               int sohd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOHD")));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
//                ThanhToanDAO thanhtoandao = new ThanhToanDAO(context);
//                if(thanhtoandao.countKhachHangChuaThuTheoMaKH(kh.getMaKhachHang()) > 0) {
                ListKH.add(kh);
//                }
//                if(sohd>0){
//                    ListKH.add(kh);
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListKH;
    }

    public List<KhachHangThuDTO> getAllKHTamThuDaCapNhat(String maduong) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"='' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and  " + MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU + " = '2'   ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        // String selectQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU+" WHERE " +MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN +"='' ORDER BY cast( "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//               int sohd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOHD")));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
//                ThanhToanDAO thanhtoandao = new ThanhToanDAO(context);
//                if(thanhtoandao.countKhachHangChuaThuTheoMaKH(kh.getMaKhachHang()) > 0) {
                ListKH.add(kh);
//                }
//                if(sohd>0){
//                    ListKH.add(kh);
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListKH;
    }

    public List<KhachHangThuDTO> getAllKHTamThuDaCapNhatBiTrung(String maduong) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"='' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        String selectQuery = "SELECT DISTINCT " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ".* FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and  " + MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU + " = '3'   ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        // String selectQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU+" WHERE " +MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN +"='' ORDER BY cast( "+MyDatabaseHelper.TABLE_DANHSACHKHTHU+"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//               int sohd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOHD")));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
//                ThanhToanDAO thanhtoandao = new ThanhToanDAO(context);
//                if(thanhtoandao.countKhachHangChuaThuTheoMaKH(kh.getMaKhachHang()) > 0) {
                ListKH.add(kh);
//                }
//                if(sohd>0){
//                    ListKH.add(kh);
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ListKH;
    }


    public String getTamThuTheoMAKH(String makh) {
        db = myda.openDB();

        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();

        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "='" + makh + "' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String TamThu = "0";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TamThu = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU));
//               Log.e("sohd",String.valueOf(sohd) +" "+ cursor.getColumnIndex("SOHD"));
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return TamThu;
    }

    public List<KhachHangThuDTO> getAllKH() {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));

                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach kh", String.valueOf(ListKH.size()));
        return ListKH;
    }

    // Updating SDT
    public boolean updateDienThoai(String maKH, String sdt) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI, sdt);

        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH}) > 0;
        db.close();
        return kt;

    }



    public boolean updateGhiChuThu(String maKH, String ghichu) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU, ghichu);

        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH}) > 0;
        db.close();
        return kt;

    }





    public boolean updateGiaNuoc(String maKH, String tiennuoc, String phi, String tongcong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC, tiennuoc);
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI, phi);
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG, tongcong);


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH}) > 0;
        if (kt) {
            Log.e("TinhTienNuoc", "OK");
        }

        db.close();
        return kt;

    }

    //UPDATE trạng thái cập nhật lên server , 0: chưa cập nhật, 1: đã cập nhật
    public boolean updateTrangThaiCapNhat(String maKH, String trangthai) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHAT, trangthai);

        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH}) > 0;
        db.close();
        return kt;

    }

    //UPdate trang thai thu
    public boolean updateTrangThaiThuCapNhat(String maKH, String trangthai) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU, trangthai);


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH}) > 0;
        db.close();
        return kt;

    }

    public boolean updateKhachHang(String maKH, String Chiso, String Chisocon, String Dienthoai, String ghichu, String vido, String kinhdo, String nhanvien, String SL, String SLCon, String thoigian, String trangthaiTLK, String bt) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO, Chiso.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON, Chisocon.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI, Dienthoai.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU, ghichu.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO, vido.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO, kinhdo.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN, nhanvien.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU, SL.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON, SLCon.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK, trangthaiTLK.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_BATTHUONG, bt.trim());
        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH.trim()}) > 0;
        db.close();
        return kt;

    }


    public boolean updateKhachHangTamThu(String maKH, String thoigian, String nhanvienthu) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU, nhanvienthu.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU, "1");


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH.trim()}) > 0;
        db.close();
        return kt;

    }

    public boolean updateKhachHangTamThuCapNhatServer(String maKH, String trangthai) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_TAMTHU, trangthai);


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH.trim()}) > 0;
        db.close();
        return kt;

    }


    public boolean updateKhachHangThanhToan(String maKH, String thoigian, String nhanvienthu) {
        db = myda.openDB();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU, nhanvienthu.trim());


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH.trim()}) > 0;
        db.close();
        return kt;

    }

    public boolean updateDaChamNo(String maKH) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU, "4");


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{maKH.trim()}) > 0;
        db.close();
        return kt;

    }

    public int countKhachHangTheoDuong(String maduong) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong.trim() + "'";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangDaGhiTheoDuong(String maduong) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "<>''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangDaGhi() {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "<>''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }


    public int countKhachHangDaGhiServer() {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + ", " + MyDatabaseHelper.TABLE_DUONG + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=" + MyDatabaseHelper.TABLE_DUONG + "." + MyDatabaseHelper.KEY_DUONG_MADUONG + " and " + MyDatabaseHelper.KEY_DUONG_KHOASO + "='0' and  " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "<>''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }


    public int countKhachHangDaThuTheoNhanVien(String tennv) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "<>'' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU + "= '" + tennv.trim() + "'";
        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public boolean checkKHDaChamNo(String maKH) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "='4' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "='" + maKH + "'";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        if (sokh > 0) {
            return true;
        } else {
            Log.e("ketquacheckKHDaChamNo", String.valueOf(sokh));
            return false;
        }

    }

    public int checkKHDaGhi(String maKH) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "<>'' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "='" + maKH + "'";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }



    public int countKhachHangDaThuHomNayTheoNV(String thoigian, String tennv) {
        db = myda.openDB();
        int sokh = 0;
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " LIKE '%" + thoigian + "%' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " <>'' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU + "= '" + tennv + "'    ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Cursor cursor = db.rawQuery(selectQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }



    public int countKhachHangChuaGhiTheoDuong(String maduong) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangChuaThuTheoDuong(String maduong) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh = cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangAll() {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU;
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



    public int countKhachHangThuTrongNgay(String maduong, String ngay) {
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " LIKE '%" + ngay + "%' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " <>'' ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Log.e("dathutrongngay", countQuery);
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor == null) {
            sokh = 0;
        } else {
            sokh = cursor.getCount();
        }
        cursor.close();
        db.close();
        Log.e("sokh thu trong ngay", String.valueOf(sokh));
        return sokh;


    }




    public List<KhachHangThuDTO> getAllKHDaThuHomNay(String maduong, String ngay) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " LIKE '%" + ngay + "%'  ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Log.e("selectQuery", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38));
                kh.setTienNuoc(cursor.getString(39));
                kh.setphi(cursor.getString(40));
                kh.settongcong(cursor.getString(41));
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));

                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }




    public List<KhachHangThuDTO> getAllKHGhiChu(String maduong) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU + " <> ''  ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38).trim());
                kh.setTienNuoc(cursor.getString(39).trim());
                kh.setphi(cursor.getString(40).trim());
                kh.settongcong(cursor.getString(41).trim());
                kh.setvat(cursor.getString(42));

                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }


    public List<KhachHangThuDTO> getAllKHGhiChuThu(String maduong) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU + " <> ''  ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38).trim());
                kh.setTienNuoc(cursor.getString(39).trim());
                kh.setphi(cursor.getString(40).trim());
                kh.settongcong(cursor.getString(41).trim());
                kh.setvat(cursor.getString(42));

                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));
                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }





    public boolean checkExistKH(String MaKH, String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU

                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=?",
                new String[]{maduong, MaKH},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());

        }


        cursor.close();
        db.close();
        if (kh != null) {
            return true;
        } else {
            return false;
        }
    }

    public KhachHangThuDTO getKHTheoSTT_Duong(String STT, String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU

                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "=?",
                new String[]{maduong, STT},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());

        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }

    public KhachHangThuDTO getKHTheoSTT_Duong_maKH(String STT, String maduong, String maKH) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU

                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=?",
                new String[]{maduong, STT, maKH},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());

        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }


    public KhachHangThuDTO getKHTheoSTT_Duong_khacmaKH(String STT, String maduong, String maKH, String sosanh) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU
                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO + sosanh + " ?",
                new String[]{maduong, STT, maKH},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());
        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }

    public KhachHangThuDTO getKHTheoSTT_Duong_khacmaKH_chuaghi(String STT, String maduong, String maKH) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU
                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "<> ? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "=''",
                new String[]{maduong, STT, maKH},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());
        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }

    public KhachHangThuDTO getKHTheoSTT_Duong_khacmaKH_chuaThu(String STT, String maduong, String maKH) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU
                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "<> ? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "=''",
                new String[]{maduong, STT, maKH},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());
        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }

    public KhachHangThuDTO getKHTheoMaKH(String makh) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU
                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=? ",
                new String[]{makh},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());
        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }

    public KhachHangThuDTO getKHTheoDanhBoSTTDuong(String maduong, String tk) {
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TENKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIACHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DIENTHOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_STT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TRANGTHAITLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHITIETLOAI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_COTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_DINHMUC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_HIEUTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_MASOTLK,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISOCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU1CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU2CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU3CON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHUCON,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_VIDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_KINHDO,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THOIGIAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIEN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NTSH,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NOVAT,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIENNUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_PHI,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TONGCONG,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_THUE,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_GHICHUTHU,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN1,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN2,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN3,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_TIEN4,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN,
                        MyDatabaseHelper.KEY_DANHSACHKHTHU_NHANVIENTHU

                },
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and ( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "='" + tk + "' or " + MyDatabaseHelper.KEY_DANHSACHKHTHU_DANHBO + "='" + tk + "' )",
                new String[]{maduong},
                null, null, null, null);
        KhachHangThuDTO kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangThuDTO();
            kh.setMaKhachHang(cursor.getString(0).trim());
            kh.setTenKhachHang(cursor.getString(1).trim());
            kh.setDanhBo(cursor.getString(2).trim());
            kh.setDiaChi(cursor.getString(3).trim());
            kh.setDienThoai(cursor.getString(4).trim());
            kh.setSTT(String.valueOf(cursor.getInt(5)));
            kh.setTrangThaiTLK(cursor.getString(6).trim());
            kh.setChitietloai(cursor.getString(7).trim());
            kh.setCotlk(cursor.getString(8).trim());
            kh.setDinhmuc(cursor.getString(9).trim());
            kh.setHieutlk(cursor.getString(10).trim());
            kh.setLoaikh(cursor.getString(11).trim());
            kh.setMasotlk(cursor.getString(12).trim());
            kh.setGhiChu(cursor.getString(13).trim());
            kh.setChiSo(cursor.getString(14).trim());
            kh.setChiSocon(cursor.getString(15).trim());
            kh.setChiSo1(cursor.getString(16).trim());
            kh.setChiSo1con(cursor.getString(17).trim());
            kh.setChiSo2(cursor.getString(18).trim());
            kh.setChiSo2con(cursor.getString(19).trim());
            kh.setChiSo3(cursor.getString(20).trim());
            kh.setChiSo3con(cursor.getString(21).trim());
            kh.setSLTieuThu(cursor.getString(22).trim());
            kh.setSLTieuThu1(cursor.getString(23).trim());
            kh.setSLTieuThu1con(cursor.getString(24).trim());
            kh.setSLTieuThu2(cursor.getString(25).trim());
            kh.setSLTieuThu2con(cursor.getString(26).trim());
            kh.setSLTieuThu3(cursor.getString(27).trim());
            kh.setSLTieuThu3con(cursor.getString(28).trim());
            kh.setSLTieuThucon(cursor.getString(29).trim());
            kh.setLat(cursor.getString(30).trim());
            kh.setLon(cursor.getString(31).trim());
            kh.setThoiGian(cursor.getString(32).trim());
            kh.setNhanVien(cursor.getString(33).trim());
            kh.setLoaikhmoi(cursor.getString(34).trim());
            kh.setNTSH(cursor.getString(35).trim());
            kh.setvat(cursor.getString(36).trim());
            kh.setTienNuoc(cursor.getString(37).trim());
            kh.setphi(cursor.getString(38).trim());
            kh.settongcong(cursor.getString(39).trim());
            kh.setThue(cursor.getString(40).trim());
            kh.setM3t1(cursor.getString(41).trim());
            kh.setM3t2(cursor.getString(42).trim());
            kh.setM3t3(cursor.getString(43).trim());
            kh.setGhichuthu(cursor.getString(44).trim());
            kh.setTien1(cursor.getString(45).trim());
            kh.setTien2(cursor.getString(46).trim());
            kh.setTien3(cursor.getString(47).trim());
            kh.setTien4(cursor.getString(48).trim());
            kh.setNgaythanhtoan(cursor.getString(49).trim());
            kh.setNhanvienthu(cursor.getString(50).trim());

        }

        cursor.close();
        db.close();
        if (kh != null) {
            return kh;
        } else {
            return null;
        }
    }


    public String getSTTChuaGhiNhoNhat(String maduong) {
        String data = "1";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "='' ",
                new String[]{maduong},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay so thu tu chua ghi nho nhat", "ok");
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
            if (data.equals("0")) {
                data = "1";
            }
        }

        db.close();
        return data;

    }

    public String getSTTChuaThuNhoNhat(String maduong) {
        String data = "1";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' ",
                new String[]{maduong},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay so thu tu chua ghi nho nhat", "ok");
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
            if (data.equals("0")) {
                data = "1";
            }
        }
        Log.e("data selection", String.valueOf(data));
        db.close();
        return data;

    }

    public String getSTTChuaGhiNhoNhatLonHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">=? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        // db.close();
        return data;

    }

    public String getSTTChuaThuNhoNhatLonHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> 'KODONG'",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        // db.close();
        return data;

    }

    public String getSTTChuaThuNhoNhatLonHonHienTaiKhacMaKH(String maduong, String stthientai, String makh) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">= ? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "<> ? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> 'KODONG'",
                new String[]{maduong, stthientai, makh},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        // db.close();
        return data;

    }

    public String getSTTChuaGhiNhoNhatLonHonHienTai1(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTChuaThuNhoNhatLonHonHienTai1(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();


        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">?  and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> ?",
                new String[]{maduong, stthientai, "KODONG"},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        cursor.close();
        db.close();
        return data;


//
//        String selectQuery = "SELECT MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT  FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and "+ MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." +MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "> '"+stthientai +"'  and  ( SELECT COUNT (BIENLAI) FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + "=" + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " and " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_DANHSACHKH_NGAYTHANHTOAN + " <>'' ) >  0  ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
//        //SUABUG:Ghi chu sai du lieu (SQL sua)
//        //String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"<>'' and ("+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO  +"-"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1   +")<>"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU +" ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor != null && cursor.moveToFirst()) {
//            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
//        } else {
//            data = "";
//        }
//        Log.e("data chua ghi nho nhat lon hon hien tai", data);
//        cursor.close();
//        db.close();
//        return data;

    }


    public String getSTTNhoNhatLonHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ">? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "0";
        }
        Log.e("data chua ghi nho nhat lon hon hien tai", data);
        cursor.close();
        db.close();
        return data;

    }

    public String getSTTChuaGhiLonNhatNhoHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<=? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTChuaThuLonNhatNhoHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> ?",
                new String[]{maduong, stthientai, "KODONG"},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        cursor.close();
        db.close();
        return data;

    }

    public String getSTTChuaThuLonNhatNhoHonHienTaiKhacMaKH(String maduong, String stthientai, String makh) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "<> ? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> 'KODONG'",
                new String[]{maduong, stthientai, makh},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTChuaGhiLonNhatNhoHonHienTai1(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "0";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTChuaThuLonNhatNhoHonHienTai1(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT, " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<?  and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CAPNHATTHU + "<> ? ", //
                new String[]{maduong, stthientai, "KODONG"},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
            Log.e("data capnhatthu", String.valueOf(cursor.getString(1)));
        } else {
            data = "0";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);

        db.close();
        return data;

//        String selectQuery = "SELECT MAX(" + MyDatabaseHelper.TABLE_DANHSACHKHTHU +"."+ MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT  FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "," + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=" + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + " and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "='" + maduong + "' and " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_NGAYTHANHTOAN + "='' and "+ MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." +MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "< '"+stthientai +"'  and  ( SELECT COUNT (BIENLAI) FROM " + MyDatabaseHelper.TABLE_THANHTOAN + " WHERE " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_THANHTOAN_MAKH + "=" + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " and " + MyDatabaseHelper.TABLE_THANHTOAN + "." + MyDatabaseHelper.KEY_DANHSACHKH_NGAYTHANHTOAN + " <>'' ) >  0  ORDER BY cast( " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "." + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )";
//        //SUABUG:Ghi chu sai du lieu (SQL sua)
//        //String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO +"<>'' and ("+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO  +"-"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_CHISO1   +")<>"+ MyDatabaseHelper.KEY_DANHSACHKHTHU_SLTIEUTHU +" ORDER BY cast( " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + " as unsigned )" ;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor != null && cursor.moveToFirst()) {
//            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
//        } else {
//            data = "";
//        }
//        Log.e("data chua ghi nho nhat lon hon hien tai", data);
//        cursor.close();
//        db.close();
//        return data;

    }

    public String getSTTLonNhatNhoHonHienTai(String maduong, String stthientai) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + "<? ",
                new String[]{maduong, stthientai},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "0";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTLonNhat(String maduong) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MAX(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MAXSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? ",
                new String[]{maduong},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "0";
        }
        Log.e("data chua ghi lon nhat nho hon hien tai", data);
        db.close();
        return data;

    }

    public String getSTTnhoNhat(String maduong) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{"MIN(" + MyDatabaseHelper.KEY_DANHSACHKHTHU_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + "=? ",
                new String[]{maduong},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        } else {
            data = "0";
        }
        Log.e("data nho nhat", data);
        db.close();
        return data;

    }

    public List<KhachHangThuDTO> TimKiemTheoSQL(String sqlstring) {
        db = myda.openDB();
        List<KhachHangThuDTO> ListKH = new ArrayList<KhachHangThuDTO>();
        // Select All Query
        //    String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU ;
        Cursor cursor = db.rawQuery(sqlstring, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangThuDTO kh = new KhachHangThuDTO();
                kh.setMaKhachHang(cursor.getString(0).trim());
                kh.setTenKhachHang(cursor.getString(1).trim());
                kh.setDanhBo(cursor.getString(2).trim());
                kh.setDiaChi(cursor.getString(3).trim());
                kh.setDienThoai(cursor.getString(4).trim());
                kh.setSTT(String.valueOf(cursor.getInt(5)));
                kh.setTrangThaiTLK(cursor.getString(6).trim());
                kh.setChitietloai(cursor.getString(7).trim());
                kh.setCotlk(cursor.getString(8).trim());
                kh.setDinhmuc(cursor.getString(9).trim());
                kh.setHieutlk(cursor.getString(10).trim());
                kh.setLoaikh(cursor.getString(11).trim());
                kh.setMasotlk(cursor.getString(12).trim());
                kh.setGhiChu(cursor.getString(13).trim());
                kh.setChiSo(cursor.getString(14).trim());
                kh.setChiSocon(cursor.getString(15).trim());
                kh.setChiSo1(cursor.getString(16).trim());
                kh.setChiSo1con(cursor.getString(17).trim());
                kh.setChiSo2(cursor.getString(18).trim());
                kh.setChiSo2con(cursor.getString(19).trim());
                kh.setChiSo3(cursor.getString(20).trim());
                kh.setChiSo3con(cursor.getString(21).trim());
                kh.setSLTieuThu(cursor.getString(22).trim());
                kh.setSLTieuThu1(cursor.getString(23).trim());
                kh.setSLTieuThu1con(cursor.getString(24).trim());
                kh.setSLTieuThu2(cursor.getString(25).trim());
                kh.setSLTieuThu2con(cursor.getString(26).trim());
                kh.setSLTieuThu3(cursor.getString(27).trim());
                kh.setSLTieuThu3con(cursor.getString(28).trim());
                kh.setSLTieuThucon(cursor.getString(29).trim());
                kh.setLat(cursor.getString(30).trim());
                kh.setLon(cursor.getString(31).trim());
                kh.setThoiGian(cursor.getString(32).trim());
                kh.setNhanVien(cursor.getString(33).trim());
                kh.setLoaikhmoi(cursor.getString(36).trim());
                kh.setNTSH(cursor.getString(38).trim());
                kh.setTienNuoc(cursor.getString(39).trim());
                kh.setphi(cursor.getString(40).trim());
                kh.settongcong(cursor.getString(41).trim());
                kh.setvat(cursor.getString(42));
                kh.setThue(cursor.getString(43));
                kh.setM3t1(cursor.getString(44));
                kh.setM3t2(cursor.getString(45));
                kh.setM3t3(cursor.getString(46));
                kh.setGhichuthu(cursor.getString(47));
                kh.setTien1(cursor.getString(48));
                kh.setTien2(cursor.getString(49));
                kh.setTien3(cursor.getString(50));
                kh.setTien4(cursor.getString(51));
                kh.setNgaythanhtoan(cursor.getString(52));
                kh.setNhanvienthu(cursor.getString(54));


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach kh tim kiem", String.valueOf(ListKH.size()));
        return ListKH;
    }

    public String getMaDuongTheoMaKhachHang(String makhachhang) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=? ",
                new String[]{makhachhang},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay so thu tu chua ghi nho nhat", "ok");
            data = cursor.getString(0);// use the data type of the column or use String itself you can parse it

        }

        db.close();
        return data;

    }

    public String checkTrangThaiBatThuongKH(String makhachhang) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{MyDatabaseHelper.KEY_DANHSACHKHTHU_BATTHUONG},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=? ",
                new String[]{makhachhang},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay bat thuong", "ok");
            data = cursor.getString(0);// use the data type of the column or use String itself you can parse it

        }

        db.close();
        return data;

    }

    public String getLoaiKHMoi(String makhachhang) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH_MOI},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=? ",
                new String[]{makhachhang},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay bat thuong", "ok");
            data = cursor.getString(0);// use the data type of the column or use String itself you can parse it

        }

        db.close();
        return data;

    }

    public String getLoaiKHCu(String makhachhang) {
        String data = "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKHTHU,
                new String[]{MyDatabaseHelper.KEY_DANHSACHKHTHU_LOAIKH},
                MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + "=? ",
                new String[]{makhachhang},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("lay bat thuong", "ok");
            data = cursor.getString(0);// use the data type of the column or use String itself you can parse it

        }

        db.close();
        return data;

    }

    public List<DuongThuDTO> getListDuongTheoDK(Context con, String dk) {
        db = myda.openDB();
        DuongThuDAO duongthudao = new DuongThuDAO(con);
        List<DuongThuDTO> ListDuong = new ArrayList<DuongThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  DISTINCT " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MADUONG + " FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + "  " + dk;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String maduong = "";
                maduong = cursor.getString(0);
                Log.e("maduongtheodk", maduong);
                DuongThuDTO duong = duongthudao.getDuongTheoMa(maduong);

                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong duong thoa dk", String.valueOf(ListDuong.size()));
        return ListDuong;
    }

    public void tinhTienNuoc(String maKH) {

        double tiennuoc = 0;
        double tongcong = 0;
        //Loại kh 0:
        //Đơn giá: tg01= 6.800, tg02=9.000, tg03=10.500, tg04=11.800
        final double dg01 = 6800, dg02 = 9000, dg03 = 10500, dg04 = 11800;
        //Đơn giá tiền nước: tg01tn= 6.800/115*100, tg02tn=9.000/115*100, tg03tn=10.500/115*100, tg04tn=11.800/115*100
        final double dg01tn = 6800 / 115 * 100, dg02tn = 9000 / 115 * 100, dg03tn = 10500 / 115 * 100, dg04tn = 11800 / 115 * 100;
        //Đơn giá tiền nước không phí ntsh: tg01p= 6.800/115*105, tg02p=9.000/115*105, tg03p=10.500/115*105, tg04p=11.800/115*105
        final double dg01p = 6800 / 115 * 105, dg02p = 9000 / 115 * 105, dg03p = 10500 / 115 * 105, dg04p = 11800 / 115 * 105;
         /*Loại kh1:
         Đơn giá: tg1= 4.700
	     Đơn giá tiền nước: tg1tn= 4.700/105*100 */
        final double dg1 = 4700, dg1tn = 4700 / 105 * 100;

        /*	Loại kh2:
        Đơn giá: tg2= 6.000
        Đơn giá tiền nước: tg2tn= 6.000/105*100*/
        final double dg2 = 6000, dg2tn = 6000 / 105 * 100;
        /* Loại kh3:
         Đơn giá: tg3= 6.700
	    Đơn giá tiền nước: tg3tn= 6.700/105*100 */
        final double dg3 = 6700, dg3tn = 6700 / 105 * 100;
        /*	Loại kh4:
    	Đơn giá: tg4= 8.600
	    Đơn giá tiền nước: tg4tn= 4.700/105*100 */
        final double dg4 = 8600, dg4tn = 8600 / 105 * 100;
        /*Loại kh5:
	    Đơn giá: tg01= 6.800, tg02=9.000, tg05=12.000, tg05nt=10.800
	    Đơn giá tiền nước: tg01tn= 6.800/115*100, tg02tn=9.000/115*100, tg05tn=12.000/115*100, tg05nttn =10.800/105*100
    	Đơn giá tiền nước không phí ntsh: tg01p= 6.800/115*105, tg02p=9.000/115*105, tg05p=12.000/115*105*/
        final double dg5 = 12000, dg5nt = 10800;
        final double dg5tn = 12000 / 115 * 100, dg5nttn = 10800 / 105 * 100;
        final double dg5p = 12000 / 115 * 105;
        /*Loại kh6:
        Đơn giá: tg6=10.000, tg05=12.000, tg07=10.500
	    Đơn giá tiền nước: tg06tn= 10.000/115*100, tg05tn=12.000/115*100, tg07tn =10.500/105*100
	    Đơn giá tiền nước không phí ntsh: tg06p= 10.000/115*105, tg05p=12.000/115*105, tg07p =10.500/115*105*/
        final double dg6 = 10000, dg7 = 10500, dg6nt = 9000;
        final double dg6tn = 10000 / 115 * 100, dg7tn = 10500 / 115 * 100, dg6nttn = 9000 / 115 * 100;
        final double dg6p = 10000 / 115 * 105, dg7p = 10500 / 115 * 105;
        /*Loại kh7:
    Đơn giá: tg01= 6.800, tg02=9.000, tg07=10.500, tg07nt=9.500
	Đơn giá tiền nước: tg01tn= 6.800/115*100, tg02tn=9.000/115*100, tg07tn=10.500/115*100, tg07nttn =9.500/105*100
	Đơn giá tiền nước không phí ntsh: tg01p= 6.800/115*105, tg02p=9.000/115*105, tg07p=10.500/115*105 */


        final double dg7nt = 9500;
        final double dg7nttn = 9500 / 105 * 100;
        KhachHangThuDTO kh = getKHTheoMaKH(maKH);

        double tieuthu = Double.parseDouble(kh.getSLTieuThu());

        double dmuc = Double.parseDouble(kh.getDinhmuc());
        double m3t1 = 0, m3t2 = 0, m3t3 = 0, m3t4 = 0;
        double tien1 = 0, tien2 = 0, tien3 = 0, tien4 = 0;
        double dm01 = 10, dm02 = 20, dm03 = 30;
        double ntsh = Double.parseDouble(kh.getNTSH());
        double phi = 0;
        double tienthue = 0;
        String MIENTHUE = kh.getvat();
        //tinh tien tu gia
        if (kh.getLoaikh().equals("0")) // LOAI KHACH HANG TU GIA THANH THI
        {
            if (kh.getChitietloai().toUpperCase().equals("T")) // truong hop nha tro hoac to hop (nhieu nguoi su dung chung mot dong ho nuoc )
            {
                if (tieuthu <= dmuc)//muc 1 gia 6200/dg01
                {
                    m3t1 = tieuthu;
                } else if (tieuthu > dmuc && tieuthu <= (dmuc * 2))//muc 2 gia 8200/dg02
                {
                    m3t1 = dmuc;
                    m3t2 = tieuthu - m3t1;
                } else if (tieuthu > (dmuc * 2) && tieuthu <= (dmuc * 3))//muc 3 gia 9600/dg03
                {
                    m3t1 = dmuc;
                    m3t2 = dmuc;
                    m3t3 = tieuthu - (m3t1 + m3t2);
                } else if (tieuthu > dmuc * 3)//muc 4 gia 10.800/dg04
                {
                    m3t1 = dmuc;
                    m3t2 = dmuc;
                    m3t3 = dmuc;
                    m3t4 = tieuthu - (m3t1 + m3t2 + m3t3);
                }

                tien1 = lamtron2sothapphan(m3t1 * dg01tn);// tien  nuoc muc 1 theo dinh muc
                tien2 = lamtron2sothapphan(m3t2 * dg02tn);// tien  nuoc muc 2 theo dinh muc
                tien3 = lamtron2sothapphan(m3t3 * dg03tn);// tien  nuoc muc 3 theo dinh muc
                tien4 = lamtron2sothapphan(m3t4 * dg04tn);// tien  nuoc muc 4 theo dinh muc
                tiennuoc = tien1 + tien2 + tien3 + tien4;//tong nuoc

                if (ntsh > 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg01p) + lamtron2sothapphan(m3t2 * dg02p) + lamtron2sothapphan(m3t3 * dg03p) + lamtron2sothapphan(m3t4 * dg04p);// tien nuoc tog cong
                } else if (ntsh == 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg01) + lamtron2sothapphan(m3t2 * dg02) + lamtron2sothapphan(m3t3 * dg03) + lamtron2sothapphan(m3t4 * dg04);// tien nuoc tog cong
                }

            }// ket thuc nha tro hoac to hop
            else if (kh.getChitietloai().toUpperCase().equals("B")) // tu gia su dung nuoc bi be ong sau dong ho
            {
                if (dmuc == 0)// dinh muc bang 0 tinh gia 8.200
                {
                    m3t1 = tieuthu;//m3 tieu thu
                    tien1 = lamtron2sothapphan(m3t1 * dg02tn);// tien nuoc


                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg02);//tong cong
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg02p);//tong cong
                    }
                } else if (dmuc > 0)// dinh muc lon hon 0 tinh luy tuyen theo dinh muc
                {
                    if (tieuthu <= dmuc)//muc 1 gia 6.200/dg01
                    {
                        m3t1 = tieuthu;
                    } else if (tieuthu > dmuc && tieuthu <= (dmuc + dm01))//muc 2 gia 8.200/dg02
                    {
                        m3t1 = dmuc;
                        m3t2 = tieuthu - m3t1;
                    } else if (tieuthu > (dmuc + dm01) && tieuthu <= (dmuc + dm02))//muc 3 gia 9.600/dg03
                    {
                        m3t1 = dmuc;
                        m3t2 = dm01;
                        m3t3 = tieuthu - (m3t1 + m3t2);
                    } else if (tieuthu > dmuc + dm02)//muc 4 gia 10.800/dg04
                    {
                        m3t1 = dmuc;
                        m3t2 = dm01;
                        m3t3 = dm01;
                        m3t4 = tieuthu - (m3t1 + m3t2 + m3t3);
                    }
                    tien1 = lamtron2sothapphan(m3t1 * dg01tn);// tien  nuoc muc 1 theo dinh muc
                    tien2 = lamtron2sothapphan(m3t2 * dg02tn);// tien  nuoc muc 2 theo dinh muc dm01
                    tien3 = lamtron2sothapphan(m3t3 * dg03tn);// tien  nuoc muc 3 theo dinh muc dm01
                    tien4 = lamtron2sothapphan(m3t4 * dg04tn);// tien  nuoc muc 4 theo dinh muc

                    tiennuoc = tien1 + tien2 + tien3 + tien4;//tong nuoc
                    if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01p) + lamtron2sothapphan(m3t2 * dg02p) + lamtron2sothapphan(m3t3 * dg03p) + lamtron2sothapphan(m3t4 * dg04p);// tien nuoc tog cong
                    } else if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01) + lamtron2sothapphan(m3t2 * dg02) + lamtron2sothapphan(m3t3 * dg03) + lamtron2sothapphan(m3t4 * dg04);// tien nuoc tog cong
                    }

                    //    tongcong =  m3t1 * dg01 +  m3t2 * dg02 +  m3t3 * dg03 +  m3t4 * dg04;//tong cong
                }

            }// tu gia bi be ống
            else// tinh luy tuyen binh thuong theo bon muc
            {
                if (tieuthu <= dm01)//muc 1
                {
                    m3t1 = tieuthu;
                } else if (tieuthu > dm01 && tieuthu <= dm02)//muc 2
                {
                    m3t1 = dm01;
                    m3t2 = tieuthu - m3t1;
                } else if (tieuthu > dm02 && tieuthu <= dm03)//muc 3
                {
                    m3t1 = dm01;
                    m3t2 = dm01;
                    m3t3 = tieuthu - (m3t1 + m3t2);
                } else if (tieuthu > dm03)//muc 4
                {
                    m3t1 = dm01;
                    m3t2 = dm01;
                    m3t3 = dm01;
                    m3t4 = tieuthu - (m3t1 + m3t2 + m3t3);
                }
                tien1 = lamtron2sothapphan(m3t1 * dg01tn);// tien  nuoc muc 1 theo dinh muc
                tien2 = lamtron2sothapphan(m3t2 * dg02tn);// tien  nuoc muc 2 theo dinh muc
                tien3 = lamtron2sothapphan(m3t3 * dg03tn);// tien  nuoc muc 3 theo dinh muc
                tien4 = lamtron2sothapphan(m3t4 * dg04tn);// tien  nuoc muc 4 theo dinh muc

                tiennuoc = tien1 + tien2 + tien3 + tien4;//tong nuoc
                if (ntsh > 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg01p) + lamtron2sothapphan(m3t2 * dg02p) + lamtron2sothapphan(m3t3 * dg03p) + lamtron2sothapphan(m3t4 * dg04p);// tien nuoc tog cong
                } else if (ntsh == 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg01) + lamtron2sothapphan(m3t2 * dg02) + lamtron2sothapphan(m3t3 * dg03) + lamtron2sothapphan(m3t4 * dg04);// tien nuoc tog cong
                }
                //  tongcong =  m3t1 * dg01 +  m3t2 * dg02 +  m3t3 * dg03 +  m3t4 * dg04;//tong cong
            }
        }// loaikh bang o / tu gia
        else if (kh.getLoaikh().equals("1"))// TINH TIEN LOAI KHACH HANG GIA MUC LOAIKH =1
        {
            m3t1 = tieuthu;
            tien1 = lamtron2sothapphan(m3t1 * dg1tn);

            tiennuoc = tien1;
            tongcong = lamtron2sothapphan(tieuthu * dg1);

            //  thue =  tongcong -  tiennuoc;
        }// ket thuc loaikh =1
        else if (kh.getLoaikh().equals("2")) {
            m3t1 = tieuthu;
            tien1 = lamtron2sothapphan(m3t1 * dg2tn);

            tiennuoc = tien1;
            tongcong = lamtron2sothapphan(tieuthu * dg2);

            //  thue =  tongcong -  tiennuoc;
        }// ket thuc loaikh =2
        else if (kh.getLoaikh().equals("3")) {
            m3t1 = tieuthu;
            tien1 = lamtron2sothapphan(m3t1 * dg3tn);

            tiennuoc = tien1;
            tongcong = lamtron2sothapphan(tieuthu * dg3);

            //  thue =  tongcong -  tiennuoc;
        }// ket thuc loaikh =3
        else if (kh.getLoaikh().equals("4")) {
            m3t1 = tieuthu;
            tien1 = lamtron2sothapphan(m3t1 * dg4tn);
            tiennuoc = tien1;
            tongcong = lamtron2sothapphan(tieuthu * dg4);


            //   thue =  tongcong -  tiennuoc;
        }// ket thuc loaikh =4
        else if (kh.getLoaikh().equals("5")) {
            if (kh.getChitietloai().toUpperCase().equals("N")) {
                m3t1 = tieuthu;
                tien1 = lamtron2sothapphan(m3t1 * dg5nttn);
                tiennuoc = tien1;
                tongcong = lamtron2sothapphan(tieuthu * dg5nt);


            } else {
                if (dmuc == 0)//dmuc
                {
                    m3t1 = tieuthu;
                    tien1 = lamtron2sothapphan(m3t1 * dg5tn);

                    tiennuoc = tien1;
                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg5);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg5p);
                    }


                } else if (dmuc > 0) {
                    if (tieuthu <= dm01)//muc 1
                    {
                        m3t1 = tieuthu;
                    } else if (tieuthu > dm01 && tieuthu <= dm02)//muc 2
                    {
                        m3t1 = dm01;
                        m3t2 = tieuthu - m3t1;
                    } else if (tieuthu > dm02)//muc 3
                    {
                        m3t1 = dm01;
                        m3t2 = dm01;
                        m3t3 = tieuthu - (m3t1 + m3t2);
                    }
                    tien1 = lamtron2sothapphan(m3t1 * dg01tn);// tien  nuoc muc 1 theo dinh muc
                    tien2 = lamtron2sothapphan(m3t2 * dg02tn);// tien  nuoc muc 2 theo dinh muc dm01
                    tien3 = lamtron2sothapphan(m3t3 * dg5tn);// tien  nuoc muc 3 theo dinh muc dm01

                    //  tien4 =  m3t4 * dg04tn;// tien  nuoc muc 4 theo dinh muc
                    tiennuoc = tien1 + tien2 + tien3;

                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01) + lamtron2sothapphan(m3t2 * dg02) + lamtron2sothapphan(m3t3 * dg5);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01p) + lamtron2sothapphan(m3t2 * dg02p) + lamtron2sothapphan(m3t3 * dg5p);
                    }

                }
            }


        }// ket thuc loaikh =5
        else if (kh.getLoaikh().equals("6")) {
            if (kh.getChitietloai().toUpperCase().equals("D"))// CO QUAN SAN XUAT
            {
                if (dmuc == 0)// dinh muc bang 0 tinh gia 8.200
                {
                    m3t1 = tieuthu;//m3 tieu thu
                    tien1 = lamtron2sothapphan(m3t1 * dg5tn);// tien nuoc


                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg5);//tong cong
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg5p);//tong cong
                    }
                } else {
                    if (tieuthu <= dmuc) {
                        m3t1 = tieuthu;
                    } else if (tieuthu > dmuc) {
                        m3t1 = dmuc;
                        m3t2 = tieuthu - m3t1;
                    }
                    tien1 = lamtron2sothapphan(m3t1 * dg6tn);
                    tien2 = lamtron2sothapphan(m3t2 * dg5tn);


                    tiennuoc = tien1 + tien2;

                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg6) + lamtron2sothapphan(m3t2 * dg5);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg6p) + lamtron2sothapphan(m3t2 * dg5p);
                    }
                }

            } else if (kh.getChitietloai().toUpperCase().equals("S"))// CO QUAN SAN XUAT
            {
                if (dmuc == 0)// dinh muc bang 0 tinh gia 10500
                {
                    m3t1 = tieuthu;//m3 tieu thu
                    tien1 = lamtron2sothapphan(m3t1 * dg7tn);// tien nuoc

                    tiennuoc = tien1;
                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg7);//tong cong
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg7p);//tong cong
                    }
                } else {
                    if (tieuthu <= dmuc) {
                        m3t1 = tieuthu;
                    } else if (tieuthu > dmuc) {
                        m3t1 = dmuc;
                        m3t2 = tieuthu - m3t1;
                    }
                    tien1 = lamtron2sothapphan(m3t1 * dg6tn);
                    tien2 = lamtron2sothapphan(m3t2 * dg7tn);


                    tiennuoc = tien1 + tien2;

                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg6) + lamtron2sothapphan(m3t2 * dg7);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg6p) + lamtron2sothapphan(m3t2 * dg7p);
                    }
                }
            } else if (kh.getChitietloai().toUpperCase().equals("N"))//CO QUAN giá 9000
            {
                m3t1 = tieuthu;
                tien1 = lamtron2sothapphan(m3t1 * dg6nttn);

                tiennuoc = tien1;
                tongcong = lamtron2sothapphan(m3t1 * dg6nt);
            } else //CO QUAN
            {
                m3t1 = tieuthu;
                tien1 = lamtron2sothapphan(m3t1 * dg6tn);

                tiennuoc = tien1;

                if (ntsh == 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg6);
                } else if (ntsh > 0) {
                    tongcong = lamtron2sothapphan(m3t1 * dg6p);
                }

            }

        }// ket thuc loaikh =6
        else if (kh.getLoaikh().equals("7")) {

            if (kh.getChitietloai().toUpperCase().equals("N")) {
                m3t1 = tieuthu;
                tien1 = lamtron2sothapphan(m3t1 * dg7nttn);
                tiennuoc = tien1;
                tongcong = lamtron2sothapphan(tieuthu * dg7nt);

            } else {
                if (dmuc == 0)//dmuc
                {
                    m3t1 = tieuthu;
                    tien1 = lamtron2sothapphan(m3t1 * dg7tn);

                    tiennuoc = tien1;

                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg7);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg7p);
                    }
                } else {
                    if (tieuthu <= dm01)//muc 1
                    {
                        m3t1 = tieuthu;
                    } else if (tieuthu > dm01 && tieuthu <= dm02)//muc 2
                    {
                        m3t1 = dm01;
                        m3t2 = tieuthu - m3t1;
                    } else if (tieuthu > dm02)//muc 3
                    {
                        m3t1 = dm01;
                        m3t2 = dm01;
                        m3t3 = tieuthu - (m3t1 + m3t2);
                    }
                    tien1 = lamtron2sothapphan(m3t1 * dg01tn);// tien  nuoc muc 1 theo dinh muc
                    tien2 = lamtron2sothapphan(m3t2 * dg02tn);// tien  nuoc muc 2 theo dinh muc dm01
                    tien3 = lamtron2sothapphan(m3t3 * dg7tn);// tien  nuoc muc 3 theo dinh muc dm01

                    //  tien4 =  m3t4 * dg04tn;// tien  nuoc muc 4 theo dinh muc
                    tiennuoc = tien1 + tien2 + tien3;

                    if (ntsh == 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01) + lamtron2sothapphan(m3t2 * dg02) + lamtron2sothapphan(m3t3 * dg7);
                    } else if (ntsh > 0) {
                        tongcong = lamtron2sothapphan(m3t1 * dg01p) + lamtron2sothapphan(m3t2 * dg02p) + lamtron2sothapphan(m3t3 * dg7p);
                    }

                }
            }


        }// ket thuc loaikh =7

        if (kh.getLoaikh().equals("0") || kh.getLoaikh().equals("5") || kh.getLoaikh().equals("6") || kh.getLoaikh().equals("7")) {
            if (ntsh == 0 && !kh.getChitietloai().toUpperCase().equals("N")) {
                phi = tiennuoc * 10 / 100;
            } else if (ntsh > 0 || (ntsh == 0 && kh.getChitietloai().toUpperCase().equals("N"))) {
                phi = 0;
            }
        } else {
            phi = 0;
        }

        if (MIENTHUE.equals("0")) {
            tienthue = tongcong - (tiennuoc + phi);
        } else {
            tienthue = tongcong - (tiennuoc + phi);
            tongcong = tongcong - tienthue;
        }
        // Luu tien nuoc, tong cong, phi3
        Log.e("TinhTienNuoc TienNuoc", String.valueOf(tiennuoc));
        Log.e("TinhTienNuoc PhiNTSH", String.valueOf(phi));
        Log.e("TinhTienNuoc TongCong", String.valueOf(tongcong));

        updateGiaNuoc(maKH, String.valueOf(tiennuoc), String.valueOf(phi), String.valueOf(tongcong));

    }

    public double lamtron2sothapphan(double a) {
        return (Math.round(a * 100)) / 100;
    }

    public List<String> GetSoLanInTheoMaKH(String makh) {
        db = myda.openDB();
        List<String> ListIn = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKHTHU + " WHERE " + MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = '" + makh + "' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String laninbiennhan = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN));

                String laninthongbaotruoc = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC));

                String laninthongbaosau = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU));


                // Adding contact to list
                ListIn.add(laninbiennhan);
                ListIn.add(laninthongbaotruoc);
                ListIn.add(laninthongbaosau);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("so luong danh sach tt tim kiem", String.valueOf(ListIn.size()));
        return ListIn;
    }

    public boolean updateLanIn(String makh, String biennhan, String thbaotruoc, String thbaosau) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINBN, biennhan);
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBTRUOC, thbaotruoc);
        values.put(MyDatabaseHelper.KEY_DANHSACHKHTHU_LANINTBSAU, thbaosau);


        // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKHTHU, values, MyDatabaseHelper.KEY_DANHSACHKHTHU_MAKH + " = ?", new String[]{makh}) > 0;
        db.close();
        return kt;

    }


    public boolean tangSoLanIn(String makh, int solanbienhan, int solantbtruoc, int solantbsau) {
        List<String> listsolanin = GetSoLanInTheoMaKH(makh);
        String solaninbiennhan = String.valueOf(Integer.parseInt(listsolanin.get(0)) + solanbienhan);
        String solanintbtruoc = String.valueOf(Integer.parseInt(listsolanin.get(1)) + solantbtruoc);
        String solanintbsau = String.valueOf(Integer.parseInt(listsolanin.get(2)) + solantbsau);
        Log.e("so lan in sau", solanintbsau);
        if (updateLanIn(makh, solaninbiennhan, solanintbtruoc, solanintbsau)) {
            return true;
        }
        return false;
    }
}
