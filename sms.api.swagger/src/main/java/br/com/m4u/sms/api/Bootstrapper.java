package br.com.m4u.sms.api;

import static io.undertow.servlet.Servlets.servlet;

import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import br.com.m4u.sms.api.v1.ResourcesConfig;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;


public class Bootstrapper {

	private static Undertow server;

	private final static String HOST = "localhost";
	private final static int PORT = 9090;

	public static void main(String[] args) throws ServletException {
		startContainer(HOST, PORT);
	}

	public static void stopContainer() {
		server.stop();
	}

	public static void startContainer(String host, int port) throws ServletException {
		DeploymentInfo servletBuilder = Servlets.deployment();

		servletBuilder.setClassLoader(Bootstrapper.class.getClassLoader()).setContextPath("/")
				.setDeploymentName("sms-api.war")
				.addServlets(servlet("SmsJerseyServlet", ServletContainer.class).setLoadOnStartup(1)

						.setAsyncSupported(true)

						.addMapping("/sms/api/v1/*")

						.addInitParam("jersey.config.server.wadl.disableWadl", "true")
						.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ResourcesConfig.class.getName()))

				// CDI listener
				.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);

		manager.deploy();

		PathHandler path = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", manager.start());

		server = Undertow.builder().addHttpListener(port, host).setHandler(path).build();

		server.start();
	}
}