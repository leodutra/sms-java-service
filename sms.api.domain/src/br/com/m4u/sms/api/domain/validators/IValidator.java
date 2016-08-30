package br.com.m4u.sms.api.domain.validators;

import br.com.m4u.sms.api.domain.valueobjects.OperationResult;

public interface IValidator<TEntity> {
	
	OperationResult<TEntity> validateCreate(TEntity entity);
	OperationResult<TEntity> validateUpdate(TEntity entity);
	OperationResult<TEntity> validateGetById(Object id);
	OperationResult<TEntity> validateDelete(TEntity entity);
}
