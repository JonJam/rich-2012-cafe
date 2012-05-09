package com.googlecode.rich2012cafe;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.googlecode.rich2012cafe.activities.AccountsActivity;
import com.googlecode.rich2012cafe.activities.GMapActivity;
import com.googlecode.rich2012cafe.activities.GraphActivity;
import com.googlecode.rich2012cafe.activities.LeaderboardActivity;
import com.googlecode.rich2012cafe.activities.SettingsActivity;
import com.googlecode.rich2012cafe.caffeinelevel.CaffeineLevel;
import com.googlecode.rich2012cafe.caffeinelevel.CaffeineLevelWriter;
import com.googlecode.rich2012cafe.shared.CaffeineProductProxy;
import com.googlecode.rich2012cafe.utils.DeviceRegistrar;
import com.googlecode.rich2012cafe.utils.Rich2012CafeUtil;
import com.googlecode.rich2012cafe.utils.ScheduledTasks;
import com.googlecode.rich2012cafe.utils.Util;

/**
 * @author Pratik Patel (p300ss@gmail.com), Michael Elkins (thorsion@gmail.com)
 */

public class Rich2012CafeActivity extends Activity implements OnClickListener{
    
    //The current context.
    private Context mContext = this;

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
        SharedPreferences sp = Util.getSharedPreferences(this);
        as.setScore(sp.getInt("userScore", 1));
        
        //change for automatic product download
        //ScheduledTasks.getCaffeineProducts(this, true);


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

        menu.getItem(0).setIntent(new Intent(this, GraphActivity.class));
        menu.getItem(1).setIntent(new Intent(this, GMapActivity.class));
        menu.getItem(2).setIntent(new Intent(this, LeaderboardActivity.class));
        menu.getItem(3).setIntent(new Intent(this, SettingsActivity.class));
        
        return true;
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
            	this.findViewById(R.id.intakeButton).setOnClickListener(this);
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
		if(view.getId() == R.id.intakeButton){
			ApplicationState as = (ApplicationState) this.getApplicationContext();
			if(as.getCaffeineProducts() == null){
				ScheduledTasks.getCaffeineProducts(this, true);
			}
			
			loadChoiceDialog(as.getCaffeineProducts());
		}
	}
	
	private void loadChoiceDialog(final List<CaffeineProductProxy> products){
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.choicedialog);
		dialog.setTitle("Select Drink");
		LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.choiceDialoglayout);
		if(products != null){
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
		}else{
			createAlert(this, "Error connecting to DB");
		}
	}
	
	private void closeDialog(Dialog d, CaffeineProductProxy p){
		d.dismiss();
		CaffeineLevelWriter clw = new CaffeineLevelWriter(this);
		clw.appendToCaffeineLevels(new CaffeineLevel(new Date(System.currentTimeMillis()), (int) p.getCaffeineContent()), Rich2012CafeUtil.ADHOC_DRINKS_SETTING_NAME);
	}
}
