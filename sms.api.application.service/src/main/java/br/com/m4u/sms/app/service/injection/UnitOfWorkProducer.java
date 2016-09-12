package br.com.m4u.sms.app.service.injection;

import javax.enterprise.inject.Produces;

import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.exceptions.IntegrationException;
import br.com.m4u.sms.persistence.postgre.PostgreUnitOfWork;

public class UnitOfWorkProducer {

	@Produces
	@Preferred
	public UnitOfWork produceUnitOfWork(PostgreUnitOfWork postgreUnitOfWork) throws IntegrationException {
		return postgreUnitOfWork;
	}
}