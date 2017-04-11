package tiwaco.thefirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tiwaco.thefirstapp.DAO.KhachHangDAO;
import tiwaco.thefirstapp.DTO.KhachHangDTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKH_Fragment extends Fragment {

    ListView listviewKH ;
    List<KhachHangDTO> liskh ;
    KhachHangDAO khachhangDAO;
    public ListKH_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listkh_fragment, container, false);
        listviewKH  = (ListView)v.findViewById(R.id.lv_khachhang) ;
        liskh = new ArrayList<KhachHangDTO>();
        khachhangDAO = new KhachHangDAO(getContext());
        liskh = khachhangDAO.getAllKHTheoDuong("01");
        listviewKH.setAdapter(new CustomListAdapter(getContext(),liskh));
        return v;
    }

}
