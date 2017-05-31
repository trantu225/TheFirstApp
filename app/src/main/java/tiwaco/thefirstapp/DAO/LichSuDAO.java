package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;

/**
 * Created by TUTRAN on 22/05/2017.
 */

public class LichSuDAO {
    SQLiteDatabase db ;
    MyDatabaseHelper myda ;
    Context  context;
    public LichSuDAO(Context con){
        context = con;
        myda = new MyDatabaseHelper(con);

    }
    //ADD TABLE_DUONG
    public boolean addTable_History(LichSuDTO ls) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_LICHSU_MALENH, ls.getMaLenh().trim());
        values.put(MyDatabaseHelper.KEY_LICHSU_NDLS, ls.getNoiDungLS().trim());
        values.put(MyDatabaseHelper.KEY_LICHSU_THOIGIAN, ls.getThoiGianLS().trim());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_LICHSU, null, values);
        db.close();
        if(kt !=0) {
            return true;
        }
        else{
            return false;
        }
    }
    //// Getting All Duong
    public List<LichSuDTO> getAllLichSu() {
        db = myda.openDB();
        List<LichSuDTO> ListLS = new ArrayList<LichSuDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_LICHSU +" ORDER BY "+ MyDatabaseHelper.KEY_LISHSU_MALS + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LichSuDTO ls = new LichSuDTO();
                ls.setMaLS(cursor.getInt(0));
                Log.e("0", String.valueOf(cursor.getInt(0)));
                ls.setNoiDungLS(cursor.getString(1));
                Log.e("1", String.valueOf(cursor.getString(1)));
                ls.setMaLenh(cursor.getString(2));
                Log.e("3", String.valueOf(cursor.getString(2)));
                ls.setThoiGianLS(cursor.getString(3));
                Log.e("2", String.valueOf(cursor.getString(3)));


                // Adding contact to list
                ListLS.add(ls);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListLS;
    }
}
