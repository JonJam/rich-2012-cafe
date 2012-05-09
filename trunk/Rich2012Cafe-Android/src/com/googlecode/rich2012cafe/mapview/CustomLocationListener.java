package com.googlecode.rich2012cafe.mapview;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.googlecode.rich2012cafe.mapview.MapViewInterface;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public class CustomLocationListener implements LocationListener {
    
    private MapViewInterface activity;
    
    public CustomLocationListener(MapViewInterface activity) {
        this.activity = activity;
    }

    public void onLocationChanged(Location location) {
        activity.handleLocationChanged(location);
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {}

    public void onProviderEnabled(String s) {}

    public void onProviderDisabled(String s) {}
}
