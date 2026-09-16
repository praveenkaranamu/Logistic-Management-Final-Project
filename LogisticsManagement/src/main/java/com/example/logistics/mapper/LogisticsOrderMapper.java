package com.example.logistics.mapper;

import org.springframework.stereotype.Component;

import com.example.logistics.dto.LogisticsOrderRequest;
import com.example.logistics.dto.LogisticsOrderResponse;
import com.example.logistics.entity.LogisticsOrder;

@Component
public class LogisticsOrderMapper {

    public LogisticsOrder toEntity(LogisticsOrderRequest request) {

        LogisticsOrder order = new LogisticsOrder();

        order.setCustomerName(request.getCustomerName());
        order.setSource(request.getSource());
        order.setDestination(request.getDestination());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setShippingCost(request.getShippingCost());
        order.setStatus(request.getStatus());

        return order;
    }

    public LogisticsOrderResponse toResponse(LogisticsOrder order) {

        LogisticsOrderResponse response =
                new LogisticsOrderResponse();

        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setSource(order.getSource());
        response.setDestination(order.getDestination());
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setShippingCost(order.getShippingCost());
        response.setStatus(order.getStatus());
        response.setFileName(order.getFileName());
        response.setVersion(order.getVersion());
        response.setCreatedDate(order.getCreatedDate());
        response.setLastModifiedDate(order.getLastModifiedDate());
        response.setCreatedBy(order.getCreatedBy());
        response.setLastModifiedBy(order.getLastModifiedBy());

        return response;
    }

    public void updateEntity(
            LogisticsOrder order,
            LogisticsOrderRequest request) {

        order.setCustomerName(request.getCustomerName());
        order.setSource(request.getSource());
        order.setDestination(request.getDestination());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setShippingCost(request.getShippingCost());

        if (request.getStatus() != null &&
                !request.getStatus().isBlank()) {

            order.setStatus(request.getStatus());
        }
    }
}