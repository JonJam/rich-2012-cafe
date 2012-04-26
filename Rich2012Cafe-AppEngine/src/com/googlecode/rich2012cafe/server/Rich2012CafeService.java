package com.googlecode.rich2012cafe.server;

import java.util.List;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.LeaderboardScore;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;

/**
 * This class forms the Rich2012Cafe Service and contains all the RPC methods.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Rich2012CafeService {

	private static DataStore db = new DataStore();
		
	/**
	 * Method to get CaffeineSources given latitude and longitude position.
	 * 
	 * 
	 * @return List of CaffeineSource objects.
	 */
	@ServiceMethod
	public List<CaffeineSource> getCaffeineSourcesGiven(double latitude, double longitude){
		return db.getCaffeineSourcesGiven(latitude, longitude);
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
	 * Method to get leaderboard score for user requesting.
	 * 
	 * @return LeaderboardScore object.
	 */
	@ServiceMethod
	public LeaderboardScore getLeaderboardScore(){
		return db.getLeaderboardScore();
	}
	
	/**
	 * Method to update score of user.
	 * 
	 * @param score (double value)
	 */
	@ServiceMethod
	public void updateLeaderboardScore(double score){
		db.updateScore(score);
	}
	
	/**
	 * Method to get top five leaderboard scores.
	 * 
	 * @return List of LeaderboardScore objects
	 */
	@ServiceMethod
	public List<LeaderboardScore> getTopFiveLeaderboardScores(){
		return db.getTopFiveLeaderboardScores();
	}
}
