package com.assignment.WebMvc.Service;


import com.assignment.WebMvc.dao.UserDao;
import com.assignment.WebMvc.model.User;
import com.assignment.WebMvc.model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserDao userDao;

    User user;

    @BeforeEach
    void setUp() {
        user = new UserImpl(1L, "someName", "someEmail@abc.com");
    }

    @Test
    void getUserById() {
        when(userDao.get(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getName(), userService.getUserById(1L).getName());
    }

    @Test
    void getUserByEmail() {
        when(userDao.findByEmail(anyString())).thenReturn(user);
        assertEquals(user.getEmail(), userService.getUserByEmail("someEmail@abc.com").getEmail());
    }

    @Test
    void getUsersByName() {
        when(userDao.findByName(user.getName())).thenReturn(Arrays.asList(user));
        assertEquals(user.getName(), userService.getUsersByName("someName", 1, 0)
                .stream()
                .findFirst()
                .map(User::getName)
                .orElse("No User Found"));
    }

    @Test
    void createUser() {
        when(userDao.save(user)).thenReturn(user);
        assertEquals(user, userService.createUser(user));
    }

    @Test
    void updateUser() {
        user.setEmail("someOtherEmail@abc.com");
        when(userDao.update(user)).thenReturn(user);
        assertEquals(user, userService.updateUser(user));
    }

}