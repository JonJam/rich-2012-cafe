package com.googlecode.rich2012cafe.view;

import android.location.Location;
import com.google.android.maps.MapView;

/**
 * User: cs16g08
 * Date: 27/02/2012
 * Time: 17:05
 */
public interface MapViewInterface {

    public MapView getMapView();

    public void handleLocationChanged(Location location);

}
