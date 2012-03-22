package com.googlecode.rich2012cafe.server.sparql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSourceProduct;
import com.googlecode.rich2012cafe.server.datastore.objects.CaffeineSource;
import com.googlecode.rich2012cafe.server.datastore.objects.OpeningTime;
import com.googlecode.rich2012cafe.server.utils.Rich2012CafeUtil;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

/**
 * Class to perform SPARQL queries on endpoints and create datastore objects.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com), Samantha Kanza (samikanza@gmail.com)
 */
public class SPARQLQuerier {
	
	private ArrayList<CaffeineProduct> products;
	
	public SPARQLQuerier(){
		products = new ArrayList<CaffeineProduct>();
	}
	
	/**
	 * Method to execute a SPARQL query and obtain sources of Caffeine as CaffeineSource objects.
	 * 
	 * @return ArrayList of CaffeineSource objects
	 */
	public ArrayList<CaffeineSource> getCaffeineSources(){

		ArrayList<CaffeineSource> sources = new ArrayList<CaffeineSource>();
		
		//Execute query
		Query query = QueryFactory.create(Rich2012CafeUtil.CAFFEINE_SOURCES_QUERY, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(Rich2012CafeUtil.DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet caffeineSourcesResults = qe.execSelect();

		//Iterate through results.
        while (caffeineSourcesResults.hasNext()) {
        	
            QuerySolution solution = caffeineSourcesResults.next();
            
            String id = solution.getResource(Rich2012CafeUtil.SOURCE_ID).getURI();
            String buildingNumber;
            int offCampus;
            String type;
            
            if(id.contains("vending-machine")){
            	//Vending machine type
            	
            	type = Rich2012CafeUtil.VENDING_MACHINE_TYPE;
            } else{
            	//Point of Service type
            	
            	type = Rich2012CafeUtil.POS_TYPE;
            }
            
            if(solution.get(Rich2012CafeUtil.SOURCE_BUILDING_ID) == null){
            	//No building number so set as off campus
            	
            	buildingNumber = "N/A";
            	offCampus = 1;
            } else{
            	//Building number so set as on campus
            	
            	buildingNumber = solution.getLiteral(Rich2012CafeUtil.SOURCE_BUILDING_ID).getString();
            	offCampus = 0;
            }
            
            CaffeineSource source = new CaffeineSource(id, 
            		solution.getLiteral(Rich2012CafeUtil.SOURCE_NAME).getString(), 
            		buildingNumber, 
            		solution.getLiteral(Rich2012CafeUtil.SOURCE_BUILDING_NAME).getString(), 
            		solution.getLiteral(Rich2012CafeUtil.SOURCE_BUILDING_LAT).getDouble(), 
            		solution.getLiteral(Rich2012CafeUtil.SOURCE_BUILDING_LONG).getDouble(), type, offCampus);
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
		String todayString = new SimpleDateFormat(Rich2012CafeUtil.DATE_FORMAT).format(Calendar.getInstance().getTime());
		
		//Execute query
		Query query = QueryFactory.create(Rich2012CafeUtil.OPENING_TIMES_QUERY1 + caffeineSourceId 
				+ Rich2012CafeUtil.OPENING_TIMES_QUERY2 + todayString 
				+ Rich2012CafeUtil.OPENING_TIMES_QUERY3 + todayString 
				+ Rich2012CafeUtil.OPENING_TIMES_QUERY4, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(Rich2012CafeUtil.DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet  openingTimesResults = qe.execSelect();

		//Iterate through results
        while (openingTimesResults.hasNext()) {
        	
            QuerySolution solution = openingTimesResults.next();
            
            String id = solution.getResource(Rich2012CafeUtil.TIME_ID).getURI();
            String dayURI = solution.getResource(Rich2012CafeUtil.TIME_DAY).getURI();
            String day = dayURI.substring(dayURI.indexOf("#") + 1);
            String openTime = solution.getLiteral(Rich2012CafeUtil.TIME_OPENS).getString();
            String closeTime = solution.getLiteral(Rich2012CafeUtil.TIME_CLOSES).getString();
            Date validFrom = null;
            Date validTo = null;
            
    		try {
    			validFrom = new SimpleDateFormat(Rich2012CafeUtil.DATE_FORMAT).parse(
    					solution.getLiteral(Rich2012CafeUtil.TIME_FROM).getString());
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		
    		try {
    			validTo = new SimpleDateFormat(Rich2012CafeUtil.DATE_FORMAT).parse(
    					solution.getLiteral(Rich2012CafeUtil.TIME_TO).getString());
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
	public ArrayList<CaffeineSourceProduct> getCaffeineSourceProducts(String caffeineSourceId){
		
		ArrayList<CaffeineSourceProduct> sourceProducts = new ArrayList<CaffeineSourceProduct>();
		
		//Execute query
		Query query = QueryFactory.create(Rich2012CafeUtil.CAFFEINE_PRODUCTS_QUERY1 + caffeineSourceId 
				+ Rich2012CafeUtil.CAFFEINE_PRODUCTS_QUERY2, Syntax.syntaxARQ);
        QueryExecution qe = QueryExecutionFactory.sparqlService(Rich2012CafeUtil.DATA_SOUTHAMPTON_ENDPOINT, query);
        ResultSet caffeineProductsResults = qe.execSelect();
	
		//Iterate through results
        while (caffeineProductsResults.hasNext()) {
  
        	QuerySolution solution = caffeineProductsResults.next();
        	
        	//Creating CaffeineSourceProduct object.
   			String name = solution.getLiteral(Rich2012CafeUtil.PRODUCT_NAME).getString();
        	String id = solution.getResource(Rich2012CafeUtil.PRODUCT_ID).getURI();
            String priceString = solution.getLiteral(Rich2012CafeUtil.PRODUCT_PRICE).getString().replace(name, "");
     	   	double price;
     	   	String currency;
            String priceType;
            
            //Set correct type and obtain price information.
 	   		if(id.endsWith(Rich2012CafeUtil.STAFF_TYPE)){
 	   			//Staff Product
 	   			
 	   			priceType = Rich2012CafeUtil.STAFF_TYPE;
 	   			priceString = priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else if(id.endsWith(Rich2012CafeUtil.STUDENT_TYPE)){
 	   			//Student Product
 	   			
 	   			priceType = Rich2012CafeUtil.STUDENT_TYPE;
 	   			priceString= priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else{
 	   			//All Product.
 	   			
 	   			priceType = Rich2012CafeUtil.ALL_TYPE;
 	   			priceString = priceString.substring(priceString.indexOf("-") + 1).trim();
 	   			
 	   		}
 	   		
   			currency = String.valueOf(priceString.toCharArray()[0]);
   			price = Double.parseDouble((priceString.substring(priceString.indexOf(currency) + 1).trim()));
 	   		
   			if(!containsProduct(name)){
   				//No existing CaffeineProduct so create one and add to products ArrayList
   				
   				products.add(createCaffeineProduct(name));
   			}
           
   		 	sourceProducts.add(new CaffeineSourceProduct(id, caffeineSourceId, name, price, currency, priceType)); 
        }
        
        qe.close();
        
		return sourceProducts;
	}
	
	/**
	 * Method to determine whether a CaffeineProduct object already exists with the given name.
	 * 
	 * @param name (String object)
	 * @return boolean value.
	 */
	private boolean containsProduct(String name){
		
		for(CaffeineProduct p : products){
			
			if(p.getName().equals(name)){
				//Object exists so return true.
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method to create a CaffeineProduct object.
	 * 
	 * @param name (String object)
	 * @return CaffeineProduct object
	 */
	private CaffeineProduct createCaffeineProduct(String name){
		
		String productType = "";
		double caffeineContent;
		String nameForComparison = name.toLowerCase();
	
   		if(nameForComparison.contains("coke")){
   			//Soft Drink Product
   			
   			productType = Rich2012CafeUtil.SOFT_DRINK_TYPE;
   			caffeineContent = Rich2012CafeUtil.SOFT_DRINK_CAFFEINE_CONTENT;
   			
   		} else if(nameForComparison.contains("coffee")){
   			//Coffee Product
   			
   			productType = Rich2012CafeUtil.COFFEE_TYPE;
   			caffeineContent = Rich2012CafeUtil.COFFEE_CAFFEINE_CONTENT;
   			
   		} else if(nameForComparison.contains("tea")){
   			//Tea Product
   			
   			productType = Rich2012CafeUtil.TEA_TYPE;
   			caffeineContent = Rich2012CafeUtil.TEA_CAFFEINE_CONTENT;
   			
   		} else if(nameForComparison.contains("relentless") || nameForComparison.contains("powerade") 
   				|| nameForComparison.contains("lucozade") || nameForComparison.contains("red bull")){
   			//Enery Drink Product
   			
   			productType = Rich2012CafeUtil.ENERGY_DRINK_TYPE;
   			caffeineContent = Rich2012CafeUtil.ENERGY_DRINK_CAFFEINE_CONTENT;
   			
   		} else{
   			//All others such as frappe, cappuchino, americano, latte, esspresso
   			
   			productType = Rich2012CafeUtil.COFFEE_TYPE;
   			caffeineContent = Rich2012CafeUtil.COFFEE_CAFFEINE_CONTENT;
   		}
	   		
   		return new CaffeineProduct(name, productType, caffeineContent);
	}
	
	/**
	 * Method to get list of CaffeineProduct object.
	 * 
	 * N.B. SHOULD ONLY BE CALLED AFTER CALLING getCaffeineSourceProducts.
	 * @return
	 */
	public ArrayList<CaffeineProduct> getCaffeineProducts(){
		return products;
	}
}