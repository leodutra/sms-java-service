package br.com.m4u.sms.domain.services;

import br.com.m4u.sms.domain.entities.SmsMessage;
import br.com.m4u.sms.domain.results.Result;

public interface SmsMessageService {

	Result<SmsMessage> sendStoringSmsMessage(SmsMessage sms);
	
}
