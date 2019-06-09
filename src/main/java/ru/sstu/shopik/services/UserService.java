package ru.sstu.shopik.services;

import org.springframework.security.core.Authentication;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.UserRegistrationForm;

import java.util.Locale;

public interface UserService {

    boolean isUserWithLoginExist(String login);
    boolean isUserWithEmailExist(String email);
    boolean isUserWithEmailExistAndEnabled(String email);
    void createUserFromRegistrationForm(UserRegistrationForm userForm, Locale locale);
    boolean confirmEmail(String token);
    void recoverPassword(PasswordRecoveryForm passwordRecoveryForm, Locale locale);
    User getById(long id);
    void changeFullName(Authentication authentication, FullNameChangeForm fullNameChangeForm);
}
