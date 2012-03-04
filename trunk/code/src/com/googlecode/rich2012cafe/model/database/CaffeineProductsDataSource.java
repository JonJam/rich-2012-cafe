package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;

/**
 * Data Access Object class for CaffeineProduct objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineProductsDataSource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
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
		values.put(DatabaseHelper.COLUMN_CURRENCY, product.getCurrency());
		values.put(DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_TYPE, product.getProductType());
		values.put(DatabaseHelper.COLUMN_PRICE_TYPE, product.getPriceType());
		
		database.insert(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, null, values);
	}
		
	/**
	 * Method to get CaffeineProduct objects for a caffeine source.
	 * 
	 * @param id (String object)
	 * @return ArrayList of CaffeineProduct objects.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsForCaffeineSource(String id) {
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM caffeineProducts WHERE caffeineSourceId = ? ORDER BY name ASC", new String[] {id});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create CaffeineProduct objects.
			
			CaffeineProduct product = new CaffeineProduct(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getDouble(3) , cursor.getString(4), cursor.getString(5) , cursor.getString(6));
			
			products.add(product);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return products;
	}
	
	/**
	 * Method to get all unique CaffeineProduct names.
	 * 
	 * @return ArrayList of String objects.
	 */
	public ArrayList<String> getAllCaffeineProductNames(){
		
		ArrayList<String> products = new ArrayList<String>();
		
		Cursor cursor = database.rawQuery("SELECT DISTINCT name FROM caffeineProducts ORDER BY name ASC", new String[] {});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and populate ArrayList.
			products.add(cursor.getString(0));
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return products;
	}
	
	/**
	 * Method to get all unique CaffeineProduct names for product type.
	 * 
	 * @param type (String object)
	 * @return ArrayList of String objects.
	 */
	public ArrayList<String> getCaffeineProductsForProductType(String type){
		
		ArrayList<String> products = new ArrayList<String>();
		
		Cursor cursor = database.rawQuery("SELECT DISTINCT name FROM caffeineProducts WHERE productType = ? ORDER BY name ASC", new String[] {type});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and populate ArrayList.
			products.add(cursor.getString(0));
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return products;
	
	}
	
	/**
	 * Method to get  CaffeineProduct objects in price range.
	 * 
	 * @param minPrice (double)
	 * @param maxPrice (double)
	 * @return ArrayList of CaffeineProduct objects.
	 */
	public ArrayList<CaffeineProduct> getCaffeineProductsInPriceRange(double maxPrice){
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM caffeineProducts WHERE price <= ? ORDER BY price ASC", new String[] {Double.toString(maxPrice)});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create CaffeineProduct objects.
			
			CaffeineProduct product = new CaffeineProduct(cursor.getString(0) , cursor.getString(1) , cursor.getString(2) , 
					cursor.getDouble(3) , cursor.getString(4), cursor.getString(5) , cursor.getString(6));
			
			products.add(product);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return products;
	
	}
	
	/**
	 * Method to get CaffeineSource ids for those that sell productName.
	 * 
	 * @param productName (String object)
	 * @return ArrayList of String objects
	 */
	public ArrayList<String> getCaffeineSourceIdsForProductName(String productName){
		ArrayList<String> sourceIds = new ArrayList<String>();
		
		Cursor cursor = database.rawQuery("SELECT DISTINCT caffeineSourceId FROM caffeineProducts WHERE name = ?", new String[] {productName});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and populate ArrayList.
			
			sourceIds.add(cursor.getString(0));
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return sourceIds;
	}
	
	/**
	 * Method to delete all CaffeineProduct objects in the database.
	 */
	public void deleteAllCaffeineProducts(){
		
		Cursor cursor = database.rawQuery("SELECT * FROM caffeineProducts", new String[] {});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and delete all OpeningTime objects.
			
			database.delete(DatabaseHelper.TABLE_CAFFEINE_PRODUCTS, DatabaseHelper.COLUMN_CAFFEINE_PRODUCT_ID + " = '" + cursor.getString(0) + "'", null);
			cursor.moveToNext();
		}
		
		cursor.close();
	}
}