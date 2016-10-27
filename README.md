## M4U SMS API

BDD + DDD modeled API.
BDD started defining a ubiquitous language and basic behaviour (domain of application), and after "failing first", DDD helped introducing a minimun responsibility separation and shaping the main domain components. After a green result on expected behaviours, a ping pong between BDD expectations and ideal DDD patterns is an easy peasy job.

### Installation

On sms.solution:
```
mvn clean 
mvn install 
``` 

### Usage

Run br.com.m4u.sms.api.Application as Java SE executable.

### Details

Using Undertow, Jersey (JAX-RS), Jackson, Swagger, Weld (CDI), Maven module aggregation and cross-dependency, Cucumber (BDD), Mockito ("TDD commons"), Java 8 features and more.

Undertow was picked because of the challenge and the great benchmarks on the Internet ([one of them](https://www.techempower.com/benchmarks/#section=data-r12&hw=peak&test=json)).
Jersey is a clean and simple JAX-RS implementation, using Jackson for serialization/deserialization.
Swagger simply is awesome!
Weld has a great integration with Undertow and Jersey. A bit hard to master this partnership, but very powerful as I could see.
Maven module aggregation and dependencies is not for everyone... it destroys teams if not well managed. Here it is managed.
Cumcuber is a little buggy with Maven Sunfire/Failsafe plugins, but still is way more friendly than JBehave.
Mockito is another awesome framework. No PowerMockito were used in this project.
Java 8 functions for the glory of FP and streams.

Leo Dutra



