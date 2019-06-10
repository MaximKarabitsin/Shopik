package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.services.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addUser(Model model, Authentication authentication) {
        if (authentication != null){
           model.addAttribute("user", userService.getUserFromAuthentication(authentication).orElse(null));
        }
    }

}
