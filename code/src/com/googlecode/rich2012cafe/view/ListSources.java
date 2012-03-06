package com.googlecode.rich2012cafe.view;

import java.util.ArrayList;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;
import com.googlecode.rich2012cafe.model.database.CaffeineProductsDataSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSourcesDataSource;
import com.googlecode.rich2012cafe.model.database.OpeningTimesDataSource;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListSources extends Fragment {

	private  LinearLayout ll;
//	private AppController controller;
	private AppDataStore ds;
	private ScrollView sv;
	private Activity currAct;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		currAct = getActivity();
		super.onCreate(savedInstanceState);
		sv = new ScrollView(currAct);
		ds = new AppDataStore(new CaffeineSourcesDataSource(currAct), new OpeningTimesDataSource(currAct), new CaffeineProductsDataSource(currAct));
		TextView tv = new TextView(currAct);
//		this.controller = new AppController(tv, ds);
		ll = new LinearLayout(currAct);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		generateList();
		//this.setContentView(sv);
		return(sv);
	}

	private void generateList(){
		ArrayList<CaffeineSource> sources = ds.getAllCaffeineSources();
		System.out.println(sources.size());
		Log.e(currAct.STORAGE_SERVICE, ""+sources.size());
		for(CaffeineSource cs: sources){
			Button tempSource = new Button(currAct);
			tempSource.setText(cs.getBuildingNumber()+" : "+cs.getBuildingName());
			final PopupMenu menu = new PopupMenu(currAct, tempSource);
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
