package com.googlecode.rich2012cafe.activities;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.Rich2012CafeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class SettingsActivity extends Activity implements OnClickListener{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
        PreferencesFragment prefs = new PreferencesFragment();
        prefs.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.pref_frag_content, prefs).commit();
        this.getActionBar().setHomeButtonEnabled(true);
		findViewById(R.id.accountButton).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.accountButton) {
			Intent intent = new Intent(view.getContext(), AccountsActivity.class);
			this.startActivity(intent);
		}
		
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          //return controller.optionsActions(item, this);
    	switch(item.getItemId()){
    	case android.R.id.home:{
            Intent intent = new Intent(this, Rich2012CafeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;

    	}
    	}
    	return false;
    }
}
