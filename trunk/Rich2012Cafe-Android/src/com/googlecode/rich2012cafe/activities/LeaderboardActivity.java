package com.googlecode.rich2012cafe.activities;

import java.util.List;

import com.googlecode.rich2012cafe.ApplicationState;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;
import com.googlecode.rich2012cafe.utils.ScheduledTasks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LeaderboardActivity extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		
		final TextView scoreLabel = (TextView) this.findViewById(R.id.scoreLabel);
		ApplicationState as = (ApplicationState) this.getApplicationContext();
		scoreLabel.setText("Your Current Score: "+as.getScore());
		
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true); 
        ScheduledTasks st = new ScheduledTasks();
        st.updateLeaderboard(this, true);
		generateTable();
	}
	
	private void generateTable(){
		final TableLayout tbl = (TableLayout) this.findViewById(R.id.leader_table);
				
		ApplicationState as = (ApplicationState) this.getApplicationContext();
		List<LeaderboardScoreProxy> scores = as.getLeaderboard();
		if(scores != null){
			for(int i=0; i<scores.size();i++){
				tbl.addView(addRank(i+1, scores.get(i).getScore()));
			}
		}
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
