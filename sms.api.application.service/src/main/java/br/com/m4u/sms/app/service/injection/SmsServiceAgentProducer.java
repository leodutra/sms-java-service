package br.com.m4u.sms.app.service.injection;

import javax.enterprise.inject.Produces;

import br.com.m4u.sms.service.agents.smsagent.SmsServiceAgent;
import br.com.m4u.sms.service.agents.smsagent.SmsServiceAgentImpl;

public class SmsServiceAgentProducer {
	
	@Produces
	@Preferred
	public SmsServiceAgent produceSmsServiceAgent(SmsServiceAgentImpl smsServiceAgentImpl) {
		return smsServiceAgentImpl;
	}
}
