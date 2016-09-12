package br.com.m4u.sms.domain.persistence;

import br.com.m4u.sms.domain.entities.Entity;
import br.com.m4u.sms.exceptions.IntegrationException;

public interface Repository<TEntity extends Entity<?>> {
	
	TEntity getById(Object id) throws IntegrationException;

	TEntity insert(TEntity entity) throws IntegrationException;
}
