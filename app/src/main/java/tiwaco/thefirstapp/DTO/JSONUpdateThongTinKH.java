package tiwaco.thefirstapp.DTO;

/**
 * Created by TUTRAN on 09/11/2018.
 */

public class JSONUpdateThongTinKH {

    String custNo;
    String phoneNumber;
    String phoneNumber2;
    String paymentNote;
    String userName;
    String passWord;
    String requestTime;
    String CMND;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
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

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public JSONUpdateThongTinKH setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
        return this;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    public String getCMND() {
        return CMND;
    }

    public JSONUpdateThongTinKH setCMND(String CMND) {
        this.CMND = CMND;
        return this;
    }
}
