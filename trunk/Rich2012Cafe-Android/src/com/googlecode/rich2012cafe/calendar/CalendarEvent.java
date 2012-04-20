package com.googlecode.rich2012cafe.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to represent a calendar event.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CalendarEvent implements Comparable<CalendarEvent>{
	
	private String calenderId;
 	private String title;
 	private String description;
 	private String eventLocation;
	private long startTime;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private Date start, end;
	
 	private long endTime;
 	
 	public CalendarEvent(String calendarId, String title, String description, 
 			String eventLocation, long startTime, long endTime){
 		this.calenderId = calendarId;
 		this.title = title;
 		this.description = description;
 		this.eventLocation = eventLocation;
 		this.startTime = startTime;
 		this.endTime = endTime;
 		start = new Date(startTime);
 		end = new Date(endTime);
 	}
 	
 	/**
 	 * Method to get calendar id.
 	 * 
 	 * @return calendarId (String object)
 	 */
 	public String getCalenderId() {
		return calenderId;
	}

 	/**
 	 * Method to set calender id.
 	 * 
 	 * @param calenderId (String object)
 	 */
	public void setCalenderId(String calenderId) {
		this.calenderId = calenderId;
	}

	/**
	 * Method to get title.
	 * 
	 * @return title (String object)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method to set title.
	 * 
	 * @param title (String object)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method to get description.
	 * 
	 * @return description (String object)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method to set description.
	 * 
	 * @param description (String object)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method to get event location.
	 * 
	 * @return eventLocation (String object)
	 */
	public String getEventLocation() {
		return eventLocation;
	}

	/**
	 * Method to set event location.
	 * 
	 * @param eventLocation (String object)
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/**
	 * Method to get start time.
	 * 
	 * @return startTime (long value)
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Method to set start time.
	 * 
	 * @param startTime (long value)
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Method to get end time.
	 * 
	 * @return endTime (long value)
	 */
	public long getEndTime() {
		return endTime;
	}

	public Date getStartDate(){
		return start;
	}
	/**
	 * Method to set end time.
	 * 
	 * @param endTime (long value)
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String formatTime(Date date){
		return sdf.format(date);
	}
 	
	public String getDisplay(){
		return title+" @ "+formatTime(start)+" - "+formatTime(end);
	}
 	@Override
	public String toString() {
		return "CalendarEvent [calenderId=" + calenderId + ", title=" + title
				+ ", description=" + description + ", eventLocation="
				+ eventLocation + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

	@Override
	public int compareTo(CalendarEvent event) {
		// TODO Auto-generated method stub
		return this.getStartDate().compareTo(event.getStartDate());
	}
}
