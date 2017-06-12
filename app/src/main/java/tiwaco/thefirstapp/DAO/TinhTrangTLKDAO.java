package tiwaco.thefirstapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.LichSuDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;
import tiwaco.thefirstapp.Database.MyDatabaseHelper;

import static tiwaco.thefirstapp.Database.MyDatabaseHelper.TABLE_DUONG;
import static tiwaco.thefirstapp.Database.MyDatabaseHelper.TABLE_TINHTRANGTLK;

/**
 * Created by TUTRAN on 07/06/2017.
 */

public class TinhTrangTLKDAO {
    SQLiteDatabase db ;
    MyDatabaseHelper myda ;
    Context context;
    public TinhTrangTLKDAO(Context con){
        context = con;
        myda = new MyDatabaseHelper(con);

    }

    //ADD TABLE_DUONG
    public boolean addTable_TinhTrangTLK(TinhTrangTLKDTO tt) {
        db = myda.openDB();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.KEY_TINHTRANGTLK_MATT, tt.getMatt().trim());
        values.put(MyDatabaseHelper.KEY_TINHTRANGTLK_TENTT, tt.getTentt().trim());
        values.put(MyDatabaseHelper.KEY_TINHTRANGTLK_ThuTuTT, tt.getThututt());
        // Inserting Row
        long kt = db.insert(MyDatabaseHelper.TABLE_TINHTRANGTLK, null, values);
        db.close();
        if(kt !=0) {
            return true;
        }
        else{
            return false;
        }
    }
    //// Getting All Duong
    public List<TinhTrangTLKDTO> getAllTinhTrang() {
        db = myda.openDB();
        List<TinhTrangTLKDTO> ListTT = new ArrayList<TinhTrangTLKDTO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_TINHTRANGTLK ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TinhTrangTLKDTO tt = new TinhTrangTLKDTO();
                tt.setMatt(cursor.getString(0));

                tt.setThututt(cursor.getString(1));

                tt.setTentt(cursor.getString(2));




                // Adding contact to list
                ListTT.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListTT;
    }
    public int getindexTinhTrang(String matt){
        db = myda.openDB();
        String countQuery = "SELECT  * FROM " + TABLE_TINHTRANGTLK +" WHERE "+ MyDatabaseHelper.KEY_TINHTRANGTLK_TENTT+"='"+matt+"'";

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

    public String getindexTinhTrang1(String matt){
        String data= "";
        db = myda.openDB();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_TINHTRANGTLK,
                new String[]{ MyDatabaseHelper.KEY_TINHTRANGTLK_MATT,MyDatabaseHelper.KEY_TINHTRANGTLK_ThuTuTT,MyDatabaseHelper.KEY_TINHTRANGTLK_TENTT },
                MyDatabaseHelper.KEY_TINHTRANGTLK_TENTT + "=? ",
                new String[] { matt },
                null, null, null, null);
        TinhTrangTLKDTO tinhtrangtlk  = null;
        if( cursor != null &&cursor.moveToFirst()) {

            tinhtrangtlk = new TinhTrangTLKDTO(cursor.getString(0),cursor.getString(2), cursor.getString(1));
            data = tinhtrangtlk.getThututt();
            Log.e("tinhtrangtlk",tinhtrangtlk.getMatt()+"." + tinhtrangtlk.getTentt()+"."+String.valueOf(tinhtrangtlk.getThututt()) );
        }
        if(data.equals("")){
            data = "0";
        }
        cursor.close();
        db.close();
        return data;

    }



    public List<TinhTrangTLKDTO> TaoDSTinhTrang()
    {
        List<TinhTrangTLKDTO> listtt = new ArrayList<TinhTrangTLKDTO>();
        TinhTrangTLKDTO ttA = new TinhTrangTLKDTO("A","Bình Thường","0");
        TinhTrangTLKDTO ttB = new TinhTrangTLKDTO("B","Không dùng","1");
        TinhTrangTLKDTO ttC = new TinhTrangTLKDTO("C","Cúp","2");
        TinhTrangTLKDTO ttD = new TinhTrangTLKDTO("D","Đứt chì","3");
        TinhTrangTLKDTO ttH = new TinhTrangTLKDTO("H","Hỏng","4");
        TinhTrangTLKDTO ttK = new TinhTrangTLKDTO("K","Lệch kim","5");
        TinhTrangTLKDTO ttL = new TinhTrangTLKDTO("L","Lố","6");
        TinhTrangTLKDTO ttM = new TinhTrangTLKDTO("M","Mờ số","7");
        TinhTrangTLKDTO ttN = new TinhTrangTLKDTO("N","Nâng cấp đồng hồ nhiễm từ","8");
        TinhTrangTLKDTO ttV = new TinhTrangTLKDTO("V","Vắng","9");
        TinhTrangTLKDTO ttY = new TinhTrangTLKDTO("Y","Chảy van","10");
        listtt.add(ttA);
        listtt.add(ttB);
        listtt.add(ttC);
        listtt.add(ttD);
        listtt.add(ttH);
        listtt.add(ttK);
        listtt.add(ttL);
        listtt.add(ttM);
        listtt.add(ttN);
        listtt.add(ttV);
        listtt.add(ttY);


        return listtt;
    }
}
