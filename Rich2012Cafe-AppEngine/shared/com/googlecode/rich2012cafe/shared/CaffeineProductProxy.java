package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct", locator = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProductLocator")
public interface CaffeineProductProxy extends ValueProxy {

	String getId();

	String getCaffeineSourceId();

	void setCaffeineSourceId(String caffeineSourceId);

	String getName();

	void setName(String name);

	double getPrice();

	void setPrice(double price);

	String getCurrency();

	void setCurrency(String currency);

	String getProductType();

	void setProductType(String productType);

	String getPriceType();

	void setPriceType(String priceType);

}
