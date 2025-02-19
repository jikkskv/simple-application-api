package com.coda.assignment.simple_application.controller;

import com.coda.assignment.simple_application.service.IPAddressProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SimpleApiController.class)
class SimpleApiControllerTest {

    @MockitoBean
    private IPAddressProvider ipAddressProvider;

    @Autowired
    private MockMvc mockMvc;

    private final String serverPort = "8080";

    @Test
    void testGetTotalTrips_success() throws Exception {
        Mockito.when(ipAddressProvider.getLocalIPAddress()).thenReturn("192.168.1.100");
        String startDate = "2024-02-18";
        String endDate = "2024-02-25";

        mockMvc.perform(post("/total_trips")
                        .param("start", startDate)
                        .param("end", endDate))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.startDateStr").value("2024-02-18"))
                .andExpect(jsonPath("$.endDateStr").value("2024-02-25"))
                .andExpect(jsonPath("$.hostName").value("192.168.1.100"))
                .andExpect(jsonPath("$.port").value(serverPort))
                .andExpect(content().contentType("application/json"));
    }
}
