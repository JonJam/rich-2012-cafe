package com.googlecode.rich2012cafe.activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.googlecode.rich2012cafe.R;

/**
 * @author Pratik Patel (p300ss@gmail.com)
 */

public class PreferencesFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}