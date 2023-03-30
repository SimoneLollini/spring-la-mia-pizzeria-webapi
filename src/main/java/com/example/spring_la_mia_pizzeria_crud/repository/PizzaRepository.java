package com.example.spring_la_mia_pizzeria_crud.repository;

import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    //costruisce una query che richiami dal modello pizza un nome che contiene
    // la String name che viene passata come parametro
    // select name from pizza where name like "%stringa che viene passata%"
    public List<Pizza> findByNameContainingIgnoreCase(String name);
}
