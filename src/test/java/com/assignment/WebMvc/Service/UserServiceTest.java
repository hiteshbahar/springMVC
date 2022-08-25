package com.assignment.WebMvc.Service;


import com.assignment.WebMvc.model.User;
import com.assignment.WebMvc.model.UserImpl;
import com.assignment.WebMvc.repositories.UserImplRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserImplRepository userImplRepository;


    UserImpl user;

    @BeforeEach
    void setUp() {
        user = new UserImpl(1L, "someName", "someEmail@abc.com");
    }

    @Test
    void getUserById() {
        when(userImplRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getName(), userService.getUserById(1L).getName());
    }

    @Test
    void getUserByEmail() {
        when(userImplRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getEmail(), userService.getUserByEmail("someEmail@abc.com").getEmail());
    }

    @Test
    void getUsersByName() {
        when(userImplRepository.findByNameIgnoreCase(user.getName())).thenReturn(Arrays.asList(user));
        assertEquals(user.getName(), userService.getUsersByName("someName", 1, 0)
                .stream()
                .findFirst()
                .map(User::getName)
                .orElse("No User Found"));
    }

    @Test
    void createUser() {
        when(userImplRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.createUser(user));
    }

    @Test
    void updateUser() {
        user.setEmail("someOtherEmail@abc.com");
        when(userImplRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(userImplRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.updateUser(user));
    }

}