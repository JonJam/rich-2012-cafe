package com.googlecode.rich2012cafe.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.utils.Util;

/**
 * AsyncTask class to perform RPC call to update leaderboard score.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class UpdateLeaderboardScoreTask extends AsyncTask<Context, Void, Void>{
	
	@Override
	protected Void doInBackground(Context... params) {
		Context mContext = params[0];
		double score = 1; // Change to actual value
		
		MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
		requestFactory.rich2012CafeRequest().updateLeaderboardScore(score).fire(new Receiver<Void>(){

			@Override
			public void onSuccess(Void products) {
			
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
