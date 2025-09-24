package com.example.orderorchestrator.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private String customerName;
    private List<String> items;
    private OrderStatus status;

    public Order() {}

    public Order(String customerName, List<String> items) {
        this.orderId = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.items = items;
        this.status = OrderStatus.CREATED;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
