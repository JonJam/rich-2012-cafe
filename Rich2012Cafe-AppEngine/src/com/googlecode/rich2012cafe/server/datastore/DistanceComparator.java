package com.googlecode.rich2012cafe.server.datastore;

import java.util.Comparator;

import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.utils.Rich2012CafeUtil;

/**
 * Class to sort CaffeineSource objects in ascending order of 
 * distance away from current latitude and longitude point.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class DistanceComparator implements Comparator<CaffeineSource>{
	
	private double currentLatitude;
	private double currentLongitude;
	
	public DistanceComparator(double currentLatitude, double currentLongitude){
		this.currentLatitude = currentLatitude;
		this.currentLongitude = currentLongitude;
	}

	/**
	 * Method to calculate the distance between two lat/long points.
	 * 
	 * @param latitude1 (double value)
	 * @param longitude1 (double value)
	 * @param latitude2 (double value)
	 * @param longitude2 (double value)
	 * @return double value
	 */
	private double getDistanceBetweenPoints(double latitude1, double longitude1, double latitude2, double longitude2){
		double dLat = Math.toRadians(latitude2 - latitude1);
		double dLong = Math.toRadians(longitude2 - longitude1);
		
		latitude1 = Math.toRadians(latitude1);
		latitude2 = Math.toRadians(latitude2);
		
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLong / 2) * Math.sin(dLong / 2) * Math.cos(latitude1) * Math.cos(latitude2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = Rich2012CafeUtil.EARTH_RADIUS * c;
		
		return d;
	}
	
	/**
	 * Method to compare two CaffeineSource objects.
	 * 
	 * @param o1 (CaffeineSource object)
	 * @param o2 (CaffeineSource object)
	 * @return int value
	 */
	@Override
	public int compare(CaffeineSource o1, CaffeineSource o2) {
		
		double d1 = getDistanceBetweenPoints(currentLatitude, currentLongitude, o1.getBuildingLat(), o1.getBuildingLong());
		double d2 = getDistanceBetweenPoints(currentLatitude, currentLongitude, o2.getBuildingLat(), o2.getBuildingLong());
		
		if( d1 > d2){
			return 1;
		} else if (d1 < d2){
			return -1;
		} else{
			return 0;
		}
	}
}
