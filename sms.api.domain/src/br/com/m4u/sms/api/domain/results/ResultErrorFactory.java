package br.com.m4u.sms.api.domain.results;

import java.util.Properties;

import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.logging.Logger;

public class ResultErrorFactory {
	
	private static final String SPLIT_PATTERN = "\\|";
	
	public static ResultError factory(String messagePropertyKey) throws IntegrationException {
		try {
			
			Properties properties = new Properties();
			
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/errors.properties"));
			
			String[] splittedPropMsg = properties.getProperty(messagePropertyKey).split(SPLIT_PATTERN);
			
			if (splittedPropMsg.length > 1) {
				
				int code = Integer.parseInt(splittedPropMsg[0].trim());
				String message = splittedPropMsg[1].trim();
				
				return new ResultError(code, message);
			}
			else {
				return new ResultError(splittedPropMsg[0].trim());
			}
			
		} catch (Exception ex) {	
			Logger.logError(ResultErrorFactory.class, ex);
			throw new IntegrationException(ex);
		}
	}
}
