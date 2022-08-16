package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.exceptions.ApplicationException;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/event")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private BookingFacadeImpl bookingFacade;

    @GetMapping("/{id}")
    public String getEventById(Model model, @PathVariable(value = "id") Long id) {
        logger.info("getting event details for id {}", id);
        Event eventById = bookingFacade.getEventById(id);
        model.addAttribute("message", eventById);
        return "index";
    }

    @GetMapping("/title")
    public String getEventByTitle(Model model, @RequestParam(value = "title") String title) {
        logger.info("getting event details for title {}", title);
        List<Event> eventsByTitle = bookingFacade.getEventsByTitle(title, 1, 0);
        model.addAttribute("message", eventsByTitle);
        return "index";
    }

    @GetMapping("/day")
    public String getEventByDay(Model model, @RequestParam(value = "day") Date date) {
        logger.info("getting event details for day {}", date);
        List<Event> eventsForDay = bookingFacade.getEventsForDay(date, 1, 0);
        model.addAttribute("message", eventsForDay);
        return "index";
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public String registerEvent(@RequestBody @NonNull EventImpl event, Model model) {
        logger.info("registering event ");
        Event createdEvent = bookingFacade.createEvent(event);
        model.addAttribute("message", createdEvent);
        return "index";
    }

    @PatchMapping(value = "/update", consumes = {"application/json"})
    public String updateEvent(Model model, @RequestBody @NonNull EventImpl event) {
        logger.info("updating event for id {}", event.getId());
        Event updateEvent = bookingFacade.updateEvent(event);
        model.addAttribute("message",updateEvent);
        return "index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEventById(Model model, @PathVariable(value = "id") Long id) {
        boolean eventStatus = bookingFacade.deleteEvent(id);
        if (!eventStatus) {
            logger.debug("No event found for given id: {}", id);
            throw new ApplicationException("No Event found for given event id: " + id, model);
        }
        model.addAttribute("message", true);
        return "index";
    }
}
