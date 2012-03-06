package com.googlecode.rich2012cafe.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.activities.Settings;

public class UserInterfaceController extends Controller{

	public boolean optionsActions(MenuItem item, Context context){
        switch (item.getItemId()) {
        case R.id.settings:
            this.startActivity(context, Settings.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return true;
        }
        return false;
	}
	
}
