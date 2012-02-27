package com.googlecode.rich2012cafe;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.googlecode.rich2012cafe.controller.AppController;

import android.app.Activity;
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
public class CaffeineFinder extends Activity implements OnClickListener, HomeViewInterface {

	private AppController controller;
    private TextView tv;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);

        tv = (TextView) findViewById(R.id.textview);
        this.controller = new AppController(this);

        controller.performDatabaseCheck();

        Button viewMapButton = this.getViewMapButton();
        viewMapButton.setOnClickListener(this);




    }

    private Button getViewMapButton() {
        return (Button)findViewById(R.id.viewMap);
    }


    public void onClick(View view) {

        if (view.getId() == R.id.viewMap) {
            
            //this.controller.handleMapButton();

            AlertDialog.Builder builder = new AlertDialog.Builder(controller.getContext());
            builder.setMessage("Clicked the button!");
            AlertDialog dialog = builder.create();
            dialog.show();
                                                       
            //setContentView(R.layout.map);
        }
    }

    public TextView getTextView() {
        return tv;
    }
}