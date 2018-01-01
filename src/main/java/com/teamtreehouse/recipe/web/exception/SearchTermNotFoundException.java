package com.teamtreehouse.recipe.web.exception;

public class SearchTermNotFoundException extends RuntimeException {
    public SearchTermNotFoundException() {
        super("No recipe descriptions matched your search term.");
    }
}
