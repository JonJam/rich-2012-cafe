package com.googlecode.rich2012cafe.model.database;

/**
 * Class to represent a favourite caffeine source. 
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class FavouriteCaffeineSource {
	
	private String id;
	
	public FavouriteCaffeineSource(String id){
		this.id = id;
	}

	/**
	 * Method to get id of favourite caffeine source.
	 * 
	 * @return id (String object)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set id of favourite caffeine source.
	 * 
	 * @param id (String object)
	 */
	public void setId(String id) {
		this.id = id;
	}
}
