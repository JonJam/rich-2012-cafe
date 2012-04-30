package com.googlecode.rich2012cafe.alarm;

import com.googlecode.rich2012cafe.utils.ScheduledTasks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ScheduledService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	  @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	        // TBD
		  try{
			  ScheduledTasks.updateLeaderboard(this, false);
			  ScheduledTasks.getCaffeineProducts(this, false);
			  ScheduledTasks.uploadCurrentScore(this);
			  ScheduledTasks.clearCaffeineLevels(this);
			  
		  }catch(Exception e){
			  Log.e("Service Class", e.getMessage());
		  }
	        return Service.START_FLAG_REDELIVERY;
	    }
	 
	

}
