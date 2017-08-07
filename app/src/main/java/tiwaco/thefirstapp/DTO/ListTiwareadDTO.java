package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 30/03/2017.
 */

public class ListTiwareadDTO {
    String Maduong;
    String Tenduong;
    List<KhachHangDTO> TiwareadList;

    public List<KhachHangDTO> getTiwareadList() {
        return TiwareadList;
    }

    public String getMaDuong() {
        return Maduong;
    }

    public String getTenDuong() {
        return Tenduong;
    }

    public void setTiwareadList(List<KhachHangDTO> tiwareadList) {
        TiwareadList = tiwareadList;
    }

    public void setMaDuong(String maDuong) {
        Maduong = maDuong;
    }

    public void setTenDuong(String tenDuong) {
        Tenduong = tenDuong;
    }
}
