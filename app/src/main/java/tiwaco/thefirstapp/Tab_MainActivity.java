package tiwaco.thefirstapp;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import tiwaco.thefirstapp.CustomAdapter.PagerAdapter;

public class Tab_MainActivity extends FragmentActivity  {
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

      //  mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
      //  mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
      //  mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Tab 1"),MainActivity.class, null);
       // mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Tab 2"),ListKH_Fragment.class, null);


        pager = (ViewPager) findViewById(R.id.view_pager);
      //  tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
       // tabLayout.setupWithViewPager(pager);
      //  pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      //  tabLayout.setTabsFromPagerAdapter(adapter);
    }


}
