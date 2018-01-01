package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Category;
import com.teamtreehouse.recipe.repository.CategoryRepository;
import com.teamtreehouse.recipe.repository.IngredientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService service = new CategoryServiceImpl();


    @Test
    public void findAllReturnsTwo() throws Exception {
        Category[] categories  = {new Category("Category 1"), new Category("Category 2")};
        service.save(Arrays.asList(categories));

        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(categories));

    }
    @Test
    public void deleteCategoryTest() throws Exception {
        Category category = new Category("Test Category");
        service.save(category);

        service.delete(category);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }
    @Test
    public void addCategoryTest() throws Exception{
        Category category = new Category("Test Category");
        service.save(category);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }
}
