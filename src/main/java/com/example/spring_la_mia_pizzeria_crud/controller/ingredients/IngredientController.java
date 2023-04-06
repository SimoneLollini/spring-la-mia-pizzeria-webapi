package com.example.spring_la_mia_pizzeria_crud.controller.ingredients;

import com.example.spring_la_mia_pizzeria_crud.model.AlertMessage;
import com.example.spring_la_mia_pizzeria_crud.model.Ingredient;
import com.example.spring_la_mia_pizzeria_crud.service.IngredientService;
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

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;


    @GetMapping
    public String index(Model model) {
        List<Ingredient> result;

        result = ingredientService.getAllIngredients();
        model.addAttribute("ingredientList", result);
        return "/ingredients/index";
    }

    @GetMapping("/{ingredientId}")
    public String show(@PathVariable("ingredientId") Integer id, Model model) {
        try {
            Ingredient ingredient = ingredientService.getById(id);
            model.addAttribute(ingredient);
            model.addAttribute("keyword", "");
            return "/ingredients/show";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient non trovata!");
        }
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "/ingredients/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "/ingredients/create";

        ingredientService.createIngredient(formIngredient);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingredient aggiunta con successo!"));
        return "redirect:/ingredients";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        try {
            Ingredient ingredient = ingredientService.getById(id);
            model.addAttribute("ingredient", ingredient);
            List<Ingredient> ingredientList = ingredientService.getAllIngredients();
            model.addAttribute("ingredientList", ingredientList);
            return "/ingredients/edit";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ingredient non trovata");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "/ingredients/edit";

        try {
            Ingredient updatedIngredient = ingredientService.updateIngredient(formIngredient, id);
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingredient modificata con successo!"));
            return "redirect:/ingredients";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ingredient non trovata");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean success = ingredientService.deleteById(id);
            if (success)
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingredient eliminata con successo."));
            else {
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Non puoi eliminare!"));
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Ingredient non trovata!"));
        }
        return "redirect:/ingredients";
    }

}
