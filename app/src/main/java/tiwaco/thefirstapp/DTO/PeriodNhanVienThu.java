package tiwaco.thefirstapp.DTO;

/**
 * Created by Admin on 12/7/2019.
 */

public class PeriodNhanVienThu {
    private String BillNo;

    private String TotalMoney;

    private String PeriodNum;

    private String TransactionID;

    private String ThoiGianThu;

    private String NhanVienThu;

    public String getBillNo() {
        return BillNo;
    }

    public PeriodNhanVienThu setBillNo(String billNo) {
        BillNo = billNo;
        return this;
    }

    public String getTotalMoney() {
        return TotalMoney;
    }

    public PeriodNhanVienThu setTotalMoney(String totalMoney) {
        TotalMoney = totalMoney;
        return this;
    }

    public String getPeriodNum() {
        return PeriodNum;
    }

    public PeriodNhanVienThu setPeriodNum(String periodNum) {
        PeriodNum = periodNum;
        return this;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public PeriodNhanVienThu setTransactionID(String transactionID) {
        TransactionID = transactionID;
        return this;
    }

    public String getThoiGianThu() {
        return ThoiGianThu;
    }

    public PeriodNhanVienThu setThoiGianThu(String thoiGianThu) {
        ThoiGianThu = thoiGianThu;
        return this;
    }

    public String getNhanVienThu() {
        return NhanVienThu;
    }

    public PeriodNhanVienThu setNhanVienThu(String nhanVienThu) {
        NhanVienThu = nhanVienThu;
        return this;
    }
}
