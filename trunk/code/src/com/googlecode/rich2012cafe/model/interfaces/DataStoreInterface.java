package com.googlecode.rich2012cafe.model.interfaces;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.model.database.CaffeineProduct;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.OpeningTime;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public interface DataStoreInterface {
	
	void openDataSourceConnections();
	void closeDataSourceConnections();
	void performDatabaseCheck();
	
	ArrayList<OpeningTime> getOpeningTimesForCaffeineSource(String id);
	ArrayList<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id);
	
	ArrayList<String> getAllCaffeineProductNames();
	ArrayList<String> getCaffeineProductsForProductType(String type);
	ArrayList<CaffeineSource> getCaffeineSourcesForProductName(String productName);
}
