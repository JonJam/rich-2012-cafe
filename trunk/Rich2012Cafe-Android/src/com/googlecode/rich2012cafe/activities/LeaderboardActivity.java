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
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LeaderboardActivity extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		

		//this.findViewById(R.id.refreshLeaderboard).setOnClickListener(this);
		ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true); 
		generateTable();
	}
	
	private void generateTable(){
		final TableLayout tbl = (TableLayout) this.findViewById(R.id.leader_table);
		ApplicationState as = (ApplicationState) this.getApplicationContext();
		tbl.removeAllViewsInLayout();
		
		tbl.addView(addTitle("Current Score:"));
		tbl.addView(addTitle(as.getScore()+""));
		tbl.addView(addTitle(""));
		tbl.addView(addTitle("Current Leaderboard"));
		
		TableRow headers = new TableRow(this);
		headers.setGravity(Gravity.CENTER_HORIZONTAL);
		headers.addView(addTextView("Rank", Color.rgb(255, 252, 191), Color.BLACK, 0));
		headers.addView(addTextView("Score", Color.rgb(255, 252, 191), Color.BLACK, 0));
		tbl.addView(headers);
		
		
		final List<LeaderboardScoreProxy> scores = as.getLeaderboard();
		if(scores != null){
			boolean twist = true;
			for(int i=0; i<scores.size();i++){
				tbl.addView(addRank(i+1, scores.get(i).getScore(), twist));
				twist = !twist;
			}
		}
		
		Button refresh = new Button(this);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ScheduledTasks.updateLeaderboard(tbl.getContext(), true);
				generateTable();
			}
			
		});
		tbl.addView(addTitle(""));
		refresh.setText("Refresh");
		TableRow buttonRow = new TableRow(this);
		buttonRow.addView(refresh);
		tbl.addView(buttonRow);
		
	}
	
	private TableRow addTitle(String text){
		TableRow title = new TableRow(this);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.addView(addTextView(text, Color.BLACK, Color.WHITE, 1));
		return title;
	}
	private TableRow addRank(int rank, double score, boolean invert){
		TableRow temp = new TableRow(this);
		temp.setGravity(Gravity.CENTER_HORIZONTAL);
		int color = Color.BLACK;
		int textColor = Color.WHITE;
		if(invert){
			color = Color.rgb(51, 51, 47);
			textColor = Color.WHITE;
		}else{
			color = Color.rgb(174, 174, 174);
			color = Color.BLACK;
		}
		temp.addView(addTextView(rank+"", color, textColor, 0));
		temp.addView(addTextView(score+"", color, textColor, 0));
		return temp;
	}
	
	private TextView addTextView(String text, int background, int textColor, int size){
		TextView temp = new TextView(this);
		temp.setPadding(5, 5, 5, 5);
		temp.setTextSize(temp.getTextSize()+size);
		temp.setGravity(Gravity.CENTER_HORIZONTAL);
		temp.setText(text);
		temp.setTextColor(textColor);
		temp.setBackgroundColor(background);
		return temp;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        // Invoke the Register activity
        menu.getItem(0).setIntent(new Intent(this, GMapActivity.class));
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