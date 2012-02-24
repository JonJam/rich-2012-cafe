package com.googlecode.rich2012cafe.model;

import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

/**
 * Resources used:
 * 	- http://code.google.com/p/androjena/
 * 	- http://monead.com/blog/?p=1420
 * 
 * TODO Sort out querying and objects being created/stored
 * TODO Tidy/Structure class so readable.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com), Samantha Kanza (samikanza@gmail.com)
 */
public class SPARQLQuerier {
	
	private static final String DATA_SOUTHAMPTON_ENDPOINT = "http://sparql.data.southampton.ac.uk/";
	private static final String CAFFEINE_LOCATIONS_QUERY = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
			+ "PREFIX sr: <http://data.ordnancesurvey.co.uk/ontology/spatialrelations/>"
			+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
			+ "PREFIX rooms: <http://vocab.deri.ie/rooms#>"
			+ "PREFIX pos: <http://id.southampton.ac.uk/generic-products-and-services/>"
			+ "SELECT DISTINCT ?pos ?building ?buildinglabel ?buildinglat ?buildinglong ?lat ?long WHERE {"
				+ "?poscaffeine purl:includes pos:Caffeine ."
				+ "?poscaffeine purl:availableAtOrFrom ?pos ."
				+ "OPTIONAL {"
					+ "?pos sr:within ?building ."
					+ "?building a rooms:Building ; rdfs:label ?buildinglabel ."
					+ "OPTIONAL { ?building skos:notation ?buildingid }"
					+ "OPTIONAL { ?building geo:lat ?buildinglat ; geo:long ?buildinglong }"
				+ "}"
				+ "OPTIONAL { ?pos geo:lat ?lat ; geo:long ?long }"
			+ "}";
	
	private static ResultSet performQuery(String queryString){
		
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
	                
        QueryExecution qe = QueryExecutionFactory.sparqlService(DATA_SOUTHAMPTON_ENDPOINT, query);

        // Execute the query and obtain results
        ResultSet resultSet = qe.execSelect();
    
        // Free up resources used running the query
        qe.close();
        
        return resultSet;
	}
	
	public static String getCaffeineSources(){

		ResultSet caffeineLocationsResults = performQuery(CAFFEINE_LOCATIONS_QUERY);
		
        StringBuffer results = new StringBuffer();

        // Iterate through all resulting rows
        while (caffeineLocationsResults.hasNext()) {
        	
            // Get the next result row
            QuerySolution solution = caffeineLocationsResults.next();
            
            String locationURI = solution.getResource("pos").getURI();
            
//            ResultSet openingTimesResults = performQuery("PREFIX purl: <http://purl.org/goodrelations/v1#>"
//        			+ "SELECT DISTINCT ?times WHERE {"
//        				+ "<" + locationURI + ">" + " purl:hasOpeningHoursSpecification ?times ."
//        			+ "}");
            
            ResultSet caffeineProductsResults = performQuery("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
            		+ "PREFIX purl: <http://purl.org/goodrelations/v1#>"
            		+ "SELECT DISTINCT ?product ?label WHERE {"
            			+ "?product purl:availableAtOrFrom <" + locationURI + "> ."
            			+ "?product rdfs:label ?label ."
            			+ "FILTER (regex(?label, 'coke|coffee|tea|relentless|powerade|lucozade|red bull|frappe|cappuchino|latte|iced tea', 'i') ||"
            			+ "?label = 'Caffeine')"
        			+ "}");
        	
            // Get the column names (the aliases supplied in the SELECT clause)
            List<String> columnNames = caffeineProductsResults.getResultVars();

            results.append(locationURI + "\n");
            
            while (caffeineProductsResults.hasNext()){
            
                QuerySolution solution1 = caffeineProductsResults.next();
                
            	 // Iterate through the columns
                for (String var : columnNames) {
                    // Add the column label to the StringBuffer
                    results.append(var + ": ");

                    // Add the returned row/column data to the StringBuffer
                    
                    // Data value will be null if optional and not present
                    if (solution1.get(var) == null) {
                        results.append("{null}");
                    // Test whether the returned value is a literal value
                    } else if (solution1.get(var).isLiteral()) {
                        results.append(solution1.getLiteral(var).toString());
                    // Otherwise the returned value is a URI
                    } else {
                        results.append(solution1.getResource(var).getURI());
                    }
                    results.append('\n');
                }
            }
           
            results.append("-----------------\n");
        }
        
        // Return the results as a String
        return results.toString();
	}
}
