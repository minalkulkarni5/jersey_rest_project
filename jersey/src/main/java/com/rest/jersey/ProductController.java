package com.rest.jersey;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("getProductDetails")
public class ProductController {

	ProductService service = new ProductService();
	
	public ProductController() {
		service = new ProductService();
	}
	
	@RolesAllowed("ADMIN")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductDetails() {
		return service.getProductDetails();
	}
	
}
