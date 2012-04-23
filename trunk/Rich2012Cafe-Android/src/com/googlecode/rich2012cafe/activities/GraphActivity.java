package com.googlecode.rich2012cafe.activities;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class GraphActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		this.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		this.getActionBar().setDisplayShowTitleEnabled(false);
        this.getActionBar().setHomeButtonEnabled(true);

	    /* 
	     * first init data 
	     */  
	    // sin curve  
	    int num = 150;  
	    GraphViewData[] data = new GraphViewData[num];  
	    double v=0;  
	    for (int i=0; i<num; i++) {  
	       v += 0.2;  
	       data[i] = new GraphViewData(i, Math.sin(v));  
	    }  
	    GraphViewSeries seriesSin = new GraphViewSeries(data);
	     	    
	    /* 
	     * create graph 
	     */  
	    GraphView graphView = new LineGraphView(  
	          this  
	          , "EstimatedCaffeine Status Graph"  
	    );  
	    // add data  
	    graphView.addSeries(seriesSin);  
	    // optional - set view port, start=2, size=10  
	    graphView.setViewPort(2, 10);  
	    graphView.setScalable(true);  
	    // optional - legend    
	      
	    LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);  
	    layout.addView(graphView);  
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
