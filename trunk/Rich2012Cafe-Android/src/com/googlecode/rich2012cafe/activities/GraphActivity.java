package com.googlecode.rich2012cafe.activities;

import com.googlecode.rich2012cafe.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class GraphActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);

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
}
