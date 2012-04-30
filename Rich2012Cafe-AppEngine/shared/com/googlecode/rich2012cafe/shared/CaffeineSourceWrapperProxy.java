package com.googlecode.rich2012cafe.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceWrapper", locator = "com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceWrapperLocator")
public interface CaffeineSourceWrapperProxy extends ValueProxy {

	CaffeineSourceProxy getSource();

	List<OpeningTimeProxy> getOpeningTimes();

	List<CaffeineSourceProductProxy> getProducts();

}
