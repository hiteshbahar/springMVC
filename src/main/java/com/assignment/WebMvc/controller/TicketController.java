package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.exceptions.ApplicationException;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import com.assignment.WebMvc.model.Ticket;
import com.assignment.WebMvc.model.TicketImpl;
import com.assignment.WebMvc.model.User;
import com.assignment.WebMvc.model.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private BookingFacadeImpl bookingFacade;

    @PostMapping(value = "/book", consumes = {"application/json"})
    public String bookTicket(@RequestBody @NonNull TicketImpl ticket, Model model) {
        Ticket bookTicket = bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        model.addAttribute("message", bookTicket);
        return "index";
    }

    @GetMapping(value = "/user",
            consumes = "application/pdf",
            produces =  "application/pdf")
    public String getTicketsForUser(@RequestParam Long id, Model model) {
        User user = new UserImpl();
        user.setId(id);
        List<Ticket> bookedUserTickets = bookingFacade.getBookedTickets(user, 10, 0);
        model.addAttribute("message", bookedUserTickets);
        return "index";
    }

    @GetMapping(value = "/event",
            consumes = "application/pdf",
            produces =  "application/pdf")
    public String getTicketsForEvent(@RequestParam Long id, Model model) {
        Event event = new EventImpl();
        event.setId(id);
        List<Ticket> bookedUserTickets = bookingFacade.getBookedTickets(event, 10, 0);
        model.addAttribute("message", bookedUserTickets);
        return "index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id, Model model) {
        boolean ticketStatus = bookingFacade.cancelTicket(id);
        if (!ticketStatus) {
            LOGGER.debug("No event found for given id: {}", id);
            throw new ApplicationException("No Event found for given event id: " + id, model);
        }
        model.addAttribute("message", true);
        return "index";
    }
}