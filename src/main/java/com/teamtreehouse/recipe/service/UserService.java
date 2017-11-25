package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    void delete(Long id);
    void save (User user);
    User findOne(Long id);
}
