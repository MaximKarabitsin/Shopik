package ru.sstu.shopik.services;

import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.UserRegistrationForm;

public interface UserService {

    boolean isUserWithLoginExist(String login);
    boolean isUserWithEmailExist(String email);
    boolean isUserWithEmailExistAndEnabled(String email);
    void createUserFromRegistrationForm(UserRegistrationForm userForm);
    boolean confirmEmail(String token);
    void recoverPassword(PasswordRecoveryForm passwordRecoveryForm);
}
