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
    public String CustNo;


    public String ResponseTime;

    public String CustName;

    public String BillAddress;


    public String Note;

    public int SumOfTotalMoney;


    public List<PeriodNhanVienThu> PeriodNums;


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

    public String getCustNo() {
        return CustNo;
    }

    public ResponePayBillAllNB setCustNo(String custNo) {
        CustNo = custNo;
        return this;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public ResponePayBillAllNB setResponseTime(String responseTime) {
        ResponseTime = responseTime;
        return this;
    }

    public String getCustName() {
        return CustName;
    }

    public ResponePayBillAllNB setCustName(String custName) {
        CustName = custName;
        return this;
    }

    public String getBillAddress() {
        return BillAddress;
    }

    public ResponePayBillAllNB setBillAddress(String billAddress) {
        BillAddress = billAddress;
        return this;
    }

    public String getNote() {
        return Note;
    }

    public ResponePayBillAllNB setNote(String note) {
        Note = note;
        return this;
    }

    public int getSumOfTotalMoney() {
        return SumOfTotalMoney;
    }

    public ResponePayBillAllNB setSumOfTotalMoney(int sumOfTotalMoney) {
        SumOfTotalMoney = sumOfTotalMoney;
        return this;
    }

    public List<PeriodNhanVienThu> getPeriodNums() {
        return PeriodNums;
    }

    public ResponePayBillAllNB setPeriodNums(List<PeriodNhanVienThu> periodNums) {
        PeriodNums = periodNums;
        return this;
    }
}
