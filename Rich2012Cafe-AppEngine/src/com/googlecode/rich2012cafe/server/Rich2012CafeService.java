package com.googlecode.rich2012cafe.server;

import com.googlecode.rich2012cafe.annotation.ServiceMethod;
import com.googlecode.rich2012cafe.server.datastore.DataStore;

/*
 * Resources:
 * 	http://code.google.com/eclipse/docs/appeng_android_add_rpc.html
 *  http://bradabrams.com/2011/05/google-io-session-overview-android-app-engine-a-developers-dream-combination/
 */
public class Rich2012CafeService {

	static DataStore db = new DataStore();
	
	/*
	 * METHOD THAT WILL BE USED TO GET SOURCES GIVEN LOCATION AND SETTINGS INFO 
	 * 
	 * CHANGE AS NEED
	 */
	@ServiceMethod
	public void getCaffeineSources(){
		
	}
	
	/*
	 * METHOD THAT WILL BE USED BY SCHEDULED JOB TO UPDATE DATASTORE.
	 * 
	 * CHANGE AS NEED
	 */
	@ServiceMethod
	public void updateDataStore(){
		
	}
}
