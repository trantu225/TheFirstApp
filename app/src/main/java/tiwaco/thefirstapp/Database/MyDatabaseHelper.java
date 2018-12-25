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
import tiwaco.thefirstapp.DTO.TinhTrangTLKDTO;

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
    public static final String TABLE_DUONGTHU = "DuongThu";
    public static final String TABLE_DANHSACHKH = "danhsachkh";
    public static final String TABLE_DANHSACHKHTHU = "danhsachkhthu";
    public static final String TABLE_LICHSU = "lichsu";
    public static final String TABLE_TINHTRANGTLK = "tinhtrangtlk";
    public static final String TABLE_THANHTOAN = "thanhtoan";


    // Duong Table Columns names
    public static final String KEY_DUONG_MADUONG = "maduong";
    public static final String KEY_DUONG_TENDUONG = "tenduong";
    public static final String KEY_DUONG_TRANGTHAI = "trangthai"; //0:chua ghi, 1: da ghi
    public static final String KEY_DUONG_TRANGTHAI_THU = "trangthaithu"; //0:chua ghi, 1: da thu
    public static final String KEY_DUONG_KHOASO = "khoaso"; //0:chua khoa , 1:dakhoa
    //DuongThu Table
    public static final String KEY_DUONGTHU_MADUONG = "maduong";
    public static final String KEY_DUONGTHU_TENDUONG = "tenduong";
    public static final String KEY_DUONGTHU_TRANGTHAI = "trangthai"; //0:chua ghi, 1: da ghi
    public static final String KEY_DUONGTHU_TRANGTHAI_THU = "trangthaithu"; //0:chua ghi, 1: da thu
    public static final String KEY_DUONGTHU_KHOASO = "khoaso"; //0:chua khoa , 1:dakhoa

    // TinhtrangTLK Table Columns names
    public static final String KEY_TINHTRANGTLK_MATT  = "matinhtrang";
    public static final String KEY_TINHTRANGTLK_TENTT  = "tentinhtrang";
    public static final String KEY_TINHTRANGTLK_ThuTuTT  = "thututinhtrang";

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
    public static final String KEY_DANHSACHKH_BATTHUONG= "batthuong";
    public static final String KEY_DANHSACHKH_MADUONG= "maduong";
    public static final String KEY_DANHSACHKH_CAPNHAT= "capnhat";
    public static final String KEY_DANHSACHKH_LOAIKH_MOI = "loaikhmoi";
    public static final String KEY_DANHSACHKH_NTSH = "NTSH";
    public static final String KEY_DANHSACHKH_TIENNUOC = "tiennuoc";
    public static final String KEY_DANHSACHKH_PHI = "phi";
    public static final String KEY_DANHSACHKH_TONGCONG = "tongcong";
    public static final String KEY_DANHSACHKH_NOVAT = "novat";
    public static final String KEY_DANHSACHKH_THUE = "thue";
    public static final String KEY_DANHSACHKH_LANINBN = "m3muc1";
    public static final String KEY_DANHSACHKH_LANINTBTRUOC = "m3muc2";
    public static final String KEY_DANHSACHKH_LANINTBSAU = "m3muc3";
    public static final String KEY_DANHSACHKH_GHICHUTHU = "m3muc4"; //ko sd
    public static final String KEY_DANHSACHKH_TIEN1 = "tienmuc1";//ko sd
    public static final String KEY_DANHSACHKH_TIEN2 = "tienmuc2";//ko sd
    public static final String KEY_DANHSACHKH_TIEN3 = "tienmuc3";//ko sd
    public static final String KEY_DANHSACHKH_TIEN4 = "tienmuc4";//ko sd
    public static final String KEY_DANHSACHKH_NGAYTHANHTOAN = "ngaythanhtoan";
    public static final String KEY_DANHSACHKH_CAPNHATTHU= "capnhatthu";
    public static final String KEY_DANHSACHKH_NHANVIENTHU = "nhanvienthu";

//KhachHangThu Table Columns names


    public static final String KEY_DANHSACHKHTHU_CHISO = "ChiSo";
    public static final String KEY_DANHSACHKHTHU_CHISOCON = "ChiSocon";
    public static final String KEY_DANHSACHKHTHU_CHISO1 = "ChiSo1";
    public static final String KEY_DANHSACHKHTHU_CHISO1CON = "ChiSo1con";
    public static final String KEY_DANHSACHKHTHU_CHISO2 = "ChiSo2";
    public static final String KEY_DANHSACHKHTHU_CHISO2CON = "ChiSo2con";
    public static final String KEY_DANHSACHKHTHU_CHISO3 = "ChiSo3";
    public static final String KEY_DANHSACHKHTHU_CHISO3CON = "ChiSo3con";
    public static final String KEY_DANHSACHKHTHU_DANHBO = "DanhBo";
    public static final String KEY_DANHSACHKHTHU_DIACHI = "DiaChi";
    public static final String KEY_DANHSACHKHTHU_DIENTHOAI = "DienThoai";
    public static final String KEY_DANHSACHKHTHU_GHICHU = "GhiChu";
    public static final String KEY_DANHSACHKHTHU_VIDO = "Lat";
    public static final String KEY_DANHSACHKHTHU_KINHDO = "Lon";
    public static final String KEY_DANHSACHKHTHU_MAKH = "MaKhachHang";
    public static final String KEY_DANHSACHKHTHU_NHANVIEN = "NhanVien";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU = "SLTieuThu";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU1 = "SLTieuThu1";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU1CON = "SLTieuThu1con";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU2 = "SLTieuThu2";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU2CON = "SLTieuThu2con";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU3 = "SLTieuThu3";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHU3CON = "SLTieuThu3con";
    public static final String KEY_DANHSACHKHTHU_SLTIEUTHUCON = "SLTieuThucon";
    public static final String KEY_DANHSACHKHTHU_STT = "STT";
    public static final String KEY_DANHSACHKHTHU_TENKH = "TenKhachHang";
    public static final String KEY_DANHSACHKHTHU_THOIGIAN = "ThoiGian";
    public static final String KEY_DANHSACHKHTHU_TRANGTHAITLK = "TrangThaiTLK";
    public static final String KEY_DANHSACHKHTHU_CHITIETLOAI = "chitietloai";
    public static final String KEY_DANHSACHKHTHU_COTLK = "cotlk";
    public static final String KEY_DANHSACHKHTHU_DINHMUC = "dinhmuc";
    public static final String KEY_DANHSACHKHTHU_HIEUTLK = "hieutlk";
    public static final String KEY_DANHSACHKHTHU_LOAIKH = "loaikh";
    public static final String KEY_DANHSACHKHTHU_MASOTLK = "masotlk";
    public static final String KEY_DANHSACHKHTHU_BATTHUONG = "batthuong";
    public static final String KEY_DANHSACHKHTHU_MADUONG = "maduong";
    public static final String KEY_DANHSACHKHTHU_CAPNHAT = "capnhat";
    public static final String KEY_DANHSACHKHTHU_LOAIKH_MOI = "loaikhmoi";
    public static final String KEY_DANHSACHKHTHU_NTSH = "NTSH";
    public static final String KEY_DANHSACHKHTHU_TIENNUOC = "tiennuoc";
    public static final String KEY_DANHSACHKHTHU_PHI = "phi";
    public static final String KEY_DANHSACHKHTHU_TONGCONG = "tongcong";
    public static final String KEY_DANHSACHKHTHU_NOVAT = "novat";
    public static final String KEY_DANHSACHKHTHU_THUE = "thue";
    public static final String KEY_DANHSACHKHTHU_LANINBN = "m3muc1";
    public static final String KEY_DANHSACHKHTHU_LANINTBTRUOC = "m3muc2";
    public static final String KEY_DANHSACHKHTHU_LANINTBSAU = "m3muc3";
    public static final String KEY_DANHSACHKHTHU_GHICHUTHU = "m3muc4"; //ko sd
    public static final String KEY_DANHSACHKHTHU_TIEN1 = "tienmuc1";//ko sd
    public static final String KEY_DANHSACHKHTHU_TIEN2 = "tienmuc2";//ko sd
    public static final String KEY_DANHSACHKHTHU_TIEN3 = "tienmuc3";//ko sd
    public static final String KEY_DANHSACHKHTHU_TIEN4 = "tienmuc4";//ko sd
    public static final String KEY_DANHSACHKHTHU_NGAYTHANHTOAN = "ngaythanhtoan";
    public static final String KEY_DANHSACHKHTHU_CAPNHATTHU = "capnhatthu";
    public static final String KEY_DANHSACHKHTHU_NHANVIENTHU = "nhanvienthu";

    // Duong Table Columns names
    public static final String KEY_LISHSU_MALS = "malichsu";
    public static final String KEY_LICHSU_NDLS = "noidunglichsu";
    public static final String KEY_LICHSU_THOIGIAN = "thoigianlichsu";
    public static final String KEY_LICHSU_MALENH = "malenh";

    //Thanhtoan Table Columns names

    public static final String KEY_THANHTOAN_BIENLAI = "BIENLAI";
    public static final String KEY_THANHTOAN_CHISOCU = "ChiSoCu";
    public static final String KEY_THANHTOAN_CHISOMOI = "ChiSoMoi";
    public static final String KEY_THANHTOAN_GHICHU = "GhiChu";
    public static final String KEY_THANHTOAN_TRANSACTIONID = "TRANSACTIONID";
    public static final String KEY_THANHTOAN_KINHDO = "Lon";
    public static final String KEY_THANHTOAN_MAKH = "MaKhachHang";
    public static final String KEY_THANHTOAN_SLTIEUTHU = "SLTieuThu";
    public static final String KEY_THANHTOAN_MADUONG = "maduong";
    public static final String KEY_THANHTOAN_TIENNUOC = "tiennuoc";
    public static final String KEY_THANHTOAN_PHI = "phi";
    public static final String KEY_THANHTOAN_TONGCONG = "tongcong";
    public static final String KEY_THANHTOAN_KYHD = "kyhd";
    public static final String KEY_THANHTOAN_THUE = "thue";
    public static final String KEY_THANHTOAN_M31 = "m3muc1";
    public static final String KEY_THANHTOAN_M32 = "m3muc2";
    public static final String KEY_THANHTOAN_M33 = "m3muc3";
    public static final String KEY_THANHTOAN_M34 = "m3muc4";
    public static final String KEY_THANHTOAN_TIEN1 = "tienmuc1";
    public static final String KEY_THANHTOAN_TIEN2 = "tienmuc2";
    public static final String KEY_THANHTOAN_TIEN3 = "tienmuc3";
    public static final String KEY_THANHTOAN_TIEN4 = "tienmuc4";
    public static final String KEY_THANHTOAN_NGAYTHANHTOAN = "ngaythanhtoan";
    public static final String KEY_THANHTOAN_NGAYTHANHTOANREQUEST = "ngaythanhtoanrequest";
    public static final String KEY_THANHTOAN_CAPNHATTHU = "capnhatthu";
    public static final String KEY_THANHTOAN_NHANVIENTHU = "nhanvienthu";
    public static final String KEY_THANHTOAN_LANINBIENNHAN = "laninbiennhan";
    public static final String KEY_THANHTOAN_LANINTHONGBAOSAU = "laninthongbaosau";
    public static final String KEY_THANHTOAN_LANINTHONGBAOTRUOC = "laninthongbaotruoc";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DUONG_TABLE = "CREATE TABLE " + TABLE_DUONG + "("
                + KEY_DUONG_MADUONG + " TEXT PRIMARY KEY,"
                + KEY_DUONG_TENDUONG + " TEXT,"
                + KEY_DUONG_TRANGTHAI + " INTEGER,"
                + KEY_DUONG_KHOASO + " TEXT,"
                + KEY_DUONG_TRANGTHAI_THU + " INTEGER"+ ")";

        String CREATE_DUONGTHU_TABLE = "CREATE TABLE " + TABLE_DUONGTHU + "("
                + KEY_DUONGTHU_MADUONG + " TEXT PRIMARY KEY,"
                + KEY_DUONGTHU_TENDUONG + " TEXT,"
                + KEY_DUONGTHU_TRANGTHAI + " INTEGER,"
                + KEY_DUONGTHU_KHOASO + " TEXT,"
                + KEY_DUONGTHU_TRANGTHAI_THU + " INTEGER" + ")";

        String CREATE_KHACHHANG_TABLE = "CREATE TABLE  " + TABLE_DANHSACHKH + "("
                + KEY_DANHSACHKH_MAKH + " TEXT PRIMARY KEY," //0
                + KEY_DANHSACHKH_TENKH + " TEXT,"            //1
                + KEY_DANHSACHKH_DANHBO + " TEXT,"           //2
                + KEY_DANHSACHKH_DIACHI + " TEXT,"           //3
                + KEY_DANHSACHKH_DIENTHOAI + " TEXT,"        //4
                + KEY_DANHSACHKH_STT + " INTEGER,"           //5
                + KEY_DANHSACHKH_TRANGTHAITLK + " TEXT,"     //6
                + KEY_DANHSACHKH_CHITIETLOAI + " TEXT,"      //7
                + KEY_DANHSACHKH_COTLK + " TEXT,"            //8
                + KEY_DANHSACHKH_DINHMUC + " TEXT,"          //9
                + KEY_DANHSACHKH_HIEUTLK + " TEXT,"          //10
                + KEY_DANHSACHKH_LOAIKH + " TEXT,"           //11
                + KEY_DANHSACHKH_MASOTLK + " TEXT,"          //12
                + KEY_DANHSACHKH_GHICHU + " TEXT,"           //13
                + KEY_DANHSACHKH_CHISO + " TEXT,"            //14
                + KEY_DANHSACHKH_CHISOCON + " TEXT,"         //15
                + KEY_DANHSACHKH_CHISO1 + " TEXT,"           //16
                + KEY_DANHSACHKH_CHISO1CON + " TEXT,"        //17
                + KEY_DANHSACHKH_CHISO2 + " TEXT,"           //18
                + KEY_DANHSACHKH_CHISO2CON + " TEXT,"        //19
                + KEY_DANHSACHKH_CHISO3 + " TEXT,"           //20
                + KEY_DANHSACHKH_CHISO3CON + " TEXT,"        //21
                + KEY_DANHSACHKH_SLTIEUTHU + " TEXT,"        //22
                + KEY_DANHSACHKH_SLTIEUTHU1 + " TEXT,"       //23
                + KEY_DANHSACHKH_SLTIEUTHU1CON + " TEXT,"    //24
                + KEY_DANHSACHKH_SLTIEUTHU2 + " TEXT,"       //25
                + KEY_DANHSACHKH_SLTIEUTHU2CON + " TEXT,"    //26
                + KEY_DANHSACHKH_SLTIEUTHU3 + " TEXT,"       //27
                + KEY_DANHSACHKH_SLTIEUTHU3CON + " TEXT,"    //28
                + KEY_DANHSACHKH_SLTIEUTHUCON + " TEXT,"     //29
                + KEY_DANHSACHKH_VIDO + " TEXT,"             //30
                + KEY_DANHSACHKH_KINHDO + " TEXT,"           //31
                + KEY_DANHSACHKH_THOIGIAN + " TEXT,"         //32
                + KEY_DANHSACHKH_NHANVIEN + " TEXT,"         //33
                + KEY_DANHSACHKH_BATTHUONG + " TEXT,"        //34
                + KEY_DANHSACHKH_MADUONG + " TEXT,"          //35
                + KEY_DANHSACHKH_LOAIKH_MOI + " TEXT, "      //36
                + KEY_DANHSACHKH_CAPNHAT + " TEXT, "         //37
                + KEY_DANHSACHKH_NTSH + " TEXT, "            //38
                + KEY_DANHSACHKH_TIENNUOC + " TEXT, "        //39
                + KEY_DANHSACHKH_PHI + " TEXT, "             //40
                + KEY_DANHSACHKH_TONGCONG + " TEXT, "       //41
                + KEY_DANHSACHKH_NOVAT + " TEXT, "       //42
                + KEY_DANHSACHKH_THUE+ " TEXT, "          //43
                + KEY_DANHSACHKH_LANINBN + " TEXT, "          //44
                + KEY_DANHSACHKH_LANINTBTRUOC + " TEXT, "          //45
                + KEY_DANHSACHKH_LANINTBSAU + " TEXT, "          //46
                + KEY_DANHSACHKH_GHICHUTHU + " TEXT, "          //47
                + KEY_DANHSACHKH_TIEN1 + " TEXT, "          //48
                + KEY_DANHSACHKH_TIEN2 + " TEXT, "          //49
                + KEY_DANHSACHKH_TIEN3 + " TEXT, "          //50
                + KEY_DANHSACHKH_TIEN4 + " TEXT, "          //51
                + KEY_DANHSACHKH_NGAYTHANHTOAN + " TEXT, "          //52
                + KEY_DANHSACHKH_CAPNHATTHU + " TEXT, "          //53
                + KEY_DANHSACHKH_NHANVIENTHU + " TEXT " + ")";  //54


        String CREATE_KHACHHANGTHU_TABLE = "CREATE TABLE  " + TABLE_DANHSACHKHTHU + "("
                + KEY_DANHSACHKHTHU_MAKH + " TEXT PRIMARY KEY," //0
                + KEY_DANHSACHKHTHU_TENKH + " TEXT,"            //1
                + KEY_DANHSACHKHTHU_DANHBO + " TEXT,"           //2
                + KEY_DANHSACHKHTHU_DIACHI + " TEXT,"           //3
                + KEY_DANHSACHKHTHU_DIENTHOAI + " TEXT,"        //4
                + KEY_DANHSACHKHTHU_STT + " INTEGER,"           //5
                + KEY_DANHSACHKHTHU_TRANGTHAITLK + " TEXT,"     //6
                + KEY_DANHSACHKHTHU_CHITIETLOAI + " TEXT,"      //7
                + KEY_DANHSACHKHTHU_COTLK + " TEXT,"            //8
                + KEY_DANHSACHKHTHU_DINHMUC + " TEXT,"          //9
                + KEY_DANHSACHKHTHU_HIEUTLK + " TEXT,"          //10
                + KEY_DANHSACHKHTHU_LOAIKH + " TEXT,"           //11
                + KEY_DANHSACHKHTHU_MASOTLK + " TEXT,"          //12
                + KEY_DANHSACHKHTHU_GHICHU + " TEXT,"           //13
                + KEY_DANHSACHKHTHU_CHISO + " TEXT,"            //14
                + KEY_DANHSACHKHTHU_CHISOCON + " TEXT,"         //15
                + KEY_DANHSACHKHTHU_CHISO1 + " TEXT,"           //16
                + KEY_DANHSACHKHTHU_CHISO1CON + " TEXT,"        //17
                + KEY_DANHSACHKHTHU_CHISO2 + " TEXT,"           //18
                + KEY_DANHSACHKHTHU_CHISO2CON + " TEXT,"        //19
                + KEY_DANHSACHKHTHU_CHISO3 + " TEXT,"           //20
                + KEY_DANHSACHKHTHU_CHISO3CON + " TEXT,"        //21
                + KEY_DANHSACHKHTHU_SLTIEUTHU + " TEXT,"        //22
                + KEY_DANHSACHKHTHU_SLTIEUTHU1 + " TEXT,"       //23
                + KEY_DANHSACHKHTHU_SLTIEUTHU1CON + " TEXT,"    //24
                + KEY_DANHSACHKHTHU_SLTIEUTHU2 + " TEXT,"       //25
                + KEY_DANHSACHKHTHU_SLTIEUTHU2CON + " TEXT,"    //26
                + KEY_DANHSACHKHTHU_SLTIEUTHU3 + " TEXT,"       //27
                + KEY_DANHSACHKHTHU_SLTIEUTHU3CON + " TEXT,"    //28
                + KEY_DANHSACHKHTHU_SLTIEUTHUCON + " TEXT,"     //29
                + KEY_DANHSACHKHTHU_VIDO + " TEXT,"             //30
                + KEY_DANHSACHKHTHU_KINHDO + " TEXT,"           //31
                + KEY_DANHSACHKHTHU_THOIGIAN + " TEXT,"         //32
                + KEY_DANHSACHKHTHU_NHANVIEN + " TEXT,"         //33
                + KEY_DANHSACHKHTHU_BATTHUONG + " TEXT,"        //34
                + KEY_DANHSACHKHTHU_MADUONG + " TEXT,"          //35
                + KEY_DANHSACHKHTHU_LOAIKH_MOI + " TEXT, "      //36
                + KEY_DANHSACHKHTHU_CAPNHAT + " TEXT, "         //37
                + KEY_DANHSACHKHTHU_NTSH + " TEXT, "            //38
                + KEY_DANHSACHKHTHU_TIENNUOC + " TEXT, "        //39
                + KEY_DANHSACHKHTHU_PHI + " TEXT, "             //40
                + KEY_DANHSACHKHTHU_TONGCONG + " TEXT, "       //41
                + KEY_DANHSACHKHTHU_NOVAT + " TEXT, "       //42
                + KEY_DANHSACHKHTHU_THUE + " TEXT, "          //43
                + KEY_DANHSACHKHTHU_LANINBN + " TEXT, "          //44
                + KEY_DANHSACHKHTHU_LANINTBTRUOC + " TEXT, "          //45
                + KEY_DANHSACHKHTHU_LANINTBSAU + " TEXT, "          //46
                + KEY_DANHSACHKHTHU_GHICHUTHU + " TEXT, "          //47
                + KEY_DANHSACHKHTHU_TIEN1 + " TEXT, "          //48
                + KEY_DANHSACHKHTHU_TIEN2 + " TEXT, "          //49
                + KEY_DANHSACHKHTHU_TIEN3 + " TEXT, "          //50
                + KEY_DANHSACHKHTHU_TIEN4 + " TEXT, "          //51
                + KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " TEXT, "          //52
                + KEY_DANHSACHKHTHU_CAPNHATTHU + " TEXT, "          //53
                + KEY_DANHSACHKHTHU_NHANVIENTHU + " TEXT " + ")";  //54


        String CREATE_LISHSU_TABLE = "CREATE TABLE  " + TABLE_LICHSU + "("
                + KEY_LISHSU_MALS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LICHSU_NDLS + " TEXT,"
                + KEY_LICHSU_MALENH + " TEXT,"
                + KEY_LICHSU_THOIGIAN + " TEXT" + ")";

        String CREATE_TINHTRANGTLK_TABLE = "CREATE TABLE " + TABLE_TINHTRANGTLK + "("
                + KEY_TINHTRANGTLK_MATT + " TEXT PRIMARY KEY,"
                + KEY_TINHTRANGTLK_ThuTuTT + " INTEGER,"
                + KEY_TINHTRANGTLK_TENTT + " TEXT" + ")";

        String CREATE_THANHTOAN_TABLE = "CREATE TABLE  " + TABLE_THANHTOAN + "("
                + KEY_THANHTOAN_BIENLAI + " TEXT PRIMARY KEY," //0
                + KEY_THANHTOAN_MAKH + " TEXT ," //1
                + KEY_THANHTOAN_GHICHU + " TEXT,"           //2
                + KEY_THANHTOAN_CHISOCU + " TEXT,"        //3
                + KEY_THANHTOAN_CHISOMOI + " TEXT,"        //4
                + KEY_THANHTOAN_SLTIEUTHU + " TEXT,"        //5
                + KEY_THANHTOAN_MADUONG + " TEXT,"          //6
                + KEY_THANHTOAN_TIENNUOC + " TEXT, "        //7
                + KEY_THANHTOAN_PHI + " TEXT, "             //8
                + KEY_THANHTOAN_TONGCONG + " TEXT, "       //9
                + KEY_THANHTOAN_THUE + " TEXT, "          //10
                + KEY_THANHTOAN_M31 + " TEXT, "          //11
                + KEY_THANHTOAN_M32 + " TEXT, "          //12
                + KEY_THANHTOAN_M33 + " TEXT, "          //13
                + KEY_THANHTOAN_M34 + " TEXT, "          //14
                + KEY_THANHTOAN_TIEN1 + " TEXT, "          //15
                + KEY_THANHTOAN_TIEN2 + " TEXT, "          //16
                + KEY_THANHTOAN_TIEN3 + " TEXT, "          //17
                + KEY_THANHTOAN_TIEN4 + " TEXT, "          //18
                + KEY_THANHTOAN_NGAYTHANHTOAN + " TEXT, "          //19
                + KEY_THANHTOAN_CAPNHATTHU + " TEXT, "          //20
                + KEY_THANHTOAN_TRANSACTIONID + " TEXT,"             //21
                + KEY_THANHTOAN_KINHDO + " TEXT,"           //22
                + KEY_THANHTOAN_KYHD + " TEXT,"           //23
                + KEY_THANHTOAN_NHANVIENTHU + " TEXT,"           //24
                + KEY_THANHTOAN_LANINBIENNHAN + " TEXT,"           //25
                + KEY_THANHTOAN_LANINTHONGBAOSAU + " TEXT,"           //26
                + KEY_THANHTOAN_LANINTHONGBAOTRUOC + " TEXT,"           //26
                + KEY_THANHTOAN_NGAYTHANHTOANREQUEST + " TEXT " + ")";  //27


        db.execSQL(CREATE_DUONG_TABLE);
        db.execSQL(CREATE_DUONGTHU_TABLE);
        db.execSQL(CREATE_KHACHHANG_TABLE);
        db.execSQL(CREATE_KHACHHANGTHU_TABLE);
        db.execSQL(CREATE_LISHSU_TABLE);
        db.execSQL(CREATE_TINHTRANGTLK_TABLE);
        db.execSQL(CREATE_THANHTOAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        if(newVersion > oldVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONG);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONGTHU);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKHTHU);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINHTRANGTLK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHTOAN);
            // Create tables again
            onCreate(db);
        }
    }
    public void resetDatabase(SQLiteDatabase db){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONG);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONGTHU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKHTHU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINHTRANGTLK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHTOAN);
        // Create tables again
        onCreate(db);
    }
    public void resetDatabaseTT(SQLiteDatabase db){


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINHTRANGTLK);
        // Create tables again
        String CREATE_TINHTRANGTLK_TABLE = "CREATE TABLE " + TABLE_TINHTRANGTLK + "("
                + KEY_TINHTRANGTLK_MATT + " TEXT PRIMARY KEY,"
                + KEY_TINHTRANGTLK_ThuTuTT + " INTEGER,"
                + KEY_TINHTRANGTLK_TENTT + " TEXT" + ")";
        db.execSQL(CREATE_TINHTRANGTLK_TABLE);


    }


    public void resetDatabaseGHI(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINHTRANGTLK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
        String CREATE_DUONG_TABLE = "CREATE TABLE " + TABLE_DUONG + "("
                + KEY_DUONG_MADUONG + " TEXT PRIMARY KEY,"
                + KEY_DUONG_TENDUONG + " TEXT,"
                + KEY_DUONG_TRANGTHAI + " INTEGER,"
                + KEY_DUONG_KHOASO + " TEXT,"
                + KEY_DUONG_TRANGTHAI_THU + " INTEGER" + ")";
        // Create tables again
        String CREATE_TINHTRANGTLK_TABLE = "CREATE TABLE " + TABLE_TINHTRANGTLK + "("
                + KEY_TINHTRANGTLK_MATT + " TEXT PRIMARY KEY,"
                + KEY_TINHTRANGTLK_ThuTuTT + " INTEGER,"
                + KEY_TINHTRANGTLK_TENTT + " TEXT" + ")";
        String CREATE_KHACHHANG_TABLE = "CREATE TABLE  " + TABLE_DANHSACHKH + "("
                + KEY_DANHSACHKH_MAKH + " TEXT PRIMARY KEY," //0
                + KEY_DANHSACHKH_TENKH + " TEXT,"            //1
                + KEY_DANHSACHKH_DANHBO + " TEXT,"           //2
                + KEY_DANHSACHKH_DIACHI + " TEXT,"           //3
                + KEY_DANHSACHKH_DIENTHOAI + " TEXT,"        //4
                + KEY_DANHSACHKH_STT + " INTEGER,"           //5
                + KEY_DANHSACHKH_TRANGTHAITLK + " TEXT,"     //6
                + KEY_DANHSACHKH_CHITIETLOAI + " TEXT,"      //7
                + KEY_DANHSACHKH_COTLK + " TEXT,"            //8
                + KEY_DANHSACHKH_DINHMUC + " TEXT,"          //9
                + KEY_DANHSACHKH_HIEUTLK + " TEXT,"          //10
                + KEY_DANHSACHKH_LOAIKH + " TEXT,"           //11
                + KEY_DANHSACHKH_MASOTLK + " TEXT,"          //12
                + KEY_DANHSACHKH_GHICHU + " TEXT,"           //13
                + KEY_DANHSACHKH_CHISO + " TEXT,"            //14
                + KEY_DANHSACHKH_CHISOCON + " TEXT,"         //15
                + KEY_DANHSACHKH_CHISO1 + " TEXT,"           //16
                + KEY_DANHSACHKH_CHISO1CON + " TEXT,"        //17
                + KEY_DANHSACHKH_CHISO2 + " TEXT,"           //18
                + KEY_DANHSACHKH_CHISO2CON + " TEXT,"        //19
                + KEY_DANHSACHKH_CHISO3 + " TEXT,"           //20
                + KEY_DANHSACHKH_CHISO3CON + " TEXT,"        //21
                + KEY_DANHSACHKH_SLTIEUTHU + " TEXT,"        //22
                + KEY_DANHSACHKH_SLTIEUTHU1 + " TEXT,"       //23
                + KEY_DANHSACHKH_SLTIEUTHU1CON + " TEXT,"    //24
                + KEY_DANHSACHKH_SLTIEUTHU2 + " TEXT,"       //25
                + KEY_DANHSACHKH_SLTIEUTHU2CON + " TEXT,"    //26
                + KEY_DANHSACHKH_SLTIEUTHU3 + " TEXT,"       //27
                + KEY_DANHSACHKH_SLTIEUTHU3CON + " TEXT,"    //28
                + KEY_DANHSACHKH_SLTIEUTHUCON + " TEXT,"     //29
                + KEY_DANHSACHKH_VIDO + " TEXT,"             //30
                + KEY_DANHSACHKH_KINHDO + " TEXT,"           //31
                + KEY_DANHSACHKH_THOIGIAN + " TEXT,"         //32
                + KEY_DANHSACHKH_NHANVIEN + " TEXT,"         //33
                + KEY_DANHSACHKH_BATTHUONG + " TEXT,"        //34
                + KEY_DANHSACHKH_MADUONG + " TEXT,"          //35
                + KEY_DANHSACHKH_LOAIKH_MOI + " TEXT, "      //36
                + KEY_DANHSACHKH_CAPNHAT + " TEXT, "         //37
                + KEY_DANHSACHKH_NTSH + " TEXT, "            //38
                + KEY_DANHSACHKH_TIENNUOC + " TEXT, "        //39
                + KEY_DANHSACHKH_PHI + " TEXT, "             //40
                + KEY_DANHSACHKH_TONGCONG + " TEXT, "       //41
                + KEY_DANHSACHKH_NOVAT + " TEXT, "       //42
                + KEY_DANHSACHKH_THUE + " TEXT, "          //43
                + KEY_DANHSACHKH_LANINBN + " TEXT, "          //44
                + KEY_DANHSACHKH_LANINTBTRUOC + " TEXT, "          //45
                + KEY_DANHSACHKH_LANINTBSAU + " TEXT, "          //46
                + KEY_DANHSACHKH_GHICHUTHU + " TEXT, "          //47
                + KEY_DANHSACHKH_TIEN1 + " TEXT, "          //48
                + KEY_DANHSACHKH_TIEN2 + " TEXT, "          //49
                + KEY_DANHSACHKH_TIEN3 + " TEXT, "          //50
                + KEY_DANHSACHKH_TIEN4 + " TEXT, "          //51
                + KEY_DANHSACHKH_NGAYTHANHTOAN + " TEXT, "          //52
                + KEY_DANHSACHKH_CAPNHATTHU + " TEXT, "          //53
                + KEY_DANHSACHKH_NHANVIENTHU + " TEXT " + ")";  //54

        String CREATE_LISHSU_TABLE = "CREATE TABLE  " + TABLE_LICHSU + "("
                + KEY_LISHSU_MALS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LICHSU_NDLS + " TEXT,"
                + KEY_LICHSU_MALENH + " TEXT,"
                + KEY_LICHSU_THOIGIAN + " TEXT" + ")";

        db.execSQL(CREATE_TINHTRANGTLK_TABLE);
        db.execSQL(CREATE_DUONG_TABLE);
        db.execSQL(CREATE_KHACHHANG_TABLE);
        db.execSQL(CREATE_LISHSU_TABLE);


    }

    public void resetDatabaseTHU(SQLiteDatabase db) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TINHTRANGTLK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUONGTHU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHSACHKHTHU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICHSU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHTOAN);
        // Create tables again
        String CREATE_TINHTRANGTLK_TABLE = "CREATE TABLE " + TABLE_TINHTRANGTLK + "("
                + KEY_TINHTRANGTLK_MATT + " TEXT PRIMARY KEY,"
                + KEY_TINHTRANGTLK_ThuTuTT + " INTEGER,"
                + KEY_TINHTRANGTLK_TENTT + " TEXT" + ")";
        String CREATE_LISHSU_TABLE = "CREATE TABLE  " + TABLE_LICHSU + "("
                + KEY_LISHSU_MALS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LICHSU_NDLS + " TEXT,"
                + KEY_LICHSU_MALENH + " TEXT,"
                + KEY_LICHSU_THOIGIAN + " TEXT" + ")";
        String CREATE_DUONGTHU_TABLE = "CREATE TABLE " + TABLE_DUONGTHU + "("
                + KEY_DUONGTHU_MADUONG + " TEXT PRIMARY KEY,"
                + KEY_DUONGTHU_TENDUONG + " TEXT,"
                + KEY_DUONGTHU_TRANGTHAI + " INTEGER,"
                + KEY_DUONGTHU_KHOASO + " TEXT,"
                + KEY_DUONGTHU_TRANGTHAI_THU + " INTEGER" + ")";
        String CREATE_KHACHHANGTHU_TABLE = "CREATE TABLE  " + TABLE_DANHSACHKHTHU + "("
                + KEY_DANHSACHKHTHU_MAKH + " TEXT PRIMARY KEY," //0
                + KEY_DANHSACHKHTHU_TENKH + " TEXT,"            //1
                + KEY_DANHSACHKHTHU_DANHBO + " TEXT,"           //2
                + KEY_DANHSACHKHTHU_DIACHI + " TEXT,"           //3
                + KEY_DANHSACHKHTHU_DIENTHOAI + " TEXT,"        //4
                + KEY_DANHSACHKHTHU_STT + " INTEGER,"           //5
                + KEY_DANHSACHKHTHU_TRANGTHAITLK + " TEXT,"     //6
                + KEY_DANHSACHKHTHU_CHITIETLOAI + " TEXT,"      //7
                + KEY_DANHSACHKHTHU_COTLK + " TEXT,"            //8
                + KEY_DANHSACHKHTHU_DINHMUC + " TEXT,"          //9
                + KEY_DANHSACHKHTHU_HIEUTLK + " TEXT,"          //10
                + KEY_DANHSACHKHTHU_LOAIKH + " TEXT,"           //11
                + KEY_DANHSACHKHTHU_MASOTLK + " TEXT,"          //12
                + KEY_DANHSACHKHTHU_GHICHU + " TEXT,"           //13
                + KEY_DANHSACHKHTHU_CHISO + " TEXT,"            //14
                + KEY_DANHSACHKHTHU_CHISOCON + " TEXT,"         //15
                + KEY_DANHSACHKHTHU_CHISO1 + " TEXT,"           //16
                + KEY_DANHSACHKHTHU_CHISO1CON + " TEXT,"        //17
                + KEY_DANHSACHKHTHU_CHISO2 + " TEXT,"           //18
                + KEY_DANHSACHKHTHU_CHISO2CON + " TEXT,"        //19
                + KEY_DANHSACHKHTHU_CHISO3 + " TEXT,"           //20
                + KEY_DANHSACHKHTHU_CHISO3CON + " TEXT,"        //21
                + KEY_DANHSACHKHTHU_SLTIEUTHU + " TEXT,"        //22
                + KEY_DANHSACHKHTHU_SLTIEUTHU1 + " TEXT,"       //23
                + KEY_DANHSACHKHTHU_SLTIEUTHU1CON + " TEXT,"    //24
                + KEY_DANHSACHKHTHU_SLTIEUTHU2 + " TEXT,"       //25
                + KEY_DANHSACHKHTHU_SLTIEUTHU2CON + " TEXT,"    //26
                + KEY_DANHSACHKHTHU_SLTIEUTHU3 + " TEXT,"       //27
                + KEY_DANHSACHKHTHU_SLTIEUTHU3CON + " TEXT,"    //28
                + KEY_DANHSACHKHTHU_SLTIEUTHUCON + " TEXT,"     //29
                + KEY_DANHSACHKHTHU_VIDO + " TEXT,"             //30
                + KEY_DANHSACHKHTHU_KINHDO + " TEXT,"           //31
                + KEY_DANHSACHKHTHU_THOIGIAN + " TEXT,"         //32
                + KEY_DANHSACHKHTHU_NHANVIEN + " TEXT,"         //33
                + KEY_DANHSACHKHTHU_BATTHUONG + " TEXT,"        //34
                + KEY_DANHSACHKHTHU_MADUONG + " TEXT,"          //35
                + KEY_DANHSACHKHTHU_LOAIKH_MOI + " TEXT, "      //36
                + KEY_DANHSACHKHTHU_CAPNHAT + " TEXT, "         //37
                + KEY_DANHSACHKHTHU_NTSH + " TEXT, "            //38
                + KEY_DANHSACHKHTHU_TIENNUOC + " TEXT, "        //39
                + KEY_DANHSACHKHTHU_PHI + " TEXT, "             //40
                + KEY_DANHSACHKHTHU_TONGCONG + " TEXT, "       //41
                + KEY_DANHSACHKHTHU_NOVAT + " TEXT, "       //42
                + KEY_DANHSACHKHTHU_THUE + " TEXT, "          //43
                + KEY_DANHSACHKHTHU_LANINBN + " TEXT, "          //44
                + KEY_DANHSACHKHTHU_LANINTBTRUOC + " TEXT, "          //45
                + KEY_DANHSACHKHTHU_LANINTBSAU + " TEXT, "          //46
                + KEY_DANHSACHKHTHU_GHICHUTHU + " TEXT, "          //47
                + KEY_DANHSACHKHTHU_TIEN1 + " TEXT, "          //48
                + KEY_DANHSACHKHTHU_TIEN2 + " TEXT, "          //49
                + KEY_DANHSACHKHTHU_TIEN3 + " TEXT, "          //50
                + KEY_DANHSACHKHTHU_TIEN4 + " TEXT, "          //51
                + KEY_DANHSACHKHTHU_NGAYTHANHTOAN + " TEXT, "          //52
                + KEY_DANHSACHKHTHU_CAPNHATTHU + " TEXT, "          //53
                + KEY_DANHSACHKHTHU_NHANVIENTHU + " TEXT " + ")";  //54
        String CREATE_THANHTOAN_TABLE = "CREATE TABLE  " + TABLE_THANHTOAN + "("
                + KEY_THANHTOAN_BIENLAI + " TEXT PRIMARY KEY," //0
                + KEY_THANHTOAN_MAKH + " TEXT ," //1
                + KEY_THANHTOAN_GHICHU + " TEXT,"           //2
                + KEY_THANHTOAN_CHISOCU + " TEXT,"        //3
                + KEY_THANHTOAN_CHISOMOI + " TEXT,"        //4
                + KEY_THANHTOAN_SLTIEUTHU + " TEXT,"        //5
                + KEY_THANHTOAN_MADUONG + " TEXT,"          //6
                + KEY_THANHTOAN_TIENNUOC + " TEXT, "        //7
                + KEY_THANHTOAN_PHI + " TEXT, "             //8
                + KEY_THANHTOAN_TONGCONG + " TEXT, "       //9
                + KEY_THANHTOAN_THUE + " TEXT, "          //10
                + KEY_THANHTOAN_M31 + " TEXT, "          //11
                + KEY_THANHTOAN_M32 + " TEXT, "          //12
                + KEY_THANHTOAN_M33 + " TEXT, "          //13
                + KEY_THANHTOAN_M34 + " TEXT, "          //14
                + KEY_THANHTOAN_TIEN1 + " TEXT, "          //15
                + KEY_THANHTOAN_TIEN2 + " TEXT, "          //16
                + KEY_THANHTOAN_TIEN3 + " TEXT, "          //17
                + KEY_THANHTOAN_TIEN4 + " TEXT, "          //18
                + KEY_THANHTOAN_NGAYTHANHTOAN + " TEXT, "          //19
                + KEY_THANHTOAN_CAPNHATTHU + " TEXT, "          //20
                + KEY_THANHTOAN_TRANSACTIONID + " TEXT,"             //21
                + KEY_THANHTOAN_KINHDO + " TEXT,"           //22
                + KEY_THANHTOAN_KYHD + " TEXT,"           //23
                + KEY_THANHTOAN_NHANVIENTHU + " TEXT,"           //24
                + KEY_THANHTOAN_LANINBIENNHAN + " TEXT,"           //25
                + KEY_THANHTOAN_LANINTHONGBAOSAU + " TEXT,"           //26
                + KEY_THANHTOAN_LANINTHONGBAOTRUOC + " TEXT,"           //26
                + KEY_THANHTOAN_NGAYTHANHTOANREQUEST + " TEXT " + ")";  //27
        db.execSQL(CREATE_TINHTRANGTLK_TABLE);
        db.execSQL(CREATE_LISHSU_TABLE);
        db.execSQL(CREATE_DUONGTHU_TABLE);
        db.execSQL(CREATE_KHACHHANGTHU_TABLE);
        db.execSQL(CREATE_THANHTOAN_TABLE);


    }
    public SQLiteDatabase openDB(){

        return this.getWritableDatabase();
    }



}
