package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 21/11/2017.
 */

public class ListKHTheoDuong {
    String Maduong;
    String Tenduong;
    String Huyen;
    List<RequestObject> TiwareadList;

    public List<RequestObject> getTiwareadList() {
        return TiwareadList;
    }

    public String getMaDuong() {
        return Maduong;
    }

    public String getTenDuong() {
        return Tenduong;
    }

    public String getHuyen() {
        return Huyen;
    }

    public void setTiwareadList(List<RequestObject> tiwareadList) {
        TiwareadList = tiwareadList;
    }

    public void setMaDuong(String maDuong) {
        Maduong = maDuong;
    }

    public void setTenDuong(String tenDuong) {
        Tenduong = tenDuong;
    }

    public void setHuyen(String huyen) {
        Huyen = huyen;
    }

}
