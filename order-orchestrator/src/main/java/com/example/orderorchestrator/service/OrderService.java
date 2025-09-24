package com.example.orderorchestrator.service;

import com.example.orderorchestrator.model.Order;
import com.example.orderorchestrator.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public Order createOrder(String customerName, List<String> items) {
        Order order = new Order(customerName, items);
        orders.put(order.getOrderId(), order);
        log.info("Order {} created at {} with status {}", order.getOrderId(), OffsetDateTime.now(), order.getStatus());
        return order;
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public Order getById(String id) {
        Order o = orders.get(id);
        if (o == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        return o;
    }

    public Order moveToNext(String id) {
        Order order = orders.get(id);
        if (order == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");

        synchronized (order) {
            OrderStatus current = order.getStatus();
            OrderStatus next = nextStatus(current);
            if (next == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Order is already in final state (" + current + ")");
            }

            if (next == OrderStatus.VALIDATED) {
                if (order.getItems() == null || order.getItems().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Cannot VALIDATE: items list is empty");
                }
            }

            if (next == OrderStatus.SHIPPED) {
                if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Cannot SHIP: customerName is empty");
                }
            }

            order.setStatus(next);
            log.info("Order {}: {} -> {} at {}", id, current, next, OffsetDateTime.now());
            return order;
        }
    }

    private OrderStatus nextStatus(OrderStatus s) {
        return switch (s) {
            case CREATED -> OrderStatus.VALIDATED;
            case VALIDATED -> OrderStatus.PACKED;
            case PACKED -> OrderStatus.SHIPPED;
            case SHIPPED -> OrderStatus.DELIVERED;
            case DELIVERED -> null;
        };
    }
}
