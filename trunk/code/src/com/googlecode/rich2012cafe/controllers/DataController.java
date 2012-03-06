package com.googlecode.rich2012cafe.controllers;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;

public class DataController extends Controller{

	public DataController(Context context){
		super(context);
	}
	
	public Button createButtonWithPopup(Context context, final CaffeineSource cs){
		Button tempButton = new Button(context);
		tempButton.setText(cs.getBuildingNumber()+" : "+cs.getBuildingName());
		
		final PopupMenu menu = new PopupMenu(context, tempButton);
		menu.getMenuInflater().inflate(R.menu.locations_menu, menu.getMenu());
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch(item.getItemId()){
				case R.id.loc_menu_map:{
					showOnMap(cs);
				}
				case R.id.loc_menu_prod:{
					showProducts(cs);	
				}
				case R.id.loc_menu_fav:{
					addToFavourites(cs);
				}
				}
				return false;
			}
		});
		tempButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				menu.show();
			}

		});
		
		return tempButton;		
	}
	
	private void showOnMap(CaffeineSource cs){
		
	}
	
	private void showProducts(CaffeineSource cs){
		
	}
	
	private void addToFavourites(CaffeineSource cs){
		
	}
}
