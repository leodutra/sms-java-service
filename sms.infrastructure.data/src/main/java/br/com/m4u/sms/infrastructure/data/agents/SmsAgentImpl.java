package br.com.m4u.sms.infrastructure.data.agents;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.m4u.sms.domain.agents.SmsAgent;
import br.com.m4u.sms.domain.model.SendSmsRequest;
import br.com.m4u.sms.domain.model.SendSmsStatus;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;
import br.com.m4u.sms.infrastructure.crosscutting.logging.Logger;


public class SmsAgentImpl implements SmsAgent {

	@Override
	public SendSmsStatus sendSms(SendSmsRequest sms) throws IntegrationException {
		try {

			URL url = new URL("http://localhost:8080/api/v1/sms");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("PUT");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			connection.setRequestProperty("Accept-Language", "pt-BR, en-US;q=0.9, en;q=0.8, es;q=0.7");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);

			ObjectMapper jsonMapper = new ObjectMapper();

			try (DataOutputStream postBody = new DataOutputStream(connection.getOutputStream())) {
				postBody.write(jsonMapper.writeValueAsString(sms).getBytes());
			}

			switch (connection.getResponseCode()) {
				case 200:
					return SendSmsStatus.SMS_SENT;

				case 404:
					return SendSmsStatus.MOBILE_USER_NOT_FOUND;

				case 405:
					return SendSmsStatus.VALIDATION_EXCEPTION;

				case 500:
				default:
					throw new IntegrationException(connection.getResponseMessage());
			}

		}
		catch (Exception ex) {
			Logger.logError(SmsAgentImpl.class, ex);
			throw new IntegrationException(ex);
		}
	}
}
