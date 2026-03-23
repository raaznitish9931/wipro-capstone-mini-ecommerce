package com.wipro.orderservice.dto;

import java.time.LocalDate;

public class OrderResponseDto {

    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDate orderDate;

    public OrderResponseDto() {
    }

    public OrderResponseDto(int id, int userId, int productId, int quantity, double totalPrice, String status, LocalDate orderDate) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    } 

    public int getUserId() {
        return userId;
    } 

    public void setUserId(int userId) {
        this.userId = userId;
    } 

    public int getProductId() {
        return productId;
    } 

    public void setProductId(int productId) {
        this.productId = productId;
    } 

    public int getQuantity() {
        return quantity;
    } 

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    } 

    public double getTotalPrice() {
        return totalPrice;
    } 

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    } 

    public String getStatus() {
        return status;
    } 

    public void setStatus(String status) {
        this.status = status;
    } 

    public LocalDate getOrderDate() {
        return orderDate;
    } 

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}