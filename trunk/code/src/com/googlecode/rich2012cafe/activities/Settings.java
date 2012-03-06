package com.googlecode.rich2012cafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controllers.SettingsController;

public class Settings extends Activity implements OnClickListener{

	private SettingsController controller;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		controller = new SettingsController();
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
			controller.createAlert(this, "This app helps you locate hot/cold drink vendors around Highfield Campus - Soton").show();
		}
		if (view.getId() == R.id.prefButton) {
			controller.startActivity(this, Preferences.class);
		}
	}
}
