package com.googlecode.rich2012cafe.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.activities.MapView;

public class MapFragment extends LocalActivityManagerFragment{
	
	private View mapViewContainer;
	private com.google.android.maps.MapView mapView;
	
	private TabHost mTabHost;
	
	public static MapFragment newInstance() {
		MapFragment fragment = new MapFragment();

		Bundle args = new Bundle();
		fragment.setArguments( args );

		return fragment;
	}

	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        
	        View view = inflater.inflate(R.layout.ui_main_mapviewer, container, false);
	        mTabHost = (TabHost)view.findViewById(android.R.id.tabhost);
	        mTabHost.setup(getLocalActivityManager());
	        
	        TabSpec tab = mTabHost.newTabSpec("map")
	                              .setIndicator("map")
	                              .setContent(new Intent(getActivity(), MapView.class));
	        mTabHost.addTab(tab);        
	        return view;
		 
	    }
	
}
