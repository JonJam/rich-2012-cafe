package com.googlecode.rich2012cafe.server.utils;

/**
 * Class to contain all static variables used throughout project.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class Rich2012CafeUtil {

	//SPARQL Endpoint
	public static final String DATA_SOUTHAMPTON_ENDPOINT = "http://sparql.data.southampton.ac.uk/";
		
	//SPARQL Fields
	public static final String SOURCE_ID = "id";
	public static final String SOURCE_NAME = "name";
	public static final String SOURCE_BUILDING_ID = "buildingid";
	public static final String SOURCE_BUILDING_NAME = "buildingname";
	public static final String SOURCE_BUILDING_LAT = "buildinglat";
	public static final String SOURCE_BUILDING_LONG = "buildinglong";
	
	public static final String TIME_ID = "id";
	public static final String TIME_OPENS = "opens";
	public static final String TIME_CLOSES = "closes";
	public static final String TIME_FROM = "from";
	public static final String TIME_TO = "to";
	public static final String TIME_DAY = "day";
	
	public static final String PRODUCT_ID = "id";
	public static final String PRODUCT_NAME = "name";
	public static final String PRODUCT_PRICE = "price";
		
	//SPARQL Queries
	public static final String CAFFEINE_SOURCES_QUERY = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
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
		
	public static final String OPENING_TIMES_QUERY1 = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
		+ "PREFIX xs: <http://www.w3.org/2001/XMLSchema#>"
		+ "SELECT DISTINCT * WHERE { <";
	public static final String OPENING_TIMES_QUERY2 = "> gr:hasOpeningHoursSpecification ?" + TIME_ID + " ."
		+ " ?" + TIME_ID + " gr:opens ?" + TIME_OPENS + " ; gr:closes ?" + TIME_CLOSES + " ; gr:validFrom ?" + TIME_FROM 
		+ " ; gr:validThrough ?" + TIME_TO +" ; "
		+ "gr:hasOpeningHoursDayOfWeek ?" + TIME_DAY + " . }";
	
	public static final String CURRENT_OPENING_TIMES_QUERY1 = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
		+ "PREFIX xs: <http://www.w3.org/2001/XMLSchema#>"
		+ "SELECT DISTINCT * WHERE { <";
	public static final String CURRENT_OPENING_TIMES_QUERY2 = "> gr:hasOpeningHoursSpecification ?" + TIME_ID + " ."
		+ " ?" + TIME_ID + " gr:opens ?" + TIME_OPENS + " ; gr:closes ?" + TIME_CLOSES + " ; gr:validFrom ?" + TIME_FROM 
		+ " ; gr:validThrough ?" + TIME_TO +" ; "
		+ "gr:hasOpeningHoursDayOfWeek ?" + TIME_DAY + " . "
		+ "FILTER('";
	public static final String CURRENT_OPENING_TIMES_QUERY3 = "'^^xs:dateTime > ?" + TIME_FROM + " && '";
	public static final String CURRENT_OPENING_TIMES_QUERY4 = "'^^xs:dateTime < ?" + TIME_TO + " )}";
		
	public static final String CAFFEINE_PRODUCTS_QUERY1 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
		+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
		+ "SELECT DISTINCT ?" + PRODUCT_ID + " ?" + PRODUCT_NAME + " ?" + PRODUCT_PRICE + " WHERE {"
			+ " ?" + PRODUCT_ID + " purl:availableAtOrFrom <";
	public static final String CAFFEINE_PRODUCTS_QUERY2 = "> ."
				+ " ?" + PRODUCT_ID + " purl:includes ?product ."
				+ " ?product rdfs:label ?" + PRODUCT_NAME + " ."
				+ " ?" + PRODUCT_ID + " rdfs:label ?" + PRODUCT_PRICE + " ."
				+ "FILTER (regex(?" + PRODUCT_NAME + " , '^coke | coke |^coffee | coffee |^tea | tea |^relentless | relentless "
				+ "|^powerade | powerade |^lucozade | lucozade |^red bull | red bull |^frappe | frappe |^cappuchino "
				+ "| cappuchino |^americano | americano |^latte | latte |^espresso | espresso |^iced teas | iced teas "
				+ "|^speciality teas | speciality teas ', 'i')"
				+ "&& regex(?" + PRODUCT_NAME + " , '^((?!decaf).)*$', 'i') && regex(?" + PRODUCT_NAME + " , '^((?!peppermint).)*$', 'i')"
				+ "&& regex(?" + PRODUCT_NAME + " , '^((?!camomile).)*$', 'i'))}";
	
	//Date Format
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		
	//Source Types
	public static final String POS_TYPE = "Point of Service";
	public static final String VENDING_MACHINE_TYPE = "Vending Machine";
	
	//Product Price Types
	public static final String STAFF_TYPE = "Staff";
	public static final String STUDENT_TYPE = "Student";
	public static final String ALL_TYPE = "All";
	
	//Product Types
	public static final String SOFT_DRINK_TYPE = "Soft Drink";
	public static final String COFFEE_TYPE = "Coffee";
	public static final String TEA_TYPE = "Tea";
	public static final String ENERGY_DRINK_TYPE = "Energy Drink";
	
	//Caffeine Content (In Milligrams)
	public static final double SOFT_DRINK_CAFFEINE_CONTENT = 34.5;
	public static final double COFFEE_CAFFEINE_CONTENT = 54;
	public static final double TEA_CAFFEINE_CONTENT = 40;
	public static final double ENERGY_DRINK_CAFFEINE_CONTENT = 80;
	
	//Earth Radius (In KM)
	public static final double EARTH_RADIUS = 6371;
}
