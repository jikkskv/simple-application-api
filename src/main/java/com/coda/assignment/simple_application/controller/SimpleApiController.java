package com.coda.assignment.simple_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SimpleApiController {

    @PostMapping("/total_trips")
    public ResponseEntity<?> getTotalTrips(@RequestParam(value = "start") String startDateStr,
                                           @RequestParam(value = "end") String endDateStr) {
        return ResponseEntity.ok(Map.of("startDateStr", startDateStr, "endDateStr", endDateStr));
    }
}
