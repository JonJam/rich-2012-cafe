package com.googlecode.rich2012cafe.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.util.Log;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.googlecode.rich2012cafe.calendar.CalendarEvent;
import com.googlecode.rich2012cafe.calendar.CalendarReader;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.model.CaffeineLevel;
import com.googlecode.rich2012cafe.model.CaffeineLevelReader;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.utils.Util;

public class CaffeineTracker extends Activity {

	private final static int HALF_LIFE = 240;
	private final static int OPTIMAL_CAFFEINE_UPPER_LIMIT = 250;
	private final static int OPTIMAL_CAFFEINE_LOWER_LIMIT = 175;
	private final static int CAFFEINE_BUFFER = 10;

	private final static String HISTORIC_VALUES_SETTING_NAME = "historicCaffeineValues";
	private final static String PROJECTED_VALUES_SETTING_NAME = "projectedCaffeineValues";
	
	private TreeMap<Integer, CaffeineProductProxy> productsTree = null;
	private TreeMap<Date, Integer> projectedLevels;
	
	CalendarReader cReader;
	int startCaffeineLevel = 50;
	Context mContext = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        CalendarReader cReader = new CalendarReader();
		ArrayList<CalendarEvent> todaysEvents = cReader.getTodaysEvents(this);
		
		CaffeineLevelReader reader = new CaffeineLevelReader(this);
		TreeMap<Date, Integer> caffeineLevels = reader.getCaffeineLevels(HISTORIC_VALUES_SETTING_NAME);
		projectedLevels = reader.getCaffeineLevels(PROJECTED_VALUES_SETTING_NAME);
		

		
		new AsyncTask<Void, Void, List<CaffeineProductProxy>>(){
			
			private List<CaffeineProductProxy> caffeineProducts;

			@Override
			protected List<CaffeineProductProxy> doInBackground(Void... params) {
				MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
				requestFactory.rich2012CafeRequest().getAllCaffeineProducts().fire(new Receiver<List<CaffeineProductProxy>>(){
					
					@Override
					public void onSuccess(List<CaffeineProductProxy> products) {
						caffeineProducts = products;
					}
		        	
					@Override
		            public void onFailure(ServerFailure error) {
		                
		            }
					
		        });
				
				return caffeineProducts;
			}
			
		    @Override
		    protected void onPostExecute(List<CaffeineProductProxy> result) {
		    	storeCaffeineProducts(result);
		    }
		    
		}.execute();
	
		Log.e("t-msg", "finished async");
		Entry<Date, Integer> lastLevelReading;
				
		if (caffeineLevels.isEmpty()) {
			Log.e("T-msg", "empty caffeine level");
			//Should never happen? -- Need to work out how we hand-over from the previous day
			
		} else {
			Log.i("t-mas", "not empty wooo");
			lastLevelReading = caffeineLevels.lastEntry();
			parseEvents(todaysEvents, lastLevelReading);
			
		}
			
//		if (caffeineValues.isEmpty()) {
//			
//			CalendarEvent calendarEvent = todaysEvents.get(0);
//			
//		} else {
//			
//			//interpret the JSON string
//			
//		}	
    }
	
	private void storeCaffeineProducts(List<CaffeineProductProxy> list) {
		
		productsTree = new TreeMap<Integer, CaffeineProductProxy>();
		
		
		for (CaffeineProductProxy c: list) { //loop through the products and insert them to the tree
 			productsTree.put((int) Math.round(c.getCaffeineContent()), c);
		}
		
	}
	
	
	private void parseEvents(ArrayList<CalendarEvent> todaysEvents, Entry<Date, Integer> lastLevel) {
		
		TreeMap<Date, Integer> newProjectedLevels = new TreeMap<Date, Integer>();
		TreeMap<Date, Integer> suggestedIntakes = new TreeMap<Date, Integer>();
		
		ArrayList<CalendarEvent> mergedEvents = mergeBackToBackEvents(todaysEvents);
		
		Entry<Date, Integer> tempPreviousLevel = lastLevel; 	

	
		for (CalendarEvent e: mergedEvents) {
		
			Log.e("T-msg", "within event");
			long startTime = e.getStartTime();
			long endTime = e.getEndTime();	
			
			Calendar eventStartTime = Calendar.getInstance();
			eventStartTime.setTimeInMillis(startTime);
			
			Calendar eventEndTime = Calendar.getInstance();
			eventEndTime.setTimeInMillis(endTime);
			
			Calendar previousTime = Calendar.getInstance();
			previousTime.setTime(tempPreviousLevel.getKey());
			
			if (eventStartTime.before(previousTime)) { //Does this event start before the time we have recorded up to 
				continue;
			} else {
				
				int minutesDifference = calculateMinutesDifference(eventStartTime, previousTime);
				int projectedCaffeineLevel = calculateCaffeineLevel(tempPreviousLevel.getValue(), minutesDifference);
				CaffeineLevel requiredIntake;
				
				if (projectedCaffeineLevel < getOptimalCaffeineLowerLimit()) { //caffeine level below optimal for start of event
					
					try {
						
						requiredIntake = calculateCaffeineIntake(eventStartTime, eventEndTime, previousTime, tempPreviousLevel.getValue());
						suggestedIntakes.put(requiredIntake.getTime(), requiredIntake.getLevel());
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					
					int minutesDifferenceToEndOfEvent = calculateMinutesDifference(eventEndTime, previousTime);
					int projectedLevelEndOfEvent = calculateCaffeineLevel(tempPreviousLevel.getValue(), minutesDifferenceToEndOfEvent);
					
					if (projectedLevelEndOfEvent > getOptimalCaffeineLowerLimit()) {
						
						continue;
						
					} else { //caffeine level drops below optimal during the event
						
						try {
							
							requiredIntake = calculateCaffeineIntake(eventStartTime, eventEndTime, previousTime, tempPreviousLevel.getValue());
							suggestedIntakes.put(requiredIntake.getTime(), requiredIntake.getLevel()); 
							//put the time and the amount of caffeine to be consumed
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}			
				}
			}
		}
		
		Date projectedCurrentDate = lastLevel.getKey();
		Integer projectedCurrentLevel = lastLevel.getValue();
		
		Calendar projectedCurrentCalendar = Calendar.getInstance();
		projectedCurrentCalendar.setTime(projectedCurrentDate);
		
		Calendar firstSuggestedIntakeCalendar = Calendar.getInstance();
		firstSuggestedIntakeCalendar.setTime(suggestedIntakes.firstKey());
		
		while (projectedCurrentCalendar.before(firstSuggestedIntakeCalendar)) {
			newProjectedLevels.put(projectedCurrentCalendar.getTime(), projectedCurrentLevel);
			projectedCurrentCalendar.add(Calendar.MINUTE, 15);
			projectedCurrentLevel = calculateCaffeineLevel(projectedCurrentLevel, 15);
		}
		
		newProjectedLevels.put(projectedCurrentCalendar.getTime(), projectedCurrentLevel);
		
		Iterator<Date> iterator = suggestedIntakes.keySet().iterator();
		
		Integer caffeineBoost; 
		
		while (iterator.hasNext()) {
			
			Date next = iterator.next();
			Calendar tempCal = Calendar.getInstance();
			tempCal.setTime(next);

			int minsDiff = calculateMinutesDifference(tempCal, projectedCurrentCalendar);
			projectedCurrentLevel = calculateCaffeineLevel(projectedCurrentLevel, minsDiff);
			newProjectedLevels.put(next, projectedCurrentLevel);
			
			caffeineBoost = suggestedIntakes.get(next);
			projectedCurrentCalendar.setTime(next);
						
			int increment = Math.round(caffeineBoost / 4);
			
			for (int i = 1; i <= 3; i++) {
				projectedCurrentCalendar.add(Calendar.MINUTE, 15);
				newProjectedLevels.put(projectedCurrentCalendar.getTime(), projectedCurrentLevel + (i * increment));
			}
			
			projectedCurrentCalendar.add(Calendar.MINUTE, 15);
			newProjectedLevels.put(projectedCurrentCalendar.getTime(), projectedCurrentLevel + caffeineBoost);
			
			projectedCurrentCalendar.add(Calendar.MINUTE, 15);
			
			if (suggestedIntakes.higherKey(projectedCurrentCalendar.getTime()) != null) {
			
				while (projectedCurrentCalendar.before(suggestedIntakes.higherKey(projectedCurrentCalendar.getTime()))) {
					
					projectedCurrentLevel = calculateCaffeineLevel(projectedCurrentLevel, 15);
					newProjectedLevels.put(projectedCurrentCalendar.getTime(), projectedCurrentLevel);					
				}
			}
		}
		
		projectedLevels = newProjectedLevels;
		
	}
	
	private CaffeineLevel calculateCaffeineIntake(Calendar eventStartTime, Calendar eventEndTime, Calendar lastLevelTime, Integer lastCaffeineLevel) throws Exception {
		
		int eventHour = eventStartTime.get(Calendar.HOUR_OF_DAY);
		
		if (eventHour != 0) {
			
			int hourBefore = eventHour -1;
		
			Calendar hourBeforeTime = (Calendar) eventStartTime.clone();
			hourBeforeTime.set(Calendar.HOUR_OF_DAY, hourBefore);
			
			int minutesDifference = calculateMinutesDifference(lastLevelTime, hourBeforeTime);
			int caffeineLevelHourBefore = calculateCaffeineLevel(lastCaffeineLevel, minutesDifference);
		
			int eventDuration = calculateMinutesDifference(eventEndTime, eventEndTime);
			
			int requiredStartingCaffeineLevel = calculateStartingCaffeineLevelForSpecifiedEndLevel(eventDuration);	
			int requiredCaffeineDifference = requiredStartingCaffeineLevel - lastCaffeineLevel;
			
			Integer closestKey, counter = 0;
			
			while (getProductsTree() == null && counter < 5) {
				getProductsTree().wait(1000);
			}
			
			if (getProductsTree() == null) {
				throw new Exception("No caffeine products");
			} else {
					
				if ((closestKey = getProductsTree().ceilingKey(requiredCaffeineDifference)) == null) {
					closestKey = getProductsTree().lastKey();
				}
				
				return new CaffeineLevel(hourBeforeTime.getTime(), closestKey);
				
			}
		} else {
			
			throw new Exception("Event is too close to start of the day (before 1AM) -- possible issue");
			
		}
		
	}
	
	private ArrayList<CalendarEvent> mergeBackToBackEvents(ArrayList<CalendarEvent> eventsArray) {
		
		long previousEndTime = 0L;
		
		ArrayList<CalendarEvent> mergedEvents = new ArrayList<CalendarEvent>();
		
		for (CalendarEvent e: eventsArray) {
			
			if (previousEndTime == e.getStartTime()) {
				mergedEvents.get(mergedEvents.size()).setEndTime(e.getEndTime());
			} else {	
				mergedEvents.add(e);
			}
			
			previousEndTime = e.getEndTime();
			
		}
		
		return mergedEvents;
		
	} 
	
	
	private Calendar timeRoundedToCurrentHour(Calendar currentTime) {
		currentTime.set(Calendar.MILLISECOND, 0);
		currentTime.set(Calendar.SECOND, 0);
		currentTime.set(Calendar.MINUTE, 0);
		
		return currentTime;
	}
	
	private int calculateCaffeineLevel(int currentLevel, int minutes) {
		//Formula: currentLevel * 0.5^(minutes/HALF-LIFE)
		double caffeineLevel = currentLevel * Math.pow(0.5, (minutes / getHalfLife() ));
		return (int) Math.round(caffeineLevel);
			
	}
	
	private int calculateStartingCaffeineLevelForSpecifiedEndLevel(int minutes) {
		//Formula: Desired End level / 0.5^(minutes/HALF-LIFE
		
		double caffeineLevel = getOptimalCaffeineLowerLimit() + getCaffeineBuffer() / Math.pow(0.5, (minutes / getHalfLife() ));
		return (int) Math.round(caffeineLevel);
		
	}
	
	private int calculateMinutesDifference(Calendar eventTime, Calendar previousTime) {
		
		int hoursDifference = eventTime.get(Calendar.HOUR_OF_DAY) - previousTime.get(Calendar.HOUR_OF_DAY);
		int minutesDifference = eventTime.get(Calendar.MINUTE) - previousTime.get(Calendar.MINUTE);
		
		if (minutesDifference < 0) {
			minutesDifference = 60 - previousTime.get(Calendar.MINUTE) + previousTime.get(Calendar.MINUTE);
		}
		
		return (hoursDifference * 60) + minutesDifference;
		
		
	}

	private int getHalfLife() {
		return HALF_LIFE;
	}
	
	public static int getOptimalCaffeineUpperLimit() {
		return OPTIMAL_CAFFEINE_UPPER_LIMIT;
	}

	public static int getOptimalCaffeineLowerLimit() {
		return OPTIMAL_CAFFEINE_LOWER_LIMIT;
	}

	public static int getCaffeineBuffer() {
		return CAFFEINE_BUFFER;
	}
	
	
    public TreeMap<Integer, CaffeineProductProxy> getProductsTree() {
		return productsTree;
	}

	/**
     * Resumes the activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
    
    }

}
		
		