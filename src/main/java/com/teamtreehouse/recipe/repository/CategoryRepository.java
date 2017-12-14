package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long>{
    Category findByName(String name);
}
