package com.coda.assignment.simple_application.service;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class IPAddressProvider {
    public String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to fetch local IP address", e);
        }
    }
}