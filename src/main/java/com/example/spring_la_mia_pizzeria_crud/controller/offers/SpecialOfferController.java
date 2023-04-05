package com.example.spring_la_mia_pizzeria_crud.controller.offers;

import com.example.spring_la_mia_pizzeria_crud.model.AlertMessage;
import com.example.spring_la_mia_pizzeria_crud.model.Pizza;
import com.example.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import com.example.spring_la_mia_pizzeria_crud.service.PizzaService;
import com.example.spring_la_mia_pizzeria_crud.service.SpecialOfferService;
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
@RequestMapping("/offers")
public class SpecialOfferController {
    @Autowired
    PizzaService pizzaService;
    @Autowired
    private SpecialOfferService specialOfferService;

    @GetMapping
    public String index(Model model) {
        List<SpecialOffer> result;

        result = specialOfferService.getAllSpecialOffersSortByTitle();
        model.addAttribute("offers", result);
        return "/offers/index";
    }


    @GetMapping("/create")
    public String create(@RequestParam(name = "pizza_id") Integer id, Model model) {

//        if (id.isPresent()) {
//            try {
//                Book book = bookService.getById(id.get());
//                borrowing.setBook(book);
//            } catch (BookNotFoundException e) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//            }
//        }
        SpecialOffer specialOffer = new SpecialOffer();
        try {
            Pizza pizza = pizzaService.getById(id);
            specialOffer.setPizza(pizza);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("offer", specialOffer);
        return "/offers/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("specialOffer") SpecialOffer formOffer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "/offers/create";

        specialOfferService.createSpecialOffer(formOffer);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Offerta aggiunta con successo!"));
        return "redirect:/offers";

    }
}
