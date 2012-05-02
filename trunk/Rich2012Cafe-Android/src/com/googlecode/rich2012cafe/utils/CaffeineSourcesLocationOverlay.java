package com.googlecode.rich2012cafe.utils;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.googlecode.rich2012cafe.ApplicationState;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.activities.GMapActivity.PopupPanel;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;
import com.googlecode.rich2012cafe.shared.LeaderboardScoreProxy;
import com.googlecode.rich2012cafe.shared.OpeningTimeProxy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * User: CS
 * Date: 15/03/12
 * Time: 11:41
 */
public class CaffeineSourcesLocationOverlay extends MapOverlays {
	
	private com.google.android.maps.MapView mapView;
	Context mContext;
	View dialogLayout;
	PopupPanel popupView;
	NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public CaffeineSourcesLocationOverlay(com.google.android.maps.MapView mapView, PopupPanel popupView, Drawable defaultMarker, Context context) {
        super(defaultMarker, context);
    	mContext = context;
    	this.popupView = popupView;
    	this.mapView = mapView;
    }

    protected boolean onTap(int index) {

        CaffeineSourceOverlayItem item = (CaffeineSourceOverlayItem) getOverlays().get(index);
        GeoPoint geo=item.getPoint();
        View view=popupView.getView();
        Point pt=mapView.getProjection().toPixels(geo, null);
        Calendar cal =Calendar.getInstance();
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
        
        ((TextView)view.findViewById(R.id.title))
          .setText(item.getTitle());
        try{
        if(item.source.hasOpeningTimes()){
        ((TextView)view.findViewById(R.id.openingTimes)).setText("This location is open " +item.openingTimes.get(0).getOpeningTime() + " - "+item.openingTimes.get(0).getClosingTime());
        } else {
            ((TextView)view.findViewById(R.id.openingTimes)).setText("");

        }}catch(NullPointerException e){
        	Log.i("ERROR:","NullPointer at openingTime" + e.getStackTrace());
        	((TextView)view.findViewById(R.id.openingTimes)).setText("");}
        try{
        	final TableLayout tbl = (TableLayout) view.findViewById(R.id.productsTable);
    		tbl.removeAllViewsInLayout();
    		List<CaffeineSourceProductProxy> caffeineProductsList = item.caffeineSourceList;
        	for(int i=0; i<caffeineProductsList.size();i = i+2){
        		if(i+1 <= caffeineProductsList.size()){
        		tbl.addView(addRank(caffeineProductsList.get(i).getName() +" ("+ formatter.format(caffeineProductsList.get(i).getPrice()) +")", caffeineProductsList.get(i+1).getName() +" ("+ formatter.format(caffeineProductsList.get(i+1).getPrice()) +")"));
        		} else
            	tbl.addView(addRank(caffeineProductsList.get(i).getName() +" ("+ formatter.format(caffeineProductsList.get(i).getPrice()) +")", ""));

        	}
        }catch(NullPointerException e){
        	Log.i("ERROR:","NullPointer at products and tables");
        	((TextView)view.findViewById(R.id.products))
            .setText("");
        }
        popupView.show(pt.y*2<mapView.getHeight(),geo);
        
        

        return true;
    }
    
    private TableRow addRank(String item1, String item2){
		TableRow temp = new TableRow(mContext);
		temp.addView(addTextView(item1));
		temp.addView(addTextView(item2));
		return temp;
	}
	
	private TextView addTextView(String text){
		TextView temp = new TextView(mContext);
		temp.setTextSize(9);
		temp.setTextColor(Color.rgb(90, 58, 41));
		temp.setPadding(2, 2, 5, 2);
		temp.setText(text);
		return temp;
	}

}
