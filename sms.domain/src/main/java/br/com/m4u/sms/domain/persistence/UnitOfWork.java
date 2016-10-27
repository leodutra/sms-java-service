package br.com.m4u.sms.domain.persistence;

import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;


public interface UnitOfWork extends AutoCloseable {

	SmsMessageRepository smsMessageRepository();

	void beginTransaction() throws IntegrationException;

	void rollback() throws IntegrationException;

	void commit() throws IntegrationException;
}
