package com.googlecode.rich2012cafe.alarm;

import java.util.Calendar;

import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Class to control alarms used in application.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AlarmController {
	
	private static int caffeineTrackerAlarmId = 0;
	private static int caffeineWarningAlarmId = 0;

	/**
	 * Method to set caffeine tracker alarm.
	 * 
	 * @param context (Context object)
	 * @param timeToSet (Calendar object)
	 */
	public static void setCaffeineTrackerAlarm(Context context, Calendar timeToSet){
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis(), createCaffeineTrackerPendingIntent(context, caffeineTrackerAlarmId));
		caffeineTrackerAlarmId++;
	}
	
	/**
	 * Method to set caffeine warning alarm.
	 * 
	 * @param context (Context object)
	 * @param timeToSet (Calendar object)
	 */
	public static void setCaffeineWarningAlarm(Context context, Calendar timeToSet){
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis(), createCaffeineWarningPendingIntent(context, caffeineWarningAlarmId));
		caffeineWarningAlarmId++;
	}
	
	/**
	 * Method to set application state update alarm.
	 * 
	 * @param context (Context object)
	 */
	public static void setApplicationStateUpdateAlarm(Context context){
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Intent intent = new Intent(context, ApplicationStateUpdateReceiver.class);
		
		PendingIntent sender = PendingIntent.getBroadcast(context, Rich2012CafeUtil.APPLICATION_STATE_UPDATE_ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		
	}
	
	/**
	 * Method to set clear all caffeine tracker and warning alarms.
	 * 
	 * @param context (Context object)
	 */
	public static void clearAllAlarms(Context context){
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			
		//Cancelling Caffeine Tracker alarms
		for(int i = 0; i < caffeineTrackerAlarmId; i++){
			am.cancel(createCaffeineTrackerPendingIntent(context, i));
		}
		
		//Cancelling Caffeine Warning alarms
		for(int i = 0; i < caffeineWarningAlarmId; i++){
			am.cancel(createCaffeineWarningPendingIntent(context, i));
		}
				
		caffeineTrackerAlarmId = 0;
		caffeineWarningAlarmId = 0;
	}
	
	/**
	 * Method to create a caffeine tracker pending intent.
	 * 
	 * @param context (Context object)
	 * @param id (int value)
	 * @return PendingIntent object
	 */
	private static PendingIntent createCaffeineTrackerPendingIntent(Context context, int id){
		
		Intent intent = new Intent(context, CaffeineTrackerReceiver.class);
		
		PendingIntent sender = PendingIntent.getBroadcast(context, 
				id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
		return sender;
	}
	
	/**
	 * Method to create a caffeine warning pending intent.
	 * 
	 * @param context (Context object)
	 * @param id (int value)
	 * @return PendingIntent object
	 */
	private static PendingIntent createCaffeineWarningPendingIntent(Context context, int id){

		Intent intent = new Intent(context, CaffeineWarningReceiver.class);
		
		PendingIntent sender = PendingIntent.getBroadcast(context, 
				id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		return sender;
	}
}
