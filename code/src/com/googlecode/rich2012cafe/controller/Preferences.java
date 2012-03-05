package com.googlecode.rich2012cafe.controller;

import java.util.List;

import com.googlecode.rich2012cafe.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Resources used:
 * 	http://developer.android.com/reference/android/preference/PreferenceActivity.html
 * 
 */
public class Preferences extends PreferenceActivity{
	 
	public static class UserSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
        
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

    	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
    		
    		if(key.equals("userPref")){
    			Log.i("TEST",sp.getString(key, ""));
    		}
    	}
    }
	
   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }
}
