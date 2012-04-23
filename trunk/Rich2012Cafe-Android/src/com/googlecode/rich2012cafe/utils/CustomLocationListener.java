package com.googlecode.rich2012cafe.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.googlecode.rich2012cafe.utils.MapViewInterface;

/**
 * User: CS
 * Date: 08/03/12
 * Time: 21:23
*/
public class CustomLocationListener implements LocationListener {
    
    MapViewInterface activity;
    
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
