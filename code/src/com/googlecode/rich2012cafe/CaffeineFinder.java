package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.googlecode.rich2012cafe.activities.JonText;
import com.googlecode.rich2012cafe.activities.MapView;
import com.googlecode.rich2012cafe.activities.UserInterface;
import com.googlecode.rich2012cafe.model.AppDataStore;

/**
 * In running and developing application:
 * ======================================
 * 1. Need to increase Eclipse minimum/maximum memory requirements by altering eclipse.ini file (Linux - located in /usr/lib/eclipse/)
 *    and change appropriate lines to this:
 *              -Xms512m
 *              -Xmx1024m
 * 2. Ensure AVD and Project are using Google APIs platform.
 * 3. Add -partition-size 1024 to run configurations for application.
 * 4. Add custom debug keystore to Android build configuration. 
 * 
 * WORK
 * ==== 
 * TODO Sort out source queries and adjust as others need (See DataStoreInterface).
 * TODO Sort out settings - what settings to have and how going to be used.
 * TODO Move Google calendar stuff from JonText To AppDataStore (WHEN NECESSARY - JON WILL DO THIS)
 * TODO Sort out leaderboard database.
 * 
 * TODO Sort out storage of data and updating.
 * 
 * TODO Research caffeine milligrammes for CaffeineProducts, caffeine decay calculations, optimal level etc using minimum of data
 *      users have to enter.
 *      
 * TODO Sort out scoring system and communication with leaderboards.
 * TODO Consume caffeine functionality
 * TODO View leaderboard functionality (e.g. my position, viewing all, friends (maybe))
 * TODO Notifications / Alerts
 * 
 * TODO Web Interface for Leaderboard (MAYBE)
 * TODO Settings to select which calendars to read from.
 * 
 * CURRENT TASKS
 * =============
 * TODO Alter settings to remove PreferenceFragments so all in one view and compatiblity (ASSIGNED TO: T)
 * 		- Check all settings still work when makes changes and affect db queries appropiatly.
 * 		- Remove redundant XML files.
 * TODO Overlay map of locations (ASSIGNED TO: Craig)
 * TODO Sort out Actionbar compatibility (ASSSIGNED TO: Mike)
 * TODO Research Sections of Document (ASSIGNED TO: Costello)
 * TODO Project Tools, Techniques, Future Work of Document / Help Jon (ASSIGNED TO: Sami)
 * TODO Sort out database / backend things (Working on GAE Database at moment) (Assigned to: Jon)
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com) 
 */
public class CaffeineFinder extends Activity implements OnClickListener{

	private AppDataStore appDataStore;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        appDataStore = AppDataStore.getInstance(this);
        
        this.getViewMapButton().setOnClickListener(this);
        this.getSparqlButton().setOnClickListener(this);
        this.getuiButton().setOnClickListener(this);
                
        //Set default preferences
        setDefaultPreferences();
    }
	
    private void setDefaultPreferences(){
        PreferenceManager.setDefaultValues(this, R.xml.user_settings, true);
        PreferenceManager.setDefaultValues(this, R.xml.source_settings, true);
        PreferenceManager.setDefaultValues(this, R.xml.product_settings, true);
    }
    
    private Button getViewMapButton() {
        return (Button)findViewById(R.id.viewMap);
    }

    private Button getuiButton() {
        return (Button)findViewById(R.id.uiButton);
    }
    
    private Button getSparqlButton() {
        return (Button)findViewById(R.id.sparqlButton);
    }
        
    public void onClick(View view) {

        if (view.getId() == R.id.viewMap) {
        	Intent intent = new Intent(view.getContext(), MapView.class);
        	this.startActivity(intent);
        }  else if (view.getId() == R.id.sparqlButton) {
        	Intent intent = new Intent(view.getContext(), JonText.class);
        	this.startActivity(intent);
        }
        if(view.getId() == R.id.uiButton){
        	Intent intent = new Intent(view.getContext(), UserInterface.class);
        	this.startActivity(intent);
        }
    }


}