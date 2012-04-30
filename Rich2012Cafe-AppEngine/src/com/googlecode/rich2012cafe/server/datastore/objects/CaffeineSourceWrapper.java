package com.googlecode.rich2012cafe.server.datastore.objects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class used to wrap up CaffeineSource objects and their information objects such as OpeningTime together
 * to send to phone.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class CaffeineSourceWrapper {

	@Id
	private CaffeineSource source;
	private List<OpeningTime> openingTimes;
	private List<CaffeineSourceProduct> products;
	
	public CaffeineSourceWrapper(CaffeineSource source, List<OpeningTime> openingTimes, List<CaffeineSourceProduct> products){
		this.source = source;
		this.openingTimes = openingTimes;
		this.products = products;
	}

	/**
	 * Method to get caffeine source.
	 * 
	 * @return source (CaffeineSource object)
	 */
	public CaffeineSource getSource() {
		return source;
	}
	
	/**
	 * Method to get list of opening times.
	 * 
	 * @return openingTimes (List of OpeningTime objects)
	 */
	public List<OpeningTime> getOpeningTimes() {
		return openingTimes;
	}
	
	/**
	 * Method to get list of products.
	 * 
	 * @return products (List of CaffeineSourceProduct objects)
	 */
	public List<CaffeineSourceProduct> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "CaffeineSourceWrapper [source=" + source + ", openingTimes="
				+ openingTimes + ", products=" + products + "]";
	}
}
