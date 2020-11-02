package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 3/5/2019.
 */

public class ResponePayTamThu {

    private List<LOGNBDTO> ListError;
    private int ResponseCode;
    private String ResponseDesc;
    private String ResponseDate;

    public List<LOGNBDTO> getListError() {
        return ListError;
    }

    public void setListError(List<LOGNBDTO> listError) {
        ListError = listError;
    }

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseDesc() {
        return ResponseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        ResponseDesc = responseDesc;
    }

    public String getResponseDate() {
        return ResponseDate;
    }

    public void setResponseDate(String responseDate) {
        ResponseDate = responseDate;
    }
}
