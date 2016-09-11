package br.com.m4u.sms.api.app.service.v1.endpoints;

import static javax.ws.rs.core.Response.created;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.m4u.sms.api.app.service.v1.contracts.SendSmsRequest;
import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.results.Result;
import br.com.m4u.sms.api.domain.results.ResultTypeEnum;
import br.com.m4u.sms.api.domain.services.ISmsMessageService;
import br.com.m4u.sms.api.domain.services.SmsMessageService;
import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.logging.Logger;
import br.com.m4u.sms.api.persistence.postgre.UnitOfWork;

@Path("/v1/sms")
@ApplicationScoped
public class SmsEndpoint {

	
//	@Inject
	private ISmsMessageService smsMessageService;
	
	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response send(SendSmsRequest  sendSmsRequest) {
//		StringBuilder crunchifyBuilder = new StringBuilder();
//		try {
//			BufferedReader in = new BufferedReader(new InputStreamReader(sendSmsRequest));
//			String line = null;
//			while ((line = in.readLine()) != null) {
//				crunchifyBuilder.append(line);
//			}
//		} catch (Exception e) {
//			System.out.println("Error Parsing: - ");
//		}
//		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		// return HTTP response 200 in case of success
	//	return Response.status(200).entity(crunchifyBuilder.toString()).build();
		try {
			smsMessageService = new SmsMessageService(new UnitOfWork());
		} catch (IntegrationException ex) {
			Logger.logError(SmsEndpoint.class, ex);
			Result<SendSmsRequest> result = new Result<SendSmsRequest>(ResultTypeEnum.INTERNAL_SERVER_ERROR, sendSmsRequest);
			return Response.status(500).entity(result).build();
		}
		
		Result<SmsMessage> resultEnvelop = smsMessageService
				.sendStoringSmsMessage(convertToSmsMessage(sendSmsRequest));
		
		if (resultEnvelop.isSuccessful()) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(resultEnvelop.getData().getId())).build();
			return created(uri).entity(resultEnvelop).build();
		}

		else if (ResultTypeEnum.VALIDATION_ERROR.equals(resultEnvelop.getResultType()))
			return Response.status(422).entity(resultEnvelop).build();
		
		else if (ResultTypeEnum.OPERATION_ERROR.equals(resultEnvelop.getResultType()))
			return Response.status(500).entity(resultEnvelop).build();

		return Response.status(500).entity(resultEnvelop).build();
	}
	
	@GET
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyRESTService(InputStream incomingData) {
		return Response.status(200).entity("{\"message\":\"SMS API Successfully started..\"}").build();
	}

	private static final SmsMessage convertToSmsMessage(SendSmsRequest sendSmsRequest) {
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setReceiverInformation(sendSmsRequest.getReceiverInformation());
		smsMessage.setSenderInformation(sendSmsRequest.getSenderInformation());
		smsMessage.setExpirationTime(sendSmsRequest.getSmsExpirationTime());
		smsMessage.setBody(sendSmsRequest.getSmsBody());
		return smsMessage;
	}
}
