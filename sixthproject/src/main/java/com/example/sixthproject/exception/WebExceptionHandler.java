package com.example.sixthproject.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleWebException(Exception ex, Model model) {
        model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
        return "error";
    }
}
