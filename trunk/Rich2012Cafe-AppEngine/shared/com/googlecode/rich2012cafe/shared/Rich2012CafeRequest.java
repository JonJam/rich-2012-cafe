package com.googlecode.rich2012cafe.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName(value = "com.googlecode.rich2012cafe.server.Rich2012CafeService", locator = "com.googlecode.rich2012cafe.server.Rich2012CafeServiceLocator")
public interface Rich2012CafeRequest extends RequestContext {

	Request<CaffeineSourceProxy> createCaffeineSource();

	Request<CaffeineSourceProxy> readCaffeineSource(Long id);

	Request<CaffeineSourceProxy> updateCaffeineSource(
			CaffeineSourceProxy caffeinesource);

	Request<Void> deleteCaffeineSource(CaffeineSourceProxy caffeinesource);

	Request<List<CaffeineSourceProxy>> queryCaffeineSources();

	Request<CaffeineProductProxy> createCaffeineProduct();

	Request<CaffeineProductProxy> readCaffeineProduct(Long id);

	Request<CaffeineProductProxy> updateCaffeineProduct(
			CaffeineProductProxy caffeineproduct);

	Request<Void> deleteCaffeineProduct(CaffeineProductProxy caffeineproduct);

	Request<List<CaffeineProductProxy>> queryCaffeineProducts();

	Request<OpeningTimeProxy> createOpeningTime();

	Request<OpeningTimeProxy> readOpeningTime(Long id);

	Request<OpeningTimeProxy> updateOpeningTime(OpeningTimeProxy openingtime);

	Request<Void> deleteOpeningTime(OpeningTimeProxy openingtime);

	Request<List<OpeningTimeProxy>> queryOpeningTimes();

}
