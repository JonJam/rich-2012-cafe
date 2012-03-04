package com.googlecode.rich2012cafe.controller;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.model.*;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;

import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Resources used:
 * 	- http://www.vogella.de/articles/AndroidPerformance/article.html
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppController {//extends Controller<HomeViewInterface> {
	
	private AppDataStore ds;
	
	public AppController(AppDataStore ds) {
        this.ds = ds;
	}
	
	/**
	 * Asynchronous task to perform database check which may involve network activity.
	 * 
	 * @author Jonathan Harrison (jonjam1990@googlemail.com)
	 */
	private class DatabaseCheck extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected Void doInBackground(Void... params) {
			ds.performDatabaseCheck();
			return null;
		}
	}
	
	/**
	 * Method to perform check on the database to ensure it contains data or doesn't need updating.
	 * 
	 */
	public void performDatabaseCheck(){
		DatabaseCheck check = new DatabaseCheck();
		check.execute();
	}
	
	/**
	 * Method to open the data source connections,
	 */
	public void openDataSourceConnections(){
		ds.openDataSourceConnections();
	}
	
	/**
	 * Method to close the data source connections.
	 */
	public void closeDataSourceConnections(){
		ds.closeDataSourceConnections();
	}
	
	/**
	 * Method to get ArrayList of all CaffeineSource objects.
	 * 
	 * @return ArrayList of CaffeineSources
	 */
	public ArrayList<CaffeineSource> getAllCaffeineSources(){
		return ds.getAllCaffeineSources();
	}
	
		
	//TESTING MATERIAL
	private TextView tv;
	
	public AppController(TextView view, AppDataStore ds) {
        this.tv =  view;
        this.ds = ds;
	}
	
	public void test(){
		String test = ds.test();
		tv.setMovementMethod(new ScrollingMovementMethod());
		tv.setText(test);
	}
}
