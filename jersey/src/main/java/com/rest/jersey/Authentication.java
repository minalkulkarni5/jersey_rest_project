package com.rest.jersey;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class Authentication implements ContainerRequestFilter
{
     
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String USERNAME = "USER1";
    private static final String PASSWORD = "Password";
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
      
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        Method method = resourceInfo.getResourceMethod();
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                         .entity("No access to all users ").build());
                return;
            }
              
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
              
            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("This resource cannot be accessed").build());
                return;
            }
              
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
              
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
  
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
              
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
                Set<String> roles = new HashSet<String>(Arrays.asList(rolesAllowed.value()));
                  
                //Is user valid?
                if( ! isUserAllowed(username, password, roles))
                {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("This resource cannot be accessed").build());
                    return;
                }
            }
        }
    }
    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {
        boolean isAllowed = false;
          
        if(username.equals(USERNAME) && password.equals(PASSWORD))
        {
            String userRole = "ADMIN";
             
            if(rolesSet.contains(userRole))
            {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}
