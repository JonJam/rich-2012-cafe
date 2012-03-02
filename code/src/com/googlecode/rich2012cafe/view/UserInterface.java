package com.googlecode.rich2012cafe.view;

import com.googlecode.rich2012cafe.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class UserInterface extends TabActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        TabHost tabHost = this.getTabHost();
        tabHost.setup();
        
        TabHost.TabSpec mapSpec = tabHost.newTabSpec("Map");
        mapSpec.setContent(new Intent(this, GoogleMap.class));
        mapSpec.setIndicator("Map");
        
        TabHost.TabSpec settingsSpec = tabHost.newTabSpec("Settings");
        settingsSpec.setContent(new Intent(this, Settings.class));
        settingsSpec.setIndicator("Settings");
        
        tabHost.addTab(mapSpec);
        tabHost.addTab(settingsSpec);
    }


}
