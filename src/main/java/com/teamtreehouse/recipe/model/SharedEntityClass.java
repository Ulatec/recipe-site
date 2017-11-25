package com.teamtreehouse.recipe.model;

import javax.annotation.Generated;
import javax.persistence.*;

@MappedSuperclass
public abstract class SharedEntityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    protected SharedEntityClass(){
        id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

