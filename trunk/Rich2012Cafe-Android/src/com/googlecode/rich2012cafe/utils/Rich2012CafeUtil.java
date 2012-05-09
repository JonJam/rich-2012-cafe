package com.googlecode.rich2012cafe.utils;

import android.util.Log;

/**
 * Class to contain all static variables used throughout project.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Rich2012CafeUtil {

	public final static String HISTORIC_VALUES_SETTING_NAME = "historicCaffeineValues";
	public final static String PROJECTED_VALUES_SETTING_NAME = "projectedCaffeineValues";
	public final static String ADHOC_DRINKS_SETTING_NAME = "adhocDrinkCaffeineValues";
	
	
	//Variables for CaffeineLevelReader
	public final static String JSON_DATE_FIELD = "date";
	public final static String JSON_LEVEL_FIELD = "level";

	//Variables for CaffeineTrackerService
	public final static int HALF_LIFE = 240;
	public final static int CAFFEINE_BUFFER = 10;
	
	//Variables used for Alarms.
	public final static int OPTIMAL_CAFFEINE_UPPER_LIMIT = 250;
	public final static int OPTIMAL_CAFFEINE_LOWER_LIMIT = 175;
	public final static int APPLICATION_STATE_UPDATE_ALARM_ID = 19568;
	public final static int CAFFEINE_TRACKER_ALARM_ID = 32767;
	public final static int CAFFEINE_TRACKER_ALARM_REPEAT = 3600000; //Every hour
	public final static String CAFFEINE_WARNING_MESSAGE = "Caffeine consumption required...";
	
	//Database time format
	public static final String DB_TIME_FORMAT = "HH:mm:ss";
	
	//Days of week
	public static final String[] DAY_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
}
