package com.googlecode.rich2012cafe.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

/**
 * Class to obtain events from Android Calendar.
 * 
 * N.B. This skips any all day events.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 *
 */
public class CalendarController {
	
     private Uri eventUri;
     private String[] eventProjection;
     
     public CalendarController(){
    	 eventUri = CalendarContract.Events.CONTENT_URI;
    	 eventProjection = new String[]{
    		CalendarContract.Events.CALENDAR_ID,
	 		CalendarContract.Events.TITLE,
	 		CalendarContract.Events.DESCRIPTION,
	 		CalendarContract.Events.EVENT_LOCATION,
	 		CalendarContract.Events.DTSTART, //Start time of event in UTC Milliseconds 
	 		CalendarContract.Events.DTEND, //End time of event in UTC Milliseconds
	 		CalendarContract.Events.ALL_DAY
	     };
     }
     
     /**
      * Method to get an ArrayList of today's calendar events.
      * 
      * @param activityContext (Context object)
      * @return ArrayList of CalendarEvent objects
      */
	public ArrayList<CalendarEvent> getTodaysEvents(Context activityContext){

		//Criteria to select events from calendars for today.
    	String selection = CalendarContract.Events.DTSTART + " >= " + getTodayStartTimeInMilli() + 
    			" AND " + CalendarContract.Events.DTSTART + " < " + getTommorrowStartTimeInMilli();
    	
        return createCalendarEvents(activityContext, selection);      
	}
	
	/**
     * Method to get an ArrayList of yesterday's calendar events.
     * 
     * @param activityContext (Context object)
     * @return ArrayList of CalendarEvent objects
     */
    public ArrayList<CalendarEvent> getYesterdayEvents(Context activityContext){
 		
    	//Criteria to select events from calendars for yesterday.
     	String selection = CalendarContract.Events.DTSTART + " >= " + getYesterdayStartTimeInMilli() + 
     			" AND " + CalendarContract.Events.DTSTART + " < " + getTodayStartTimeInMilli();
     	
         return createCalendarEvents(activityContext, selection);
 	}
    
    /**
     * Method to merge back to back calendar events.
     * 
     * @param eventsArray (ArrayList of CalendarEvent objects)
     * @return ArrayList of CalendarEvent objects.
     */
    public ArrayList<CalendarEvent> mergeBackToBackEvents(ArrayList<CalendarEvent> eventsArray) {
		
		long previousEndTime = 0L;
		
		ArrayList<CalendarEvent> mergedEvents = new ArrayList<CalendarEvent>();
		
		for (CalendarEvent e: eventsArray) {
			
			if (previousEndTime == e.getStartTime()) {
				mergedEvents.get(mergedEvents.size()).setEndTime(e.getEndTime());
			} else {	
				mergedEvents.add(e);
			}
			
			previousEndTime = e.getEndTime();
			
		}
		
		return mergedEvents;
		
	} 
    
    /**
     * Method to create calendar events from selection query.
     * 
     * @param activityContext (Context object)
     * @param selection (String object)
     * @return ArrayList of CalendarEvent objects
     */
    private ArrayList<CalendarEvent> createCalendarEvents(Context activityContext, String selection){
    
    	ContentResolver cr = activityContext.getContentResolver();
 		ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
 		
     	Cursor eventCursor = cr.query(eventUri, eventProjection, selection, null, null);
          
        eventCursor.moveToFirst();
        while(!eventCursor.isAfterLast()){

        	int allDay = eventCursor.getColumnIndex(CalendarContract.Events.ALL_DAY);
        	
        	if(eventCursor.getInt(allDay) == 1){
        		//Event is all day so skip.
        		
        		eventCursor.moveToNext();
        		continue;
        	}
        	
         	int calenderId = eventCursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID);
          	int title = eventCursor.getColumnIndex(CalendarContract.Events.TITLE);
          	int description = eventCursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
          	int eventLocation = eventCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
          	int dstart = eventCursor.getColumnIndex(CalendarContract.Events.DTSTART);
          	int dend = eventCursor.getColumnIndex(CalendarContract.Events.DTEND);

          	events.add(new CalendarEvent(
          			eventCursor.getString(calenderId), 
          			eventCursor.getString(title), 
          			eventCursor.getString(description),
          			eventCursor.getString(eventLocation),
          			Long.parseLong(eventCursor.getString(dstart)),
          			Long.parseLong(eventCursor.getString(dend))));
          	
         	eventCursor.moveToNext();
         }
         
         Collections.sort(events);
         
         return events;
    }

    /**
     * Method to get yesterday's starting date/time i.e. 00:00:00 in milliseconds.
     * 
     * @return long value
     */
	private long getYesterdayStartTimeInMilli(){

    	Calendar startTime = Calendar.getInstance(); 
    	startTime.add(Calendar.DATE, -1);
    	startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        
        return startTime.getTimeInMillis();
	}

	/**
	 * Method to get today's starting date/time i.e. 00:00:00 in milliseconds.
	 * 
	 * @return long value
	 */
	private long getTodayStartTimeInMilli(){

    	Calendar startTime = Calendar.getInstance(); 
    	startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        
        return startTime.getTimeInMillis();
	}
	
	/**
	 * Method to get tomorrow's starting date/time i.e. 00:00:00 in milliseconds.
	 * 
	 * @return long value
	 */
	private long getTommorrowStartTimeInMilli(){
		
		Calendar endTime = Calendar.getInstance(); 
        endTime.add(Calendar.DAY_OF_YEAR, 1);
    	endTime.set(Calendar.HOUR_OF_DAY, 0);
        endTime.set(Calendar.MINUTE, 0);
        endTime.set(Calendar.SECOND, 0);
        endTime.set(Calendar.MILLISECOND, 0);   
        
        return endTime.getTimeInMillis();
	}
}
