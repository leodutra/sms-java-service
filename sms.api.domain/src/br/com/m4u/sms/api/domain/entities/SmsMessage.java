package br.com.m4u.sms.api.domain.entities;

import java.math.BigInteger;
import java.time.Instant;

public class SmsMessage extends Entity<BigInteger> {

	private String senderInformation;
	private String receiverInformation;
	private String body;
	private Instant registrationTime;
	private Instant expirationTime;

	public void setRegistrationTime(Instant registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Instant getRegistrationTime() {
		return registrationTime;
	}

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
	
	public void setExpirationTime(Instant expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Instant getExpirationTime() {
		return expirationTime;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}
	
}
