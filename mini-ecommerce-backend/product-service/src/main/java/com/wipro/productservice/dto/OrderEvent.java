package com.wipro.productservice.dto;

import java.time.LocalDateTime;

public class OrderEvent {

    private String event;
    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private LocalDateTime timestamp;

    public OrderEvent() {}

    public OrderEvent(String event, int orderId, int productId, String productName, int quantity, LocalDateTime timestamp) {
        this.event = event;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    // getters & setters
    public String getEvent() { return event; }
    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setEvent(String event) { this.event = event; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}