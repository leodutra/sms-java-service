package br.com.m4u.sms.domain.validators;

import java.util.List;

import br.com.m4u.sms.domain.results.ResultError;
import br.com.m4u.sms.exceptions.IntegrationException;

public interface Validator<TEntity> {
	List<ResultError> validate(TEntity obj) throws IntegrationException;
}
