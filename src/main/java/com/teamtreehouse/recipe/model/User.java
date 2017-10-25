package com.teamtreehouse.recipe.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String[] roles;


    public User(){

    }

    public User(String username, String password, String[] roles) {
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
