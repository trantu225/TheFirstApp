package tiwaco.thefirstapp.DTO;

/**
 * Created by Admin on 8/6/2020.
 */

public class ResponseKHGhi {
    String MaKhachHang;
    String DanhBo;
    String DienThoai;
    String DienThoai2;
    String GhiChu;
    String STT;
    String CMND;
    String MaDuong;
    String IDHuyen;

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public ResponseKHGhi setMaKhachHang(String maKhachHang) {
        MaKhachHang = maKhachHang;
        return this;
    }

    public String getDanhBo() {
        return DanhBo;
    }

    public ResponseKHGhi setDanhBo(String danhBo) {
        DanhBo = danhBo;
        return this;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public ResponseKHGhi setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
        return this;
    }

    public String getDienThoai2() {
        return DienThoai2;
    }

    public ResponseKHGhi setDienThoai2(String dienThoai2) {
        DienThoai2 = dienThoai2;
        return this;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public ResponseKHGhi setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
        return this;
    }

    public String getSTT() {
        return STT;
    }

    public ResponseKHGhi setSTT(String STT) {
        this.STT = STT;
        return this;
    }

    public String getCMND() {
        return CMND;
    }

    public ResponseKHGhi setCMND(String CMND) {
        this.CMND = CMND;
        return this;
    }

    public String getMaDuong() {
        return MaDuong;
    }

    public ResponseKHGhi setMaDuong(String maDuong) {
        MaDuong = maDuong;
        return this;
    }

    public String getIDHuyen() {
        return IDHuyen;
    }

    public ResponseKHGhi setIDHuyen(String IDHuyen) {
        this.IDHuyen = IDHuyen;
        return this;
    }
}
