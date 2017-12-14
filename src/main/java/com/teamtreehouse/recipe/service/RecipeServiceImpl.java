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

//    @Autowired
//    private IngredientRepository ingredients;
    @Autowired
    private UserRepository userService;
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findByUser(User user) {
        return recipeRepository.findByUser(user);
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findOne(id);
    }

    @Override
    public List<Recipe> findAll() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public void delete(Recipe recipe) {

        List<User> users = (List<User>) userService.findAll();
        users.forEach( user -> {
            user.removeFavorite(recipe);
            userService.save(user);
        });
        recipeRepository.delete(recipe);
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

}
