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
	private String userId;
	private double score;
	private int position;
	
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
	
	/**
	 * Method to get position.
	 * 
	 * @return position (int value)
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Method to set position.
	 * 
	 * @param position (int value)
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * Method to get String representation of LeaderboardScore object.
	 * 
	 * @return String object
	 */
	@Override
	public String toString() {
		return "LeaderboardScore [userId=" + userId + ", score=" + score
				+ ", position=" + position + "]";
	}
}