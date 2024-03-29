package com.googlecode.rich2012cafe.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.mapview.CaffeineSourceOverlayItem;
import com.googlecode.rich2012cafe.mapview.CaffeineSourcesLocationOverlay;
import com.googlecode.rich2012cafe.mapview.CurrentLocationOverlay;
import com.googlecode.rich2012cafe.mapview.CustomLocationListener;
import com.googlecode.rich2012cafe.mapview.MapViewInterface;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceWrapperProxy;
import com.googlecode.rich2012cafe.utils.LocationUtils;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import com.googlecode.rich2012cafe.utils.Util;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public class GMapActivity extends MapActivity implements MapViewInterface {

	private com.google.android.maps.MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private Location currentBestLocation;
    private CurrentLocationOverlay currentLocationOverlay;
    private CaffeineSourcesLocationOverlay caffeineSourcesLocationOverlay;

    private Context mContext = this;
    
 @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        setMapView((com.google.android.maps.MapView) findViewById(R.id.mapview));
        mapController = mapView.getController();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        getMapView().setBuiltInZoomControls(true);

        String networkLocationProvider = LocationManager.NETWORK_PROVIDER;
        String gpsLocationProvider = LocationManager.GPS_PROVIDER; //TODO Craig: Change this back to network provider for real

        // Check if enabled and if not send user to the GSP settings better solution would be to display a dialog and suggesting to go to the settings

        
        if ( !getLocationManager().isProviderEnabled(gpsLocationProvider) ) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Location lastGPSKnownLocation = locationManager.getLastKnownLocation(gpsLocationProvider);
        Location lastNetworkKnownLocation = locationManager.getLastKnownLocation(networkLocationProvider);

        currentBestLocation = handleQuickFixFromLastKnownLocation(lastGPSKnownLocation, lastNetworkKnownLocation);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        Drawable currentLocationMarker = this.getResources().getDrawable(R.drawable.current_location_marker);
        Drawable sourceMarker = this.getResources().getDrawable(R.drawable.mapsource);
        currentLocationOverlay = new CurrentLocationOverlay(currentLocationMarker, this);
        
        if (currentBestLocation != null) {
            Log.e(GMapActivity.class.getName(), currentBestLocation.toString());
            showCurrentLocationOnMap(currentBestLocation);
        }

        LocationListener locationListener = new CustomLocationListener(this);

        getLocationManager().requestLocationUpdates(gpsLocationProvider, 0, 0, locationListener);

        if (locationManager.isProviderEnabled(networkLocationProvider)) {
            getLocationManager().requestLocationUpdates(networkLocationProvider, 0, 0, locationListener);
        } else {
            Log.i(GMapActivity.class.getName(), "Network not enabled...");
        }
              
        PopupPanel panel=new PopupPanel(R.layout.mapsourcedetails);
        caffeineSourcesLocationOverlay = new CaffeineSourcesLocationOverlay(mapView,panel,sourceMarker, this);
        
        loadAndDisplayCaffeinePoints(panel);

    }
 
 private void loadAndDisplayCaffeinePoints(PopupPanel panel) {
	 
	 new AsyncTask<Void, Void, List<CaffeineSourceWrapperProxy>>(){
 		List<CaffeineSourceWrapperProxy> locationList;
 		
 		@Override
 		protected List<CaffeineSourceWrapperProxy> doInBackground(Void... params) {
 			
 			//Get time information.
 			SimpleDateFormat sdf = new SimpleDateFormat(Rich2012CafeUtil.DB_TIME_FORMAT);
 			Calendar cal = Calendar.getInstance();
 			String dayName = Rich2012CafeUtil.DAY_NAMES[cal.get(Calendar.DAY_OF_WEEK) - 1];
 			String todayTime = sdf.format(cal.getTime());   
 			
 			MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
 			
 			//Get caffeine sources given
 	        Log.e(GMapActivity.class.getName(), currentBestLocation.getLatitude() + " and " +currentBestLocation.getLongitude());
 			requestFactory.rich2012CafeRequest().getCaffeineSourcesGiven(currentBestLocation.getLatitude(),currentBestLocation.getLongitude(), dayName, todayTime).fire(new Receiver<List<CaffeineSourceWrapperProxy>>(){
 				@Override
 				public void onSuccess(List<CaffeineSourceWrapperProxy> sources) {
 					for (CaffeineSourceWrapperProxy caffeineSource: sources) {
 				        Log.e(GMapActivity.class.getName(), caffeineSource.getSource().getBuildingLat() + " and " +caffeineSource.getSource().getBuildingLong());
 				         showCaffeineSourceOnMap(caffeineSource);
 					}
 					
 				    mapView.getOverlays().add(caffeineSourcesLocationOverlay);
 					locationList = sources;
 				}
 	        	
 				@Override
 	            public void onFailure(ServerFailure error) {
 					Log.i(GMapActivity.class.getName(), "request failed for map sources");
 	            }
 			});

 			return locationList;
 		}
 		
 	    @Override
 	    protected void onPostExecute(List<CaffeineSourceWrapperProxy> results) {
 	    }
	    }.execute();

 }
 
 private void showCaffeineSourceOnMap(CaffeineSourceWrapperProxy caffeineSource) {
	 CaffeineSourceProxy source = caffeineSource.getSource();
	 String sourceTitle = source.getBuildingName() + " (" + source.getBuildingNumber() + ")";
     String snippet = "Long: " + source.getBuildingLong() + ", Lat: " + source.getBuildingLat();
     String caffeineSourceId = source.getId();      
     int buildingLat = (int) (source.getBuildingLat() * 1E6);
     int buildingLong = (int) (source.getBuildingLong() * 1E6);
     GeoPoint point = new GeoPoint(buildingLat, buildingLong);

     CaffeineSourceOverlayItem overlayItem = new CaffeineSourceOverlayItem(point, sourceTitle, snippet, caffeineSourceId);
    
     overlayItem.setOpeningTimes(caffeineSource.getOpeningTimes());
     overlayItem.setCaffeineSourceList(caffeineSource.getProducts());
     overlayItem.setSource(caffeineSource.getSource());
     
     caffeineSourcesLocationOverlay.addOverlay(overlayItem);
 }

 private Location handleQuickFixFromLastKnownLocation(Location lastGPSKnownLocation, Location lastNetworkKnownLocation) {

     if (lastGPSKnownLocation != null && lastNetworkKnownLocation != null) {
         if (lastGPSKnownLocation.hasAccuracy() && lastNetworkKnownLocation.hasAccuracy()) {
             return lastGPSKnownLocation.getAccuracy() <= lastNetworkKnownLocation.getAccuracy() ? lastGPSKnownLocation : lastNetworkKnownLocation;
         }
     } else if (lastGPSKnownLocation == null) {
         if (lastNetworkKnownLocation != null) {
             return lastNetworkKnownLocation;
         }
     } else {
         return lastGPSKnownLocation;
     }

     return null;
 }

 @Override
 public void handleLocationChanged(Location location) {
     if (LocationUtils.isNewLocationBetterThanCurrentBest(location, currentBestLocation)) {
         currentBestLocation = location;
         showCurrentLocationOnMap(location);
     }
 }

 private void showCurrentLocationOnMap(Location location) {

     if (location != null) {

         Log.w(GMapActivity.class.getName(), "Lat: " + location.getLatitude() + " -- Long: " + location.getLongitude());

         int lat = (int) (location.getLatitude() * 1E6);
         int lng = (int) (location.getLongitude() * 1E6);
         GeoPoint point = new GeoPoint(lat, lng);
         OverlayItem overlayItem = new OverlayItem(point, "Current Location", "");
         currentLocationOverlay.addOverlay(overlayItem);
         mapView.getOverlays().add(currentLocationOverlay);
         mapController.animateTo(point);
         mapController.setZoom(15);

     }
 }
 
 private LocationManager getLocationManager() {
     return locationManager;
 }

 public com.google.android.maps.MapView getMapView() {
     return mapView;
 }

 public void setMapView(com.google.android.maps.MapView mapView) {
     this.mapView = mapView;
 }
 
 @Override
 protected boolean isRouteDisplayed() {
     return false;
 }
 
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.actionmenu, menu);
     // Invoke the Register activity
     menu.getItem(0).setIntent(new Intent(this, GraphActivity.class));
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
 
 public class PopupPanel {
	    View popup;
	    boolean isVisible=false;
	    
	     public PopupPanel(int layout) {

	      popup=getLayoutInflater().inflate(layout, mapView, false);
	      popup.setBackgroundColor(Color.rgb(255,252, 191));
	                  
	      popup.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	          hide();
	        }
	      });
	    }
	    
	    public View getView() {
	      return(popup);
	    }
	    
	    public void show(boolean alignTop, GeoPoint point) {
	    	MapView.LayoutParams lp=new MapView.LayoutParams(
	            MapView.LayoutParams.FILL_PARENT,
	            MapView.LayoutParams.WRAP_CONTENT,
	            point,MapView.LayoutParams.BOTTOM_CENTER
	      );
	      
	      if (alignTop) {
	        mapView.requestLayout();
	      }
	      else {
	        mapView.requestLayout();
	      }
	      
	  	  mapView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		  	Projection projection = mapView.getProjection();
			Point p = new Point();
	
			projection.toPixels(point, p);
			p.offset(0, -(popup.getMeasuredHeight() / 2));
	      hide();
	      
	      ((ViewGroup)mapView.getParent()).addView(popup, lp);
	      isVisible=true;
	    }
	    
	    public void hide() {
	      if (isVisible) {
	        isVisible=false;
	        ((ViewGroup)popup.getParent()).removeView(popup);
	      }
	    }
 }
}
