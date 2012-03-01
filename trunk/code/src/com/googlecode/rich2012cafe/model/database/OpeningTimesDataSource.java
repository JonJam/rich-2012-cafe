package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object class for OpeningTime objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class OpeningTimesDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { 
			DatabaseHelper.COLUMN_OPENING_TIME_ID,
			DatabaseHelper.COLUMN_OPENING_TIME_CAFFEINE_SOURCE_ID,
			DatabaseHelper.COLUMN_DAY,
			DatabaseHelper.COLUMN_OPENING_TIME,
			DatabaseHelper.COLUMN_CLOSING_TIME,
			DatabaseHelper.COLUMN_VALID_FROM,
			DatabaseHelper.COLUMN_VALID_TO
		};
		
	public OpeningTimesDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
		
	/**
	 * Method to open data connection.
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
		
	/*
	 * Method to close data connection.
	 */
	public void close() {
		dbHelper.close();
	}
		
	/**
	 * Method to insert an OpeningTime object into the database.
	 * 
	 * @param openingTime (OpeningTime object)
	 */
	public void insertOpeningTime(OpeningTime openingTime) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_OPENING_TIME_ID , openingTime.getId());
		values.put(DatabaseHelper.COLUMN_OPENING_TIME_CAFFEINE_SOURCE_ID , openingTime.getCaffeineSourceId());
		values.put(DatabaseHelper.COLUMN_DAY , openingTime.getDay());
		values.put(DatabaseHelper.COLUMN_OPENING_TIME , openingTime.getOpeningTime());
		values.put(DatabaseHelper.COLUMN_CLOSING_TIME , openingTime.getClosingTime());
		values.put(DatabaseHelper.COLUMN_VALID_FROM , openingTime.getValidFrom());
		values.put(DatabaseHelper.COLUMN_VALID_TO , openingTime.getValidTo());
	
		database.insert(DatabaseHelper.TABLE_OPENING_TIMES, null, values);
	}
		
	/**
	 * Method to get all OpeningTime objects in the database.
	 * 
	 * @return ArrayList of OpeningTime objects.
	 */
	public ArrayList<OpeningTime> getAllOpeningTimes() {
		
		ArrayList<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_OPENING_TIMES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create OpeningTime objects.
			
			OpeningTime openingTime = new OpeningTime(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getString(4) , cursor.getString(5), cursor.getString(6));
			
			openingTimes.add(openingTime);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return openingTimes;
	}
	
	/**
	 * Method to get all expired OpeningTime objects from the database.
	 * 
	 * @return ArrayList of OpeningTime objects
	 */
	public ArrayList<OpeningTime> getExpiredOpeningTimes(){
		ArrayList<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_OPENING_TIMES, allColumns, "datetime(validTo) < datetime('now')", 
				null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create OpeningTime objects.
			
			OpeningTime openingTime = new OpeningTime(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getString(4) , cursor.getString(5), cursor.getString(6));
			
			openingTimes.add(openingTime);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return openingTimes;
						
	}
	
	/**
	 * Method to delete all OpeningTime objects in the database.
	 */
	public void deleteAllOpeningTimes(){
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_OPENING_TIMES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and delete all OpeningTime objects.
			
			database.delete(DatabaseHelper.TABLE_OPENING_TIMES, DatabaseHelper.COLUMN_OPENING_TIME_ID + " = '" + cursor.getString(0) + "'", null);
			cursor.moveToNext();
		}
		
		cursor.close();
	}
}
