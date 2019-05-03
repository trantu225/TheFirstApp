package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 8/4/2019.
 */

public class RequestTamThu {

    String userName;
    String passWord;
    String requestTime;
    String paymentChannel;
    List<BillTamThu> listTamThu;

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

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public List<BillTamThu> getListTamThu() {
        return listTamThu;
    }

    public void setListTamThu(List<BillTamThu> listTamThu) {
        this.listTamThu = listTamThu;
    }
}
