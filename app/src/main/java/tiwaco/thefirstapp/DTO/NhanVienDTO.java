package tiwaco.thefirstapp.DTO;

/**
 * Created by TUTRAN on 08/04/2017.
 */

public class NhanVienDTO {
    private String IDNV;
    private String PassNV;

    public NhanVienDTO(String IDNV, String passNV) {
        this.IDNV = IDNV;
        PassNV = passNV;
    }

    public NhanVienDTO() {
    }

    public String getIDNV() {
        return IDNV;
    }

    public void setIDNV(String IDNV) {
        this.IDNV = IDNV;
    }

    public String getPassNV() {
        return PassNV;
    }

    public void setPassNV(String passNV) {
        PassNV = passNV;
    }
}
