package com.googlecode.rich2012cafe.model;

/**
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
public class AppDataStore {

	public String getData(){
		return SPARQLQuerier.certainSPARQLQuery();
	}
}