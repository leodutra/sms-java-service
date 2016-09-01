package br.com.m4u.sms.api.domain.functions;

import br.com.m4u.sms.api.domain.valueobjects.OperationResult;

public interface IOperationValidator<TValidatable> {
	OperationResult<TValidatable> Validate(TValidatable validationTarget);
}
