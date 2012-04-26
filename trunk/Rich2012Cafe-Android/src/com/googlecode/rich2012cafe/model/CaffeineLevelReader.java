package com.googlecode.rich2012cafe.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.rich2012cafe.utils.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class CaffeineLevelReader {
	
	private final static String JSON_TIME_FIELD = "time";
	private final static String JSON_LEVEL_FIELD = "level";
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
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
	
	public CaffeineLevelReader(Context context){
		prefs = Util.getSharedPreferences(context);
	}
	
	
	
	public TreeMap<Date, Integer> getCaffeineLevels(String settingName){
		
		String json = prefs.getString(settingName, "");
		TreeMap<Date, Integer> levels = new TreeMap<Date, Integer>();
		Log.i("caffeinereader", json);
		if(!json.equals("")){
			try {
				
				JSONArray jsonArray = new JSONArray(json);
				Log.i("json", "created object");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject o = jsonArray.getJSONObject(i);
					
					int level = o.getInt("level");
					Long currentTime = o.getLong("date");
					SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
					Date date = null;
					
					try {
						date = new Date(currentTime);
					} catch (Exception e) {
						Log.i("t-msg", e.getMessage());
					}
					
					levels.put(date, level);
				}
				
			} catch (JSONException e) {
				Log.e("josn error", e.getMessage());
			}
		}
		
		return levels;
	}
}
