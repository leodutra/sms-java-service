package br.com.m4u.sms.logging;

import java.util.logging.Level;

public class Logger {
	public static final void logError(Class<?> classDef, Throwable thrown) {
		final String className = classDef.getName();
		final StringBuilder message = new StringBuilder(thrown.getMessage().length() + 50)
				.append("Error occured inside class ")
				.append(className)
				.append(" (")
				.append(thrown.getMessage())
				.append(')');
						
		java.util.logging.Logger.getLogger(className).log(Level.SEVERE, message.toString(), thrown);
	}
}
