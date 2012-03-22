package com.googlecode.rich2012cafe.server.datastore.objects;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class to represent an opening time for a caffeine source.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class OpeningTime {
	
	//URI Identifier for an opening time e.g. http://id.southampton.ac.uk/point-of-service/6-bar-food-performance-nights#Monday-1800-1900-2011-01-03
	@Id
	private String id;
	//URI Identifier for a caffeine source e.g. http://id.southampton.ac.uk/vending-machine/71
	private String caffeineSourceId;
	private String day;
	private String openingTime;
	private String closingTime;
	private Date validFrom;
	private Date validTo;
	
	public OpeningTime(String id, String caffeineSourceId, String day, String openingTime, String closingTime, Date validFrom, Date validTo){
		this.id = id;
		this.caffeineSourceId = caffeineSourceId;
		this.day = day;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	/**
	 * Method to get id.
	 * 
	 * @return id (String object)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to set id.
	 * 
	 * @param id (String object)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Method to get caffeine source id
	 * 
	 * @return caffeineSourceId (String object)
	 */
	public String getCaffeineSourceId() {
		return caffeineSourceId;
	}

	/**
	 * Method to set caffeine source id
	 * 
	 * @param caffeineSourceId (String object)
	 */
	public void setCaffeineSourceId(String caffeineSourceId) {
		this.caffeineSourceId = caffeineSourceId;
	}

	/**
	 * Method to get day
	 * 
	 * @return day(String object)
	 */
	public String getDay(){
		return day;
	}
	
	/**
	 * Method to set day.
	 * 
	 * @param day (String object)
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Method to get opening time
	 * 
	 * @return openingTime (String object)
	 */
	public String getOpeningTime(){
		return openingTime;
	}
	
	/**
	 * Method to set opening time.
	 * 
	 * @param openingTime (String object)
	 */
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	
	/**
	 * Method to get closing time
	 * 
	 * @return closingTime (String object)
	 */
	public String getClosingTime(){
		return closingTime;
	}
	
	/**
	 * Method to set closing time.
	 * 
	 * @param closingTime (String object)
	 */
	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	
	/**
	 * Method to get valid from date.
	 * 
	 * @return validFrom (Date object)
	 */
	public Date getValidFrom() {
		return validFrom;
	}

	/**
	 * Method to set valid from date
	 * 
	 * @param validFrom (Date object)
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * Method to get valid to date.
	 * 
	 * @return validTo (Date object)
	 */
	public Date getValidTo() {
		return validTo;
	}

	/**
	 * Method to set valid to date.
	 * 
	 * @param validTo (Date object)
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
	/**
	 * Method to get String representation of a CaffeineSource.
	 * 
	 * @return String value
	 */
	public String toString(){
		return "id: " + id + " caffeineSourceId: " + caffeineSourceId + " day: " + day + " openingTime: " + openingTime
				+ " closingTime: " + closingTime + " validFrom: " + validFrom + " validTo: " + validTo;
	}
}
