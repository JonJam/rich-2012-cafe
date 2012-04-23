package com.googlecode.rich2012cafe.activities;

import com.googlecode.rich2012cafe.R;

import android.app.Activity;
import android.os.Bundle;
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
}
