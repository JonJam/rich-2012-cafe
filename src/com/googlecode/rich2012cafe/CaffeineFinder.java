package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.rich2012cafe.controller.AppController;

/**
 * N.B. In running and developing application need to increase Eclipse minimum/maximum memory requirements by altering
 * eclipse.ini file (Linux - located in /usr/lib/eclipse/) and change appropiate lines to this:
 * 		-Xms512m
 * 		-Xmx1024m
 * 
 * TODO WORK WITH VIEWS DEFINED IN XML FILE. 
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineFinder extends Activity {
	
	private AppController controller;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //TextView tv = (TextView) findViewById(R.id.textview); CAUSING NULLPOINTER
        TextView tv = new TextView(this);
        setContentView(tv);
        
        controller = new AppController(tv);
        controller.runSPARQLQuery();
    }
}