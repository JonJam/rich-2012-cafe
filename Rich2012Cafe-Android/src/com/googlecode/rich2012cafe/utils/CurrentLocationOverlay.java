package com.googlecode.rich2012cafe.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.OverlayItem;

/**
 * User: CS
 * Date: 15/03/12
 * Time: 11:36
 */
public class CurrentLocationOverlay extends MapOverlays {
	
	Context mContext;

    public CurrentLocationOverlay(Drawable defaultMarker, Context context) {
        super(defaultMarker, context);
        mContext = context;
    }

    @Override
    public void addOverlay(OverlayItem overlay) {

        if (!getOverlays().isEmpty()) {
            getOverlays().remove(0);
        }

        getOverlays().add(0, overlay);
        populate();
    }
    
    protected boolean onTap(int index) {
        
        

        return true;
    }

}
