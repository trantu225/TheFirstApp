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

import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_MADUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_TENDUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.KEY_DUONG_TRANGTHAI;
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

        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_MADUONG, duong.getMaDuong());
        values.put(MyDatabaseHelper.KEY_DUONG_TENDUONG, duong.getTenDuong());
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI, duong.getTrangThai());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_DUONG, null, values);

        if(kt !=0) {
            return true;
        }
        else{
            return false;
        }
    }
    //// Getting All Duong
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
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
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
                // Adding contact to list
                ListDuong.add(duong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return ListDuong;
    }


    // Updating trạng thái Duong
    public boolean updateContact(String  maduong, int trangthai) {

        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_DUONG_TRANGTHAI, trangthai);

        // updating row
        long kt = db.update(MyDatabaseHelper.TABLE_DUONG, values, KEY_DUONG_MADUONG + " = ?", new String[] { maduong });

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
                new String[] { KEY_DUONG_MADUONG, KEY_DUONG_TENDUONG, KEY_DUONG_TRANGTHAI },
                KEY_DUONG_MADUONG + "=?",
                new String[] { String.valueOf(maduong) },
                null, null, null, null);
        DuongDTO duong = null;
        if (cursor != null) {
            cursor.moveToFirst();

          duong = new DuongDTO(cursor.getString(0),cursor.getString(1), cursor.getInt(2));
        }
        cursor.close();

        if(duong!=null){
            return true;
        }
        else{
            return false;
        }
    }
        public int countDuong(){
            db = myda.openDB();
            String countQuery = "SELECT  * FROM " + TABLE_DUONG;

            Cursor cursor = db.rawQuery(countQuery, null);
            int soduong = 0;
            soduong= cursor.getCount();
            cursor.close();
            return soduong;

        }

}
