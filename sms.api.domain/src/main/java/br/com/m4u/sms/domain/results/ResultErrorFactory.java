package br.com.m4u.sms.domain.results;

import java.text.MessageFormat;
import java.util.Properties;

import br.com.m4u.sms.exceptions.IntegrationException;
import br.com.m4u.sms.logging.Logger;

public class ResultErrorFactory {
	
	private static final String SPLIT_PATTERN = "\\|";
	
	public static ResultError factory(String messagePropertyKey) throws IntegrationException {
		return factory(messagePropertyKey, null);
	}
	
	public static ResultError factory(String messagePropertyKey, Object ...params) throws IntegrationException {
		try {
			
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/errors.properties"));
			
			final String propertyValue = properties.getProperty(messagePropertyKey);
			
			if (propertyValue == null) throw new IntegrationException(MessageFormat.format("Could not load value of property {0}", messagePropertyKey));
			
			String[] splittedPropMsg = propertyValue.split(SPLIT_PATTERN);
			if (splittedPropMsg.length > 1) {
				
				int code = Integer.parseInt(splittedPropMsg[0].trim());
				String message = splittedPropMsg[1].trim();
				
				return new ResultError(code, MessageFormat.format(message, params));
			}
			else {
				String message = splittedPropMsg[0].trim();
				return new ResultError(MessageFormat.format(message, params));
			}
			
		} catch (Exception ex) {	
			Logger.logError(ResultErrorFactory.class, ex);
			throw new IntegrationException(ex);
		}
	}
}
