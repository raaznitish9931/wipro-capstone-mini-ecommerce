package com.wipro.productservice.dto;

public class ProductRequestDto {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageUrl;
    private String brand;
    private double rating;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String description, double price, int quantity, String category, String imageUrl, String brand, double rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.rating = rating;
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