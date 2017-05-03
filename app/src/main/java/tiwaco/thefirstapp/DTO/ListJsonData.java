package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 03/05/2017.
 */

public class ListJsonData {
    private List<ListTiwareadDTO> ListTiwaread;
    private String tenDS;
    private String tongSLkh;

    public List<ListTiwareadDTO> getListTiwaread() {
        return ListTiwaread;
    }

    public void setListTiwaread(List<ListTiwareadDTO> listTiwaread) {
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
