package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
}
