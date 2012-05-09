package com.googlecode.rich2012cafe.mapview;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.activities.GMapActivity.PopupPanel;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public class CaffeineSourcesLocationOverlay extends MapOverlays {
	
	private com.google.android.maps.MapView mapView;
	private Context mContext;
	private PopupPanel popupView;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();

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
        
        ((TextView)view.findViewById(R.id.title))
          .setText(item.getTitle());
        try{
        if(item.getSource().hasOpeningTimes()){
        ((TextView)view.findViewById(R.id.openingTimes)).setText("This location is open " +item.getOpeningTimes().get(0).getOpeningTime() + " - "+item.getOpeningTimes().get(0).getClosingTime());
        } else {
            ((TextView)view.findViewById(R.id.openingTimes)).setText("");

        }}catch(NullPointerException e){
        	Log.i(CaffeineSourcesLocationOverlay.class.getName(),"NullPointer at openingTime" + e.getStackTrace());
        	((TextView)view.findViewById(R.id.openingTimes)).setText("");}
        try{
        	final TableLayout tbl = (TableLayout) view.findViewById(R.id.productsTable);
    		tbl.removeAllViewsInLayout();
    		List<CaffeineSourceProductProxy> caffeineProductsList = item.getCaffeineSourceList();
        	for(int i=0; i<caffeineProductsList.size();i = i+2){
        		if(i+1 <= caffeineProductsList.size()){
        		tbl.addView(addRank(caffeineProductsList.get(i).getName() +" ("+ formatter.format(caffeineProductsList.get(i).getPrice()) +")", caffeineProductsList.get(i+1).getName() +" ("+ formatter.format(caffeineProductsList.get(i+1).getPrice()) +")"));
        		} else
            	tbl.addView(addRank(caffeineProductsList.get(i).getName() +" ("+ formatter.format(caffeineProductsList.get(i).getPrice()) +")", ""));

        	}
        }catch(NullPointerException e){
        	Log.i(CaffeineSourcesLocationOverlay.class.getName(),"NullPointer at products and tables");
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
