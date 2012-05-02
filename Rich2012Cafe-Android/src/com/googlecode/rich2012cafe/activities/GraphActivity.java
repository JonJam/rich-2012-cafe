package com.googlecode.rich2012cafe.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.PanListener;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.googlecode.rich2012cafe.model.CaffeineLevelReader;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class GraphActivity extends Activity{
	  private XYMultipleSeriesDataset mDataset;
	    private XYMultipleSeriesRenderer mRenderer;
	    private GraphicalView mChartView;
	    private TimeSeries time_series, min_series, max_series;
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
        r.setColor(Color.BLUE);
        r.setPointStyle(PointStyle.CIRCLE);
        r.setFillPoints(true);
        
        XYSeriesRenderer min = new XYSeriesRenderer();
        min.setColor(Color.GREEN);
        min.setPointStyle(PointStyle.CIRCLE);
        min.setFillPoints(true);
        
        XYSeriesRenderer max = new XYSeriesRenderer();
        max.setColor(Color.RED);
        max.setPointStyle(PointStyle.CIRCLE);
        max.setFillPoints(true);
        
        mRenderer.addSeriesRenderer(r);
        mRenderer.addSeriesRenderer(min);
        mRenderer.addSeriesRenderer(max);
        mRenderer.setClickEnabled(true);
        mRenderer.setSelectableBuffer(20);
        mRenderer.setPanEnabled(true);

        time_series = new TimeSeries("Caffeine Level");
        min_series = new TimeSeries("Min-Optimum");
        max_series = new TimeSeries("Max-Optimum");
        mDataset.addSeries(time_series);
        mDataset.addSeries(min_series);
        mDataset.addSeries(max_series);

        fillData();

        mChartView = ChartFactory.getTimeChartView(this, mDataset, mRenderer,
                "H:mm");

        layout.addView(mChartView);

	}

    private void fillData() {
        //CaffeineLevelReader clr = new CaffeineLevelReader(this);
        
//        for(Map.Entry<Date, Integer> entry : clr.getCaffeineLevels(Rich2012CafeUtil.ADHOC_DRINKS_SETTING_NAME).entrySet()){
//            time_series.add(entry.getKey(), entry.getValue());
//            min_series.add(entry.getKey(), 100);
//            max_series.add(entry.getKey(), 200);
//        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, Calendar.MAY, Calendar.WEDNESDAY, 0, 01);
        int[] data = {12, 10, 10, 10, 10, 10,
        			10, 110, 110, 100, 75, 50, 
        			150, 150, 150, 150, 100, 100,
        			50, 50, 25, 25, 12, 12};
        for(int i=0; i<24; i++){
        	time_series.add(calendar.getTime(), data[i]);
        	min_series.add(calendar.getTime(), 100);
        	max_series.add(calendar.getTime(), 200);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionmenu, menu);
		// Invoke the Register activity
		menu.getItem(0).setIntent(new Intent(this, GMapActivity.class));
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
