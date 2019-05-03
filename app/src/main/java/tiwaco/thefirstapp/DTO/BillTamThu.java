package tiwaco.thefirstapp.DTO;

/**
 * Created by Admin on 8/4/2019.
 */

public class BillTamThu {
    String custNo;
    Integer totalMoney;
    String period;
    String transactionID;
    String UserThu;
    String timeThu;

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserThu() {
        return UserThu;
    }

    public void setUserThu(String userThu) {
        UserThu = userThu;
    }

    public String getTimeThu() {
        return timeThu;
    }

    public void setTimeThu(String timeThu) {
        this.timeThu = timeThu;
    }
}
