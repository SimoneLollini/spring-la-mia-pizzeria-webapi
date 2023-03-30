package com.example.spring_la_mia_pizzeria_crud.controller;

import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> result = pizzaRepository.findAll();
        model.addAttribute("pizzas", result);
        return "/pizzas/index";
    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/pizzas/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");
        }
    }
}
