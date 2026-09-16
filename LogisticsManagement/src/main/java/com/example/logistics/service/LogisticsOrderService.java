package com.example.logistics.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.logistics.dto.LogisticsOrderRequest;
import com.example.logistics.dto.LogisticsOrderResponse;
import com.example.logistics.entity.LogisticsOrder;
import com.example.logistics.exception.ResourceNotFoundException;
import com.example.logistics.mapper.LogisticsOrderMapper;
import com.example.logistics.repository.LogisticsOrderRepository;

@Service
public class LogisticsOrderService {

    private final LogisticsOrderRepository repository;
    private final LogisticsOrderMapper mapper;

    public LogisticsOrderService(
            LogisticsOrderRepository repository,
            LogisticsOrderMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE
    @Transactional
    public LogisticsOrderResponse createOrder(
            LogisticsOrderRequest request) {

        LogisticsOrder order = mapper.toEntity(request);

        if (order.getStatus() == null ||
                order.getStatus().isBlank()) {

            order.setStatus("PENDING");
        }

        LogisticsOrder saved =
                repository.save(order);

        return mapper.toResponse(saved);
    }

    // GET ALL
    public List<LogisticsOrderResponse> getAllOrders() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // GET BY ID
    public LogisticsOrderResponse getOrderById(Long id) {

        LogisticsOrder order =
                repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Order not found with id: " + id));

        return mapper.toResponse(order);
    }

    // UPDATE
    @Transactional
    public LogisticsOrderResponse updateOrder(
            Long id,
            LogisticsOrderRequest request) {

        LogisticsOrder order =
                repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Order not found with id: " + id));

        mapper.updateEntity(order, request);

        LogisticsOrder updated =
                repository.save(order);

        return mapper.toResponse(updated);
    }

    // DELETE
    @Transactional
    public void deleteOrder(Long id) {

        LogisticsOrder order =
                repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Order not found with id: " + id));

        repository.delete(order);
    }

    // UPDATE STATUS
    @Transactional
    public LogisticsOrderResponse updateStatus(
            Long id,
            String status) {

        LogisticsOrder order =
                repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Order not found with id: " + id));

        String newStatus =
                status.toUpperCase();

        if (!newStatus.equals("PENDING") &&
            !newStatus.equals("READY") &&
            !newStatus.equals("IN_TRANSIT") &&
            !newStatus.equals("DELIVERED") &&
            !newStatus.equals("CANCELLED")) {

            throw new IllegalArgumentException(
                "Invalid status. Use PENDING, READY, "
                + "IN_TRANSIT, DELIVERED or CANCELLED");
        }

        order.setStatus(newStatus);

        return mapper.toResponse(
                repository.save(order));
    }

    // FIND BY STATUS
    public List<LogisticsOrderResponse>
    getByStatus(String status) {

        return repository.findByStatus(
                status.toUpperCase())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // SEARCH DESTINATION
    public List<LogisticsOrderResponse>
    searchDestination(String destination) {

        return repository
                .findByDestinationContainingIgnoreCase(
                    destination)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // JPQL
    public List<LogisticsOrderResponse>
    getExpensiveOrders(Double amount) {

        return repository
                .findExpensiveOrders(amount)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // NATIVE SQL
    public List<LogisticsOrderResponse>
    getNativeStatus(String status) {

        return repository
                .findByStatusNative(
                    status.toUpperCase())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // PAGINATION + SORTING
    public Page<LogisticsOrderResponse>
    getOrdersPage(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {

            sort = Sort.by(sortBy).descending();

        } else {

            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    // PAGINATION BY STATUS
    public Page<LogisticsOrderResponse>
    getOrdersByStatusPage(
            String status,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(
                    page,
                    size,
                    Sort.by("id").descending()
                );

        return repository
                .findByStatus(
                    status.toUpperCase(),
                    pageable)
                .map(mapper::toResponse);
    }
}