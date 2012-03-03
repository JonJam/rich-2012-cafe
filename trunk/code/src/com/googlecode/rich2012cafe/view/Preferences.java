package com.googlecode.rich2012cafe.view;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	private ListPreference userTypePref;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(com.googlecode.rich2012cafe.R.xml.preferences);
        // Get the custom preference
        
        userTypePref = (ListPreference)this.getPreferenceScreen().findPreference("userPref");
        
        this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        sp.getString("userPref", "Student");
//        Preference customPref = (Preference) findPreference("prefs_screen");
//        customPref
//                        .setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//                                public boolean onPreferenceClick(Preference preference) {
//                                        return true;
//                                }
//
//                        });
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		// TODO Auto-generated method stub
		if(key.equals("userPref")){
			userTypePref.setSummary(sp.getString(key, ""));
		}
	}
}
