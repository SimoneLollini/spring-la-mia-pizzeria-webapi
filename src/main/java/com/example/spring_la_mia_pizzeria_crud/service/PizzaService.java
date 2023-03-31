package com.example.spring_la_mia_pizzeria_crud.service;

import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzasSortByName() {
        return pizzaRepository.findAll(Sort.by("name"));
    }

    public List<Pizza> getFilteredPizzasSortByName(String keyword) {
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza getById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException(Integer.toString(id));
        }
    }

    public void createPizza(Pizza formPizza) {
        Pizza newPizza = new Pizza();
        newPizza.setName(formPizza.getName());
        newPizza.setDescription(formPizza.getDescription());
        newPizza.setPrice(formPizza.getPrice());
        pizzaRepository.save(newPizza);
    }
}
