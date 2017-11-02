package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.Ingredient;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private RecipeRepository recipes;

    @Autowired
    private IngredientRepository ingredients;

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
        model.addAttribute("action", "/new");
        return "edit";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String submitNewRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("recipe", recipe);
            return "/new";
        }
        recipe.getIngredients().forEach(ingredient -> ingredients.save(ingredient));
        recipes.save(recipe);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Recipe recipe = recipes.findOne(id);
        model.addAttribute("action", String.format("/edit/%s", id));
        if(recipe != null){
            model.addAttribute("recipe", recipe);

        }
        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        System.out.println("updateRecipe()");
        if(bindingResult.hasErrors()){
            return String.format("redirect:/" , recipe.getId());
        }
        recipes.save(applyFormValues(recipe));
        return "redirect:/";
    }


    public Recipe applyFormValues(Recipe newRecipeFromForm){
        Recipe existingRecipe = recipes.findOne(newRecipeFromForm.getId());
        newRecipeFromForm.getIngredients().forEach(ingredient -> ingredients.save(ingredient));
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
