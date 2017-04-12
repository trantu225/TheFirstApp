package tiwaco.thefirstapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.CustomAdapter.CustomListAdapter;
import tiwaco.thefirstapp.CustomAdapter.CustomListDuongAdapter;
import tiwaco.thefirstapp.DAO.DuongDAO;
import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.DuongDTO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKH_Fragment extends Fragment {

    ListView listviewKH ;
    List<KhachHangDTO> liskh ;
    KhachHangDAO khachhangDAO;
    RecyclerView recyclerView;
    CustomListDuongAdapter adapter;
    List<DuongDTO> listduong;
    DuongDAO duongDAO;
    public ListKH_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listkh_fragment, container, false);
        ListView listviewKH = (ListView) v.findViewById(R.id.lv_khachhang);
        duongDAO = new DuongDAO(getContext());
        listduong = duongDAO.getAllDuongChuaGhi();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_listduong);
        adapter = new CustomListDuongAdapter(getContext(),listduong,listviewKH);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        return v;
    }


}
