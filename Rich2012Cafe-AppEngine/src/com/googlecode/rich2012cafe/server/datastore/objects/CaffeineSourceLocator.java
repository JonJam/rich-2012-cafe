package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;

/*
 * GENERATED
 */
public class CaffeineSourceLocator extends Locator<CaffeineSource, Void> {

	@Override
	public CaffeineSource create(Class<? extends CaffeineSource> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public CaffeineSource find(Class<? extends CaffeineSource> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<CaffeineSource> getDomainType() {
		return CaffeineSource.class;
	}

	@Override
	public Void getId(CaffeineSource domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(CaffeineSource domainObject) {
		return null;
	}

}
