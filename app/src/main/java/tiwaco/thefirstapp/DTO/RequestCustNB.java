package tiwaco.thefirstapp.DTO;

/**
 * Created by Admin on 15/3/2019.
 */

public class RequestCustNB {

    String custNo;
    String userName;
    String passWord;
    String requestTime;
    String kyhd;

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

    public String getKyhd() {
        return kyhd;
    }

    public void setKyhd(String kyhd) {
        this.kyhd = kyhd;
    }


}
