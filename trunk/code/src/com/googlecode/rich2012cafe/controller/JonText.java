package com.googlecode.rich2012cafe.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProductsDataSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSourcesDataSource;
import com.googlecode.rich2012cafe.model.database.OpeningTimesDataSource;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

public class JonText extends Activity implements OnClickListener, HomeViewInterface{

	private AppDataStore ds;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        tv = (TextView) findViewById(R.id.textview2);
        
        ds = new AppDataStore(new CaffeineSourcesDataSource(this), new OpeningTimesDataSource(this), new CaffeineProductsDataSource(this));
        
        ds.performDatabaseCheck();
        ds.test();
    }
    
	protected void onResume() {
    	ds.openDataSourceConnections();
		super.onResume();
	}

	protected void onPause() {
		ds.closeDataSourceConnections();
		super.onPause();
	}
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextView getTextView() {
		// TODO Auto-generated method stub
		return tv;
	}
}
