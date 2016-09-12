package br.com.m4u.sms.domain.validators;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.domain.entities.SmsMessage;
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

		if (StringUtils.isBlank(sms.getBody()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.body.null"));

		if (StringUtils.isBlank(sms.getReceiverInformation()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.receiver.null"));

		if (StringUtils.isBlank(sms.getSenderInformation()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.sender.null"));

		if (sms.getRegistrationTime() == null)
			violations.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.null"));

		else if (sms.getRegistrationTime().isAfter(Instant.now()))
			violations.add(ResultErrorFactory.factory("vld.smsmessage.resgistration.time.future"));

		return violations;
	}

	public ResultError validateExpirationDateOnly(SmsMessage obj) throws IntegrationException {
		if (obj == null) {
			return ResultErrorFactory.factory("vld.smsmessage.null");
		}

		if (obj.getExpirationTime() != null && obj.getExpirationTime().isBefore(Instant.now()))
			return ResultErrorFactory.factory("vld.smsmessage.expired");

		return null;
	}
}