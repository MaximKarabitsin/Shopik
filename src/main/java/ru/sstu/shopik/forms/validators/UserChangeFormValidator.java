package ru.sstu.shopik.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sstu.shopik.forms.UserChangeForm;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.services.UserService;

@Component
public class UserChangeFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserChangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserChangeForm form = (UserChangeForm) target;
        /*При вводе этого же логина срабатывает*/
        if (this.userService.isUserWithLoginExist(form.getLogin())) {
            errors.rejectValue("login", "enter.login.exist", "User with login already exists");
        }

        switch (form.getRole()) {
            case "admin":
            case "seller":
            case "user":
                break;
            default:
                errors.rejectValue("role", "settings.section.user.type.invalid", "Invalid type");
        }


    }

}
