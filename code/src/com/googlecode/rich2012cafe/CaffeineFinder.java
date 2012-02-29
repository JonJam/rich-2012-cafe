package com.googlecode.rich2012cafe;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.googlecode.rich2012cafe.controller.AppController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.googlecode.rich2012cafe.view.HomeViewInterface;

/**
 * N.B. In running and developing application need to increase Eclipse minimum/maximum memory requirements by altering
 * eclipse.ini file (Linux - located in /usr/lib/eclipse/) and change appropriate lines to this:
 *              -Xms512m
 *              -Xmx1024m
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineFinder extends Activity implements OnClickListener{

	private AppController controller;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.getViewMapButton().setOnClickListener(this);
        this.getSparqlButton().setOnClickListener(this);
        this.getuiButton().setOnClickListener(this);
    }

    private Button getViewMapButton() {
        return (Button)findViewById(R.id.viewMap);
    }

    private Button getuiButton() {
        return (Button)findViewById(R.id.uiButton);
    }
    
    private Button getSparqlButton() {
        return (Button)findViewById(R.id.sparqlButton);
    }
    
    public void onClick(View view) {

        if (view.getId() == R.id.viewMap) {
        	Intent intent = new Intent(view.getContext(), GoogleMap.class);
        	this.startActivity(intent);
        }
        if(view.getId() == R.id.sparqlButton){
        	Intent intent = new Intent(view.getContext(), JonText.class);
        	this.startActivity(intent);
        }
        if(view.getId() == R.id.uiButton){
        	Intent intent = new Intent(view.getContext(), UserInterface.class);
        	this.startActivity(intent);
        }
    }


}