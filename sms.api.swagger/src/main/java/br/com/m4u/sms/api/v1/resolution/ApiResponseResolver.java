package br.com.m4u.sms.api.v1.resolution;

import static javax.ws.rs.core.Response.created;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import br.com.m4u.sms.domain.model.Entity;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.Result.Type;
import br.com.m4u.sms.infrastructure.crosscutting.function.Converter;


@ApplicationScoped
public class ApiResponseResolver {

	public <TModel extends Entity<?>> Response sendId(Result<TModel> envelop, UriInfo uriContextInfo) {

		if (Type.SUCCESS.equals(envelop.getResultType())) {

			URI uri = uriContextInfo.getAbsolutePathBuilder().path(String.valueOf(envelop.getData().getId())).build();

			return created(uri).entity(new Result<URI>(Type.SUCCESS).withData(uri)).build();
		}
		return send(envelop); // any unsuccessful
	}

	public Response send(Result<?> envelop) {
		return send(envelop, null);
	}

	public <TModel> Response send(Result<TModel> envelop, Converter<TModel, ?> converter) {

		ResponseBuilder response = Response.ok();

		switch (envelop.getResultType()) {

			case SUCCESS:
				if (converter != null && envelop.getData() != null) {
					Object converted = converter.convert(envelop.getData());
					response.entity(new Result<Object>(Type.SUCCESS).withData(converted));
				}
				else
					response.entity(envelop);
				break;

			case RESOURCE_NOT_FOUND:
				response.status(Status.NOT_FOUND).entity(envelop.withData(null));
				break;

			case OPERATION_FAILURE:
				response.status(Status.BAD_GATEWAY).entity(envelop.withData(null));
				break;

			case VALIDATION_ERROR:
				response.status(Status.CONFLICT).entity(envelop.withData(null));
				break;

			case INTERNAL_SERVER_ERROR:
				response.status(Status.INTERNAL_SERVER_ERROR).entity(envelop.withData(null));
				break;

			default:
				throw new WebApplicationException("PLEASE VERIFY: Response type conversion not mapped.");
		}

		return response.build();
	}

}
