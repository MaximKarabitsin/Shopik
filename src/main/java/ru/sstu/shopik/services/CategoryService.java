package ru.sstu.shopik.services;

import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.forms.CategoryAddForm;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCatalog();
    void addCategory(CategoryAddForm categoryAddForm);
    boolean checkMotherCategory(int motherId);
}
