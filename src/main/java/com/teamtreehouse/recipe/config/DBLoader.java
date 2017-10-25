package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DBLoader implements ApplicationRunner{
    private final UserRepository users;

    @Autowired
    public DBLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        users.save(new User("mark", "password", new String[] {"ROLE_USER"}));
    }
}
