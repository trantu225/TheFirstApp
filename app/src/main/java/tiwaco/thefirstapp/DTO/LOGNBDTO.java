package tiwaco.thefirstapp.DTO;

import java.util.Date;

/**
 * Created by Admin on 3/5/2019.
 */

public class LOGNBDTO {
    String LOGID;
    String USERNAME;
    String MAKH;
    Date date;
    Date REQUESTTIME;
    String IPADDRESS;
    String ACTION;
    int RETURNCODE;
    String RETURNCODEDESC;

    public String getLOGID() {
        return LOGID;
    }

    public void setLOGID(String LOGID) {
        this.LOGID = LOGID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getMAKH() {
        return MAKH;
    }

    public void setMAKH(String MAKH) {
        this.MAKH = MAKH;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getREQUESTTIME() {
        return REQUESTTIME;
    }

    public void setREQUESTTIME(Date REQUESTTIME) {
        this.REQUESTTIME = REQUESTTIME;
    }

    public String getIPADDRESS() {
        return IPADDRESS;
    }

    public void setIPADDRESS(String IPADDRESS) {
        this.IPADDRESS = IPADDRESS;
    }

    public String getACTION() {
        return ACTION;
    }

    public void setACTION(String ACTION) {
        this.ACTION = ACTION;
    }

    public int getRETURNCODE() {
        return RETURNCODE;
    }

    public void setRETURNCODE(int RETURNCODE) {
        this.RETURNCODE = RETURNCODE;
    }

    public String getRETURNCODEDESC() {
        return RETURNCODEDESC;
    }

    public void setRETURNCODEDESC(String RETURNCODEDESC) {
        this.RETURNCODEDESC = RETURNCODEDESC;
    }
}
