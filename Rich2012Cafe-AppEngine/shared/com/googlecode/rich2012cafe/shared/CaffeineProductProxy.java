package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct", locator = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProductLocator")
public interface CaffeineProductProxy extends ValueProxy {

	String getName();

	void setName(String name);

	String getProductType();

	void setProductType(String productType);

	double getCaffeineContent();

	void setCaffeineContent(double caffeineContent);

}
