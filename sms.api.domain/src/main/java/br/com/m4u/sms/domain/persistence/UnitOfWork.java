package br.com.m4u.sms.domain.persistence;

import br.com.m4u.sms.exceptions.IntegrationException;

public interface UnitOfWork extends AutoCloseable {

	SmsMessageRepository smsMessageRepository();
	
	void commit() throws IntegrationException;
}
