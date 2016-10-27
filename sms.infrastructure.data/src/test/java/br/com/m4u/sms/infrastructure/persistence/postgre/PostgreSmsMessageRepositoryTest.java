package br.com.m4u.sms.infrastructure.persistence.postgre;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.time.Instant;
import java.util.List;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;
import br.com.m4u.sms.infrastructure.data.persistence.postgre.PostgreSmsMessageRepository;


@Category(UnitTest.class)
public class PostgreSmsMessageRepositoryTest {

	private static final String H2_SCHEMA_CLASS_PATH = "classpath:tdd-schema.sql";
	private static final String H2_CONNECTION_DEFINITIONS = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

	private static JdbcConnectionPool connPool;
	private SmsMessage completeSms;
	private SmsMessage smsWithNullExpiration;
	private SmsMessageRepository repository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// In memory database
		RunScript.execute(H2_CONNECTION_DEFINITIONS, "sa", "", H2_SCHEMA_CLASS_PATH, StandardCharsets.UTF_8, false);
		connPool = JdbcConnectionPool.create(H2_CONNECTION_DEFINITIONS, "sa", "");
	}

	@Before
	public void setUp() throws Exception {

		// In memory database
		Connection conn = connPool.getConnection();
		conn.setAutoCommit(false); // transactional == rollback when not commited

		repository = new PostgreSmsMessageRepository(conn);
		dummyMessages();
	}

	@After
	public void tearDown() throws Exception {
		completeSms = null;
		smsWithNullExpiration = null;
		repository = null;
	}

	private void dummyMessages() {

		completeSms = new SmsMessage();
		completeSms.setBody("Hello World");
		completeSms.setExpirationTime(Instant.now().plusSeconds(36000));
		completeSms.setRegistrationTime(Instant.now());
		completeSms.setSenderInformation("2198996776");
		completeSms.setAddresseeInformation("2197881111");

		smsWithNullExpiration = new SmsMessage();
		smsWithNullExpiration.setBody("Hello Null Expiration");
		smsWithNullExpiration.setExpirationTime(null);
		smsWithNullExpiration.setRegistrationTime(Instant.now());
		smsWithNullExpiration.setSenderInformation("2198996776");
		smsWithNullExpiration.setAddresseeInformation("2197881111");
	}

	@Test
	public void should_returnIdentifiedSms_when_smsIsInserted() throws Exception {
		SmsMessage retrievedNullExpirationSms = repository.insert(smsWithNullExpiration);
		SmsMessage retrievedCompleteSms = repository.insert(completeSms);

		assertNotEquals(0, retrievedNullExpirationSms.getId().longValue());
		assertNotEquals(0, retrievedCompleteSms.getId().longValue());

		assertEquals(smsWithNullExpiration.getBody(), retrievedNullExpirationSms.getBody());
		assertEquals(smsWithNullExpiration.getExpirationTime(), retrievedNullExpirationSms.getExpirationTime());
		assertEquals(smsWithNullExpiration.getRegistrationTime(), retrievedNullExpirationSms.getRegistrationTime());
		assertEquals(smsWithNullExpiration.getSenderInformation(), retrievedNullExpirationSms.getSenderInformation());
		assertEquals(smsWithNullExpiration.getAddresseeInformation(),
				retrievedNullExpirationSms.getAddresseeInformation());

		assertEquals(completeSms.getBody(), retrievedCompleteSms.getBody());
		assertEquals(completeSms.getExpirationTime(), retrievedCompleteSms.getExpirationTime());
		assertEquals(completeSms.getRegistrationTime(), retrievedCompleteSms.getRegistrationTime());
		assertEquals(completeSms.getSenderInformation(), retrievedCompleteSms.getSenderInformation());
		assertEquals(completeSms.getAddresseeInformation(), retrievedCompleteSms.getAddresseeInformation());
	}

	@Test
	public void should_getSms_when_smsWasInserted() throws Exception {

		SmsMessage retrievedNullExpirationSms = repository.insert(smsWithNullExpiration);
		SmsMessage retrievedCompleteSms = repository.insert(completeSms);

		retrievedNullExpirationSms = repository.findById(retrievedNullExpirationSms.getId());
		retrievedCompleteSms = repository.findById(retrievedCompleteSms.getId());

		assertEquals(smsWithNullExpiration.getId().longValue(), retrievedNullExpirationSms.getId().longValue());
		assertEquals(completeSms.getId().longValue(), retrievedCompleteSms.getId().longValue());

		assertEquals(smsWithNullExpiration.getBody(), retrievedNullExpirationSms.getBody());
		assertEquals(smsWithNullExpiration.getExpirationTime(), retrievedNullExpirationSms.getExpirationTime());
		assertEquals(smsWithNullExpiration.getRegistrationTime().toEpochMilli(),
				retrievedNullExpirationSms.getRegistrationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(smsWithNullExpiration.getSenderInformation(), retrievedNullExpirationSms.getSenderInformation());
		assertEquals(smsWithNullExpiration.getAddresseeInformation(),
				retrievedNullExpirationSms.getAddresseeInformation());

		assertEquals(completeSms.getBody(), retrievedCompleteSms.getBody());
		assertEquals(completeSms.getExpirationTime().toEpochMilli(),
				retrievedCompleteSms.getExpirationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(completeSms.getRegistrationTime().toEpochMilli(),
				retrievedCompleteSms.getRegistrationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(completeSms.getSenderInformation(), retrievedCompleteSms.getSenderInformation());
		assertEquals(completeSms.getAddresseeInformation(), retrievedCompleteSms.getAddresseeInformation());
	}

	@Test
	public void should_listStoredSms_when_smsListWereInserted() throws Exception {

		smsWithNullExpiration = repository.insert(smsWithNullExpiration);
		completeSms = repository.insert(completeSms);

		List<SmsMessage> storedSms = repository.listAll();

		assertEquals(2, storedSms.size());

		SmsMessage first = storedSms.get(0);
		SmsMessage second = storedSms.get(1);

		assertEquals(smsWithNullExpiration.getBody(), first.getBody());
		assertEquals(smsWithNullExpiration.getExpirationTime(), first.getExpirationTime());
		assertEquals(smsWithNullExpiration.getRegistrationTime().toEpochMilli(),
				first.getRegistrationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(smsWithNullExpiration.getSenderInformation(), first.getSenderInformation());
		assertEquals(smsWithNullExpiration.getAddresseeInformation(), first.getAddresseeInformation());

		assertEquals(completeSms.getBody(), second.getBody());
		assertEquals(completeSms.getExpirationTime().toEpochMilli(), second.getExpirationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(completeSms.getRegistrationTime().toEpochMilli(), second.getRegistrationTime().toEpochMilli()); // H2 / JDBC messes with millis precision for Instant - Timestamp
		assertEquals(completeSms.getSenderInformation(), second.getSenderInformation());
		assertEquals(completeSms.getAddresseeInformation(), second.getAddresseeInformation());
	}
}
