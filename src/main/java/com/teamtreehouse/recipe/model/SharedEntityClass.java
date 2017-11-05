package com.teamtreehouse.recipe.model;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SharedEntityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public SharedEntityClass(){
        id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

