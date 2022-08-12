package com.spring.mvc.assginment.springMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;

@Controller
public class Status {

    @GetMapping("/status")
    public String status() {
        return Instant.now().toString();
    }
}
