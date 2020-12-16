package ru.sstu.shopik.forms;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductAddForm {

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9 ]{1,50}")
    private String productName;

    @NotBlank
    @Pattern(regexp = "[0-9]{1,9}")
    private String cost;

    @NotBlank
    @Pattern(regexp = "[0-9]{1,9}")
    private String quantity;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9., ]{1,200}")
    private String description;

    @NotBlank
    private String motherCategory;

    private MultipartFile[] files;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
