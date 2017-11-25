package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private IngredientRepository ingredients;
    @Autowired
    private UserRepository users;
    @Autowired
    private RecipeRepository recipes;

    @Override
    public List<Recipe> findByUser(User user) {
        return null;
    }

    @Override
    public List<Recipe> findAll() {
        return (List<Recipe>) recipes.findAll();
    }

    @Override
    public void delete(Long id) {
        recipes.delete(id);
    }

    @Override
    public void save(Recipe recipe) {
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        recipe.setUser(user);
        recipes.save(recipe);
    }

    @Override
    public Recipe findOne(Long id) {
        return recipes.findOne(id);
    }
}
