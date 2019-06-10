package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.validators.PasswordRecoveryFormValidator;
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
        binder.addValidators(this.passwordRecoveryFormValidator);
    }

    @GetMapping
    public String login(Model model) {
        return "authorization/login";
    }

    @RequestMapping("/error")
    public String loginError(Model model, String username, Locale locale) {
        model.addAttribute("error", this.messageSource.getMessage("login.error.invalid", null, locale));                                  //I18N
        model.addAttribute("username", username);
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
        try {
            this.userService.recoverUserPassword(passwordRecoveryForm, locale);
            model.addAttribute("title", this.messageSource.getMessage("passwordRecovery.title", null, locale));
            model.addAttribute("message", this.messageSource.getMessage("passwordRecovery.email.send", null, locale));
            return "/authorization/message";
        } catch (UserDoesNotExist e) {
            return "redirect:/error";
        }
    }

}
