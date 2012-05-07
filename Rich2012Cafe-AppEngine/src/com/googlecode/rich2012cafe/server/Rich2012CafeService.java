package com.googlecode.rich2012cafe.server;

import java.util.List;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceWrapper;
import com.googlecode.rich2012cafe.server.datastore.objects.LeaderboardScore;

/**
 * This class forms the Rich2012Cafe Service and contains all the RPC methods.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Rich2012CafeService {

	private static DataStore db = new DataStore();
		
	/**
	 * Method get caffeine sources and their information given:
	 *  - Latitude and longitude position.
	 *  - Whether sources are currently open.
	 * 
	 * @param latitude (double value)
	 * @param longitude (double value)
	 * @param phoneDayName (String object)
	 * @param phoneTime (String object)
	 * @return List of CaffeineSourceWrapper object
	 */
	@ServiceMethod
	public List<CaffeineSourceWrapper> getCaffeineSourcesGiven(double latitude, double longitude, 
			String phoneDayName, String phoneTime){
		return db.getCaffeineSourcesGiven(latitude, longitude, phoneDayName, phoneTime);
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
