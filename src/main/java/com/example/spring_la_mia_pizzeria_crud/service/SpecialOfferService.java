package com.example.spring_la_mia_pizzeria_crud.service;

import com.example.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import com.example.spring_la_mia_pizzeria_crud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialOfferService {
    @Autowired
    SpecialOfferRepository specialOfferRepository;

    public List<SpecialOffer> getAllSpecialOffersSortByTitle() {
        return specialOfferRepository.findAll(Sort.by("title"));
    }

    public SpecialOffer getById(Integer id) {
        Optional<SpecialOffer> result = specialOfferRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException(Integer.toString(id));
        }
    }

    public SpecialOffer createSpecialOffer(SpecialOffer formSpecialOffer) {
        SpecialOffer newSpecialOffer = new SpecialOffer();
        newSpecialOffer.setTitle(formSpecialOffer.getTitle());
        newSpecialOffer.setEndDate(formSpecialOffer.getEndDate());
        newSpecialOffer.setStartDate(formSpecialOffer.getStartDate());
        newSpecialOffer.setPizza(formSpecialOffer.getPizza());
        return specialOfferRepository.save(newSpecialOffer);
    }

    public SpecialOffer updateSpecialOffer(SpecialOffer formSpecialOffer, Integer id) throws RuntimeException {
        SpecialOffer offerToUpdate = getById(id);
        offerToUpdate.setTitle(formSpecialOffer.getTitle());
        offerToUpdate.setEndDate(formSpecialOffer.getEndDate());
        offerToUpdate.setStartDate(formSpecialOffer.getStartDate());
        offerToUpdate.setPizza(formSpecialOffer.getPizza());
        return specialOfferRepository.save(offerToUpdate);
    }

    public boolean deleteById(Integer id) throws RuntimeException {
        try {
            specialOfferRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
