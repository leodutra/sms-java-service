package br.com.m4u.sms.api.app.service.injection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import br.com.m4u.sms.api.domain.persistence.IUnitOfWork;
import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.logging.Logger;
import br.com.m4u.sms.api.persistence.postgre.UnitOfWork;

@ApplicationScoped
public class UnitOfWorkProducer {

	@Produces
	public IUnitOfWork produceUnitOfWork() throws IntegrationException {
		try (final UnitOfWork unitOfWork = new UnitOfWork()) {
			return unitOfWork;
		} catch (Exception ex) {
			Logger.logError(UnitOfWorkProducer.class, ex);
			throw new IntegrationException(ex);
		}
	}
}