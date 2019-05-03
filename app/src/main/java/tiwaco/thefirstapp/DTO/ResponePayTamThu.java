package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 3/5/2019.
 */

public class ResponePayTamThu {

    List<LOGNBDTO> ListError;
    int ResponseCode;
    String ResponseDesc;
    String ResponseDate;

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
