package com.teamtreehouse.recipe.web.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("No category match.");
    }
}
