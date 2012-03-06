package com.googlecode.rich2012cafe.activities;

import java.util.List;

import com.googlecode.rich2012cafe.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
/**
 * Resources used:
 * 	http://developer.android.com/reference/android/preference/PreferenceActivity.html
 * 
 * @author Pratik Patel (p300ss@gmail.com), Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Preferences extends PreferenceActivity{
	 
	public static class UserSettingsFragment extends PreferenceFragment{
        
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.user_settings);
        }
    }
	
	public static class SourceSettingsFragment extends PreferenceFragment{
        
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.source_settings);
    	}
    }
	
	public static class ProductSettingsFragment extends PreferenceFragment{
        
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.product_settings);
    	}
    }
	
	public static class ProductsToViewFragment extends PreferenceFragment{
		
		 public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.products_to_view_settings);
    	}
	}
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }
}
