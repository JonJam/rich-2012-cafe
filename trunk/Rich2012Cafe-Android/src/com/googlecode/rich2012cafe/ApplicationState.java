package com.googlecode.rich2012cafe;

import java.util.List;

import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;

import android.app.Application;

public class ApplicationState extends Application{

	private double score;
	private List<LeaderboardScoreProxy> leaderboard;
	
	public void setScore(double score){
		this.score = score;
	}
	
	public void setLeaderboard(List<LeaderboardScoreProxy> leaderboard){
		this.leaderboard=leaderboard;
	}
	
	public List<LeaderboardScoreProxy> getLeaderboard(){
		return leaderboard;
	}
	
	public double getScore(){
		return score;
	}
}
