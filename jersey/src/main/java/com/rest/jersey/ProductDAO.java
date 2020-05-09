package com.rest.jersey;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	List<Product> products;
	
	public ProductDAO() {
		products = new ArrayList<Product>();
		
		Product product1 = new Product();
		product1.setProductID(1);
		product1.setProductName("Product1");
		
		Product product2 = new Product();
		product2.setProductID(2);
		product2.setProductName("Product2");
		
		products.add(product1);
		products.add(product2);
		
	}
	
	public List<Product> getProductDetails() {
		return products;
	}
}
