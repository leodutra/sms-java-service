package br.com.m4u.sms.api.v1.resources;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.m4u.sms.api.v1.contracts.Sms;
import br.com.m4u.sms.api.v1.resolution.ApiResponseResolver;
import br.com.m4u.sms.application.services.SmsMessageService;
import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.infrastructure.crosscutting.function.Converter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/sms")
@Api(tags = { "sms" })
public class SmsResource {

	@Inject
	private SmsMessageService smsMessageService; // Weld injecting Connection,
													// UnitOfWork and SmsAgent
	@Inject
	private ApiResponseResolver responseResolver;

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Stores a SMS and sends to the carrier SMS endpoint.",
			notes = "Returns HTTP status \"BAD_GATEWAY\" when a mobile user could not be found by the endpoint",
			response = Long.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
	public Response send(Sms sendSmsRequest) throws Exception {

		Converter<Sms, SmsMessage> smsToDomain = (Sms smsToSend) -> {

			if (smsToSend == null) return null;

			SmsMessage smsMessage = new SmsMessage();
			smsMessage.setAddresseeInformation(smsToSend.addresseeInformation);
			smsMessage.setSenderInformation(smsToSend.senderInformation);
			smsMessage.setExpirationTime(
					smsToSend.expirationTime != null ? Instant.ofEpochMilli(smsToSend.expirationTime.getTime()) : null);
			smsMessage.setBody(smsToSend.body);
			return smsMessage;
		};

		return responseResolver.sendId(smsMessageService.sendAndStoreSmsMessage(smsToDomain.convert(sendSmsRequest)),
				uriInfo);
	}

	@GET
	@Path("/{smsId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "SMS not found") })
	public Response findStoredSmsById(@PathParam("smsId") Long smsId) throws Exception {

		return responseResolver.send(smsMessageService.findStoredSmsById(smsId), TO_SMS);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllStoredSms() throws Exception {

		return responseResolver.send(smsMessageService.listAllStoredSms(), (List<SmsMessage> smsList) -> {

			return smsList.parallelStream().map(domainSms -> TO_SMS.convert(domainSms)).collect(Collectors.toList());
		});
	}

	private static final Converter<SmsMessage, Sms> TO_SMS = domainSms -> {

		Sms contract = new Sms();

		contract.id = domainSms.getId();
		contract.body = domainSms.getBody();
		contract.expirationTime = domainSms.getExpirationTime() != null ? Date.from(domainSms.getExpirationTime())
				: null;
		contract.registrationTime = Date.from(domainSms.getRegistrationTime());
		contract.addresseeInformation = domainSms.getAddresseeInformation();
		contract.senderInformation = domainSms.getSenderInformation();

		return contract;
	};
}
