package com.googlecode.rich2012cafe.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.maps.*;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.view.MapOverlays;
import com.googlecode.rich2012cafe.view.MapViewInterface;


public class GoogleMap extends MapActivity implements MapViewInterface {

    private MapView mapView;
    private MapController mapController;
    private MapOverlays itemizedOverlays;
    private LocationManager locationManager;
    
    private Location currentBestLocation;

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        setMapView((MapView) findViewById(R.id.mapview));
        mapController = mapView.getController();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



        getMapView().setBuiltInZoomControls(true);

        String networkLocationProvider = LocationManager.NETWORK_PROVIDER;
        String gpsLocationProvider = LocationManager.GPS_PROVIDER; //TODO Craig: Change this back to network provider for real

        boolean enabled = getLocationManager().isProviderEnabled(gpsLocationProvider);

        // Check if enabled and if not send user to the GSP settings better solution would be to display a dialog and suggesting to go to the settings
        //TODO Craig: Find if there is a better way to ask for GPS position
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Location lastGPSKnownLocation = locationManager.getLastKnownLocation(gpsLocationProvider);
        Location lastNetworkKnownLocation = locationManager.getLastKnownLocation(networkLocationProvider);

        currentBestLocation = handleQuickFixFromLastKnownLocation(lastGPSKnownLocation, lastNetworkKnownLocation);

        if (currentBestLocation != null) {
            updateMap(currentBestLocation);
        }


//        
       
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
        itemizedOverlays = new MapOverlays(drawable);
        
        

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                updateMap(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}

        };

        //dummy default location
        Location dummyLocation = new Location(gpsLocationProvider);
        dummyLocation.setLatitude(50.935396);
        dummyLocation.setLongitude(-1.396214);
        updateMap(dummyLocation);

//        updateMap(getLocationManager().getLastKnownLocation(locationProvider));

        getLocationManager().requestLocationUpdates(gpsLocationProvider, 0, 0, locationListener);


    }

    private Location handleQuickFixFromLastKnownLocation(Location lastGPSKnownLocation, Location lastNetworkKnownLocation) {
        Location quickFixLocation = null;

        if (lastGPSKnownLocation != null && lastNetworkKnownLocation != null) {

            if (lastGPSKnownLocation.hasAccuracy() && lastNetworkKnownLocation.hasAccuracy()) {
                quickFixLocation = lastGPSKnownLocation.getAccuracy() <= lastNetworkKnownLocation.getAccuracy() ? lastGPSKnownLocation : lastNetworkKnownLocation;
            }

        } else if (lastGPSKnownLocation == null) {

            if (lastNetworkKnownLocation != null) {
                quickFixLocation = lastNetworkKnownLocation;
            }

        } else {

            quickFixLocation = lastGPSKnownLocation;

        }
        
        return quickFixLocation;

    }

    private LocationManager getLocationManager() {
        return locationManager;
    }

    public MapView getMapView() {
        return mapView;
    }
    
    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    private void updateMap(Location location) {
        
        if (location != null) {

            int lat = (int) (location.getLatitude() * 1E6);
            int lng = (int) (location.getLongitude() * 1E6);
            GeoPoint point = new GeoPoint(lat, lng);

            GeoPoint p = mapView.getMapCenter();
            OverlayItem overlayItem = new OverlayItem(p, "", "");
            itemizedOverlays.addOverlay(overlayItem);
            mapView.getOverlays().add(itemizedOverlays);
            mapController.animateTo(point);
            mapController.setZoom(10);


        }


        
        
    }
}
