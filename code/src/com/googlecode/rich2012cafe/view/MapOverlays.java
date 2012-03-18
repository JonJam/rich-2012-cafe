package com.googlecode.rich2012cafe.view;

import android.app.AlertDialog;
import android.content.Context;
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
public abstract class MapOverlays extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

    private Context context;

    public MapOverlays(Drawable defaultMarker, Context context) {
        super(boundCenterBottom(defaultMarker));
        this.context = context;
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

    @Override
    protected boolean onTap(int index) {
        OverlayItem item =  overlays.get(index);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();

        return true;
    }

    protected Context getContext() {
        return context;
    }

    protected ArrayList<OverlayItem> getOverlays() {
        return overlays;
    }


}

