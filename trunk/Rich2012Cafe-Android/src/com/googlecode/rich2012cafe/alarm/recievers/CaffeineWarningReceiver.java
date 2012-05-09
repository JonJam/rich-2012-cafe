package com.googlecode.rich2012cafe.alarm.recievers;

import com.googlecode.rich2012cafe.activities.GMapActivity;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
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
	
		Intent newIntent = new Intent(context, GMapActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				 
		
		Util.generateNotification(context, Rich2012CafeUtil.CAFFEINE_WARNING_MESSAGE, newIntent);	
	}
}
