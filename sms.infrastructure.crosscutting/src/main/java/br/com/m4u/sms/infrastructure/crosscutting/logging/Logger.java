package br.com.m4u.sms.infrastructure.crosscutting.logging;

import java.util.logging.Level;

public class Logger {
	public static final void logError(Class<?> classDef, Throwable thrown) {
		java.util.logging.Logger.getLogger(classDef.getName()).log(Level.SEVERE, thrown.getMessage(), thrown);
	}
	
	public static final void logError(Class<?> classDef, String msg) { 
		java.util.logging.Logger.getLogger(classDef.getName()).log(Level.SEVERE, msg);
	}
}
