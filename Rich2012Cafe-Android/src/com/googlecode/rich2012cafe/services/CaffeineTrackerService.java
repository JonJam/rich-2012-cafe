package com.googlecode.rich2012cafe.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.googlecode.rich2012cafe.ApplicationState;
import com.googlecode.rich2012cafe.caffeinelevel.CaffeineLevel;
import com.googlecode.rich2012cafe.caffeinelevel.CaffeineLevelReader;
import com.googlecode.rich2012cafe.caffeinelevel.CaffeineLevelWriter;
import com.googlecode.rich2012cafe.calendar.CalendarController;
import com.googlecode.rich2012cafe.calendar.CalendarEvent;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import com.googlecode.rich2012cafe.utils.ScheduledTasks;


/**
 * @author Craig Saunders (mrman2289@gmail.com), Pratik Patel (p300ss@gmail.com)
 */

public class CaffeineTrackerService extends Service {


	private TreeMap<Integer, CaffeineProductProxy> productsTree = null;
	private TreeMap<Date, Integer> projectedLevels;
	private CaffeineLevel tempPreviousLevel; 
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {

		Log.e(CaffeineTrackerService.class.getName(), "Calling the service");
        CalendarController cReader = new CalendarController();
		ArrayList<CalendarEvent> todaysEvents = cReader.getTodaysEvents(this);
		
		CaffeineLevelReader reader = new CaffeineLevelReader(this);
		TreeMap<Date, Integer> caffeineLevels = reader.getCaffeineLevels(Rich2012CafeUtil.HISTORIC_VALUES_SETTING_NAME);
		projectedLevels = reader.getCaffeineLevels(Rich2012CafeUtil.PROJECTED_VALUES_SETTING_NAME);

		ApplicationState appState = (ApplicationState) this.getApplicationContext();
		List<CaffeineProductProxy> caffeineProducts;
		
		if ((caffeineProducts = appState.getCaffeineProducts()) == null) {
			ScheduledTasks.getCaffeineProducts(this, true);
			caffeineProducts = appState.getCaffeineProducts();
		}
		
		storeCaffeineProducts(caffeineProducts);
		
		Entry<Date, Integer> lastLevelReading;
				
		if (caffeineLevels.isEmpty()) {
			
			Calendar timeNow = Calendar.getInstance();
			Calendar timeRoundedToCurrentHour = timeRoundedToCurrentHour(timeNow);
			timeRoundedToCurrentHour.set(Calendar.HOUR_OF_DAY, 0);
			
			Log.e(CaffeineTrackerService.class.getName(), timeRoundedToCurrentHour.toString() + " < time");
			
			caffeineLevels.put(timeRoundedToCurrentHour.getTime(), 0);
			
			CaffeineLevelWriter caffeineLevelWriter = new CaffeineLevelWriter(this);
			caffeineLevelWriter.writeNewCaffeineLevels(caffeineLevels, Rich2012CafeUtil.HISTORIC_VALUES_SETTING_NAME);
			
			Log.e(CaffeineTrackerService.class.getName(), "empty caffeine level");
			
		} else {
			Log.i(CaffeineTrackerService.class.getName(), "not empty wooo");
			
		}
		
		lastLevelReading = caffeineLevels.lastEntry();
		Log.e(CaffeineTrackerService.class.getName(), lastLevelReading.getKey() +","+lastLevelReading.getValue()+"");
		parseEvents(todaysEvents, lastLevelReading);
		
		
		
		//this.finish();
		return Service.START_FLAG_REDELIVERY;

    }
	
	private void storeCaffeineProducts(List<CaffeineProductProxy> list) {
		
		productsTree = new TreeMap<Integer, CaffeineProductProxy>();
		
		for (CaffeineProductProxy c: list) { //loop through the products and insert them to the tree
 			productsTree.put((int) Math.round(c.getCaffeineContent()), c);
		}
		
	}
	
	
	private void parseEvents(ArrayList<CalendarEvent> todaysEvents, Entry<Date, Integer> lastLevel) {
		
		Log.i(CaffeineTrackerService.class.getName(), "Got to parsing method");
		
		TreeMap<Date, Integer> newProjectedLevels = new TreeMap<Date, Integer>();
		TreeMap<Date, Integer> suggestedIntakes = new TreeMap<Date, Integer>();
		
		if (!todaysEvents.isEmpty()) {
			
			Log.i(CaffeineTrackerService.class.getName(), "Have events");
		
			ArrayList<CalendarEvent> mergedEvents = mergeBackToBackEvents(todaysEvents);
			
			tempPreviousLevel = new CaffeineLevel(lastLevel.getKey(), lastLevel.getValue()); 	
	
			for (CalendarEvent e: mergedEvents) {
			
				Log.e(CaffeineTrackerService.class.getName(), "within event");
				long startTime = e.getStartTime();
				long endTime = e.getEndTime();	
				
				Calendar eventStartTime = Calendar.getInstance();
				eventStartTime.setTimeInMillis(startTime);
				
				Calendar eventEndTime = Calendar.getInstance();
				eventEndTime.setTimeInMillis(endTime);
				
				Calendar previousTime = Calendar.getInstance();
				previousTime.setTime(tempPreviousLevel.getTime());
				
				if (eventStartTime.before(previousTime)) { //Does this event start before the time we have recorded up to 
					continue;
				} else {
					
					int minutesDifference = calculateMinutesDifference(eventStartTime, previousTime);
					int projectedCaffeineLevel = calculateCaffeineLevel(tempPreviousLevel.getLevel(), minutesDifference);
					CaffeineLevel requiredIntake;
					
					if (projectedCaffeineLevel < Rich2012CafeUtil.OPTIMAL_CAFFEINE_LOWER_LIMIT) { //caffeine level below optimal for start of event
						
						try {
							
							requiredIntake = calculateCaffeineIntake(eventStartTime, eventEndTime, previousTime, tempPreviousLevel.getLevel());
							suggestedIntakes.put(requiredIntake.getTime(), requiredIntake.getLevel());
						
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} else {
						
						int minutesDifferenceToEndOfEvent = calculateMinutesDifference(eventEndTime, previousTime);
						int projectedLevelEndOfEvent = calculateCaffeineLevel(tempPreviousLevel.getLevel(), minutesDifferenceToEndOfEvent);
						
						if (projectedLevelEndOfEvent > Rich2012CafeUtil.OPTIMAL_CAFFEINE_LOWER_LIMIT) {
							
							continue;
							
						} else { //caffeine level drops below optimal during the event
							
							try {
								
								requiredIntake = calculateCaffeineIntake(eventStartTime, eventEndTime, previousTime, tempPreviousLevel.getLevel());
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
		
			
		} else {
			Log.i(CaffeineTrackerService.class.getName(), "Calendar empty");
		}
			
			
		
		if (!suggestedIntakes.isEmpty()) {
	
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
			
		}	
		
		projectedLevels = newProjectedLevels;
		
	}
	
	private CaffeineLevel calculateCaffeineIntake(Calendar eventStartTime, Calendar eventEndTime, Calendar lastLevelTime, Integer lastCaffeineLevel) throws Exception {
		
		int eventHour = eventStartTime.get(Calendar.HOUR_OF_DAY);
		
		if (eventHour != 0) {
			
			int hourBefore = eventHour -1;
		
			Calendar hourBeforeTime = (Calendar) eventStartTime.clone();
			hourBeforeTime.set(Calendar.HOUR_OF_DAY, hourBefore);
			
			int minutesDifference = calculateMinutesDifference(hourBeforeTime, lastLevelTime);
			int caffeineLevelHourBefore = calculateCaffeineLevel(lastCaffeineLevel, minutesDifference);
		
			int eventDuration = calculateMinutesDifference(eventEndTime, eventStartTime);
			
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
				
				tempPreviousLevel.setTime(eventStartTime.getTime());
				tempPreviousLevel.setLevel( (caffeineLevelHourBefore + closestKey) );
				
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
				mergedEvents.get( (mergedEvents.size() - 1) ).setEndTime(e.getEndTime());
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
	
	private int calculateCaffeineLevel(Integer currentLevel, Integer minutes) {
		//Formula: currentLevel * 0.5^(minutes/HALF-LIFE)
		double caffeineLevel = currentLevel * Math.pow(0.5, (minutes.doubleValue() / Rich2012CafeUtil.HALF_LIFE ));
		return (int) Math.round(caffeineLevel);
			
	}
	
	private int calculateStartingCaffeineLevelForSpecifiedEndLevel(Integer minutes) {
		//Formula: Desired End level / 0.5^(minutes/HALF-LIFE
		
		int endLevel = Rich2012CafeUtil.OPTIMAL_CAFFEINE_LOWER_LIMIT +  Rich2012CafeUtil.CAFFEINE_BUFFER;
		double power = minutes.doubleValue() / Rich2012CafeUtil.HALF_LIFE;
		double caffeineLevel = endLevel / Math.pow(0.5, power);
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


	
    public TreeMap<Integer, CaffeineProductProxy> getProductsTree() {
		return productsTree;
	}


}
		
		