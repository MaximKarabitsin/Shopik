package ru.sstu.shopik.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewsAddForm {

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9 ,.!@#$%^&*();:'?/]{1,50}")
    private String newsTitle;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9 ,.!@#$%^&*();:'?/]{1,200}")
    private String description;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
