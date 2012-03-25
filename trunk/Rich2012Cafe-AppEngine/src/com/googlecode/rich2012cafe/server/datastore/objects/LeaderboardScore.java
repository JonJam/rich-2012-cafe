package com.googlecode.rich2012cafe.server.datastore.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Enitity class to represent score for a user.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class LeaderboardScore {

	@Id
	public String userId;
	public double score;
	
	public LeaderboardScore(String userId, double score){
		this.userId = userId;
		this.score = score;
	}

	/**
	 * Method to get user id.
	 * 
	 * @return userId (String object)
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Method to set user id
	 * 
	 * @param userId (String object)
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Method to get score.
	 * 
	 * @return score (double value)
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Method to set score.
	 * 
	 * @param score (double value)
	 */
	public void setScore(double score) {
		this.score = score;
	}
}