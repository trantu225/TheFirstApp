package tiwaco.thefirstapp.DTO;

/**
 * Created by TUTRAN on 25/10/2018.
 */

public class PeriodDTO {
    public PeriodDTO() {

    }

    String BillNo;
    Integer TotalMoney;
    String PeriodNum;

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public Integer getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getPeriodNum() {
        return PeriodNum;
    }

    public void setPeriodNum(String periodNum) {
        PeriodNum = periodNum;
    }


}
