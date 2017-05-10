package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;



/**
 * Created by TUTRAN on 04/04/2017.
 */

public class KhachHangDAO {
    SQLiteDatabase db ;
    MyDatabaseHelper myda;
    public KhachHangDAO(Context con){
        myda = new MyDatabaseHelper(con);
   //     db = myda.openDB();
    //    myda.resetDatabase(db);
    }
    public boolean addTable_KH(KhachHangDTO kh, String maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_MAKH , kh.getMaKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_TENKH , kh.getTenKhachHang().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DANHBO , kh.getDanhBo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DIACHI , kh.getDiaChi().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI , kh.getDienThoai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_STT , Integer.parseInt(kh.getSTT().trim()));
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_TRANGTHAITLK , kh.getTrangThaiTLK().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHITIETLOAI , kh.getChitietloai().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_COTLK , kh.getCotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DINHMUC , kh.getDinhmuc().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_HIEUTLK , kh.getHieutlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_LOAIKH , kh.getLoaikh().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_MASOTLK , kh.getMasotlk().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_GHICHU , kh.getGhiChu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO , kh.getChiSo().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISOCON , kh.getChiSocon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO1 , kh.getChiSo1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO1CON , kh.getChiSo1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO2 , kh.getChiSo2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO2CON , kh.getChiSo2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO3 , kh.getChiSo3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO3CON , kh.getChiSo3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU , kh.getSLTieuThu().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1 , kh.getSLTieuThu1().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1CON , kh.getSLTieuThu1con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2 , kh.getSLTieuThu2().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2CON , kh.getSLTieuThu2con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3 , kh.getSLTieuThu3().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3CON , kh.getSLTieuThu3con().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHUCON , kh.getSLTieuThucon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_VIDO , kh.getLat().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_KINHDO , kh.getLon().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_THOIGIAN , kh.getThoiGian().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_NHANVIEN  , kh.getNhanVien().trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_MADUONG , maduong.trim());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DANHSACHKH, null, values);
        db.close();
        if(kt !=0) {
            return true;
        }
        else{
            return false;
        }
    }

    public List<KhachHangDTO> getAllKHChuaGhi() {
        db = myda.openDB();
        List<KhachHangDTO> ListKH = new ArrayList<KhachHangDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_CHISO +"='' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangDTO kh = new KhachHangDTO();
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


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }


    public List<KhachHangDTO> getAllKHTheoDuong(String maduong) {
        db = myda.openDB();
        List<KhachHangDTO> ListKH = new ArrayList<KhachHangDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +"='"+maduong+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangDTO kh = new KhachHangDTO();
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


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }

    public List<KhachHangDTO> getAllKHDaGhiTheoDuong(String maduong) {
        db = myda.openDB();
        List<KhachHangDTO> ListKH = new ArrayList<KhachHangDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKH_CHISO +"<>''";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangDTO kh = new KhachHangDTO();
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


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }

    public List<KhachHangDTO> getAllKHChuaGhiTheoDuong(String maduong) {
        db = myda.openDB();
        List<KhachHangDTO> ListKH = new ArrayList<KhachHangDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +"='"+maduong+"' and " + MyDatabaseHelper.KEY_DANHSACHKH_CHISO +"=''";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KhachHangDTO kh = new KhachHangDTO();
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


                // Adding contact to list
                ListKH.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListKH;
    }

    // Updating SDT
    public boolean updateDienThoai(String  maKH, String sdt) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI, sdt);

        // updating row
      boolean kt =db.update(MyDatabaseHelper.TABLE_DANHSACHKH, values, MyDatabaseHelper.KEY_DANHSACHKH_MAKH + " = ?", new String[]{maKH}) >0;
        db.close();
        return kt ;

    }


    public boolean updateKhachHang(String  maKH,String Chiso, String Chisocon, String Dienthoai, String ghichu,String vido, String kinhdo,String nhanvien, String SL, String SLCon, String thoigian, String trangthaiTLK ) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISO, Chiso.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_CHISOCON, Chisocon.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI, Dienthoai.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_GHICHU, ghichu.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_VIDO, vido.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_KINHDO, kinhdo.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_NHANVIEN, nhanvien.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU, SL.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHUCON, SLCon.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_THOIGIAN, thoigian.trim());
        values.put(MyDatabaseHelper.KEY_DANHSACHKH_TRANGTHAITLK, trangthaiTLK.trim());
      // updating row
        boolean kt = db.update(MyDatabaseHelper.TABLE_DANHSACHKH, values, MyDatabaseHelper.KEY_DANHSACHKH_MAKH + " = ?", new String[] { maKH }) >0;
        db.close();
        return  kt ;

    }
    public int countKhachHangTheoDuong(String maduong){
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +"='"+maduong+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh =cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangChuaGhiTheoDuong(String maduong){
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH +" WHERE " + MyDatabaseHelper.KEY_DANHSACHKH_MADUONG +"='"+maduong+"' and "+ MyDatabaseHelper.KEY_DANHSACHKH_CHISO+"=''";

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh =cursor.getCount();
        cursor.close();
        db.close();
        return sokh;
    }

    public int countKhachHangAll(){
        db = myda.openDB();
        int sokh = 0;
        String countQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DANHSACHKH ;

        Cursor cursor = db.rawQuery(countQuery, null);
        sokh =cursor.getCount();
        cursor.close();
        db.close();
        Log.e("sokh", String.valueOf(sokh));
        return sokh;


    }
    public boolean checkExistKH(String MaKH, String maduong){
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKH,
                new String[] {
                        MyDatabaseHelper.KEY_DANHSACHKH_MAKH ,
                        MyDatabaseHelper.KEY_DANHSACHKH_TENKH  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DANHBO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DIACHI  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI ,
                        MyDatabaseHelper.KEY_DANHSACHKH_STT ,
                        MyDatabaseHelper.KEY_DANHSACHKH_TRANGTHAITLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHITIETLOAI ,
                        MyDatabaseHelper.KEY_DANHSACHKH_COTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DINHMUC ,
                        MyDatabaseHelper.KEY_DANHSACHKH_HIEUTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_LOAIKH ,
                        MyDatabaseHelper.KEY_DANHSACHKH_MASOTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISOCON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO1  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO1CON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO2  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO2CON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO3 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO3CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHUCON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_VIDO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_KINHDO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_THOIGIAN  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_NHANVIEN
                },
                MyDatabaseHelper.KEY_DANHSACHKH_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKH_MAKH + "=?" ,
                new String[] { maduong,MaKH },
                null, null, null, null);
        KhachHangDTO kh = null;
        if (cursor != null &&  cursor.moveToFirst()) {
          //  cursor.moveToFirst();

            kh = new KhachHangDTO();
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
        }

        cursor.close();
        db.close();
        if(kh!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public KhachHangDTO getKHTheoSTT_Duong(String STT, String maduong){
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKH,
                new String[] {
                        MyDatabaseHelper.KEY_DANHSACHKH_MAKH ,
                        MyDatabaseHelper.KEY_DANHSACHKH_TENKH  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DANHBO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DIACHI  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DIENTHOAI ,
                        MyDatabaseHelper.KEY_DANHSACHKH_STT ,
                        MyDatabaseHelper.KEY_DANHSACHKH_TRANGTHAITLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHITIETLOAI ,
                        MyDatabaseHelper.KEY_DANHSACHKH_COTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_DINHMUC ,
                        MyDatabaseHelper.KEY_DANHSACHKH_HIEUTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_LOAIKH ,
                        MyDatabaseHelper.KEY_DANHSACHKH_MASOTLK ,
                        MyDatabaseHelper.KEY_DANHSACHKH_GHICHU,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISOCON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO1  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO1CON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO2  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO2CON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO3 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_CHISO3CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU1CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU2CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3 ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHU3CON ,
                        MyDatabaseHelper.KEY_DANHSACHKH_SLTIEUTHUCON  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_VIDO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_KINHDO  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_THOIGIAN  ,
                        MyDatabaseHelper.KEY_DANHSACHKH_NHANVIEN
                },
                MyDatabaseHelper.KEY_DANHSACHKH_MADUONG + "=? and " + MyDatabaseHelper.KEY_DANHSACHKH_STT + "=?" ,
                new String[] { maduong,STT },
                null, null, null, null);
        KhachHangDTO kh = null;
        if (cursor != null &&  cursor.moveToFirst()) {
            //  cursor.moveToFirst();

            kh = new KhachHangDTO();
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
        }

        cursor.close();
        db.close();
        if(kh!=null){
            return kh;
        }
        else{
            return null;
        }
    }
    public String getSTTChuaGhiNhoNhat(String maduong){
            String data= "1";
            db = myda.openDB();
            Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKH,
                    new String[]{"MIN(" +MyDatabaseHelper.KEY_DANHSACHKH_STT + ") AS MINSTT"},
                    MyDatabaseHelper.KEY_DANHSACHKH_MADUONG + "=? and "+ MyDatabaseHelper.KEY_DANHSACHKH_CHISO + "='' ",
                    new String[] { maduong },
                    null, null, null, null);
           if(cursor!=null &&  cursor.moveToFirst()) {
               Log.e("lay so thu tu chua ghi nho nhat","ok");
               data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
               if(data.equals("0")){
                   data ="1";
               }
           }

            db.close();
            return data;

    }

    public String getSTTChuaGhiNhoNhatLonHonHienTai(String maduong,String stthientai){
        String data= "1";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_DANHSACHKH,
                new String[]{"MIN(" +MyDatabaseHelper.KEY_DANHSACHKH_STT + ") AS MINSTT"},
                MyDatabaseHelper.KEY_DANHSACHKH_MADUONG + "=? and "+ MyDatabaseHelper.KEY_DANHSACHKH_CHISO + "='' and "+ MyDatabaseHelper.KEY_DANHSACHKH_STT + ">? ",
                new String[] { maduong ,stthientai},
                null, null, null, null);
        if(cursor!=null &&  cursor.moveToFirst()) {
            data = String.valueOf(cursor.getInt(0));// use the data type of the column or use String itself you can parse it
        }
        db.close();
        return data;

    }
}
