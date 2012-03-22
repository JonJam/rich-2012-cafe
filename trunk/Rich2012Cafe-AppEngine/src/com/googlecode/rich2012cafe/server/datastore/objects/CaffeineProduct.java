package com.googlecode.rich2012cafe.server.datastore.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CaffeineProduct {

	@Id
	private String name;
	private String productType;
	private double caffeineContent;
	
	public CaffeineProduct(String name, String productType, double caffeineContent){
		this.name = name;
		this.productType = productType;
		this.caffeineContent = caffeineContent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public double getCaffeineContent() {
		return caffeineContent;
	}
	public void setCaffeineContent(double caffeineContent) {
		this.caffeineContent = caffeineContent;
	}
	
	public String toString(){
		return "name: " + name + " productType: " + productType + " caffeineContent: " + caffeineContent;
	}	
}
