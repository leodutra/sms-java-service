package br.com.m4u.sms.domain.model;

import java.time.Instant;


public class SmsMessage extends Entity<Long> {

	private String senderInformation;
	private String addresseeInformation;
	private String body;
	private Instant registrationTime;
	private Instant expirationTime;

	public static class Constraint {

		public static final int SMS_BODY_MAX_LENGTH = 160;
		public static final int SENDER_INFO_MAX_LENGTH = 32;
		public static final int RECEIVER_INFO_MAX_LENGTH = 32;
	}

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

	public void setAddresseeInformation(String addresseeInformation) {
		this.addresseeInformation = addresseeInformation;
	}

	public String getAddresseeInformation() {
		return addresseeInformation;
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
