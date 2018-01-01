package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.repository.*;
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

    private final CategoryRepository categories;

    @Autowired
    public DBLoader(UserRepository users, CategoryRepository categories) {
        this.users = users;
        this.categories = categories;
    }

    @Override
    public void run(ApplicationArguments args) {

        //Some default categories to persist.
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(new Category("Breakfast"));
        newCategories.add(new Category("Lunch"));
        newCategories.add(new Category("Dinner"));
        newCategories.add(new Category("Dessert"));
        for(Category category : newCategories){
            if(categories.findByName(category.getName()) == null){
                categories.save(category);
            }
        }
        //Save test user if does not exist.
        if(users.findByUsername("test") == null){
            users.save(new User("test", "password", new String[] {"ROLE_USER"}));
        }
    }
}
