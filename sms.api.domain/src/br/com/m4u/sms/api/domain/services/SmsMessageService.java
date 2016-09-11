package br.com.m4u.sms.api.domain.services;

import java.time.Instant;
import java.util.List;

import br.com.m4u.sms.api.constants.Constants;
import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.persistence.IUnitOfWork;
import br.com.m4u.sms.api.domain.results.Result;
import br.com.m4u.sms.api.domain.results.ResultError;
import br.com.m4u.sms.api.domain.results.ResultTypeEnum;
import br.com.m4u.sms.api.domain.validators.SmsMessageValidator;
import br.com.m4u.sms.api.logging.Logger;
import br.com.m4u.sms.api.service.agents.smsagent.SmsServiceAgent;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsResponse;

public final class SmsMessageService implements ISmsMessageService {

	private IUnitOfWork unitOfWork;

	public SmsMessageService(IUnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	@Override
	public Result<SmsMessage> sendStoringSmsMessage(final SmsMessage sms) {

		final SmsMessageValidator smsMessageValidator = new SmsMessageValidator();

		try {

			// Validate domain entity
			List<ResultError> validationDetails = smsMessageValidator.validate(sms);

			if (validationDetails.isEmpty() == false)
				return Result.<SmsMessage>build(ResultTypeEnum.VALIDATION_ERROR).errors(validationDetails).data(sms)
						.result();

			// Set registration time
			sms.setRegistrationTime(Instant.now());

			// Store SMS
			SmsMessage insertedSms = unitOfWork.smsMessageRepository().insert(sms);

			// Validate expiration date
			ResultError expiredError = smsMessageValidator.validateExpirationDateOnly(insertedSms);
			if (expiredError != null)
				return Result.<SmsMessage>build(ResultTypeEnum.VALIDATION_ERROR).error(expiredError).data(sms).result();

			// Send SMS
			SendSmsResponse agentResponse = new SmsServiceAgent().sendSms(convertSmsToSend(insertedSms));
			if (agentResponse.isSucessful() == false)
				return Result.<SmsMessage>build(ResultTypeEnum.OPERATION_ERROR).error(agentResponse).data(sms).result();

				return new Result<SmsMessage>(ResultTypeEnum.OPERATION_ERROR, agentResponse.getMessage(), sms);

			return new Result<SmsMessage>(ResultTypeEnum.SUCCESS, insertedSms);

		} catch (Exception ex) {

			Logger.logError(getClass(), ex);
			return new Result<SmsMessage>(ResultTypeEnum.INTERNAL_SERVER_ERROR, Constants.MSG_INTERNAL_SERVER_ERROR,
					sms);
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
