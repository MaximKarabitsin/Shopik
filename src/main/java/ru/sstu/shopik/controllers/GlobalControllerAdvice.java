package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.services.UserService;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addUser(Model model, Principal principal) {
        if (principal != null){
           User user = userService.getByLogin(principal.getName());
           model.addAttribute("user", user);
        }
    }

}
