package com.example.spring_la_mia_pizzeria_crud.controller.pizzas;

import com.example.spring_la_mia_pizzeria_crud.model.AlertMessage;
import com.example.spring_la_mia_pizzeria_crud.model.Ingredient;
import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.service.IngredientService;
import com.example.spring_la_mia_pizzeria_crud.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
//    @Autowired
//    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword) {
        List<Pizza> result;
        if (keyword.isPresent()) {
            result = pizzaService.getFilteredPizzasSortByName(keyword.get());
            model.addAttribute("keyword", keyword.get());
        } else {
            result = pizzaService.getAllPizzasSortByName();
        }
        model.addAttribute("pizzas", result);
        return "/pizzas/index";
    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model) {
        try {
            Pizza pizza = pizzaService.getById(id);
            model.addAttribute(pizza);
            model.addAttribute("keyword", "");
            return "/pizzas/show";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata!");
        }
    }


    @GetMapping("/create")
    public String create(Model model) {
        List<Ingredient> ingredientList = ingredientService.getAllIngredients();
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientList", ingredientList);
        return "/pizzas/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "/pizzas/create";

        pizzaService.createPizza(formPizza);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Pizza aggiunta con successo!"));
        return "redirect:/pizzas";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        try {
            Pizza pizza = pizzaService.getById(id);
            model.addAttribute("pizza", pizza);
            return "/pizzas/edit";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza non trovata");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "/pizzas/edit";

        try {
            Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Pizza modificata con successo!"));
            return "redirect:/pizzas";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza non trovata");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean success = pizzaService.deleteById(id);
            if (success)
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Pizza eliminata con successo."));
            else {
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Non puoi eliminare!"));
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Pizza non trovata!"));
        }
        return "redirect:/pizzas";
    }

}
