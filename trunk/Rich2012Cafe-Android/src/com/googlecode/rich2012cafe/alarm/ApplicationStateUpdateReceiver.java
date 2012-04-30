package com.googlecode.rich2012cafe.alarm;

import com.googlecode.rich2012cafe.utils.Util;

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
	
		//CHANGE TO WHAT REQUIRED.
		
		Util.generateNotification(context, "STATE UPDATE", new Intent());
	}
}
