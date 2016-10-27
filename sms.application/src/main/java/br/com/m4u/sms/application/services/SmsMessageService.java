package br.com.m4u.sms.application.services;

import java.util.List;

import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.SmsMessage;

public interface SmsMessageService {

	Result<SmsMessage> sendAndStoreSmsMessage(SmsMessage sms);
	
	Result<List<SmsMessage>> listAllStoredSms();
	
	Result<SmsMessage> findStoredSmsById(Object id);
}
