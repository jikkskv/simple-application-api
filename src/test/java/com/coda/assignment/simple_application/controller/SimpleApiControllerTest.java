package com.coda.assignment.simple_application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SimpleApiController.class)
class SimpleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTotalTrips_success() throws Exception {
        String startDate = "2024-02-18";
        String endDate = "2024-02-25";

        mockMvc.perform(post("/total_trips")
                        .param("start", startDate)
                        .param("end", endDate))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.startDateStr").value(startDate))
                .andExpect(jsonPath("$.endDateStr").value(endDate))
                .andExpect(content().contentType("application/json"));
    }
}