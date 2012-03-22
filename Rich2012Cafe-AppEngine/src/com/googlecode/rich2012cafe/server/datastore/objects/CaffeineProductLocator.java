package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * GENERATED CLASS
 */
public class CaffeineProductLocator extends Locator<CaffeineProduct, Void> {

	@Override
	public CaffeineProduct create(Class<? extends CaffeineProduct> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public CaffeineProduct find(Class<? extends CaffeineProduct> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<CaffeineProduct> getDomainType() {
		return CaffeineProduct.class;
	}

	@Override
	public Void getId(CaffeineProduct domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(CaffeineProduct domainObject) {
		return null;
	}

}
