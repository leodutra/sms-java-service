package br.com.m4u.sms.api.domain.services;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.valueobjects.OperationResult;

public interface ISmsMessageService {

	OperationResult<SmsMessage> sendSmsMessage(SmsMessage sms);
	
}
