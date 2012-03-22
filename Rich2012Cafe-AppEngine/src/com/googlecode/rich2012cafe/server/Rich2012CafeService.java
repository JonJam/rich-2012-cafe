package com.googlecode.rich2012cafe.server;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;


public class Rich2012CafeService {

	static DataStore db = new DataStore();
	
	/*
	 * METHOD THAT WILL BE USED TO GET SOURCES GIVEN LOCATION AND SETTINGS INFO 
	 * 
	 * CHANGE AS NEED
	 */
//	@ServiceMethod
//	public void getCaffeineSources(){
//		
//	}
	
//	@ServiceMethod
//	public List<CaffeineProduct> getUniqueCaffeineProducts(){
//		return db.getUniqueCaffeineProducts();
//	}
	
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
