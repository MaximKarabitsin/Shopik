package ru.sstu.shopik.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sstu.shopik.forms.CategoryAddForm;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.services.CategoryService;
import ru.sstu.shopik.services.UserService;

@Component
public class CategoryAddFormValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryAddForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CategoryAddForm form = (CategoryAddForm) target;

        try {
            if (Integer.parseInt(form.getMotherId()) < 2) {
                throw new NumberFormatException();
            }
            if (!this.categoryService.checkMotherCategory(Integer.parseInt(form.getMotherId()))) {
                errors.rejectValue("motherId", "settings.section.categories.mainCategory", "Main category does not exist");
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("motherId", "settings.section.categories.mainCategory", "Main category does not exist");
        }


    }

}
