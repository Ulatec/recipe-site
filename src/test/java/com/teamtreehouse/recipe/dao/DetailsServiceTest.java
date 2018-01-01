package com.teamtreehouse.recipe.dao;

import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DetailsServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private DetailsService detailsService;

    @Test
    public void createUserAccountTest() throws Exception {
        User user = new User("Test User", "password", new String[]{"ROLE_USER"});
        user.setId(1L);
        when(userService.findByUsername("Test User")).thenReturn(user);
        assertThat(detailsService.loadUserByUsername("Test User").getUsername(), Matchers.is("Test User"));
    }

    @Test
    public void usernameNotFoundExceptionTest() throws Exception {
        try {
            detailsService.loadUserByUsername("unassigned user name");
        } catch (UsernameNotFoundException ex) {
            assertThat(ex.getMessage(), Matchers.is("unassigned user name not found."));
        }
    }



}
