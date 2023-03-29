package com.example.spring_la_mia_pizzeria_crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pizzas")
public class Pizza {
    String name;
    @Lob
    String description;
    float price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
