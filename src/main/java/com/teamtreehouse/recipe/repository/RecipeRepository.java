package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByUser(User user);
    Recipe findById(Long id);
    @Override
    @PreAuthorize("@recipeRepository.findOne(#id)?.user?.username == authentication.name")
    void delete(@Param("id") Long id);

    @Override
    @PreAuthorize("#recipe.user?.username == authentication.name")
    void delete(@Param("recipe") Recipe recipe);

    @RestResource(rel = "search", path = "search")
    List<Recipe> findByDescriptionContaining(@Param("search") String searchTerm);

    @RestResource(rel = "recipe-category", path = "categoryName")
    List<Recipe> findByCategoryName(@Param("categoryName") String name);
}
