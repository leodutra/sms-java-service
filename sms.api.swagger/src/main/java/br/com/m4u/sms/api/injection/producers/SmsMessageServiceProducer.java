package br.com.m4u.sms.api.injection.producers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import br.com.m4u.sms.application.services.SmsMessageService;
import br.com.m4u.sms.application.services.SmsMessageServiceImpl;
import br.com.m4u.sms.domain.agents.SmsAgent;
import br.com.m4u.sms.domain.persistence.UnitOfWork;


@Singleton
public class SmsMessageServiceProducer {

	@Produces
	@RequestScoped
	public SmsMessageService create(UnitOfWork unitOfWork, SmsAgent smsAgent) {
		return new SmsMessageServiceImpl(unitOfWork, smsAgent);
	}
}
