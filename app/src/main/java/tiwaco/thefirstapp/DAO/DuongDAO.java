package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;

import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_KHOASO;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_MADUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_TENDUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_TRANGTHAI;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_TRANGTHAI_THU;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.TABLE_DUONG;

/**
 * Created by TUTRAN on 03/04/2017.
 */

public class DuongDAO {
    SQLiteDatabase db ;
    MyDatabaseHelper myda ;
    public DuongDAO(Context con){
        myda = new MyDatabaseHelper(con);
 //       db = myda.openDB();
    }
    //ADD TABLE_DUONG
    public boolean addTable_Duong(DuongDTO duong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_MADUONG, duong.getMaDuong());
        values.put(MyDatabaseHelper.KEY_DUONG_TENDUONG, duong.getTenDuong());
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI, duong.getTrangThai());
        values.put(MyDatabaseHelper.KEY_DUONG_KHOASO, duong.getKhoaSo());
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI_THU, duong.getTrangThaiThu());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DUONG, null, values);
        db.close();
        if(kt !=0) {
            return true;
        }
        else{
            return false;
        }
    }
    //// Getting All Duong
    public List<DuongDTO> getAllDuong() {
        db = myda.openDB();
        List<DuongDTO> ListDuong = new ArrayList<DuongDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONG ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongDTO duong = new DuongDTO();
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





    public List<DuongDTO> getAllDuongChuaGhi() {
        db = myda.openDB();
        List<DuongDTO> ListDuong = new ArrayList<DuongDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONG +" WHERE " + MyDatabaseHelper.KEY_DUONG_TRANGTHAI +"=0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongDTO duong = new DuongDTO();
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

    public List<DuongDTO> getAllDuongDaGhi() {
        db = myda.openDB();
        List<DuongDTO> ListDuong = new ArrayList<DuongDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONG +" WHERE " + MyDatabaseHelper.KEY_DUONG_TRANGTHAI +"=1 ";


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongDTO duong = new DuongDTO();
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


    public List<DuongDTO> getAllDuongChuaKhoaSo() {
        db = myda.openDB();
        List<DuongDTO> ListDuong = new ArrayList<DuongDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_DUONG +" WHERE " + MyDatabaseHelper.KEY_DUONG_KHOASO +"='0' ";


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DuongDTO duong = new DuongDTO();
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
    public boolean updateDuongDaGhi(String  maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI, 1);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONG, values, KEY_DUONG_MADUONG + " = ?", new String[] { maduong });
        db.close();
        if(kt != 0){
            return true;
        }
        else{
            return false;
        }


    }

    public boolean updateDuongDaThu(String  maduong) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI_THU, 1);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONG, values, KEY_DUONG_MADUONG + " = ?", new String[] { maduong });
        db.close();
        if(kt != 0){
            return true;
        }
        else{
            return false;
        }


    }


    //0: chua khoa, 1: da khoa
    public boolean updateDuongKhoaSo(String  maduong,String khoa) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_KHOASO, khoa);//1:: đã ghi

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONG, values, KEY_DUONG_MADUONG + " = ?", new String[] { maduong });
        db.close();
        if(kt != 0){
            return true;
        }
        else{
            return false;
        }


    }
    public boolean checkExistDuong(String maduong){
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONG,
                new String[] { KEY_DUONG_MADUONG, KEY_DUONG_TENDUONG, KEY_DUONG_TRANGTHAI,KEY_DUONG_KHOASO,KEY_DUONG_TRANGTHAI_THU },
                KEY_DUONG_MADUONG + "=?",
                new String[] { String.valueOf(maduong) },
                null, null, null, null);
        DuongDTO duong = null;
        if (cursor != null &&cursor.moveToFirst()) {


          duong = new DuongDTO(cursor.getString(0),cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getInt(4));
        }
        cursor.close();
        db.close();
        if(duong!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public String getTenDuongTheoMa(String maduong){
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONG,
                new String[] { KEY_DUONG_MADUONG, KEY_DUONG_TENDUONG, KEY_DUONG_TRANGTHAI,KEY_DUONG_KHOASO, KEY_DUONG_TRANGTHAI_THU },
                KEY_DUONG_MADUONG + "=?",
                new String[] { String.valueOf(maduong) },
                null, null, null, null);
        DuongDTO duong = null;
        if (cursor != null && cursor.moveToFirst()) {


            duong = new DuongDTO(cursor.getString(0),cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getInt(4));
        }
        cursor.close();
        db.close();
        if(duong!=null){
            return duong.getTenDuong();
        }
        else{
            return "";
        }









    }
    public DuongDTO getDuongTheoMa(String maduong){
        db = myda.openDB();
        Cursor cursor = db.query(TABLE_DUONG,
                new String[] { KEY_DUONG_MADUONG, KEY_DUONG_TENDUONG, KEY_DUONG_TRANGTHAI,KEY_DUONG_KHOASO,KEY_DUONG_TRANGTHAI_THU },
                KEY_DUONG_MADUONG + "=?",
                new String[] { String.valueOf(maduong) },
                null, null, null, null);
        DuongDTO duong = null;
        if (cursor != null &&cursor.moveToFirst()) {


            duong = new DuongDTO(cursor.getString(0),cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getInt(4));
        }
        cursor.close();
        db.close();
       return duong;
    }



        public int countDuong(){
            db = myda.openDB();
            String countQuery = "SELECT  * FROM " + TABLE_DUONG;

            Cursor cursor = db.rawQuery(countQuery, null);
            int soduong = 0;
            soduong= cursor.getCount();
            cursor.close();
            db.close();
            return soduong;

        }

    public int countDuongChuaGhi(){
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONG +" WHERE "+ MyDatabaseHelper.KEY_DUONG_TRANGTHAI+"=0";

        Cursor cursor = db.rawQuery(countQuery, null);
        int soduong = 0;
        soduong= cursor.getCount();
        cursor.close();
        db.close();
        return soduong;

    }

    public int countDuongChuaThu(){
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONG +" WHERE "+ MyDatabaseHelper.KEY_DUONG_TRANGTHAI_THU+"=0";

        Cursor cursor = db.rawQuery(countQuery, null);
        int soduong = 0;
        soduong= cursor.getCount();
        cursor.close();
        db.close();
        return soduong;

    }
    public int getindexDuong(String maduong){
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_DUONG +" WHERE "+ MyDatabaseHelper.KEY_DUONG_MADUONG+"='"+maduong+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int index = 0;
        index= cursor.getPosition();
        if(index ==-1) {
            index = 0;
        }
        cursor.close();
        db.close();
        return index;

    }
}
