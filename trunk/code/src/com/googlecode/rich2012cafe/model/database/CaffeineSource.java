package com.googlecode.rich2012cafe.model.database;

/**
 * Class to represent a caffeine source.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineSource {

	//URI Identifier e.g. http://id.southampton.ac.uk/vending-machine/71
	private String id;
	//Name of source
	private String name;
	//Building number 
	private String buildingNumber;
	//Building name
	private String buildingName;
	//Building latitude
	private double buildingLat;
	//Building longitude
	private double buildingLong;
	//Source type
	private String type;

	public CaffeineSource(String id, String name, String buildingNumber, String buildingName, double buildingLat, double buildingLong, String type){
		this.id = id;
		this.name = name;
		this.buildingNumber = buildingNumber;
		this.buildingName = buildingName;
		this.buildingLat = buildingLat;
		this.buildingLong = buildingLong;
		this.type = type;
	}
	
	/**
	 * Method to get id of caffeine source.
	 * 
	 * @return id (String object)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set id of caffeine source.
	 * 
	 * @param id (String object)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to get name of caffeine source.
	 * 
	 * @return name (String object)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of caffeine source.
	 * 
	 * @param name (String object)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to get building number of caffeine source.
	 * 
	 * @return buildingNumber (String)
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}

	/**
	 * Method to set building number of caffeine source.
	 * 
	 * @param buildingNumber (String)
	 */
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	/**
	 * Method to get building name of caffeine source.
	 * 
	 * @return buildingName (String object)
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * Method to get building name of caffeine source.
	 * 
	 * @param buildingName (String object)
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	/**
	 * Method to get building latitude of caffeine source.
	 * 
	 * @return buildingLat (double object)
	 */
	public double getBuildingLat() {
		return buildingLat;
	}

	/**
	 * Method to set building latitude of caffeine source.
	 * 
	 * @param buildingLat (double object)
	 */
	public void setBuildingLat(double buildingLat) {
		this.buildingLat = buildingLat;
	}

	/**
	 * Method to get building longitude of caffeine source.
	 * 
	 * @return buildingLong (String object)
	 */
	public double getBuildingLong() {
		return buildingLong;
	}

	/**
	 * Method to set buildingLong of caffeine source.
	 * 
	 * @param buildingLong (double object)
	 */
	public void setBuildingLong(double buildingLong) {
		this.buildingLong = buildingLong;
	}	
	
	/*
	 * Method to get type of caffeine source
	 * 
	 * @return type (String object)
	 */
	public String getType() {
		return type;
	}

	/*
	 * Method to set type of caffeine source
	 * 
	 * @param type (String object)
	 */
	public void setType(String type) {
		this.type = type;
	}
}