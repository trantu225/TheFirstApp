package tiwaco.thefirstapp.DAO;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DTO.NhanVienDTO;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 08/04/2017.
 */

public class NhanVienDAO {
    public NhanVienDAO() {
    }
    public boolean kiemtraDangNhap(String id, String pass,List<NhanVienDTO> listnv,Context con){
        boolean kt =false;
        for(int i =0;i <listnv.size();i++){
            Log.e("DangNhap: id",id);
            Log.e("DangNhap: pass",pass);
            Log.e("DangNhap: listid",listnv.get(i).getIDNV());
            Log.e("DangNhap: listpass",listnv.get(i).getPassNV());

            if(id.equalsIgnoreCase(listnv.get(i).getIDNV())  && pass.equalsIgnoreCase(listnv.get(i).getPassNV())){
                kt =true;
                Log.e("DangNhap ketqua: ", String.valueOf(kt));
            }

        }
        return kt;
    }
    public static List<NhanVienDTO> TaoDSNhanVien()
    {
        List<NhanVienDTO> listnhanvien = new ArrayList<NhanVienDTO>();
        NhanVienDTO nvghi0 = new NhanVienDTO();
        nvghi0.setIDNV("Tung");
        nvghi0.setPassNV("123");
        listnhanvien.add(nvghi0);

        NhanVienDTO nvghi1 = new NhanVienDTO();
        nvghi1.setIDNV("Chap");
        nvghi1.setPassNV("123");
        listnhanvien.add(nvghi1);

        NhanVienDTO nvghi2 = new NhanVienDTO();
        nvghi2.setIDNV("Luu");
        nvghi2.setPassNV("123");
        listnhanvien.add(nvghi2);

        NhanVienDTO nvghi3 = new NhanVienDTO();
        nvghi3.setIDNV("Quy");
        nvghi3.setPassNV("123");
        listnhanvien.add(nvghi3);

        NhanVienDTO nvghi4 = new NhanVienDTO();
        nvghi4.setIDNV("Thu");
        nvghi4.setPassNV("123");
        listnhanvien.add(nvghi4);

        NhanVienDTO nvghi5 = new NhanVienDTO();
        nvghi5.setIDNV("Hong");
        nvghi5.setPassNV("123");
        listnhanvien.add(nvghi5);

        NhanVienDTO nvghi6 = new NhanVienDTO();
        nvghi6.setIDNV("Vu");
        nvghi6.setPassNV("123");
        listnhanvien.add(nvghi6);

        NhanVienDTO nvghi7 = new NhanVienDTO();
        nvghi7.setIDNV("Nam");
        nvghi7.setPassNV("123");
        listnhanvien.add(nvghi7);

        NhanVienDTO nvghi8 = new NhanVienDTO();
        nvghi8.setIDNV("San");
        nvghi8.setPassNV("123");
        listnhanvien.add(nvghi8);

        NhanVienDTO nvghi9 = new NhanVienDTO();
        nvghi9.setIDNV("Viet");
        nvghi9.setPassNV("123");
        listnhanvien.add(nvghi9);

        NhanVienDTO nvghi10 = new NhanVienDTO();
        nvghi10.setIDNV("Vung");
        nvghi10.setPassNV("123");
        listnhanvien.add(nvghi10);

        NhanVienDTO nvghi11 = new NhanVienDTO();
        nvghi11.setIDNV("Tu");
        nvghi11.setPassNV("123");
        listnhanvien.add(nvghi11);

        NhanVienDTO nvghi12 = new NhanVienDTO();
        nvghi12.setIDNV("Vinh");
        nvghi12.setPassNV("123");
        listnhanvien.add(nvghi12);

        NhanVienDTO nvghi13 = new NhanVienDTO();
        nvghi13.setIDNV("Chau");
        nvghi13.setPassNV("123");
        listnhanvien.add(nvghi13);

        NhanVienDTO nvghi14 = new NhanVienDTO();
        nvghi14.setIDNV("Hung");
        nvghi14.setPassNV("123");
        listnhanvien.add(nvghi14);

        NhanVienDTO nvghi15 = new NhanVienDTO();
        nvghi15.setIDNV("HoaiLinh");
        nvghi15.setPassNV("123");
        listnhanvien.add(nvghi15);
        return listnhanvien;
    }


}
