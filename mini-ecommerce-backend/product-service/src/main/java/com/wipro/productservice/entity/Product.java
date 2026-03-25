


package com.wipro.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double price;
    private int quantity;


    private String category;
    private String imageUrl;
    private String brand;
    private double rating;

    public Product() {
    }

    public Product(int id, String name, String description, double price, int quantity, String category, String imageUrl, String brand, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    } 

    public void setName(String name) {
        this.name = name;
    } 

    public String getDescription() {
        return description;
    } 

    public void setDescription(String description) {
        this.description = description;
    } 

    public double getPrice() {
        return price;
    } 

    public void setPrice(double price) {
        this.price = price;
    } 

    public int getQuantity() {
        return quantity;
    } 

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

   
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}