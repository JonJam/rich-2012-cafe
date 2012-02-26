package com.googlecode.rich2012cafe.model.database;

import java.util.Calendar;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class OpeningTime {

	private String caffeineSourceId;
	private String day;
	private String openTime;
	private String closeTime;
	private Calendar date;
	
	public OpeningTime(String caffeineSourceId, String day, String openTime, String closeTime, Calendar date){
		this.caffeineSourceId = caffeineSourceId;
		this.day = day;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.date = date;
	}

	public String getCaffeineSourceId() {
		return caffeineSourceId;
	}

	public void setCaffeineSourceId(String caffeineSourceId) {
		this.caffeineSourceId = caffeineSourceId;
	}

	public String getDay(){
		return day;
	}
	
	public String getOpenTime(){
		return openTime;
	}
	
	public String getCloseTime(){
		return closeTime;
	}
	
	public Calendar getDate(){
		return date;
	}
}
