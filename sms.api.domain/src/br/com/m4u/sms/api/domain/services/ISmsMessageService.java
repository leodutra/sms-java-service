package br.com.m4u.sms.api.domain.services;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.results.Result;

public interface ISmsMessageService {

	Result<SmsMessage> sendStoringSmsMessage(SmsMessage sms);
	
}
