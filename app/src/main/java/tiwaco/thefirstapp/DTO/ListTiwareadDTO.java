package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 30/03/2017.
 */

public class ListTiwareadDTO {
    String MaDuong;
    String TenDuong;
    List<KhachHangDTO> TiwareadList;

    public List<KhachHangDTO> getTiwareadList() {
        return TiwareadList;
    }

    public String getMaDuong() {
        return MaDuong;
    }

    public String getTenDuong() {
        return TenDuong;
    }

    public void setTiwareadList(List<KhachHangDTO> tiwareadList) {
        TiwareadList = tiwareadList;
    }

    public void setMaDuong(String maDuong) {
        MaDuong = maDuong;
    }

    public void setTenDuong(String tenDuong) {
        TenDuong = tenDuong;
    }
}
