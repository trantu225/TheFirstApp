package tiwaco.thefirstapp;

import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;
import tiwaco.thefirstapp.DTO.NhanVienDTO;

/**
 * Created by TUTRAN on 04/04/2017.
 */

public class Bien {

    public static List<DuongDTO> listDuongChuaGhi = null ;
    public static List<DuongDTO> listDuongDaGhi = null ;
    public static List<DuongDTO> listDuongAll = null;
    public static DuongDTO DuongDuocChon = null;
    public static List<KhachHangDTO> listKHDaGhi = null ;
    public static List<KhachHangDTO> listKHChuaGhi =null ;
    public static List<KhachHangDTO> listKHALl =null ;
    public static List<NhanVienDTO> listNV =null ;
    public static CustomListAdapter adapterKH =null;
    public static String ma_duong_dang_chon ="";
    public static int selected_item = 0;
    public static int pre_item = 0;
    public static final String GOITIN_MADUONG = "goitinmaduong";
    public static final String MADUONG = "maduong";
    public static final String SPMADUONG = "spmaduong";
    public static final String SPDATA = "spdata";

}
