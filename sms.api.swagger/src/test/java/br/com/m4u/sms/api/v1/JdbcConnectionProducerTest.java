package br.com.m4u.sms.api.v1;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.com.m4u.sms.api.v1.JdbcConnectionProducer;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.IntegrationTest;


@Category(IntegrationTest.class)
public class JdbcConnectionProducerTest {

	@Test
	public void should_getConnection_when_requested() throws Exception {
		// Given
		JdbcConnectionProducer producer = new JdbcConnectionProducer();

		// When
		Connection conn = producer.create();

		// Then
		assertNotNull(conn);
	}
}
