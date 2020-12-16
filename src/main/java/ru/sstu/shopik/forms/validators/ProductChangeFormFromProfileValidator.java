package ru.sstu.shopik.forms.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sstu.shopik.forms.BalanceReplenishmentForm;
import ru.sstu.shopik.forms.ProductChangeFormFromProfile;

@Component
public class ProductChangeFormFromProfileValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return ProductChangeFormFromProfile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductChangeFormFromProfile form = (ProductChangeFormFromProfile) target;

        try {
            if (Integer.parseInt(form.getQuantity()) < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("quantity", "input.number", "Invalid type");
        }

        try {
            if (Integer.parseInt(form.getDiscount()) < 0 || Integer.parseInt(form.getDiscount()) > 100) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("discount", "input.number", "Invalid type");
        }


    }

}
