package br.com.m4u.sms.infrastructure.data.persistence.postgre;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;


public class JdbcUnitOfWork implements UnitOfWork {

	private Connection databaseConnection;
	private Savepoint savepoint;
	private boolean presetAutoCommitState;

	public JdbcUnitOfWork(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	@Override
	public void close() throws IntegrationException {
		if (savepoint != null) rollback();
	}

	@Override
	public void beginTransaction() throws IntegrationException {

		if (savepoint != null) return;

		try {
			presetAutoCommitState = databaseConnection.getAutoCommit();
			databaseConnection.setAutoCommit(false);
			savepoint = databaseConnection.setSavepoint();
		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}
	}

	@Override
	public void commit() throws IntegrationException {
		if (savepoint == null) return;

		try {
			databaseConnection.commit();
			savepoint = null;
			databaseConnection.setAutoCommit(presetAutoCommitState);
		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}
	}

	@Override
	public void rollback() throws IntegrationException {

		if (savepoint == null) return;

		try {
			databaseConnection.rollback(savepoint);
			savepoint = null;
			databaseConnection.setAutoCommit(presetAutoCommitState);
		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}
	}

	@Override
	public SmsMessageRepository smsMessageRepository() {
		return new PostgreSmsMessageRepository(databaseConnection);
	}

}
