package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;


public class CaffeineSourceWrapperLocator extends
		Locator<CaffeineSourceWrapper, Void> {

	@Override
	public CaffeineSourceWrapper create(
			Class<? extends CaffeineSourceWrapper> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public CaffeineSourceWrapper find(
			Class<? extends CaffeineSourceWrapper> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<CaffeineSourceWrapper> getDomainType() {
		return CaffeineSourceWrapper.class;
	}

	@Override
	public Void getId(CaffeineSourceWrapper domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(CaffeineSourceWrapper domainObject) {
		return null;
	}

}
