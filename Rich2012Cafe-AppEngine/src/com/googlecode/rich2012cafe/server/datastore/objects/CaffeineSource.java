package com.googlecode.rich2012cafe.server.datastore.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class to represent a caffeine source.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class CaffeineSource {

	//URI Identifier e.g. http://id.southampton.ac.uk/vending-machine/71
	@Id
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
	//Whether off(1) or on(0) campus
	private int offCampus;
	//Boolean to set whether source has opening times.
	private boolean hasOpeningTimes;

	public CaffeineSource(String id, String name, String buildingNumber, String buildingName, 
			double buildingLat, double buildingLong, String type, int offCampus){
		this.id = id;
		this.name = name;
		this.buildingNumber = buildingNumber;
		this.buildingName = buildingName;
		this.buildingLat = buildingLat;
		this.buildingLong = buildingLong;
		this.type = type;
		this.offCampus = offCampus;
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
	
	/**
	 * Method to get type of caffeine source
	 * 
	 * @return type (String object)
	 */
	public String getType() {
		return type;
	}

	/**
	 * Method to set type of caffeine source
	 * 
	 * @param type (String object)
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Method to get offCampus value of caffeine source.
	 * 
	 * @return offCampus (int value)
	 */
	public int getOffCampus() {
		return offCampus;
	}

	/**
	 * Method to set whether caffeine source off campus.
	 * 
	 * @param offCampus (int value)
	 */
	public void setOffCampus(int offCampus) {
		this.offCampus = offCampus;
	}
	
	/**
	 * Method to get hasOpeningTimes.
	 * 
	 * @return hasOpeningTimes (boolean value)
	 */
	public boolean hasOpeningTimes() {
		return hasOpeningTimes;
	}

	/**
	 * Method to set hasOpeningTimes.
	 * 
	 * @param hasOpeningTimes (boolean value)
	 */
	public void setHasOpeningTimes(boolean hasOpeningTimes) {
		this.hasOpeningTimes = hasOpeningTimes;
	}
	
	/**
	 * Method to get String representation of a CaffeineSource.
	 * 
	 * @return String value
	 */
	public String toString() {
		return "CaffeineSource [id=" + id + ", name=" + name
				+ ", buildingNumber=" + buildingNumber + ", buildingName="
				+ buildingName + ", buildingLat=" + buildingLat
				+ ", buildingLong=" + buildingLong + ", type=" + type
				+ ", offCampus=" + offCampus + ", hasOpeningTimes="
				+ hasOpeningTimes + "]";
	}
}
