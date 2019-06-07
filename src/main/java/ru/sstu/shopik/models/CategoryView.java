package ru.sstu.shopik.models;

import ru.sstu.shopik.domain.entities.Category;

import java.util.List;

public class CategoryView {
    private Category category;
    private List<Category> subCategories;

    public CategoryView() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
