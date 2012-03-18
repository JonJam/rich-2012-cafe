package com.googlecode.rich2012cafe.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.OverlayItem;
import com.googlecode.rich2012cafe.activities.BuildingDetails;

/**
 * User: CS
 * Date: 15/03/12
 * Time: 11:41
 */
public class CaffeineSourcesLocationOverlay extends MapOverlays {

    public CaffeineSourcesLocationOverlay(Drawable defaultMarker, Context context) {
        super(defaultMarker, context);
    }

    protected boolean onTap(int index) {

        OverlayItem item =  getOverlays().get(index);

        Intent intent = new Intent(getContext(), BuildingDetails.class);

//        Bundle b = new Bundle();
//
//        b.putInt();
        getContext().startActivity(intent);

        return true;
    }

}
