package com.teamtreehouse.recipe.web.controller;

import com.teamtreehouse.recipe.model.*;
import com.teamtreehouse.recipe.service.*;
import com.teamtreehouse.recipe.web.exception.CategoryNotFoundException;
import com.teamtreehouse.recipe.web.exception.SearchTermNotFoundException;
import org.hamcrest.Matchers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private InstructionService instructionService;
    @Mock
    private UserService userService;
    @InjectMocks
    private RecipeController recipeController;
    private MockMvc mockMvc;

    private Recipe recipe;
    private User user;

    @Before
    public void setup(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();



        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setViewResolvers(viewResolver)
                .addFilter(new SecurityContextPersistenceFilter())
                .build();
        recipe = recipeBuilder();
        user = userBuilder();
        recipe.setUser(user);
    }

    @Test
    public void emptyRecipeTest_Redirect() throws Exception{
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/new").with(user("Test User")))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    public void recipeSaveTest() throws Exception{
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/new")
                .param("name", "Test Recipe")
                .param("description", "Test Recipe's Description")
                .param("instructions[0]", "Test Instruction")
                .param("ingredients[0]", "Test Ingredient")
                .param("url", "Test Url")
                .param("cookTime", "1")
                .param("prepTime", "1")
                .param("id", "1")
                .with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/detail/1"));
    }
    @Test
    public void getRecipeDetails() throws Exception {
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/detail/1").with(user("Test User")))
                .andExpect(model().attribute("recipe", recipe))
                .andExpect(status().isOk())
                .andExpect(view().name("detail"));
    }

    @Test
    public void getEditPage() throws Exception {
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1").with(user("Test User")))
                .andExpect(model().attribute("recipe", recipe))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }
    @Test
    public void getNewRecipePage() throws Exception {
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/new").with(user("Test User")))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void favoriteTest() throws Exception{
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/1").with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("flash", hasProperty("message", Matchers.equalTo("Favorite successfully added!"))));
    }

    @Test
    public void unfavoriteTest() throws Exception {
        user.addFavorite(recipe);
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/1").with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("flash", hasProperty("message", Matchers.equalTo("Favorite successfully removed!"))));
    }

    @Test
    public void deleteRecipeTest() throws Exception {
        recipe.setUser(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        when(userService.findByUsername("Test User")).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/delete/1").with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("flash", hasProperty("message", Matchers.equalTo("Recipe successfully deleted!"))));
    }

    @Test
    public void updateRecipeTest() throws Exception {
        List<Category> categories = new ArrayList<Category>() {{
            add(new Category("New Category"));
        }};
        List<Ingredient> ingredients = new ArrayList<Ingredient>() {{
            add(new Ingredient("New Ingredient"));
        }};;
        List<Instruction> instructions = new ArrayList<Instruction>() {{
            add(new Instruction("New Instruction"));
        }};
        recipeService.save(recipe);

        when(categoryService.findAll()).thenReturn(categories);
        when(ingredientService.findAll()).thenReturn(ingredients);
        when(instructionService.findAll()).thenReturn(instructions);
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findById(1L)).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.post("/edit/1")
                .param("name", "New Test Recipe Name")
                .param("description", "Test Recipe's New Description")
                .param("cookTime", "0")
                .param("prepTime", "0")
                .with(user("Test User")))
                .andExpect(status().is3xxRedirection())

                .andExpect(flash().attribute("flash", hasProperty("status", Matchers.equalTo(FlashMessage.Status.SUCCESS))));
    }
    @Test
    public void searchTest() throws Exception {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Test Recipe 1","brown description"));
        recipes.add(new Recipe("Test Recipe 2","red description"));
        recipes.add(new Recipe("Test Recipe 3","green description"));
        for(Recipe recipe : recipes){
            recipeService.save(recipe);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .param("searchTerm", "brown")
                .with(user("Test User")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test(expected = SearchTermNotFoundException.class)
    public void invalidSearchTest() throws Exception {
        when(recipeService.findByDescriptionContaining("yellow")).thenThrow(new SearchTermNotFoundException());
        recipeService.findByDescriptionContaining("yellow");
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .param("searchTerm", "yellow")
                .with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("ex", Matchers.instanceOf(SearchTermNotFoundException.class)))
                .andExpect(view().name("error"));
    }

    @Test(expected = CategoryNotFoundException.class)
    public void invalidCategoryTest() throws Exception {
        when(recipeService.findByCategoryName("unknown category")).thenThrow(new CategoryNotFoundException());
        recipeService.findByCategoryName("unknown category");
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                .param("category", "unknown category")
                .with(user("Test User")))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("ex", Matchers.instanceOf(CategoryNotFoundException.class)))
                .andExpect(view().name("error"));
    }
    @Test
    public void categoryTest() throws Exception {
        List<Recipe> recipes = new ArrayList<Recipe>() {{ add(recipe); }};
        when(userService.findByUsername("Test User")).thenReturn(user);
        when(recipeService.findByCategoryName("Test Category")).thenReturn(recipes);
        recipeService.findByCategoryName("Test Category");
        mockMvc.perform(MockMvcRequestBuilders.get("/category").param("category", "Test Category").with(user("Test User")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    public Recipe recipeBuilder(){
        Recipe recipe = new Recipe("Test Recipe", "Test Recipe's Description");
        User user = userBuilder();
        recipe.setId(1L);
        recipe.setCategory(new Category("Test Category"));
        recipe.setUrl("Some Test URL");
        recipe.addIngredient(new Ingredient("Test Ingredient #1"));
        recipe.addInstructions(new Instruction("Test Instruction #1"));
        recipe.setPrepTime(50);
        recipe.setCookTime(50);
        recipe.isFavorite(user);
        return recipe;
    }
    public User userBuilder(){
        User user = new User("Test User", "password", new String[] {"ROLE_USER"});
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "Test User");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        userService.save(user);
        user.setId(1L);
        return user;
    }
}
