package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 8/6/2020.
 */

public class ResponseTinhTienNuoc {
    int MaTraVe;
    ResponseKHGhi KhachHang;
    List<ThanhToanGhiDTO> listHoaDon;

    public int getMaTraVe() {
        return MaTraVe;
    }

    public ResponseTinhTienNuoc setMaTraVe(int maTraVe) {
        MaTraVe = maTraVe;
        return this;
    }

    public ResponseKHGhi getKhachHang() {
        return KhachHang;
    }

    public ResponseTinhTienNuoc setKhachHang(ResponseKHGhi khachHang) {
        KhachHang = khachHang;
        return this;
    }

    public List<ThanhToanGhiDTO> getListHoaDon() {
        return listHoaDon;
    }

    public ResponseTinhTienNuoc setListHoaDon(List<ThanhToanGhiDTO> listHoaDon) {
        this.listHoaDon = listHoaDon;
        return this;
    }
}
