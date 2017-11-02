package com.teamtreehouse.recipe.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SharedEntityClass {
    @Id
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

