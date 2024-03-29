package com.googlecode.rich2012cafe.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

/**
 * Interface class for Rich2012Cafe Service.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@ServiceName(value = "com.googlecode.rich2012cafe.server.Rich2012CafeService", locator = "com.googlecode.rich2012cafe.server.Rich2012CafeServiceLocator")
public interface Rich2012CafeRequest extends RequestContext {

	Request<List<CaffeineSourceWrapperProxy>> getCaffeineSourcesGiven(double latitude, double longitude, 
			String phoneDayName, String phoneTime);
	Request<List<CaffeineProductProxy>> getAllCaffeineProducts();
	Request<LeaderboardScoreProxy> getLeaderboardScore();
	Request<Void> updateLeaderboardScore(double score);
	Request<List<LeaderboardScoreProxy>> getTopFiveLeaderboardScores();
}
