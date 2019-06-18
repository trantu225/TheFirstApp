package tiwaco.thefirstapp.DTO;

/**
 * Created by Admin on 18/6/2019.
 */

public class JSONDUONGTHU {
    //    public static final String KEY_DUONG_MADUONG = "maduong";
//    public static final String KEY_DUONG_TENDUONG = "tenduong";
//    public static final String KEY_DUONG_TRANGTHAI = "trangthai"; //0:chua ghi, 1: da ghi
//    public static final String KEY_DUONG_TRANGTHAI_THU = "trangthaithu"; //0:chua ghi, 1: da thu
//    public static final String KEY_DUONG_KHOASO = "khoaso"; //0:chua khoa , 1:dakhoa

    String maduong;
    String tenduong;
    String trangthai;
    String trangthaithu;
    String khoaso;

    public String getMaduong() {
        return maduong;
    }

    public void setMaduong(String maduong) {
        this.maduong = maduong;
    }

    public String getTenduong() {
        return tenduong;
    }

    public void setTenduong(String tenduong) {
        this.tenduong = tenduong;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getTrangthaithu() {
        return trangthaithu;
    }

    public void setTrangthaithu(String trangthaithu) {
        this.trangthaithu = trangthaithu;
    }

    public String getKhoaso() {
        return khoaso;

    }

    public void setKhoaso(String khoaso) {
        this.khoaso = khoaso;
    }

}
