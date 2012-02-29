package com.googlecode.rich2012cafe.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *  Resources:
 *  - http://www.vogella.de/articles/AndroidSQLite/article.html
 *  - http://mobile.tutsplus.com/tutorials/android/android-sqlite/
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "caffeine_sources.db";
	private static final int DATABASE_VERSION = 1;
	
	//TABLES
	public static final String TABLE_SOURCES = "sources";
	public static final String COLUMN_SOURCE_ID = "id";
	public static final String COLUMN_SOURCE_NAME = "name";
	public static final String COLUMN_BUILDING_NUMBER = "buildingNnumber";
	public static final String COLUMN_BUILDING_NAME = "buildingName";
	public static final String COLUMN_BUILDING_LAT = "buildingLat";
	public static final String COLUMN_BUILDING_LONG = "buildingLong";
	
	public static final String TABLE_OPENING_TIMES = "opening_times";
	public static final String COLUMN_OPENING_TIME_ID = "id";
	public static final String COLUMN_OPENING_TIME_CAFFEINE_SOURCE_ID = "caffeineSourceId";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_OPENING_TIME = "openingTime";
	public static final String COLUMN_CLOSTING_TIME = "closingTime";
	public static final String COLUMN_VALID_FROM = "validFrom";
	public static final String COLUMN_VALID_TO = "validTo";
	
	public static final String TABLE_PRODUCTS = "products";
	public static final String COLUMN_PRODUCT_ID = "id";
	public static final String COLUMN_PRODUCT_CAFFEINE_SOURCE_ID = "caffeineSourceId";
	public static final String COLUMN_PRODUCT_NAME= "name";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_PRODUCT_TYPE= "productType";
	public static final String COLUMN_PRICE_TYPE= "priceType";
	
	//SQL create statements
	private static final String DATABASE_CREATE_SOURCES = "CREATE TABLE " + TABLE_SOURCES + " ( "
				+ COLUMN_SOURCE_ID + "TEXT, "
				+ COLUMN_SOURCE_NAME + "TEXT NOT NULL, "
				+ COLUMN_BUILDING_NUMBER +" TEXT NOT NULL, "
				+ COLUMN_BUILDING_NAME + "TEXT NOT NULL, "
				+ COLUMN_BUILDING_LAT  + "REAL NOT NULL, "
				+ COLUMN_BUILDING_LONG + "REAL NOT NULL, "
				+ "PRIMARY KEY ( " + COLUMN_SOURCE_ID + " ) "
			+");";
	
	private static final String DATABASE_CREATE_OPENING_TIMES = "CREATE TABLE " + TABLE_OPENING_TIMES + " ( "
			+ COLUMN_OPENING_TIME_ID + "TEXT, "
			+ COLUMN_OPENING_TIME_CAFFEINE_SOURCE_ID + "TEXT NOT NULL REFERENCES " + TABLE_SOURCES + "(" + COLUMN_SOURCE_ID + "), "
			+ COLUMN_DAY + "TEXT NOT NULL, "
			+ COLUMN_OPENING_TIME + "TEXT NOT NULL, "
			+ COLUMN_CLOSTING_TIME + "TEXT NOT NULL, "
			+ COLUMN_VALID_FROM + "TEXT NOT NULL, "
			+ COLUMN_VALID_TO + "TEXT NOT NULL, "
			+ "PRIMARY KEY ( " + COLUMN_OPENING_TIME_ID + " )"
		+");";
	
	private static final String DATABASE_CREATE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + " ( "
			+ COLUMN_PRODUCT_ID + "TEXT, "
			+ COLUMN_PRODUCT_CAFFEINE_SOURCE_ID + "TEXT NOT NULL REFERENCES " + TABLE_SOURCES + "(" + COLUMN_SOURCE_ID + "), "
			+ COLUMN_PRODUCT_NAME + "TEXT NOT NULL, "
			+ COLUMN_PRICE + "TEXT NOT NULL, "
			+ COLUMN_PRODUCT_TYPE + "TEXT NOT NULL, "
			+ COLUMN_PRICE_TYPE + "TEXT NOT NULL, "
			+ "PRIMARY KEY ( " + COLUMN_PRODUCT_ID + " )"
		+");";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		//Create Tables
		database.execSQL(DATABASE_CREATE_SOURCES);
		database.execSQL(DATABASE_CREATE_OPENING_TIMES);
		database.execSQL(DATABASE_CREATE_PRODUCTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + 
				" to " + newVersion + ", which will destroy all old data");
		
		//Drop tables.
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_OPENING_TIMES);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_SOURCES);
		onCreate(db);
	}
}
