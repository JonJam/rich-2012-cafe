package com.googlecode.rich2012cafe;

import com.googlecode.rich2012cafe.controller.AppController;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JonText extends Activity implements OnClickListener, HomeViewInterface{

	private AppController controller;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        tv = (TextView) findViewById(R.id.textview2);
        this.controller = new AppController(this);

        controller.performDatabaseCheck();

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
