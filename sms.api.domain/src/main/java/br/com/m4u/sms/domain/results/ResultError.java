package br.com.m4u.sms.domain.results;

public class ResultError {

	private int messageCode;
	private String message;

	public ResultError() { }

	public ResultError(String message) {
		this.message = message;
	}
	
	public ResultError(int code, String message) {
		this.messageCode = code;
		this.message = message;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public String getMessage() {
		return message;
	}
}
