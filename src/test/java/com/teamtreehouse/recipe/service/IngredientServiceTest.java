package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Ingredient;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService = new IngredientServiceImpl();

    @Test
    public void findAllReturnsTwo() throws Exception {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Test Ingredient 1"),
                new Ingredient("Test Ingredient 2")
        );
        when(ingredientRepository.findAll()).thenReturn(ingredients);
        assertEquals(2, ingredientService.findAll().size());
        verify(ingredientRepository).findAll();
    }

    @Test
    public void findByIdOnlyReturnsOne() throws Exception {
        when(ingredientRepository.findOne(1L)).thenReturn(new Ingredient());
        assertThat(ingredientService.findOne(1L), instanceOf(Ingredient.class));
        verify(ingredientRepository).findOne(1L);
        verify(ingredientRepository, times(1)).findOne(1L);
    }

    @Test
    public void saveIngredientTest() throws Exception {
        Ingredient ingredient = new Ingredient("Test Ingredient");
        ingredient.setId(1L);
        ingredient.setCondition("Test Condition");
        ingredient.setQuantity("Test Quantity");
        when(ingredientRepository.findOne(1L)).thenReturn(ingredient);
        ingredientService.save(ingredient);
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    public void deleteIngredientTest() throws Exception {
        Ingredient ingredient = new Ingredient("Test Ingredient");
        when(ingredientRepository.findOne(1L)).thenReturn(ingredient);
        ingredientService.delete(ingredient);
        verify(ingredientRepository, times(1)).delete(ingredient);
    }
}