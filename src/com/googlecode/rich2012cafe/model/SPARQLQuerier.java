package com.googlecode.rich2012cafe.model;

import java.util.List;
import com.hp.hpl.jena.query.*;

/**
 * http://code.google.com/p/androjena/
 * http://monead.com/blog/?p=1420
 * 
 * @author Jonathn Harrison (jonjam1990@googlemail.com)
 */
public class SPARQLQuerier {
	
	private static final String DATA_SOUTHAMPTON_ENDPOINT = "http://sparql.data.southampton.ac.uk/";
	
	public static String performQuery(){
		
		 // Set the query
		String queryString = "SELECT ?dataType ?data WHERE {<http://nasa.dataincubator.org/launch/1961-012> ?dataType ?data.}";
    
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        
        // Limit the number of results returned
        // Setting the limit is optional - default is unlimited
        query.setLimit(10);

        // Set the starting record for results returned
        // Setting the limit is optional - default is 1 (and it is 1-based)
        query.setOffset(1);
        
        String sparqlEndpointUri = "http://api.talis.com/stores/space/services/sparql";
        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlEndpointUri, query);

        // Execute the query and obtain results
        ResultSet resultSet = qe.execSelect();

        // Setup a place to house results for output
        StringBuffer results = new StringBuffer();

        // Get the column names (the aliases supplied in the SELECT clause)
        List<String> columnNames = resultSet.getResultVars();

        // Iterate through all resulting rows
        while (resultSet.hasNext()) {
        	
            // Get the next result row
            QuerySolution solution = resultSet.next();

            // Iterate through the columns
            for (String var : columnNames) {
                // Add the column label to the StringBuffer
                results.append(var + ": ");

                // Add the returned row/column data to the StringBuffer
                
                // Data value will be null if optional and not present
                if (solution.get(var) == null) {
                    results.append("{null}");
                // Test whether the returned value is a literal value
                } else if (solution.get(var).isLiteral()) {
                    results.append(solution.getLiteral(var).toString());
                // Otherwise the returned value is a URI
                } else {
                    results.append(solution.getResource(var).getURI());
                }
                results.append('\n');
            }
            results.append("-----------------\n");
        }

        // Important - free up resources used running the query
        qe.close();
        
        // Return the results as a String
        return results.toString();
	}
}
