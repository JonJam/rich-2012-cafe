package com.googlecode.rich2012cafe.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.rich2012cafe.utils.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class CaffeineLevelWriter {
	
	private final static String JSON_TIME_FIELD = "time";
	private final static String JSON_LEVEL_FIELD = "level";
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private Context mContext;
	
	private SharedPreferences prefs;
	/*
	 * 
	 * [
		    {
		        "time": "STRING",
		        "level": 20
		    },
		    {
		        "time": "STRING",
		        "level": 20
		    }
		]
	 */
	
	public CaffeineLevelWriter(Context context){
		prefs = Util.getSharedPreferences(context);
		mContext = context;
	}
	
	public boolean appendToCaffeineLevels(CaffeineLevel level, String settingName) {
		
		CaffeineLevelReader reader = new CaffeineLevelReader(mContext);
		
		TreeMap<Date, Integer> caffeineLevels = reader.getCaffeineLevels(settingName);
		
		caffeineLevels.put(level.getTime(), level.getLevel());
		
		if (writeNewCaffeineLevels(caffeineLevels, settingName)) {
			return true;
		}
		
		
		return false;
				
	}
	
	public boolean writeNewCaffeineLevels(TreeMap<Date,Integer> levels, String settingName) {
		
		Iterator<Date> iterator = levels.keySet().iterator();
		
		JSONArray levelsArray = new JSONArray();
		
		Calendar cal =  Calendar.getInstance();
		
		JSONObject jsonObj;
		
		while (iterator.hasNext()) {
			
			Date entryDate = iterator.next();
			Integer entryValue = levels.get(entryDate);
			
			cal.setTime(entryDate);
			
			jsonObj = new JSONObject();
			
			try {
				
				jsonObj.accumulate("date", cal.getTimeInMillis());
				jsonObj.accumulate("level", entryValue);
				
				levelsArray.put(jsonObj);
				
			} catch (JSONException e) {
				
				Log.e("t-msg", "Failed to create JSON object");
				e.printStackTrace();
				
				return false;
			}
			
		}
		
		Editor editor = prefs.edit();
		editor.putString(settingName, levelsArray.toString());
		editor.commit();
		return true;
				
	}
}
