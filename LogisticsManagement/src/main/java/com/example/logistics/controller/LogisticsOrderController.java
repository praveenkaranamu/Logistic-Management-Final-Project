package com.example.logistics.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.example.logistics.util.FileStorageUtil;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.logistics.dto.LogisticsOrderRequest;
import com.example.logistics.dto.LogisticsOrderResponse;
import com.example.logistics.service.LogisticsOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/logistics/orders")
public class LogisticsOrderController {

	private final LogisticsOrderService service;
	private final FileStorageUtil fileStorageUtil;

	public LogisticsOrderController(
	        LogisticsOrderService service,
	        FileStorageUtil fileStorageUtil) {

	    this.service = service;
	    this.fileStorageUtil = fileStorageUtil;
	}
    // CREATE
    @PostMapping
    public ResponseEntity<LogisticsOrderResponse>
    createOrder(
            @Valid @RequestBody
            LogisticsOrderRequest request) {

        return new ResponseEntity<>(
                service.createOrder(request),
                HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<LogisticsOrderResponse>>
    getAllOrders() {

        return ResponseEntity.ok(
                service.getAllOrders());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<LogisticsOrderResponse>
    getOrderById(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getOrderById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<LogisticsOrderResponse>
    updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody
            LogisticsOrderRequest request) {

        return ResponseEntity.ok(
                service.updateOrder(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteOrder(@PathVariable Long id) {

        service.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<LogisticsOrderResponse>
    updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                service.updateStatus(id, status));
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LogisticsOrderResponse>>
    getByStatus(@PathVariable String status) {

        return ResponseEntity.ok(
                service.getByStatus(status));
    }

    // SEARCH DESTINATION
    @GetMapping("/search/destination")
    public ResponseEntity<List<LogisticsOrderResponse>>
    searchDestination(
            @RequestParam String destination) {

        return ResponseEntity.ok(
                service.searchDestination(destination));
    }

    // JPQL SEARCH
    @GetMapping("/search/expensive")
    public ResponseEntity<List<LogisticsOrderResponse>>
    getExpensiveOrders(
            @RequestParam Double amount) {

        return ResponseEntity.ok(
                service.getExpensiveOrders(amount));
    }

    // NATIVE SQL
    @GetMapping("/native/status/{status}")
    public ResponseEntity<List<LogisticsOrderResponse>>
    getNativeStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.getNativeStatus(status));
    }

    // PAGINATION + SORT
    @GetMapping("/page")
    public ResponseEntity<Page<LogisticsOrderResponse>>
    getOrdersPage(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        return ResponseEntity.ok(
                service.getOrdersPage(
                    page,
                    size,
                    sortBy,
                    direction));
    }

    // PAGINATION BY STATUS
    @GetMapping("/page/status/{status}")
    public ResponseEntity<Page<LogisticsOrderResponse>>
    getOrdersByStatusPage(

            @PathVariable String status,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return ResponseEntity.ok(
                service.getOrdersByStatusPage(
                    status,
                    page,
                    size));
    }
    
    @PostMapping("/files/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file) {

        String fileName =
                fileStorageUtil.saveFile(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("File uploaded successfully: " + fileName);
    }
    
    @GetMapping("/files/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable String fileName) {

        byte[] data =
                fileStorageUtil.getFile(fileName);

        return ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}