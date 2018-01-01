package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.service.RecipeService;
import com.teamtreehouse.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService users;

    @Autowired
    private RecipeService recipes;

    @RequestMapping(value = "/profile")
    public String getProfile(Model model){
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = users.findByUsername(username);

            model.addAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("recipes", recipes.findByUser(user));
            return "profile";
        }
        else{
            throw new AccessDeniedException("Please sign in");
        }
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
        if(users.findByUsername(user.getUsername()) != null){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Username is already taken", FlashMessage.Status.FAILURE));
            return "redirect:/signup";
        }else {
            user.setRoles(new String[]{"ROLE_USER"});
            users.save(user);
            return "redirect:/login";
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request){
        model.addAttribute("user", new User());
        return "login";
    }
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        return "redirect:/login";
    }


    public User getAuthenticatedUser() {
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return users.findByUsername(username);
        } else{
            throw new AccessDeniedException("Please sign in.");
        }
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public String accessDeniedHandler(HttpServletRequest request, AccessDeniedException ex) {
        return "redirect:/login";
    }

}
