package com.googlecode.rich2012cafe.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controller.Preferences;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProductsDataSource;
import com.googlecode.rich2012cafe.model.database.CaffeineSourcesDataSource;
import com.googlecode.rich2012cafe.model.database.OpeningTimesDataSource;

public class Settings extends Activity implements OnClickListener{

	private AppDataStore ds;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
     
		this.ds = new AppDataStore(new CaffeineSourcesDataSource(this), new OpeningTimesDataSource(this), new CaffeineProductsDataSource(this));
        
        this.getAboutButton().setOnClickListener(this);
        this.getPrefButton().setOnClickListener(this);
    }
    
  private Button getAboutButton() {
      return (Button)findViewById(R.id.aboutButton);
  }
  private Button getPrefButton() {
      return (Button)findViewById(R.id.prefButton);
  }
  
	@Override
	public void onClick(View view) {
      if (view.getId() == R.id.aboutButton) {
        AlertDialog dialog= new AlertDialog.Builder(this).create();
        dialog.setMessage("This app helps you locate hot/cold drink vendors around Highfield Campus - Soton");
        dialog.show();
      }
      if (view.getId() == R.id.prefButton) {
      	Intent intent = new Intent(this, Preferences.class);
    	this.startActivity(intent);
       }
	}
}
