package tiwaco.thefirstapp;

import android.widget.ExpandableListView;

import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;

/**
 * Created by TUTRAN on 04/04/2017.
 */

public class Bien {


    public static List<NhanVienDTO> listNV =null ;
    public static CustomListAdapter adapterKH =null;
    public static List<KhachHangDTO> listKH =null;
    public static String ma_duong_dang_chon ="";
    public static int selected_item = 0;
    public static int bien_index_duong = 0;
    public static int bien_index_khachhang = 0;
    public static int pre_item = 0;
    public static int bienghi = 1;
    public static int bienbkall = 0;
    public static int bienbkcg = 0;
    public static int bienbkdg = 0;
    public static int bienbkdghn = 0;
    public static int bientrangthaighi = 0;
    public static int bienkieughi = 1; // 0: tới , 1: lùi
    public static int bienSoLuongKH = 0;
    public static String bienManHinhChuyenTimKiem = ""; //Start và Main , start thì ko finish, main thi finish
    public static final String GOITIN_MADUONG = "goitinmaduong";
    public static final String GOITIN_GHINUOC = "goitinghinuoc";
    public static final String MADUONG = "maduong";
    public static final String VITRI = "indexmaduong";
    public static final String MAKH= "danhbo";
    public static final String STT = "stt";
    public static final String SPMADUONG = "spmaduong";
    public static final String SPDATA = "spdata";
    public static final String SPBKALL = "spbackupall";
    public static final String SPBKDG= "spbackupdaghi";
    public static final String SPBKDGHN= "spbackupdaghihomnay";
    public static final String SPCAPNHATSERVER= "spcapnhatserver";

    public static final String SPKYHD = "spkyhd";
    public static final String SPBKCG = "spbackupchuaghi";
    public static final String SPFLAGGHI = "spghi";
    public static final String SPSTTDANGGHI = "spstt";
    public static final String SPNHANVIEN = "spnhanvien";
    public static final String SPMATKHAU = "spmatkhau";
    public static final String SPINDEXDUONG= "spindexduong";


}
