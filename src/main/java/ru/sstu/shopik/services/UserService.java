package ru.sstu.shopik.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.InvalidLogin;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.*;

import java.util.Locale;
import java.util.Optional;

public interface UserService {

    boolean isUserWithLoginExist(String login);

    boolean isUserWithEmailExist(String email);

    boolean isUserWithEmailExistAndEnabled(String email);

    void createUserFromRegistrationForm(UserRegistrationForm userForm, Locale locale);

    void confirmUserEmail(String token) throws UserDoesNotExist;

    void recoverUserPassword(PasswordRecoveryForm passwordRecoveryForm, Locale locale) throws UserDoesNotExist;

    Optional<User> getUserById(long id);

    void changeUserFullName(Authentication authentication, FullNameChangeForm fullNameChangeForm) throws UserDoesNotExist;

    Optional<User> getUserFromAuthentication(Authentication authentication);

    void changeUserPassword(Authentication authentication, PasswordChangeForm passwordChangeForm) throws UserDoesNotExist, InvalidCurrentPassword;

    Page<User> getPageUser(int page);

    void changeUser(UserChangeForm userChangeForm, long id) throws InvalidLogin, UserDoesNotExist;
}
