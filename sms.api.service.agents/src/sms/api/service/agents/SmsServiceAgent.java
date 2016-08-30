package sms.api.service.agents;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import br.com.m4u.sms.api.crosscutting.exceptions.IntegrationException;
import sms.api.service.agents.contracts.SmsServiceRequest;
import sms.api.service.agents.contracts.SmsServiceResponse;

public class SmsServiceAgent implements ISmsServiceAgent {

	@Override
	public SmsServiceResponse sendSms(SmsServiceRequest smsServiceRequest) throws IntegrationException {
		try {
			String uri = 
			    "http://localhost:8080/CustomerService/rest/customers/1";
			URL url = new URL(uri);
			HttpURLConnection connection = 
			    (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/xml");
			 
			JAXBContext jc = JAXBContext.newInstance(SmsServiceResponse.class);
			InputStream xml = connection.getInputStream();
			SmsServiceResponse response = 
			    (SmsServiceResponse) jc.createUnmarshaller().unmarshal(xml);
			 
			connection.disconnect();
			
			return response;
		}
		catch(Exception ex) {
			throw new IntegrationException(ex);
		}
	}

}
