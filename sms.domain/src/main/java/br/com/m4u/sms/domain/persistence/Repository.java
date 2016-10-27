package br.com.m4u.sms.domain.persistence;

import java.util.List;

import br.com.m4u.sms.domain.model.Entity;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;


public interface Repository<TEntity extends Entity<?>> {

	TEntity findById(Object id) throws IntegrationException;

	List<TEntity> listAll() throws IntegrationException;

	TEntity insert(TEntity entity) throws IntegrationException;
}
