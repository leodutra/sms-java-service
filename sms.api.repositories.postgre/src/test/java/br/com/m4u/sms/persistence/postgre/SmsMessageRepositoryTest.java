package br.com.m4u.sms.persistence.postgre;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.exceptions.IntegrationException;
import br.com.m4u.sms.persistence.postgre.SmsMessageRepositoryImpl;

public class SmsMessageRepositoryTest {

	private SmsMessage completeSms;
	private SmsMessage noExpirationSms;
	private SmsMessageRepository repository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// In memory database
		RunScript.execute("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "", "classpath:schema.sql",
				StandardCharsets.UTF_8, false);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		// In memory database
		JdbcConnectionPool connPool = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
		repository = new SmsMessageRepositoryImpl(connPool.getConnection());
		prepareDataSet();
	}

	@Test
	public void writeReadRepositoryData() throws IntegrationException {
		insertStep();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void insertStep() throws IntegrationException {
		SmsMessage retrievedNoExpirationSms = repository.insert(noExpirationSms);
		SmsMessage retrievedCompleteSms = repository.insert(completeSms);

		assertNotEquals(0, retrievedNoExpirationSms.getId().longValue());
		assertNonIdFields(retrievedNoExpirationSms, retrievedCompleteSms);

		assertNotEquals(0, retrievedCompleteSms.getId().longValue());
		assertNonIdFields(retrievedNoExpirationSms, retrievedCompleteSms);
	}

	public void getByIdStep() throws IntegrationException {
		SmsMessage retrievedNoExpirationSms = repository.getById(noExpirationSms.getId());
		SmsMessage retrievedCompleteSms = repository.getById(completeSms.getId());

		assertNotEquals(0, retrievedNoExpirationSms.getId().longValue());
		assertNonIdFields(retrievedNoExpirationSms, retrievedCompleteSms);

		assertNotEquals(0, retrievedCompleteSms.getId().longValue());
		assertNonIdFields(retrievedNoExpirationSms, retrievedCompleteSms);
	}

	public void assertNonIdFields(SmsMessage retrievedNoExpirationSms, SmsMessage retrievedCompleteSms) {
		assertEquals(noExpirationSms.getBody(), retrievedNoExpirationSms.getBody());
		assertEquals(noExpirationSms.getExpirationTime(), retrievedNoExpirationSms.getExpirationTime());
		assertEquals(noExpirationSms.getRegistrationTime(), retrievedNoExpirationSms.getRegistrationTime());
		assertEquals(noExpirationSms.getSenderInformation(), retrievedNoExpirationSms.getSenderInformation());
		assertEquals(noExpirationSms.getReceiverInformation(), retrievedNoExpirationSms.getReceiverInformation());

		assertEquals(completeSms.getBody(), retrievedCompleteSms.getBody());
		assertEquals(completeSms.getExpirationTime(), retrievedCompleteSms.getExpirationTime());
		assertEquals(completeSms.getRegistrationTime(), retrievedCompleteSms.getRegistrationTime());
		assertEquals(completeSms.getSenderInformation(), retrievedCompleteSms.getSenderInformation());
		assertEquals(completeSms.getReceiverInformation(), retrievedCompleteSms.getReceiverInformation());
	}

	private void prepareDataSet() {
		completeSms = new SmsMessage();
		completeSms.setBody("Hello World");
		completeSms.setExpirationTime(Instant.now().plusSeconds(36000));
		completeSms.setRegistrationTime(Instant.now());
		completeSms.setSenderInformation("2198996776");
		completeSms.setReceiverInformation("2197881111");

		noExpirationSms = new SmsMessage();
		noExpirationSms.setBody("Hello Null Expiration");
		noExpirationSms.setExpirationTime(null);
		noExpirationSms.setRegistrationTime(Instant.now());
		noExpirationSms.setSenderInformation("2198996776");
		noExpirationSms.setReceiverInformation("2197881111");
	}
}
