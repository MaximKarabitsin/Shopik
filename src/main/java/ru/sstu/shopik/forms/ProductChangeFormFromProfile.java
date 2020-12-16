package ru.sstu.shopik.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductChangeFormFromProfile {
    @NotBlank
    @Pattern(regexp = "[0-9]{1,9}")
    private String quantity;

    @NotBlank
    @Pattern(regexp = "[0-9]{1,3}")
    private String discount;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9 .,]{1,200}")
    private String description;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
