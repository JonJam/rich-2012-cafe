package com.googlecode.rich2012cafe.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.CalendarAlerts;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controllers.DataController;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

public class JonText extends Activity implements OnClickListener, HomeViewInterface{

	private DataController controller;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        tv = (TextView) findViewById(R.id.textview2);
        
        controller = new DataController(this);
        
        /*
         * http://stackoverflow.com/questions/7859005/how-to-read-and-edit-android-calendar-events-using-the-new-android-4-0-ice-cream
         * http://developer.android.com/guide/topics/providers/calendar-provider.html
         * http://developer.android.com/reference/android/provider/CalendarContract.Calendars.html
         */
        
        String text = "";
       
        ContentResolver cr = getContentResolver();
        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        String[] calendarProjection = new String[] {
               CalendarContract.Calendars._ID,
               CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };
        
        Uri eventUri = CalendarContract.Events.CONTENT_URI;
        String[] eventProjection = new String[]{
        		CalendarContract.Events.TITLE, //Event Title
        		CalendarContract.Events.DESCRIPTION, //Event Description
        		CalendarContract.Events.DTSTART, //Start time of event in UTC Milliseconds 
        		CalendarContract.Events.DTEND //End time of event in UTC Milliseconds
        };
        
        
        Cursor calendarCursor = cr.query(calendarUri, calendarProjection, null, null, null);
        
        calendarCursor.moveToFirst();
        while(!calendarCursor.isAfterLast()){
        	
        	int id = calendarCursor.getColumnIndex(CalendarContract.Calendars._ID);
        	int name = calendarCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME);
        	
        	text += calendarCursor.getString(id) + " " + calendarCursor.getString(name) +"\n\n";
        
        	//Time frame for today
        	Calendar startTime = Calendar.getInstance(); //Today from 00:00:00
        	startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.SECOND, 0);
            startTime.set(Calendar.MILLISECOND, 0);
   
            Calendar endTime = Calendar.getInstance(); //Tomorrow 00:00:00
            endTime.add(Calendar.DAY_OF_YEAR, 1);
        	endTime.set(Calendar.HOUR_OF_DAY, 0);
            endTime.set(Calendar.MINUTE, 0);
            endTime.set(Calendar.SECOND, 0);
            endTime.set(Calendar.MILLISECOND, 0);            
        	
        	String selection = CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + 
        			" AND " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis();
        	
        	//Query to select events from calendars for today.
        	Cursor eventCursor = cr.query(eventUri, eventProjection, selection, null, null);
             
            eventCursor.moveToFirst();
            while(!eventCursor.isAfterLast()){
        
             	int title = eventCursor.getColumnIndex(CalendarContract.Events.TITLE);
             	int description = eventCursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
             	
             	text += eventCursor.getString(title) + " " + eventCursor.getString(description) +"\n\n";
            	 
            	eventCursor.moveToNext();
            }
        	
        	calendarCursor.moveToNext();
        }
        
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(text);
    }
    
//	protected void onResume() {
//		controller.getAppDataStore().openDataSourceConnections();
//		super.onResume();
//	}
//
//	protected void onPause() {
//		controller.getAppDataStore().closeDataSourceConnections();
//		super.onPause();
//	}
	    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextView getTextView() {
		// TODO Auto-generated method stub
		return tv;
	}
}
