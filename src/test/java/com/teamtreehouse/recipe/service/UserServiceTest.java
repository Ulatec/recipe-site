package com.teamtreehouse.recipe.service;


import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.model.User;
import com.teamtreehouse.recipe.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository users;

    @InjectMocks
    private UserService service = new UserServiceImpl();

    @Test
    public void addUserTest(){
        User user = new User("name","password", new String[] {"ROLE_USER"});
        service.save(user);
        verify(users, times(1)).save(user);
    }
    @Test
    public void UserDeleteTest(){
        User user = new User("name","password", new String[] {"ROLE_USER"});
        service.save(user);

        when(users.findOne(1L)).thenReturn(user);
        service.delete(1L);
        verify(users, times(1)).delete(1L);
    }
    @Test
    public void findById_ShouldReturnOne() throws Exception{
        when(users.findOne(1L)).thenReturn(new User("name","password", new String[] {"ROLE_USER"}));
        assertThat(service.findOne(1L), Matchers.instanceOf(User.class));
        verify(users).findOne(1L);
    }

}
