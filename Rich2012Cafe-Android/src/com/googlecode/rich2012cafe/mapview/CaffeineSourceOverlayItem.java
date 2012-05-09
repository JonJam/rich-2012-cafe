package com.googlecode.rich2012cafe.mapview;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.OpeningTimeProxy;

/**
 * @author Michael Elkins (thorsion@gmail.com), Craig Saunders (mrman2289@gmail.com)
 */

public class CaffeineSourceOverlayItem extends OverlayItem {

	private String caffeineSourceId;
	private String buildingNumber;
	private List<CaffeineSourceProductProxy> caffeineSourceList;
	private List<OpeningTimeProxy> openingTimes;
	private CaffeineSourceProxy source;

    public CaffeineSourceOverlayItem(GeoPoint geoPoint, String title, String snippet, String caffeineSourceId) {

        super(geoPoint, title, snippet);
        this.caffeineSourceId = caffeineSourceId;
        

    }

    public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public List<CaffeineSourceProductProxy> getCaffeineSourceList() {
		return caffeineSourceList;
	}

	public List<OpeningTimeProxy> getOpeningTimes() {
		return openingTimes;
	}

	public CaffeineSourceProxy getSource() {
		return source;
	}

	public void setCaffeineSourceId(String caffeineSourceId) {
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
