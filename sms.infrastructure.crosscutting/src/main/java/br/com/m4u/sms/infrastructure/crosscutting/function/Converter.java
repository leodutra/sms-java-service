package br.com.m4u.sms.infrastructure.crosscutting.function;

@FunctionalInterface
public interface Converter<From, To> {

	To convert(From source);
}
