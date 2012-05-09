package com.googlecode.rich2012cafe.activities;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;


/**
 * @author Pratik Patel (p300ss@gmail.com)
 */

public class GraphActivity extends Activity{
	  private XYMultipleSeriesDataset mDataset;
	    private XYMultipleSeriesRenderer mRenderer;
	    private GraphicalView mChartView;
	    private XYSeries time_series, min_series, max_series;
	    private LinearLayout layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		layout = (LinearLayout) this.findViewById(R.id.graph1);
		this.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		this.getActionBar().setHomeButtonEnabled(true);
		graphFill();
	}
	
	private void graphFill(){
	       // create dataset and renderer
        mDataset = new XYMultipleSeriesDataset();
        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setLegendTextSize(15);
        mRenderer.setPointSize(3f);

        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.WHITE);
        r.setLineWidth(2);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(false);
        
        XYSeriesRenderer min = new XYSeriesRenderer();
        min.setColor(Color.GREEN);
        min.setLineWidth(5);
        min.setPointStyle(PointStyle.POINT);
        min.setFillPoints(false);
        
        XYSeriesRenderer max = new XYSeriesRenderer();
        max.setColor(Color.RED);
        max.setLineWidth(5);
        max.setPointStyle(PointStyle.POINT);
        max.setFillPoints(false);
       
        mRenderer.addSeriesRenderer(r);
        mRenderer.addSeriesRenderer(min);
        mRenderer.addSeriesRenderer(max);
        mRenderer.setYTitle("Caffeine (mg)");
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(24);
        mRenderer.setYAxisMax(250);
        mRenderer.setYAxisMin(0);
        mRenderer.setXTitle("Hours");
        mRenderer.setClickEnabled(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setInScroll(true);
        mRenderer.setSelectableBuffer(2);
        mRenderer.setPanEnabled(true);
        mRenderer.setAntialiasing(true);
        
        mRenderer.setOrientation(Orientation.HORIZONTAL);
        mRenderer.setZoomEnabled(true);
        mRenderer.setPanLimits(new double[] { -10, 20, -10, 40 });
        mRenderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

        time_series = new XYSeries("Caffeine Level");
        min_series = new XYSeries("Min-Optimum");
        max_series = new XYSeries("Max-Optimum");
        mDataset.addSeries(time_series);
        mDataset.addSeries(min_series);
        mDataset.addSeries(max_series);

        fillData();

        mChartView = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.1f);
        layout.addView(mChartView);

	}

    private void fillData() {
    	
        //CaffeineLevelReader clr = new CaffeineLevelReader(this);
        
//        for(Map.Entry<Date, Integer> entry : clr.getCaffeineLevels(Rich2012CafeUtil.ADHOC_DRINKS_SETTING_NAME).entrySet()){
//            time_series.add(entry.getKey(), entry.getValue());
//            min_series.add(entry.getKey(), 100);
//            max_series.add(entry.getKey(), 200);
//        }
    	
    	
        int[] data = {12, 10, 10, 10, 10, 10,
        			10, 110, 110, 100, 75, 50, 
        			150, 150, 150, 150, 100, 100,
        			50, 50, 25, 25, 12, 12};
        for(int i=0; i<data.length; i++){
        	time_series.add(i, data[i]);
        	min_series.add(i, 100);
        	max_series.add(i, 200);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionmenu, menu);
		// Invoke the Register activity
		menu.getItem(1).setIntent(new Intent(this, GMapActivity.class));
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
