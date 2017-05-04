package tiwaco.thefirstapp.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import tiwaco.thefirstapp.Bien;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TUTRAN on 04/05/2017.
 */

public class SPData  {
    Context context;
    SharedPreferences pre;
    public SPData(Context con) {
        this.context = con;
        pre= con.getSharedPreferences(Bien.SPDATA,con.MODE_PRIVATE);
    }
    public String getDataDuongDangGhiTrongSP(){

        String maduong=pre.getString(Bien.SPMADUONG, "");
        return maduong;
    }
    public void luuDataDuongDangGhiTrongSP(String maduong){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putString(Bien.SPMADUONG, maduong);
        editor.apply();
    }

    public void luuDataFlagGhivaBackUpTrongSP(int bienghi,int flagall, int flagdaghi, int flagchuaghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPFLAGGHI, bienghi);
        editor.putInt(Bien.SPBKALL, flagall);
        editor.putInt(Bien.SPBKCG, flagchuaghi);
        editor.putInt(Bien.SPBKDG, flagdaghi);
        editor.commit();
    }

    public void luuDataFlagGhiTrongSP1(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPFLAGGHI, bienghi);
        editor.commit();
    }
    public int getDataFlagGhiTrongSP(){


        int flagghi=pre.getInt(Bien.SPFLAGGHI, 1);
        return flagghi;
    }
    public void luuDataFlagBKAllTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKALL, bienghi);
        editor.commit();
    }

    public void luuDataFlagBKChuaGhiTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKCG, bienghi);
        editor.commit();
    }

    public void luuDataFlagBKDaghiTrongSP(int bienghi){

        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt(Bien.SPBKDG, bienghi);
        editor.commit();
    }

    public int getDataBKALLTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKALL, 0);
        return flagghi;
    }

    public int getDataBKCGTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKCG, 0);
        return flagghi;
    }

    public int getDataBKDGTrongSP(){


        int flagghi=pre.getInt(Bien.SPBKDG, 0);
        return flagghi;
    }
}