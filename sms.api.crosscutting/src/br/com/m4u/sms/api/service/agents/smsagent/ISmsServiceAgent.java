package br.com.m4u.sms.api.service.agents.smsagent;

import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.api.service.agents.smsagent.contracts.SendSmsResponse;

public interface ISmsServiceAgent {

	SendSmsResponse sendSms(SendSmsRequest smsServiceRequest) throws IntegrationException;
}
