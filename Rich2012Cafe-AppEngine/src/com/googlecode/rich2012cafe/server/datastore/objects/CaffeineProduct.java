package com.googlecode.rich2012cafe.server.datastore.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class to represent a caffeine product. e.g Red Bull Can.
 * 
 * @author Jonathan Harrison (jonjam1990@googlemail.com)
 */
@Entity
public class CaffeineProduct {

	@Id
	private String name;
	//Type of product (Coffee, Tea, Energy Drink, Soft Drink).
	private String productType;
	//Milligrams of caffeine in product.
	private double caffeineContent;
	
	public CaffeineProduct(String name, String productType, double caffeineContent){
		this.name = name;
		this.productType = productType;
		this.caffeineContent = caffeineContent;
	}
	
	/**
	 * Method to get name.
	 * 
	 * @return name (String object)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to set name.
	 * 
	 * @param name (String object)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to get productType.
	 * 
	 * @return productType (String object)
	 */
	public String getProductType() {
		return productType;
	}
	
	/**
	 * Method to set productType.
	 * 
	 * @param productType (String object)
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	/**
	 * Method to get caffeineContent.
	 * 
	 * @return caffeineContent (double value)
	 */
	public double getCaffeineContent() {
		return caffeineContent;
	}
	
	/**
	 * Method to set caffeineContent
	 * 
	 * @param caffeineContent (double value)
	 */
	public void setCaffeineContent(double caffeineContent) {
		this.caffeineContent = caffeineContent;
	}
	
	/**
	 * Method to get String representation of object.
	 * 
	 * @return String object
	 */
	public String toString(){
		return "name: " + name + " productType: " + productType + " caffeineContent: " + caffeineContent;
	}	
}
