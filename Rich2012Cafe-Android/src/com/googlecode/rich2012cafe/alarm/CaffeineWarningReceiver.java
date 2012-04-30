package com.googlecode.rich2012cafe.alarm;

import com.googlecode.rich2012cafe.utils.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class to receive caffeine warning alarms and perform appropiate action.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineWarningReceiver extends BroadcastReceiver{
	
	/**
	 * Method called on receive an alarm message.
	 */
	@Override
	public void onReceive(Context context, Intent intent){
	
		//SET BELOW TO MAP ACTIVITY
		
//				 Intent newIntent = new Intent(context, LeaderboardActivity.class);
//				 newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////				 
//				 Util.generateNotification(context, message, newIntent);
		Util.generateNotification(context, "WARN", new Intent());
			
	}
}
