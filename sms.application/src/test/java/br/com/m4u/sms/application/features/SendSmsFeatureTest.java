package br.com.m4u.sms.application.features;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


// @Category(UnitTest.class)
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "br.com.m4u.sms.application.features")
public class SendSmsFeatureTest {}
