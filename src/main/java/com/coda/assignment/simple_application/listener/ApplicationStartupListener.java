package com.coda.assignment.simple_application.listener;

import com.coda.assignment.simple_application.service.IPAddressProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ApplicationStartupListener {

    public static final int HEARTBEAT_INTERVAL = 5_000;

    private final RestTemplate restTemplate;

    private final String healthCheckUrl;

    @Autowired
    ApplicationStartupListener(IPAddressProvider ipAddressProvider,
                               RestTemplate restTemplate,
                               @Value("${routing-service.register.path}") String routingServicePath,
                               @Value("${spring.application.name:simple-application-app}") String applicationName,
                               @Value("${server.port:8080}") String serverPort) {
        this.restTemplate = restTemplate;
        String myIpAddress = "http://" + ipAddressProvider.getLocalIPAddress() + ":" + serverPort;
        healthCheckUrl = routingServicePath + "?serviceName=" + applicationName + "&instanceIP=" + myIpAddress;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("ApplicationReady Event triggered");
        new Thread(() -> {
            while (true) {
                try {
                    log.info("Sending HeartBeat to load balancer at: {}", healthCheckUrl);
                    restTemplate.getForObject(healthCheckUrl, Object.class);
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (InterruptedException e) {
                    log.error("Error occurred in registering with load balancer at: {}", healthCheckUrl);
                }
            }
        }).start();
    }
}