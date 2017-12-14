package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Category;

import java.util.List;

public interface CategoryService {
    void save(List<Category> categories);
    void save(Category category);
    List<Category> findAll();
}
