package com.googlecode.rich2012cafe.utils;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.ApplicationState;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ScheduledTasks {


	public void updateLeaderboard(final Context c, final boolean visible){
		final ApplicationState as = (ApplicationState) c.getApplicationContext();
		 final ProgressDialog pd = new ProgressDialog(c);
		 if(visible){
			 pd.show(c, "Leaderboard", "Getting scores...");
		 }
		new AsyncTask<Void, Void, List<LeaderboardScoreProxy>>(){

			private List<LeaderboardScoreProxy> scores;

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
		 final ProgressDialog pd = new ProgressDialog(mContext);
		 if(visible){
			 pd.show(mContext, "Products", "Getting Products...");
		 }
		new AsyncTask<Void, Void, List<CaffeineProductProxy>>(){

			private List<CaffeineProductProxy> product;
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
		       pd.dismiss();
		       Log.i("T", "I IS HERE");
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
