package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;

import com.googlecode.rich2012cafe.model.*;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineFinder extends Activity {
	
	private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        tv = new TextView(this);
        setContentView(tv);
        runAsyncTask();
    }
    
    /*
     * http://www.vogella.de/articles/AndroidPerformance/article.html
     */
    private class SPARQLTask extends AsyncTask<String, Void, String>{
    	
    	protected String doInBackground(String... params) {
    		return SPARQLQuerier.performQuery();
    	}
    	
    	protected void onPostExecute(String result) {
    		tv.setText(result);
    	}
    }
    
    public void runAsyncTask() {
		SPARQLTask task = new SPARQLTask();
		task.execute();
	}
}