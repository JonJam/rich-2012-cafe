package com.googlecode.rich2012cafe.alarm;

import java.util.Calendar;
import java.util.TimeZone;

import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Class to set an alarm.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AlarmSetter {

	/**
	 * Method to set an alarm for given time and message.
	 * 
	 * @param context (Context object)
	 * @param timeToSet (Calendar object)
	 * @param message (String object)
	 */
	public static void setAlarm(Context context, Calendar timeToSet, String message){
				
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(Rich2012CafeUtil.MESSAGE_NAME, message);
		
		int requestCode = 1;
		PendingIntent sender = PendingIntent.getBroadcast(context, 
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis(), sender);
	}
	
}
