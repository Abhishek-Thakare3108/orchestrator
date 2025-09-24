package com.example.orderorchestrator.dto;

import java.util.List;

public class CreateOrderRequest {
    private String customerName;
    private List<String> items;

    public CreateOrderRequest() {}

    public CreateOrderRequest(String customerName, List<String> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }
}
