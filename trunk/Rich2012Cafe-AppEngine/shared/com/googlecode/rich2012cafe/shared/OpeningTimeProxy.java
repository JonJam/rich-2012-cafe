package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.objects.OpeningTime", locator = "com.googlecode.rich2012cafe.server.objects.OpeningTimeLocator")
public interface OpeningTimeProxy extends ValueProxy {

	String getId();

	String getCaffeineSourceId();

	void setCaffeineSourceId(String caffeineSourceId);

	String getDay();

	void setDay(String day);

	String getOpeningTime();

	void setOpeningTime(String openingTime);

	String getClosingTime();

	void setClosingTime(String closingTime);

	String getValidFrom();

	void setValidFrom(String validFrom);

	String getValidTo();

	void setValidTo(String validTo);

}
