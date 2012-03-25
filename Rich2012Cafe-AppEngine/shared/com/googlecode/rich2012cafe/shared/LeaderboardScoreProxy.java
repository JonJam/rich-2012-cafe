package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.LeaderboardScore", locator = "com.googlecode.rich2012cafe.server.datastore.objects.LeaderboardScoreLocator")
public interface LeaderboardScoreProxy extends ValueProxy {

	String getUserId();

	void setUserId(String userId);

	double getScore();

	void setScore(double score);

}
