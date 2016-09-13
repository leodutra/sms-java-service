package br.com.m4u.sms.domain.validators;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.domain.entities.constraints.DomainConstraints;
import br.com.m4u.sms.domain.results.ResultError;
import br.com.m4u.sms.domain.results.ResultErrorFactory;
import br.com.m4u.sms.exceptions.IntegrationException;

public class SmsMessageValidator extends AbstractValidator<SmsMessage> {

	@Override
	public List<ResultError> validate(SmsMessage sms) throws IntegrationException {

		List<ResultError> violations = new ArrayList<ResultError>();

		if (sms == null) {
			violations.add(ResultErrorFactory.factory("vld.smsmessage.null"));
			return violations;
		}

		// Body
		if (StringUtils.isBlank(sms.getBody())) 
			violations.add(ResultErrorFactory.factory("vld.smsmessage.body.null"));
		
		else if (sms.getBody().length() > DomainConstraints.SmsMessage.smsBodyMaxLength)
			violations.add(ResultErrorFactory.factory("vld.smsmessage.body.overflow.length", DomainConstraints.SmsMessage.smsBodyMaxLength));

		
		// Receiver
		if (StringUtils.isBlank(sms.getReceiverInformation()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.receiver.null"));
		
		else if (sms.getReceiverInformation().length() > DomainConstraints.SmsMessage.receiverInfoMaxLength)
			violations.add(ResultErrorFactory.factory("vld.smsmessage.receiver.overflow.length", DomainConstraints.SmsMessage.receiverInfoMaxLength));

		
		// Sender
		if (StringUtils.isBlank(sms.getSenderInformation()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.sender.null"));
		
		else if (sms.getSenderInformation().length() > DomainConstraints.SmsMessage.senderInfoMaxLength)
			violations.add(ResultErrorFactory.factory("vld.smsmessage.sender.overflow.length", DomainConstraints.SmsMessage.senderInfoMaxLength));

		
		// Registration
		if (sms.getRegistrationTime() == null)
			violations.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.null"));

		else if (sms.getRegistrationTime().isAfter(Instant.now()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.future"));
		
		return violations;
	}

	public List<ResultError> validateExpirationOnly(SmsMessage sms) throws IntegrationException {
		
		List<ResultError> violations = new ArrayList<ResultError>();
		
		if (sms == null) {
			violations.add(ResultErrorFactory.factory("vld.smsmessage.null"));
		}
		else if (sms.getExpirationTime() != null) {
			
			if ((sms.getRegistrationTime() != null && sms.getExpirationTime().isBefore(sms.getRegistrationTime())) 
					|| sms.getExpirationTime().isBefore(Instant.now()))
				violations.add(ResultErrorFactory.factory("vld.smsmessage.expired"));
		}
		
		return violations;
	}
}