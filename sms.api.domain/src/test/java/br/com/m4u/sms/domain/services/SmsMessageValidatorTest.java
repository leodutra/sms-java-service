package br.com.m4u.sms.domain.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.domain.entities.constraints.DomainConstraints;
import br.com.m4u.sms.domain.validators.SmsMessageValidator;
import br.com.m4u.sms.exceptions.IntegrationException;

public class SmsMessageValidatorTest {
	
	private static SmsMessageValidator smsMessageValidator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		smsMessageValidator = new SmsMessageValidator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		smsMessageValidator = null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void allValid() throws IntegrationException {
		
		SmsMessage sms = dummyValidSmsMessage();
		
		sms.setId(null);
		sms.setExpirationTime(sms.getRegistrationTime().plusNanos(1));
		
		assertTrue(smsMessageValidator.validate(sms).isEmpty());
	
		
		sms.setId(0L); 
		sms.setExpirationTime(null);
		
		assertTrue(smsMessageValidator.validate(sms).isEmpty());
	}

	@Test
	public void invalidBody() throws IntegrationException {
		
		SmsMessage sms = dummyValidSmsMessage();
		
		// blank
		sms.setBody("");
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// nullify;
		sms.setBody(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// too big
		sms.setBody(RandomStringUtils.random(DomainConstraints.SmsMessage.smsBodyMaxLength + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}
	
	@Test
	public void invalidSender() throws IntegrationException {
		
		SmsMessage sms = dummyValidSmsMessage();
		
		// blank
		sms.setSenderInformation("");
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// nullify
		sms.setSenderInformation(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// too big
		sms.setSenderInformation(RandomStringUtils.random(DomainConstraints.SmsMessage.senderInfoMaxLength + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}

	@Test
	public void invalidReceiver() throws IntegrationException {
	
		SmsMessage sms = dummyValidSmsMessage();
		
		// blank
		sms.setReceiverInformation("");
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// nullify
		sms.setReceiverInformation(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// too big
		sms.setReceiverInformation(RandomStringUtils.random(DomainConstraints.SmsMessage.receiverInfoMaxLength + 1));
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}
	
	@Test
	public void invalidRegistrationTime() throws IntegrationException {
	
		SmsMessage sms = dummyValidSmsMessage();
		
		// nullify
		sms.setRegistrationTime(null);
		assertEquals(1, smsMessageValidator.validate(sms).size());
		
		// can't register in future
		sms.setRegistrationTime(Instant.now().plusSeconds(180)); // FIXME not exactly deterministic, precision is low
		assertEquals(1, smsMessageValidator.validate(sms).size());
	}
	
	@Test
	public void invalidExpirationTime() throws IntegrationException {
		
		SmsMessage sms = dummyValidSmsMessage();
		
		// past 1 nanosecond
		sms.setExpirationTime(sms.getRegistrationTime().minusNanos(1));
		assertEquals(1, smsMessageValidator.validateExpirationOnly(sms).size());
	}
	
	private static SmsMessage dummyValidSmsMessage() {
		SmsMessage sms = new SmsMessage();
		
		sms.setId(null);
		sms.setBody(RandomStringUtils.random(DomainConstraints.SmsMessage.smsBodyMaxLength));
		sms.setRegistrationTime(Instant.now());
		sms.setExpirationTime(sms.getRegistrationTime().plusNanos(1));
		sms.setSenderInformation(RandomStringUtils.randomNumeric(DomainConstraints.SmsMessage.senderInfoMaxLength));
		sms.setReceiverInformation(RandomStringUtils.randomNumeric(DomainConstraints.SmsMessage.receiverInfoMaxLength));
		
		return sms;
	}

}
