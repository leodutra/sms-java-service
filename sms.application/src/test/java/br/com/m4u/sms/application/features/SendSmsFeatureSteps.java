package br.com.m4u.sms.application.features;

import java.time.Instant;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.m4u.sms.application.services.SmsMessageService;
import br.com.m4u.sms.application.services.SmsMessageServiceImpl;
import br.com.m4u.sms.domain.agents.SmsAgent;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.SendSmsRequest;
import br.com.m4u.sms.domain.model.SendSmsStatus;
import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.domain.persistence.UnitOfWork;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;


public class SendSmsFeatureSteps {

	private SmsMessage receivedSms;

	private UnitOfWork unitOfWork;

	private SmsMessageRepository smsRepository;

	private SmsAgent smsAgent;

	private SmsMessageService smsMessageService;

	private Result<SmsMessage> smsResult;

	@Given("^a SMS message with from \"([^\"]*)\", to \"([^\"]*)\", body \"([^\"]*)\", expiration \"([^\"]*)\"$")
	public void a_SMS_message_with_from_to_body_expiration(String sender, String addressee, String body,
			String expirationTime) throws Throwable {
		receivedSms = new SmsMessage();
		receivedSms.setSenderInformation(sender.trim());
		receivedSms.setAddresseeInformation(addressee.trim());
		receivedSms.setBody(body.trim());
		receivedSms.setExpirationTime(Instant.parse(expirationTime.trim()));

		receivedSms.setId(1L);
	}

	@Given("^a SMS message with from \"([^\"]*)\", to \"([^\"]*)\", body \"([^\"]*)\"$")
	public void a_SMS_message_with_from_to_body_expiration(String sender, String addressee, String body)
			throws Throwable {
		receivedSms = new SmsMessage();
		receivedSms.setSenderInformation(sender.trim());
		receivedSms.setAddresseeInformation(addressee.trim());
		receivedSms.setBody(body.trim());

		receivedSms.setId(1L);
	}

	@And("^a Short Message Service$")
	public void sms_service() throws Throwable {
		try {
			unitOfWork = mock(UnitOfWork.class);
			smsRepository = mock(SmsMessageRepository.class);

			given(smsRepository.insert(receivedSms)).willReturn(receivedSms);
			given(unitOfWork.smsMessageRepository()).willReturn(smsRepository);

			smsAgent = mock(SmsAgent.class);
			given(smsAgent.sendSms(any(SendSmsRequest.class))).willReturn(SendSmsStatus.SMS_SENT);

			smsMessageService = new SmsMessageServiceImpl(unitOfWork, smsAgent);
		}
		catch (IntegrationException e) {
			fail(SmsMessageRepository.class.getName() + " fail caused by exception.");
		}
	}

	@When("^the service receives the SMS$")
	public void service_receives_sms() throws Throwable {
		smsResult = smsMessageService.sendAndStoreSmsMessage(receivedSms);
	}

	@Then("^the service stores that SMS$")
	public void service_stores_sms() throws Throwable {
		verify(smsRepository, times(1)).insert(receivedSms);
	}

	@And("^the service sends that SMS message to the service of our preferred carrier$")
	public void service_sends_to_carrier_endpoint() throws Throwable {
		verify(smsAgent, times(1)).sendSms(isA(SendSmsRequest.class));
	}

	@And("^the service returns storage identificator of the SMS identification$")
	public void server_returns_resource_identifier() throws Throwable {
		assertEquals(receivedSms.getId(), smsResult.getData().getId());
	}
}
