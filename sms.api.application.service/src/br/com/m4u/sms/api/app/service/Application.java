package br.com.m4u.sms.api.app.service;

import static io.undertow.servlet.Servlets.servlet;

import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;	

public class Application extends javax.ws.rs.core.Application {  
	
    private static Undertow server;

    public static void main(String[] args) throws ServletException {
        startContainer(9090);
    }

    public static void stopContainer(){
        server.stop();
    }

    public static void startContainer(int port) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment();

        servletBuilder
			    .setClassLoader(Application.class.getClassLoader())
			    .setContextPath("/")
			    .setDeploymentName("sms-api.war")
			    .addServlets(servlet("SmsJerseyServlet", ServletContainer.class)
			            .setLoadOnStartup(1)
			            .addInitParam("javax.ws.rs.Application", ResourcesConfig.class.getName())
			            .addMapping("/api/*"))
			    		.addListener(Servlets.listener(Listener.class)); 

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        
        manager.deploy();
        
        PathHandler path = Handlers.path(Handlers.redirect("/"))
                .addPrefixPath("/", manager.start());

        server = Undertow
                        .builder()
                        .addHttpListener(port, "localhost")
                        .setHandler(path)
                        .build();

        server.start();
    }
}