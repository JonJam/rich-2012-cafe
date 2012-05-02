package com.googlecode.rich2012cafe.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class to receive caffeine tracker alarms and perform appropiate action.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineTrackerReceiver extends BroadcastReceiver{
	
	/**
	 * Method called on receive an alarm message.
	 */
	@Override
	public void onReceive(Context context, Intent intent){
		
		//SET BELOW FOR CAFFEINETRACKER SERVICE
		//context.startService(new Intent(context, .class));
	}
}
