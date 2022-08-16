package com.assignment.WebMvc.controller;

import com.assignment.WebMvc.exceptions.ApplicationException;
import com.assignment.WebMvc.facade.BookingFacadeImpl;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BookingFacadeImpl bookingFacade;

    @GetMapping("/{id}")
    public String getUserById(Model model, @PathVariable(value = "id") Long id) {
        User userById = bookingFacade.getUserById(id);
        model.addAttribute("message", userById);
        return "index";
    }

    @GetMapping("/email")
    public String getUserByEmail(Model model, @RequestParam(value = "email") String email) {
        User user = bookingFacade.getUserByEmail(email);
        model.addAttribute("message", user);
        return "index";
    }

    @GetMapping("/name")
    public String getUsersByName(Model model, @RequestParam(value = "name") String name) {
        List<User> userList = bookingFacade.getUsersByName(name, 10, 0);
        model.addAttribute("message", userList);
        return "index";
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public String registerUser(@RequestBody @NonNull UserImpl user, Model model) {
        User createdUser = bookingFacade.createUser(user);
        model.addAttribute("message", createdUser);
        return "index";
    }

    @PatchMapping(value = "/update", consumes = {"application/json"})
    public String updateEvent(Model model, @RequestBody @NonNull UserImpl user) {
        User updatedUser = bookingFacade.updateUser(user);
        model.addAttribute("message",updatedUser);
        return "index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(Model model, @PathVariable(value = "id") Long id) {
        boolean userStatus = bookingFacade.deleteUser(id);
        if (!userStatus) {
            LOGGER.debug("No event found for given id: {}", id);
            throw new ApplicationException("No Event found for given event id: " + id, model);
        }
        model.addAttribute("message", true);
        return "index";
    }
}
