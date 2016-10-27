package br.com.m4u.sms.api.v1.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/")
@Api(tags = { "heartbeat" })
public class HeartbeatResource {

	@GET
	@Path("/heartbeat")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns API heartbeat", notes = "Returns HTTP status 200(OK) when the API is online",
			response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SMS API is running"),
			@ApiResponse(code = 500, message = "SMS API has some internal error(s)") })
	public Response verifyRESTService() {
		return Response.ok().build();
	}
}
