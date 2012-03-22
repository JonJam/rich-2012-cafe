package com.googlecode.rich2012cafe.server.datastore.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class to represent an instance of a caffeine product at a caffeine source.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class CaffeineSourceProduct {
	
	//URI Identifier for a product at a caffeine source e.g. http://id.southampton.ac.uk/point-of-service/42-cafe#offering-Cappuchino-Staff
	@Id
	private String id;
	//URI Identifier for a caffeine source e.g. http://id.southampton.ac.uk/vending-machine/71
	private String caffeineSourceId;
	//Id of CaffineProduct object
	private String name;
	private double price;
	private String currency;
	//Determines whether Staff, Student or All price.
	private String priceType;
	
	public CaffeineSourceProduct(String id, String caffeineSourceId, String name, double price, String currency, 
			String priceType){
		this.id = id;
		this.caffeineSourceId = caffeineSourceId;
		this.name = name;
		this.price = price;
		this.currency = currency;
		this.priceType = priceType;
	}

	/**
	 * Method to get id of product.
	 * 
	 * @return id (String object)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set id of product.
	 * 
	 * @param id (String object)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Method to get id of caffeine source.
	 * 
	 * @return caffeineSourceId (String object)
	 */
	public String getCaffeineSourceId() {
		return caffeineSourceId;
	}

	/**
	 * Method to set id of caffeine source.
	 * 
	 * @param caffeineSourceId (String object)
	 */
	public void setCaffeineSourceId(String caffeineSourceId) {
		this.caffeineSourceId = caffeineSourceId;
	}

	/**
	 * Method to get name of product.
	 * 
	 * @return name (String object)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of product.
	 * 
	 * @param name (String object)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to get price of product.
	 * 
	 * @return price (double object)
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Method to set price of product.
	 * 
	 * @param price (double object)
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Method to get currency of product.
	 * 
	 * @return currency (String object)
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Method to set currency of product.
	 * 
	 * @param currency (String object)
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**
	 * Method to get price type of product.
	 * 
	 * @return type (String object)
	 */
	public String getPriceType() {
		return priceType;
	}
	
	/**
	 * Method to set price type of product.
	 * 
	 * @param priceType (String object)
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	/**
	 * Method to get String representation of a CaffeineSource.
	 * 
	 * @return String value
	 */
	public String toString(){
		return "id: " + id + " caffeineSourceId: " + caffeineSourceId + " name: " + name 
				+ " price: " + price + " currency: " + currency + " priceType: " + priceType;
	}
}
