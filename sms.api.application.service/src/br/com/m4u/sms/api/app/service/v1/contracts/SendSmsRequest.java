package br.com.m4u.sms.api.app.service.v1.contracts;

import java.time.Instant;

public class SendSmsRequest {
	
	private String senderInformation;
	private String receiverInformation;
	private String smsBody;
    private Instant smsExpirationTime;

	public void setSenderInformation(String senderInformation) {
		this.senderInformation = senderInformation;
	}

	public String getSenderInformation() {
		return senderInformation;
	}

	public void setReceiverInformation(String receiverInformation) {
		this.receiverInformation = receiverInformation;
	}

	public String getReceiverInformation() {
		return receiverInformation;
	}
	
	public void setSmsExpirationTime(Instant smsExpirationTime) {
		this.smsExpirationTime = smsExpirationTime;
	}

	public Instant getSmsExpirationTime() {
		return smsExpirationTime;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public String getSmsBody() {
		return smsBody;
	}
}
