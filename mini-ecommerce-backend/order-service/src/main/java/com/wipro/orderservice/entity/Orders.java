
package com.wipro.orderservice.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDate orderDate;

    public Orders() {
    }

    public Orders(int id, int userId, int productId, int quantity, double totalPrice, String status, LocalDate orderDate) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
    }



    public void createOrder(int userId, int productId, int quantity, double unitPrice) {
        if (userId <= 0) {
            throw new RuntimeException("Invalid user id");
        }
        if (productId <= 0) {
            throw new RuntimeException("Invalid product id");
        }
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        if (unitPrice < 0) {
            throw new RuntimeException("Unit price cannot be negative");
        }

        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
        this.status = "PLACED";
        this.orderDate = LocalDate.now();
    }

    public void cancelOrder() {
        if ("CANCELLED".equalsIgnoreCase(this.status)) {
            throw new RuntimeException("Order is already cancelled");
        }

        this.status = "CANCELLED";
    }

    public void validateOrder() {
        if (userId <= 0) {
            throw new RuntimeException("Invalid user id");
        }
        if (productId <= 0) {
            throw new RuntimeException("Invalid product id");
        }
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }
        if (totalPrice < 0) {
            throw new RuntimeException("Total price cannot be negative");
        }
        if (status == null || status.isBlank()) {
            throw new RuntimeException("Order status cannot be empty");
        }
        if (orderDate == null) {
            throw new RuntimeException("Order date cannot be null");
        }
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