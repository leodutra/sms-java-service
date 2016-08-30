package br.com.m4u.sms.api.domain.services;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.valueobjects.OperationResult;
import br.com.m4u.sms.api.domain.valueobjects.PaginationSpecification;

public interface ISmsMessageService {

	OperationResult<SmsMessage> sendSmsMessage(SmsMessage sms);
	OperationResult<SmsMessage> listRegisteredSmsMessages(PaginationSpecification pagination);
}
