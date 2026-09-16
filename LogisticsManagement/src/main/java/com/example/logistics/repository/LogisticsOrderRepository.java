package com.example.logistics.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.logistics.entity.LogisticsOrder;

public interface LogisticsOrderRepository
        extends JpaRepository<LogisticsOrder, Long> {

    // Derived Query
    List<LogisticsOrder> findByStatus(String status);

    // Search
    List<LogisticsOrder>
    findByDestinationContainingIgnoreCase(String destination);

    // JPQL
    @Query("""
           SELECT o
           FROM LogisticsOrder o
           WHERE o.shippingCost > :amount
           """)
    List<LogisticsOrder>
    findExpensiveOrders(@Param("amount") Double amount);

    // Native SQL
    @Query(
        value = "SELECT * FROM logistics_orders WHERE status = :status",
        nativeQuery = true
    )
    List<LogisticsOrder>
    findByStatusNative(@Param("status") String status);

    // Pagination
    Page<LogisticsOrder>
    findAll(Pageable pageable);

    // Pagination + status
    Page<LogisticsOrder>
    findByStatus(String status, Pageable pageable);
}