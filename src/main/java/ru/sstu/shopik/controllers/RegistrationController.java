package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.forms.responses.JsonResponse;
import ru.sstu.shopik.forms.validators.UserRegistrationFormValidator;
import ru.sstu.shopik.services.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRegistrationFormValidator userValidator;

    @Autowired
    MessageSource messageSource;

    @InitBinder("userRegistrationForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.userValidator);
    }

    @GetMapping
    public String registration(Model model) {
        return "authorization/registration";
    }


    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public JsonResponse newUser(Locale locale, @ModelAttribute @Valid UserRegistrationForm userRegistrationForm, BindingResult result) {

        JsonResponse jsonResponse = new JsonResponse();
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );
            jsonResponse.setValidated(false);
            jsonResponse.setErrorMessages(errors);
        } else {
            this.userService.createUserFromRegistrationForm(userRegistrationForm, locale);
            jsonResponse.setValidated(true);
        }

        return jsonResponse;
    }

    @PostMapping(value = "/check/{fieldType}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean checkUnique(@PathVariable String fieldType, String fieldText) {
        switch (fieldType) {
            case "login":
                return !this.userService.isUserWithLoginExist(fieldText);
            case "email":
                return !this.userService.isUserWithEmailExist(fieldText);
            default:
                return false;
        }

    }

    @GetMapping("/confirm/{token}")
    public String confirmEmailWithToken(@PathVariable String token, Model model, Locale locale) {
        try {
            this.userService.confirmUserEmail(token);
            model.addAttribute("title", this.messageSource.getMessage("registration.email.title", null, locale));
            model.addAttribute("message", this.messageSource.getMessage("registration.email.confirmed", null, locale));
            return "authorization/message";
        } catch (UserDoesNotExist e) {
            return "authorization/registration";

        }
    }

    @GetMapping("/confirm")
    public String confirmEmail(Model model, Locale locale) {
        model.addAttribute("title", this.messageSource.getMessage("registration.email.title", null, locale));
        model.addAttribute("message", this.messageSource.getMessage("registration.email.confirm", null, locale));
        return "authorization/message";
    }
}
