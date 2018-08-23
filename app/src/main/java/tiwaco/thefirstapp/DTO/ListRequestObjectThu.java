package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 21/11/2017.
 */

public class ListRequestObjectThu {
    private List<ListKHTheoDuongThu> ListTiwaread;
    private String tenDS;
    private String tongSLkh;

    public List<ListKHTheoDuongThu> getListTiwaread() {
        return ListTiwaread;
    }

    public void setListTiwaread(List<ListKHTheoDuongThu> listTiwaread) {
        ListTiwaread = listTiwaread;
    }

    public String getTenDS() {
        return tenDS;
    }

    public void setTenDS(String tenDS) {
        this.tenDS = tenDS;
    }

    public String getTongSLkh() {
        return tongSLkh;
    }

    public void setTongSLkh(String tongSLkh) {
        this.tongSLkh = tongSLkh;
    }
}
