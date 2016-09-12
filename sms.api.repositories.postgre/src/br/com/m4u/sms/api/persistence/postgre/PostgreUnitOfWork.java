package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Savepoint;
import java.util.Properties;

import org.postgresql.ds.PGPoolingDataSource;

import br.com.m4u.sms.api.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.api.domain.persistence.UnitOfWork;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public class PostgreUnitOfWork implements UnitOfWork {
	
	private Connection databaseConnection;
	private Savepoint savepoint;
	private boolean commited;
	private boolean lastAutoCommitState;
	private static PGPoolingDataSource source;

	public PostgreUnitOfWork() throws IntegrationException {
		
//		if (source == null) {
//			source = new PGPoolingDataSource();
//			source.setDataSourceName("PostgreSQL DataSource");
//			source.setServerName("localhost");
//			source.setDatabaseName("sms");
//			source.setUser("postgres");
//			source.setPassword("root");
//			source.setMaxConnections(10);
//		}
		
		String url = "jdbc:postgresql:sms?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","root");
		//Connection conn = DriverManager.getConnection(url, props);
			
//		try (final Connection databasePooledConnection = source.getConnection()){
		try (final Connection databasePooledConnection = DriverManager.getConnection(url, props)) {
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
