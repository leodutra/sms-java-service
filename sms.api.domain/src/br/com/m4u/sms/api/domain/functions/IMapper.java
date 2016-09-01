package br.com.m4u.sms.api.domain.functions;

public interface IMapper<TInput, TOutput> {
	
	TOutput map(TInput mappable);
}
