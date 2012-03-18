package com.googlecode.rich2012cafe.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

        CaffeineSourceOverlayItem item = (CaffeineSourceOverlayItem) getOverlays().get(index);

        Intent intent = new Intent(getContext(), BuildingDetails.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putString("source_id", item.getCaffeineSourceId());
        
        intent.putExtras(dataBundle);

        getContext().startActivity(intent);

        return true;
    }

}
