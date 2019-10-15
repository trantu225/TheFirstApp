package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 12/7/2019.
 */

public class ResponePayBillAllNB {
    public int ResponseCode;
    public String ResponseDesc;
    public String ResponseDate;
    public CustBillNB CusBill;
    public List<TienNuocInFo> DataInfo;

    public int getResponseCode() {
        return ResponseCode;
    }

    public ResponePayBillAllNB setResponseCode(int responseCode) {
        ResponseCode = responseCode;
        return this;
    }

    public String getResponseDesc() {
        return ResponseDesc;
    }

    public ResponePayBillAllNB setResponseDesc(String responseDesc) {
        ResponseDesc = responseDesc;
        return this;
    }

    public String getResponseDate() {
        return ResponseDate;
    }

    public ResponePayBillAllNB setResponseDate(String responseDate) {
        ResponseDate = responseDate;
        return this;
    }

    public CustBillNB getCusBill() {
        return CusBill;
    }

    public ResponePayBillAllNB setCusBill(CustBillNB cusBill) {
        CusBill = cusBill;
        return this;
    }

    public List<TienNuocInFo> getDataInfo() {
        return DataInfo;
    }

    public ResponePayBillAllNB setDataInfo(List<TienNuocInFo> dataInfo) {
        DataInfo = dataInfo;
        return this;
    }
}
