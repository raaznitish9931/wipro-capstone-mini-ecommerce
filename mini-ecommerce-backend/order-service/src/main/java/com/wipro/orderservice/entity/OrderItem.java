
package com.wipro.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(int id, int orderId, int productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

   
    public void validateItem() {
        if (orderId < 0) {
            throw new RuntimeException("Invalid order id");
        }
        if (productId <= 0) {
            throw new RuntimeException("Invalid product id");
        }
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        if (price < 0) {
            throw new RuntimeException("Price cannot be negative");
        }
    }

    public double getSubTotal() {
        return quantity * price;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getOrderId() {
        return orderId;
    } 

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getPrice() {
        return price;
    } 

    public void setPrice(double price) {
        this.price = price;
    }
}