package com.example.spring_la_mia_pizzeria_crud.api;

import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> index(@RequestParam(name = "q") Optional<String> search) {
        if (search.isPresent())
            return pizzaService.getFilteredPizzasSortByName(search.get());

        return pizzaService.getAllPizzasSortByName();
    }

    @GetMapping("/{pizzaId}")
    public Pizza show(@PathVariable("pizzaId") Integer id) {
        try {
            return pizzaService.getById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata!");
        }
    }

    @PostMapping("/create")
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/{id}")
    public Pizza update(@Valid @RequestBody Pizza pizza, @PathVariable Integer id) {
        try {
            return pizzaService.updatePizza(pizza, id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        try {
            return pizzaService.deleteById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
