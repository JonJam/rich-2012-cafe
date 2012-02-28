package com.googlecode.rich2012cafe.controller;

import com.googlecode.rich2012cafe.model.*;
import android.widget.TextView;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

/**
 * Resources used:
 * 	- http://www.vogella.de/articles/AndroidPerformance/article.html
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppController extends Controller<HomeViewInterface> {
	
	private TextView tv;
	private AppDataStore ds;
	
	public AppController(HomeViewInterface view) {
        super(view);
        this.tv =  view.getTextView();
		this.ds = new AppDataStore();
	}
	
	public void performDatabaseCheck(){
		ds.performDatabaseCheck();
	}
	
	/*private class SPARQLTask extends AsyncTask<String, Void, String>{
		
		protected String doInBackground(String... params) {
			StringBuffer results = new StringBuffer();
			ArrayList<CaffeineSource> caffeineSources = ds.getCaffeineSources();
			
			for(CaffeineSource s : caffeineSources){
				
				results.append(s.getName() + "\n");
				
				ArrayList<CaffeineProduct> products = ds.getCaffeineProducts(s.getId());
				for(CaffeineProduct p : products){
					results.append(p.getName() + " " + p.getPrice() + " " + p.getPriceType() + " " + p.getProductType() + "\n");
				}
				
				results.append("\n");
			}
			
			return results.toString();
		}
		
		protected void onPostExecute(String result) {
			tv.setMovementMethod(new ScrollingMovementMethod());
			tv.setText(result);
		}
	}*/
}
