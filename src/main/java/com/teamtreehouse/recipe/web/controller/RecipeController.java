package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.service.*;
import com.teamtreehouse.recipe.web.exception.CategoryNotFoundException;
import com.teamtreehouse.recipe.web.exception.SearchTermNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService users;

    @Autowired
    private IngredientService ingredients;

    @Autowired
    private InstructionService instructions;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public String index(Model model){
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("recipes", recipeService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRecipeForm(Model model){
        if(!model.containsAttribute("recipe")){
            model.addAttribute("recipe", new Recipe() );
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("ingredients", new ArrayList<Ingredient>());
        model.addAttribute("instructions", new ArrayList<Instruction>());
        model.addAttribute("action", "/new");
        return "edit";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String submitNewRecipe(@Valid Recipe recipe, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipe", recipe);
            return "redirect:/new";
        }

        User user = getAuthenticatedUser();
        recipe.setUser(user);
        instructions.save(recipe.getInstructions());
        ingredients.save(recipe.getIngredients());
        recipeService.save(recipe);
        return String.format("redirect:/detail/%d", recipe.getId());
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("action", String.format("/edit/%s", id));
        if(recipe != null){
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", categoryService.findAll());
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
        recipeService.save(applyFormValues(recipe));
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe updated successfully!", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/detail/%d", recipe.getId());
    }

    @RequestMapping("/search")
    public String searchRecipes(@RequestParam String searchTerm, Model model) {
        List<Category> categories = categoryService.findAll();
        List<Recipe> recipes;
        if (searchTerm.equals("")) {
            recipes = recipeService.findAll();
        } else {
            recipes = recipeService.findByDescriptionContaining(searchTerm);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("recipes", recipes);
        model.addAttribute("action", "/search");
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("user", getAuthenticatedUser());

        return "index";
    }

    @RequestMapping("/category")
    public String category(@RequestParam String category, Model model) {
        List<Category> categories = categoryService.findAll();
        List<Recipe> recipes;
        if (category.equals("")) {
            recipes = recipeService.findAll();
        } else {
            recipes = recipeService.findByCategoryName(category);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("recipes", recipes);
        model.addAttribute("action", "/category");
        model.addAttribute("user", getAuthenticatedUser());

        return "index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String recipeDetails(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        if(recipe != null){
            model.addAttribute("recipe", recipe);
            model.addAttribute("user", getAuthenticatedUser());
        }

        return "detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Recipe recipe = recipeService.findById(id);
        if(recipe.getUser() != getAuthenticatedUser()){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("You are not the owner of that recipe.", FlashMessage.Status.FAILURE));
            return "redirect:/";
        }
        User user = recipe.getUser();
        user.removeFavorite(recipe);
        recipeService.delete(recipe);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe successfully deleted!", FlashMessage.Status.SUCCESS));
        return "redirect:/";

    }

    @RequestMapping(value = "/favorite/{id}", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long id,  RedirectAttributes redirectAttributes, HttpServletRequest request){
        User user = users.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Recipe recipe = recipeService.findById(id);
        if(user.getFavorites().contains(recipe)){
            user.removeFavorite(recipe);
            users.save(user);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Favorite successfully removed!", FlashMessage.Status.SUCCESS));
        }else{
            user.addFavorite(recipe);
            users.save(user);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Favorite successfully added!", FlashMessage.Status.SUCCESS));
        }
        return String.format("redirect:%s", request.getHeader("referer"));
    }



    public Recipe applyFormValues(Recipe newRecipeFromForm){
        Recipe existingRecipe = recipeService.findById(newRecipeFromForm.getId());
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

    @ExceptionHandler(SearchTermNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String searchTermNotFound(Model model, Exception ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noCategoriesFound(Model model, Exception ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

}
