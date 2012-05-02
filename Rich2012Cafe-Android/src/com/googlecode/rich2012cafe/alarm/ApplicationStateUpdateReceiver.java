package com.googlecode.rich2012cafe.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class to receive application state update alarm and perform appropiate action.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class ApplicationStateUpdateReceiver extends BroadcastReceiver{
	
	/**
	 * Method called on receive an alarm message.
	 */
	@Override
	public void onReceive(Context context, Intent intent){

		context.startService(new Intent(context, ScheduledService.class));
	}
}
