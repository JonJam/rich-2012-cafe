package com.googlecode.rich2012cafe;

import com.googlecode.rich2012cafe.controller.AppController;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * N.B. In running and developing application need to increase Eclipse minimum/maximum memory requirements by altering
 * eclipse.ini file (Linux - located in /usr/lib/eclipse/) and change appropiate lines to this:
 *              -Xms512m
 *              -Xmx1024m
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CaffeineFinder extends Activity {

	private AppController controller;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.textview);
        
        controller = new AppController(tv);
        controller.runSPARQLQuery();


    }

    private
}