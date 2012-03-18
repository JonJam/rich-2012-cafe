package com.googlecode.rich2012cafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.googlecode.rich2012cafe.R;
import com.googlecode.rich2012cafe.model.AppDataStore;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;

import java.util.ArrayList;

/**
 * User: CS
 * Date: 18/03/12
 * Time: 14:32
 */
public class BuildingDetails extends Activity {

    private AppDataStore appDataStore;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildings_details);
        
        Bundle dataBundle = getIntent().getExtras();
        
        String caffeineSourceId = dataBundle.getString("source_id");

        appDataStore = AppDataStore.getInstance(this);

        ArrayList<CaffeineProduct> caffeineProductsForCaffeineSource = appDataStore.getCaffeineProductsForCaffeineSource(caffeineSourceId);

        TextView view = (TextView) findViewById(R.id.tv_holder);
        view.setText("CaffeineSource ID:" + caffeineSourceId);




    }
}
