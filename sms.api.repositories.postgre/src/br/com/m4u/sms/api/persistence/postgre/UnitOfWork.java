package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;
import java.sql.Savepoint;

import org.postgresql.ds.PGPoolingDataSource;

import br.com.m4u.sms.api.domain.persistence.ISmsMessageRepository;
import br.com.m4u.sms.api.domain.persistence.IUnitOfWork;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public class UnitOfWork implements IUnitOfWork {
	
	private Connection databaseConnection;
	private Savepoint savepoint;
	private boolean commited;
	private boolean lastAutoCommitState;

	public UnitOfWork() throws IntegrationException {
		
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setDataSourceName("PostgreSQL DataSource");
		source.setServerName("localhost");
		source.setDatabaseName("sms");
		source.setUser("postgres");
		source.setPassword("root");
		source.setMaxConnections(10);
			
		try (final Connection databasePooledConnection = source.getConnection()){	
			databaseConnection = databasePooledConnection;
			lastAutoCommitState = databaseConnection.getAutoCommit();
			databaseConnection.setAutoCommit(false);
			savepoint = databaseConnection.setSavepoint();
		}
		catch(Exception ex) {
			throw new IntegrationException(ex);
		}
	}
	
	@Override
	public ISmsMessageRepository smsMessageRepository() {
		return new SmsMessageRepository(databaseConnection);
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
