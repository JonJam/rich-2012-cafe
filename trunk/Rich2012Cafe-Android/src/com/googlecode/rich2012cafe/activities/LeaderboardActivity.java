package com.googlecode.rich2012cafe.activities;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;

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
		int score =-1;
		if(this.getIntent().getExtras() != null){
			score = this.getIntent().getExtras().getInt("currentscore");
		}
		
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setHomeButtonEnabled(true);
		
		TextView scoreLabel = (TextView) this.findViewById(R.id.scoreLabel);
		scoreLabel.setText("Your Current Score: "+score);
		generateTable();
	}
	
	private void generateTable(){
		TableLayout tbl = (TableLayout) this.findViewById(R.id.leader_table);
		
	}
	
	private TableRow addRank(String rank, String score){
		TableRow temp = new TableRow(this);
		TextView r = new TextView(this);
		r.setText(rank);
		TextView s = new TextView(this);
		s.setText(score);
		
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
