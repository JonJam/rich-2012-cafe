package com.googlecode.rich2012cafe.model.database;

/**
 * Class to represent a favourite caffeine product.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class FavouriteCaffeineProduct {
	
	private String name;
	
	public FavouriteCaffeineProduct(String name){
		this.name = name;
	}

	/**
	 * Method to get name of favourite product.
	 * 
	 * @return String object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of favourite product.
	 * 
	 * @param name (String object)
	 */
	public void setName(String name) {
		this.name = name;
	}
}
