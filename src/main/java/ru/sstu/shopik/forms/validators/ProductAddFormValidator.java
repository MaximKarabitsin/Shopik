package ru.sstu.shopik.forms.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.shopik.forms.ProductAddForm;

@Component
public class ProductAddFormValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return ProductAddForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductAddForm form = (ProductAddForm) target;

        try {
            if (Integer.parseInt(form.getCost()) <= 0 || Integer.parseInt(form.getQuantity()) <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("cost", "input.number", "Invalid type");
        }

        MultipartFile[] files = form.getFiles();

        if (files.length > 10) {
            errors.rejectValue("files", "settings.section.user.type.invalid", "Invalid type");

        }

        for (MultipartFile file : files) {
            if (!file.isEmpty() && !file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
                errors.rejectValue("files", "settings.section.user.type.invalid", "Invalid type");
            }
        }
    }
}
