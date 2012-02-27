package com.googlecode.rich2012cafe.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import com.googlecode.rich2012cafe.model.database.CaffeineSource;
import com.googlecode.rich2012cafe.model.database.OpeningTime;
import com.googlecode.rich2012cafe.model.database.CaffeineProduct;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Literal;

/**
 * Resources used:
 * 	- http://code.google.com/p/androjena/
 * 	- http://monead.com/blog/?p=1420
 *  - http://www.vogella.de/articles/AndroidSQLite/article.html
 * 
 * TODO Change product query to filter out decaffenated products
 * TODO Add product type to CaffeineProduct class e.g. Coke, Coffee, Energy Drink, Tea
 * TODO Change OpeningTime Calendar variable so can be stored in db.
 * TODO Add lat and log columns to get caffeine sources if need to.
 * TODO Sort out storage of information
 * 	STORE DATA UPTO END OF TERM AS OPENING TIMES WILL CHANGE SO UPDATE
 * TODO set up database so expires and updates data automatically
 * TODO Tidy/Structure classes so readable.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com), Samantha Kanza (samikanza@gmail.com)
 */
public class SPARQLQuerier {
	
	private static final String DATA_SOUTHAMPTON_ENDPOINT = "http://sparql.data.southampton.ac.uk/";
	
	private static final String CAFFEINE_SOURCES_QUERY = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
			+ "PREFIX sr: <http://data.ordnancesurvey.co.uk/ontology/spatialrelations/>"
			+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
			+ "PREFIX rooms: <http://vocab.deri.ie/rooms#>"
			+ "PREFIX pos: <http://id.southampton.ac.uk/generic-products-and-services/>"
			+ "SELECT DISTINCT ?pos ?name ?buildingid ?buildinglabel ?buildinglat ?buildinglong ?lat ?long WHERE {"
				+ "?poscaffeine purl:includes pos:Caffeine ."
				+ "?poscaffeine purl:availableAtOrFrom ?pos ."
				+ "OPTIONAL {"
					+ "?pos rdfs:label ?name ."
					+ "?pos sr:within ?building ."
					+ "?building a rooms:Building ; rdfs:label ?buildinglabel ."
					+ "OPTIONAL { ?building skos:notation ?buildingid }"
					+ "OPTIONAL { ?building geo:lat ?buildinglat ; geo:long ?buildinglong }"
				+ "}"
				+ "OPTIONAL { ?pos geo:lat ?lat ; geo:long ?long }"
			+ "}";
	
	private static final String OPENING_TIMES_QUERY1 = "PREFIX purl: <http://purl.org/goodrelations/v1#>"
			+ "SELECT DISTINCT ?times WHERE { <";
	private static final String OPENING_TIMES_QUERY2 = "> purl:hasOpeningHoursSpecification ?times . }";
	
	private static final String CAFFEINE_PRODUCTS_QUERY1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
    		+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
    		+ "SELECT DISTINCT ?product ?label WHERE {"
    			+ "?product purl:availableAtOrFrom <";
	private static final String CAFFEINE_PRODUCTS_QUERY2 = "> ."
				+ "?product rdfs:label ?label ."
				+ "FILTER (regex(?label, '^coke | coke |^coffee | coffee |^tea | tea |^relentless | relentless |^powerade | powerade |^lucozade | lucozade |^red bull | red bull |^frappe | frappe |^cappuchino | cappuchino |^latte | latte |^iced tea | iced tea', 'i'))"
			+ "}";
	
	private static final String STAFF_TYPE = "Staff";
	private static final String STUDENT_TYPE = "Student";
	private static final String ALL_TYPE = "All";
	
	/**
	 * Method to execute a SPARQL query at SPARQL endpoint.
	 * 
	 * @param queryString (Query to execute)
	 * @return ResultSet (Query results)
	 */
	private static ResultSet performQuery(String queryString){
		
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
	                
        QueryExecution qe = QueryExecutionFactory.sparqlService(DATA_SOUTHAMPTON_ENDPOINT, query);

        ResultSet resultSet = qe.execSelect();
        
        qe.close();
        
        return resultSet;
	}
	
	/**
	 * Method to execute a SPARQL query and obtain sources of Caffeine as CaffeineSource objects.
	 * 
	 * @return ArrayList of CaffeineSource objects
	 */
	public static ArrayList<CaffeineSource> getCaffeineSources(){

		ArrayList<CaffeineSource> sources = new ArrayList<CaffeineSource>();
		ResultSet caffeineSourcesResults = performQuery(CAFFEINE_SOURCES_QUERY);
        
		//Iterate through results.
        while (caffeineSourcesResults.hasNext()) {
        	
            QuerySolution solution = caffeineSourcesResults.next();
            
            String id = solution.getResource("pos").getURI();
            
            Literal nameLiteral = solution.getLiteral("name");
            Literal buildingNumberLiteral = solution.getLiteral("buildingid");
            Literal buildingNameLiteral = solution.getLiteral("buildinglabel");
            Literal buildingLatLiteral = solution.getLiteral("buildinglat");
            Literal buildingLongLiteral = solution.getLiteral("buildinglong");
            
            if(nameLiteral == null && buildingNumberLiteral == null && buildingNameLiteral == null && buildingLongLiteral == null 
            		&& buildingLongLiteral == null){
            	//All information is null so skipping.
            	continue;
            }
        	
            CaffeineSource source = new CaffeineSource(id, nameLiteral.getString(), buildingNumberLiteral.getString(), 
            		buildingNameLiteral.getString(), buildingLatLiteral.getDouble(), buildingLongLiteral.getDouble());
            
            sources.add(source);

        }
        
        return sources;
	}
	
	/**
	 * Method to execute a SPARQL query and obtain opening times for a caffeine source for current term.
	 * 
	 * N.B. Sources will return empty ArrayList when don't have opening times.
	 * 
	 * Opening time dates come in the following formats (which I have seen):
	 * 	Wednesday-1200-1400-26-Sep-2011
	 *  Wednesday-1200-1400-2011-9-26
	 *  Wednesday-CLOSED-2011-9-26
	 * 
	 * @param caffeineSourceId (URI for caffeine source)
	 * @return ArrayList of OpeningTime objects.
	 */
	public static ArrayList<OpeningTime> getCurrentOpeningTimes(String caffeineSourceId){

		ArrayList<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
		ResultSet openingTimesResults = performQuery(OPENING_TIMES_QUERY1 + caffeineSourceId + OPENING_TIMES_QUERY2);

		//Iterate through results
        while (openingTimesResults.hasNext()) {
        	
            QuerySolution solution = openingTimesResults.next();
            
            String timeURI = solution.getResource("times").getURI();
            String timeString = timeURI.substring(timeURI.indexOf("#") + 1);
     	   	String[] timeStringParts = timeString.split("-");
     	   
     	   	String day = timeStringParts[0];
     	   	String openTime = timeStringParts[1];
     	   	String closeTime;
     	   	Calendar cal = Calendar.getInstance();
     	   	
     	   	if(openTime.equals("CLOSED")){
     	   		//No close time so dates in different positions.
     		   
     	   		closeTime = "CLOSED";
     	   		
     	   		try{
     	   			//Converting month number string to int
     	   			int month = Integer.parseInt(timeStringParts[3]);
     	   			
         	   		cal.set(Calendar.MONTH, month);
     	   			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(timeStringParts[4]));
     	   			cal.set(Calendar.YEAR, Integer.parseInt(timeStringParts[2]));
         	   		
     	   		} catch(NumberFormatException e){
     	   			//String is text version of month e.g. Sep so convert to int.
     	   			
     	   			try {
						Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(timeStringParts[3]);
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(date);
						
						cal.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
						cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(timeStringParts[2]));
	     	   			cal.set(Calendar.YEAR, Integer.parseInt(timeStringParts[4]));
	     	   			
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
     	   		}
     	   		
   			} else{
   				
			   closeTime = timeStringParts[2];
			   
			   try{
				   //Converting month number string to int
				   int month = Integer.parseInt(timeStringParts[4]);
				   
				   cal.set(Calendar.MONTH, month);
				   cal.set(Calendar.YEAR, Integer.parseInt(timeStringParts[3]));
				   cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(timeStringParts[5]));
    	   			
			   } catch(NumberFormatException e){
				   //String is text version of month e.g. Sep so convert to int.
				   
				   try {
					   Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(timeStringParts[4]);
					   Calendar cal2 = Calendar.getInstance();
					   cal2.setTime(date);
					   
					   cal.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
					   cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(timeStringParts[3]));
					   cal.set(Calendar.YEAR, Integer.parseInt(timeStringParts[5]));
					   
				   } catch (ParseException e1) {
					   e1.printStackTrace();
				   }
			   }
		   }
     	   
     	   OpeningTime o = new OpeningTime(caffeineSourceId, day , openTime, closeTime , cal);
     	   openingTimes.add(o);
        }
        
        if(openingTimes.size() != 0){
        	//Process opening times to get current term's opening times.
        	
        	//Sort OpeningTime objects in DESC order.
	        Collections.sort(openingTimes, new OpeningTimeComparator());
	  	   
	  	   	ArrayList<OpeningTime> currentOpeningTimes = new ArrayList<OpeningTime>();
	  	   	Calendar today = Calendar.getInstance();
	  	   
	  	   	boolean adding = false;
	  	    int day = 0;
	  	    int month = 0;
	  	    int year = 0;
	  	   
	  	   	//Loop to get current term opening hours
	  	   	for(OpeningTime ot : openingTimes){
	  	   		
	  	   		if(ot.getDate().before(today)){
	  	   				
	  	   			/*
	  	   			 * Conditions met are:
	  	   			 * 
	  	   			 * 1. Years same.
	  	   			 * 2. Month is less than or equal to today's month.
	  	   			 * 3. Day is less than or equal to today's day.
	  	   			 */

   					if(adding == false){
   						//Start adding OpeningTime objects to ArrayList
   						
   						adding = true;
   						day = ot.getDate().get(Calendar.DAY_OF_MONTH);
   						month = ot.getDate().get(Calendar.MONTH);
   						year = ot.getDate().get(Calendar.YEAR);
   						
   						currentOpeningTimes.add(ot);
   					
   					} else if(adding == true && ot.getDate().get(Calendar.DAY_OF_MONTH) == day && 
   							ot.getDate().get(Calendar.MONTH) == month && ot.getDate().get(Calendar.YEAR) == year){
   						//While dates same and adding is true keep adding OpeningTime objects.
   						
   						currentOpeningTimes.add(ot);
   					} else{
   						//Dates changed so break out of loop.
   					
   						break;
   					}
	  	   		} 
	  	   	}

	        return currentOpeningTimes;
        }
        
        return openingTimes;
	}
	
	/**
	 * Method to get caffeine products for a caffeine source.
	 * 
	 * N.B. Sources will return empty ArrayList when don't have product lists.
	 * 
	 * Products can be of the form:
	 * 	Powerade Bottle - £1.30 (Student Price)
	 *	Tea - White - £1.20 (Staff Price)
	 *	Cappuchino - £ 1.56 (Staff Price)
	 *	Filter Coffee (Large) - £1.60 (Staff Price)
	 *	Red Bull Can - £ 1.40
	 * 
	 * @param caffeineSourceId (URI for caffeine source)
	 * @return ArrayList of CaffeineProduct objects
	 */
	public static ArrayList<CaffeineProduct> getCaffeineProducts(String caffeineSourceId){
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		ResultSet caffeineProductsResults = performQuery(CAFFEINE_PRODUCTS_QUERY1 + caffeineSourceId + CAFFEINE_PRODUCTS_QUERY2);

		//Iterate through results
        while (caffeineProductsResults.hasNext()) {
  
        	QuerySolution solution = caffeineProductsResults.next();
            
        	String id = solution.getResource("product").getURI();       
        	String label = solution.getLiteral("label").getString();
     	   	String name = label.substring(0 , label.lastIndexOf("-"));
            String type;
            String price;

            //Set correct type and obtain price information.
 	   		if(id.endsWith(STAFF_TYPE)){
 	   			//Staff Product
 	   			
 	   			type = STAFF_TYPE;
 	   			price = label.substring(label.lastIndexOf("-") + 1, label.lastIndexOf("(Staff Price)")).trim();
 	   			
 	   		} else if(id.endsWith(STUDENT_TYPE)){
 	   			//Student Product
 	   			
 	   			type = STAFF_TYPE;
 	   			price = label.substring(label.lastIndexOf("-") + 1, label.lastIndexOf("(Student Price)")).trim();
 	   			
 	   		} else{
 	   			//All Product.
 	   			
 	   			type = ALL_TYPE;
 	   			price = label.substring(label.lastIndexOf("-") + 1).trim();
 	   			
 	   		}
 	   		
     	   	products.add(new CaffeineProduct(id, caffeineSourceId, name, price, type));
     	   	
        }
        
		return products;
	}
}


