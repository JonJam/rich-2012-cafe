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

	private static final String VENDING_MACHINE_WHERE = "type != 'Vending Machine'";
	private static final String OFF_CAMPUS_WHERE = "offCampus = 0";
	private static final String AND = " AND ";
	private static final String WHERE = "WHERE ";
	
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
		values.put(DatabaseHelper.COLUMN_OFFCAMPUS , caffeineSource.getOffCampus());
		
		database.insert(DatabaseHelper.TABLE_CAFFEINE_SOURCES, null, values);
	}
	
	/**
	 * Method to get all CaffeineSource objects in the database.
	 * 
	 * @param viewVendingMachines (boolean value)
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getAllCaffeineSources(boolean viewVendingMachines, boolean viewOffCampus) {
		
		ArrayList<CaffeineSource> caffeineSources = new ArrayList<CaffeineSource>();
		
		Cursor cursor;
		
		String whereClause = "";
		
		if(!viewVendingMachines){
			whereClause += VENDING_MACHINE_WHERE;
		}
		
		if(!viewOffCampus){
			if(!whereClause.equals("")) whereClause += AND;
			whereClause +=  OFF_CAMPUS_WHERE;
		}
		
	
		cursor = database.rawQuery("SELECT * FROM caffeineSources " 
				+ (whereClause.equals("") ? "" : WHERE + whereClause)
				+ " ORDER BY buildingNumber ASC", new String[] {});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create FavouriteCaffeineSource objects.
			
			CaffeineSource source = new CaffeineSource(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getDouble(4) , cursor.getDouble(5), cursor.getString(6), cursor.getInt(7));
			
			caffeineSources.add(source);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return caffeineSources;
	}
	
	/**
	 * Method to get the specified CaffeineSource objects from the database.
	 * 
	 * @param ids (ArrayList of String objects)
	 * @param viewVendingMachines (boolean value)
	 * @return ArrayList of CaffeineSource objects.
	 */
	public ArrayList<CaffeineSource> getCaffeineSources(ArrayList<String> ids, boolean viewVendingMachines, 
			boolean viewOffCampus) {
		
		ArrayList<CaffeineSource> caffeineSources = new ArrayList<CaffeineSource>();
		
		for(String id: ids){
			
			Cursor cursor;

			String whereClause = "";
			
			if(!viewVendingMachines){
				whereClause += VENDING_MACHINE_WHERE;
			}
			
			if(!viewOffCampus){
				if(!whereClause.equals("")) whereClause += AND;
				whereClause +=  OFF_CAMPUS_WHERE;
			}
			
		
			cursor = database.rawQuery("SELECT * FROM caffeineSources WHERE " 
					+ "id = ? "
					+ (whereClause.equals("") ? "" : AND + whereClause), new String[] {id});
			
			if(viewVendingMachines){
				//Include vending machine locations
				cursor = database.rawQuery("SELECT * FROM caffeineSources WHERE id = ?", new String[]{id});
			} else {
				//Don't include vending machine locations.
				cursor = database.rawQuery("SELECT * FROM caffeineSources WHERE id = ? AND type != ? ", new String[] {id, "Vending Machine"});
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				
				//Iterate through table and create CaffeineSource objects.
				
				CaffeineSource source = new CaffeineSource(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
						cursor.getString(3) , cursor.getDouble(4) , cursor.getDouble(5), cursor.getString(6), cursor.getInt(7));
				
				caffeineSources.add(source);
				cursor.moveToNext();
			}
			
			cursor.close();
		}
		
		return caffeineSources;
	}
	
	/**
	 * Method to delete all CaffeineSource objects in the database.
	 */
	public void deleteAllCaffeineSources(){
		
		Cursor cursor = database.rawQuery("SELECT * FROM caffeineSources", new String[] {});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and delete all CaffeineSource objects.
			
			database.delete(DatabaseHelper.TABLE_CAFFEINE_SOURCES, DatabaseHelper.COLUMN_CAFFEINE_SOURCE_ID + " = '" + cursor.getString(0) + "'", null);
			cursor.moveToNext();
		}
		
		cursor.close();
	}
}
