package br.com.m4u.sms.service.agents.smsagent.contracts;

public final class SendSmsResponse {

	boolean sucessful;
	String message;
	
	public SendSmsResponse() {}
	
	public SendSmsResponse(boolean sucessful, String message) {
		this.sucessful = sucessful;
		this.message = message;
	}

	public boolean isSucessful() {
		return sucessful;
	}

	public void setSucessful(Boolean sucessful) {
		this.sucessful = sucessful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
