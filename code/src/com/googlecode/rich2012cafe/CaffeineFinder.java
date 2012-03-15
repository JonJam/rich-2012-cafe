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
 * How Settings affect View / Queries:
 * ===================================
 * 
 *  		Student or Staff: (QUERIES COMPLETE)
 *  			Functions in AppDataStore effected:
 *  				getCaffeineProductsForCaffeineSource (Get Products for user type either student or staff or all)
 *    
 *  		Display Vending Machines: (QUERIES COMPLETE)
 *  			Functions in AppDataStore effected:
 *  				getAllCaffeineSources (Get point of sale and if applicable vending machine sources)
 *  				getCaffeineSourcesForProductName (Get point of sale and if applicable vending machine sources)
 *  
 *  		Display Off Campus Locations: (QUERIES COMPLETE)
 *  				getAllCaffeineSources (Get off campus if applicable)
 *  				getCaffeineSourcesForProductName (Get off campus if applicable)
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
 * WORK
 * ==== 
 * TODO Implement queries and adjust as others need (See DataStoreInterface):
 * 
 *   Get sources given lat and long (NEED TO DECIDE WHAT GIVING MODEL EITHER OBJECT OR COORDINATES)
 *   May need to change getCaffeineProductsinPriceRange
 *   
 * TODO Add loading overlay (blocking off functionality) whilst performing database check.
 * 
 * TODO Find way of doing database check and installation of data when install app. (Takes 1 min to download all data on my machine)
 * 
 * TODO MVC layout
 * 
 * TODO Handle all onCreate, onResume, onPause, onDestory etc.
 * 
 * TODO Backwards Compatibility
 * 
 * TODO Locations option in UI displays same info for multiple items. Think may need to change SPARQL query or what locations tab uses.
 * 
 * TODO Add logging and getting University Timetable information (JON - Emailed guy who wrote iSoton after speaking to Ash to
 * find out how done it)
 * TODO Caffeine MG for CaffeineProducts
 * TODO Make system so can update accordingly in backend
 * TODO Complete Settings (Inc Favourites) 
 * TODO Change appDataStore functions for new uses cases / settings.
 * TODO Research about caffeine decay and having minimum of requirements to enter (i.e decay rate, optimal level)
 * TODO Create database on permanent system to store leaderboards
 * TODO Scoring system.
 * TODO Notifications / Alerts
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
 * TODO Login and DB stuff (Assigned to: Jon)
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