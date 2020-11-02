package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 19/11/2019.
 */

public class ResponseObjectNhieuHDNB {
    private int ResponseCode;
    private String ResponseDesc;
    private String ResponseDate;
    private List<TienNuocInFo> DataInfo;
    private List<PeriodNhanVienThu> DataDaThu;
    private List<LOGNBDTO> ListError;

    public int getResponseCode() {
        return ResponseCode;
    }

    public ResponseObjectNhieuHDNB setResponseCode(int responseCode) {
        ResponseCode = responseCode;
        return this;
    }

    public String getResponseDesc() {
        return ResponseDesc;
    }

    public ResponseObjectNhieuHDNB setResponseDesc(String responseDesc) {
        ResponseDesc = responseDesc;
        return this;
    }

    public String getResponseDate() {
        return ResponseDate;
    }

    public ResponseObjectNhieuHDNB setResponseDate(String responseDate) {
        ResponseDate = responseDate;
        return this;
    }

    public List<TienNuocInFo> getDataInfo() {
        return DataInfo;
    }

    public ResponseObjectNhieuHDNB setDataInfo(List<TienNuocInFo> dataInfo) {
        DataInfo = dataInfo;
        return this;
    }

    public List<PeriodNhanVienThu> getDataDaThu() {
        return DataDaThu;
    }

    public ResponseObjectNhieuHDNB setDataDaThu(List<PeriodNhanVienThu> dataDaThu) {
        DataDaThu = dataDaThu;
        return this;
    }

    public List<LOGNBDTO> getListError() {
        return ListError;
    }

    public ResponseObjectNhieuHDNB setListError(List<LOGNBDTO> listError) {
        ListError = listError;
        return this;
    }
}
