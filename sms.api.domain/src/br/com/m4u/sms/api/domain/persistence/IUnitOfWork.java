package br.com.m4u.sms.api.domain.persistence;

import br.com.m4u.sms.api.exceptions.IntegrationException;

public interface IUnitOfWork extends AutoCloseable {

	ISmsMessageRepository smsMessageRepository();
	
	void commit() throws IntegrationException;
}
