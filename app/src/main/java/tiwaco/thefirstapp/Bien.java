package tiwaco.thefirstapp;

import android.bluetooth.BluetoothSocket;
import android.widget.ExpandableListView;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListThu2Adapter;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.KhachHangThuDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;
import tiwaco.thefirstapp.DTO.ThanhToanDTO;

/**
 * Created by TUTRAN on 04/04/2017.
 */

public class Bien {


    public static List<NhanVienDTO> listNV =null ;
    public static CustomListAdapter adapterKH =null;
    public static CustomListThu2Adapter adapterKHThu = null;
    public static List<KhachHangDTO> listKH =null;
    public static List<KhachHangThuDTO> listKH_thu = null;
    public static String ma_duong_dang_chon ="";
    public static String ma_duong_dang_chon_thu ="";
    public static int selected_item = 0;
    public static int selected_item_thu = 0;
    public static int bien_index_duong = 0;
    public static int bien_index_khachhang = 0;
    public static int bien_index_khachhang_thu = 0;
    public static int pre_item = 0;
    public static int bienghi = 1;
    public static int bienthu = 1;
    public static int bienbkall = 0;
    public static int bienbkcg = 0;
    public static int bienbkdg = 0;
    public static int bienbkdghn = 0;
    public static int bientrangthaighi = 0;
    public static int bientrangthaithu = 0;
    public static int bienkieughi = 1; // 0: tới , 1: lùi
    public static int bienSoLuongKH = 0;
    public static int bienSoLuongKHThu = 0;
    public static String bienManHinhChuyenTimKiem = ""; //Start và Main , start thì ko finish, main thi finish
    public static final String GOITIN_MADUONG = "goitinmaduong";
    public static final String GOITIN_MADUONGTHU = "goitinmaduongthu";
    public static final String GOITIN_GHINUOC = "goitinghinuoc";
    public static final String MADUONG = "maduong";
    public static final String VITRI = "indexmaduong";
    public static final String GOITIN_THU = "goitinthunuoc";
    public static final String MADUONGTHU = "maduongthu";
    public static final String VITRITHU = "indexmaduongthu";

    public static final String MAKH= "danhbo";
    public static final String MAKHTHU= "danhbothu";
    public static final String STT = "stt";
    public static final String STTTHU = "sttthu";
    public static final String SPMADUONG = "spmaduong";
    public static final String SPMADUONGTHU = "spmaduongthu";
    public static final String SPDATA = "spdata";
    public static final String SPBKALL = "spbackupall";
    public static final String SPBKDG= "spbackupdaghi";
    public static final String SPBKDGHN= "spbackupdaghihomnay";
    public static final String SPCAPNHATSERVER= "spcapnhatserver";
    public static final String SPCAPNHATSERVERTHU= "spcapnhatserverthu";
    public static final String SPCHISOLUUTUDONG = "spchisoluutudong";
    public static final String SPTAPTINLUUTUDONG = "sptaptinluutudong";
    public static UUID uuidtest = null;
    public static final String SPCNGHITHU = "spcnghithu";
    public static final String SPONOFFLUU = "sponoffluu";
    public static final String SPLUUTUDONGTHU = "spluutudongthu";
    public static final String SPLUUTUDONGCHUYENOFFLINE = "sptudongchuyenoffline";
    public static final String SPONOFFTHUOFFLINE = "sponoffthuoffline";
    public static final String SPKYHD = "spkyhd";
    public static final String SPKYHDTHU = "spkyhdthu";
    public static final String SPBKCG = "spbackupchuaghi";
    public static final String SPFLAGGHI = "spghi";
    public static final String SPSTTDANGGHI = "spstt";
    public static final String SPSTTDANGTHU = "spsttthu";
    public static final String SPNHANVIEN = "spnhanvien";
    public static final String SPDIENTHOAI = "spdienthoai";
    public static final String SPHUYEN = "sphuyen";
    public static final String SPDTHUYEN = "spdthuyen";
    public static final String SPTENNHANVIEN = "sptennhanvien";
    public static final String SPIDNHANVIEN = "spidnhanvien";
    public static final String SPCHOPHEPGHI = "spchophepghi";
    public static final String SPCHOPHEPTHU = "spchophepthu";
    public static final String SPMATKHAU = "spmatkhau";
    public static final String SPINDEXDUONG= "spindexduong";
    public static final String SPINDEXDUONGTHU= "spindexduongthu";
    public static final String SPTHOIGIANTAIGOITHU = "spthoigiantaigoithu";
    public static BluetoothSocket btsocket;
    public static OutputStream btoutputstream;
    public static List<ThanhToanDTO> ListThanhToanHo = new ArrayList<>();


}
