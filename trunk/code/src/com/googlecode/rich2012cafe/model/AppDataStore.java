package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;
import com.googlecode.rich2012cafe.model.database.*;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore {

	public void performDatabaseCheck(){
		
	}

	//TODO Remove this function after completed database / testing
    public ArrayList<CaffeineSource> getCaffeineSources(){
            return new SPARQLQuerier().getCaffeineSources();
    }

    //TODO Remove this function after completed database / testing
    public ArrayList<OpeningTime> getOpeningTimes(String caffeineSourceId){
            return new SPARQLQuerier().getCurrentOpeningTimes(caffeineSourceId);
    }
    
    //TODO Remove this function after completed database / testing
    public ArrayList<CaffeineProduct> getCaffeineProducts(String caffeineSourceId){
            return new SPARQLQuerier().getCaffeineProducts(caffeineSourceId);
    }
}
