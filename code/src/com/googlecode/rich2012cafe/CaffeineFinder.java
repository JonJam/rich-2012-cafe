package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.googlecode.rich2012cafe.controller.JonText;
import com.googlecode.rich2012cafe.view.GoogleMap;
import com.googlecode.rich2012cafe.view.UserInterface;

/**
 * N.B. In running and developing application:
 * 1. Need to increase Eclipse minimum/maximum memory requirements by altering eclipse.ini file (Linux - located in /usr/lib/eclipse/)
 *    and change appropriate lines to this:
 *              -Xms512m
 *              -Xmx1024m
 * 2. Ensure AVD and Project are using Google APIs platform.
 * 3. Add -partition-size 1024 to run configurations for application.
 * 4. Add custom debug keystore to Android build configuration. 
 * 
 * TODO Add in check for null nodes in SPARQLQuerier functions like for lat and long so more
 *      robust.
 * 
 * TODO Implement queries and any others people need (See DataStoreInterface):
 * 
 	*   Get sources given lat and long (NEED TO DECIDE WHAT GIVING MODEL EITHER OBJECT OR COORDINATES)
 * 
 * TODO Saving settings (XML or table) and integrate into AppDataStore if appropiate. 
	 * 		Whether student or staff
	 * 		Product type (Setting what types of caffeine what to display All, or combination of coffee, tea, red bull, etc).
	 * 		Display Vending Machines
	 * 		Favourite products
	 * 		Favourite places
	 * 
 * 	Resources: 
 * 			http://developer.android.com/guide/topics/data/data-storage.html
 * 			http://developer.android.com/reference/android/preference/PreferenceActivity.html
 * 
 * TODO Alter queries for settings
 *  		Student or Staff affects:
 *  			Only get Products for type set.
 *  		Favourite Products affects:
 *  			Display favourites as seperate list at top of any product list
 *  		Favourite Locations affects:
 *  			Always display favourite locations on map no matter what distance.
 *  		Product Types
 *  			Only show places/products that server/are types.
 *  		Display Vending Machine affects:
 *  			Get Locations
 *  			
* 
 * TODO Remove deprecated actions/classes in: 
 * 		Preferences (this.addPreferencesFromResource(com.googlecode.rich2012cafe.R.xml.preferences);)
 * 		UserInterface (TabbedActivity)
 * 
 * TODO Add loading overlay (blocking off functionality) whilst performing database check.
 * 
 * TODO Find way of doing database check and installation of data when install app. (Takes 1 min to download all data on my machine)
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com) 
 */
public class CaffeineFinder extends Activity implements OnClickListener{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.getViewMapButton().setOnClickListener(this);
        this.getSparqlButton().setOnClickListener(this);
        this.getuiButton().setOnClickListener(this);
        

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
        	Intent intent = new Intent(view.getContext(), GoogleMap.class);
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