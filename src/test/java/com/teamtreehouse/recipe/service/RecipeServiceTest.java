package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Category;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.RecipeRepository;
import com.teamtreehouse.recipe.service.RecipeService;
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

    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_ShouldReturnTwo(){
        List<Recipe> recipes = Arrays.asList(
                new Recipe(), new Recipe()
        );

        when(recipeRepository.findAll()).thenReturn(recipes);
        int num = recipeService.findAll().size();
        assertEquals(2, recipeService.findAll().size());
    }
    @Test
    public void findById_ShouldReturnOne() throws Exception{
        when(recipeRepository.findOne(1L)).thenReturn(new Recipe());
        assertThat(recipeService.findById(1L), Matchers.instanceOf(Recipe.class));
        verify(recipeRepository).findOne(1L);
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

}
