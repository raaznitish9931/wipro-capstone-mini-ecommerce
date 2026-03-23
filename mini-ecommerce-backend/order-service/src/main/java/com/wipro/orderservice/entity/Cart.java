package com.wipro.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int productId;
    private int quantity;

    public Cart() {
    }

    public Cart(int id, int userId, int productId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
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
}