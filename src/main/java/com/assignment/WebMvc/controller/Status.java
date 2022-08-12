package com.assignment.WebMvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;

@Controller
public class Status {

    @GetMapping("/status")
    public String status() {
        return Instant.now().toString();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
