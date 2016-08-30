package sms.api.service.agents;

import br.com.m4u.sms.api.crosscutting.exceptions.IntegrationException;
import sms.api.service.agents.contracts.SmsServiceRequest;
import sms.api.service.agents.contracts.SmsServiceResponse;

public interface ISmsServiceAgent {

	SmsServiceResponse sendSms(SmsServiceRequest smsServiceRequest) throws IntegrationException;
}
