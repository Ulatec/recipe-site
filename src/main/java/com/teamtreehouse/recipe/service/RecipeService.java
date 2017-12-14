package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecipeService {
    List<Recipe> findByUser(User user);
    List<Recipe> findAll();
    void delete(Recipe recipe);
    void save (Recipe recipe);
    Recipe findById(Long id);
}
