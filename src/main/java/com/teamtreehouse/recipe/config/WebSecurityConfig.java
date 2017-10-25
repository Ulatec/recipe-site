package com.teamtreehouse.recipe.config;

import com.teamtreehouse.recipe.dao.DetailsService;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.web.controller.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(User.PASSWORD_ENCODER);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    public AuthenticationSuccessHandler loginSuccessHandler(){
        return (request, response, authentication) -> response.sendRedirect("/");
    }
    public AuthenticationFailureHandler loginFailureHandler(){
        return (request, response, authentication) ->
        {
            request.getSession().setAttribute("flash", new FlashMessage("Incorrect login information", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        };
    }
}
