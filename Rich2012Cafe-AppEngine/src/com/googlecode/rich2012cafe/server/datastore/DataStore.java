package com.googlecode.rich2012cafe.server.datastore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceWrapper;
import com.googlecode.rich2012cafe.server.datastore.objects.LeaderboardScore;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;
import com.googlecode.rich2012cafe.server.sparql.SPARQLQuerier;
import com.googlecode.rich2012cafe.server.utils.Rich2012CafeUtil;

/**
 * Class to contain all methods that affect the database.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class DataStore {
	
	/**
	 * Method get caffeine sources and their information given:
	 *  - Latitude and longitude position.
	 *  - Whether sources are currently open.
	 * 
	 * @param latitude (double value)
	 * @param longitude (double value)
	 * @return List of CaffeineSourceWrapper object
	 */
	@SuppressWarnings("unchecked")
	public List<CaffeineSourceWrapper> getCaffeineSourcesGiven(double latitude, double longitude){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSource.class.getName());
			List<CaffeineSource> list = (List<CaffeineSource>) query.execute();
			
			if(list.size() == 0){
				return null; 
			} else{
				
				//Sort CaffeineSources according to distance away from current position
				Collections.sort(list, new DistanceComparator(latitude, longitude));
			
				List<CaffeineSourceWrapper> wrapperSources = new ArrayList<CaffeineSourceWrapper>();
				int i = 0;
				
				//Get number of currently open caffeine sources from distance ordered list above.
				while(wrapperSources.size() < Rich2012CafeUtil.CAFFEINE_LOCATION_LIMIT){
					
					CaffeineSource source = list.get(i);
					String id = source.getId();
					List<OpeningTime> times = getOpeningTimesForCaffeineSource(id);
										
					if(times == null){
						//No opening times so add source.
						
						wrapperSources.add(new CaffeineSourceWrapper(
								source,
								times,
								getCaffeineSourceProductsForCaffeineSource(id)));
						
					} else if(isOpen(times)){
						//Location has times and is open so add source.
											
						wrapperSources.add(new CaffeineSourceWrapper(
								source,
								times,
								getCaffeineSourceProductsForCaffeineSource(id)));	
					} 
					
					i++;
				}

				return wrapperSources;
			}
	  	} catch (RuntimeException e) {
	  		e.printStackTrace();
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	/**
	 * Method to determine whether a caffeine source is currently open.
	 * 
	 * @param times (List of OpeningTime objects)
	 * @return boolean value
	 */
	private boolean isOpen(List<OpeningTime> times){
		
		boolean isOpen = false;
		SimpleDateFormat sdf = new SimpleDateFormat(Rich2012CafeUtil.DB_TIME_FORMAT);
		
		//Get current time information.
		Calendar today = Calendar.getInstance();
		String dayName = Rich2012CafeUtil.DAY_NAMES[today.get(Calendar.DAY_OF_WEEK) - 1];
		String todayTime = sdf.format(today.getTime());
		
		for(OpeningTime t : times){
			
			if(dayName.equals(t.getDay())){
				//Day name same so check time
				
				String openingTime = t.getOpeningTime();
				String closingTime = t.getClosingTime();
				
				if(openingTime.compareTo(todayTime) <= 0 && todayTime.compareTo(closingTime) <0 ){
					//Currently open so set isOpen to true.
					
					isOpen = true;
				}				

				break;
			}
		}
				
		return isOpen;
	}

	/**
	 * Method to perform database check.
	 */
	public void performDatastoreCheck(){
		
		if(getAllCaffeineSources() == null){
			//Empty datastore
			
			populateDatastore();
			
		}  else{
			//Performing opening times check

			boolean modificationsMade = false;
			
			SPARQLQuerier querier = new SPARQLQuerier();
			//Set querier with existing caffeine products.
			querier.setCaffeineProducts(getAllCaffeineProducts());
			
			
			for(CaffeineSource source : getAllCaffeineSources()){
				
				String id = source.getId();
				List<OpeningTime> times = getOpeningTimesForCaffeineSource(id);
				
				if(source.hasOpeningTimes() == true && 
						(getExpiredOpeningTimesForSource(id) != null || times == null)){
					//CaffeineSource has opening times and either has expired opening times or none so update.
					
					modificationsMade = true;
					
					//Delete all OpeningTimes objects for CaffeineSource if any.
					if(times != null){
						deleteOpeningTimes(times);
					}
					
					//Delete all CaffeineSourceProduct objects for CaffeineSource if any.
					List<CaffeineSourceProduct> products = getCaffeineSourceProductsForCaffeineSource(id);
					if(products != null){
						deleteCaffeineSourceProducts(products);
					}
					
					//Get current opening times for source and add to database.
					for(OpeningTime ot : querier.getCurrentOpeningTimes(id)){
						createOpeningTime(ot);
					}
					
					//Add source products to database.
					for(CaffeineSourceProduct p : querier.getCaffeineSourceProducts(id)){
						createCaffeineSourceProduct(p);
					}
				}
			}
			
			if(modificationsMade){
				//Modifications have been made to datastore so check CaffeineProducts.
				
				deleteAllCaffeineProducts();
				List<CaffeineProduct> products = querier.getCaffeineProducts();
				
				for(CaffeineProduct p : products){
					
					if(getCaffeineSourceProductsForCaffeineProduct(p.getName()) != null){
						//CaffeineProduct is in use so add
						createCaffeineProduct(p);
					}
				}
			}
		}
	}
	
	/**
	 * Method to populate datastore using SPARQL endpoint.
	 */
	private void populateDatastore(){
		SPARQLQuerier querier = new SPARQLQuerier();
		
		List<CaffeineSource> sources = querier.getCaffeineSources();
		
		for(CaffeineSource source: sources){
	
			//Add source to database.
			createCaffeineSource(source);
			
			if(source.hasOpeningTimes()){
				//Has opening times so get current opening times and add to database.
				
				for(OpeningTime ot : querier.getCurrentOpeningTimes(source.getId())){
					createOpeningTime(ot);
				}
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
	
	/**
	 * Method to create/update LeaderboardScore objects.
	 * 
	 * @param score (LeaderboardScore object)
	 */
	private void updateLeaderboardScore(LeaderboardScore score){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(score);
	    } finally {
	    	pm.close();
	    }
	}

	
	/**
	 * Method to update requesting user's score.
	 * 
	 * @param score (double value)
	 */
	public void updateScore(double score){
		String userId = null;
		
		try{
			userId = getUserId();
		} catch(NullPointerException e){
			//Failed to get user id so return.
			
			return;
		}
		
		LeaderboardScore userScore = getLeaderboardScore(userId);
		
		if(userScore == null){
			//Doesn't exist so make score object
			
			userScore = new LeaderboardScore(userId, score);
		} else{
			//Score object exists
			
			userScore.setScore(userScore.getScore() + score);
		}
		
		updateLeaderboardScore(userScore);		
	}
	
	
	//Get Methods
	
	
	/**
	 * Method to get all CaffeineSources.
	 * 
	 * @return List of CaffeineSource objects.
	 */
	@SuppressWarnings("unchecked")
	private List<CaffeineSource> getAllCaffeineSources(){
		
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
	 * Method to get LeaderboardScore object for the user with userId.
	 * 
	 * @param userId (String object)
	 * @return LeaderboardScore object
	 */
	@SuppressWarnings("unchecked")
	private LeaderboardScore getLeaderboardScore(String userId){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + LeaderboardScore.class.getName() 
					+ " WHERE userId == '" + userId + "'");
			
			List<LeaderboardScore> list = (List<LeaderboardScore>) query.execute();
			
			return list.size() == 0 ? null : list.get(0);
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
	private String getUserId() {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    return user.getUserId();
	}

	/**
	 * Method to get current user email.
	 * 
	 * @return String object.
	 */
	private String getUserEmail() {
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    return user.getEmail();
	}
	
	/**
	 * Method to get CaffeineSourceProducts for CaffeineProduct name
	 * 
	 * @param name (String object)
	 * @return List of CaffeineSourceProduct objects
	 */
	@SuppressWarnings("unchecked")
	public List<CaffeineSourceProduct> getCaffeineSourceProductsForCaffeineProduct(String name){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + CaffeineSourceProduct.class.getName()
					+ " WHERE name == '" + name + "'");
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
	 * Method to get expired OpeningTime objects for CaffeineSource.
	 * 
	 * @param id (String object)
	 * @return List of OpeningTime objects.
	 */
	@SuppressWarnings("unchecked")
	private List<OpeningTime> getExpiredOpeningTimesForSource(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
				
		try {
			Query query = pm.newQuery("SELECT FROM " + OpeningTime.class.getName() 
				+ " WHERE caffeineSourceId == '" + id +"'");
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
	 * Method to get LeaderboardScore object for requesting user.
	 * 
	 * @return LeaderboardScore object
	 */
	public LeaderboardScore getLeaderboardScore(){
		String userId;
		
		try{
			userId = getUserId();
		} catch(NullPointerException e){
			//Failed to get user id so return null.	
			return null;
		}
	
		LeaderboardScore score = getLeaderboardScore(userId);
		score.setPosition(getPosition(userId));
		
		return score;
	}
	
	/**
	 * Method to get leaderboard position for requesting user.
	 * 
	 * @param userId (String id)
	 * @return int value
	 */
	@SuppressWarnings("unchecked")
	private int getPosition(String userId){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + LeaderboardScore.class.getName() 
					+ " ORDER BY score DESC");
			
			List<LeaderboardScore> list = (List<LeaderboardScore>) query.execute();
			
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).getUserId().equals(userId)){
					return i + 1;
				}
			}
			
			return 0;
					
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
	
	/**
	 * Method to get top five leaderboard scores.
	 * 
	 * @return List of LeaderboardScore objects.
	 */
	@SuppressWarnings("unchecked")
	public List<LeaderboardScore> getTopFiveLeaderboardScores(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + LeaderboardScore.class.getName());
			query.setOrdering("score desc");
			query.setRange(0, 5);
			
			List<LeaderboardScore> list = (List<LeaderboardScore>) query.execute();
			
			return list.size() == 0 ? null : list;
			
	  	} catch (RuntimeException e) {
	  		System.out.println(e);
	  		throw e;
	  	} finally {
	  		pm.close();
	  	}
	}
		
	//Delete Methods
	/**
	 * Method to delete OpeningTimes objects passed.
	 * 
	 * @param times (List of OpeningTime objects)
	 */
	private void deleteOpeningTimes(List<OpeningTime> times){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for(OpeningTime time : times){
				pm.deletePersistent(pm.getObjectById(OpeningTime.class, time.getId()));
			}
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to delete all CaffeineProduct objects.
	 */
	private void deleteAllCaffeineProducts(){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for(CaffeineProduct product : getAllCaffeineProducts()){
				pm.deletePersistent(pm.getObjectById(CaffeineProduct.class, product.getName()));
			}
	    } finally {
	    	pm.close();
	    }
	}
	
	/**
	 * Method to delete CaffeineSourceProducts selected.
	 *
	 * @param products (List of CaffeineSourceProduct objects)
	 */
	private void deleteCaffeineSourceProducts(List<CaffeineSourceProduct> products){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for(CaffeineSourceProduct product : products){
				pm.deletePersistent(pm.getObjectById(CaffeineSourceProduct.class, product.getId()));
			}
	    } finally {
	    	pm.close();
	    }
	}
}
