package br.com.m4u.sms.api.domain.validators;

import java.util.List;

import br.com.m4u.sms.api.domain.results.ResultError;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public interface IValidator<TEntity> {
	List<ResultError> validate(TEntity obj) throws IntegrationException;
}
