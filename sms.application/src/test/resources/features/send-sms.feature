Feature: Send and Store Sms 
	This feature will test functionality of our integrated SMS service

Scenario Outline: Send SMS with expiration time
	Given a SMS message with from "<sender>", to "<addressee>", body "<body>", expiration "<expiration time>" 
	And a Short Message Service 
	When the service receives the SMS 
	Then the service stores that SMS 
	And the service sends that SMS message to the service of our preferred carrier 
	And the service returns storage identificator of the SMS identification 
	
	Examples: 
		|sender    |addressee  |body  |expiration time         |
		|2199992222|2199322222|fit   |2018-10-26T00:13:32.803Z|
		|2199992223|2199991333|fat   |2017-03-26T17:13:32.803Z|
		|2199991333|2199322222|Hi!   |2017-12-26T17:13:32.803Z|
		|2199322222|2199991333|Test! |2020-10-26T17:13:32.803Z|
		
Scenario Outline: Send SMS without expiration time
	Given a SMS message with from "<sender>", to "<addressee>", body "<body>"
	And a Short Message Service 
	When the service receives the SMS 
	Then the service stores that SMS 
	And the service sends that SMS message to the service of our preferred carrier 
	And the service returns storage identificator of the SMS identification 
	
	Examples: 
		|sender    |addressee  |body  |
		|2199992222|2199322222|fit   |
		|2199992223|2199991333|fat   |
		|2199991333|2199322222|Hi!   |
		|2199322222|2199991333|Test! |
