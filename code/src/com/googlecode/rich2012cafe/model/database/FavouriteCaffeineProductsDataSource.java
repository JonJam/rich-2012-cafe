package com.googlecode.rich2012cafe.model.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object class for FavouriteCaffeineProduct objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class FavouriteCaffeineProductsDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public FavouriteCaffeineProductsDataSource(Context context) {
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
	 * Method to insert an FavouriteCaffeineProduct object into the database.
	 * 
	 * @param favouriteCaffeineProduct (FavouriteCaffeineProduct object)
	 */
	public void insertFavouriteCaffeineProduct(FavouriteCaffeineProduct favouriteCaffeineProduct) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_FAVOURITE_CAFFEINE_PRODUCT_NAME , favouriteCaffeineProduct.getName());
		
		database.insert(DatabaseHelper.TABLE_FAVOURITE_CAFFEINE_SOURCES, null, values);
	}
	
	/**
	 * Method to get all FavouriteCaffeineProduct objects in the database.
	 * 
	 * @return ArrayList of FavouriteCaffeineProduct objects.
	 */
	public ArrayList<FavouriteCaffeineProduct> getAllFavouriteCaffeineProducts() {
		
		ArrayList<FavouriteCaffeineProduct> favouriteCaffeineProducts = new ArrayList<FavouriteCaffeineProduct>();
		
		Cursor cursor = database.rawQuery("SELECT * FROM favouriteCaffeineProducts", new String[] {});
	
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//Iterate through table and create FavouriteCaffeineProduct objects.
			
			FavouriteCaffeineProduct product = new FavouriteCaffeineProduct(cursor.getString(0));
			
			favouriteCaffeineProducts.add(product);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return favouriteCaffeineProducts;
	}
	
	/**
	 * Method to delete a favourite caffeine product.
	 * 
	 * @param favouriteCaffeineProduct (FavouriteCaffeineProduct object)
	 */
	public void deleteFavouriteCaffeineProduct(FavouriteCaffeineProduct favouriteCaffeineProduct){
		
		database.delete(DatabaseHelper.TABLE_FAVOURITE_CAFFEINE_PRODUCTS, DatabaseHelper.COLUMN_FAVOURITE_CAFFEINE_PRODUCT_NAME
				+ " = '" + favouriteCaffeineProduct.getName() + "'", null);	
	}
}
