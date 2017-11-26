package com.teamtreehouse.recipe.model;

public enum Category {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER ("Dinner"),
    DESSERT("Dessert");

    private final String name;

    Category(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
