package com.teamtreehouse.recipe.model;

import javax.persistence.Entity;

@Entity
public class Instruction extends SharedEntityClass{

    private String description;

    public Instruction() {
        super();
    }

    public Instruction(String description){
        this();
        this.description = description;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
