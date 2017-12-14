package com.teamtreehouse.recipe.model;

import javax.persistence.Entity;

@Entity
public class Category extends SharedEntityClass{

    private String name;

    public Category(){
        super();
    }

    public Category(String name){

        this.name = name;
    }

    public String getName(){
        return name;
    }
}
