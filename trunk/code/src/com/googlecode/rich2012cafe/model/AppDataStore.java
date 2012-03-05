package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;
import java.util.Collections;

import com.googlecode.rich2012cafe.model.database.*;
import com.googlecode.rich2012cafe.model.interfaces.DataStoreInterface;

/**
 * Class which stores all data associated with the application including the database.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore implements DataStoreInterface{
	
	private CaffeineSourcesDataSource sourcesTable;
	private OpeningTimesDataSource openingTimesTable;
	private CaffeineProductsDataSource productsTable;
	
	public AppDataStore(CaffeineSourcesDataSource sourcesTable, OpeningTimesDataSource openingTimesTable, CaffeineProductsDataSource productsTable){
		this.sourcesTable = sourcesTable;
		this.openingTimesTable = openingTimesTable;
		this.productsTable = productsTable;
		
		openDataSourceConnections();
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
	public void performDatabaseCheck(){
				
		if(sourcesTable.getAllCaffeineSources().size() == 0){
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
	 * Method to get OpeningTime objects for a caffeine source.
	 * 
	 * @param id (String object)
	 * @return ArrayList of OpeningTime objects.
	 */
	public ArrayList<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		
		return openingTimesTable.getOpeningTimesForCaffeineSource(id);
	}
	
	/**
	 * Method to get CaffeineProduct objects for a caffeine source.
	 * 
	 * @param id (String object)
	 * @return ArrayList of CaffeineProduct objects.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id){
		
		return productsTable.getCaffeineProductsForCaffeineSource(id);
	}
	
	/**
	 * Method to get CaffeineProduct objects for a caffeine source.
	 * 
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getAllCaffeineSources(){
		
		return sourcesTable.getAllCaffeineSources();
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
	 * Method to get all caffeine product names
	 * 
	 * @return ArrayList of String objects.
	 */
	public ArrayList<String> getAllCaffeineProductNames() {
		return productsTable.getAllCaffeineProductNames();
	}
	
	/**
	 * Method to get CaffeineSource objects for a CaffeineProduct name.
	 * 
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getCaffeineSourcesForProductName(String productName) {
		ArrayList<String> ids = productsTable.getCaffeineSourceIdsForProductName(productName);
		return sourcesTable.getCaffeineSources(ids);
	}
	
	/**
	 * Method to get CaffeineProduct objects in price range.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsInPriceRange(double maxPrice){
		return productsTable.getCaffeineProductsInPriceRange(maxPrice);
	}
	
	
	public String test(){
		ArrayList<CaffeineSource> sources = getAllCaffeineSources();
				String a = "";
		
		for(CaffeineSource s : sources){
			a += s.getBuildingNumber() + " " + s.getName() + " " + s.getBuildingLat() + " " + s.getBuildingLong() +"\n";
		}
		
		return a;
	
	}
}