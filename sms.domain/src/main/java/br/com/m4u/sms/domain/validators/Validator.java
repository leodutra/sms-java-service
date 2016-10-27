package br.com.m4u.sms.domain.validators;

import java.util.List;


@FunctionalInterface
public interface Validator<TModel> {

	List<String> validate(TModel model);
}
