package test.integrated.br.com.m4u.api.app.service.steps;

import java.time.Instant;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import br.com.m4u.sms.api.domain.entities.SmsMessage;

public class SendSmsSteps extends Steps {

	private SmsMessage smsMessage;

    @Given("Given a valid SMS message")
    public void createValidSms() {
    	smsMessage = new SmsMessage();
    	smsMessage.setSenderInformation("2196636565");
    	smsMessage.setReceiverInformation("2188987755");
		smsMessage.setBody("Hello SMS");
	    smsMessage.setExpirationTime(Instant.parse("2100-01-01T00:00:000Z"));
    }
    
    @When("I send a $method SMS request on \"$path\"")
    public void sendRequest(String method, String path, String request) {
     
    }

    @Then("Then ensure SMS is returned with id.") 
    public void ensureSMSIdReturn() {
       
    }
    
    @Then("And ensure SMS is stored in the database") 
    public void ensureSMSRecorded() {
       
    }
}
