package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;
import java.util.Collections;

import com.googlecode.rich2012cafe.model.database.*;

/**
 * Class which stores all data associated with the application including the database.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore {
	
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
	public ArrayList<CaffeineProduct> getProductsForCaffeineSource(String id){
		
		return productsTable.getCaffeineProductsForCaffeineSource(id);
	}
	
	
	public String test(){
		ArrayList<CaffeineProduct> sources = getProductsForCaffeineSource("http://id.southampton.ac.uk/point-of-service/42-lattes");
		String a = "";
		
		for(CaffeineProduct s : sources){
			a += s.getName() +" " + s.getPrice() +"\n";
		}
		
		return a;
	
	}
}