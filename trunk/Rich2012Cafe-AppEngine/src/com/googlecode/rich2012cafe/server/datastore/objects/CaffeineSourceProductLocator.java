package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * GENERATED CLASS
 */
public class CaffeineSourceProductLocator extends
		Locator<CaffeineSourceProduct, Void> {

	@Override
	public CaffeineSourceProduct create(
			Class<? extends CaffeineSourceProduct> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public CaffeineSourceProduct find(
			Class<? extends CaffeineSourceProduct> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<CaffeineSourceProduct> getDomainType() {
		return CaffeineSourceProduct.class;
	}

	@Override
	public Void getId(CaffeineSourceProduct domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(CaffeineSourceProduct domainObject) {
		return null;
	}

}
