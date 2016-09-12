package br.com.m4u.sms.domain.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.service.agents.smsagent.SmsServiceAgent;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsResponse;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmsMessageServiceTest {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
