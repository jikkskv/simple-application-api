package com.coda.assignment.simple_application.controller;

import com.coda.assignment.simple_application.service.IPAddressProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SimpleApiController {

    private final IPAddressProvider ipAddressProvider;

    private final String serverPort;

    @Autowired
    SimpleApiController(IPAddressProvider ipAddressProvider,
                        @Value("${server.port:8080}") String serverPort) {
        this.ipAddressProvider = ipAddressProvider;
        this.serverPort = serverPort;
    }

    @PostMapping("/total_trips")
    public ResponseEntity<?> getTotalTrips(@RequestParam(value = "start") String startDateStr,
                                           @RequestParam(value = "end") String endDateStr) {
        log.info("Request received for SimpleApiController.getTotalTrips, startDateStr: {}, endDateStr: {} ", startDateStr, endDateStr);
        return ResponseEntity.ok(Map.of("startDateStr", startDateStr, "endDateStr", endDateStr,
                "hostName", ipAddressProvider.getLocalIPAddress(), "port", serverPort));
    }
}
