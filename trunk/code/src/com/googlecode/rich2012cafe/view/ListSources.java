package com.googlecode.rich2012cafe.view;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controller.AppController;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;
import com.googlecode.rich2012cafe.model.database.CaffeineProductsDataSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSourcesDataSource;
import com.googlecode.rich2012cafe.model.database.OpeningTimesDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListSources extends Activity{

	private LinearLayout ll;
	private AppController controller;
	private ScrollView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sv = new ScrollView(this);
		
		this.controller = new AppController(new AppDataStore(new CaffeineSourcesDataSource(this), new OpeningTimesDataSource(this), new CaffeineProductsDataSource(this)));
		
		ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		generateList();
		this.setContentView(sv);
	}

	private void generateList(){
		ArrayList<CaffeineSource> sources = controller.getAllCaffeineSources();
		
		System.out.println(sources.size());
		
		Log.e(STORAGE_SERVICE, ""+sources.size());
		
		for(CaffeineSource cs: sources){
			Button tempSource = new Button(this);
			tempSource.setText(cs.getBuildingNumber()+" : "+cs.getBuildingName());
			final PopupMenu menu = new PopupMenu(this, tempSource);
			menu.getMenuInflater().inflate(R.menu.locations_menu, menu.getMenu());
			menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					switch(item.getItemId()){
					case R.id.loc_menu_map:{
						return true;
					}
					case R.id.loc_menu_prod:{
						return true;	
					}
					case R.id.loc_menu_fav:{
						return true;
					}
					}
					return false;
				}
			});
			tempSource.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					menu.show();
				}

			});
			ll.addView(tempSource);
		}
	}
}
