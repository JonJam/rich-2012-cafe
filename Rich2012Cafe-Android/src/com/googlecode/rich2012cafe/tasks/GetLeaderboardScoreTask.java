package com.googlecode.rich2012cafe.tasks;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;
import com.googlecode.rich2012cafe.utils.Util;

import android.content.Context;
import android.os.AsyncTask;

/**
 * AsyncTask class to perform RPC call to get leaderboard score.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class GetLeaderboardScoreTask extends AsyncTask<Context, Void, Void>{

	@Override
	protected Void doInBackground(Context... params) {
		Context mContext = params[0];
		
		MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
		requestFactory.rich2012CafeRequest().getLeaderboardScore().fire(new Receiver<LeaderboardScoreProxy>(){
			
			@Override
			public void onSuccess(LeaderboardScoreProxy score) {
	      		
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
