package com.teamtreehouse.recipe.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }
    @RequestMapping("/edit")
    public String edit(Model model){
        return "edit";
    }

}
