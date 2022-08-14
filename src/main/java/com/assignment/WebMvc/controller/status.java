package com.assignment.WebMvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/status")
public class status {

    @GetMapping("/up")
    public String status(Model model) {
        model.addAttribute("message", Instant.now().toString());
        return "index";
    }

}
