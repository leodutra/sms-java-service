SMS Story

Narrative:
In order to provide a simple enterprise SMS service (API)
As a development team
I want to specify a content type and get that content


As a development team,
We want to send SMS using a simple enterprise service (API),
so that we do not have to implement a complex integration service ourselves.


Scenario: SMS is sent and stored in database

Given a valid SMS message
When I send a POST SMS request on "/sms"
Then ensure SMS is returned with id
And ensure SMS is stored in the database


Scenario: Error 'cause SMS is expired (past expiration date)

Given a SMS message with past expiration date 
When SMS message is sent  
Then ensure expiration message is shown
And ensure SMS is stored in the database 


Scenario: Error 'cause SMS body is blank

Given a SMS message with blank body
When SMS message is sent  
Then ensure blank body message is shown


Scenario: Error 'cause SMS sender is blank

Given a SMS message with blank sender identification
When SMS message is sent  
Then ensure blank sender message is shown


Scenario: Error 'cause SMS receiver is blank

Given a SMS message with blank receiver identification
When SMS message is sent  
Then ensure blank receiver message is shown