package br.com.m4u.sms.api.exceptions;

public class IntegrationException extends Exception {

	private static final long serialVersionUID = -4046563672884362880L;

	public IntegrationException(String message) {
		super(message);
	}
	
	public IntegrationException(Throwable cause) {
		super(cause);
	}
	
	public IntegrationException(String message, Throwable cause) {
		super(message, cause);
	}
}
