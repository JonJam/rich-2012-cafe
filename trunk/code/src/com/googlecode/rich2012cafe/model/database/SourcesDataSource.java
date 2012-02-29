package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SourcesDataSource{

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { 
			DatabaseHelper.COLUMN_SOURCE_ID,
			DatabaseHelper.COLUMN_SOURCE_NAME,
			DatabaseHelper.COLUMN_BUILDING_NUMBER,
			DatabaseHelper.COLUMN_BUILDING_NAME,
			DatabaseHelper.COLUMN_BUILDING_LAT,
			DatabaseHelper.COLUMN_BUILDING_LONG
		};
	
	public SourcesDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void insertCaffeineSource(CaffeineSource caffeineSource) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_SOURCE_ID , caffeineSource.getId());
		values.put(DatabaseHelper.COLUMN_SOURCE_NAME , caffeineSource.getName());
		values.put(DatabaseHelper.COLUMN_BUILDING_NUMBER , caffeineSource.getBuildingNumber());
		values.put(DatabaseHelper.COLUMN_BUILDING_NAME , caffeineSource.getBuildingName());
		values.put(DatabaseHelper.COLUMN_BUILDING_LAT , caffeineSource.getBuildingLat());
		values.put(DatabaseHelper.COLUMN_BUILDING_LONG , caffeineSource.getBuildingLong());
		
		database.insert(DatabaseHelper.TABLE_SOURCES, null, values);
	}
	
	public void deleteCaffeineSource(CaffeineSource caffeineSource) {
		
		String id = caffeineSource.getId();
		database.delete(DatabaseHelper.TABLE_SOURCES, DatabaseHelper.COLUMN_SOURCE_ID + " = " + id, null);
	}
	
	public ArrayList<CaffeineSource> getAllCaffeineSources() {
		
		ArrayList<CaffeineSource> caffeineSources = new ArrayList<CaffeineSource>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_SOURCES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CaffeineSource source = new CaffeineSource(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getDouble(4) , cursor.getDouble(5));
			
			caffeineSources.add(source);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return caffeineSources;
	}
}
