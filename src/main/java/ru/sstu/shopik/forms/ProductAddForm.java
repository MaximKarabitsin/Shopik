package ru.sstu.shopik.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductAddForm {

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,50}")
    private String productName;

    @Min(1)
    private int cost;

    @Min(1)
    private int quantity;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,200}")
    private String description;

    @NotBlank
    private String motherCategory;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMotherCategory() {
        return motherCategory;
    }

    public void setMotherCategory(String motherCategory) {
        this.motherCategory = motherCategory;
    }
}
