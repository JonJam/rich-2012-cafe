package com.googlecode.rich2012cafe.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.rich2012cafe.server.datastore.DataStore;

/**
 * HttpServlet to run database check and update which is called via cron job.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class CronServlet extends HttpServlet{

	private static DataStore db = new DataStore();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3755284163912524262L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

		if(db.getAllCaffeineSources() == null){
			//Empty datastore
			
			db.populateDatastore();

		}  else if(db.getExpiredOpeningTimes() != null){
			//Expired Opening Times

			db.clearDatastore();
			db.populateDatastore();
		}
    }
}
