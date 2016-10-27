package br.com.m4u.sms.api.v1.exceptions.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.m4u.sms.api.v1.resolution.ApiResponseResolver;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.Result.Type;
import br.com.m4u.sms.infrastructure.crosscutting.logging.Logger;


@Provider
public class GenericExceptionHandler implements ExceptionMapper<Throwable> {

	@ApplicationScoped
	private ApiResponseResolver resolver;

	@Override
	public Response toResponse(Throwable ex) {
		Logger.logError(GenericExceptionHandler.class, ex);
		return resolver.send(new Result(Type.INTERNAL_SERVER_ERROR));
	}
}
