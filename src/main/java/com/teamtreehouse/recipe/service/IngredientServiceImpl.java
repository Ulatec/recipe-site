package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Ingredient;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientsRepository;

    @Override
    public List<Ingredient> findByUser(User user) {
        return null;
    }

    @Override
    public List<Ingredient> findAll() {
        return null;
    }

    @Override
    public void save(List<Ingredient> ingredients) {
            ingredientsRepository.save(ingredients);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Ingredient ingredient) {

    }

    @Override
    public Ingredient findOne(Long id) {
        return ingredientsRepository.findOne(id);
    }
}
