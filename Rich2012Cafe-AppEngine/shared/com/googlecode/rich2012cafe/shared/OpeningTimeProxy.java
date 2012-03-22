package com.googlecode.rich2012cafe.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * GENERATED CLASS
 */
@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime", locator = "com.googlecode.rich2012cafe.server.datastore.objects.OpeningTimeLocator")
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

	Date getValidFrom();

	void setValidFrom(Date validFrom);

	Date getValidTo();

	void setValidTo(Date validTo);

}
