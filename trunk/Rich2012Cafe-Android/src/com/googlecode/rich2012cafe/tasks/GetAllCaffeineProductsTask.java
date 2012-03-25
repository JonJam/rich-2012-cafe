package com.googlecode.rich2012cafe.tasks;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.utils.Util;

import android.content.Context;
import android.os.AsyncTask;

/**
 * AsyncTask class to perform RPC call to get all caffeine products.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class GetAllCaffeineProductsTask extends AsyncTask<Context, Void, Void>{

	@Override
	protected Void doInBackground(Context... params) {
		Context mContext = params[0];
		
		MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
		requestFactory.rich2012CafeRequest().getAllCaffeineProducts().fire(new Receiver<List<CaffeineProductProxy>>(){
			
			@Override
			public void onSuccess(List<CaffeineProductProxy> products) {
				
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
