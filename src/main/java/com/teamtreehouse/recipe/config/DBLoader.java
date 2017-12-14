package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.InstructionRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.service.CategoryService;
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

    private final InstructionRepository instructions;
    private final CategoryService categories;

    @Autowired
    public DBLoader(UserRepository users, RecipeRepository recipes, IngredientRepository ingredients, InstructionRepository instructions,
                    CategoryService categories) {
        this.users = users;
        this.recipes = recipes;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.categories = categories;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(new Category("Breakfast"));
        newCategories.add(new Category("Lunch"));
        newCategories.add(new Category("Dinner"));
        newCategories.add(new Category("Dessert"));
        categories.save(newCategories);

        users.save(new User("mark", "password", new String[] {"ROLE_USER"}));
        users.save(new User("test", "password", new String[] {"ROLE_USER"}));
        Recipe recipe = new Recipe("Test Recipe", "A new test recipe");
        recipe.setUser(users.findByUsername("mark"));
        recipe.setCookTime(69);
        recipe.setPrepTime(70);
        recipe.setUrl("https://www.droid-life.com/wp-content/uploads/2017/05/essential-phone-white.jpg");
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("test Ingredient");
        ingredient.setCondition("Idk");
        ingredient.setQuantity("5");
        ingredientList.add(ingredient);
        ingredients.save(ingredientList);
        ArrayList<Instruction> instructionList = new ArrayList<>();
        instructionList.add(new Instruction("test Instruction"));
        recipe.setInstructions(instructionList);
        instructions.save(instructionList);
        recipe.setIngredients(ingredientList);
        recipes.save(recipe);
    }
}
