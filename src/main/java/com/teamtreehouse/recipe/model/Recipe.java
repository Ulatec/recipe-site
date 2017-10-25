package com.teamtreehouse.recipe.model;

import javax.persistence.*;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private int cookTime;
    private int prepTime;
    private Category category;
    @ManyToOne
    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
