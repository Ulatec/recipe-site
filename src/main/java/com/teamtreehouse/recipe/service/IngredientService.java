package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Ingredient;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findByUser(User user);
    List<Ingredient> findAll();
    void save (List<Ingredient> ingredients);
    void delete(Ingredient ingredient);
    void save (Ingredient ingredient);
    Ingredient findOne(Long id);
}
