package br.com.m4u.sms.api.domain.services;

import java.time.Instant;
import java.util.List;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.persistence.UnitOfWork;
import br.com.m4u.sms.api.domain.results.Result;
import br.com.m4u.sms.api.domain.results.ResultError;
import br.com.m4u.sms.api.domain.results.ResultTypeEnum;
import br.com.m4u.sms.api.domain.validators.SmsMessageValidator;
import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.logging.Logger;
import br.com.m4u.sms.api.service.agents.smsagent.SmsServiceAgentImpl;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsResponse;

public final class SmsMessageServiceImpl implements SmsMessageService {

	private UnitOfWork unitOfWork;

	public SmsMessageServiceImpl(UnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	@Override
	public Result<SmsMessage> sendStoringSmsMessage(final SmsMessage sms) {

		final SmsMessageValidator smsMessageValidator = new SmsMessageValidator();

		try {
			
			// Set registration time
			sms.setRegistrationTime(Instant.now());

			// Validate domain entity
			List<ResultError> validationDetails = smsMessageValidator.validate(sms);

			if (validationDetails.isEmpty() == false)
				return Result.<SmsMessage>getBuilder(ResultTypeEnum.VALIDATION_ERROR).errors(validationDetails).data(sms)
						.result();

			// Store SMS
			SmsMessage insertedSms = unitOfWork.smsMessageRepository().insert(sms);

			// Validate expiration date
			ResultError expiredError = smsMessageValidator.validateExpirationDateOnly(insertedSms);
			if (expiredError != null) {
				return Result.<SmsMessage>getBuilder(ResultTypeEnum.VALIDATION_ERROR).error(expiredError).data(sms).result();
			}

			// Send SMS
			SendSmsResponse agentResponse = new SmsServiceAgentImpl().sendSms(convertSmsToSend(insertedSms));
			if (agentResponse.isSucessful() == false) {
				throw new IntegrationException(
						new StringBuilder()
							.append(SmsServiceAgentImpl.class.getName())
							.append("::")
							.append(agentResponse.getMessage())
							.toString());
			}

			return Result.<SmsMessage>getBuilder(ResultTypeEnum.SUCCESS).data(insertedSms).result();

		} catch (Exception ex) {

			Logger.logError(getClass(), ex);
			return Result.<SmsMessage>getBuilder(ResultTypeEnum.INTERNAL_SERVER_ERROR).error(ResultTypeEnum.INTERNAL_SERVER_ERROR.getMessage()).data(sms).result();
		}
	}

	private static SendSmsRequest convertSmsToSend(SmsMessage sms) {
		SendSmsRequest request = new SendSmsRequest();
		request.setFrom(sms.getSenderInformation());
		request.setTo(sms.getReceiverInformation());
		request.setBody(sms.getBody());
		return request;
	}
}
