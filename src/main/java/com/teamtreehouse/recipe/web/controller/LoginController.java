package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("user", new User());
        return "login";
    }
}
