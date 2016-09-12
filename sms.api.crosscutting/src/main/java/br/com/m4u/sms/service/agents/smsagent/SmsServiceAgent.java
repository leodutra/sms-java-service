package br.com.m4u.sms.service.agents.smsagent;

import br.com.m4u.sms.exceptions.IntegrationException;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsResponse;

public interface SmsServiceAgent {

	SendSmsResponse sendSms(SendSmsRequest smsServiceRequest) throws IntegrationException;
}
