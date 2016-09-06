/*
 * (c) 2016 Novetta
 */
package com.github.mike10004.cargotomcat8;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author mike
 */
@Path("context")
public class MessageResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("init-param/{paramName}")
    public java.lang.String getContextInitParam(@Context ServletContext servletContext, @PathParam("paramName") String paramName) {
        return servletContext.getInitParameter(paramName);
    }

}
