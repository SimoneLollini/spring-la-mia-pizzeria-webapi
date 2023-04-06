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

    public Pizza createPizza(Pizza formPizza) {
        Pizza newPizza = new Pizza();
        newPizza.setName(formPizza.getName());
        newPizza.setDescription(formPizza.getDescription());
        newPizza.setPrice(formPizza.getPrice());
        return pizzaRepository.save(newPizza);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws RuntimeException {
        Pizza pizzaToUpdate = getById(id);
        pizzaToUpdate.setName(formPizza.getName());
        pizzaToUpdate.setDescription(formPizza.getDescription());
        pizzaToUpdate.setPrice(formPizza.getPrice());
        return pizzaRepository.save(pizzaToUpdate);
    }

    public boolean deleteById(Integer id) throws RuntimeException {
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
