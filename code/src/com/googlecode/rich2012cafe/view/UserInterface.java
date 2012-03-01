package com.googlecode.rich2012cafe.view;

import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.R.id;
import com.googlecode.rich2012cafe.R.layout;
import com.googlecode.rich2012cafe.controller.AppController;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserInterface extends Activity implements OnClickListener{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        
        this.getAboutButton().setOnClickListener(this);
    }
    
    private Button getAboutButton() {
        return (Button)findViewById(R.id.aboutButton);
    }
    
	@Override
	public void onClick(View view) {
        if (view.getId() == R.id.aboutButton) {
          AlertDialog dialog= new AlertDialog.Builder(this).create();
          dialog.setMessage("This app helps you locate hot/cold drink vendors around Highfield Campus - Soton");
          dialog.show();
        }
	}

}
