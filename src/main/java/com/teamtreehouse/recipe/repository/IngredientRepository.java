package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
}
