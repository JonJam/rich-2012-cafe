package com.googlecode.rich2012cafe.server.datastore;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.android.c2dm.server.PMF;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;

public class DataStore {

	//Create Methods
	public void createCaffeineSource(CaffeineSource source){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	public void createCaffeineProduct(CaffeineProduct product){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	public void createOpeningTime(OpeningTime time){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(time);
	    } finally {
	    	pm.close();
	    }
	}
	
	//Delete Methods
	public void deleteCaffeineSource(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineSource source = pm.getObjectById(CaffeineSource.class, id);
			pm.deletePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	public void deleteCaffeineProduct(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineProduct product = pm.getObjectById(CaffeineProduct.class, id);
			pm.deletePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	public void deleteOpeningTime(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			OpeningTime time = pm.getObjectById(OpeningTime.class, id);
			pm.deletePersistent(time);
	    } finally {
	    	pm.close();
	    }
	}
	
	//Get Methods
	
	//USED FOR SCHEDULE TASK
	@SuppressWarnings("unchecked")
	public List<CaffeineSource> getAllCaffeineSources(){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSource.class.getName());
			List<CaffeineSource> list = (List<CaffeineSource>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	@SuppressWarnings("unchecked")
	public List<OpeningTime> getAllOpeningTimes(){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName());
			List<OpeningTime> list = (List<OpeningTime>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	@SuppressWarnings("unchecked")
	public List<CaffeineProduct> getAllCaffeineProducts(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineProduct.class.getName());
			List<CaffeineProduct> list = (List<CaffeineProduct>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	//USE TO GET SOURCE GIVEN LOCATION, SETTINGS INFO
	public void getCaffeineSources(){
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineProduct.class.getName() 
					+ "WHERE caffeineSourceId == '" + id + "'");
			List<CaffeineProduct> list = (List<CaffeineProduct>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}

	@SuppressWarnings("unchecked")
	public List<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName() 
					+ "WHERE caffeineSourceId == '" + id + "'");
			List<OpeningTime> list = (List<OpeningTime>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
}
