package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object class for CaffeineSource objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineSourcesDataSource{

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { 
			DatabaseHelper.COLUMN_CAFFEINE_SOURCE_ID,
			DatabaseHelper.COLUMN_CAFFEINE_SOURCE_NAME,
			DatabaseHelper.COLUMN_BUILDING_NUMBER,
			DatabaseHelper.COLUMN_BUILDING_NAME,
			DatabaseHelper.COLUMN_BUILDING_LAT,
			DatabaseHelper.COLUMN_BUILDING_LONG,
			DatabaseHelper.COLUMN_TYPE
		};
	
	public CaffeineSourcesDataSource(Context context) {
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
	 * Method to insert an CaffeineSource object into the database.
	 * 
	 * @param product (CaffeineSource object)
	 */
	public void insertCaffeineSource(CaffeineSource caffeineSource) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_CAFFEINE_SOURCE_ID , caffeineSource.getId());
		values.put(DatabaseHelper.COLUMN_CAFFEINE_SOURCE_NAME , caffeineSource.getName());
		values.put(DatabaseHelper.COLUMN_BUILDING_NUMBER , caffeineSource.getBuildingNumber());
		values.put(DatabaseHelper.COLUMN_BUILDING_NAME , caffeineSource.getBuildingName());
		values.put(DatabaseHelper.COLUMN_BUILDING_LAT , caffeineSource.getBuildingLat());
		values.put(DatabaseHelper.COLUMN_BUILDING_LONG , caffeineSource.getBuildingLong());
		values.put(DatabaseHelper.COLUMN_TYPE , caffeineSource.getType());
		
		database.insert(DatabaseHelper.TABLE_CAFFEINE_SOURCES, null, values);
	}
	
	/**
	 * Method to get all CaffeineSource objects in the database.
	 * 
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getAllCaffeineSources() {
		
		ArrayList<CaffeineSource> caffeineSources = new ArrayList<CaffeineSource>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_CAFFEINE_SOURCES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create CaffeineSource objects.
			
			CaffeineSource source = new CaffeineSource(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getDouble(4) , cursor.getDouble(5), cursor.getString(6));
			
			caffeineSources.add(source);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return caffeineSources;
	}
	
	/**
	 * Method to delete all CaffeineSource objects in the database.
	 */
	public void deleteAllCaffeineSources(){
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_CAFFEINE_SOURCES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and delete all CaffeineSource objects.
			
			database.delete(DatabaseHelper.TABLE_CAFFEINE_SOURCES, DatabaseHelper.COLUMN_CAFFEINE_SOURCE_ID + " = '" + cursor.getString(0) + "'", null);
			cursor.moveToNext();
		}
		
		cursor.close();
	}
}
