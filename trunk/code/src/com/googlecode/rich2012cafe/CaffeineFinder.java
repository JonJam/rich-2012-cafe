package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.googlecode.rich2012cafe.activities.GoogleMap;
import com.googlecode.rich2012cafe.activities.JonText;
import com.googlecode.rich2012cafe.activities.UserInterface;

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
 * TODO Implement queries and adjust as others need (See DataStoreInterface):
 * 
 	*   Get sources given lat and long (NEED TO DECIDE WHAT GIVING MODEL EITHER OBJECT OR COORDINATES)
 	*   May need to change getCaffeineProductsinPriceRange
 *
 * TODO Adjust SPARQLQuerier to sort out blanks in field for source: (SAMI)
 * 			Edit query or try strip out name from ID where this occurs.
 * 
 * TODO Add column into DB for Source: In campus / on campus. (SAMI / JON)
 * 		boolean value (0 / 1) 
 * 		Setting include on and off campus values.
 * 
 * TODO TEST ALL QUERIES WITH SETTINGS
 * 
 * How Settings affect View / Queries.
 * ===================================
 * 
 *  		Student or Staff: (QUERIES COMPLETE)
 *  			Functions in AppDataStore effected:
 *  				getCaffeineProductsForCaffeineSource (Get Products for user type either student or staff or all)
 *    
 *  		Display Vending Machine: (QUERIES COMPLETE)
 *  			Functions in AppDataStore effected:
 *  				getAllCaffeineSources (Get point of sale and if applicable vending machine sources)
 *  				getCaffeineSourcesForProductName (Get point of sale and if applicable vending machine sources)
 *  	
 *  		Product Types: (QUERIES COMPLETE)
 *  			Only show products that are types.
 * 
 *  			getAllCaffeineProductNames (Only show products that are of wanted type)
 *  				getCaffeineSourcesForProductNames (Inherits blocking from previous)
 *  			getCaffeineProductTypes (Only show product types that are wanted)
 *  				getCaffeineProductsForProductType (Inherits blocking from previous)
 *  			getCaffeineProductsForCaffeineSource (Only show products that are of wanted type)
 *  			getCaffeineProductsInPriceRange (Only show products that are of wanted type)
 * 
 * TODO Add loading overlay (blocking off functionality) whilst performing database check.
 * 
 * TODO Find way of doing database check and installation of data when install app. (Takes 1 min to download all data on my machine)
 * 
 * TODO MVC layout
 * 
 * TODO Handle all onCreate, onResume, onPause, onDestory etc.
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
        
        //Set default preferences
        setPreferences();
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
    
    private void setPreferences(){
        PreferenceManager.setDefaultValues(this, R.xml.user_settings, true);
        PreferenceManager.setDefaultValues(this, R.xml.source_settings, true);
        PreferenceManager.setDefaultValues(this, R.xml.product_settings, true);
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