package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.service.CategoryService;
import com.teamtreehouse.recipe.service.IngredientService;
import com.teamtreehouse.recipe.service.InstructionService;
import com.teamtreehouse.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class RecipeController {
    @Autowired
    private RecipeService recipes;

    @Autowired
    private UserRepository users;

    @Autowired
    private IngredientService ingredients;

    @Autowired
    private InstructionService instructions;

    @Autowired
    private CategoryService categories;

    @RequestMapping("/")
    public String index(Model model){
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("recipes", recipes.findAll());
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRecipeForm(Model model){
        if(!model.containsAttribute("recipe")){
            model.addAttribute("recipe", new Recipe() );
        }
        model.addAttribute("ingredients", new ArrayList<Ingredient>());
        model.addAttribute("instructions", new ArrayList<Instruction>());
        model.addAttribute("action", "/new");
        return "edit";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String submitNewRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("recipe", recipe);
            return "/new";
        }
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        recipe.setUser(user);
        instructions.save(recipe.getInstructions());
        ingredients.save(recipe.getIngredients());
        recipes.save(recipe);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findById(id);
        model.addAttribute("action", String.format("/edit/%s", id));
        if(recipe != null){
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", categories.findAll());
        }
        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){

            for(FieldError error : bindingResult.getFieldErrors()){
                System.out.println(error.toString());
            }
            return String.format("redirect:/edit/%s" , recipe.getId());
        }
        recipes.save(applyFormValues(recipe));
        return "redirect:/";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String recipeDetails(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findById(id);
        if(recipe != null){
            model.addAttribute("recipe", recipe);
        }

        return "detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteRecipe(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findById(id);
        recipes.delete(recipe);
        return "redirect:/";
    }

    @RequestMapping(value = "/favorite/{id}", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long id, Model model){
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Recipe recipe = recipes.findById(id);
        if(user.getFavorites().contains(recipe)){
            user.removeFavorite(recipe);
            users.save(user);
        }else{
            user.addFavorite(recipe);
            users.save(user);
        }
        return "redirect:/";
    }



    public Recipe applyFormValues(Recipe newRecipeFromForm){
        Recipe existingRecipe = recipes.findById(newRecipeFromForm.getId());
        //Save Ingredients
        newRecipeFromForm.getIngredients().forEach(
                ingredient -> ingredients.save(ingredient));
        //Set Ingredients
        existingRecipe.setIngredients(newRecipeFromForm.getIngredients());
        //Save Ingredients
        newRecipeFromForm.getInstructions().forEach(
                instruction -> instructions.save(instruction));
        //Set Instructions
        existingRecipe.setInstructions(newRecipeFromForm.getInstructions());
        //Set Category
        existingRecipe.setCategory(newRecipeFromForm.getCategory());
        //Set name
        existingRecipe.setName(newRecipeFromForm.getName());
        //Set url
        existingRecipe.setUrl(newRecipeFromForm.getUrl());
        //Set Description
        existingRecipe.setDescription(newRecipeFromForm.getDescription());
        //Set CookTime
        existingRecipe.setCookTime(newRecipeFromForm.getCookTime());
        //Set PrepTime
        existingRecipe.setPrepTime(newRecipeFromForm.getPrepTime());
        return existingRecipe;
    }

}
