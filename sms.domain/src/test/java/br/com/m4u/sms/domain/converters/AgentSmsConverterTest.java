package br.com.m4u.sms.domain.converters;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

import br.com.m4u.sms.domain.model.SendSmsRequest;
import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;


@Category(UnitTest.class)
public class AgentSmsConverterTest {

	@Test
	public void should_convertToAgentRequest_when_smsIsInput() throws Exception {
		// Given
		SmsAgentConverter converter = new SmsAgentConverter();
		SmsMessage sms = dummyValidSmsMessage();

		// When
		SendSmsRequest request = converter.convert(sms);

		// Then
		assertEquals(sms.getSenderInformation(), request.getFrom());
		assertEquals(sms.getAddresseeInformation(), request.getTo());
		assertEquals(sms.getBody(), request.getBody());
	}

	@Test
	public void should_convertToAgentRequest_when_inputIsNull() throws Exception {
		// Given
		SmsAgentConverter converter = new SmsAgentConverter();

		// When
		SendSmsRequest request = converter.convert(null);

		// Then
		assertEquals(null, request);
	}

	private static SmsMessage dummyValidSmsMessage() {
		SmsMessage sms = new SmsMessage();

		sms.setId(null);
		sms.setBody(RandomStringUtils.random(SmsMessage.Constraint.SMS_BODY_MAX_LENGTH));
		sms.setRegistrationTime(Instant.now());
		sms.setExpirationTime(sms.getRegistrationTime().plusNanos(1));
		sms.setSenderInformation(RandomStringUtils.randomNumeric(SmsMessage.Constraint.SENDER_INFO_MAX_LENGTH));
		sms.setAddresseeInformation(RandomStringUtils.randomNumeric(SmsMessage.Constraint.RECEIVER_INFO_MAX_LENGTH));

		return sms;
	}
}
