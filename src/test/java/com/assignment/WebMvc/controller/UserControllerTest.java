package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.AppConfig.ApplicationConfig;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.User;
import com.assignment.WebMvc.model.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = ApplicationConfig.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacadeImpl bookingFacade;

    private User user;

    @BeforeEach
    void setup() {
         user = new UserImpl(1, "someName", "someemail@domain.com");
    }

    @Test
    public void getUserById() throws Exception {
        when(bookingFacade.getUserById(anyLong())).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", user));
    }

    @Test
    public void getUserByEmail() throws Exception {
        when(bookingFacade.getUserByEmail(anyString())).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/email?email=someEmail@domain.com"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", user));
    }

    @Test
    public void getUsersByName() throws Exception {
        when(bookingFacade.getUsersByName(anyString(), anyInt(), anyInt())).thenReturn(Collections.singletonList(user));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/name?name=userName"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", Collections.singletonList(user)));
    }

    @Test
    public void registerUser() throws Exception {
        when(bookingFacade.createUser(any())).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n    \"id\": 1," +
                                "\n    \"name\": \"userName\"," +
                                "\n    \"email\": \"someEmail@domain.com\"\n}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", user));
    }

    @Test
    public void updateEvent() throws Exception {
        user.setEmail("someNew@domail.com");
        when(bookingFacade.updateUser(any())).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n    \"id\": 1," +
                                "\n    \"name\": \"userName\"," +
                                "\n    \"email\": \"someNew@domain.com\"\n}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", user));
    }

    @Test
    public void deleteUserById() throws Exception {
        when(bookingFacade.deleteUser(anyLong())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", true));
    }

    @Test
    void deleteEventByIdThrowException() throws Exception {
        when(bookingFacade.deleteUser(anyLong())).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}