package com.googlecode.rich2012cafe.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.OverlayItem;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;
import com.googlecode.rich2012cafe.utils.CustomLocationListener;
import com.googlecode.rich2012cafe.utils.CurrentLocationOverlay;
import com.googlecode.rich2012cafe.utils.LocationUtils;
import com.googlecode.rich2012cafe.utils.MapViewInterface;


public class GMapActivity extends MapActivity implements MapViewInterface {

	private com.google.android.maps.MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private Location currentBestLocation;
    private CurrentLocationOverlay currentLocationOverlay;


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
        //TODO Craig: Find if there is a better way to ask for GPS position
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
        currentLocationOverlay = new CurrentLocationOverlay(currentLocationMarker, this);

        if (currentBestLocation != null) {
            Log.e("Caffeinder", currentBestLocation.toString());
            showCurrentLocationOnMap(currentBestLocation);
        }


        LocationListener locationListener = new CustomLocationListener(this);

        getLocationManager().requestLocationUpdates(gpsLocationProvider, 0, 0, locationListener);

        if (locationManager.isProviderEnabled(networkLocationProvider)) {
            getLocationManager().requestLocationUpdates(networkLocationProvider, 0, 0, locationListener);
        } else {
            Log.i("App", "Network not enabled...");
        }

    }
 
 private void loadAndDisplayCaffeinePoints() {
	 
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

         Log.w("debug", "Lat: " + location.getLatitude());
         Log.w("debug", "Long: " + location.getLongitude());

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
