package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.forms.validators.PasswordRecoveryFormValidator;
import ru.sstu.shopik.forms.validators.UserRegistrationFormValidator;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @Autowired
    PasswordRecoveryFormValidator passwordRecoveryFormValidator;

    @InitBinder("passwordRecoveryForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(passwordRecoveryFormValidator);
    }

    @RequestMapping("/error")
    public String loginError(Model model, String username, Locale locale) {
        model.addAttribute("error", messageSource.getMessage("login.error.invalid", null, locale));                                  //I18N
        model.addAttribute("username", username);
        return "authorization/login";
    }

    @GetMapping
    public String login(Model model) {

        return "authorization/login";
    }


    @GetMapping("/passwordRecovery")
    public String getPasswordRecovery(Model model, PasswordRecoveryForm passwordRecoveryForm) {
        model.addAttribute("passwordRecoveryForm", passwordRecoveryForm);
        return "authorization/passwordRecovery";
    }

    @PostMapping("/passwordRecovery")
    public String recoverPassword(Locale locale, Model model, @Valid @ModelAttribute("passwordRecoveryForm") PasswordRecoveryForm passwordRecoveryForm,
                                  BindingResult binding) {

        if (binding.hasErrors()) {
            return "authorization/passwordRecovery";
        }
        userService.recoverPassword(passwordRecoveryForm, locale);
        model.addAttribute("title", messageSource.getMessage("passwordRecovery.title", null, locale));
        model.addAttribute("message", messageSource.getMessage("passwordRecovery.email.send", null, locale));
        return "/authorization/message";
    }

}
