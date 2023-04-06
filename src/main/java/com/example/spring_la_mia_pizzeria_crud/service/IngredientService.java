package com.example.spring_la_mia_pizzeria_crud.service;

import com.example.spring_la_mia_pizzeria_crud.model.Ingredient;
import com.example.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
//
//    public Ingredient getById(Integer id) {
//        Optional<Ingredient> result = ingredientRepository.findById(id);
//        if (result.isPresent()) {
//            return result.get();
//        } else {
//            throw new RuntimeException(Integer.toString(id));
//        }
//    }
//
//    public Ingredient createIngredient(Ingredient formIngredient) {
//        Ingredient newIngredient = new Ingredient();
//        newIngredient.setName(formIngredient.getName());
//        newIngredient.setDescription(formIngredient.getDescription());
//        return ingredientRepository.save(newIngredient);
//    }
//
//    public Ingredient updateIngredient(Ingredient formIngredient, Integer id) throws RuntimeException {
//        Ingredient offerToUpdate = getById(id);
//        offerToUpdate.setName(formIngredient.getName());
//        offerToUpdate.setDescription(formIngredient.getDescription());
//        return ingredientRepository.save(offerToUpdate);
//    }
//
//    public boolean deleteById(Integer id) throws RuntimeException {
//        try {
//            ingredientRepository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
