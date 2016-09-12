package br.com.m4u.sms.app.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/*")
public class ResourcesConfig extends ResourceConfig {  
    public ResourcesConfig() {
        packages(true, "br.com.m4u.sms.api.app.service");

        register(JsonObjectMapper.class);
        register(JacksonFeature.class);
    }
}