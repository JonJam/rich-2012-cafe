package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource", locator = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceLocator")
public interface CaffeineSourceProxy extends ValueProxy {

	String getId();

	String getName();

	void setName(String name);

	String getBuildingNumber();

	void setBuildingNumber(String buildingNumber);

	String getBuildingName();

	void setBuildingName(String buildingName);

	double getBuildingLat();

	void setBuildingLat(double buildingLat);

	double getBuildingLong();

	void setBuildingLong(double buildingLong);

	String getType();

	void setType(String type);

	int getOffCampus();

	void setOffCampus(int offCampus);

	boolean hasOpeningTimes();

	void setHasOpeningTimes(boolean hasOpeningTimes);

}
