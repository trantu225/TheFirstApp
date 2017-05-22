package tiwaco.thefirstapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.ListTiwareadDTO;

/**
 * Created by TUTRAN on 03/04/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    public static int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "TIWAREAD";

    // Table name
    public static final String TABLE_DUONG = "Duong";
    public static final String TABLE_DANHSACHKH = "danhsachkh";
    public static final String TABLE_LICHSU = "lichsu";


    // Duong Table Columns names
    public static final String KEY_DUONG_MADUONG = "maduong";
    public static final String KEY_DUONG_TENDUONG = "tenduong";
    public static final String KEY_DUONG_TRANGTHAI = "trangthai";

    //KhachHang Table Columns names

    public static final String KEY_DANHSACHKH_CHISO = "ChiSo";
    public static final String KEY_DANHSACHKH_CHISOCON= "ChiSocon";
    public static final String KEY_DANHSACHKH_CHISO1 = "ChiSo1";
    public static final String KEY_DANHSACHKH_CHISO1CON= "ChiSo1con";
    public static final String KEY_DANHSACHKH_CHISO2 = "ChiSo2";
    public static final String KEY_DANHSACHKH_CHISO2CON= "ChiSo2con";
    public static final String KEY_DANHSACHKH_CHISO3 = "ChiSo3";
    public static final String KEY_DANHSACHKH_CHISO3CON= "ChiSo3con";
    public static final String KEY_DANHSACHKH_DANHBO = "DanhBo";
    public static final String KEY_DANHSACHKH_DIACHI= "DiaChi";
    public static final String KEY_DANHSACHKH_DIENTHOAI = "DienThoai";
    public static final String KEY_DANHSACHKH_GHICHU= "GhiChu";
    public static final String KEY_DANHSACHKH_VIDO = "Lat";
    public static final String KEY_DANHSACHKH_KINHDO = "Lon";
    public static final String KEY_DANHSACHKH_MAKH = "MaKhachHang";
    public static final String KEY_DANHSACHKH_NHANVIEN= "NhanVien";
    public static final String KEY_DANHSACHKH_SLTIEUTHU = "SLTieuThu";
    public static final String KEY_DANHSACHKH_SLTIEUTHU1= "SLTieuThu1";
    public static final String KEY_DANHSACHKH_SLTIEUTHU1CON = "SLTieuThu1con";
    public static final String KEY_DANHSACHKH_SLTIEUTHU2= "SLTieuThu2";
    public static final String KEY_DANHSACHKH_SLTIEUTHU2CON = "SLTieuThu2con";
    public static final String KEY_DANHSACHKH_SLTIEUTHU3= "SLTieuThu3";
    public static final String KEY_DANHSACHKH_SLTIEUTHU3CON = "SLTieuThu3con";
    public static final String KEY_DANHSACHKH_SLTIEUTHUCON= "SLTieuThucon";
    public static final String KEY_DANHSACHKH_STT = "STT";
    public static final String KEY_DANHSACHKH_TENKH= "TenKhachHang";
    public static final String KEY_DANHSACHKH_THOIGIAN = "ThoiGian";
    public static final String KEY_DANHSACHKH_TRANGTHAITLK= "TrangThaiTLK";
    public static final String KEY_DANHSACHKH_CHITIETLOAI = "chitietloai";
    public static final String KEY_DANHSACHKH_COTLK= "cotlk";
    public static final String KEY_DANHSACHKH_DINHMUC = "dinhmuc";
    public static final String KEY_DANHSACHKH_HIEUTLK= "hieutlk";
    public static final String KEY_DANHSACHKH_LOAIKH = "loaikh";
    public static final String KEY_DANHSACHKH_MASOTLK= "masotlk";
    public static final String KEY_DANHSACHKH_MADUONG= "maduong";


    // Duong Table Columns names
    public static final String KEY_LISHSU_MALS = "malichsu";
    public static final String KEY_LICHSU_NDLS = "noidunglichsu";
    public static final String KEY_LICHSU_THOIGIAN = "thoigianlichsu";
    public static final String KEY_LICHSU_MALENH = "malenh";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DUONG_TABLE = "CREATE TABLE " + TABLE_DUONG + "("
                + KEY_DUONG_MADUONG + " TEXT PRIMARY KEY,"
                + KEY_DUONG_TENDUONG + " TEXT,"
                + KEY_DUONG_TRANGTHAI + " INTEGER" + ")";

        String CREATE_KHACHHANG_TABLE = "CREATE TABLE  " + TABLE_DANHSACHKH + "("
                + KEY_DANHSACHKH_MAKH + " TEXT PRIMARY KEY,"
                + KEY_DANHSACHKH_TENKH + " TEXT,"
                + KEY_DANHSACHKH_DANHBO + " TEXT,"
                + KEY_DANHSACHKH_DIACHI + " TEXT,"
                + KEY_DANHSACHKH_DIENTHOAI + " TEXT,"
                + KEY_DANHSACHKH_STT + " INTEGER,"
                + KEY_DANHSACHKH_TRANGTHAITLK + " TEXT,"
                + KEY_DANHSACHKH_CHITIETLOAI + " TEXT,"
                + KEY_DANHSACHKH_COTLK + " TEXT,"
                + KEY_DANHSACHKH_DINHMUC + " TEXT,"
                + KEY_DANHSACHKH_HIEUTLK + " TEXT,"
                + KEY_DANHSACHKH_LOAIKH + " TEXT,"
                + KEY_DANHSACHKH_MASOTLK + " TEXT,"
                + KEY_DANHSACHKH_GHICHU + " TEXT,"
                + KEY_DANHSACHKH_CHISO + " TEXT,"
                + KEY_DANHSACHKH_CHISOCON + " TEXT,"
                + KEY_DANHSACHKH_CHISO1 + " TEXT,"
                + KEY_DANHSACHKH_CHISO1CON + " TEXT,"
                + KEY_DANHSACHKH_CHISO2 + " TEXT,"
                + KEY_DANHSACHKH_CHISO2CON + " TEXT,"
                + KEY_DANHSACHKH_CHISO3 + " TEXT,"
                + KEY_DANHSACHKH_CHISO3CON + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU1 + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU1CON + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU2 + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU2CON + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU3 + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHU3CON + " TEXT,"
                + KEY_DANHSACHKH_SLTIEUTHUCON + " TEXT,"
                + KEY_DANHSACHKH_VIDO + " TEXT,"
                + KEY_DANHSACHKH_KINHDO + " TEXT,"
                + KEY_DANHSACHKH_THOIGIAN + " TEXT,"
                + KEY_DANHSACHKH_NHANVIEN + " TEXT,"
                + KEY_DANHSACHKH_MADUONG + " TEXT" + ")";

        String CREATE_LISHSU_TABLE = "CREATE TABLE  " + TABLE_LICHSU + "("
                + KEY_LISHSU_MALS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LICHSU_NDLS + " TEXT,"
                + KEY_LICHSU_MALENH + " TEXT,"
                + KEY_LICHSU_THOIGIAN + " TEXT" + ")";

        db.execSQL(CREATE_DUONG_TABLE);
        db.execSQL(CREATE_KHACHHANG_TABLE);
        db.execSQL(CREATE_LISHSU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        if(newVersion > oldVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONG);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
            // Create tables again
            onCreate(db);
        }
    }
    public void resetDatabase(SQLiteDatabase db){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
        // Create tables again
        onCreate(db);
    }

    public SQLiteDatabase openDB(){

        return this.getWritableDatabase();
    }



}
