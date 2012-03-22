package com.googlecode.rich2012cafe.server;

import java.util.List;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;


public class Rich2012CafeService {

	static DataStore db = new DataStore();
	
	@ServiceMethod
	public List<CaffeineSource> getCaffeineSourcesGiven(){
		return db.getCaffeineSourcesGiven();
	}
	
	@ServiceMethod
	public List<CaffeineSourceProduct> getCaffeineSourceProductsForCaffeineSource(String id){
		return db.getCaffeineSourceProductsForCaffeineSource(id);
	}
	
	@ServiceMethod
	public List<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		return db.getOpeningTimesForCaffeineSource(id);
	}
	
	@ServiceMethod
	public List<CaffeineProduct> getAllCaffeineProducts(){
		return db.getAllCaffeineProducts();
	}
	
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
