package com.googlecode.rich2012cafe.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "caffeine_sources.db";
	private static final int DATABASE_VERSION = 1;
	
	//TABLES
	public static final String TABLE_SOURCES = "sources";
	public static final String COLUMN_SOURCE_ID = "_id";
	public static final String COLUMN_SOURCE_NAME = "name";
	public static final String COLUMN_BUILDING_NUMBER = "buildingNnumber";
	public static final String COLUMN_BUILDING_NAME = "buildingName";
	public static final String COLUMN_BUILDING_LAT = "buildingLat";
	public static final String COLUMN_BUILDING_LONG = "buildingLong";
	
	public static final String TABLE_OPENING_TIMES = "opening_times";
	public static final String COLUMN_OPENING_TIME_ID = "_id";
	public static final String COLUMN_OPENING_TIME_CAFFEINE_SOURCE_ID = "caffeineSourceId";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_OPENING_TIME = "openingTime";
	public static final String COLUMN_CLOSTING_TIME = "closingTime";
	
	public static final String TABLE_PRODUCTS = "products";
	public static final String COLUMN_PRODUCT_ID = "_id";
	public static final String COLUMN_PRODUCT_CAFFEINE_SOURCE_ID = "caffeineSourceId";
	public static final String COLUMN_PRODUCT_NAME= "name";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_PRODUCT_TYPE= "productType";
	public static final String COLUMN_PRICE_TYPE= "productType";
	
	//SQL create statements
	private static final String DATABASE_CREATE_SOURCES = "CREATE TABLE " + TABLE_SOURCES + "( "
				+ COLUMN_SOURCE_ID + "TEXT,"
				+ COLUMN_SOURCE_NAME + "TEXT NOT NULL,"
				+ COLUMN_BUILDING_NUMBER + "TEXT NOT NULL,"
				+ COLUMN_BUILDING_NAME + "TEXT NOT NULL,"
				+ COLUMN_BUILDING_LAT  + "REAL NOT NULL,"
				+ COLUMN_BUILDING_LONG + "REAL NOT NULL,"
				+ "PRIMARY KEY (" + COLUMN_SOURCE_ID + "),"
			+");";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
