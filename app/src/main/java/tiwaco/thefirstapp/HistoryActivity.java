package tiwaco.thefirstapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListHistoryAdapter;
import tiwaco.thefirstapp.DAO.LichSuDAO;
import tiwaco.thefirstapp.DTO.LichSuDTO;

public class HistoryActivity extends AppCompatActivity {
    List<LichSuDTO> listhistory ;
    LichSuDAO lishsudao;
    Context con;
    CustomListHistoryAdapter historyadapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        con  = getApplicationContext();
        getSupportActionBar().setTitle("Lược sử");
        listview  = (ListView) findViewById(R.id.lv_history);
        lishsudao = new LichSuDAO(con);
        HienThiLuocSu(listview);

    }
    private void HienThiLuocSu(ListView listView){
        listhistory = new ArrayList<>();
        listhistory = lishsudao.getAllLichSu();
        historyadapter = new CustomListHistoryAdapter(con,listhistory);
        listview.setAdapter(historyadapter);

    }
}
