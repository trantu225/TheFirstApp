package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 12/7/2019.
 */

public class CustBillNB {
    public String CustNo;

    public int ResponseCode;

    public String ResponseTime;

    public String CustName;

    public String BillAddress;


    public String Note;

    public int SumOfTotalMoney;


    public List<PeriodNhanVienThu> PeriodNums;

    public String getCustNo() {
        return CustNo;
    }

    public CustBillNB setCustNo(String custNo) {
        CustNo = custNo;
        return this;
    }

    public int getResponseCode() {
        return ResponseCode;
    }

    public CustBillNB setResponseCode(int responseCode) {
        ResponseCode = responseCode;
        return this;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public CustBillNB setResponseTime(String responseTime) {
        ResponseTime = responseTime;
        return this;
    }

    public String getCustName() {
        return CustName;
    }

    public CustBillNB setCustName(String custName) {
        CustName = custName;
        return this;
    }

    public String getBillAddress() {
        return BillAddress;
    }

    public CustBillNB setBillAddress(String billAddress) {
        BillAddress = billAddress;
        return this;
    }

    public String getNote() {
        return Note;
    }

    public CustBillNB setNote(String note) {
        Note = note;
        return this;
    }

    public int getSumOfTotalMoney() {
        return SumOfTotalMoney;
    }

    public CustBillNB setSumOfTotalMoney(int sumOfTotalMoney) {
        SumOfTotalMoney = sumOfTotalMoney;
        return this;
    }

    public List<PeriodNhanVienThu> getPeriodNums() {
        return PeriodNums;
    }

    public CustBillNB setPeriodNums(List<PeriodNhanVienThu> periodNums) {
        PeriodNums = periodNums;
        return this;
    }
}
