package br.com.m4u.sms.api.domain.validators;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.valueobjects.OperationResult;

public final class SmsMessageValidator implements IValidator<SmsMessage> {


	@Override
	public OperationResult<SmsMessage> validateCreate(SmsMessage entity) {
		
		OperationResult<SmsMessage> result = new OperationResult<SmsMessage>(entity);
		
		if (entity == null) {
			result.addBrokenRule("SmsMessage instance is required.");
			return result;
		}
		
		if (StringUtils.isBlank(entity.getBody())) 
			result.addBrokenRule("SmsMessage.body can't be blank or null.");
		
		if (StringUtils.isBlank(entity.getReceiverInformation())) 
			result.addBrokenRule("SmsMessage.receiver information can't be blank or null.");
		
		if (StringUtils.isBlank(entity.getSenderInformation())) 
			result.addBrokenRule("SmsMessage.sender information can't be blank or null.");
			
		
		if (entity.getRegistrationTime() == null)  
			result.addBrokenRule("SmsMessage.resgistration time is required.");
		
		else if (entity.getRegistrationTime().isAfter(Instant.now())) 
			result.addBrokenRule("SmsMessage.registration time can't be a future mark.");
		
		return result;
	}

	@Override
	public OperationResult<SmsMessage> validateUpdate(SmsMessage entity) {

		OperationResult<SmsMessage> result = new OperationResult<SmsMessage>(entity);
		
		if (entity == null) {
			result.addBrokenRule("SmsMessage instance is required.");
			return result;
		}
		
		if (entity.getId() == null) 
			result.addBrokenRule("SmsMessage.id is required.");
		
		if (StringUtils.isBlank(entity.getBody())) 
			result.addBrokenRule("SmsMessage.body can't be blank or null.");
		
		if (StringUtils.isBlank(entity.getReceiverInformation())) 
			result.addBrokenRule("SmsMessage.receiver information can't be blank or null.");
		
		if (StringUtils.isBlank(entity.getSenderInformation())) 
			result.addBrokenRule("SmsMessage.sender information can't be blank or null.");
			
		if (entity.getRegistrationTime() == null)  
			result.addBrokenRule("SmsMessage.resgistration time is required.");
		
		else if (entity.getRegistrationTime().isAfter(Instant.now())) 
			result.addBrokenRule("SmsMessage.registration time can't be a future mark.");
		
		return result;
	}

	@Override
	public OperationResult<SmsMessage> validateGetById(Object id) {
		OperationResult<SmsMessage> result = new OperationResult<SmsMessage>();

		if (id == null) 
			result.addBrokenRule("SmsMessage.id is required for entity obtaintion.");
		
		return result;
	}

	@Override
	public OperationResult<SmsMessage> validateDelete(SmsMessage entity) {
		OperationResult<SmsMessage> result = new OperationResult<SmsMessage>();

		if (entity.getId() == null) 
			result.addBrokenRule("SmsMessage.id is required for deletion.");
		
		return result;
	}
}
