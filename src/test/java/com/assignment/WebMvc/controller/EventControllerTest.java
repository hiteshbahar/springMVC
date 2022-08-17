package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.AppConfig.ApplicationConfig;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = ApplicationConfig.class)
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacadeImpl bookingFacade;


    @Test
    void getEventById() throws Exception {
        Event event = new EventImpl(1, "some Event", new Date());
        when(bookingFacade.getEventById(1)).thenReturn(event);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/event/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", event));
    }

    @Test
    void getEventByTitle() throws Exception {
        Event event = new EventImpl(1, "some music event", new Date());
        when(bookingFacade.getEventsByTitle("music", 1, 0)).thenReturn(Collections.singletonList(event));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/event/title?title=music"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", Collections.singletonList(event)));
    }

    @Test
    void registerEvent() throws Exception {
        Event event = new EventImpl(1, "some music event", new Date());
        when(bookingFacade.createEvent(any())).thenReturn(event);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/event/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\r\n    \"id\": 1,\r\n    \"title\": \"Moscow Music festival\"\r\n}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", event));
    }

    @Test
    void updateEvent() throws Exception {
        Event event = new EventImpl(1, "Pullman music event", new Date());
        when(bookingFacade.updateEvent(any())).thenReturn(event);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/event/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\r\n    \"id\": 1,\r\n    \"title\": \"Pullman Music festival\"\r\n}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", event));
    }

    @Test
    void deleteEventById() throws Exception {
        when(bookingFacade.deleteEvent(anyLong())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/event/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", true));
    }

    @Test
    void deleteEventByIdThrowException() throws Exception {
        when(bookingFacade.deleteEvent(anyLong())).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/event/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}

