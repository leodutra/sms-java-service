package br.com.m4u.sms.domain.entities.constraints;

public final class DomainConstraints {

	public static class SmsMessage {
		public static final int smsBodyMaxLength = 160;
		public static final int senderInfoMaxLength = 32;
		public static final int receiverInfoMaxLength = 32;
	}
}
