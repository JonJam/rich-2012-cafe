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