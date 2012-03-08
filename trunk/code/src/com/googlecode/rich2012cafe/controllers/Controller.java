package com.googlecode.rich2012cafe.controllers;

import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProductsDataSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSourcesDataSource;
import com.googlecode.rich2012cafe.model.database.OpeningTimesDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class Controller {

	
	public Controller(){
		
	}
	
	public void startActivity(Context context, Class<?> cls){
		context.startActivity(new Intent(context, cls));
	}
	
	public void startActivity(Context context, Class<?> cls, int... flags){
		Intent tempIntent = new Intent(context, cls);
		for(int i: flags){
			tempIntent.addFlags(i);
		}
		context.startActivity(tempIntent);
	}
	
	public AlertDialog createAlert(Activity activity, String msg){
	     AlertDialog dialog= new AlertDialog.Builder(activity).create();
	     dialog.setMessage(msg);
	     return dialog;   
	}
}
