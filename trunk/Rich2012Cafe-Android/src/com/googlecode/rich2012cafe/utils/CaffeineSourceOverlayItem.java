package com.googlecode.rich2012cafe.utils;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.OpeningTimeProxy;

/**
 * User: CS
 * Date: 18/03/12
 * Time: 16:01
 */
public class CaffeineSourceOverlayItem extends OverlayItem {

	public String caffeineSourceId;
    public String buildingNumber;
    public List<CaffeineSourceProductProxy> caffeineSourceList;
    public List<OpeningTimeProxy> openingTimes;
    public CaffeineSourceProxy source;

    public CaffeineSourceOverlayItem(GeoPoint geoPoint, String title, String snippet, String caffeineSourceId) {

        super(geoPoint, title, snippet);
        this.caffeineSourceId = caffeineSourceId;
        

    }

    public String getCaffeineSourceId() {
        return caffeineSourceId;
    }
    
    public void setCaffeineSourceList(List<CaffeineSourceProductProxy> products){
    	caffeineSourceList = products;
    }
    
    public void setOpeningTimes(List<OpeningTimeProxy> openingTimes){
    	this.openingTimes = openingTimes;
    }
    
    public void setSource(CaffeineSourceProxy source){
    	this.source = source;
    }
}
