package com.googlecode.rich2012cafe.controller;

import com.googlecode.rich2012cafe.model.*;

import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Resources used:
 * 	- http://www.vogella.de/articles/AndroidPerformance/article.html
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppController {
	
	private TextView tv;
	private AppDataStore ds;
	
	public AppController(TextView tv){
		this.tv = tv;
		this.ds = new AppDataStore();
	}
	
	public void runSPARQLQuery(){
		SPARQLTask task = new SPARQLTask();
		task.execute();
	}
	
	private class SPARQLTask extends AsyncTask<String, Void, String>{
		
		protected String doInBackground(String... params) {
			return ds.getData();
		}
		
		protected void onPostExecute(String result) {
			tv.setMovementMethod(new ScrollingMovementMethod());
			tv.setText(result);
		}
	}
}
