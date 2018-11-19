package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by TUTRAN on 22/10/2018.
 */

public class RequestGetBillInfoDTO {


    /*///
    {

        "custNo": "01000209",

            "userName": "tranvanthu",

            "passWord": "246",

            "transactionID": "",

            "requestTime": "20181024105000",

            "SumOfTotalMoney": 200800,

            "paymentChannel": "",

            "PeriodNums": [

        {

            "BillNo": "H723383897",

                "TotalMoney": 140000,

                "PeriodNum": "201808"

        },



        {

            "BillNo": "J723469020",

                "TotalMoney": 60800,

                "PeriodNum": "201810"

        }


  ]

    }*/
    String custNo;
    String userName;
    String passWord;
    String requestTime;

    String kyhd;
    String transactionID;
    Integer SumOfTotalMoney;
    String paymentChannel;
    List<PeriodDTO> PeriodNums;

    public RequestGetBillInfoDTO() {

    }

    public String getKyhd() {
        return kyhd;
    }

    public void setKyhd(String kyhd) {
        this.kyhd = kyhd;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getSumOfTotalMoney() {
        return SumOfTotalMoney;
    }

    public void setSumOfTotalMoney(Integer sumOfTotalMoney) {
        SumOfTotalMoney = sumOfTotalMoney;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public List<PeriodDTO> getPeriodNums() {
        return PeriodNums;
    }

    public void setPeriodNums(List<PeriodDTO> periodNums) {
        PeriodNums = periodNums;
    }

}
