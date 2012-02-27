package com.googlecode.rich2012cafe.model;

import java.util.Comparator;

import com.googlecode.rich2012cafe.model.database.OpeningTime;

/**
 * Class to sort OpeningTime objects into descending date order.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class OpeningTimeComparator implements Comparator<OpeningTime>{

	public int compare(OpeningTime lhs, OpeningTime rhs) {
		
		if(lhs.getDate().before(rhs.getDate())){
			return 1;
		} else if(lhs.getDate().after(rhs.getDate())){
			return -1;
		} else{
			return 0;
		}
	}
}
