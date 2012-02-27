package com.googlecode.rich2012cafe.model.database;

/**
 * Class to represent an opening time.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class OpeningTime {

	//URI Identifier for a caffeine source e.g. http://id.southampton.ac.uk/vending-machine/71
	private String caffeineSourceId;
	private String day;
	private String openingTime;
	private String closingTime;
	
	public OpeningTime(String caffeineSourceId, String day, String openingTime, String closingTime){
		this.caffeineSourceId = caffeineSourceId;
		this.day = day;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
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
}
