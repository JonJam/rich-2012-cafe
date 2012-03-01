package com.googlecode.rich2012cafe.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.googlecode.rich2012cafe.model.database.OpeningTime;

/*
 * 
 */
public class OpeningTimeComparator implements Comparator<OpeningTime>{

	private ArrayList<String> dayNames;
	
	public OpeningTimeComparator(){
		dayNames = new ArrayList<String>();
		dayNames.add("Monday");
		dayNames.add("Tuesday");
		dayNames.add("Wednesday");
		dayNames.add("Thrusday");
		dayNames.add("Friday");
		dayNames.add("Saturday");
		dayNames.add("Sunday");
	}
			
	@Override
	public int compare(OpeningTime lhs, OpeningTime rhs) {
		
		//if()
		return 0;
	}
}
