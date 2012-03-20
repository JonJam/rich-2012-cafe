package com.googlecode.rich2012cafe.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/*
 * GENERATED
 */
public class Rich2012CafeServiceLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
