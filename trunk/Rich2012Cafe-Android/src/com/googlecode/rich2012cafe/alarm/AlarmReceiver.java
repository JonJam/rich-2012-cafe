package com.googlecode.rich2012cafe.alarm;

import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Class to receive alarms and perform appropiate action.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AlarmReceiver extends BroadcastReceiver{
	
	/**
	 * Method called on receive an alarm message.
	 */
	@Override
	public void onReceive(Context context, Intent intent){
		try{
			Bundle bundle = intent.getExtras();
			String message = bundle.getString(Rich2012CafeUtil.MESSAGE_NAME);
			
			if(message.equals(Rich2012CafeUtil.CAFFEINE_TRACKER)){
				
				//SET BELOW FOR CAFFEINETRACKER ACTIVITY in BACKGROUND
				/*
				 * Intent newIntent = new Intent(context, .class);
				 * newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * context.startActivity(newIntent);
				 */
				
			} else if(message.equals(Rich2012CafeUtil.CAFFEINE_WARNING)){

				//SET BELOW TO MAP ACTIVITY
				
//				 Intent newIntent = new Intent(context, LeaderboardActivity.class);
//				 newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////				 
//				 Util.generateNotification(context, message, newIntent);
			} else if(message.equals("service_test")){
				Log.i("Alarm", "Starting Services");
				Intent temp = new Intent(context, ScheduledService.class);
				context.stopService(temp);
			}
		
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
