package br.com.m4u.sms.infrastructure.persistence.postgre;

import java.sql.Connection;
import java.sql.Savepoint;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;
import br.com.m4u.sms.infrastructure.data.persistence.postgre.JdbcUnitOfWork;


@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class JdbcUnitOfWorkTest {

	@Mock
	private Connection connection;

	@Mock
	private Savepoint savepoint;

	@Test
	public void should_setAutoCommitFalse_when_beginingTransaction() throws Exception {

		// Given
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		// When
		unitOfWork.beginTransaction();

		// Then
		verify(connection, times(1)).setAutoCommit(false);
		unitOfWork.close();
	}

	@Test
	public void should_setSavepoint_when_beginingTransaction() throws Exception {

		// Given
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		// When
		unitOfWork.beginTransaction();

		// Then
		verify(connection, times(1)).setSavepoint();
		unitOfWork.close();
	}

	@Test
	public void should_rollbackTransaction_when_rollbackIsIssued() throws Exception {

		// Given
		given(connection.setSavepoint()).willReturn(savepoint);
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		// When
		unitOfWork.beginTransaction();
		unitOfWork.rollback();

		// Then
		verify(connection, times(1)).rollback(savepoint);
		unitOfWork.close();
	}

	@Test
	public void should_commitTransaction_when_commitIsIssued() throws Exception {

		// Given
		given(connection.setSavepoint()).willReturn(savepoint);
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		// When
		unitOfWork.beginTransaction();
		unitOfWork.commit();

		// Then
		verify(connection, times(1)).commit();
		unitOfWork.close();
	}

	@Test
	public void should_setAutoCommitPreset_when_transactionCommited() throws Exception {

		// Given
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		given(connection.getAutoCommit()).willReturn(true);
		given(connection.setSavepoint()).willReturn(savepoint);

		// When
		unitOfWork.beginTransaction();
		unitOfWork.commit();

		// Then
		verify(connection, times(1)).setAutoCommit(false);
		verify(connection, times(1)).setAutoCommit(true);
		unitOfWork.close();
	}

	@Test
	public void should_setAutoCommitPreset_when_transactionRolledback() throws Exception {

		// Given
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		given(connection.getAutoCommit()).willReturn(true);
		given(connection.setSavepoint()).willReturn(savepoint);

		// When
		unitOfWork.beginTransaction();
		unitOfWork.rollback();

		// Then
		verify(connection, times(1)).setAutoCommit(false);
		verify(connection, times(1)).setAutoCommit(true);
		unitOfWork.close();
	}

	@Test
	public void should_returnRepositories_when_requested() throws Exception {

		// Given
		UnitOfWork unitOfWork = new JdbcUnitOfWork(connection);

		// When
		SmsMessageRepository smsMessageRepository = unitOfWork.smsMessageRepository();

		// Then
		assertTrue(smsMessageRepository instanceof SmsMessageRepository);
		unitOfWork.close();
	}
}
