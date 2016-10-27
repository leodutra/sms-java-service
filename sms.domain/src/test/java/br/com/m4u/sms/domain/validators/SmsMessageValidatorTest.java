package br.com.m4u.sms.domain.validators;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;


@Category(UnitTest.class)
public class SmsMessageValidatorTest {

	private static SmsMessageValidator smsMessageValidator = new SmsMessageValidator();

	@Test
	public void should_returnNoBrokenRule_when_SmsMessageIsValid() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		sms.setId(null);
		sms.setExpirationTime(sms.getRegistrationTime().plusNanos(1));

		assertTrue(smsMessageValidator.validate(sms).isEmpty());

		sms.setId(0L);
		sms.setExpirationTime(null);

		assertTrue(smsMessageValidator.validate(sms).isEmpty());
	}

	@Test
	public void should_returnBrokenRule_when_bodyIsInvalid() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		// blank
		sms.setBody("");
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// null
		sms.setBody(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// too big
		sms.setBody(RandomStringUtils.random(SmsMessage.Constraint.SMS_BODY_MAX_LENGTH + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}

	@Test
	public void should_returnBrokenRule_when_senderIsInvalid() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		// blank
		sms.setSenderInformation("");
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// null
		sms.setSenderInformation(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// too big
		sms.setSenderInformation(RandomStringUtils.random(SmsMessage.Constraint.SENDER_INFO_MAX_LENGTH + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}

	@Test
	public void should_returnBrokenRule_when_addresseeIsInvalid() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		// blank
		sms.setAddresseeInformation("");
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// null
		sms.setAddresseeInformation(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// too big
		sms.setAddresseeInformation(RandomStringUtils.random(SmsMessage.Constraint.RECEIVER_INFO_MAX_LENGTH + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}

	@Test
	public void should_returnBrokenRule_when_registrationTimeIsInvalid() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		// null
		sms.setRegistrationTime(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());

		// impossible to register in the future
		sms.setRegistrationTime(Instant.now().plusSeconds(10)); // FIXME not exactly deterministic, precision is low
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}

	@Test
	public void should_returnBrokenRule_when_smsMessageExpired() throws Exception {

		SmsMessage sms = dummyValidSmsMessage();

		// past 1 nanosecond
		sms.setExpirationTime(sms.getRegistrationTime().minusNanos(1));

		// one broken rule
		assertEquals(1, smsMessageValidator.validateExpirationOnly(sms).size());
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
