package com.googlecode.rich2012cafe.caffeinelevel;

import java.util.Date;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import com.googlecode.rich2012cafe.utils.Util;


/**
 * @author Craig Saunders (mrman2289@gmail.com)
 */

public class CaffeineLevelReader {
	
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
		Log.i(CaffeineLevelReader.class.getName(), json);
		if(!json.equals("")){
			try {
				
				JSONArray jsonArray = new JSONArray(json);
				Log.i(CaffeineLevelReader.class.getName(), "created object");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject o = jsonArray.getJSONObject(i);
					
					int level = o.getInt(Rich2012CafeUtil.JSON_LEVEL_FIELD);
					Long currentTime = o.getLong(Rich2012CafeUtil.JSON_DATE_FIELD);
					Date date = null;
					
					try {
						date = new Date(currentTime);
					} catch (Exception e) {
						Log.i(CaffeineLevelReader.class.getName(), e.getMessage());
					}
					
					levels.put(date, level);
				}
				
			} catch (JSONException e) {
				Log.e(CaffeineLevelReader.class.getName(), e.getMessage());
				Log.e(CaffeineLevelReader.class.getName(), e.getStackTrace().toString());
			}
		}
		
		return levels;
	}
}
