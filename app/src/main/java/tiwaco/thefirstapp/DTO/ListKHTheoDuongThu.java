package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 21/11/2017.
 */

public class ListKHTheoDuongThu {
    String Maduong;
    String Tenduong;
    List<RequestObjectThu> TiwareadList;

    public List<RequestObjectThu> getTiwareadList() {
        return TiwareadList;
    }

    public String getMaDuong() {
        return Maduong;
    }

    public String getTenDuong() {
        return Tenduong;
    }

    public void setTiwareadList(List<RequestObjectThu> tiwareadList) {
        TiwareadList = tiwareadList;
    }

    public void setMaDuong(String maDuong) {
        Maduong = maDuong;
    }

    public void setTenDuong(String tenDuong) {
        Tenduong = tenDuong;
    }
}
