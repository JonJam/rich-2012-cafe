package com.googlecode.rich2012cafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.controllers.DataController;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

public class JonText extends Activity implements OnClickListener, HomeViewInterface{

	private DataController controller;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        tv = (TextView) findViewById(R.id.textview2);
        
        controller = new DataController(this);
        
        controller.getAppDataStore().performDatabaseCheck();
        
        String text = controller.getAppDataStore().test();
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(text);
    }
    
	protected void onResume() {
		controller.getAppDataStore().openDataSourceConnections();
		super.onResume();
	}

	protected void onPause() {
		controller.getAppDataStore().closeDataSourceConnections();
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
