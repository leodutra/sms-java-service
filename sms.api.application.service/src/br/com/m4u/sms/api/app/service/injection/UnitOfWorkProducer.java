package br.com.m4u.sms.api.app.service.injection;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import br.com.m4u.sms.api.domain.persistence.UnitOfWork;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public class UnitOfWorkProducer {

	@Produces
	@PostgreUnitOfWork
	@RequestScoped
	public UnitOfWork produceUnitOfWork() throws IntegrationException {
		return new br.com.m4u.sms.api.persistence.postgre.PostgreUnitOfWork();
	}
}