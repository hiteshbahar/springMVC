package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.AppConfig.SpringConfig;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.Ticket;
import com.assignment.WebMvc.model.Ticket.Category;
import com.assignment.WebMvc.model.TicketImpl;
import com.assignment.WebMvc.model.User;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = SpringConfig.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacadeImpl bookingFacade;

    private Ticket ticket;

    @BeforeEach
    void setup() {
        ticket = new TicketImpl(100L, 10, 1L, 1L, Category.BAR);
    }

    @Test
    public void getTicketsForUser() throws Exception {
        when(bookingFacade.getBookedTickets(any(User.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(ticket));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/ticket/user?id=1")
                        .contentType(MediaType.APPLICATION_PDF)
                        .accept(MediaType.APPLICATION_PDF))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", Collections.singletonList(ticket)));
    }

    @Test
    public void bookTicket() throws Exception {
        when(bookingFacade.bookTicket(anyLong(), anyLong(), anyInt(), any(Category.class)))
                .thenReturn(ticket);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/ticket/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n    \"id\": 11," +
                                "\n    \"place\": 10," +
                                "\n    \"eventId\": 1," +
                                "\n    \"userId\": 1," +
                                "\n    \"category\": \"PREMIUM\"" +
                                "\n}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", ticket));
    }

    @Test
    public void getTicketsForEvent() throws Exception {
        when(bookingFacade.getBookedTickets(any(Event.class), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(ticket));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/ticket/event?id=1")
                        .contentType(MediaType.APPLICATION_PDF)
                        .accept(MediaType.APPLICATION_PDF))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", Collections.singletonList(ticket)));

    }

    @Test
    public void deleteTicket() throws Exception {
        when(bookingFacade.cancelTicket(anyLong())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/ticket/delete/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", true));
    }
}