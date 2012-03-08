package com.googlecode.rich2012cafe.activities;

import java.util.ArrayList;
import com.googlecode.rich2012cafe.controllers.DataController;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineSource;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ListSources extends Fragment {

	private  LinearLayout ll;
	private ScrollView sv;
	private Activity currAct;
	private DataController controller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currAct = getActivity();
		
		controller = new DataController(container.getContext());	
		sv = new ScrollView(currAct);
		ll = new LinearLayout(currAct);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		sv.addView(ll);
		generateList();
		return(sv);
	}

	private void generateList(){
		ArrayList<CaffeineSource> sources = controller.getAppDataStore().getAllCaffeineSources();
		for(CaffeineSource cs: sources){
			ll.addView(controller.createButtonWithPopup(currAct, cs));
		}
	}
}
