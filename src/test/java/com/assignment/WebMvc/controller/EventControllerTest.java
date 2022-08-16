package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.AppConfig.SpringConfig;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = SpringConfig.class)
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
}

