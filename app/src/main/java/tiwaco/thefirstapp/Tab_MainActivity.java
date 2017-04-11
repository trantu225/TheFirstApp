package tiwaco.thefirstapp;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;

import tiwaco.thefirstapp.DTO.KhachHangDTO;



public class Tab_MainActivity extends FragmentActivity  {
    FragmentTabHost mTabHost ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Tab 1"),MainActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Tab 2"),ListKH_Fragment.class, null);
    }


}
