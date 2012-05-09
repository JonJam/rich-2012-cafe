package com.googlecode.rich2012cafe;

import java.util.List;

import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;

import android.app.Application;

/**
 * @author Pratik Patel (p300ss@gmail.com)
 */

public class ApplicationState extends Application{

	private double score;
	private List<LeaderboardScoreProxy> leaderboard;
	private List<CaffeineProductProxy> caffeineProducts;
	
	public void setScore(double score){
		this.score = score;
	}
	
	public void setLeaderboard(List<LeaderboardScoreProxy> leaderboard){
		this.leaderboard=leaderboard;
	}
	
	public void setCaffeineProducts(List<CaffeineProductProxy> products){
		this.caffeineProducts = products;
	}
	
	public List<CaffeineProductProxy> getCaffeineProducts(){
		return caffeineProducts;
	}
	
	public List<LeaderboardScoreProxy> getLeaderboard(){
		return leaderboard;
	}
	
	public double getScore(){
		return score;
	}
}
