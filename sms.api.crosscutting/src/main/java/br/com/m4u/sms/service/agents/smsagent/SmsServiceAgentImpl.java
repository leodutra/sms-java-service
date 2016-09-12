package br.com.m4u.sms.service.agents.smsagent;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.m4u.sms.exceptions.IntegrationException;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsRequest;
import br.com.m4u.sms.service.agents.smsagent.contracts.SendSmsResponse;

public class SmsServiceAgentImpl implements SmsServiceAgent {

	@Override
	public SendSmsResponse sendSms(SendSmsRequest sendSmsRequest) throws IntegrationException {
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
				postBody.write(jsonMapper.writeValueAsString(sendSmsRequest).getBytes());
			}

			SendSmsResponse response = new SendSmsResponse(connection.getResponseCode() == 200,
					connection.getResponseMessage());

			// cached keep alive socket == no connection.disconnect()

			return response;
		} catch (Exception ex) {
			throw new IntegrationException(ex);
		}
	}
}
