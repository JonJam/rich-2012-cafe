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
 * TODO SPARQLQuerier sort out where haves blanks for places e.g. everything apart from lat / long
 * 
 * TODO Move setPreferences method in this class to where appropriate. Ask T.
 * 
 * TODO Sort out an Agile Development process for the report.
 * 
 * TODO Saving settings. (JON and SAMI: WORKING ON)
	 * 		Favourite products
	 * 		Favourite places
	 * 		
	 * 		QUESTION: AT MOMENT LAUNCHING AN ACTIVITY WHICH WILL DISPLAY VIEW WITH CHECKBOXES
	 * 				  WHICH WILL INTERACT WITH DB AS APPROPIATE TO SAVE LISTS. Is this best ok before I implement it?
	 * 
	 * 		NEED TO CHANGE:
	 * 			product_settings.xml
	 * 			source_settings.xml
 * 
 * TODO How Settings affect View / Queries.
 * 
 *  		Student or Staff affects: (QUERIES COMPLETE)
 *  			Only get Products for type set.
 *  		
 *  			Functions in AppDataStore effected:
 *  				getAllCaffeineProductNames (Just in case a product only available to a certain user)
 *  					getCaffeineSourcesForProductName (Inherits from previous)
 *  				getCaffeineProductsForProductType (Just in case a product only available to a certain user)
 *  				getCaffeineProductsForCaffeineSource
 *  				getCaffineProductsInPriceRange (Just in case a product only available to a certain user)
 *  
 *  			DECISIONS TO MAKE:
 *  				Should we check for a product being only available to a certain user (More for future)?
 *  					If yes, should we also only get locations where have student / staff or all products ?
 *  						If yes, what happens when place doesn't have list of products?
 *  
 *  		Display Vending Machine affects: (QUERIES COMPLETE)
 *  			Get Locations
 *  
 *  			Functions in AppDataStore effected:
 *  				getAllCaffeineSources
 *  				getCaffeineSourcesForProductName			
 *  	
 *  		Product Types
 *  			Only show places/products that serve/are types.
 *  	
 *  			getAllCaffeineProductNames (Only show products that are of wanted type)
 *  				getCaffeineSourcesForProductNames (Inherits blocking from previous)
 *  			getCaffeineProductTypes (Only show product types that are wanted)
 *  				getCaffeineProductsForProductType (Inherits blocking from previous)
 *  			getCaffeineProductsForCaffeineSource (Only show products that are of wanted type)
 *  			getCaffeineProductsInPriceRange (Only show products that are of wanted type)
 *  			getAllCaffeineSources (Only show places that serve products want)
 *  
 *  			DECISIONS TO MAKE:
 *  				For getAllCaffeineSources how do we deal when a place doesn't have list of products?
 *  
 *  		Favourite Products affects:
 *  			Display favourites as seperate list at top of any product list
 *  		Favourite Locations affects:
 *  			Always display favourite locations on map no matter what distance.
 *  			
* 
 * TODO Remove deprecated actions/classes in: 
 * 		UserInterface (TabbedActivity)
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
        PreferenceManager.setDefaultValues(this, R.xml.products_to_view_settings, true);
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