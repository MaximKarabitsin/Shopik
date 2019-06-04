package ru.sstu.shopik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/loginError")
    public String loginError(Model model, String username) {
        model.addAttribute("error", "Your username and password is invalid.");                                  //I18N
        model.addAttribute("username", username);
        return "authorization/login";
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "authorization/login";
    }



    @GetMapping("/passwordRecovery")
    public String passwordRecovery(Model model, String username) {
        return "authorization/passwordRecovery";
    }

}
