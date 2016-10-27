package br.com.m4u.sms.api.v1;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.CreationException;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.postgresql.ds.PGPoolingDataSource;

import br.com.m4u.sms.infrastructure.crosscutting.logging.Logger;


@Singleton
public class JdbcConnectionProducer {

	private PGPoolingDataSource singletonDatasource;

	public JdbcConnectionProducer() {
		singletonDatasource = new PGPoolingDataSource();
		singletonDatasource.setDataSourceName("PostgreSQL DataSource");
		singletonDatasource.setServerName("localhost");
		singletonDatasource.setDatabaseName("postgres");
		singletonDatasource.setUser("postgres");
		singletonDatasource.setPassword("root");
		singletonDatasource.setMaxConnections(20);
	}

	@Produces
	@RequestScoped
	public Connection create() throws SQLException {
		try {
			return singletonDatasource.getConnection();
		}
		catch (Exception ex) {
			// https://docs.jboss.org/cdi/spec/1.2/cdi-spec.html#contextual
			Logger.logError(JdbcConnectionProducer.class, ex);
			throw new CreationException(ex);
		}
	}
}
