package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 18/6/2019.
 */

public class JSONBACKUPTHU {
    List<JSONDUONGTHU> listduong;
    List<JSONTHANHTOANTHU> listthanhtoan;
    List<JSONKHTHU> listkh;

    public List<JSONDUONGTHU> getListduong() {
        return listduong;
    }

    public JSONBACKUPTHU setListduong(List<JSONDUONGTHU> listduong) {
        this.listduong = listduong;
        return this;
    }

    public List<JSONTHANHTOANTHU> getListthanhtoan() {
        return listthanhtoan;
    }

    public JSONBACKUPTHU setListthanhtoan(List<JSONTHANHTOANTHU> listthanhtoan) {
        this.listthanhtoan = listthanhtoan;
        return this;
    }

    public List<JSONKHTHU> getListkh() {
        return listkh;
    }

    public JSONBACKUPTHU setListkh(List<JSONKHTHU> listkh) {
        this.listkh = listkh;
        return this;
    }
}
