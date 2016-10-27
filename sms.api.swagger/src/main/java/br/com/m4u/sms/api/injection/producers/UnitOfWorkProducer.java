package br.com.m4u.sms.api.injection.producers;

import java.sql.Connection;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.infrastructure.data.persistence.postgre.JdbcUnitOfWork;


@Singleton
public class UnitOfWorkProducer {

	@Produces
	@RequestScoped
	public UnitOfWork create(Connection conn) {
		return new JdbcUnitOfWork(conn);
	}
}
