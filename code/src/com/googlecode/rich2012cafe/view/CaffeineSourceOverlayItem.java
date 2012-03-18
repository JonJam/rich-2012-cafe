package com.googlecode.rich2012cafe.view;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * User: CS
 * Date: 18/03/12
 * Time: 16:01
 */
public class CaffeineSourceOverlayItem extends OverlayItem {

    private String caffeineSourceId;

    public CaffeineSourceOverlayItem(GeoPoint geoPoint, String title, String snippet, String caffeineSourceId) {

        super(geoPoint, title, snippet);
        this.caffeineSourceId = caffeineSourceId;

    }

    public String getCaffeineSourceId() {
        return caffeineSourceId;
    }
}
