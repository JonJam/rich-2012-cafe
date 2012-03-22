package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * GENERATED CLASS
 */
@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct", locator = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProductLocator")
public interface CaffeineSourceProductProxy extends ValueProxy {

	String getId();

	String getCaffeineSourceId();

	void setCaffeineSourceId(String caffeineSourceId);

	String getName();

	void setName(String name);

	double getPrice();

	void setPrice(double price);

	String getCurrency();

	void setCurrency(String currency);

	String getPriceType();

	void setPriceType(String priceType);

}
