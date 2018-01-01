package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Category;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.service.RecipeService;
import com.teamtreehouse.recipe.web.exception.CategoryNotFoundException;
import com.teamtreehouse.recipe.web.exception.SearchTermNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RecipeServiceTest {
    @InjectMocks
    private RecipeService recipeService = new RecipeServiceImpl();
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserService userService;
    @Before
    public void setupMock(){

    }

    @Test
    public void  findAllReturnsTwo(){
        List<Recipe> recipes = Arrays.asList(new Recipe(), new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(2, recipeService.findAll().size());
    }
    @Test
    public void findByIdOnlyReturnsOne() throws Exception{
        when(recipeRepository.findOne(1L)).thenReturn(new Recipe());
        assertThat(recipeService.findById(1L), Matchers.instanceOf(Recipe.class));
        verify(recipeRepository).findOne(1L);
    }
    @Test
    public void findByUserReturnsAllRecipesForUser() throws Exception {
        User user = new User("Test User", "password",  new String[]{"ROLE_USER", "ROLE_ADMIN"});
        user.setId(1L);
        List<Recipe> recipes = Arrays.asList(new Recipe("Test Recipe 1", "Test Description 1"),
                new Recipe("Test Recipe 2", "Test Description 2"),
                new Recipe("Test Recipe 3", "Test Description 3"));
        for(Recipe recipe : recipes){
            recipe.setUser(user);
        }
        recipeRepository.save(recipes);
        when(recipeRepository.findByUser(user)).thenReturn(recipes);
        assertEquals(recipeService.findByUser(user).size(), 3);
    }

    @Test
    public void saveRecipeTest(){
        Recipe newRecipe = new Recipe();
        newRecipe.setName("New Test Recipe");
        newRecipe.setCategory(new Category("Breakfast"));
        newRecipe.setCookTime(50);
        newRecipe.setPrepTime(50);
        newRecipe.setUser(new User("Test User", "password", new String[] {"ROLE_USER"}));
        when(recipeRepository.findOne(1L)).thenReturn(newRecipe);
        assertEquals(recipeService.findById(1L).getCategory(), newRecipe.getCategory());
    }
    @Test
    public void deleteRecipe(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe One");
        User user = new User("Test User", "password", new String[] {"ROLE_USER"});
        //user.setId(1L);
        user.addFavorite(recipe);

        when(recipeRepository.findOne(1L)).thenReturn(recipe);
        recipeService.delete(recipe);
        verify(recipeRepository, times(1)).delete(recipe);
    }
    @Test
    public void searchTermNotFoundExceptionTest() throws Exception {
        try {
            recipeService.findByDescriptionContaining("test search term");
        } catch (SearchTermNotFoundException exception) {
            assertThat(exception.getMessage(), is("No recipe descriptions matched your search term."));
        }
    }
    @Test
    public void categoryNotFoundExceptionTest() throws Exception {
        try {
            recipeService.findByCategoryName("test category name");
        } catch (CategoryNotFoundException exception) {
            assertThat(exception.getMessage(), is("No category match."));
        }
    }


}
