package br.com.m4u.sms.app.service;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class JsonObjectMapper implements ContextResolver<ObjectMapper> {
 
	private final ObjectMapper defaultObjectMapper;

    public JsonObjectMapper() {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
    	return defaultObjectMapper;

    }

    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.enable(SerializationFeature.INDENT_OUTPUT);
        return result;
    }
}
