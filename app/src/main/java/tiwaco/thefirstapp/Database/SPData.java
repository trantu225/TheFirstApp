package tiwaco.thefirstapp.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import tiwaco.thefirstapp.Bien;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TUTRAN on 04/05/2017.
 */

public class SPData  {
    Context context;
    SharedPreferences pre;
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
        editor.clear();
        editor.commit();
        luuDataNhanVienTrongSP(nv);
        luuDataMatKhauNhanVienTrongSP(pass);
    }

    public void luuDataKyHoaDonTrongSP(String kyhd){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPKYHD, kyhd);
        editor.commit();
    }
    public String getDataKyHoaDonTrongSP(){


        String kyhd=pre.getString(Bien.SPKYHD, "");
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
        editor.putInt(Bien.SPCAPNHATSERVER, bienghi);
        editor.commit();
    }
    public int getDataThuUPdateServer(){


        int flagghi=pre.getInt(Bien.SPCAPNHATSERVER, 0);
        return flagghi;
    }

}
