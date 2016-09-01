package br.com.m4u.sms.api.domain.services;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.api.constants.Constants;
import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.functions.IMapper;
import br.com.m4u.sms.api.domain.functions.IOperationValidator;
import br.com.m4u.sms.api.domain.persistence.IUnitOfWork;
import br.com.m4u.sms.api.domain.valueobjects.OperationResult;
import br.com.m4u.sms.api.logging.Logger;
import br.com.m4u.sms.api.service.agents.smsagent.SmsServiceAgent;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsResponse;

public class SmsMessageService implements ISmsMessageService {

	private IUnitOfWork unitOfWork;
	
	public SmsMessageService(IUnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}
	
	@Override
	public OperationResult<SmsMessage> sendSmsMessage(SmsMessage sms)  {
		
		OperationResult<SmsMessage> operationResult = sendSmsMessageValidation.Validate(sms);
		
		if (operationResult.isSuccessful() == false) return operationResult;
		
		try {
			
			unitOfWork.smsMessageRepository().insert(sms);

			if (sms.getExpirationTime().isBefore(Instant.now())) {
				operationResult.resetError("Sms could not be sent: past expiration date.");
				return operationResult;
			}
			
			SendSmsResponse agentResponse = new SmsServiceAgent().sendSms(sendSmsRequestMapper.map(sms));
			
			if (agentResponse.isSucessful() == false) {
				 operationResult.resetError(agentResponse.getMessage());
				 return operationResult;
			}
			
			operationResult.setData(sms);
			
		} catch (Exception ex) {
			
			Logger.logError(getClass(), ex);
			
			operationResult.clearErrors();
			operationResult.addError(Constants.MSG_INTERNAL_SERVER_ERROR);
		}
		
		return null;
	}
	
	private static IOperationValidator<SmsMessage> sendSmsMessageValidation = (smsMessage) -> {
		
		OperationResult<SmsMessage> result = new OperationResult<SmsMessage>(smsMessage);
		
		if (smsMessage == null) {
			result.addError("SmsMessage instance is required.");
			return result;
		}
		
		if (StringUtils.isBlank(smsMessage.getBody())) 
			result.addError("SmsMessage.body can't be blank or null.");
		
		if (StringUtils.isBlank(smsMessage.getReceiverInformation())) 
			result.addError("SmsMessage.receiver information can't be blank or null.");
		
		if (StringUtils.isBlank(smsMessage.getSenderInformation())) 
			result.addError("SmsMessage.sender information can't be blank or null.");
			
		
		if (smsMessage.getRegistrationTime() == null)  
			result.addError("SmsMessage.resgistration time is required.");
		
		else if (smsMessage.getRegistrationTime().isAfter(Instant.now())) 
			result.addError("SmsMessage.registration time can't be a future mark.");
		
		return result;
	};
	
	private static IMapper<SmsMessage, SendSmsRequest> sendSmsRequestMapper = (smsMessage) -> {
		SendSmsRequest agentRequest = new SendSmsRequest();
		agentRequest.setFrom(smsMessage.getSenderInformation());
		agentRequest.setTo(smsMessage.getReceiverInformation());
		agentRequest.setBody(smsMessage.getBody());
		return agentRequest;
	};
}
