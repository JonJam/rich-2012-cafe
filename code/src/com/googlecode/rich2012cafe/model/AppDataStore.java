package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;
import java.util.Collections;

import android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.googlecode.rich2012cafe.model.database.*;
import com.googlecode.rich2012cafe.model.interfaces.DataStoreInterface;

/**
 * Class which contains connections to all data associated with the application. Including:
 * 	Database
 * 	Shared preferences
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore implements DataStoreInterface{
	
	private CaffeineSourcesDataSource sourcesTable;
	private OpeningTimesDataSource openingTimesTable;
	private CaffeineProductsDataSource productsTable;
	private SharedPreferences settings;
	
	private static AppDataStore instance;
	
	private static final String USER_TYPE = "userPref";
	private static final String USER_TYPE_DEFAULT = "Student";
	
	private static final String VIEW_VENDING = "viewVendingMachines";
	private static final boolean VIEW_VENDING_DEFAULT = true;
	
	private static final String VIEW_OFF_CAMPUS = "viewOffCampus";
	private static final boolean VIEW_OFF_CAMPUS_DEFAULT = true;
	
	private static final String VIEW_COFFEE = "viewCoffee";
	private static final boolean VIEW_COFFEE_DEFAULT = true;

	private static final String VIEW_TEA = "viewTea";
	private static final boolean VIEW_TEA_DEFAULT = true;

	private static final String VIEW_SOFT_DRINKS = "viewSoftDrinks";
	private static final boolean VIEW_SOFT_DRINKS_DEFAULT = true;

	private static final String VIEW_ENERGY_DRINKS = "viewEnergyDrinks";
	private static final boolean VIEW_ENERGY_DRINKS_DEFAULT = true;

	public AppDataStore(CaffeineSourcesDataSource sourcesTable, OpeningTimesDataSource openingTimesTable, 
			CaffeineProductsDataSource productsTable, SharedPreferences settings){
		
		this.sourcesTable = sourcesTable;
		this.openingTimesTable = openingTimesTable;
		this.productsTable = productsTable;
		
		this.settings = settings;
		
		openDataSourceConnections();
		instance = this;
		
		DatabaseCheck dc = new DatabaseCheck();
		dc.execute();
	}

	 /**
     * Asynchronous task to perform database check which may involve network activity.
     * 
     */
    private class DatabaseCheck extends AsyncTask<Void, Void, Void>{
            
        @Override
        protected Void doInBackground(Void... params) {
                performDatabaseCheck();
                return null;
        }
    }
	
	public static AppDataStore getInstance(Context context){
		if(instance == null){
			instance = new AppDataStore(
					new CaffeineSourcesDataSource(context), 
					new OpeningTimesDataSource(context), 
					new CaffeineProductsDataSource(context),
					PreferenceManager.getDefaultSharedPreferences(context));
		}else{
			Log.e("storage", "successful get instance");
		}
		return instance;
	}
	/**
	 * Method to open connections to the data source objects.
	 */
	public void openDataSourceConnections(){
		sourcesTable.open();
		openingTimesTable.open();
		productsTable.open();
	}
	
	/**
	 * Method to close connections to the data source objects.
	 */
	public void closeDataSourceConnections(){
		sourcesTable.close();
		openingTimesTable.close();
		productsTable.close();
	}
	
	/**
	 * Method to perform SPARQL queries and insert returned objects into the appropriate data sources.
	 */
	private void insertLinkedDataIntoDatabase(){
		
		SPARQLQuerier querier = new SPARQLQuerier();
		
		ArrayList<CaffeineSource> sources = querier.getCaffeineSources();
		
		for(CaffeineSource source: sources){
	
			//Add source to database.
			sourcesTable.insertCaffeineSource(source);
			
			//Add opening times to database.
			for(OpeningTime ot : querier.getCurrentOpeningTimes(source.getId())){
				openingTimesTable.insertOpeningTime(ot);
			}
			
			//Add products to database.
			for(CaffeineProduct p : querier.getCaffeineProducts(source.getId())){
				productsTable.insertProduct(p);
			}
		}
	}
	
	/**
	 * Method to check whether the database contains data and needs updating. If 
	 * either conditions are not met then the appropriate actions are taken by calling 
	 * insertLinkedDataIntoDatabase.
	 */
	private void performDatabaseCheck(){
				
		if(sourcesTable.getAllCaffeineSources(
				settings.getBoolean(VIEW_VENDING, VIEW_VENDING_DEFAULT),
				settings.getBoolean(VIEW_OFF_CAMPUS, VIEW_OFF_CAMPUS_DEFAULT)).size() == 0){
			//No results in database so fill with data.
			
			insertLinkedDataIntoDatabase();
		
		} else if(openingTimesTable.getExpiredOpeningTimes().size() != 0){
			//Some OpeningTime objects have expired so drop database data and re add content.
		
			productsTable.deleteAllCaffeineProducts();
			openingTimesTable.deleteAllOpeningTimes();
			sourcesTable.deleteAllCaffeineSources();
			
			insertLinkedDataIntoDatabase();
		}
	}
	
	/**
	 * Method to get CaffeineProduct objects for a caffeine source.
	 * 
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getAllCaffeineSources(){
		
		return sourcesTable.getAllCaffeineSources(settings.getBoolean(VIEW_VENDING, VIEW_VENDING_DEFAULT),
				settings.getBoolean(VIEW_OFF_CAMPUS, VIEW_OFF_CAMPUS_DEFAULT));
	}
	
	/**
	 * Method to get OpeningTime objects for a caffeine source.
	 * 
	 * @param id (String object)
	 * @return ArrayList of OpeningTime objects.
	 */
	public ArrayList<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		
		return openingTimesTable.getOpeningTimesForCaffeineSource(id);
	}

	/**
	 * Method to get all caffeine product names
	 * 
	 * @return ArrayList of String objects.
	 */
	public ArrayList<String> getAllCaffeineProductNames() {
		return productsTable.getAllCaffeineProductNames(settings.getBoolean(VIEW_COFFEE, VIEW_COFFEE_DEFAULT),
				settings.getBoolean(VIEW_TEA, VIEW_TEA_DEFAULT),
				settings.getBoolean(VIEW_SOFT_DRINKS, VIEW_SOFT_DRINKS_DEFAULT),
				settings.getBoolean(VIEW_ENERGY_DRINKS, VIEW_ENERGY_DRINKS_DEFAULT)
				);
	}
	
	/*
	 * Method to get all caffeine product types
	 * 
	 * @return ArrayList of String objects.
	 */
	public ArrayList<String> getAllCaffeineProductTypes(){
		return productsTable.getCaffeineProductTypes(settings.getBoolean(VIEW_COFFEE, VIEW_COFFEE_DEFAULT),
				settings.getBoolean(VIEW_TEA, VIEW_TEA_DEFAULT),
				settings.getBoolean(VIEW_SOFT_DRINKS, VIEW_SOFT_DRINKS_DEFAULT),
				settings.getBoolean(VIEW_ENERGY_DRINKS, VIEW_ENERGY_DRINKS_DEFAULT)
				);
	}
	
	/**
	 * Method to get CaffeineSource objects for a CaffeineProduct name.
	 * 
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getCaffeineSourcesForProductName(String productName) {
		ArrayList<String> ids = productsTable.getCaffeineSourceIdsForProductName(productName);
		return sourcesTable.getCaffeineSources(ids,
				settings.getBoolean(VIEW_VENDING,VIEW_VENDING_DEFAULT),
				settings.getBoolean(VIEW_OFF_CAMPUS, VIEW_OFF_CAMPUS_DEFAULT));
	}
	
	/**
	 * Method to get all caffeine product names for a product type.
	 * 
	 * @param type (String object)
	 * @return ArrayList of String objects
	 */
	public ArrayList<String> getCaffeineProductsForProductType(String type){
		return productsTable.getCaffeineProductsForProductType(type);
	}
			
	/**
	 * Method to get CaffeineProduct objects for a caffeine source.
	 * 
	 * @param id (String object)
	 * @return ArrayList of CaffeineProduct objects.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id){
		
		return productsTable.getCaffeineProductsForCaffeineSource(id, settings.getString(USER_TYPE, USER_TYPE_DEFAULT),
				settings.getBoolean(VIEW_COFFEE, VIEW_COFFEE_DEFAULT),
				settings.getBoolean(VIEW_TEA, VIEW_TEA_DEFAULT),
				settings.getBoolean(VIEW_SOFT_DRINKS, VIEW_SOFT_DRINKS_DEFAULT),
				settings.getBoolean(VIEW_ENERGY_DRINKS, VIEW_ENERGY_DRINKS_DEFAULT)
				);
		}
	
	/**
	 * Method to get CaffeineProduct objects in price range.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsInPriceRange(double maxPrice){
		return productsTable.getCaffeineProductsInPriceRange(maxPrice,
				settings.getBoolean(VIEW_COFFEE, VIEW_COFFEE_DEFAULT),
				settings.getBoolean(VIEW_TEA, VIEW_TEA_DEFAULT),
				settings.getBoolean(VIEW_SOFT_DRINKS, VIEW_SOFT_DRINKS_DEFAULT),
				settings.getBoolean(VIEW_ENERGY_DRINKS, VIEW_ENERGY_DRINKS_DEFAULT)
				);
	}
	
	
	public String test(){
		
		String a = "";
//		
//		ArrayList<CaffeineProduct> sources = getCaffeineProductsInPriceRange(1.20);
//				
//		for(CaffeineProduct s : sources){
//			a += s.getName() + " " + s.getProductType() + " " + s.getPriceType()+ "\n\n";
//		}
		
		ArrayList<CaffeineSource> sources = getCaffeineSourcesForProductName("Red Bull Can");
				
		for(CaffeineSource s : sources){
			a += s.getName() + " " + s.getType() + " " 
					+ s.getOffCampus() + "\n\n";
		}
		
//		ArrayList<String> types = 
//		
//		for(String t : types){
//			a += t + "\n\n";
//		}
		
		return a;
	}
}