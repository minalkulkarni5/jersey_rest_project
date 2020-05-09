package com.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;

public class AuthenticationRegister extends ResourceConfig{

	public AuthenticationRegister() 
    {
        register(Authentication.class);
    }

}
