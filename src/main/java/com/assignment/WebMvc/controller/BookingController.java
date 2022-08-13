package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.facade.BookingFacadeImpl;
import com.assignment.WebMvc.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/bookingFacade")
public class BookingController {

    @Autowired
    private BookingFacadeImpl bookingFacade;

    @GetMapping("/status")
    public String status(Model model) {
        model.addAttribute("message", Instant.now().toString());
        return "index";
    }

    @GetMapping("/event/{id}")
    public String getEventById(Model model, @PathVariable(value="id") Long id){
        System.out.println("request Param is :" + id);
        Event eventById = bookingFacade.getEventById(id);
        model.addAttribute("message", eventById);
        return "index";
    }
}
