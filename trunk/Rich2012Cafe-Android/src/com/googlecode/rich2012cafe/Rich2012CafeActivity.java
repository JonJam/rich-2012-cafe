/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.googlecode.rich2012cafe;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import com.googlecode.rich2012cafe.activities.AccountsActivity;
import com.googlecode.rich2012cafe.client.MyRequestFactory;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProductProxy;
import com.googlecode.rich2012cafe.shared.CaffeineSourceProxy;
import com.googlecode.rich2012cafe.shared.OpeningTimeProxy;
import com.googlecode.rich2012cafe.utils.DeviceRegistrar;
import com.googlecode.rich2012cafe.utils.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

/**
 * NOTES
 * =====
 * 
 * - Need to install Google App Engine Plugin with SDKs.
 * - To run project, have to select Android project and select debug as Local App Engine Connected Android Application
 * - Issues which you may have which I encountered are:
 * 
 * 		When default time zone not being set for Google App Engine server.
 * 			http://code.google.com/p/googleappengine/issues/detail?id=6928
 * 
 * 		In Util Lines 210 - 212, in order for communication to work on linux between phone and app had to insert IP
 * 		address:
 * 			http://code.google.com/p/google-plugin-for-eclipse/issues/detail?id=46
 * 		Everyone else use the commented out line.
 * 
 * 		Shared folder in Rich2012Cafe-Android is linked to shared folder in Rich2012Cafe-AppEngine
 * 			Need to make sure linked and if not follow:
 * 				http://stackoverflow.com/questions/1907275/in-eclipse-cdt-shared-resource-folder-that-is-built-differently-for-the-project
 * 
 * 
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class Rich2012CafeActivity extends Activity {
    
	//Tag for logging.
    private static final String TAG = "Rich2012CafeActivity";
    //The current context.
    private Context mContext = this;
    private TextView tv;

    /**
     * A {@link BroadcastReceiver} to receive the response from a register or
     * unregister request, and to update the UI.
     */
    private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
            int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
                    DeviceRegistrar.ERROR_STATUS);
            String message = null;
            String connectionStatus = Util.DISCONNECTED;
            if (status == DeviceRegistrar.REGISTERED_STATUS) {
                message = getResources().getString(R.string.registration_succeeded);
                connectionStatus = Util.CONNECTED;
            } else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
                message = getResources().getString(R.string.unregistration_succeeded);
            } else {
                message = getResources().getString(R.string.registration_error);
            }

            // Set connection status
            SharedPreferences prefs = Util.getSharedPreferences(mContext);
            prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();

            // Display a notification
            Util.generateNotification(mContext, String.format(message, accountName));
        }
    };

    /**
     * Begins the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register a receiver to provide register/unregister notifications
        registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
        
    }
    
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
        
        if (Util.DISCONNECTED.equals(connectionStatus)) {
            startActivity(new Intent(this, AccountsActivity.class));
        }
        
        setScreenContent(R.layout.hello_world);

    }
    
    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
        unregisterReceiver(mUpdateUIReceiver);
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        
        // Invoke the Register activity
        menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
        return true;
    }

    private void setHelloWorldScreenContent() {
    	tv = (TextView) findViewById(R.id.hello_world_info);

        new AsyncTask<Void, Void, String>() {
        	String message = "";
        	
			@Override
            protected String doInBackground(Void... arg0) {
                MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
                                           
                //Get Opening times
                requestFactory.rich2012CafeRequest()
            	.getOpeningTimesForCaffeineSource("http://id.southampton.ac.uk/point-of-service/38-arlott")
            	.fire(new Receiver<List<OpeningTimeProxy>>(){
            	
            	@Override
				public void onSuccess(List<OpeningTimeProxy> products) {
					for(OpeningTimeProxy c : products){
						message +=  c.getId() + " " + c.getCaffeineSourceId() + " " + c.getDay() + " " + c.getOpeningTime()
								+ " " + c.getClosingTime() + " " + c.getValidFrom() + " " + c.getValidTo() + "\n\n";
					}
				}
	        	
				@Override
	            public void onFailure(ServerFailure error) {
	                message = "Failure: " + error.getMessage();
	            }
            });
            
                
                //Get caffeine source products.
//                requestFactory.rich2012CafeRequest()
//                	.getCaffeineSourceProductsForCaffeineSource("http://id.southampton.ac.uk/point-of-service/38-arlott")
//                	.fire(new Receiver<List<CaffeineSourceProductProxy>>(){
//                	
//                	@Override
//    				public void onSuccess(List<CaffeineSourceProductProxy> products) {
//    					for(CaffeineSourceProductProxy c : products){
//    						message +=  c.getId() + " " + c.getName() + " " + c.getCaffeineSourceId() + " " 
//    								+ c.getCurrency() + " " + c.getPrice() + " " + c.getPriceType() + "\n\n";
//    					}
//    				}
//    	        	
//    				@Override
//    	            public void onFailure(ServerFailure error) {
//    	                message = "Failure: " + error.getMessage();
//    	            }
//                });
//                
                //Get caffeine sources given
//                requestFactory.rich2012CafeRequest().getCaffeineSourcesGiven().fire(new Receiver<List<CaffeineSourceProxy>>(){
//
//                	@Override
//    				public void onSuccess(List<CaffeineSourceProxy> products) {
//    					for(CaffeineSourceProxy c : products){
//    						message += c.getId() + " " + c.getName() + " " + c.getBuildingName() + " " 
//    								+ c.getBuildingNumber() + " " + c.getBuildingLong() + " " + c.getBuildingLat()
//    								+ " " + c.getOffCampus() + " " + c.getType();
//    					}
//    				}
//    	        	
//    				@Override
//    	            public void onFailure(ServerFailure error) {
//    	                message = "Failure: " + error.getMessage();
//    	            }
//                });
                
                //Get all caffeine products
//                requestFactory.rich2012CafeRequest().getAllCaffeineProducts().fire(
//                		new Receiver<List<CaffeineProductProxy>>(){
//					
//        			@Override
//					public void onSuccess(List<CaffeineProductProxy> products) {
//						for(CaffeineProductProxy c : products){
//							message += c.getName() + " " + c.getProductType() + " " + c.getCaffeineContent() + "\n\n";
//						}
//					}
//                	
//					@Override
//                    public void onFailure(ServerFailure error) {
//                        message = "Failure: " + error.getMessage();
//                    }
//
//                });

                //Update database method.
//              requestFactory.rich2012CafeRequest().updateDataStore().fire(
//        		new Receiver<Void>(){
//			        	
//				@Override
//	            public void onFailure(ServerFailure error) {
//	                message = "Failure: " + error.getMessage();
//	            }
//
//				@Override
//				public void onSuccess(Void arg0) {
//					message = "UPDATED";
//				}
//	
//	        });
                
				return message;
            }
            
            @Override
            protected void onPostExecute(String result) {
                tv.setMovementMethod(new ScrollingMovementMethod());
                tv.setText(result);
            }
        }.execute();
    }

    /**
     * Sets the screen content based on the screen id.
     */
    private void setScreenContent(int screenId) {
        setContentView(screenId);
        switch (screenId) {
            case R.layout.hello_world:
                setHelloWorldScreenContent();
                break;
        }
    }
}
