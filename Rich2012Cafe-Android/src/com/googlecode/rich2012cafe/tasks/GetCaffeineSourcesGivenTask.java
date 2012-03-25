package com.googlecode.rich2012cafe.tasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.OpeningTimeProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;
import com.googlecode.rich2012cafe.utils.Util;

/**
 * AsyncTask class to perform RPC call to get all caffeine sources given params:
 * 
 * 
 * And additional information.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class GetCaffeineSourcesGivenTask extends AsyncTask<Context, Void, Void>{

	@Override
	protected Void doInBackground(Context... params) {
		Context mContext = params[0];
		
		final MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
		
		//Get caffeine sources given
		requestFactory.rich2012CafeRequest().getCaffeineSourcesGiven().fire(new Receiver<List<CaffeineSourceProxy>>(){

			@Override
			public void onSuccess(List<CaffeineSourceProxy> sources) {
				String sourceId = "";
				
				//Get Opening Times
				requestFactory.rich2012CafeRequest().getOpeningTimesForCaffeineSource(sourceId)
				.fire(new Receiver<List<OpeningTimeProxy>>(){
			      	
		      		@Override
					public void onSuccess(List<OpeningTimeProxy> products) {
						
					}
		      	
					@Override
		          public void onFailure(ServerFailure error) {
		             
		          }
				});
				
				 //Get caffeine source products.
				requestFactory.rich2012CafeRequest().getCaffeineSourceProductsForCaffeineSource(sourceId)
	          	.fire(new Receiver<List<CaffeineSourceProductProxy>>(){
	          	
	          		@Override
					public void onSuccess(List<CaffeineSourceProductProxy> products) {
						
					}
		        	
					@Override
		            public void onFailure(ServerFailure error) {
		                
		            }
	          	});
			}
        	
			@Override
            public void onFailure(ServerFailure error) {
                
            }
		});

		return null;
	}
	
    @Override
    protected void onPostExecute(Void result) {
       
    }
}
