package tiwaco.thefirstapp.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tiwaco.thefirstapp.Bien;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DAO.KhachHangThuDAO;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TUTRAN on 04/05/2017.
 */

public class SPData  {
    Context context;
    SharedPreferences pre;
    private String kyhd;

    public SPData(Context con) {
        this.context = con;
        pre= con.getSharedPreferences(Bien.SPDATA,con.MODE_PRIVATE);
    }
    public String getDataDuongDangGhiTrongSP(){

        String maduong=pre.getString(Bien.SPMADUONG, "");
        return maduong;
    }

    public String getDataDuongDangThuTrongSP(){

        String maduong=pre.getString(Bien.SPMADUONGTHU, "");
        return maduong;
    }
    public String getDataSTTDangGhiTrongSP(){

        String stt=pre.getString(Bien.SPSTTDANGGHI, "");
        return stt;
    }
    public String getDataSTTDangThuTrongSP(){

        String stt=pre.getString(Bien.SPSTTDANGTHU, "");
        return stt;
    }
    public int getDataIndexDuongDangGhiTrongSP(){

        int stt=pre.getInt(Bien.SPINDEXDUONG, 0);
        return stt;
    }
    public int getDataIndexDuongDangThuTrongSP(){

        int stt=pre.getInt(Bien.SPINDEXDUONGTHU, 0);
        return stt;
    }

    public void luuDataIndexDuongDangThuTrongSP(int index){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPINDEXDUONGTHU, index);
        editor.commit();
    }
    public void luuDataIndexDuongDangGhiTrongSP(int index){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPINDEXDUONG, index);
        editor.commit();
    }

    public void luuDataDuongVaSTTDangGhiTrongSP(String maduong,String stt){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPMADUONG, maduong);
        editor.putString(Bien.SPSTTDANGGHI, stt);
        editor.commit();
    }

    public void luuDataDuongVaSTTDangThuTrongSP(String maduong,String stt){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPMADUONGTHU, maduong);
        editor.putString(Bien.SPSTTDANGTHU, stt);
        editor.commit();
    }

    public void luuDataFlagGhivaBackUpTrongSP(int bienghi,int flagall, int flagdaghi, int flagchuaghi, int flagdaghihn){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPFLAGGHI, bienghi);
        editor.putInt(Bien.SPBKALL, flagall);
        editor.putInt(Bien.SPBKCG, flagchuaghi);
        editor.putInt(Bien.SPBKDG, flagdaghi);
        editor.putInt(Bien.SPBKDGHN, flagdaghihn);
        editor.commit();
    }

    public void luuDataFlagGhiTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPFLAGGHI, bienghi);
        editor.commit();
    }

    public void luuDataNhanVienTrongSP(String nhanvien){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPNHANVIEN, nhanvien);
        editor.commit();
    }
    public String getDataNhanVienTrongSP(){


        String nhvien=pre.getString(Bien.SPNHANVIEN, "");
        return nhvien;
    }

    public void luuDataMatKhauNhanVienTrongSP(String nhanvien){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPMATKHAU, nhanvien);
        editor.commit();
    }
    public String getDataMatKhauNhanVienTrongSP(){


        String nhvien=pre.getString(Bien.SPMATKHAU, "");
        return nhvien;
    }
    public int getDataFlagGhiTrongSP(){


        int flagghi=pre.getInt(Bien.SPFLAGGHI, 1);
        return flagghi;
    }
    public void luuDataFlagBKAllTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKALL, bienghi);
        editor.commit();
    }

    public void luuDataFlagBKChuaGhiTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKCG, bienghi);
        editor.commit();
    }

    public void luuDataFlagBKDaghiTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKDG, bienghi);
        editor.commit();
    }
    public void luuDataFlagBKDaghiHomNayTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKDGHN, bienghi);
        editor.commit();
    }
    public int getDataBKALLTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKALL, 1);
        return flagghi;
    }

    public int getDataBKCGTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKCG, 0);
        return flagghi;
    }

    public int getDataBKDGTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKDG, 0);
        return flagghi;
    }

    public int getDataBKDGHomNayTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKDGHN, 0);
        return flagghi;
    }

    public void KhoiTaoLaiSPDATA(){
        SharedPreferences.Editor editor = pre.edit();
        String  nv = getDataNhanVienTrongSP();
        String pass  = getDataMatKhauNhanVienTrongSP();
        String hoten = getDataTenNhanVien();
        String dienthoai = getDataDienThoai();
        String idnhanvien = getDataIDNhanVien();
        String huyen = getDataHuyen();
        String dthuyen = getDataDienThoaiHuyen();
        // String trangthaighithu = getChucNangGhiThu();
        String kyhdghi = getDataKyHoaDonTrongSP();
        String kyhdthu = getDataKyHoaDonThuTrongSP();

        editor.clear();
        editor.commit();
        luuDataNhanVienTrongSP(nv);
        luuDataMatKhauNhanVienTrongSP(pass);
        //luuChucNangGhiThuTheoTrangThai(trangthaighithu);
        luuChucNangGhiThu();
        luuThongTinNhanVien(idnhanvien, hoten, dienthoai, huyen, dthuyen);
        luuDataKyHoaDonTrongSP(kyhdghi);
        luuDataKyHoaDonThuTrongSP(kyhdthu);
    }

    public void luuDataKyHoaDonTrongSP(String kyhd){
        this.kyhd = kyhd;

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPKYHD, kyhd);
        editor.commit();
    }
    public String getDataKyHoaDonTrongSP(){


        String kyhd=pre.getString(Bien.SPKYHD, "");
        return kyhd;
    }


    public void luuDataKyHoaDonThuTrongSP(String kyhd) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(Bien.SPKYHDTHU, kyhd);
        editor.commit();
    }

    public String getDataKyHoaDonThuTrongSP() {


        String kyhd = pre.getString(Bien.SPKYHDTHU, "");
        return kyhd;
    }

    public void luuDataUpdateServer(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPCAPNHATSERVER, bienghi);
        editor.commit();
    }
    public int getDataUPdateServer(){


        int flagghi=pre.getInt(Bien.SPCAPNHATSERVER, 0);
        return flagghi;
    }

    public void luuDataThuUpdateServer(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPCAPNHATSERVERTHU, bienghi);
        editor.commit();
    }
    public int getDataThuUPdateServer(){


        int flagghi = pre.getInt(Bien.SPCAPNHATSERVERTHU, 0);
        return flagghi;
    }

    public void luuDataChiSoLuuCapNhat(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPCHISOLUUTUDONG, bienghi);
        editor.commit();
    }

    public int getDataChiSoLuuCapNhat() {


        int flagghi = pre.getInt(Bien.SPCHISOLUUTUDONG, 1);
        return flagghi;
    }

    public void luuDataTuDongLuuTapTin(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPTAPTINLUUTUDONG, bienghi);
        editor.commit();
    }

    public int getDataTuDongLuuTapTin() {


        int flagghi = pre.getInt(Bien.SPTAPTINLUUTUDONG, 0);
        return flagghi;
    }

    //On:1 off:0
    public void luuDataOnOffLuu(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPONOFFLUU, bienghi);
        editor.commit();
    }

    public int getDataOnOffLuu() {


        int flagghi = pre.getInt(Bien.SPONOFFLUU, 1); //mac dinh la on
        return flagghi;
    }


    public void luuThoiGianTaiGoi() {
        String thoigian1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(Bien.SPTHOIGIANTAIGOITHU, thoigian1);
        editor.commit();
    }

    public String getThoiGianTaiGoi() {

        //tạo đối tượng Editor để lưu thay đổi
        String flagghi = pre.getString(Bien.SPTHOIGIANTAIGOITHU, ""); //mac dinh la on
        return flagghi;
    }


    public void luuDataLuuTuDongThu(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPLUUTUDONGTHU, bienghi);
        editor.commit();
    }

    public int getDataLuuTuDongThu() {


        int flagghi = pre.getInt(Bien.SPLUUTUDONGTHU, 1); //mac dinh la on
        return flagghi;
    }

    public void luuDataTuDongChuyenOffline(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPLUUTUDONGCHUYENOFFLINE, bienghi);
        editor.commit();
    }

    public int getDataTuDongChuyenOffline() {


        int flagghi = pre.getInt(Bien.SPLUUTUDONGCHUYENOFFLINE, 1); //mac dinh la on
        return flagghi;
    }


    public void luuDataThuOffline(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPONOFFTHUOFFLINE, bienghi);
        editor.commit();
    }

    public int getDataThuOffline() {


        int flagghi = pre.getInt(Bien.SPONOFFTHUOFFLINE, 0); //mac dinh la thu online
        return flagghi;
    }

    public void luuDataChoPhepGhi(int bienghi) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt(Bien.SPCHOPHEPGHI, bienghi);
        editor.commit();
    }

    public int getDataChoPhepGhi() {


        int flagghi = pre.getInt(Bien.SPCHOPHEPGHI, 1); //mac dinh la on
        return flagghi;
    }


    public void luuThongTinNhanVien(String idnhanvien, String ten, String dienthoai, String idhuyen, String dthuyen) {

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(Bien.SPIDNHANVIEN, idnhanvien);
        editor.putString(Bien.SPTENNHANVIEN, ten);
        if (dienthoai.trim().equals("")) {
            dienthoai = "0273.3873425";
        }
        editor.putString(Bien.SPDIENTHOAI, dienthoai);
        editor.putString(Bien.SPHUYEN, idhuyen);
        if (dthuyen.trim().equals("")) {
            dthuyen = "0273.3873425";
        }
        editor.putString(Bien.SPDTHUYEN, dthuyen);
        editor.commit();
    }

    public String getDataIDNhanVien() {


        String flagghi = pre.getString(Bien.SPIDNHANVIEN, "0"); //mac dinh la off
        return flagghi;
    }

    public String getDataDienThoaiHuyen() {


        String flagghi = pre.getString(Bien.SPDTHUYEN, "0273 3873425"); //mac dinh la off
        return flagghi;
    }

    public String getDataTenNhanVien() {


        String flagghi = pre.getString(Bien.SPTENNHANVIEN, ""); //mac dinh la off
        return flagghi;
    }

    public String getDataDienThoai() {


        String flagghi = pre.getString(Bien.SPDIENTHOAI, getDataDienThoaiHuyen()); //mac dinh la off
        return flagghi;
    }

    public String getDataHuyen() {


        String flagghi = pre.getString(Bien.SPHUYEN, "01"); //mac dinh la off
        Log.e("idhuyen", flagghi);
        return flagghi;
    }

    public void luuChucNangGhiThu() { //GHI, THU

        String ghithu = "";
        KhachHangDAO khdao = new KhachHangDAO(context);
        KhachHangThuDAO khthudao = new KhachHangThuDAO(context);
        Log.e("SOLUONGKHTHU", String.valueOf(khthudao.countKhachHangAll()));
        if (khdao.countKhachHangAll() == 0 && khthudao.countKhachHangAll() != 0) {
            ghithu = "THU";
        } else if (khdao.countKhachHangAll() != 0 && khthudao.countKhachHangAll() == 0) {
            ghithu = "GHI";
        } else if (khdao.countKhachHangAll() != 0 && khthudao.countKhachHangAll() != 0) {
            ghithu = "GHITHU";
        } else if (khdao.countKhachHangAll() == 0 && khthudao.countKhachHangAll() == 0) {
            ghithu = "";
        }
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(Bien.SPCNGHITHU, ghithu);
        editor.commit();
    }

    public void luuChucNangGhiThuTheoTrangThai(String trangthai) { //GHI, THU


        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(Bien.SPCNGHITHU, trangthai);
        editor.commit();
    }

    public String getChucNangGhiThu() {


        String kyhd = pre.getString(Bien.SPCNGHITHU, "");
        return kyhd;
    }
}
