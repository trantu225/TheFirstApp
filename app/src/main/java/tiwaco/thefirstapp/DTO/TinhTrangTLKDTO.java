package tiwaco.thefirstapp.DTO;

/**
 * Created by TUTRAN on 07/06/2017.
 */

public class TinhTrangTLKDTO {

    private String matt;
    private String tentt;
    private String thututt;
    public  TinhTrangTLKDTO(){

    }
    public  TinhTrangTLKDTO(String ma, String ten, String tt){
        matt = ma;
        tentt = ten;
        thututt = tt;

    }
    public String getThututt() {
        return thututt;
    }

    public void setThututt(String thututt) {
        this.thututt = thututt;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getTentt() {
        return tentt;
    }

    public void setTentt(String tentt) {
        this.tentt = tentt;
    }
}
