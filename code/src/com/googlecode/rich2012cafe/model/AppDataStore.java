package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.OpeningTime;

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
	public ArrayList<OpeningTime> getOpeningTimes(String caffeienSourceId){
		return SPARQLQuerier.getCurrentOpeningTimes(caffeienSourceId);
	}
}
