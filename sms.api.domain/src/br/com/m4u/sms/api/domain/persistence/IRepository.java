package br.com.m4u.sms.api.domain.persistence;

import java.util.List;

import br.com.m4u.sms.api.domain.entities.Entity;
import br.com.m4u.sms.api.domain.valueobjects.PaginationSpecification;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public interface IRepository<TEntity extends Entity<?>> {

	TEntity getById(Object id) throws IntegrationException;

	TEntity insert(TEntity entity) throws IntegrationException;

	int update(TEntity entity) throws IntegrationException;

	int delete(Object id) throws IntegrationException;

	int delete(TEntity entity) throws IntegrationException;

	List<TEntity> listAll(PaginationSpecification pagination) throws IntegrationException;
}
