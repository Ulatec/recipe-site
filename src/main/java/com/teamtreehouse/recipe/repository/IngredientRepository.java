package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
}
