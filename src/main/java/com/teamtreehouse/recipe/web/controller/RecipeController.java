package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.repository.UserRepository;
import com.teamtreehouse.recipe.service.IngredientService;
import com.teamtreehouse.recipe.service.InstructionService;
import com.teamtreehouse.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("recipes", recipes.findAll());
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
        instructions.save(recipe.getInstructions());
        ingredients.save(recipe.getIngredients());
        recipes.save(recipe);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findOne(id);
        model.addAttribute("action", String.format("/edit/%s", id));
        if(recipe != null){
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", Category.values());
        }
        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return String.format("redirect:/edit/%s" , recipe.getId());
        }
        recipes.save(applyFormValues(recipe));
        return "redirect:/";
    }

    @RequestMapping(value = "/detail/1", method = RequestMethod.GET)
    public String recipeDetails(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findOne(id);
        if(recipe != null){
            model.addAttribute("recipe", recipe);
        }

        return "detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteRecipe(@PathVariable Long id, Model model){
        recipes.delete(id);
        return "redirect:/";
    }


    public Recipe applyFormValues(Recipe newRecipeFromForm){
        Recipe existingRecipe = recipes.findOne(newRecipeFromForm.getId());
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

        //Set name
        existingRecipe.setName(newRecipeFromForm.getName());
        //Set Description
        existingRecipe.setDescription(newRecipeFromForm.getDescription());
        //Set CookTime
        existingRecipe.setCookTime(newRecipeFromForm.getCookTime());
        //Set PrepTime
        existingRecipe.setPrepTime(newRecipeFromForm.getPrepTime());
        return existingRecipe;
    }

}
