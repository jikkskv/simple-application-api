package com.coda.assignment.simple_application.listener;

import com.coda.assignment.simple_application.service.IPAddressProvider;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PreDestroyCallback implements DisposableBean {

    private final RestTemplate restTemplate;

    private final String healthCheckUrl;

    private final String serverPort;

    @Autowired
    PreDestroyCallback(IPAddressProvider ipAddressProvider,
                       RestTemplate restTemplate,
                       @Value("${routing-service.deregister.path}") String routingServicePath,
                       @Value("${spring.application.name:simple-application-app}") String applicationName,
                       @Value("${server.port:8080}") String serverPort) {
        this.restTemplate = restTemplate;
        this.serverPort = serverPort;
        String myIpAddress = "http://" + ipAddressProvider.getLocalIPAddress() + ":" + serverPort;
        healthCheckUrl = routingServicePath + "?serviceName=" + applicationName + "&instanceIP=" + myIpAddress;
    }

    @Override
    public void destroy() throws Exception {
        restTemplate.getForObject(healthCheckUrl, Object.class);
        System.out.println("Shutting Down the application on port: " + serverPort);
    }
}
