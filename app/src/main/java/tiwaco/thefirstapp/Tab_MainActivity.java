package tiwaco.thefirstapp;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class Tab_MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Main");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Login");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this,MainActivity.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this,LoginActivity.class));


        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

    }
}
