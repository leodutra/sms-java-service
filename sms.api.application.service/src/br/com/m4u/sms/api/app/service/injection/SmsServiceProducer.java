package br.com.m4u.sms.api.app.service.injection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.m4u.sms.api.domain.persistence.IUnitOfWork;
import br.com.m4u.sms.api.domain.services.ISmsMessageService;
import br.com.m4u.sms.api.domain.services.SmsMessageService;
import br.com.m4u.sms.api.exceptions.IntegrationException;

@ApplicationScoped
public class SmsServiceProducer {
	
	@Produces
	public ISmsMessageService produceSmsMessageService(IUnitOfWork unitOfWork) throws IntegrationException {
		return new SmsMessageService(unitOfWork);
	}
}