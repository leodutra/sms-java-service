package br.com.m4u.sms.persistence.postgre;

import java.sql.Connection;
import java.sql.Savepoint;

import org.postgresql.ds.PGPoolingDataSource;

import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.exceptions.IntegrationException;

public class PostgreUnitOfWork implements UnitOfWork {
	
	private Connection databaseConnection;
	private Savepoint savepoint;
	private boolean commited;
	private boolean lastAutoCommitState;
	private static PGPoolingDataSource source;

	public PostgreUnitOfWork() throws IntegrationException {
		
		if (source == null) {
			source = new PGPoolingDataSource();
			source.setDataSourceName("PostgreSQL DataSource");
			source.setServerName("localhost");
			source.setDatabaseName("postgres");
			source.setUser("postgres");
			source.setPassword("root");
			source.setMaxConnections(10);
		}
		
		try {
			databaseConnection = source.getConnection();
			lastAutoCommitState = databaseConnection.getAutoCommit();
			databaseConnection.setAutoCommit(false);
			savepoint = databaseConnection.setSavepoint();
		}
		catch(Exception ex) {
			throw new IntegrationException(ex);
		}
	}
	
	@Override
	public SmsMessageRepository smsMessageRepository() {
		return new SmsMessageRepositoryImpl(databaseConnection);
	}
	
	@Override
	public void close() throws Exception {
		if (!commited) databaseConnection.rollback(savepoint);
	}

	@Override
	public void commit() throws IntegrationException{
		try {
			databaseConnection.commit();
			databaseConnection.releaseSavepoint(savepoint); // redundant against bad drivers
			databaseConnection.setAutoCommit(lastAutoCommitState); // restore connection auto commit
			commited = true;
		}
		catch(Exception ex) {
			throw new IntegrationException(ex);
		}
	}

}
