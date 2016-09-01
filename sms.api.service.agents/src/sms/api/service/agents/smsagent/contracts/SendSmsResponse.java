package sms.api.service.agents.smsagent.contracts;

public final class SendSmsResponse {

	Boolean sucessful;
	String message;
	
	public SendSmsResponse() {}
	
	public SendSmsResponse(Boolean sucessful, String message) {
		this.sucessful = sucessful;
		this.message = message;
	}

	public Boolean getSucessful() {
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
