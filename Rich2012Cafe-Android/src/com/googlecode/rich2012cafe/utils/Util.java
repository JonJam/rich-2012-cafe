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
package com.googlecode.rich2012cafe.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;
import com.googlecode.rich2012cafe.R;

/**
 * GENERATED CLASS
 * 
 * Utility methods for getting the base URL for client-server communication and
 * retrieving shared preferences.
 * 
 * Modified by Jonathan Harrison (jonjam1990@googlemail.com):
 * 		To remove NullPointerException when generating Notification.
 * 		Incorrect debug url being given when testing on linux.
 */
public class Util {

    /**
     * Tag for logging.
     */
    private static final String TAG = "Util";

    // Shared constants

    /**
     * Key for account name in shared preferences.
     */
    public static final String ACCOUNT_NAME = "accountName";

    /**
     * Key for auth cookie name in shared preferences.
     */
    public static final String AUTH_COOKIE = "authCookie";

    /**
     * Key for connection status in shared preferences.
     */
    public static final String CONNECTION_STATUS = "connectionStatus";

    /**
     * Value for {@link #CONNECTION_STATUS} key.
     */
    public static final String CONNECTED = "connected";

    /**
     * Value for {@link #CONNECTION_STATUS} key.
     */
    public static final String CONNECTING = "connecting";

    /**
     * Value for {@link #CONNECTION_STATUS} key.
     */
    public static final String DISCONNECTED = "disconnected";

    /**
     * Key for device registration id in shared preferences.
     */
    public static final String DEVICE_REGISTRATION_ID = "deviceRegistrationID";

    /*
     * URL suffix for the RequestFactory servlet.
     */
    public static final String RF_METHOD = "/gwtRequest";

    /**
     * An intent name for receiving registration/unregistration status.
     */
    public static final String UPDATE_UI_INTENT = getPackageName() + ".UPDATE_UI";

    // End shared constants

    /**
     * Key for shared preferences.
     */
    private static final String SHARED_PREFS = "rich2012cafe".toUpperCase(Locale.ENGLISH) + "_PREFS";

    /**
     * Cache containing the base URL for a given context.
     */
    private static final Map<Context, String> URL_MAP = new HashMap<Context, String>();

    /**
     * Display a notification containing the given string.
     */
    public static void generateNotification(Context context, String message, Intent intent) {
        
        Notification.Builder builder = new Notification.Builder(context);

        Resources res = context.getResources();
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                    .setSmallIcon(R.drawable.ic_launcher_bean)
                 //   .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.status_icon))
                    .setTicker(message)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(res.getString(R.string.notifTitle))
                    .setContentText(message);
        Notification notification = builder.getNotification();

        
        SharedPreferences settings = Util.getSharedPreferences(context);
        int notificatonID = settings.getInt("notificationID", 0);

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(notificatonID, notification);

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notificationID", ++notificatonID % 32);
        editor.commit();
    }

    /**
     * Returns the (debug or production) URL associated with the registration
     * service.
     */
    public static String getBaseUrl(Context context) {
        String url = URL_MAP.get(context);
        if (url == null) {
            // if a debug_url raw resource exists, use its contents as the url
            url = getDebugUrl(context);
            // otherwise, use the production url
            if (url == null) {
                url = Setup.PROD_URL;
            }
            URL_MAP.put(context, url);
        }
        return url;
    }

    /**
     * Creates and returns an initialized {@link RequestFactory} of the given
     * type.
     */
    public static <T extends RequestFactory> T getRequestFactory(Context context,
            Class<T> factoryClass) {
        T requestFactory = RequestFactorySource.create(factoryClass);

        SharedPreferences prefs = getSharedPreferences(context);
        String authCookie = prefs.getString(Util.AUTH_COOKIE, null);

        String uriString = Util.getBaseUrl(context) + RF_METHOD;
        URI uri;
        try {
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            Log.w(TAG, "Bad URI: " + uriString, e);
            return null;
        }
        requestFactory.initialize(new SimpleEventBus(),
                new AndroidRequestTransport(uri, authCookie));

        return requestFactory;
    }

    /**
     * Helper method to get a SharedPreferences instance.
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS, 0);
    }

    /**
     * Returns true if we are running against a dev mode appengine instance.
     */
    public static boolean isDebug(Context context) {
        // Although this is a bit roundabout, it has the nice side effect
        // of caching the result.
        return !Setup.PROD_URL.equals(getBaseUrl(context));
    }

    /**
     * Returns a debug url, or null. To set the url, create a file
     * {@code assets/debugging_prefs.properties} with a line of the form
     * 'url=http:/<ip address>:<port>'. A numeric IP address may be required in
     * situations where the device or emulator will not be able to resolve the
     * hostname for the dev mode server.
     */
    private static String getDebugUrl(Context context) {
        BufferedReader reader = null;
        String url = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("debugging_prefs.properties");
            reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String s = reader.readLine();
            	
            	//HACK FOR LINUX TO GET DEBUGGER TO WORK
            	//String s = "url=http://192.168.1.90:8888";
                if (s == null) {
                    break;
                }
                if (s.startsWith("url=")) {
                    url = s.substring(4).trim();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // O.K., we will use the production server
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Got exception " + e);
            Log.w(TAG, Log.getStackTraceString(e));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.w(TAG, "Got exception " + e);
                    Log.w(TAG, Log.getStackTraceString(e));
                }
            }
        }

        return url;
    }

    /**
     * Returns the package name of this class.
     */
    private static String getPackageName() {
        return Util.class.getPackage().getName();
    }
}
