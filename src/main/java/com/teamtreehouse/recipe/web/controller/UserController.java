package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService users;

    @Autowired
    private RecipeRepository recipes;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByUsername(username);
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("recipes", recipes.findByUser(user));
        }

        return "profile";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user", new User());
        }
        return "signup";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupSubmit(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){


            return "redirect:/signup";
        }

        users.save(user);
        return "/login";
    }

}
