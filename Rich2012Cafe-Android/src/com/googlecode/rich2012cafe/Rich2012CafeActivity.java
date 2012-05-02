package com.googlecode.rich2012cafe;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.rich2012cafe.activities.AccountsActivity;
import com.googlecode.rich2012cafe.activities.CaffeineTracker;
import com.googlecode.rich2012cafe.activities.GMapActivity;
import com.googlecode.rich2012cafe.activities.GraphActivity;
import com.googlecode.rich2012cafe.activities.LeaderboardActivity;
import com.googlecode.rich2012cafe.activities.SettingsActivity;
import com.googlecode.rich2012cafe.alarm.AlarmController;
import com.googlecode.rich2012cafe.model.CaffeineLevel;
import com.googlecode.rich2012cafe.model.CaffeineLevelWriter;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.utils.DeviceRegistrar;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import com.googlecode.rich2012cafe.utils.ScheduledTasks;
import com.googlecode.rich2012cafe.utils.Util;

/**
 * NOTES
 * =====
 * 
 * - Need to install Google App Engine Plugin with SDKs.
 * - To run project, have to select Android project and select debug as Local App Engine Connected Android Application
 * - Issues which you may have which I encountered are:
 * 
 * 		When default time zone not being set for Google App Engine server.
 * 			http://code.google.com/p/googleappengine/issues/detail?id=6928
 * 
 * 		In Util Lines 210 - 212, in order for communication to work on linux between phone and app had to insert IP
 * 		address:
 * 			http://code.google.com/p/google-plugin-for-eclipse/issues/detail?id=46
 * 		Everyone else use the commented out line.
 * 
 * 		Shared folder in Rich2012Cafe-Android is linked to shared folder in Rich2012Cafe-AppEngine
 * 			Need to make sure linked and if not follow:
 * 				http://stackoverflow.com/questions/1907275/in-eclipse-cdt-shared-resource-folder-that-is-built-differently-for-the-project
 * 
 * 
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class Rich2012CafeActivity extends Activity implements OnClickListener{
    
    //The current context.
    private Context mContext = this;
    private TextView tv;

    /**
     * A {@link BroadcastReceiver} to receive the response from a register or
     * unregister request, and to update the UI.
     */
    private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	
            String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
            int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
                    DeviceRegistrar.ERROR_STATUS);
            String message = null;
            String connectionStatus = Util.DISCONNECTED;
            if (status == DeviceRegistrar.REGISTERED_STATUS) {
                message = getResources().getString(R.string.registration_succeeded);
                connectionStatus = Util.CONNECTED;
            } else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
                message = getResources().getString(R.string.unregistration_succeeded);
            } else {
                message = getResources().getString(R.string.registration_error);
            }

            // Set connection status
            SharedPreferences prefs = Util.getSharedPreferences(mContext);
            prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();
            
            // Display a notification
            Util.generateNotification(mContext, String.format(message, accountName), new Intent());
        }
    };

    /**
     * Begins the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CustomTheme); 
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setHomeButtonEnabled(true);
                
        ApplicationState as = (ApplicationState) this.getApplicationContext();
        as.setScore(-1);

        ScheduledTasks.getCaffeineProducts(this, true);

        // Register a receiver to provide register/unregister notifications
        registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
        
    }
    
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
        
        if (Util.DISCONNECTED.equals(connectionStatus)) {
            startActivity(new Intent(this, AccountsActivity.class));
        }
        
        setScreenContent(R.layout.home_screen);

    }
    
    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
        unregisterReceiver(mUpdateUIReceiver);
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        // Invoke the Register activity       
        menu.getItem(0).setIntent(new Intent(this, GMapActivity.class));
        menu.getItem(1).setIntent(new Intent(this, GraphActivity.class));
        menu.getItem(2).setIntent(new Intent(this, LeaderboardActivity.class));
        menu.getItem(3).setIntent(new Intent(this, SettingsActivity.class));
        
        return true;
    }

    private void setHelloWorldScreenContent() {
    	tv = (TextView) findViewById(R.id.hello_world_info);
    	    	    	
    	Calendar timeToSet = Calendar.getInstance();
    	timeToSet.add(Calendar.MINUTE, 1);
    	
    	AlarmController.setCaffeineWarningAlarm(this, timeToSet);

    	findViewById(R.id.graphButton).setOnClickListener(this);
    	this.findViewById(R.id.intakeButton).setOnClickListener(this);
    }
    
	public AlertDialog createAlert(Activity activity, String msg){
	     AlertDialog dialog= new AlertDialog.Builder(activity).create();
	     dialog.setMessage(msg);
	     return dialog;   
	}

    /**
     * Sets the screen content based on the screen id.
     */
    private void setScreenContent(int screenId) {
        setContentView(screenId);
        switch (screenId) {
            case R.layout.home_screen:
                setHelloWorldScreenContent();
                break;
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.graphButton) {
			Intent intent = new Intent(view.getContext(), CaffeineTracker.class);
			this.startActivity(intent);
		}
		if(view.getId() == R.id.intakeButton){
			Log.i("t-msg", "at button");
			ApplicationState as = (ApplicationState) this.getApplicationContext();
			Log.i("t-msg", "at button");
			if(as.getCaffeineProducts() != null){
				loadChoiceDialog(as.getCaffeineProducts());
			}else{
				ScheduledTasks.getCaffeineProducts(this, true);
				loadChoiceDialog(as.getCaffeineProducts());
			}
		}
	}
	
	private void loadChoiceDialog(final List<CaffeineProductProxy> products){
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.choicedialog);
		dialog.setTitle("Select Drink");
		LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.choiceDialoglayout);
		
		for(final CaffeineProductProxy p: products){
			Button temp = new Button(this);
			temp.setText(p.getName());
			temp.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					closeDialog(dialog, p);
				}
				
			});
			layout.addView(temp);
		}
		dialog.show();
	}
	
	private void closeDialog(Dialog d, CaffeineProductProxy p){
		d.dismiss();
		SharedPreferences prefs = Util.getSharedPreferences(this);
		String currentValue = prefs.getString(Rich2012CafeUtil.HISTORIC_VALUES_SETTING_NAME, "");
		CaffeineLevelWriter clw = new CaffeineLevelWriter(this);
		clw.appendToCaffeineLevels(new CaffeineLevel(new Date(System.currentTimeMillis()), (int) p.getCaffeineContent()), Rich2012CafeUtil.ADHOC_DRINKS_SETTING_NAME);
	}
}
