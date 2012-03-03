package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.googlecode.rich2012cafe.controller.AppController;
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
 * TODO Add lat and log columns to get caffeine sources if need to. or remove from sparql query
 * TODO Implement queries and any others people need (See DataStoreInterface):
 * 
 * 		Get sources given lat and long
 * 		Get products given price range (may need to change how price is stored)
 * TODO Saving settings (XML or table) http://developer.android.com/guide/topics/data/data-storage.html
	 * 		Whether student or staff
	 * 		Favourite products
	 * 		Product type (Setting what types of caffeine what to display All, or combination of coffee, tea, red bull, etc.
	 * 		Inc vending machines in type
 * TODO Move settings to model package and integrate into AppDataStore 
 * 
 * TODO Alter queries for settings
 *  		Student or Staff affects:
 *  			Get Products for a location
 *  			Get Locations - DO WE WANT ONLY DISPLAY LOCATIONS GIVEN TYPE?
 *  		Favourite Products affects:
 *  			Get Locations - DO WE WANT ONLY DISPLAY LOCATIONS WHERE SERVE FAVOURITE PRODUCT?
 *  			Get Products for a location - ONLY DISPLAY FAVOURITES AND NOTHING ELSE?
 *  		Product Types
 *  			Get Locations
 *  			Get Products for a location
 *  		Display Vending Machine affects:
 *  			Get Locations
 *  			
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineFinder extends Activity implements OnClickListener{

	private AppController controller;
    private TextView tv;
	
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