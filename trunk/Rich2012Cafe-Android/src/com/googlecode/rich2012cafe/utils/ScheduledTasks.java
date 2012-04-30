package com.googlecode.rich2012cafe.utils;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.ApplicationState;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceWrapperProxy;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

public class ScheduledTasks {

	public static void getSourcesFromLatLong(final Context mContext, final boolean visible){
		final ApplicationState as = (ApplicationState) mContext.getApplicationContext();
		 ProgressDialog pd = null;
		 
	    	new AsyncTask<Void, Void, List<CaffeineSourceWrapperProxy>>(){
	    		ProgressDialog pd = null;
	    		private String message = "";
	    		List<CaffeineSourceWrapperProxy> results;
				
	    		@Override
				protected void onPreExecute() {
					if(visible){ pd = ProgressDialog.show(mContext, "Leaderboard", "Getting scores...");
					}
	            }
	    		
	    		@Override
	    		protected List<CaffeineSourceWrapperProxy> doInBackground(Void... params) {
	    			
	    			MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
	    			
	    			//Get caffeine sources given
	    			requestFactory.rich2012CafeRequest().getCaffeineSourcesGiven(50.937358,-1.397763).fire(new Receiver<List<CaffeineSourceWrapperProxy>>(){

	    				@Override
	    				public void onSuccess(List<CaffeineSourceWrapperProxy> sources) {
	    					results = sources;
	    				}
	    	        	
	    				@Override
	    	            public void onFailure(ServerFailure error) {
	    	              
	    	            }
	    			});

	    			return results;
	    		}
	    		
	    	    @Override
	    	    protected void onPostExecute(List<CaffeineSourceWrapperProxy> result) {
					if(visible){
						pd.dismiss();
					}
	    	    }
		    }.execute();
	}

	public static void updateLeaderboard(final Context c, final boolean visible){
		final ApplicationState as = (ApplicationState) c.getApplicationContext();
		 ProgressDialog pd = null;
		new AsyncTask<Void, Void, List<LeaderboardScoreProxy>>(){

			private List<LeaderboardScoreProxy> scores;
			private ProgressDialog pd;

			@Override
			protected void onPreExecute() {
				if(visible){ pd = ProgressDialog.show(c, "Leaderboard", "Getting scores...");
				}
            }

			
			@Override
			protected List<LeaderboardScoreProxy> doInBackground(Void...params) {			

				MyRequestFactory requestFactory = Util.getRequestFactory(c, MyRequestFactory.class);
				requestFactory.rich2012CafeRequest().getTopFiveLeaderboardScores().fire(new Receiver<List<LeaderboardScoreProxy>>(){

					@Override
					public void onSuccess(List<LeaderboardScoreProxy> results) {
						scores = results;
					}

					@Override
					public void onFailure(ServerFailure error) {

					}
				});

				return scores;
			}

			@Override
			protected void onPostExecute(List<LeaderboardScoreProxy> results) {
				if(results != null){
					Log.e("t-msg", "found some results");
					as.setLeaderboard(results);
				}else{
					Log.e("T-msg", "Null Leaderboard Scores");
				}
				if(visible){
					pd.dismiss();
				}

			}
		}.execute();
	}
	
	public static void getCaffeineProducts(final Context mContext, final boolean visible){
		final ApplicationState as = (ApplicationState) mContext.getApplicationContext();
		
		new AsyncTask<Void, Void, List<CaffeineProductProxy>>(){

			private List<CaffeineProductProxy> product;
			private ProgressDialog pd;
			
			@Override
			protected void onPreExecute() {
				if(visible){ pd = ProgressDialog.show(mContext, "Drinks", "Getting Drinks...");
				}
            }
			
			@Override
			protected List<CaffeineProductProxy> doInBackground(Void... params) {

				
				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
				requestFactory.rich2012CafeRequest().getAllCaffeineProducts().fire(new Receiver<List<CaffeineProductProxy>>(){
					
					@Override
					public void onSuccess(List<CaffeineProductProxy> products) {
						product = products;
						Log.i("TERROR", "productsset");
					}
		        	
					@Override
		            public void onFailure(ServerFailure error) {
		                Log.e("TERROR", error.getMessage());
		            }

		        });
				
				return product;
			}
			
		    @Override
		    protected void onPostExecute(List<CaffeineProductProxy> result) {	
		       as.setCaffeineProducts(result);
				if(visible){
					pd.dismiss();
				}
		    }
		}.execute();
	}

	public void uploadCurrentScore(final Context mContext){
		final ApplicationState as = (ApplicationState) mContext.getApplicationContext();
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void...params) {				
				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
				requestFactory.rich2012CafeRequest().updateLeaderboardScore(as.getScore()).fire(new Receiver<Void>(){

					@Override
					public void onSuccess(Void scores) {
					}

					@Override
					public void onFailure(ServerFailure error) {
						Log.e("T-msg", error.getMessage());
					}
				});

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

			}
		}.execute();
	}

	public void getDBScore(final Context mContext){

		new AsyncTask<Void, Void, LeaderboardScoreProxy>(){

			private LeaderboardScoreProxy score;

			@Override
			protected LeaderboardScoreProxy doInBackground(Void...params) {				
				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
				requestFactory.rich2012CafeRequest().getLeaderboardScore().fire(new Receiver<LeaderboardScoreProxy>(){

					@Override
					public void onSuccess(LeaderboardScoreProxy s) {
						score =s;
					}

					@Override
					public void onFailure(ServerFailure error) {
						Log.e("T-msg", error.getMessage());
					}
				});

				return score;
			}

			@Override
			protected void onPostExecute(LeaderboardScoreProxy result) {
				if(result != null){

				}else{

				}
			}
		}.execute();
	}
}
