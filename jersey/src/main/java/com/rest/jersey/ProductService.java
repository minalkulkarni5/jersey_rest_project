package com.rest.jersey;

import java.util.List;

public class ProductService {

	ProductDAO productDAO;
	
	public ProductService() {
		productDAO = new ProductDAO();
	}
	
	public List<Product> getProductDetails() {
		return productDAO.getProductDetails();
	}

}
