package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.OpeningTime;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore {

	//TODO Remove this function after completed database / testing
	public ArrayList<CaffeineSource> getCaffeineSources(){
		return SPARQLQuerier.getCaffeineSources();
	}

	//TODO Remove this function after completed database / testing
	public ArrayList<OpeningTime> getOpeningTimes(String caffeineSourceId){
		return SPARQLQuerier.getCurrentOpeningTimes(caffeineSourceId);
	}
	
	//TODO Remove this function after completed database / testing
	public ArrayList<CaffeineProduct> getCaffeineProducts(String caffeineSourceId){
		return SPARQLQuerier.getCaffeineProducts(caffeineSourceId);
	}
}
