package com.googlecode.rich2012cafe.server.datastore.objects;

import com.google.web.bindery.requestfactory.shared.Locator;


public class LeaderboardScoreLocator extends Locator<LeaderboardScore, Void> {

	@Override
	public LeaderboardScore create(Class<? extends LeaderboardScore> clazz) {
		// TODO no default constructor, creation code cannot be generated
		throw new RuntimeException(String.format("Cannot instantiate %s",
				clazz.getCanonicalName()));
	}

	@Override
	public LeaderboardScore find(Class<? extends LeaderboardScore> clazz,
			Void id) {
		return create(clazz);
	}

	@Override
	public Class<LeaderboardScore> getDomainType() {
		return LeaderboardScore.class;
	}

	@Override
	public Void getId(LeaderboardScore domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(LeaderboardScore domainObject) {
		return null;
	}

}
