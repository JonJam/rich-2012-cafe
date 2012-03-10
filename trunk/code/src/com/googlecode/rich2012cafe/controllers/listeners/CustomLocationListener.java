package com.googlecode.rich2012cafe.controllers.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.googlecode.rich2012cafe.view.MapViewInterface;

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

    @Override
    public void onLocationChanged(Location location) {
        activity.handleLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}
