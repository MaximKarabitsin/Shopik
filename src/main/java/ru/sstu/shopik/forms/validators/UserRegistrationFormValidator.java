package ru.sstu.shopik.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.services.UserService;

@Component
public class UserRegistrationFormValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserRegistrationForm form = (UserRegistrationForm)target;
		if(userService.isUserWithLoginExist(form.getLogin())) {
			errors.rejectValue("login", "enter.login.exist", "User with login already exists");
		}
		if(userService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "enter.email.exist", "User with email already exists");
		}

	}

}
