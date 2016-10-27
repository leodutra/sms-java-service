package br.com.m4u.sms.domain.validators;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.m4u.sms.domain.model.SmsMessage;


public class SmsMessageValidator implements Validator<SmsMessage> {

	@Override
	public List<String> validate(SmsMessage sms) {

		List<String> brokenRules = new ArrayList<String>();

		if (sms == null) {
			brokenRules.add("Sms message can't be null.");
			return brokenRules;
		}

		// Body
		if (StringUtils.isBlank(sms.getBody()))
			brokenRules.add("Sms message body can't be blank or null.");

		else if (sms.getBody().length() > SmsMessage.Constraint.SMS_BODY_MAX_LENGTH)
			brokenRules.add(MessageFormat.format("Sms message body exceeded max length (max {0} characters).",
					SmsMessage.Constraint.SMS_BODY_MAX_LENGTH));

		// Addressee
		if (StringUtils.isBlank(sms.getAddresseeInformation()))
			brokenRules.add("Sms message addressee information can't be blank or null.");

		else if (sms.getAddresseeInformation().length() > SmsMessage.Constraint.RECEIVER_INFO_MAX_LENGTH)
			brokenRules.add(MessageFormat.format("Sms message addressee exceeded max length (max {0} characters).",
					SmsMessage.Constraint.RECEIVER_INFO_MAX_LENGTH));

		// Sender
		if (StringUtils.isBlank(sms.getSenderInformation()))
			brokenRules.add("Sms message sender information can't be blank or null.");

		else if (sms.getSenderInformation().length() > SmsMessage.Constraint.SENDER_INFO_MAX_LENGTH)
			brokenRules.add(MessageFormat.format("Sms message sender exceeded max length (max {0} characters).",
					SmsMessage.Constraint.SENDER_INFO_MAX_LENGTH));

		// Registration
		if (sms.getRegistrationTime() == null)
			brokenRules.add("SmsMessage.resgistration time is required.");

		else if (sms.getRegistrationTime().isAfter(Instant.now()))
			brokenRules.add("SmsMessage.registration time can't be a future mark.");

		return brokenRules;
	}

	public List<String> validateExpirationOnly(SmsMessage sms) {

		List<String> violations = new ArrayList<String>();

		if (sms == null)
			violations.add("Sms message can't be null.");
		else if (sms.getExpirationTime() != null)
			if (sms.getRegistrationTime() != null && sms.getExpirationTime().isBefore(sms.getRegistrationTime())
					|| sms.getExpirationTime().isBefore(Instant.now()))
				violations.add(" Sms could not be sent: past expiration date.");

		return violations;
	}
}