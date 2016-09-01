package sms.api.service.agents.smsagent;

import br.com.m4u.sms.api.crosscutting.exceptions.IntegrationException;
import sms.api.service.agents.smsagent.contracts.SendSmsRequest;
import sms.api.service.agents.smsagent.contracts.SendSmsResponse;

public interface ISmsServiceAgent {

	SendSmsResponse sendSms(SendSmsRequest smsServiceRequest) throws IntegrationException;
}
