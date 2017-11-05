package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String getProfile(@PathVariable Long id, Model model){
        User user = users.findOne(id);
        if(user != null){
            model.addAttribute("user", user);
        }

        return "profile";
    }
}
