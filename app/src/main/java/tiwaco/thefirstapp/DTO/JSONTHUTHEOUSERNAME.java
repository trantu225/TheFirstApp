package tiwaco.thefirstapp.DTO;

import java.util.List;

/**
 * Created by Admin on 12/9/2019.
 */

public class JSONTHUTHEOUSERNAME {
    String userName;
    String passWord;
    List<ObjectThu> ListThanhToan;

    public String getUserName() {
        return userName;
    }

    public JSONTHUTHEOUSERNAME setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public JSONTHUTHEOUSERNAME setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public List<ObjectThu> getListThanhToan() {
        return ListThanhToan;
    }

    public void setListThanhToan(List<ObjectThu> listThanhToan) {
        ListThanhToan = listThanhToan;
    }
}
