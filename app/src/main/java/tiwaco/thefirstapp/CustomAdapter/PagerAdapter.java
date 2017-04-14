package tiwaco.thefirstapp.CustomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tiwaco.thefirstapp.ListKH_Fragment;
import tiwaco.thefirstapp.MainActivity;
import tiwaco.thefirstapp.R;

/**
 * Created by TUTRAN on 12/04/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new ListKH_Fragment();
                break;
            case 1:
                frag=new ListKH_Fragment();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title= String.valueOf(R.string.tab_dsKH);
                break;
            case 1:
                title= String.valueOf(R.string.tab_dsKH);
                break;


        }

        return title;
    }
}
