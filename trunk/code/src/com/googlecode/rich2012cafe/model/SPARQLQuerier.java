package com.googlecode.rich2012cafe.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
 * 
 * TODO Change product query to filter out decaffenated products (SAMI - DOING)
 * 		TO ADD NOT KEYWORD APPEND: && regex(?name, '^((?!keyword).)*$', 'i'))
 * 
 * TODO Add lat and log columns to get caffeine sources if need to.
 * 
 * TODO Sort out storage of information (JON - WORKING ON CREATING DataSource classes)
 * TODO Test database
 * TODO set up database so expires and updates data automatically (STORE DATA UPTO END OF TERM AS OPENING TIMES WILL CHANGE SO UPDATE)
 * 
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
	
	private static final String OPENING_TIMES_QUERY1 = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
			+ "PREFIX xs: <http://www.w3.org/2001/XMLSchema#>"
			+ "SELECT DISTINCT * WHERE { <";
	private static final String OPENING_TIMES_QUERY2 = "> gr:hasOpeningHoursSpecification ?id ."
				+ "?id gr:opens ?opens ; gr:closes ?closes ; gr:validFrom ?from ;gr:validThrough ?to; gr:hasOpeningHoursDayOfWeek ?day ."
				+ "FILTER('";
	private static final String OPENING_TIMES_QUERY3 = "'^^xs:dateTime > ?from && '";
	private static final String OPENING_TIMES_QUERY4 = "'^^xs:dateTime < ?to)}";

	private static final String CAFFEINE_PRODUCTS_QUERY1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
				+ "SELECT DISTINCT ?id ?name ?price WHERE {"
					+ "?id purl:availableAtOrFrom <";
	private static final String CAFFEINE_PRODUCTS_QUERY2 = "> ."
				+ "?id purl:includes ?product ."
				+ "?product rdfs:label ?name ."
				+ "?id rdfs:label ?price ."
				+ "FILTER (regex(?name, '^coke | coke |^coffee | coffee |^tea | tea |^relentless | relentless "
				+ "|^powerade | powerade |^lucozade | lucozade |^red bull | red bull |^frappe | frappe |^cappuchino "
				+ "| cappuchino |^americano | americano |^latte | latte |^espresso | espresso |^iced teas | iced teas "
				+ "|^speciality teas | speciality teas ', 'i'))}";
	
	private static final String STAFF_TYPE = "Staff";
	private static final String STUDENT_TYPE = "Student";
	private static final String ALL_TYPE = "All";
	private static final String SOFT_DRINK_TYPE = "Soft Drink";
	private static final String COFFEE_TYPE = "Coffee";
	private static final String TEA_TYPE = "Tea";
	private static final String ENERGY_DRINK_TYPE = "Energy Drink";
	
	/**
	 * Method to execute a SPARQL query at SPARQL endpoint.
	 * 
	 * @param queryString (Query to execute)
	 * @return ResultSet (Query results)
	 */
	private ResultSet performQuery(String queryString){
		
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
	public ArrayList<CaffeineSource> getCaffeineSources(){

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
	public ArrayList<OpeningTime> getCurrentOpeningTimes(String caffeineSourceId){

		ArrayList<OpeningTime> openingTimes = new ArrayList<OpeningTime>();
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
		
		ResultSet openingTimesResults = performQuery(OPENING_TIMES_QUERY1 + caffeineSourceId + OPENING_TIMES_QUERY2 +
				todayString + OPENING_TIMES_QUERY3 + todayString + OPENING_TIMES_QUERY4);

		//Iterate through results
        while (openingTimesResults.hasNext()) {
        	
            QuerySolution solution = openingTimesResults.next();
            
            String id = solution.getResource("id").getURI();
            String dayURI = solution.getResource("day").getURI();
            String day = dayURI.substring(dayURI.indexOf("#") + 1);
            String openTime = solution.getLiteral("opens").getString();
            String closeTime = solution.getLiteral("closes").getString();
            String validFrom = solution.getLiteral("from").getString();
            String validTo = solution.getLiteral("to").getString();
            
            openingTimes.add(new OpeningTime(id, caffeineSourceId, day, openTime, closeTime, validFrom, validTo));
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
	public ArrayList<CaffeineProduct> getCaffeineProducts(String caffeineSourceId){
		
		ArrayList<CaffeineProduct> products = new ArrayList<CaffeineProduct>();
		ResultSet caffeineProductsResults = performQuery(CAFFEINE_PRODUCTS_QUERY1 + caffeineSourceId + CAFFEINE_PRODUCTS_QUERY2);

		//Iterate through results
        while (caffeineProductsResults.hasNext()) {
  
        	QuerySolution solution = caffeineProductsResults.next();
        	
        	String id = solution.getResource("id").getURI();
     	   	String name = solution.getLiteral("name").getString();
     	   	
            String priceString = solution.getLiteral("price").getString().replace(name, "");
            
     	   	String price;
            String type;
            String productType = "";

            //Set correct type and obtain price information.
 	   		if(id.endsWith(STAFF_TYPE)){
 	   			//Staff Product
 	   			
 	   			type = STAFF_TYPE;
 	   			price = priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else if(id.endsWith(STUDENT_TYPE)){
 	   			//Student Product
 	   			
 	   			type = STAFF_TYPE;
 	   			price = priceString.substring(priceString.indexOf("-") + 1, priceString.indexOf("(")).trim(); 
 	   			
 	   		} else{
 	   			//All Product.
 	   			
 	   			type = ALL_TYPE;
 	   			price = priceString.substring(priceString.indexOf("-") + 1).trim();
 	   			
 	   		}
 	   		
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
 	   		
     	   	products.add(new CaffeineProduct(id, caffeineSourceId, name, price, productType, type));
     	   	
        }
        
		return products;
	}
}