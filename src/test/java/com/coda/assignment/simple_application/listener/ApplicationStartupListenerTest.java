package com.coda.assignment.simple_application.listener;

import com.coda.assignment.simple_application.service.IPAddressProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class ApplicationStartupListenerTest {

    @Mock
    private IPAddressProvider ipAddressProvider;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ApplicationStartupListener applicationStartupListener;

    private static final String ROUTING_SERVICE_PATH = "http://loadbalancer.com/register";
    private static final String APPLICATION_NAME = "simple-application-app";
    private static final String SERVER_PORT = "8080";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(ipAddressProvider.getLocalIPAddress()).thenReturn("192.168.1.100");

        applicationStartupListener = new ApplicationStartupListener(
                ipAddressProvider,
                restTemplate,
                ROUTING_SERVICE_PATH,
                APPLICATION_NAME,
                SERVER_PORT
        );
    }

    @Test
    void shouldRegisterWithLoadBalancerOnApplicationReady() throws InterruptedException {
        applicationStartupListener.onApplicationReady();

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        verify(restTemplate, timeout(1000).atLeastOnce()).getForObject(urlCaptor.capture(), eq(Object.class));

        String expectedUrl = ROUTING_SERVICE_PATH + "?serviceName=" + APPLICATION_NAME + "&instanceIP=http://192.168.1.100:8080";
        assert urlCaptor.getValue().equals(expectedUrl);
    }
}