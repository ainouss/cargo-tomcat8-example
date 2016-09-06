/*
 * (c) 2016 Novetta
 */
package com.github.mike10004.cargotomcat8;

import java.util.Set;

/**
 *
 * @author mike
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @java.lang.Override
    public java.util.Set<java.lang.Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.github.mike10004.cargotomcat8.MessageResource.class);
    }
    
}
