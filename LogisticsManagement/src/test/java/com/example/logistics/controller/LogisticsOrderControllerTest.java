package com.example.logistics.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.logistics.dto.LogisticsOrderResponse;
import com.example.logistics.service.LogisticsOrderService;
import com.example.logistics.util.FileStorageUtil;

@SpringBootTest
@AutoConfigureMockMvc
class LogisticsOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogisticsOrderService service;

    @MockBean
    private FileStorageUtil fileStorageUtil;

    @Test
    void testCreateOrder() throws Exception {

        LogisticsOrderResponse response =
                new LogisticsOrderResponse();

        response.setId(1L);
        response.setCustomerName("Ravi");
        response.setStatus("PENDING");

        when(service.createOrder(any()))
                .thenReturn(response);

        String json = """
                {
                    "customerName": "Ravi",
                    "source": "Hyderabad",
                    "destination": "Vijayawada",
                    "productName": "Laptop",
                    "quantity": 2,
                    "shippingCost": 500
                }
                """;

        mockMvc.perform(
                post("/api/logistics/orders")
                .contentType(
                    MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andExpect(
                status().isCreated()
        );
    }
}