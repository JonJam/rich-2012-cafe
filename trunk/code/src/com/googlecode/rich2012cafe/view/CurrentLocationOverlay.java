package com.googlecode.rich2012cafe.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.OverlayItem;

/**
 * User: CS
 * Date: 15/03/12
 * Time: 11:36
 */
public class CurrentLocationOverlay extends MapOverlays {

    public CurrentLocationOverlay(Drawable defaultMarker, Context context) {
        super(defaultMarker, context);
    }

    @Override
    public void addOverlay(OverlayItem overlay) {

        if (!getOverlays().isEmpty()) {
            getOverlays().remove(0);
        }

        getOverlays().add(0, overlay);
        populate();
    }

}
