package br.com.m4u.sms.app.service.v1.endpoints;

import static javax.ws.rs.core.Response.created;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.m4u.sms.app.service.v1.contracts.SendSmsRequest;
import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.domain.results.Result;
import br.com.m4u.sms.domain.results.ResultTypeEnum;
import br.com.m4u.sms.domain.services.SmsMessageServiceImpl;
import br.com.m4u.sms.logging.Logger;
import br.com.m4u.sms.service.agents.smsagent.SmsServiceAgent;
import br.com.m4u.sms.service.agents.smsagent.SmsServiceAgentImpl;

@Path("/v1/sms")
public class SmsEndpoint {


	//@Inject @Preferred 
	private UnitOfWork unitOfWork;
	
	//@Inject @Preferred
	private SmsServiceAgent smsServiceAgent;
	
	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response send(SendSmsRequest sendSmsRequest) {

		try (UnitOfWork unitOfWork = new br.com.m4u.sms.persistence.postgre.PostgreUnitOfWork()){
			smsServiceAgent = new SmsServiceAgentImpl();
		//try {
		
			Result<SmsMessage> resultEnvelop = new SmsMessageServiceImpl(unitOfWork, smsServiceAgent).sendStoringSmsMessage(convertToSmsMessage(sendSmsRequest));

			if (resultEnvelop.isSuccessful()) {
				
				unitOfWork.commit();
				
				URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(resultEnvelop.getData().getId())).build();
				return created(uri).entity(resultEnvelop).build();
				
			} else if (ResultTypeEnum.VALIDATION_ERROR.equals(resultEnvelop.getResultType())) {
				return Response.status(422).entity(resultEnvelop).build();
			}
			else {
				return Response.status(500).entity(resultEnvelop).build();
			}
			
		} catch (Exception ex) {
			Logger.logError(SmsEndpoint.class, ex);
			
			Result<SendSmsRequest> result = Result.<SendSmsRequest>getBuilder(ResultTypeEnum.INTERNAL_SERVER_ERROR)
					.data(sendSmsRequest).result();
			
			return Response.status(500).entity(result).build();
		}
	}

	@GET
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyRESTService() {
		Result<String> envelop = Result.<String>getBuilder(ResultTypeEnum.SUCCESS).data("SMS API Successfully started...").result();
		return Response.status(200).entity(envelop).build();
	}

	private static final SmsMessage convertToSmsMessage(SendSmsRequest sendSmsRequest) {

		if (sendSmsRequest == null)
			return null;

		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setReceiverInformation(sendSmsRequest.getReceiverInformation());
		smsMessage.setSenderInformation(sendSmsRequest.getSenderInformation());
		smsMessage.setExpirationTime(sendSmsRequest.getSmsExpirationTime());
		smsMessage.setBody(sendSmsRequest.getSmsBody());
		return smsMessage;
	}
}
