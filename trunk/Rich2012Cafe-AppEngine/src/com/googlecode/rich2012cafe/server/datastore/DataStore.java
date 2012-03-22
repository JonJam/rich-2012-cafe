package com.googlecode.rich2012cafe.server.datastore;

import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.android.c2dm.server.PMF;

import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;
import com.googlecode.rich2012cafe.server.sparql.SPARQLQuerier;

public class DataStore {
	
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
	private List<OpeningTime> getAllOpeningTimes(){

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
	public List<CaffeineSourceProduct> getAllCaffeineSourceProducts(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSourceProduct.class.getName());
			List<CaffeineSourceProduct> list = (List<CaffeineSourceProduct>) query.execute();
		
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
	
	@SuppressWarnings("unchecked")
	public List<OpeningTime> getExpiredOpeningTimes(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
				
		try {
			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName());
			query.setFilter("validTo < dateParam");
			query.declareParameters("java.util.Date dateParam");			

			List<OpeningTime> list = (List<OpeningTime>) query.execute(Calendar.getInstance().getTime());
    		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	//Datastore methods
	
	public void populateDatastore(){
		SPARQLQuerier querier = new SPARQLQuerier();
		
		List<CaffeineSource> sources = querier.getCaffeineSources();
		
		for(CaffeineSource source: sources){
	
			//Add source to database.
			createCaffeineSource(source);
			
			//Add opening times to database.
			for(OpeningTime ot : querier.getCurrentOpeningTimes(source.getId())){
				createOpeningTime(ot);
			}
			
			//Add products to database.
			for(CaffeineSourceProduct p : querier.getCaffeineSourceProducts(source.getId())){
				createCaffeineSourceProduct(p);
			}
		}
		
		for(CaffeineProduct p : querier.getCaffeineProducts()){
			createCaffeineProduct(p);
		}
	}

	public void clearDatastore(){
		List<CaffeineSource> sources = getAllCaffeineSources();
		List<CaffeineSourceProduct> sourceProducts = getAllCaffeineSourceProducts();
		List<CaffeineProduct> products = getAllCaffeineProducts();
		List<OpeningTime> times = getAllOpeningTimes();
		
		for(CaffeineSource source : sources){
			deleteCaffeineSource(source.getId());
		}
		
		for(CaffeineSourceProduct product : sourceProducts){
			deleteCaffeineSourceProduct(product.getId());
		}
		
		for(CaffeineProduct product : products){
			deleteCaffeineProduct(product.getName());
		}
		
		for(OpeningTime time : times){
			deleteOpeningTime(time.getId());
		}
		
	}
	
	//Create Methods
	
	private void createCaffeineSource(CaffeineSource source){
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void createCaffeineSourceProduct(CaffeineSourceProduct product){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void createCaffeineProduct(CaffeineProduct product){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void createOpeningTime(OpeningTime time){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(time);
	    } finally {
	    	pm.close();
	    }
	}
	
	//Delete Methods
	
	private void deleteCaffeineSource(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineSource source = pm.getObjectById(CaffeineSource.class, id);
			pm.deletePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void deleteCaffeineSourceProduct(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineSourceProduct product = pm.getObjectById(CaffeineSourceProduct.class, id);
			pm.deletePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void deleteCaffeineProduct(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineProduct product = pm.getObjectById(CaffeineProduct.class, id);
			pm.deletePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	private void deleteOpeningTime(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			OpeningTime time = pm.getObjectById(OpeningTime.class, id);
			pm.deletePersistent(time);
	    } finally {
	    	pm.close();
	    }
	}
	
	//Get Methods
	

	
	
//	
//	//Get Methods
//	
//	//USE TO GET SOURCE GIVEN LOCATION, SETTINGS INFO
//	public void getCaffeineSources(){
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id){
//		PersistenceManager pm = PMF.get().getPersistenceManager();
//		
//		try {
//			Query query = pm.newQuery("SELECT FROM " + CaffeineProduct.class.getName() 
//					+ "WHERE caffeineSourceId == '" + id + "'");
//			List<CaffeineProduct> list = (List<CaffeineProduct>) query.execute();
//		
//			return list.size() == 0 ? null : list;
//	  	} catch (RuntimeException e) {
//	  		System.out.println(e);
//	  		throw e;
//	  	} finally {
//	  		pm.close();
//	  	}
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<OpeningTime> getOpeningTimesForCaffeineSource(String id){
//		PersistenceManager pm = PMF.get().getPersistenceManager();
//		
//		try {
//			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName() 
//					+ "WHERE caffeineSourceId == '" + id + "'");
//			List<OpeningTime> list = (List<OpeningTime>) query.execute();
//		
//			return list.size() == 0 ? null : list;
//	  	} catch (RuntimeException e) {
//	  		System.out.println(e);
//	  		throw e;
//	  	} finally {
//	  		pm.close();
//	  	}
//	}
//	
//	public static String getUserId() {
//	    UserService userService = UserServiceFactory.getUserService();
//	    User user = userService.getCurrentUser();
//	    return user.getUserId();
//	}
//
//	public static String getUserEmail() {
//		UserService userService = UserServiceFactory.getUserService();
//        User user = userService.getCurrentUser();
//        return user.getEmail();
//	}
}
