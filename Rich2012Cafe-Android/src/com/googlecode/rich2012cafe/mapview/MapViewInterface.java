package com.googlecode.rich2012cafe.mapview;

import android.location.Location;
import com.google.android.maps.MapView;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public interface MapViewInterface {

    public MapView getMapView();

    public void handleLocationChanged(Location location);

}
