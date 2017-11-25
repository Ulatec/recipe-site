package com.teamtreehouse.recipe.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Ingredient extends SharedEntityClass{

    private String quantity;
    private String condition;
    private String name;


    public Ingredient(){
        super();
    };

    public Ingredient(String name) {
        this.name = name;
    }



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
