package com.googlecode.rich2012cafe.controller;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.model.*;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.OpeningTime;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;

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
	
	//TODO Sort out below when database built
	public void runSPARQLQuery(){
		SPARQLTask task = new SPARQLTask();
		task.execute();
	}
	
	private class SPARQLTask extends AsyncTask<String, Void, String>{
		
		protected String doInBackground(String... params) {
			StringBuffer results = new StringBuffer();
			ArrayList<CaffeineSource> caffeineSources = ds.getCaffeineSources();
			
			for(CaffeineSource s : caffeineSources){
				
				results.append(s.getName() + "\n");
				
				ArrayList<CaffeineProduct> products = ds.getCaffeineProducts(s.getId());
				for(CaffeineProduct p : products){
					results.append(p.getName() + " " + p.getPrice() + " " + p.getType() + "\n");
				}
				
				results.append("\n");
			}
			return results.toString();
		}
		
		protected void onPostExecute(String result) {
			tv.setMovementMethod(new ScrollingMovementMethod());
			tv.setText(result);
		}
	}
}