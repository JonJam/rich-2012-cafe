package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * GENERATED CLASS
 */
public class OpeningTimeLocator extends Locator<OpeningTime, Void> {

	@Override
	public OpeningTime create(Class<? extends OpeningTime> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public OpeningTime find(Class<? extends OpeningTime> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<OpeningTime> getDomainType() {
		return OpeningTime.class;
	}

	@Override
	public Void getId(OpeningTime domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(OpeningTime domainObject) {
		return null;
	}

}
