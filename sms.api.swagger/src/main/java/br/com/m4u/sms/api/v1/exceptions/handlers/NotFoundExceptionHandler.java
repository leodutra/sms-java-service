package br.com.m4u.sms.api.v1.exceptions.handlers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.m4u.sms.api.v1.resolution.ApiResponseResolver;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.Result.Type;
import br.com.m4u.sms.infrastructure.crosscutting.logging.Logger;


@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

	private static final ApiResponseResolver responseResolver = new ApiResponseResolver();

	@Override
	public Response toResponse(NotFoundException ex) {
		Logger.logError(NotFoundExceptionHandler.class, ex);
		return Response.status(Status.NOT_FOUND).entity(new Result(Type.RESOURCE_NOT_FOUND)).build();
	}
}
