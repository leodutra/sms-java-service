package br.com.m4u.sms.domain.converters;

import br.com.m4u.sms.domain.model.SendSmsRequest;
import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.infrastructure.crosscutting.function.Converter;


public class SmsAgentConverter implements Converter<SmsMessage, SendSmsRequest> {

	@Override
	public SendSmsRequest convert(SmsMessage source) {

		if (source == null) return null;

		SendSmsRequest request = new SendSmsRequest();
		request.setFrom(source.getSenderInformation());
		request.setTo(source.getAddresseeInformation());
		request.setBody(source.getBody());
		return request;
	}
}
