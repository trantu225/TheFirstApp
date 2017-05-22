package tiwaco.thefirstapp.DTO;

import java.util.Date;

/**
 * Created by TUTRAN on 22/05/2017.
 */

public class LichSuDTO {
    private int MaLS;
    private String NoiDungLS;
    private String MaLenh;
    private String ThoiGianLS;
    public LichSuDTO()
    {

    }
    public  LichSuDTO(int Ma, String nd, String malenh, String time){
        this.MaLS = Ma;
        this.NoiDungLS = nd;
        this.MaLenh = malenh;
        this.ThoiGianLS = time;
    }

    public int getMaLS() {
        return MaLS;
    }

    public void setMaLS(int maLS) {
        MaLS = maLS;
    }

    public String getNoiDungLS() {
        return NoiDungLS;
    }

    public void setNoiDungLS(String noiDungLS) {
        NoiDungLS = noiDungLS;
    }

    public String getMaLenh() {
        return MaLenh;
    }

    public void setMaLenh(String maLenh) {
        MaLenh = maLenh;
    }

    public String getThoiGianLS() {
        return ThoiGianLS;
    }

    public void setThoiGianLS(String thoiGianLS) {
        ThoiGianLS = thoiGianLS;
    }
}
