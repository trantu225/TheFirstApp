package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongThuDTO;
import tiwaco.thefirstapp.DTO.JSONDUONGTHU;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;

import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONGTHU_KHOASO;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONGTHU_MADUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONGTHU_TENDUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI_THU;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.TABLE_DUONGTHU;

/**
 * Created by TUTRAN on 03/04/2017.
 */

public class DuongThuDAO {
    SQLiteDatabase db;
    MyDatabaseHelper myda;

    public DuongThuDAO(Context con) {
        myda = new MyDatabaseHelper(con);
        //       db = myda.openDB();
    }

    //ADD TABLE_DUONGTHU
    public boolean addTABLE_DUONGTHU(DuongThuDTO duong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONGTHU_MADUONG, duong.getMaDuong());
        values.put(MyDatabaseHelper.KEY_DUONGTHU_TENDUONG, duong.getTenDuong());
        values.put(MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI, duong.getTrangThai());
        values.put(MyDatabaseHelper.KEY_DUONGTHU_KHOASO, duong.getKhoaSo());
        values.put(MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI_THU, duong.getTrangThaiThu());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DUONGTHU, null, values);
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    //// Getting All Duong
    public List<DuongThuDTO> getAllDuong() {
        db = myda.openDB();
        List<DuongThuDTO> ListDuong = new ArrayList<DuongThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONGTHU;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongThuDTO duong = new DuongThuDTO();
                duong.setMaDuong(cursor.getString(0));
                duong.setTenDuong(cursor.getString(1));
                duong.setTrangThai(Integer.parseInt(cursor.getString(2)));
                duong.setKhoaSo(cursor.getString(3));
                duong.setTrangThaiThu(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDuong;
    }

    public List<JSONDUONGTHU> getAllDuongThu() {
        db = myda.openDB();
        List<JSONDUONGTHU> ListDuong = new ArrayList<JSONDUONGTHU>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONGTHU;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                JSONDUONGTHU duong = new JSONDUONGTHU();
                duong.setMaduong(cursor.getString(0));
                duong.setTenduong(cursor.getString(1));
                duong.setTrangthai(cursor.getString(2));
                duong.setKhoaso(cursor.getString(3));
                duong.setTrangthaithu(cursor.getString(4));
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDuong;
    }


    public List<DuongThuDTO> getAllDuongChuaGhi() {
        db = myda.openDB();
        List<DuongThuDTO> ListDuong = new ArrayList<DuongThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI + "=0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongThuDTO duong = new DuongThuDTO();
                duong.setMaDuong(cursor.getString(0));
                duong.setTenDuong(cursor.getString(1));
                duong.setTrangThai(Integer.parseInt(cursor.getString(2)));
                duong.setKhoaSo(cursor.getString(3));
                duong.setTrangThaiThu(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDuong;
    }

    public List<DuongThuDTO> getAllDuongDaGhi() {
        db = myda.openDB();
        List<DuongThuDTO> ListDuong = new ArrayList<DuongThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI + "=1 ";


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongThuDTO duong = new DuongThuDTO();
                duong.setMaDuong(cursor.getString(0));
                duong.setTenDuong(cursor.getString(1));
                duong.setTrangThai(Integer.parseInt(cursor.getString(2)));
                duong.setKhoaSo(cursor.getString(3));
                duong.setTrangThaiThu(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return ListDuong;
    }


    public List<DuongThuDTO> getAllDuongChuaKhoaSo() {
        db = myda.openDB();
        List<DuongThuDTO> ListDuong = new ArrayList<DuongThuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_KHOASO + "='0' ";


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongThuDTO duong = new DuongThuDTO();
                duong.setMaDuong(cursor.getString(0));
                duong.setTenDuong(cursor.getString(1));
                duong.setTrangThai(Integer.parseInt(cursor.getString(2)));
                duong.setKhoaSo(cursor.getString(3));
                duong.setTrangThaiThu(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return ListDuong;
    }


    //0: là chưa ghi 1: đã ghi
    // Updating trạng thái Duong
    public boolean updateDuongDaGhi(String maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI, 1);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONGTHU, values, KEY_DUONGTHU_MADUONG + " = ?", new String[]{maduong});
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }


    }

    public boolean updateDuongDaThu(String maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI_THU, 1);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONGTHU, values, KEY_DUONGTHU_MADUONG + " = ?", new String[]{maduong});
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }


    }


    //0: chua khoa, 1: da khoa
    public boolean updateDuongKhoaSo(String maduong, String khoa) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONGTHU_KHOASO, khoa);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONGTHU, values, KEY_DUONGTHU_MADUONG + " = ?", new String[]{maduong});
        db.close();
        if (kt != 0) {
            return true;
        } else {
            return false;
        }


    }

    public boolean checkExistDuong(String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONGTHU,
                new String[]{KEY_DUONGTHU_MADUONG, KEY_DUONGTHU_TENDUONG, KEY_DUONGTHU_TRANGTHAI, KEY_DUONGTHU_KHOASO, KEY_DUONGTHU_TRANGTHAI_THU},
                KEY_DUONGTHU_MADUONG + "=?",
                new String[]{String.valueOf(maduong)},
                null, null, null, null);
        DuongThuDTO duong = null;
        if (cursor != null && cursor.moveToFirst()) {


            duong = new DuongThuDTO(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        }
        cursor.close();
        db.close();
        if (duong != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getTenDuongTheoMa(String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONGTHU,
                new String[]{KEY_DUONGTHU_MADUONG, KEY_DUONGTHU_TENDUONG, KEY_DUONGTHU_TRANGTHAI, KEY_DUONGTHU_KHOASO, KEY_DUONGTHU_TRANGTHAI_THU},
                KEY_DUONGTHU_MADUONG + "=?",
                new String[]{String.valueOf(maduong)},
                null, null, null, null);
        DuongThuDTO duong = null;
        if (cursor != null && cursor.moveToFirst()) {


            duong = new DuongThuDTO(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        }
        cursor.close();
        db.close();
        if (duong != null) {
            return duong.getTenDuong();
        } else {
            return "";
        }


    }

    public DuongThuDTO getDuongTheoMa(String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONGTHU,
                new String[]{KEY_DUONGTHU_MADUONG, KEY_DUONGTHU_TENDUONG, KEY_DUONGTHU_TRANGTHAI, KEY_DUONGTHU_KHOASO, KEY_DUONGTHU_TRANGTHAI_THU},
                KEY_DUONGTHU_MADUONG + "=?",
                new String[]{String.valueOf(maduong)},
                null, null, null, null);
        DuongThuDTO duong = null;
        if (cursor != null && cursor.moveToFirst()) {


            duong = new DuongThuDTO(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        }
        cursor.close();
        db.close();
        return duong;
    }

    public String getTrangThaiKhoaSoCuaDuong(String maduong) {
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONGTHU,
                new String[]{KEY_DUONGTHU_MADUONG, KEY_DUONGTHU_TENDUONG, KEY_DUONGTHU_TRANGTHAI, KEY_DUONGTHU_KHOASO, KEY_DUONGTHU_TRANGTHAI_THU},
                KEY_DUONGTHU_MADUONG + "=?",
                new String[]{String.valueOf(maduong)},
                null, null, null, null);
        DuongThuDTO duong = null;
        if (cursor != null && cursor.moveToFirst()) {


            duong = new DuongThuDTO(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        }
        cursor.close();
        db.close();
        return duong.getKhoaSo();
    }


    public int countDuong() {
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONGTHU;

        Cursor cursor = db.rawQuery(countQuery, null);
        int soduong = 0;
        soduong = cursor.getCount();
        cursor.close();
        db.close();
        return soduong;

    }

    public int countDuongChuaGhi() {
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI + "=0";

        Cursor cursor = db.rawQuery(countQuery, null);
        int soduong = 0;
        soduong = cursor.getCount();
        cursor.close();
        db.close();
        return soduong;

    }

    public int countDuongChuaThu() {
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_TRANGTHAI_THU + "=0";

        Cursor cursor = db.rawQuery(countQuery, null);
        int soduong = 0;
        soduong = cursor.getCount();
        cursor.close();
        db.close();
        return soduong;

    }

    public int getindexDuong(String maduong) {
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONGTHU + " WHERE " + MyDatabaseHelper.KEY_DUONGTHU_MADUONG + "='" + maduong + "'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int index = 0;
        index = cursor.getPosition();
        if (index == -1) {
            index = 0;
        }
        cursor.close();
        db.close();
        return index;

    }
}
