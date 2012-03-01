package com.googlecode.rich2012cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.googlecode.rich2012cafe.controller.AppController;
import com.googlecode.rich2012cafe.view.UserInterface;

/**
 * N.B. In running and developing application need to increase Eclipse minimum/maximum memory requirements by altering
 * eclipse.ini file (Linux - located in /usr/lib/eclipse/) and change appropriate lines to this:
 *              -Xms512m
 *              -Xmx1024m
 *              
 * TODO Change product query to filter out decaffenated products (SAMI - DOING)
 * TODO TO ADD NOT KEYWORD APPEND: && regex(?name, '^((?!keyword).)*$', 'i'))
 * TODO Add lat and log columns to get caffeine sources if need to.
 * TODO Queries
 * 		Get sources given lat and long
 * 		Get opening times given source id
 * 		Get products given source id
 * 		Get locations given product name
 * 		Get products given product type
 * 		Get products given price type (staff or student)
 * 		Get products given price range 
 * TODO Saving settings (XML or table)
 * 		Whether student or staff
 * 		Favourite products
 * 		Product type
 * 		Inc vending machines in type (would need to add field to caffeinesource class)
 * TODO Tidy/Structure classes so readable.
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
        }  else if (view.getId() == R.id.sparqlButton) {
        	Intent intent = new Intent(view.getContext(), JonText.class);
        	this.startActivity(intent);
        }
        if(view.getId() == R.id.uiButton){
        	Intent intent = new Intent(view.getContext(), UserInterface.class);
        	this.startActivity(intent);
        }
    }


}