package com.googlecode.rich2012cafe;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

/**
 * User: cs16g08
 * Date: 27/02/2012
 * Time: 15:30
 */
public class GoogleMap extends MapActivity {

    //Test comment

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
    }
}
