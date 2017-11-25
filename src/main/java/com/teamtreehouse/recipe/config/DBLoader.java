package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.model.Ingredient;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.service.RecipeService;
import com.teamtreehouse.recipe.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DBLoader implements ApplicationRunner{

    private final UserRepository users;

    private final RecipeRepository recipes;

    private final IngredientRepository ingredients;

    @Autowired
    public DBLoader(UserRepository users, RecipeRepository recipes, IngredientRepository ingredients) {
        this.users = users;
        this.recipes = recipes;
        this.ingredients = ingredients;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        users.save(new User("mark", "password", new String[] {"ROLE_USER"}));
        users.save(new User("test", "password", new String[] {"ROLE_USER"}));
        Recipe recipe = new Recipe("Test Recipe", "A new test recipe");
        recipe.setUser(users.findByUsername("mark"));
        recipe.setCookTime(69);
        recipe.setPrepTime(70);
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("test Ingredient");
        ingredient.setCondition("Idk");
        ingredient.setQuantity("5");
        ingredientList.add(ingredient);
        ingredients.save(ingredientList);
        recipe.setIngredients(ingredientList);

        recipes.save(recipe);
    }
}
