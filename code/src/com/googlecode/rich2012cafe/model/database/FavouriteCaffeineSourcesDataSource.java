package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object class for FavouriteCaffeineSource objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class FavouriteCaffeineSourcesDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public FavouriteCaffeineSourcesDataSource(Context context) {
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
	 * Method to insert an FavouriteCaffeineSource object into the database.
	 * 
	 * @param favouriteCaffeineSource (FavouriteCaffeineSource object)
	 */
	public void insertFavouriteCaffeineSource(FavouriteCaffeineSource favouriteCaffeineSource) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_FAVOURITE_CAFFEINE_SOURCE_ID , favouriteCaffeineSource.getId());
		
		database.insert(DatabaseHelper.TABLE_FAVOURITE_CAFFEINE_SOURCES, null, values);
	}
	
	/**
	 * Method to get all FavouriteCaffeineSource objects in the database.
	 * 
	 * @return ArrayList of FavouriteCaffeineSource objects.
	 */
	public ArrayList<FavouriteCaffeineSource> getAllFavouriteCaffeineSources() {
		
		ArrayList<FavouriteCaffeineSource> favouriteCaffeineSources = new ArrayList<FavouriteCaffeineSource>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM favouriteCaffeineSources", new String[] {});
	
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create CaffeineSource objects.
			
			FavouriteCaffeineSource source = new FavouriteCaffeineSource(cursor.getString(0));
			
			favouriteCaffeineSources.add(source);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return favouriteCaffeineSources;
	}
	
	/**
	 * Method to delete a favourite caffeine source.
	 * 
	 * @param favouriteCaffeineSource (FavouriteCaffeineSource object)
	 */
	public void deleteFavouriteCaffeineSource(FavouriteCaffeineSource favouriteCaffeineSource){
		
		database.delete(DatabaseHelper.TABLE_FAVOURITE_CAFFEINE_SOURCES, DatabaseHelper.COLUMN_FAVOURITE_CAFFEINE_SOURCE_ID 
				+ " = '" + favouriteCaffeineSource.getId() + "'", null);	
	}
}
