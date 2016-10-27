package br.com.m4u.sms.domain.agents;

import br.com.m4u.sms.domain.model.SendSmsRequest;
import br.com.m4u.sms.domain.model.SendSmsStatus;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;


public interface SmsAgent {

	SendSmsStatus sendSms(SendSmsRequest sms) throws IntegrationException;
}
