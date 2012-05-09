package com.googlecode.rich2012cafe.mapview;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.OverlayItem;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public class CurrentLocationOverlay extends MapOverlays {
	
	private Context mContext;

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
