package com.googlecode.rich2012cafe.server.sparql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

/**
 * Resources used:
 * 	- http://incubator.apache.org/jena/index.html
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com), Samantha Kanza (samikanza@gmail.com)
 */
public class SPARQLQuerier {
	
	//SPARQL Endpoint
	private static final String DATA_SOUTHAMPTON_ENDPOINT = "http://sparql.data.southampton.ac.uk/";
	
	//Fields
	private static final String SOURCE_ID = "id";
	private static final String SOURCE_NAME = "name";
	private static final String SOURCE_BUILDING_ID = "buildingid";
	private static final String SOURCE_BUILDING_NAME = "buildingname";
	private static final String SOURCE_BUILDING_LAT = "buildinglat";
	private static final String SOURCE_BUILDING_LONG = "buildinglong";
	
	private static final String TIME_ID = "id";
	private static final String TIME_OPENS = "opens";
	private static final String TIME_CLOSES = "closes";
	private static final String TIME_FROM = "from";
	private static final String TIME_TO = "to";
	private static final String TIME_DAY = "day";
	
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "name";
	private static final String PRODUCT_PRICE = "price";
	
	//Queries
	private static final String CAFFEINE_SOURCES_QUERY = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
			+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ " PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
			+ " PREFIX sr: <http://data.ordnancesurvey.co.uk/ontology/spatialrelations/>"
			+ " PREFIX purl: <http://purl.org/goodrelations/v1#>"
			+ " PREFIX rooms: <http://vocab.deri.ie/rooms#>"
			+ " PREFIX pos: <http://id.southampton.ac.uk/generic-products-and-services/>"
			+ " SELECT DISTINCT ?" + SOURCE_ID + " ?" + SOURCE_NAME + " ?" + SOURCE_BUILDING_ID + " ?" + SOURCE_BUILDING_NAME
			+ " ?" + SOURCE_BUILDING_LAT + " ?" + SOURCE_BUILDING_LONG + " WHERE {"
				+ " {"
					+ " ?poscaffeine purl:includes pos:Caffeine ."
					+ " ?poscaffeine purl:availableAtOrFrom ?" + SOURCE_ID + " ."
					+ " ?" + SOURCE_ID + " rdfs:label ?" + SOURCE_NAME + " ."
					+ " ?" + SOURCE_ID + " sr:within ?building ."
					+ " ?building a rooms:Building ; rdfs:label ?" + SOURCE_BUILDING_NAME + " ."
					+ " ?building skos:notation ?" + SOURCE_BUILDING_ID + " ."
					+ " ?building geo:lat ?" + SOURCE_BUILDING_LAT + " ; geo:long ?" + SOURCE_BUILDING_LONG
				+ " } UNION {"
					+ " ?poscaffeine purl:includes pos:Caffeine ."
					+ " ?poscaffeine purl:availableAtOrFrom ?" + SOURCE_ID + " ."
					+ " ?" + SOURCE_ID + " rdfs:label ?" + SOURCE_NAME + " ."
					+ " ?" + SOURCE_ID + " rdfs:label ?" + SOURCE_BUILDING_NAME + " ."
					+ " ?" + SOURCE_ID + " geo:lat ?" + SOURCE_BUILDING_LAT + " ; geo:long ?" + SOURCE_BUILDING_LONG + " ."
				+ " }"
			+ " }";
	
	private static final String OPENING_TIMES_QUERY1 = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
			+ "PREFIX xs: <http://www.w3.org/2001/XMLSchema#>"
			+ "SELECT DISTINCT * WHERE { <";
	private static final String OPENING_TIMES_QUERY2 = "> gr:hasOpeningHoursSpecification ?" + TIME_ID + " ."
				+ " ?" + TIME_ID + " gr:opens ?" + TIME_OPENS + " ; gr:closes ?" + TIME_CLOSES + " ; gr:validFrom ?" + TIME_FROM 
				+ " ; gr:validThrough ?" + TIME_TO +" ; "
				+ "gr:hasOpeningHoursDayOfWeek ?" + TIME_DAY + " . "
				+ "FILTER('";
	private static final String OPENING_TIMES_QUERY3 = "'^^xs:dateTime > ?" + TIME_FROM + " && '";
	private static final String OPENING_TIMES_QUERY4 = "'^^xs:dateTime < ?" + TIME_TO + " )}";
	
	private static final String CAFFEINE_PRODUCTS_QUERY1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
				+ "SELECT DISTINCT ?" + PRODUCT_ID + " ?" + PRODUCT_NAME + " ?" + PRODUCT_PRICE + " WHERE {"
					+ " ?" + PRODUCT_ID + " purl:availableAtOrFrom <";
	private static final String CAFFEINE_PRODUCTS_QUERY2 = "> ."
				+ " ?" + PRODUCT_ID + " purl:includes ?product ."
				+ " ?product rdfs:label ?" + PRODUCT_NAME + " ."
				+ " ?" + PRODUCT_ID + " rdfs:label ?" + PRODUCT_PRICE + " ."
				+ "FILTER (regex(?" + PRODUCT_NAME + " , '^coke | coke |^coffee | coffee |^tea | tea |^relentless | relentless "
				+ "|^powerade | powerade |^lucozade | lucozade |^red bull | red bull |^frappe | frappe |^cappuchino "
				+ "| cappuchino |^americano | americano |^latte | latte |^espresso | espresso |^iced teas | iced teas "
				+ "|^speciality teas | speciality teas ', 'i')"
				+ "&& regex(?" + PRODUCT_NAME + " , '^((?!decaf).)*$', 'i') && regex(?" + PRODUCT_NAME + " , '^((?!peppermint).)*$', 'i')"
				+ "&& regex(?" + PRODUCT_NAME + " , '^((?!camomile).)*$', 'i'))}";
	
	//Types
	private static final String POS_TYPE = "Point of Service";
	private static final String VENDING_MACHINE_TYPE = "Vending Machine";
	private static final String STAFF_TYPE = "Staff";
	private static final String STUDENT_TYPE = "Student";
	private static final String ALL_TYPE = "All";
	private static final String SOFT_DRINK_TYPE = "Soft Drink";
	private static final String COFFEE_TYPE = "Coffee";
	private static final String TEA_TYPE = "Tea";
	private static final String ENERGY_DRINK_TYPE = "Energy Drink";
		
	/**
	 * Method to execute a SPARQL query and obtain sources of Caffeine as CaffeineSource objects.
	 * 
	 * @return ArrayList of CaffeineSource objects
	 */
	public ArrayList<CaffeineSource> getCaffeineSources(){

		ArrayList<CaffeineSource> sources = new ArrayList<CaffeineSource>();
		
		//Execute query
		Query query = QueryFactory.create(CAFFEINE_SOURCES_QUERY, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet caffeineSourcesResults = qe.execSelect();

		//Iterate through results.
        while (caffeineSourcesResults.hasNext()) {
        	
            QuerySolution solution = caffeineSourcesResults.next();
            
            String id = solution.getResource(SOURCE_ID).getURI();
            String buildingNumber;
            int offCampus;
            String type;
            
            if(id.contains("vending-machine")){
            	//Vending machine type
            	
            	type = VENDING_MACHINE_TYPE;
            } else{
            	//Point of Service type
            	
            	type = POS_TYPE;
            }
            
            if(solution.get(SOURCE_BUILDING_ID) == null){
            	//No building number so set as off campus
            	
            	buildingNumber = "N/A";
            	offCampus = 1;
            } else{
            	//Building number so set as on campus
            	
            	buildingNumber = solution.getLiteral(SOURCE_BUILDING_ID).getString();
            	offCampus = 0;
            }
            
            CaffeineSource source = new CaffeineSource(id, 
            		solution.getLiteral(SOURCE_NAME).getString(), 
            		buildingNumber, 
            		solution.getLiteral(SOURCE_BUILDING_NAME).getString(), 
            		solution.getLiteral(SOURCE_BUILDING_LAT).getDouble(), 
            		solution.getLiteral(SOURCE_BUILDING_LONG).getDouble(), type, offCampus);
            sources.add(source);

        }
        
        qe.close();
        
        return sources;
	}
	
	/**
	 * Method to execute a SPARQL query and obtain opening times for a caffeine source for current term.
	 * 
	 * N.B. Sources will return empty ArrayList when don't have opening times.
	 * 
	 * @param caffeineSourceId (URI for caffeine source)
	 * @return ArrayList of OpeningTime objects.
	 */
	public ArrayList<OpeningTime> getCurrentOpeningTimes(String caffeineSourceId){

		ArrayList<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
		String todayString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
		
		//Execute query
		Query query = QueryFactory.create(OPENING_TIMES_QUERY1 + caffeineSourceId + OPENING_TIMES_QUERY2 +
				todayString + OPENING_TIMES_QUERY3 + todayString + OPENING_TIMES_QUERY4, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet  openingTimesResults = qe.execSelect();

		//Iterate through results
        while (openingTimesResults.hasNext()) {
        	
            QuerySolution solution = openingTimesResults.next();
            
            String id = solution.getResource(TIME_ID).getURI();
            String dayURI = solution.getResource(TIME_DAY).getURI();
            String day = dayURI.substring(dayURI.indexOf("#") + 1);
            String openTime = solution.getLiteral(TIME_OPENS).getString();
            String closeTime = solution.getLiteral(TIME_CLOSES).getString();
            Date validFrom = null;
            Date validTo = null;
            
    		try {
    			validFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(solution.getLiteral(TIME_FROM).getString());
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		
    		try {
    			validTo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(solution.getLiteral(TIME_TO).getString());
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
            
            openingTimes.add(new OpeningTime(id, caffeineSourceId, day, openTime, closeTime, validFrom, validTo));
        }
        
        qe.close();
        
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
	public ArrayList<CaffeineProduct> getCaffeineProducts(String caffeineSourceId){
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		
		//Execute query
		Query query = QueryFactory.create(CAFFEINE_PRODUCTS_QUERY1 + caffeineSourceId + CAFFEINE_PRODUCTS_QUERY2, 
				Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet caffeineProductsResults = qe.execSelect();
	
		//Iterate through results
        while (caffeineProductsResults.hasNext()) {
  
        	QuerySolution solution = caffeineProductsResults.next();
        	
        	String id = solution.getResource(PRODUCT_ID).getURI();
     	   	String name = solution.getLiteral(PRODUCT_NAME).getString();
     	   	
            String priceString = solution.getLiteral(PRODUCT_PRICE).getString().replace(name, "");
            
     	   	double price;
     	   	String currency;
            String type;
            String productType = "";

            //Set correct type and obtain price information.
 	   		if(id.endsWith(STAFF_TYPE)){
 	   			//Staff Product
 	   			
 	   			type = STAFF_TYPE;
 	   			priceString = priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else if(id.endsWith(STUDENT_TYPE)){
 	   			//Student Product
 	   			
 	   			type = STUDENT_TYPE;
 	   			priceString= priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else{
 	   			//All Product.
 	   			
 	   			type = ALL_TYPE;
 	   			priceString = priceString.substring(priceString.indexOf("-") + 1).trim();
 	   			
 	   		}
 	   		
   			currency = String.valueOf(priceString.toCharArray()[0]);
   			price = Double.parseDouble((priceString.substring(priceString.indexOf(currency) + 1).trim()));
 	   		
 	   		String nameForComparison = name.toLowerCase();
 	   		if(nameForComparison.contains("coke")){
 	   			productType = SOFT_DRINK_TYPE;
 	   		} else if(nameForComparison.contains("coffee")){
 	   			productType = COFFEE_TYPE;
 	   		} else if(nameForComparison.contains("tea")){
 	   			productType = TEA_TYPE;
 	   		} else if(nameForComparison.contains("relentless") || nameForComparison.contains("powerade") 
 	   				|| nameForComparison.contains("lucozade") || nameForComparison.contains("red bull")){
 	   			productType = ENERGY_DRINK_TYPE;
 	   		} else{
 	   			//All others such as frappe, cappuchino, americano, latte, esspresso
 	   			productType = COFFEE_TYPE;
 	   		}
 	   		
     	   	products.add(new CaffeineProduct(id, caffeineSourceId, name, price, currency, productType, type));
     	   	
        }
        
        qe.close();
        
		return products;
	}
}