package ru.sstu.shopik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String getError(Model model) {
        return "error";
    }

    @GetMapping("/access-denied")
    public  String getAccessDenied(Model model){
        return "access-denied";
    }
}
