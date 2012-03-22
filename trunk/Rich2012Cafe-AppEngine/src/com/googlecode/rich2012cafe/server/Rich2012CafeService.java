package com.googlecode.rich2012cafe.server;

import java.util.List;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;

/**
 * This class forms the Rich2012Cafe Service and contains all the RPC methods.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Rich2012CafeService {

	private static DataStore db = new DataStore();
		
	/**
	 * Method to get CaffeineSources given params:
	 * 
	 * 
	 * @return List of CaffeineSource objects.
	 */
	@ServiceMethod
	public List<CaffeineSource> getCaffeineSourcesGiven(){
		return db.getCaffeineSourcesGiven();
	}
	
	/**
	 * Method to get CaffeineSourceProducts for a CaffeineSource.
	 * 
	 * @param id (String object)
	 * @return List of CaffeineSourceProduct objects.
	 */
	@ServiceMethod
	public List<CaffeineSourceProduct> getCaffeineSourceProductsForCaffeineSource(String id){
		return db.getCaffeineSourceProductsForCaffeineSource(id);
	}
	
	/**
	 * Method to get OpeningTimes for a CaffeineSource.
	 * 
	 * @param id (String object)
	 * @return List of OpeningTime objects.
	 */
	@ServiceMethod
	public List<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		return db.getOpeningTimesForCaffeineSource(id);
	}
	
	/**
	 * Method to get all CaffeineProducts.
	 * 
	 * @return List of CaffeineProduct.
	 */
	@ServiceMethod
	public List<CaffeineProduct> getAllCaffeineProducts(){
		return db.getAllCaffeineProducts();
	}
	
	/**
	 * Method to update the datastore.
	 */
	@ServiceMethod
	public void updateDataStore(){
	
		if(db.getAllCaffeineSources() == null){
			//Empty datastore
			
			db.populateDatastore();

		}  else if(db.getExpiredOpeningTimes() != null){
			//Expired Opening Times

			db.clearDatastore();
			db.populateDatastore();
		}
	}
}
