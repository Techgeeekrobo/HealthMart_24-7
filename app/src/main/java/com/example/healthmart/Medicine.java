package com.example.healthmart;

public class Medicine {

    private String name;
    private int price;
    private String description;
    private int stock;

    // Empty constructor for Firestore
    public Medicine() {
        // Default constructor required for Firestore
    }

    public Medicine(String description,String name, int price, int stock) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}


