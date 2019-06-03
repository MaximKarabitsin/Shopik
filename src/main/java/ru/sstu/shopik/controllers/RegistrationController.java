package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.forms.responses.JsonResponse;
import ru.sstu.shopik.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    @Autowired
    UserServiceImpl userService;


    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }


    @PostMapping(value = "/registration", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public JsonResponse newUser(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm, BindingResult result) {

        JsonResponse jsonResponse = new JsonResponse();
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );
            jsonResponse.setValidated(false);
            jsonResponse.setErrorMessages(errors);
        } else {
            userService.createUserFromRegistrationForm(userRegistrationForm);
            jsonResponse.setValidated(true);
        }

        return jsonResponse;
    }

    @PostMapping(value = "/registration/check/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean checkLogin(String login) {


        return !userService.isUserWithLoginExist(login);
    }

    @PostMapping(value = "/registration/check/email", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean checkEmail(String email) {


        return !userService.isUserWithEmailExist(email);
    }

}
