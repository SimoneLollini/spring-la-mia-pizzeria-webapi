package com.example.spring_la_mia_pizzeria_crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Size(min = 1, max = 250)
    @Column(nullable = false)
    private String name;
    @Lob
    private String description;
    @Column(nullable = true)
    @Min(value = 0, message = "Price must be greater than 0")
    private float price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "pizza")
    private List<SpecialOffer> specialOffers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }
}
