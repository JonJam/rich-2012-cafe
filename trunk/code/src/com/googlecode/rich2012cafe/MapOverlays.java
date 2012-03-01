package com.googlecode.rich2012cafe;

import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * User: CS
 * Date: 01/03/12
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class MapOverlays extends ItemizedOverlay<OverlayItem> {
    
    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

    public MapOverlays(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
    }
            

    @Override
    protected OverlayItem createItem(int i) {
        return overlays.get(i);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    public void addOverlay(OverlayItem overlay) {
        overlays.add(overlay);
        populate();
    }
}

