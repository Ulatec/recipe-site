package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DBLoader implements ApplicationRunner{

    private final UserRepository users;

    private final RecipeRepository recipes;

    @Autowired
    public DBLoader(UserRepository users, RecipeRepository recipes) {
        this.users = users;
        this.recipes = recipes;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        users.save(new User("mark", "password", new String[] {"ROLE_USER"}));
        Recipe recipe = new Recipe("Test Recipe", "A new test recipe");
        recipe.setUser(users.findByUsername("mark"));
        recipe.setCookTime(69);
        recipe.setPrepTime(70);
        recipes.save(recipe);
    }
}
