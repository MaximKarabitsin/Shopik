package ru.sstu.shopik.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductChangeFormFromProfile {
    @Min(0)
    private int quantity;

    @Min(0)
    @Max(100)
    private int discount;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9 .,]{1,200}")
    private String description;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
