package com.googlecode.rich2012cafe.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName(value = "com.googlecode.rich2012cafe.server.Rich2012CafeService", locator = "com.googlecode.rich2012cafe.server.Rich2012CafeServiceLocator")
public interface Rich2012CafeRequest extends RequestContext {

	Request<Void> updateDataStore();
}
