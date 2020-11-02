package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 28/10/2019.
 */

public class RequestPayThuHo {

    private List<String> ListKH;
    private String userName;

    private String passWord;


    private String requestTime;


    private List<PeriodDTO> PeriodNums;

    public List<String> getListKH() {
        return ListKH;
    }

    public RequestPayThuHo setListKH(List<String> listKH) {
        ListKH = listKH;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RequestPayThuHo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public RequestPayThuHo setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public RequestPayThuHo setRequestTime(String requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public List<PeriodDTO> getPeriodNums() {
        return PeriodNums;
    }

    public RequestPayThuHo setPeriodNums(List<PeriodDTO> periodNums) {
        PeriodNums = periodNums;
        return this;
    }
}
