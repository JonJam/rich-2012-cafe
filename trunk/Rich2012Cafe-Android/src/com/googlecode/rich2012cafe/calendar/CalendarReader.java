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
 * Class to read from phone Calendar.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 *
 */
public class CalendarReader {
	
     private Uri eventUri;
     private String[] eventProjection;
     
     public CalendarReader(){
    	 eventUri = CalendarContract.Events.CONTENT_URI;
    	 eventProjection = new String[]{
    		CalendarContract.Events.CALENDAR_ID,
	 		CalendarContract.Events.TITLE,
	 		CalendarContract.Events.DESCRIPTION,
	 		CalendarContract.Events.EVENT_LOCATION,
	 		CalendarContract.Events.DTSTART, //Start time of event in UTC Milliseconds 
	 		CalendarContract.Events.DTEND //End time of event in UTC Milliseconds
	     };
     }

     /**
      * Method to get an ArrayList of today's calendar events.
      * 
      * @param activityContext (Context object)
      * @return ArrayList of CalendarEvent objects
      */
	public ArrayList<CalendarEvent> getTodaysEvents(Context activityContext){

		ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
		
		ContentResolver cr = activityContext.getContentResolver();

    	String selection = CalendarContract.Events.DTSTART + " >= " + getTodayStartTimeInMilli() + 
    			" AND " + CalendarContract.Events.DTSTART + " < " + getTommorrowStartTimeInMilli();
    	
    	//Query to select events from calendars for today.
    	Cursor eventCursor = cr.query(eventUri, eventProjection, selection, null, null);
         
        eventCursor.moveToFirst();
        while(!eventCursor.isAfterLast()){
    
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
