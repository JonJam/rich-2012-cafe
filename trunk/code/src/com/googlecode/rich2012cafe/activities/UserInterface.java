package com.googlecode.rich2012cafe.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controllers.UserInterfaceController;
import com.googlecode.rich2012cafe.view.ActionTabListener;
import com.googlecode.rich2012cafe.view.MapFragment;

public class UserInterface extends Activity {

	private UserInterfaceController controller;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.ui_main);
        setContentView(R.layout.ui_main_actionbar);
        controller = new UserInterfaceController();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
//        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.menulist,
//                android.R.layout.simple_spinner_dropdown_item);
//        
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
            .setText("Map")
            .setTabListener(new ActionTabListener<MapFragment>(
                    this, "content", MapFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                .setText("Locations")
                .setTabListener(new ActionTabListener<ListSources>(
                        this, "content", ListSources.class));
        actionBar.addTab(tab);
        
        
 /*       ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
        	  String[] strings = getResources().getStringArray(R.array.menulist);

        	  @Override
        	  public boolean onNavigationItemSelected(int position, long itemId) {
        		  Intent intent;
                  switch (position) {
		        	  case 1:
		                  intent = new Intent(this, MapView.class);
		                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                  startActivity(intent);
		                  return true;
		              case 2:
		                  intent = new Intent(this, ListSources.class);
		                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                  startActivity(intent);
		                  return true;
	        	  }
        	  }
        	};
        	
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);*/
        
    /*    TabHost tabHost = this.getTabHost();
        tabHost.setup();
        
        TabHost.TabSpec mapSpec = tabHost.newTabSpec("Map");
        mapSpec.setContent(new Intent(this, MapView.class));
        mapSpec.setIndicator("Map");
        
        TabHost.TabSpec buildSpec = tabHost.newTabSpec("Locations");
        buildSpec.setContent(new Intent(this, ListSources.class));
        buildSpec.setIndicator("Locations");
        
        TabHost.TabSpec settingsSpec = tabHost.newTabSpec("Settings");
        settingsSpec.setContent(new Intent(this, Settings.class));
        settingsSpec.setIndicator("Settings");
        
        tabHost.addTab(mapSpec);
        tabHost.addTab(buildSpec);
        tabHost.addTab(settingsSpec);*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          return controller.optionsActions(item, this);
    }
    
   


}
