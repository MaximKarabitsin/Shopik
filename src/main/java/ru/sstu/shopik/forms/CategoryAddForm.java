package ru.sstu.shopik.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CategoryAddForm {
    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,30}")
    private String enCategory;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,30}")
    private String ruCategory;

    private Integer motherId;

    public String getEnCategory() {
        return enCategory;
    }

    public void setEnCategory(String enCategory) {
        this.enCategory = enCategory;
    }

    public String getRuCategory() {
        return ruCategory;
    }

    public void setRuCategory(String ruCategory) {
        this.ruCategory = ruCategory;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }
}
