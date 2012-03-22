package com.googlecode.rich2012cafe.server.datastore;

import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.android.c2dm.server.PMF;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;
import com.googlecode.rich2012cafe.server.sparql.SPARQLQuerier;

/**
 * Class to contain all methods that affect the database.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class DataStore {

	//Datastore methods
	
	/**
	 * Method to populate datastore using SPARQL endpoint.
	 */
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
			
			//Add source products to database.
			for(CaffeineSourceProduct p : querier.getCaffeineSourceProducts(source.getId())){
				createCaffeineSourceProduct(p);
			}
		}
		
		//Add caffeine products to database.
		for(CaffeineProduct p : querier.getCaffeineProducts()){
			createCaffeineProduct(p);
		}
	}

	/**
	 * Method to clear the datastore.
	 */
	public void clearDatastore(){
		List<CaffeineSource> sources = getAllCaffeineSources();
		List<CaffeineSourceProduct> sourceProducts = getAllCaffeineSourceProducts();
		List<CaffeineProduct> products = getAllCaffeineProducts();
		List<OpeningTime> times = getAllOpeningTimes();
		
		//Delete all CaffeineSource objects
		for(CaffeineSource source : sources){
			deleteCaffeineSource(source.getId());
		}
		
		//Delete all CaffeineSourceProduct objects.
		for(CaffeineSourceProduct product : sourceProducts){
			deleteCaffeineSourceProduct(product.getId());
		}
		
		//Delete all CaffeineProduct objects.
		for(CaffeineProduct product : products){
			deleteCaffeineProduct(product.getName());
		}
		
		//Delete all OpeningTime objects.
		for(OpeningTime time : times){
			deleteOpeningTime(time.getId());
		}
		
	}
	
	//Create Methods
	
	/**
	 * Method to store a CaffeineSource object.
	 * 
	 * @param source (CaffeineSource object)
	 */
	private void createCaffeineSource(CaffeineSource source){
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to store a CaffeineSourceProduct object.
	 * 
	 * @param product (CaffeineSourceProduct object)
	 */
	private void createCaffeineSourceProduct(CaffeineSourceProduct product){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to store a CaffeineProduct object.
	 * 
	 * @param product (CaffeineProduct object)
	 */
	private void createCaffeineProduct(CaffeineProduct product){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to store a OpeningTime object.
	 * 
	 * @param time (OpeningTime object)
	 */
	private void createOpeningTime(OpeningTime time){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(time);
	    } finally {
	    	pm.close();
	    }
	}
	
	//Delete Methods
	
	/**
	 * Method to remove a CaffeineSource object.
	 * 
	 * @param id (String id)
	 */
	private void deleteCaffeineSource(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineSource source = pm.getObjectById(CaffeineSource.class, id);
			pm.deletePersistent(source);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to remove a CaffeineSourceProduct object.
	 * 
	 * @param id (String id)
	 */
	private void deleteCaffeineSourceProduct(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineSourceProduct product = pm.getObjectById(CaffeineSourceProduct.class, id);
			pm.deletePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to remove a CaffeineProduct object.
	 * 
	 * @param id (String id)
	 */
	private void deleteCaffeineProduct(String id){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CaffeineProduct product = pm.getObjectById(CaffeineProduct.class, id);
			pm.deletePersistent(product);
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to remove an OpeningTime object.
	 * 
	 * @param id (String id)
	 */
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
	
	/**
	 * Method to get all CaffeineSources.
	 * 
	 * @return List of CaffeineSource objects.
	 */
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
	
	/**
	 * Method to get all OpeningTimes.
	 * 
	 * @return List of OpeningTime objects.
	 */
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
	
	/**
	 * Method to get all CaffeineSourceProducts.
	 * 
	 * @return List of CaffeineSourceProducts.
	 */
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
	
	/**
	 * Method to get all CaffeineProducts.
	 * 
	 * @return List of CaffeineProducts.
	 */
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
	
	/**
	 * Method to get all expired OpeningTime objects.
	 * 
	 * @return List of OpeningTime objects.
	 */
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
	
	/**
	 * Method to get CaffeineSources given params:
	 * 
	 * @return List of CaffeineSource objects.
	 */
	@SuppressWarnings("unchecked")
	public List<CaffeineSource> getCaffeineSourcesGiven(){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSource.class.getName());
			List<CaffeineSource> list = (List<CaffeineSource>) query.execute();
		
			return list.size() == 0 ? null : list.subList(0, 5);
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}

	/**
	 * Method to get CaffeineSourceProducts for a CaffeineSource.
	 * 
	 * @param id (String object)
	 * @return List of CaffeineSourceProduct objects.
	 */
	@SuppressWarnings("unchecked")
	public List<CaffeineSourceProduct> getCaffeineSourceProductsForCaffeineSource(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSourceProduct.class.getName() 
					+ " WHERE caffeineSourceId == '" + id + "'");
			List<CaffeineSourceProduct> list = (List<CaffeineSourceProduct>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	/**
	 * Method to get OpeningTimes for a CaffeineSource.
	 * 
	 * @param id (String object)
	 * @return List of OpeningTime objects.
	 */
	@SuppressWarnings("unchecked")
	public List<OpeningTime> getOpeningTimesForCaffeineSource(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName() 
					+ " WHERE caffeineSourceId == '" + id + "'");
			List<OpeningTime> list = (List<OpeningTime>) query.execute();
		
			return list.size() == 0 ? null : list;
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	/**
	 * Method to get current user id.
	 * 
	 * @return String object.
	 */
	public String getUserId() {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    return user.getUserId();
	}

	/**
	 * Method to get current user email.
	 * 
	 * @return String object.
	 */
	public String getUserEmail() {
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    return user.getEmail();
	}
}
