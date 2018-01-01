package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.web.exception.CategoryNotFoundException;
import com.teamtreehouse.recipe.web.exception.SearchTermNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private UserService userService;
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

    @Override
    public List<Recipe> findByDescriptionContaining(String searchTerm) {
        List<Recipe> recipes = recipeRepository.findByDescriptionContaining(searchTerm);
        if (recipes.isEmpty()) {
            throw new SearchTermNotFoundException();
        } else {
            return recipes;
        }
    }
    @Override
    public List<Recipe> findByCategoryName(String categoryName) {
        List<Recipe> recipes = recipeRepository.findByCategoryName(categoryName);
        if (recipes.isEmpty()) {
            throw new CategoryNotFoundException();
        } else {
            return recipes;
        }
    }
}
