package ru.sstu.shopik.forms;

import javax.validation.constraints.NotBlank;

public class ProductAddForm {

    @NotBlank
    private String productName;

    //    @NotBlank
    private int cost;

    //    @NotBlank
    private int quantity;

    @NotBlank
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
