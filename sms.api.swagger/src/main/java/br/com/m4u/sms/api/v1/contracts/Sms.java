package br.com.m4u.sms.api.v1.contracts;

import java.time.Instant;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class Sms {

	@ApiModelProperty(value = "mobile number of the SMS sender", required = false)
	public Long id;

	@ApiModelProperty(value = "mobile number of the SMS sender", required = true)
	public String senderInformation;

	@ApiModelProperty(value = "mobile number of the SMS addressee", required = true)
	public String addresseeInformation;

	@ApiModelProperty(value = "body the SMS", required = true)
	public String body;

	@ApiModelProperty(value = "date and time of the SMS expiration")
	public Date expirationTime;

	@ApiModelProperty(value = "date and time of the SMS record")
	public Date registrationTime;
}