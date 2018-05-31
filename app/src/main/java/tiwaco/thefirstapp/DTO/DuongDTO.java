package tiwaco.thefirstapp.DTO;

/**
 * Created by TUTRAN on 03/04/2017.
 */

public class DuongDTO {
    String MaDuong;
    String TenDuong;
    Integer TrangThai;
    String KhoaSo;



    Integer TrangThaiThu;
    public DuongDTO(){}


    public DuongDTO(String maduong, String tenduong, Integer trangthai,String khoaso,Integer thu){
        this.MaDuong = maduong;
        this.TenDuong = tenduong;
        this.TrangThai = trangthai;
        this.KhoaSo  =khoaso;
        this.TrangThaiThu = thu;
    }

    public Integer getTrangThaiThu() {
        return TrangThaiThu;
    }

    public void setTrangThaiThu(Integer trangThaiThu) {
        TrangThaiThu = trangThaiThu;
    }
    public String getMaDuong() {
        return MaDuong;
    }

    public String getTenDuong() {
        return TenDuong;
    }

    public Integer getTrangThai() {
        return TrangThai;
    }

    public void setTenDuong(String tenDuong) {
        TenDuong = tenDuong;
    }

    public void setMaDuong(String maDuong) {
        MaDuong = maDuong;
    }

    public void setTrangThai(Integer trangThai) {
        TrangThai = trangThai;
    }

    public String getKhoaSo() {
        return KhoaSo;
    }

    public void setKhoaSo(String khoaSo) {
        KhoaSo = khoaSo;
    }
}
