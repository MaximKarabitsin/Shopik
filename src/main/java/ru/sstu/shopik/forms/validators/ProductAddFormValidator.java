package ru.sstu.shopik.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.services.UserService;

@Component
public class ProductAddFormValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ProductAddForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ProductAddForm form = (ProductAddForm)target;

		MultipartFile[] files = form.getFiles();

		if(files.length>10) {
			errors.rejectValue("files", "settings.section.user.type.invalid", "Invalid type");

		}

		for (MultipartFile file : files) {
			if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
				errors.rejectValue("files", "settings.section.user.type.invalid", "Invalid type");
			}
		}



	}

}
