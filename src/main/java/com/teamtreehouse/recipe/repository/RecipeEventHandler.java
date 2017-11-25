package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Recipe.class)
public class RecipeEventHandler {

    private final UserRepository users;

    @Autowired
    public RecipeEventHandler(UserRepository users) {
        this.users = users;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleAfterCreate
    @HandleAfterSave
    public void handleRecipeSave(Recipe recipe) {
        System.out.println("HANDLEBEFORECREATE");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByUsername(username);
        recipe.setUser(user);
    }
}
