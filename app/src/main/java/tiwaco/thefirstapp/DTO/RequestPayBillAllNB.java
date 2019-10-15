package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 12/7/2019.
 */

public class RequestPayBillAllNB {
    private String custNo;

    private String userName;

    private String passWord;


    private String requestTime;


    private String kyhd;


    private String transactionID;


    private String paymentChannel;


    private int SumOfTotalMoney;


    private List<PeriodDTO> PeriodNums;

    public String getCustNo() {
        return custNo;
    }

    public RequestPayBillAllNB setCustNo(String custNo) {
        this.custNo = custNo;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RequestPayBillAllNB setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public RequestPayBillAllNB setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public RequestPayBillAllNB setRequestTime(String requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public String getKyhd() {
        return kyhd;
    }

    public RequestPayBillAllNB setKyhd(String kyhd) {
        this.kyhd = kyhd;
        return this;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public RequestPayBillAllNB setTransactionID(String transactionID) {
        this.transactionID = transactionID;
        return this;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public RequestPayBillAllNB setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
        return this;
    }

    public int getSumOfTotalMoney() {
        return SumOfTotalMoney;
    }

    public RequestPayBillAllNB setSumOfTotalMoney(int sumOfTotalMoney) {
        SumOfTotalMoney = sumOfTotalMoney;
        return this;
    }

    public List<PeriodDTO> getPeriodNums() {
        return PeriodNums;
    }

    public RequestPayBillAllNB setPeriodNums(List<PeriodDTO> periodNums) {
        PeriodNums = periodNums;
        return this;
    }
}
