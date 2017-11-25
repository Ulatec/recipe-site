package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    @Override
    public User findByUsername(String username) {
        return users.findByUsername(username);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(User user) {
        users.save(user);
    }

    @Override
    public User findOne(Long id) {
        return null;
    }
}
