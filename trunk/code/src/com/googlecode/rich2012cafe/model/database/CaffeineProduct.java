package com.googlecode.rich2012cafe.model.database;

/**
 * Class to represent a caffeine product.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineProduct {
	
	//URI Identifier for a product at a caffeine source e.g. http://id.southampton.ac.uk/point-of-service/42-cafe#offering-Cappuchino-Staff
	private String id;
	//URI Identifier for a caffeine source e.g. http://id.southampton.ac.uk/vending-machine/71
	private String caffeineSourceId;
	private String name;
	private String price;
	//Determines whether Staff, Student or All product.
	private String type;
	
	public CaffeineProduct(String id, String caffeineSourceId, String name, String price, String type){
		this.id = id;
		this.caffeineSourceId = caffeineSourceId;
		this.name = name;
		this.price = price;
		this.type = type;
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
	 * @return price (String object)
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Method to set price of product.
	 * 
	 * @param price (String object)
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * Method to set type of product.
	 * 
	 * @return type (String object)
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Method to set type of product.
	 * 
	 * @param type (String object)
	 */
	public void setType(String type) {
		this.type = type;
	}
}