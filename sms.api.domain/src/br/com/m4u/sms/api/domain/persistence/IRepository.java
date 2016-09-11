package br.com.m4u.sms.api.domain.persistence;

import br.com.m4u.sms.api.domain.entities.Entity;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public interface IRepository<TEntity extends Entity<?>> {
	
	TEntity getById(Object id) throws IntegrationException;

	TEntity insert(TEntity entity) throws IntegrationException;
}
