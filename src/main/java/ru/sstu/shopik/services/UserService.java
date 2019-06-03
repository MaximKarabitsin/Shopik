package ru.sstu.shopik.services;

import ru.sstu.shopik.forms.UserRegistrationForm;

public interface UserService {

    boolean isUserWithLoginExist(String login);
    boolean isUserWithEmailExist(String email);
    void createUserFromRegistrationForm(UserRegistrationForm userForm);
}
