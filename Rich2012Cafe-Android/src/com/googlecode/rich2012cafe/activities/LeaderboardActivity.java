package com.googlecode.rich2012cafe.activities;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;
import com.googlecode.rich2012cafe.utils.Util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LeaderboardActivity extends Activity{

	private Context mContext = this;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		
		final TextView scoreLabel = (TextView) this.findViewById(R.id.scoreLabel);
		
//		new AsyncTask<Void, Void, Void>(){
//
//			@Override
//			protected Void doInBackground(Void...params) {				
//				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
//				requestFactory.rich2012CafeRequest().updateLeaderboardScore(1).fire(new Receiver<Void>(){
//					
//					@Override
//					public void onSuccess(Void scores) {
//						Log.e("T-msg", "SUCESS");
//					}
//		      	
//					@Override
//					public void onFailure(ServerFailure error) {
//			              Log.e("T-msg", error.getMessage());
//			        }
//				});
//				
//				return null;
//			}
//			
//		    @Override
//		    protected void onPostExecute(Void result) {
//		       
//		    }
//		}.execute();
		
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
	      			scoreLabel.setText("Your Current Score: "+result.getScore());
	      		}else{
	      			Log.e("T-msg", "Null current score");
	      		}
		    }
		}.execute();
		
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
		
		
		
		generateTable();
	}
	
	private void generateTable(){
		final TableLayout tbl = (TableLayout) this.findViewById(R.id.leader_table);
		

		new AsyncTask<Void, Void, List<LeaderboardScoreProxy>>(){

			private List<LeaderboardScoreProxy> scores;
			
			@Override
			protected List<LeaderboardScoreProxy> doInBackground(Void...params) {			
				
				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
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
	      			for(int i=0; i<results.size();i++){
	      				tbl.addView(addRank(i+1, results.get(i).getScore()));
	      			}
	      		}else{
	      			Log.e("T-msg", "Null Leaderboard Scores");
	      		}
		    }
		}.execute();
		
//		tbl.addView(addRank(1,12));
//		tbl.addView(addRank(2,4213));
//		tbl.addView(addRank(3,43));
		
	}
	
	private TableRow addRank(int rank, double score){
		TableRow temp = new TableRow(this);
		TextView r = new TextView(this);
		r.setText(rank+"");
		TextView s = new TextView(this);
		s.setText(score+"");
		
		temp.addView(r);
		temp.addView(s);
		return temp;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        // Invoke the Register activity
        menu.getItem(1).setIntent(new Intent(this, GraphActivity.class));
        menu.getItem(2).setIntent(new Intent(this, LeaderboardActivity.class));
        menu.getItem(3).setIntent(new Intent(this, SettingsActivity.class));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          //return controller.optionsActions(item, this);
    	switch(item.getItemId()){
    	case android.R.id.home:{
    		Intent intent = new Intent(this, Rich2012CafeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;

    	}
    	}
    	return false;
    }
}
