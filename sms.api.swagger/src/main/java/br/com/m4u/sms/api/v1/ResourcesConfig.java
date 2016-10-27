package br.com.m4u.sms.api.v1;

import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;


public class ResourcesConfig extends ResourceConfig {

	public ResourcesConfig() {

		packages(true, "io.swagger.jaxrs.listing");
		packages(true, "br.com.m4u.sms.api.v1");
		//register(JsonObjectMapper.class);
		//register(JacksonFeature.class);

		Info info = new Info()

				.title("M4U Sms API")

				.description(
						"This is a sample server SMS server, built to accomplish most of a challenge requirements.")

				.contact(new Contact().email("leodutra.br@gmail.com").url("https://github.com/leodutra"))
				.license(new License().name("MIT").url("https://opensource.org/licenses/MIT"));

		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setInfo(info);
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:9090");
		beanConfig.setBasePath("/sms/api/v1");
		beanConfig.setResourcePackage("br.com.m4u.sms.api.v1");
		beanConfig.setScan(true);
	}

}