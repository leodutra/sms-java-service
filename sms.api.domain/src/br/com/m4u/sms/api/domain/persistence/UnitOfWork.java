package br.com.m4u.sms.api.domain.persistence;

import br.com.m4u.sms.api.exceptions.IntegrationException;

public interface UnitOfWork extends AutoCloseable {

	SmsMessageRepository smsMessageRepository();
	
	void commit() throws IntegrationException;
}
