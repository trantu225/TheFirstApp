package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 15/3/2019.
 */

public class RequestCustNB {

    String custNo;
    String userName;
    String passWord;
    String requestTime;
    String kyhd;
    List<PeriodDTO> listhd;


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

    public List<PeriodDTO> getListhd() {
        return listhd;
    }

    public RequestCustNB setListhd(List<PeriodDTO> listhd) {
        this.listhd = listhd;
        return this;
    }
}
