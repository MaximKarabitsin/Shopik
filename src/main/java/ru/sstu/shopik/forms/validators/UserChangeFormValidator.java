package ru.sstu.shopik.forms.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sstu.shopik.forms.UserChangeForm;

@Component
public class UserChangeFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserChangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserChangeForm form = (UserChangeForm) target;

        try {
            if (Integer.parseInt(form.getBalance()) < 0){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e){
            errors.rejectValue("balance", "input.number", "Invalid type");
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
