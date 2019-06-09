package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getProfile(Model model, FullNameChangeForm fullNameChangeForm) {
        model.addAttribute("fullNameChangeForm", fullNameChangeForm);
        model.addAttribute("currentSection", "profile");
        return "profile/profile";
    }

    @PostMapping("/fullNameChange")
    public String changeFullName(Model model, Authentication authentication, @Valid @ModelAttribute("fullNameChangeForm") FullNameChangeForm fullNameChangeForm,
                                 BindingResult binding) {
        if (binding.hasErrors()) {
            return "profile/profile";
        }

        userService.changeFullName(authentication, fullNameChangeForm);

        return "redirect:/profile";
    }


}
