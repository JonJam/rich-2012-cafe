package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object class for CaffeineProduct objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineProductsDataSource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { 
			DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_ID,
			DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_CAFFEINE_SOURCE_ID,
			DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_NAME,
			DatabaseHelper.COLUMN_PRICE,
			DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_TYPE,
			DatabaseHelper.COLUMN_PRICE_TYPE
		};
		
	public CaffeineProductsDataSource(Context context) {
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
	 * Method to insert an CaffeineProduct object into the database.
	 * 
	 * @param product (CaffeineProduct object)
	 */
	public void insertProduct(CaffeineProduct product) {
		
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_ID, product.getId());
		values.put(DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_CAFFEINE_SOURCE_ID, product.getCaffeineSourceId());
		values.put(DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_NAME, product.getName());
		values.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
		values.put(DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_TYPE, product.getProductType());
		values.put(DatabaseHelper.COLUMN_PRICE_TYPE, product.getPriceType());
		
		database.insert(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, null, values);
	}
		
	/**
	 * Method to get all CaffeineProduct objects in the database.
	 * 
	 * @return ArrayList of CaffeineProduct objects.
	 */
	public ArrayList<CaffeineProduct> getAllCaffeineProducts() {
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create CaffeineProduct objects.
			
			CaffeineProduct product = new CaffeineProduct(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getString(3) , cursor.getString(4) , cursor.getString(5));
			
			products.add(product);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return products;
	}
	
	/**
	 * Method to delete all CaffeineProduct objects in the database.
	 */
	public void deleteAllCaffeineProducts(){
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and delete all OpeningTime objects.
			
			database.delete(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_ID + " = '" + cursor.getString(0) + "'", null);
			cursor.moveToNext();
		}
		
		cursor.close();
	}
}