package br.com.m4u.sms.api.domain.results;

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

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
