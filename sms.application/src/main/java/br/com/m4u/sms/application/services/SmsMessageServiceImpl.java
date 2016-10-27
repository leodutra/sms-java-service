package br.com.m4u.sms.application.services;

import java.time.Instant;
import java.util.List;

import br.com.m4u.sms.domain.agents.SmsAgent;
import br.com.m4u.sms.domain.converters.SmsAgentConverter;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.Result.Type;
import br.com.m4u.sms.domain.model.SendSmsStatus;
import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.domain.validators.SmsMessageValidator;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;
import br.com.m4u.sms.infrastructure.crosscutting.logging.Logger;


public class SmsMessageServiceImpl implements SmsMessageService {

	private SmsMessageValidator smsMessageValidator = new SmsMessageValidator();

	private UnitOfWork unitOfWork;
	private SmsAgent smsAgent;

	public SmsMessageServiceImpl(UnitOfWork unitOfWork, SmsAgent smsAgent) {
		this.unitOfWork = unitOfWork;
		this.smsAgent = smsAgent;
	}

	@Override
	public Result<SmsMessage> sendAndStoreSmsMessage(final SmsMessage sms) {

		try {

			// Set registration time
			sms.setRegistrationTime(Instant.now());

			// Validate domain entity
			List<String> brokenRules = smsMessageValidator.validate(sms);

			if (brokenRules.isEmpty() == false)
				return new Result<SmsMessage>(Type.VALIDATION_ERROR).withDetails(brokenRules).withData(sms);

			// Store SMS
			SmsMessage insertedSms = unitOfWork.smsMessageRepository().insert(sms);

			// Validate expiration date (stored for auditing)
			List<String> expirationDetails = smsMessageValidator.validateExpirationOnly(insertedSms);

			if (expirationDetails.isEmpty() == false)
				return new Result<SmsMessage>(Type.VALIDATION_ERROR).withDetails(expirationDetails);

			// Send SMS
			//SendSmsStatus agentResponse = smsAgent.sendSms(new SmsAgentConverter().convert(sms));
			//switch (agentResponse) {
			switch (SendSmsStatus.SMS_SENT) {
				case SMS_SENT:
					return new Result<SmsMessage>(Type.SUCCESS).withData(insertedSms);

				case MOBILE_USER_NOT_FOUND:
					return new Result<SmsMessage>(Type.OPERATION_FAILURE).withDetail("Mobile user not found.");

				case VALIDATION_EXCEPTION:
				default:
					throw new IntegrationException(
							"Missing validation or unknown status, when communicating with carrier SMS service. Please verify.");
			}

		}
		catch (Exception ex) {
			Logger.logError(SmsMessageServiceImpl.class, ex);
			return new Result<SmsMessage>(Type.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Result<List<SmsMessage>> listAllStoredSms() {
		try {
			return new Result<List<SmsMessage>>(Type.SUCCESS).withData(unitOfWork.smsMessageRepository().listAll());
		}
		catch (Exception ex) {
			Logger.logError(SmsMessageServiceImpl.class, ex);
			return new Result<List<SmsMessage>>(Type.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Result<SmsMessage> findStoredSmsById(Object id) {
		try {
			SmsMessage sms = unitOfWork.smsMessageRepository().findById(id);

			if (sms == null) return new Result<SmsMessage>(Type.RESOURCE_NOT_FOUND);

			return new Result<SmsMessage>(Type.SUCCESS).withData(sms);
		}
		catch (Exception ex) {
			Logger.logError(SmsMessageServiceImpl.class, ex);
			return new Result<SmsMessage>(Type.INTERNAL_SERVER_ERROR);
		}
	}
}
