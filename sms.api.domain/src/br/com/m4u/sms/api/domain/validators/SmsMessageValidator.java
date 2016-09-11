package br.com.m4u.sms.api.domain.validators;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.results.ResultError;
import br.com.m4u.sms.api.domain.results.ResultErrorFactory;
import br.com.m4u.sms.api.exceptions.IntegrationException;

public class SmsMessageValidator extends AbstractValidator<SmsMessage> {

	@Override
	public List<ResultError> validate(SmsMessage obj) throws IntegrationException {
		
		List<ResultError> result = new ArrayList<ResultError>();

		if (obj == null) {
			result.add(ResultErrorFactory.factory("vld.smsmessage.null")); 
			return result;
		}
		
		if (StringUtils.isBlank(obj.getBody()))
			result.add(ResultErrorFactory.factory("vld.smsmessage.body.null"));

		if (StringUtils.isBlank(obj.getReceiverInformation()))
			result.add(ResultErrorFactory.factory("vld.smsmessage.receiver.null"));

		if (StringUtils.isBlank(obj.getSenderInformation()))
			result.add(ResultErrorFactory.factory("vld.smsmessage.sender.null"));

		if (obj.getRegistrationTime() == null)
			result.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.null"));

		else if (obj.getRegistrationTime().isAfter(Instant.now()))
			result.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.future"));

		return result;
	}
	
	public ResultError validateExpirationDateOnly(SmsMessage obj) throws IntegrationException {
		if (obj == null) {
			return ResultErrorFactory.factory("vld.smsmessage.null");
		}
		
		if (obj.getExpirationTime().isBefore(Instant.now())) 
			return ResultErrorFactory.factory("vld.smsmessage.expired");
		
		return null;
	}
}