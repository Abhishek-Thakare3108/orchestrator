package com.example.orderorchestrator.controller;

import com.example.orderorchestrator.dto.CreateOrderRequest;
import com.example.orderorchestrator.model.Order;
import com.example.orderorchestrator.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderRequest req) {
        Order created = service.createOrder(req.getCustomerName(), req.getItems());
        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/{id}/next")
    public ResponseEntity<Order> next(@PathVariable String id) {
        Order updated = service.moveToNext(id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> all() {
        return ResponseEntity.ok(service.getAll());
    }
}
