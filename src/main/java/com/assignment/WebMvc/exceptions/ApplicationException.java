package com.assignment.WebMvc.exceptions;

import org.springframework.ui.Model;

public class ApplicationException extends RuntimeException{

    public ApplicationException(String message, Model model) {
        super(message);
        model.addAttribute("message", message);
    }
}
