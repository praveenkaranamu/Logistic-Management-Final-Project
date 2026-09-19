package com.example.logistics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.example.logistics.dto.LogisticsOrderResponse;
import com.example.logistics.entity.LogisticsOrder;
import com.example.logistics.exception.ResourceNotFoundException;
import com.example.logistics.mapper.LogisticsOrderMapper;
import com.example.logistics.repository.LogisticsOrderRepository;

@ExtendWith(MockitoExtension.class)
class LogisticsOrderServiceTest {

    @Mock
    private LogisticsOrderRepository repository;

    @Mock
    private LogisticsOrderMapper mapper;

    @InjectMocks
    private LogisticsOrderService service;

    @Test
    void testGetOrderByIdSuccess() {

        LogisticsOrder order =
                new LogisticsOrder();

        order.setId(1L);
        order.setCustomerName("Ravi");

        LogisticsOrderResponse response =
                new LogisticsOrderResponse();

        response.setId(1L);
        response.setCustomerName("Ravi");

        when(repository.findById(1L))
                .thenReturn(Optional.of(order));

        when(mapper.toResponse(order))
                .thenReturn(response);

        LogisticsOrderResponse result =
                service.getOrderById(1L);

        assertEquals(1L, result.getId());
        assertEquals(
                "Ravi",
                result.getCustomerName());
    }

    @Test
    void testGetOrderByIdNotFound() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getOrderById(99L)
        );
    }
}