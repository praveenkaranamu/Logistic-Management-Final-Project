package com.example.logistics.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.logistics.repository.LogisticsOrderRepository;

@Component
public class LogisticsScheduler {

    private final LogisticsOrderRepository repository;

    public LogisticsScheduler(
            LogisticsOrderRepository repository) {

        this.repository = repository;
    }

    @Scheduled(fixedRate = 30000)
    public void checkLogisticsOrders() {

        System.out.println(
            "Scheduler executed at: "
            + LocalDateTime.now());

        System.out.println(
            "Total Logistics Orders: "
            + repository.count());
    }
}